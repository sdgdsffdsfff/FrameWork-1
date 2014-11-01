package com.headray.core.spring.mgr;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.headray.core.data.formula.FormulaParser;
import com.headray.core.data.spec.ConstantsData;
import com.headray.core.spring.jdo.DyDaoHelper;
import com.headray.framework.services.db.SQLParser;
import com.headray.framework.services.db.dybeans.DynamicObject;
import com.headray.framework.services.function.ListToolKit;
import com.headray.framework.services.function.StringToolKit;
import com.headray.framework.spec.GlobalConstants;

public class PageQueryMgr extends JdbcDaoSupport implements IPageQuery
{
	public int browsecount(DynamicObject objold) throws Exception
	{
		QueryFromData query = new QueryFromData();
		int count = query.browsecount(objold);
		return count;
	}

	// 
	public List browse(DynamicObject objold) throws Exception
	{
		QueryFromData query = new QueryFromData();
		List datas = query.browse(objold);
		return datas;
	}

	// 
	public String page_sql_count(DynamicObject obj) throws Exception
	{
		String input_sql_app = obj.getFormatAttr(ConstantsData.spec_sql_app);

		String[] spec_filter_field_names = (String[]) obj.getObj(ConstantsData.spec_filter_field_names);
		String[] spec_filter_field_types = (String[]) obj.getObj(ConstantsData.spec_filter_field_types);
		String[] spec_filter_field_values = (String[]) obj.getObj(ConstantsData.spec_filter_field_values);

		input_sql_app = exp_sql_fields(input_sql_app, spec_filter_field_names, spec_filter_field_types, spec_filter_field_values);

		String str_result = " select count(0) from ( " + input_sql_app.replace(exp_sql_orderby(input_sql_app), "") + " ) as a "; 
		return str_result;
		
		// String sql_from = exp_sql_from(input_sql_app);
		// String sql_where = exp_sql_where(input_sql_app);
		// String str_result = " select count(0) " + sql_from + " " + sql_where;
		// return str_result;
	}

	public static String page_sql_count(String input_sql_app) throws Exception
	{
//		String sql_from = exp_sql_from(input_sql_app);
//		String sql_where = exp_sql_where(input_sql_app);
//
//		String str_result = " select count(0) " + sql_from + " " + sql_where;
//		return str_result;
		
		String str_result = " select count(0) from ( " + input_sql_app.replace(exp_sql_orderby(input_sql_app), "") + " ) as a "; 
		return str_result;	
		
		
		
	}

	public String page_sql(DynamicObject obj) throws Exception
	{
		JdbcTemplate jt = getJdbcTemplate();

		String input_sql_app = obj.getFormatAttr(ConstantsData.spec_sql_app);
		String[] spec_filter_field_names = (String[]) obj.getObj(ConstantsData.spec_filter_field_names);
		String[] spec_filter_field_types = (String[]) obj.getObj(ConstantsData.spec_filter_field_types);
		String[] spec_filter_field_values = (String[]) obj.getObj(ConstantsData.spec_filter_field_values);

		int input_pagenum = Integer.parseInt(obj.getFormatAttr(ConstantsData.spec_pagenum));
		int input_rownum = Integer.parseInt(obj.getFormatAttr(ConstantsData.spec_rownum));

		// 解析查询语句
		// 片断组成-SELECT, FROM, WHERE, ORDER BY
		input_sql_app = exp_sql_fields(input_sql_app, spec_filter_field_names, spec_filter_field_types, spec_filter_field_values);

		// 解析查询条件语句
		input_sql_app = exp_sql_page(input_sql_app, input_pagenum, input_rownum);

		// 解析数据组权限语句
		input_sql_app = exp_sql_data(jt, obj, input_sql_app);

		return input_sql_app;
	}

	public DynamicObject getdefine(String queryid) throws Exception
	{
		JdbcTemplate jt = getJdbcTemplate();

		StringBuffer sql = new StringBuffer();
		sql.append(" select * from t_sys_query where 1 = 1 and id = " + SQLParser.charValue(queryid));

		DynamicObject obj = new DynamicObject(DyDaoHelper.queryForMap(jt, sql.toString()));

		DynamicObject newobj = new DynamicObject();

		newobj.setAttr(ConstantsData.spec_sql_app, obj.getFormatAttr("csql"));
		newobj.setAttr(ConstantsData.spec_filter_field_names, obj.getFormatAttr("cfilterfield"));
		newobj.setAttr(ConstantsData.spec_filter_field_types, obj.getFormatAttr("cfilterfieldtype"));
		newobj.setAttr(ConstantsData.swap_tableid, obj.getFormatAttr("tableid"));

		return newobj;
	}

