package com.headray.app.query.search.mod;

import java.util.List;

import com.headray.framework.services.db.dybeans.DynamicObject;

public interface ISearch
{
	public static String[] names = new String[]{"searchid", "searchname", "mysql", "formname", "formaction", "mykey", "title", "orderby", "groupby", "pagesize", "entityname", "ds", "fieldkey", "templateid", "uiid", "macro1", "macro2", "macro", "ischeck", "isno", "listtableid", "divnav", "divbutton","help","positive"};
	public static String[] types = new String[]{"0", "0", "0", "0", "0", "0", "0", "0", "0", "1", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0","0","0"};

	public List select() throws Exception;
	public List selectby(String where) throws Exception;
	public DynamicObject locate(String id) throws Exception;
	public DynamicObject locateby(String where) throws Exception;
	public DynamicObject locateby_searchname(String searchname) throws Exception;

	public String insert(DynamicObject obj) throws Exception;	
	public DynamicObject update(DynamicObject obj) throws Exception;
	
	public String copy(String searchid, String newsearchid) throws Exception;

}
