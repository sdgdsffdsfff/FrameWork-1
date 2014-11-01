/*
 * Created on 2005-2-23
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.headray.tag.tree;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.headray.tag.tree.Constants;

/**
 * @author gongrui
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TreeEntity
{
  private StringBuffer children;

  public TreeEntity()
  {
      children = new StringBuffer();
  }

  public void addBranh(String id, String branch, Map params)
  {
      String param = makeParam(params);
      String tbranch = branch;
      int i = tbranch.indexOf(">");
      int e = tbranch.indexOf("<", i);
      if(i != -1 && e != -1)
          tbranch = branch.substring(i + 1, e);
      if(!id.equals(Constants.INVALIDATE_ID))
          children.append("<table border=0 cellspacing=0 cellpadding=0><tr><td load='no'  colspan=2 valign=center height=10 style='line-height: 15px;'><img alt='���չ��' id='img_" + id + "' src=" + Constants.PIC_CLOSE_ + " onclick=javascript:tree.loadTree('" + id + "');><img id='img" + id + "' alt='���ˢ��' src=" + Constants.PIC_CLOSED + " onclick=javascript:tree.freshTree('" + id + "');>" + "&nbsp;<span class='branch' onclick=javascript:tree.clickBranch('" + id + "','" + tbranch + "','" + param + "');>" + branch + "</span></td></tr><tr>" + "<td width=12 height=0></td><td eb=no load=no id='" + id + "' height=0><div id='div" + id + "'></div></td>" + "</tr></table>");
      else
          children.append("<table border=0 cellspacing=0 cellpadding=0><tr><td load='no'  colspan=2 valign=center height=10 style='line-height: 15px;'><img alt='���չ��' id='img_" + id + "' src=" + Constants.PIC_CLOSE_ + " onclick=javascript:tree.loadTree('" + id + "');><img id='img" + id + "' alt='���ˢ��' src=" + Constants.PIC_CLOSED + " onclick=javascript:tree.freshTree('" + id + "');>" + "&nbsp;<span class='branch'>" + branch + "</span></td></tr><tr>" + "<td width=12 height=0></td><td eb=no load=no id='" + id + "' height=0><div id='div" + id + "'></div></td>" + "</tr></table>");
  }

  public void addLeaf(String id, String leaf, Map params)
  {
      String param = makeParam(params);
      String tleaf = leaf;
      int i = tleaf.indexOf(">");
      int e = tleaf.indexOf("<", i);
      if(i != -1 && e != -1)
          tleaf = leaf.substring(i + 1, e);
      if(!id.equals(Constants.INVALIDATE_ID))
          children.append("<table border=0 cellspacing=0 cellpadding=0><tr><td valign=center height=10  style='line-height: 15px;'><img id='img" + id + "' src=" + Constants.PIC_DOCS + ">" + "&nbsp;<span class='leaf' onclick=javascript:tree.clickLeaf('" + id + "','" + tleaf + "','" + param + "');>" + leaf + "</span></td></tr><tr>" + "</tr></table>");
      else
          children.append("<table border=0 cellspacing=0 cellpadding=0><tr><td valign=center height=10  style='line-height: 15px;'><img id='img" + id + "' src=" + Constants.PIC_DOCS + ">" + "&nbsp;<span class='leaf'>" + leaf + "</span></td></tr><tr>" + "</tr></table>");
  }

  public String getChildren()
  {
      return children.toString();
  }

  private String makeParam(Map params)
  {
      String param = "";
      if(params != null)
      {
          Set keys = params.keySet();
          if(keys != null)
          {
              Iterator itr = keys.iterator();
              if(itr != null)
                  while(itr.hasNext()) 
                  {
                      String key = (String)itr.next();
                      if("".equals(param))
                          param = param + key + "=" + params.get(key);
                      else
                          param = param + "&" + key + "=" + params.get(key);
                  }
          }
      }
      return param;
  }
}