	public static String exp_sql_page(String input_sql_app, int input_pagenum, int input_rownum)
	{
		String sql_app_select = exp_sql_select(input_sql_app);
		String sql_app_from = exp_sql_from(input_sql_app);
		String sql_app_where = exp_sql_where(input_sql_app);
		String sql_app_orderby = exp_sql_orderby(input_sql_app);

		// 是否具有排序
		boolean hasorderby = false;
		// 是否排序倒序
		boolean isdesc = false;

		if (!StringToolKit.isBlank(sql_app_orderby))
		{
			hasorderby = true;
		}

		if (sql_app_orderby.indexOf("desc") >= 0)
		{
			isdesc = true;
		}

		String sql_app_orderby_nodesc = sql_app_orderby.replace("desc", "");

		StringBuffer sql = new StringBuffer();

		sql.append(" select top " + input_rownum + " * from \n");
		sql.append(" ( \n");
		sql.append(" select top " + input_rownum + " * from \n");
		sql.append(" (  \n");

		int topcount = (input_pagenum) * input_rownum;

		if (topcount >= ConstantsData.spec_maxrownum_value)
		{
			topcount = ConstantsData.spec_maxrownum_value;
		}

		sql.append(input_sql_app.replaceFirst("select", "select top " + ((input_pagenum) * input_rownum)));

		sql.append(" ) temp_a " + sql_app_orderby_nodesc.replaceAll("\\w+\\.", ""));

		if (hasorderby && (!isdesc))
		{
			sql.append(" desc ");
		}

		sql.append(" ) temp_b " + sql_app_orderby_nodesc.replaceAll("\\w+\\.", ""));

		if (hasorderby && isdesc)
		{
			sql.append(" desc ");
		}

		return sql.toString();
	}

	public static String exp_sql_select(String input_sql_app)
	{
		String str_result = new String();
		String str_pattern = new String();

		str_pattern = "select\\s+(\\*|(\\w+\\.+\\w+|\\w+))(\\s*\\,\\s*(\\w+\\.+\\w+|\\w+))*";

		Pattern pattern = Pattern.compile(str_pattern);
		Matcher matcher = pattern.matcher(input_sql_app);
		if (matcher.find())
		{
			str_result = matcher.group();
		}

		return str_result;
	}

	public static String exp_sql_from(String input_sql_app)
	{
		String str_result = new String();
		String str_pattern = new String();

		str_pattern = "from\\s+(\\w+\\._\\w+|\\w+)(\\s*$|\\s+\\w+)(\\s*\\,\\s*(\\w+\\._\\w+|\\w+)(\\s*$|\\s+\\w+))*";

		Pattern pattern = Pattern.compile(str_pattern);
		Matcher matcher = pattern.matcher(input_sql_app);
		if (matcher.find())
		{
			str_result = matcher.group();
		}

		return str_result;
	}

	public static String exp_sql_where(String input_sql_app)
	{
		String str_result = new String();
		String str_pattern = new String();

		str_pattern = "where\\s+(\\w+\\.+\\w+|\\w+)\\s*(\\<|\\<\\=|\\=|\\>|\\>\\=|like)\\s*(\\w+\\.+\\w+|\\w+|\\'%{0,1}\\W*%{0,1}\\')";
		str_pattern += "((\\s*$)|(\\s+and\\s+((\\w+\\.+\\w+|\\w+)\\s*(\\<|\\<\\=|\\=|\\>|\\>\\=|like)\\s*(\\w+\\.+\\w+|\\w+|\\'%{0,1}\\W*%{0,1}\\')))*)";

		Pattern pattern = Pattern.compile(str_pattern);
		Matcher matcher = pattern.matcher(input_sql_app);
		if (matcher.find())
		{
			str_result = matcher.group();
		}

		return str_result;
	}

