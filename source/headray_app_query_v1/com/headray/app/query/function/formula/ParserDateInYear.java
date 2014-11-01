package com.headray.app.query.function.formula;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.headray.framework.common.generator.TimeGenerator;
import com.headray.framework.services.function.Types;

public class ParserDateInYear
{
	public String parser(String text)
	{
		int lp = text.indexOf("(");
		int rp = text.indexOf(")");
		int i = Types.parseInt(text.substring(lp+1, rp), 0);
		Calendar ctime = new GregorianCalendar();
		ctime.add(Calendar.DATE, 0 - ctime.get(ctime.DAY_OF_YEAR) + 1);
		ctime.add(Calendar.DATE, i);
		return TimeGenerator.getDateStr(ctime);
	}
}
