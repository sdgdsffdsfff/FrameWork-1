package com.headray.app.query.searchitem.mod;

import java.util.List;

import com.headray.framework.services.db.dybeans.DynamicObject;

public interface ISearchItem
{
	public List select() throws Exception;
	public List selectby(String where) throws Exception;
	public DynamicObject locate(String id) throws Exception;
	public DynamicObject locateby(String where) throws Exception;
	
	public static String[] names = new String[]{"searchitemid", "caption", "htmlfield", "field", "qfield", "operator", "usertag", "oorder", "searchflag", "clickevent", "searchid", "param1", "param2", "mutiselect", "fieldtype", "edittype", "dstype", "defvalue", "sfield", "sparam1", "sparam2","help"};
	public static String[] types = new String[]{"0", "0", "0", "0", "0", "0", "0", "1", "1", "0", "0", "0", "0", "1", "0", "0", "0", "0", "0", "0", "0","0"};
	public String insert(DynamicObject obj) throws Exception;	
	public DynamicObject update(DynamicObject obj) throws Exception;	
	public String arrange(String  searchid) throws Exception;	
	public List  sort(DynamicObject obj) throws Exception;	
}
