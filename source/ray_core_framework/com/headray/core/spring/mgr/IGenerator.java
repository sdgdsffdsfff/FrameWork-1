/*
 * 作者: 蒲剑
 * 
 * 创建日期: 2006-9-9
 * 
 * 邮件: skynetbird@126.com
 * 
 * MSN: skynetbird@hotmail.com
 * 
 * 版权：陕西汉瑞科技信息有限公司
 *
 * -----------------------------------------
 * Hello, i'm skynetbird, software designer.
 * ----------------------------------------- 
 *
 */

package com.headray.core.spring.mgr;

import com.headray.framework.services.db.dybeans.DynamicObject;


public interface IGenerator
{
	public String getNextValue(DynamicObject obj) throws Exception;
}
