package com.headray.app.query.function.formula;

import com.headray.framework.common.generator.TimeGenerator;
import com.headray.framework.services.function.Types;

public class ParserCurrentDate
{
	public String parser(String text)
	{
//		Pattern p = Pattern.compile("@currentdate\\(-{0,1}\\d+\\)");
//		Matcher m = p.matcher(text);
//		String ctext = m.replaceAll("");
//		return ctext;
		
//		String pattern = "@currentdate\\(-{0,1}\\d+\\)";
//		String ctext = text.replaceAll(pattern, "");
//		return ctext;
		
		int lp = text.indexOf("(");
		int rp = text.indexOf(")");
		int i = Types.parseInt(text.substring(lp+1, rp), 0);
		return TimeGenerator.getDateStr(i);
	}
}
