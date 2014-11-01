package com.headray.app.query.searchurl.mod;

import java.util.List;

import com.headray.framework.services.db.dybeans.DynamicObject;

public interface ISearchUrl
{
	public static String[] names = new String[]{"searchurlid", "title", "url", "nullflag", "oorder", "code", "parentmenu", "searchid", "pname","help","visible"};
	public static String[] types = new String[]{"0", "0", "0", "1", "1", "0", "0", "0", "0","0","0"};

	public List select() throws Exception;
	public List selectby(String where) throws Exception;
	
	public DynamicObject locate(String id) throws Exception;
	public DynamicObject locateby(String where) throws Exception;
	
	public String insert(DynamicObject obj) throws Exception;
	public DynamicObject update(DynamicObject obj) throws Exception;
	public String arrange(String searchid) throws Exception;	
	public List  sort(DynamicObject obj) throws Exception;	
}
