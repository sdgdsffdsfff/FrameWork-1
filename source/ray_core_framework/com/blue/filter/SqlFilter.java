package com.blue.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.headray.framework.services.function.StringToolKit;

public class SqlFilter implements Filter
{
	static String sqlreg = "(?:')|(?:--)|(.*<script>.*</script>.*)|(</style><style>)|(>\"><)|" + "(\\b(select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)";

	static Pattern sqlPattern;

	static String errorPage = "/";

	protected static Logger logger = LoggerFactory.getLogger(SqlFilter.class);

	protected String sEncodingName;

	/**
	 * 初始化
	 */
	public void init(FilterConfig arg0) throws ServletException
	{
		String ep = arg0.getInitParameter("errorPage");// 读配置文件
		if (ep != null)
		{
			errorPage = ep;
		}

		String sqlstr = arg0.getInitParameter("sqlStr");// 读配置文件
		
		this.sEncodingName = arg0.getInitParameter("encoding");   

		if (StringToolKit.isBlank(sqlstr))
		{
			sqlPattern = Pattern.compile(sqlreg, Pattern.CASE_INSENSITIVE);
		}
		else
		{
			try
			{
				sqlPattern = Pattern.compile(sqlstr, Pattern.CASE_INSENSITIVE);
				
			}
			catch (Exception e)
			{
				logger.error("sql注入的正则表达式  sqlstr 配置有误！使用缺省防止sql注入表达式");
				sqlPattern = Pattern.compile(sqlreg, Pattern.CASE_INSENSITIVE);
			}
		}

	}

	/**
	 * 执行过滤
	 */
	public void doFilter(ServletRequest srequest, ServletResponse sresponse, FilterChain chain) throws ServletException, IOException
	{

		HttpServletRequest request = (HttpServletRequest) srequest;
		HttpServletResponse response = (HttpServletResponse) sresponse;
		// response.setCharacterEncoding("UTF-8");
		
		srequest.setCharacterEncoding(this.sEncodingName);   
		sresponse.setContentType("text/html;charset=" + this.sEncodingName);   
		sresponse.setCharacterEncoding(this.sEncodingName);   
       

		if (!StringToolKit.isBlank(sqlreg))
		{
			Enumeration enum1 = request.getParameterNames();

			while (enum1.hasMoreElements())
			{
				String param = enum1.nextElement().toString();
				String value = request.getParameter(param);
				logger.info("------sqlFilter: value=" + value);
				if (!isCorrectContent(value))
				{
					response.getWriter().write("<script>alert('请求参数中含有非法字符!');history.go(-1);</script>");
					logger.error(">>>>>>>>>>>>>>sql过滤未通过!!param:" + param + "value:" + value);
					return;
				}
			}
		}
		logger.debug(">>>>>>>>>>>>>>sql过滤通过!!");
		
		
		chain.doFilter(request, response);

	}

	/**
	 * 判断是否是安全值
	 * 
	 * @param paraValue
	 * @return boolean true是安全的，false为不安排的
	 */
	public static synchronized boolean isCorrectContent(String paraValue)
	{
		if (StringToolKit.isBlank(paraValue))
		{
			return true;
		}
		
		sqlPattern = Pattern.compile(sqlreg, Pattern.CASE_INSENSITIVE);
		if (sqlPattern.matcher(paraValue).find())
		{
			logger.info(sqlPattern.toString());
			logger.info(paraValue);
			
			return false;
		}
		return true;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

		String paraValue = "aaa/AAA/自己/";
		String sqlreg = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|(.*<script>.*</script>.*)|(</style><style>)|(>\"><)|" + "(\\b(select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)";

		Pattern sqlPattern = Pattern.compile(sqlreg, Pattern.CASE_INSENSITIVE);

		if (sqlPattern.matcher(paraValue).find())
		{

			System.out.println("finded!");
		}
		else
		{
			System.out.println("not finded!");
		}

	}

	public void destroy()
	{
	}

}