	public static String exp_sql_orderby(String input_sql_app)
	{
		String str_result = new String();
		String str_pattern = new String();
		str_pattern = "order\\s+by\\s+(\\*|(\\w+\\.\\w+\\,\\s*)*((\\w+\\.\\w+\\s*)|(\\w+\\s*)))(\\s*$|\\s+desc\\s*$)";
		Pattern pattern = Pattern.compile(str_pattern);
		Matcher matcher = pattern.matcher(input_sql_app);
		if (matcher.find())
		{
			str_result = matcher.group();
		}

		return str_result;
	}

	public static String exp_sql_fields(String input_sql_app, String[] spec_filter_field_names, String[] spec_filter_field_types, String[] spec_filter_field_values)
	{
		if (spec_filter_field_names != null)
		{
			for (int i = 0; i < spec_filter_field_names.length; i++)
			{
				input_sql_app = exp_sql_filed(input_sql_app, spec_filter_field_names[i], spec_filter_field_types[i], spec_filter_field_values[i]);
				// input_sql_app = exp_sql_filed_noarg(input_sql_app, spec_filter_field_names[i], spec_filter_field_types[i], spec_filter_field_values[i]);
			}
		}

		return input_sql_app;
	}
	
	public static String exp_sql_fields(String input_sql_app, List<String> spec_filter_field_names, List<String> spec_filter_field_types, List<String> spec_filter_field_values)
	{
		if (spec_filter_field_names != null)
		{
			for (int i = 0; i < spec_filter_field_names.size(); i++)
			{
				input_sql_app = exp_sql_filed(input_sql_app, spec_filter_field_names.get(i), spec_filter_field_types.get(i), spec_filter_field_values.get(i));
				// input_sql_app = exp_sql_filed_noarg(input_sql_app, spec_filter_field_names[i], spec_filter_field_types[i], spec_filter_field_values[i]);
			}
		}

		return input_sql_app;
	}	

