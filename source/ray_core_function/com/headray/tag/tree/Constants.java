/*
 * Created on 2005-2-23
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.headray.tag.tree;

/**
 * @author gongrui
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Constants
{
  public static String BRANCHURL = "alert('branch');var id=\"";
  public static String IMP_TREE_CLASS = "com.blue.tag.tree.TreeHb";
  public static String INVALIDATE_ID = "-8080";
  public static String LEAFURL = "alert('leaf');var id=\"";
  public static String PIC_CLOSE = "BlueTree?act=close";
  public static String PIC_CLOSED = "BlueTree?act=closed";
  public static String PIC_CLOSE_ = "BlueTree?act=close_";
  public static String PIC_DOCS = "BlueTree?act=docs";
  public static String PIC_FLUSH = "BlueTree?act=flush";
  public static String PIC_OPEN = "BlueTree?act=open";
  public static String PIC_OPEND = "BlueTree?act=opend";
  public static String PIC_OPEN_ = "BlueTree?act=open_";
  public static String REPLACEID = "#ID#";
  public static String REPLACENAME = "#NAME#";
  public static String REPLACEPARAM = "#PARAM#";
  public static String ROOT = "/tree";
  //public static String SERVLET = "/servlet/com.blue.tag.tree.SubTree";
  public static String SERVLET = "/BlueTree";
  public static String SUBTREE = "BlueTree?act=sub";
  
  /**
   * 
   */
  public Constants()
  {
    // TODO Auto-generated constructor stub
  }
  
  public static String getTreeJS()
  {
      StringBuffer buffer = new StringBuffer();
      buffer.append("\r\n function STree() {");
      buffer.append("\r\n         this.cbicon = \"" + PIC_OPEND + "\";");
      buffer.append("\r\n         this.ebicon = \"" + PIC_CLOSED + "\";");
      buffer.append("\r\n         this.cbicon_ = \"" + PIC_OPEN_ + "\";");
      buffer.append("\r\n         this.ebicon_ = \"" + PIC_CLOSE_ + "\";");
      buffer.append("\r\n         this.refreshicon = \"\";");
      buffer.append("\r\n         this.branchicon = \"\";");
      buffer.append("\r\n         this.leaficon = \"\";");
      buffer.append("\r\n         this.blankicon = \"\";");
      buffer.append("\r\n         this.loadinfo = \"<table width=100% border=0 cellspacing=1 cellpadding=1><tr><td bordercolor=#FFCC33 bgcolor=#FFFFCC width=200>�������� ...</td></tr></table>\";");
      buffer.append("\r\n        this.subtree = \"" + SUBTREE + "&id=\";");
      buffer.append("\r\n }");
      buffer.append("\r\n STree.prototype = new STree();");
      buffer.append("\r\n STree.prototype.loadTree = function(id) {");
      buffer.append("\r\n         var targetImg = eval(\"img\" + id);");
      buffer.append("\r\n         var targetImg_ = eval(\"img_\" + id);");
      buffer.append("\r\n         var child = document.all(id);");
      buffer.append("\r\n         var targetDiv = eval(\"div\" + id);");
      buffer.append("\r\n         //���û������");
      buffer.append("\r\n         if(child.load == \"no\"){");
      buffer.append("\r\n                 targetDiv.style.display=\"block\";");
      buffer.append("\r\n                 targetDiv.innerHTML=this.loadinfo;");
      buffer.append("\r\n                 targetImg.src= this.cbicon;");
      buffer.append("\r\n                 targetImg_.src= this.cbicon_;");
      buffer.append("\r\n                 document.frames[\"hiddenframe\"].location.replace(this.subtree+id);");
      buffer.append("\r\n         }");
      buffer.append("\r\n         //����Ѿ�����");
      buffer.append("\r\n        else{");
      buffer.append("\r\n ");
      buffer.append("\r\n                 if(child.eb == \"yes\"){");
      buffer.append("\r\n                        targetDiv.style.display=\"none\";");
      buffer.append("\r\n                         targetImg.src= this.ebicon;");
      buffer.append("\r\n                         targetImg_.src= this.ebicon_;");
      buffer.append("\r\n                         child.eb = \"no\";");
      buffer.append("\r\n                 }else");
      buffer.append("\r\n                 {");
      buffer.append("\r\n                        targetDiv.style.display=\"block\";");
      buffer.append("\r\n                        targetImg.src= this.cbicon;");
      buffer.append("\r\n                        targetImg_.src= this.cbicon_;");
      buffer.append("\r\n                        child.eb = \"yes\";");
      buffer.append("\r\n                }");
      buffer.append("\r\n        }");
      buffer.append("\r\n }");
      buffer.append("\r\n STree.prototype.freshTree = function(id) {");
      buffer.append("\r\n         var child = document.all(id);");
      buffer.append("\r\n         child.load = \"no\";");
      buffer.append("\r\n         this.loadTree(id);");
      buffer.append("\r\n }");
      buffer.append("\r\n STree.prototype.clickBranch = function(id,name,param){");      
      buffer.append("\r\n var vclick=\"" + BRANCHURL + "\";");
      buffer.append("\r\n vclick = vclick.replace(\"" + REPLACEID + "\", id);");
      buffer.append("\r\n vclick = vclick.replace(\"" + REPLACENAME + "\", name);");
      buffer.append("\r\n vclick = vclick.replace(\"" + REPLACEPARAM + "\", param);");
      buffer.append("\r\n eval(vclick);");
      buffer.append("\r\n }");
      buffer.append("\r\n STree.prototype.clickLeaf = function(id,name,param){");      
      buffer.append("\r\nvar vclick=\"" + LEAFURL + "\";");
      buffer.append("\r\nvclick = vclick.replace(\"" + REPLACEID + "\", id);");
      buffer.append("\r\nvclick = vclick.replace(\"" + REPLACENAME + "\", name);");
      buffer.append("\r\nvclick = vclick.replace(\"" + REPLACEPARAM + "\", param);");
      buffer.append("\r\neval(vclick);");
      buffer.append("\r\n }");
      buffer.append("\r\n STree.prototype.addChildren = function(id,children){");
      buffer.append("        parent.document.all(\"div\"+id).innerHTML=children;");
      buffer.append("\r\n         parent.document.all(id).load=\"yes\";");
      buffer.append("\r\n         parent.document.all(id).eb=\"yes\";");
      buffer.append("\r\n }");
      buffer.append("\r\n var tree = new STree();");
      buffer.append("document.write(\"<iframe width=0 height=0  id=hiddenframe></iframe>\");");
      buffer.append("\r\n STree.prototype.outPutRoot = function(id,name){");
      buffer.append("        document.write(\"<table border=0 cellspacing=1 cellpadding=1><tr>\");");
      buffer.append("        document.write(\"<td colspan=2 height=10  style='line-height:;'><img alt='���չ��' align='absmiddle' src=" + PIC_CLOSE_ + " id='img_\"+id+\"' onclick=javascript:tree.loadTree('\"+id+\"');><img alt='���ˢ��' id='img\"+id+\"' align='absmiddle' src=" + PIC_CLOSED + " onclick=javascript:tree.freshTree('\"+id+\"');>\");");
      buffer.append("        document.write(\"&nbsp;<span class=branch onclick=javascript:tree.clickBranch('\"+id+\"');>\"+name+\"</span>\");");
      buffer.append("\r\n         document.write(\"</td>\");");
      buffer.append("\r\n         document.write(\"</tr><tr>\");");
      buffer.append("        document.write(\"<td width=10 height=0></td><td id=\"+id+\" load=no><div id=div\"+id+\"></div></td>\");");
      buffer.append("\r\n         document.write(\"</tr>\");");
      buffer.append("\r\n         document.write(\"</table>\");");
      buffer.append("\r\n }");
      return buffer.toString();
  }
}
