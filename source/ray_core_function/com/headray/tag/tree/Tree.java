/*
 * Created on 2005-2-23
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.headray.tag.tree;

import com.headray.tag.tree.TreeEntity;

/**
 * @author gongrui
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public abstract class Tree implements ITree
{
	public TreeEntity entity;

	public Tree()
	{
		entity = new TreeEntity();
	}

	public abstract void getChildren(String s);

	public String getSubTree(String id)
	{
		getChildren(id);
		return entity.getChildren();
	}
}
