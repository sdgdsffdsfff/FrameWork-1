package com.headray.app.query.function;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SearchHandler
{
	private String searchstring;

	private SearchCriteria searchcriteria;

	private static Log logger = LogFactory.getLog(SearchHandler.class);

	public SearchCriteria getSearchcriteria()
	{
		return searchcriteria;
	}

	public void setSearchcriteria(SearchCriteria searchcriteria)
	{
		this.searchcriteria = searchcriteria;
	}

	public String getSearchstring()
	{
		return searchstring;
	}

	public void setSearchstring(String searchstring)
	{
		this.searchstring = searchstring;
	}

}