	public static String exp_sql_filed(String sql_input_fields, String arg, String argtype, String argvalue)
	{
		String str_result = new String();
		String str_pattern = new String();

		argvalue = StringToolKit.formatText(argvalue);
		
		if (StringToolKit.isBlank(argvalue))
		{
			str_pattern = "and\\s+((\\w+\\.\\w+\\s*)|(\\w+\\s*))(\\<|\\<\\=|\\=|\\>|\\>\\=|like)\\s*\\:" + arg.replaceAll("\\.", "\\\\.") + "(\\s+|$)";
			
			// 增加了日期函数解析判断
			str_pattern += "|and\\s+datediff\\(\\s*((\\w+\\.\\w+\\s*)|(\\w+\\s*))\\,\\s*((\\w+\\.\\w+\\s*)|(\\w+\\s*))\\,\\s*\\:" + arg.replaceAll("\\.", "\\\\.") + "\\s*\\)\\s*(\\>|\\>\\=|\\=|\\<|\\<\\=)\\s*0";

			str_pattern +="|and\\s+to_date\\(\\w+\\(\\w+\\.\\w+\\,1,10\\)\\,\\'yyyy-mm-dd\\'\\)(\\>\\=|\\<\\=)to_date\\(\\:" + arg + "\\,\\'yyyy-mm-dd\\'\\)";

			// 增加转换小写函数解析判断
			str_pattern += "|and\\s+lower\\(\\s*((\\w+\\.\\w+\\s*)|(\\w+\\s*))\\s*\\)\\s*(\\<|\\<\\=|\\=|\\>|\\>\\=|like)\\s*lower\\(\\s*\\:" + arg.replaceAll("\\.", "\\\\.") + "\\s*\\)(\\s+|$)";

			sql_input_fields = sql_input_fields.replaceAll(str_pattern, " ");

			/*
			str_pattern = "\\$" + arg.replaceAll("\\.", "\\\\.") + "\\W.*?(?=(\\s*$|\\s+and))";
			
			int pos_arg = sql_input_fields.indexOf("\\$" + arg + " ");
			int pos_last_and = sql_input_fields.substring(0, pos_arg).lastIndexOf("and ");
			
			sql_input_fields = sql_input_fields.substring(0, pos_last_and) + sql_input_fields.substring(pos_arg).replaceAll(str_pattern, "");
			*/
		}
		else
		{
			//
			if (GlobalConstants.data_type_string.equals(argtype))
			{
				if("begintime".equals(arg))
				{
					sql_input_fields.replace(":"+arg, argvalue);
				}
				
				if("endtime".equals(arg))
				{
					sql_input_fields.replace(":"+arg, argvalue);
				}
				
				
				str_pattern = "like\\s+\\:" + arg.replaceAll("\\.", "\\\\.") + "?(?=(\\s*$|\\s+|\\b))";
				sql_input_fields = sql_input_fields.replaceAll(str_pattern, "like " + SQLParser.charLikeValue(argvalue));

				// 增加转换小写函数解析判断
				str_pattern = "like\\s+lower\\(\\s*\\:" + arg.replaceAll("\\.", "\\\\.") + "\\s*\\)?(?=(\\s*$|\\s+|\\b))";
				sql_input_fields = sql_input_fields.replaceAll(str_pattern, "like lower(" + SQLParser.charLikeValue(argvalue) + ")");				
				
				str_pattern = "\\:" + arg.replaceAll("\\.", "\\\\.") + "?(?=(\\s*$|\\s+|\\b))";
				sql_input_fields = sql_input_fields.replaceAll(str_pattern, SQLParser.charValue(argvalue));				

			}
			else
			{
				str_pattern = "\\:" + arg.replaceAll("\\.", "\\\\.") + "?(?=(\\s*$|\\s+|\\b))";
				sql_input_fields = sql_input_fields.replaceAll(str_pattern, SQLParser.numberValue("'"+argvalue+"'"));
			}
		}
		
		// System.out.println(sql_input_fields);
		
		str_result = sql_input_fields;
		return str_result;
	}
	
	
	public static String exp_sql_filed_noarg(String sql_input_fields, String arg, String argtype, String argvalue)
	{
		String str_result = new String();
		String str_pattern = new String();

		if (StringToolKit.isBlank(argvalue))
		{
			str_result = sql_input_fields;
		}
		else
		{
			str_pattern = "\\$" + arg.replaceAll("\\.", "\\\\.") + "(?=(\\s+|\\W+|$))";
			if (GlobalConstants.data_type_string.equals(argtype))
			{
				str_result = sql_input_fields.replaceFirst(str_pattern, SQLParser.charValue(argvalue));
			}
			else
			{
				str_result = sql_input_fields.replaceFirst(str_pattern, SQLParser.charValue(argvalue));
			}
		}
		
		return str_result;
	}
	
	public static String exp_sql_macro(String runsql, String[] names, String[] types, String[] values)
	{
		String find = "";
		while(!StringToolKit.isBlank((find=find_sql_macro(runsql))))
		{
			runsql = runsql.replace(find, parser_macro(find, names, types, values));
		}
		return runsql;
	}
	
	public static String exp_sql_macro(String runsql, List<String> names, List<String> types, List<String> values)
	{
		int num = names.size();
		String[] anames = new String[num];
		String[] atypes = new String[num];
		String[] avalues = new String[num];
		
		for(int i=0;i<num;i++)
		{
			anames[i] = names.get(i);
			atypes[i] = types.get(i);
			avalues[i] = values.get(i);
		}
		
		String find = "";
		while(!StringToolKit.isBlank((find=find_sql_macro(runsql))))
		{
			runsql = runsql.replace(find, parser_macro(find, anames, atypes, avalues));
		}
		return runsql;
	}	
	
	protected static String find_sql_macro(String text)
	{
		String str_result = new String();
		String str_pattern = new String();
		str_pattern = "\\#macro\\(\\w+\\)";
		Pattern pattern = Pattern.compile(str_pattern);
		Matcher matcher = pattern.matcher(text);
		if (matcher.find())
		{
			str_result = matcher.group();
		}

		return str_result;	
	}
	
	protected static String parser_macro(String text, String[] names, String[] types, String[] values)
	{
		String ctext = "";
		int lp = text.indexOf("(");
		int rp = text.indexOf(")");
		String cname = text.substring(lp+1, rp);
		int index = StringToolKit.getTextInArrayIndex(names, cname);
		if(StringToolKit.isBlank(types[index]) || "0".equals(types[index]))
		{
			ctext = "'" + StringToolKit.formatText(values[index]) + "'";
		}
		else
		{
			ctext = StringToolKit.formatText(values[index]);
		}
		
		return ctext;
	}
	
