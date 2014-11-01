package com.blue.ssh.core.action;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

/**
 * Struts2中典型CRUD Action的抽象基类.
 * 
 * 主要定义了对Preparable,ModelDriven接口的使用,以及CRUD函数和返回值的命名.
 * 
 * @param <T>
 *            CRUDAction所管理的对象类型.
 * 
 * @author calvin
 */
public abstract class BaseAction<T> extends ActionSupport implements ModelDriven<T>, Preparable{

	private static final long serialVersionUID = -1653204626115064950L;

	/** 进行增删改操作后,以redirect方式重新打开action默认页的result名. */
	public static final String RELOAD = "reload";

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected Map data = new HashMap();
	
	protected Map arg = new HashMap();

	// -- Preparable函数 --//
	/**
	 * 实现空的prepare()函数,屏蔽所有Action函数公共的二次绑定.
	 */
	public void prepare() throws Exception {
	}

	/**
	 * 在locate()前执行二次绑定.
	 */
	public void prepareLocate() throws Exception {
		prepareModel();
	}

	/**
	 * 在save()前执行二次绑定.
	 */
	public void prepareSave() throws Exception {
		prepareModel();
	}
	
	/**
	 * 在update()前执行二次绑定.
	 */
	public void prepareUpdate() throws Exception {
		prepareModel();
	}
	
	/**
	 * 在insert()前执行二次绑定.
	 */
	public void prepareInsert() throws Exception {
		prepareModel();
	}
	
	/**
	 * 等同于prepare()的内部函数,供prepardMethodName()函数调用. 
	 */
	protected abstract void prepareModel() throws Exception;

	public Map getData()
	{
		return data;
	}

	public void setData(Map data)
	{
		this.data = data;
	}

	public Map getArg()
	{
		return arg;
	}

	public void setArg(Map arg)
	{
		this.arg = arg;
	}

}
