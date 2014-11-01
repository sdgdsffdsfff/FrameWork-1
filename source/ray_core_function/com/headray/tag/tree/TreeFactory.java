/*
 * Created on 2005-2-23
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.headray.tag.tree;

import com.headray.tag.tree.Tree;

/**
 * @author gongrui
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TreeFactory
{
  public TreeFactory()
  {
  }

  public static Tree getInstance(String classpatch)
      throws Exception
  {
      Tree tree = (Tree)Class.forName(classpatch).newInstance();
      return tree;
  }
}