	public static String exp_sql_data(JdbcTemplate jt, DynamicObject swap, String sql_input) throws Exception
	{
		// 查询是否定义数据组
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from t_sys_data ");
		sql.append("  where 1 = 1 ");
		sql.append("    and queryid = " + SQLParser.charValue(swap.getFormatAttr(ConstantsData.spec_queryid)));

		List datas = DyDaoHelper.query(jt, sql.toString());

		StringBuffer sql_data = new StringBuffer();

		for (int i = 0; i < datas.size(); i++)
		{
			DynamicObject data = (DynamicObject) datas.get(i);
			String context = data.getFormatAttr("context");
			String ctype = data.getFormatAttr("ctype");

			if ("FORMULA".equals(ctype))
			{
				sql_data.append(exp_sql_data_formula(jt, swap, context));
			}

			if (i < datas.size() - 1)
			{
				sql_data.append(" or ");
			}

		}

		if (StringToolKit.isBlank(sql_data.toString()))
		{
			sql_data.append(" 1 = 1 ");
		}

		String str_pattern_orderby = "order\\s+by\\s+(\\*|(\\w+\\.\\w+\\,\\s*)*((\\w+\\.\\w+\\s*)|(\\w+\\s*)))(\\s*$|\\s+desc\\s*$)";
		sql_input = " select * from ( " + sql_input.replaceAll(str_pattern_orderby, " ") + " ) as a where 1 = 1 and (" + sql_data + ") " + exp_sql_orderby(sql_input);

		return sql_input;
	}

	public String exp_sql_data(DynamicObject swap, String sql_input) throws Exception
	{
		JdbcTemplate jt = getJdbcTemplate();
		return exp_sql_data(jt, swap, sql_input);
	}

	private static String exp_sql_data_formula(JdbcTemplate jt, DynamicObject swap, String context) throws Exception
	{
		FormulaParser parser = new FormulaParser();
		parser.setSwapFlow(swap);
		String sql_data = parser.parserSQL(jt, context);
		return sql_data;
	}

	// 数据定义查询参数方式
	class QueryFromData
	{
		public int browsecount(DynamicObject obj) throws Exception
		{
			// 查询定义
			DynamicObject obj_def = getdefine(obj.getFormatAttr(ConstantsData.spec_queryid));

			String[] spec_filter_field_names = StringToolKit.split(obj_def.getFormatAttr("spec_filter_field_names"), ",");
			String[] spec_filter_field_types = StringToolKit.split(obj_def.getFormatAttr("spec_filter_field_types"), ",");

			obj.copyAttr(obj_def, ConstantsData.spec_sql_app);
			obj.copyAttr(obj_def, ConstantsData.swap_tableid);

			obj.setObj(ConstantsData.spec_filter_field_names, spec_filter_field_names);
			obj.setObj(ConstantsData.spec_filter_field_types, spec_filter_field_types);

			// 解析查询语句
			String sql = page_sql_count(obj);

			JdbcTemplate jt = getJdbcTemplate();

			int count = DyDaoHelper.queryForInt(jt, sql);

			return count;
		}

		public List browse(DynamicObject obj) throws Exception
		{
			// 查询定义
			DynamicObject obj_def = getdefine(obj.getFormatAttr(ConstantsData.spec_queryid));

			String[] spec_filter_field_names = StringToolKit.split(obj_def.getFormatAttr("spec_filter_field_names"), ",");
			String[] spec_filter_field_types = StringToolKit.split(obj_def.getFormatAttr("spec_filter_field_types"), ",");

			obj.copyAttr(obj_def, ConstantsData.spec_sql_app);
			obj.copyAttr(obj_def, ConstantsData.swap_tableid);

			obj.setObj(ConstantsData.spec_filter_field_names, spec_filter_field_names);
			obj.setObj(ConstantsData.spec_filter_field_types, spec_filter_field_types);

			// 解析查询语句
			String sql = page_sql(obj);

			JdbcTemplate jt = getJdbcTemplate();

			List datas = DyDaoHelper.query(jt, sql);

			return datas;
		}
	}
}
