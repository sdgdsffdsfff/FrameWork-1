/*
 * Created on 2005-2-23
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.headray.tag.tree;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

import com.headray.tag.tree.Constants;

/**
 * @author gongrui TODO To change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class TreeTag extends TagSupport
{
	public static final long serialVersionUID = 0;
	
  private String branchurl;
  private String imptree;
  private String leafurl;
  private String root;
  private String rootid;
  private String rootname;
  private String servletmap;

  public TreeTag()
  {
    branchurl = "window.location=\"http://sdf?act=1&\"";
    leafurl = "window.location=\"http://sdf?act=1&\"";
    imptree = Constants.IMP_TREE_CLASS;
    servletmap = "false";
  }

  /*
   * (non-Javadoc)
   * 
   * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
   */
  public int doStartTag() throws JspException
  {
    // TODO Auto-generated method stub

    JspWriter out;

    if (root == null)
      root = ((HttpServletRequest) pageContext.getRequest()).getContextPath();
    if ("/".equals(root))
      root = "";
    Constants.ROOT = root;

    if (servletmap.equals("true"))
    {
      Constants.PIC_CLOSE = root + "/TreeInvoker?act=close";
      Constants.PIC_OPEN = root + "/TreeInvoker?act=open";
      Constants.PIC_FLUSH = root + "/TreeInvoker?act=flush";
      Constants.PIC_DOCS = root + "/TreeInvoker?act=docs";
      Constants.SUBTREE = root + "/TreeInvoker?act=sub";
      Constants.PIC_OPEN_ = root + "/TreeInvoker?act=open_";
      Constants.PIC_OPEND = root + "/TreeInvoker?act=opend";
      Constants.PIC_CLOSE_ = root + "/TreeInvoker?act=close_";
      Constants.PIC_CLOSED = root + "/TreeInvoker?act=closed";
    }
    else
    {
      Constants.PIC_CLOSE = root + Constants.SERVLET + "?act=close";
      Constants.PIC_OPEN = root + Constants.SERVLET + "?act=open";
      Constants.PIC_FLUSH = root + Constants.SERVLET + "?act=flush";
      Constants.PIC_DOCS = root + Constants.SERVLET + "?act=docs";
      Constants.SUBTREE = root + Constants.SERVLET + "?act=sub";
      Constants.PIC_OPEN_ = root + Constants.SERVLET + "?act=open_";
      Constants.PIC_OPEND = root + Constants.SERVLET + "?act=opend";
      Constants.PIC_CLOSE_ = root + Constants.SERVLET + "?act=close_";
      Constants.PIC_CLOSED = root + Constants.SERVLET + "?act=closed";
    }

    Constants.BRANCHURL = branchurl;
    Constants.LEAFURL = leafurl;
    Constants.IMP_TREE_CLASS = imptree;
    out = pageContext.getOut();
    try
    {
      out.print("<script>\r\n<!--\r\n");
      out.print(Constants.getTreeJS());
      out.print("\r\n tree.outPutRoot('" + rootid + "','" + rootname + "');");
      out.print("\r\n //-->\r\n</script>");
    }
    catch (IOException ex)
    {
      ex.printStackTrace();
    }

    return 0;
  }
  
  public String getBranchurl()
  {
      return branchurl;
  }

  public String getImptree()
  {
      return imptree;
  }

  public String getLeafurl()
  {
      return leafurl;
  }

  public String getRoot()
  {
      return root;
  }

  public String getRootid()
  {
      return rootid;
  }

  public String getRootname()
  {
      return rootname;
  }

  public String getServletmap()
  {
      return servletmap;
  }

  public void setBranchurl(String string)
  {
      branchurl = string;
  }

  public void setImptree(String string)
  {
      imptree = string;
  }

  public void setLeafurl(String string)
  {
      leafurl = string;
  }

  public void setRoot(String string)
  {
      root = string;
  }

  public void setRootid(String string)
  {
      rootid = string;
  }

  public void setRootname(String string)
  {
      rootname = string;
  }

  public void setServletmap(String servletmap)
  {
      this.servletmap = servletmap;
  }
}