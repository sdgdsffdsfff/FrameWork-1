package com.headray.app.query.function;

import java.util.ArrayList;
import java.util.List;

public class SearchCriteria
{
	private List bindings;

	private List sorts;

	private List groups;
	
	public SearchCriteria()
	{
		bindings = new ArrayList();
		sorts = new ArrayList();
		groups = new ArrayList();
	}	
}
