/*
 * 作者: 蒲剑
 * 
 * 所属项目: 汉唐工作流平台
 * 
 * 创建日期: 2006-7-23
 * 
 * 邮件: skynetbird@126.com
 * 
 * MSN: skynetbird@hotmail.com
 * 
 * 版权：陕西汉瑞科技信息有限公司
 * 
 */

package com.headray.core.spring.mgr;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.headray.core.spring.jdo.DyDaoHelper;
import com.headray.framework.common.generator.FormatKey;
import com.headray.framework.common.generator.TimeGenerator;
import com.headray.framework.services.db.dybeans.DynamicObject;
import com.headray.framework.services.function.StringToolKit;
import com.headray.framework.services.function.Types;
import com.headray.framework.spec.GlobalConstants;

public class GeneratorMgr extends JdbcDaoSupport implements IGenerator
{
	/*
	public String getNextValue(DynamicObject obj) throws Exception
	{		
		JdbcTemplate jt = getJdbcTemplate();
		
		String tableid = obj.getFormatAttr("tableid");
		String fieldid = obj.getFormatAttr("fieldid");	
		
		String[] spec_filter_field_names = (String[])obj.getObj(GlobalConstantsQL.spec_filter_field_names);
		String[] spec_filter_field_types = (String[])obj.getObj(GlobalConstantsQL.spec_filter_field_types);
		String[] spec_filter_field_values = (String[])obj.getObj(GlobalConstantsQL.spec_filter_field_values);
		
		StringBuffer sql = new StringBuffer();
	
		sql.append(" select a.id, a.tableid, a.cloop, a.sno, a.express, a.csql, a.ctype ");
		sql.append("   from t_app_sno a ");
		sql.append("  where 1 = 1 ");
		sql.append("    and a.tableid = " + SQLParser.charValue(tableid));
		sql.append("    and a.fieldid = " + SQLParser.charValue(fieldid));
			
		DynamicObject obj_sno_def = new DynamicObject(jt.queryForMap(sql.toString()));
		String sno = obj_sno_def.getFormatAttr("sno");
		String cloop = obj_sno_def.getFormatAttr("cloop");
		String csql = obj_sno_def.getFormatAttr("csql");
		String express = obj_sno_def.getFormatAttr("express");
		
		String rcsql = PageQueryMgr.exp_sql(csql, spec_filter_field_names, spec_filter_field_types, spec_filter_field_values);
		
		int rsno = jt.queryForInt(rcsql.toString());
		
		obj_sno_def.setAttr("rsno", rsno + 1);
		
		String nextvalue = parserFormat(obj_sno_def);
	
		return nextvalue;
	}
	*/

	//
	public String getNextValue(DynamicObject obj) throws Exception
	{		
		JdbcTemplate jt = getJdbcTemplate();

		String csql = obj.getFormatAttr("csql");
		String express = obj.getFormatAttr("express");
		
		String[] spec_filter_field_names = (String[])obj.getObj(GlobalConstants.spec_filter_field_names);
		String[] spec_filter_field_types = (String[])obj.getObj(GlobalConstants.spec_filter_field_types);
		String[] spec_filter_field_values = (String[])obj.getObj(GlobalConstants.spec_filter_field_values);
		
		StringBuffer sql = new StringBuffer();
	
		
		String rcsql = PageQueryMgr.exp_sql_fields(csql, spec_filter_field_names, spec_filter_field_types, spec_filter_field_values);
		
//		String rsno = DyDaoHelper.queryForString(jt, rcsql.toString());
		int rsno = DyDaoHelper.queryForInt(jt, rcsql.toString());
		// int rsno = jt.queryForInt(rcsql.toString());
		
		obj.setAttr("rsno", rsno + 1);
		
		String nextvalue = parserFormat(obj);
	
		return nextvalue;
	}
	
	// 
	public String parserFormat(DynamicObject obj)
	{
		String[] spec_filter_field_names = (String[])obj.getObj(GlobalConstants.spec_filter_field_names);
		String[] spec_filter_field_types = (String[])obj.getObj(GlobalConstants.spec_filter_field_types);
		String[] spec_filter_field_values = (String[])obj.getObj(GlobalConstants.spec_filter_field_values);
		
		String express = obj.getFormatAttr("express");
		String rsno = obj.getFormatAttr("rsno");
		
		// 转换预定义参数值
		String timestr = StringToolKit.formatText(obj.getFormatAttr("ctime"), TimeGenerator.getTimeStr());
		express = express.replaceAll("\\$yyyy", timestr.substring(0,4));
		express = express.replaceAll("\\$yy", timestr.substring(2,4));
		express = express.replaceAll("\\$mm", timestr.substring(5,7));
		express = express.replaceAll("\\$dd", timestr.substring(8,10));
		
		if(spec_filter_field_names!=null)
		{
			// 转换参数值
			for(int i=0;i<spec_filter_field_names.length;i++)
			{
				express = express.replaceAll("\\$" + spec_filter_field_names[i], spec_filter_field_values[i]);
			}
		}
		
		// 转换流水号
		String str_pattern = "\\#+";
		
		Pattern pattern = Pattern.compile(str_pattern);
		Matcher matcher = pattern.matcher(express);
		
		if(matcher.find())
		{
			String str_result = matcher.group();
			if(str_result!=null)
			{
				int len = str_result.length();
				express = express.replaceFirst(str_result, FormatKey.format(rsno, len));	
			}
		}
		
		// 转换流水号
		str_pattern = "\\*+";
		
		pattern = Pattern.compile(str_pattern);
		matcher = pattern.matcher(express);
		
		if(matcher.find())
		{
			String str_result = matcher.group();
			if(str_result!=null)
			{
				int len = str_result.length();
				Random random = new Random();
				String nsno = "";
				for(int i=0;i<len;i++)
				{
					int r = random.nextInt(10);
					nsno += String.valueOf(r);
				}
				express = express.replace(str_result, FormatKey.format(nsno, len));	
			}
		}		
		
		return express;
	}
}
