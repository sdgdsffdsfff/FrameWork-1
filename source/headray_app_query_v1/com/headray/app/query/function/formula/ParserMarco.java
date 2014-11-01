package com.headray.app.query.function.formula;

import com.headray.framework.services.function.StringToolKit;


public class ParserMarco
{
	public String parser(String text, String[] names, String[] types, String[] values)
	{
		String ctext = "";
		int lp = text.indexOf("(");
		int rp = text.indexOf(")");
		String cname = text.substring(lp+1, rp);
		int index = StringToolKit.getTextInArrayIndex(names, cname);
		if("0".equals(types[index]))
		{
			ctext = "'" + StringToolKit.formatText(values[index]) + "'";
		}
		else
		{
			ctext = StringToolKit.formatText(values[index]);
		}
		
		return ctext;
	}
	
	public static void main(String[] args)
	{
		ParserMarco p = new ParserMarco();
		String[] names = new String[]{"cno", "cdate", "amount"};
		String[] values = new String[]{"S-PC32.5R","2008-10-10", "100.00"};
		String[] types = new String[]{"0","0","1"};
		String text = "#macro(cno)";
		System.out.println(p.parser(text, names, types, values ));		
		text = "#macro(cdate)";
		System.out.println(p.parser(text, names, types, values ));
		text = "#macro(amount)";
		System.out.println(p.parser(text, names, types, values ));
	}
}
