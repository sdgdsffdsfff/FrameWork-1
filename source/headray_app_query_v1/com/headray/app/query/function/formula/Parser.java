package com.headray.app.query.function.formula;

public class Parser
{
	// 当天的前后第N天
	public static String exp_cdate = "@skynet_cdate\\(-{0,1}\\d+\\)";
	// 当年的第N天
	public static String exp_cdate_in_year = "@skynet_cdate_in_year\\(-{0,1}\\d+\\)";
	
	public String parser(String text) throws Exception
	{
		String ptext = text;
		if(text.matches(exp_cdate))
		{
			ParserCurrentDate p = new ParserCurrentDate();
			ptext = p.parser(text);
			return ptext;
		}
		
		if(text.matches(exp_cdate_in_year))
		{
			ParserDateInYear p = new ParserDateInYear();
			ptext = p.parser(text);
			return ptext;
		}
		
		return ptext;
	}
	
	public static void main(String[] args) throws Exception
	{
		Parser p = new Parser();
		System.out.println(p.parser("@skynet_cdate(-1)"));
		
		System.out.println(p.parser("@skynet_cdate_in_year(0)"));
		
	}	
}
