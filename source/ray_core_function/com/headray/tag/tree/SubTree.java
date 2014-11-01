/*
 * Created on 2005-2-23
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.headray.tag.tree;

import java.io.*;
import javax.servlet.http.*;
import javax.servlet.ServletException;

public class SubTree extends HttpServlet
{
	public static final long serialVersionUID = 0;

	public SubTree()
	{
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String pram;
		response.setContentType("text/html;charset=UTF-8");
		pram = request.getParameter("act");
		try
		{
			OutputStream output = null;
			if (!"sub".equals(pram))
			{
				output = response.getOutputStream();
				byte data[] = new byte[1024];
				outImg(output, data, "com/headray/tag/tree/pic/" + pram + ".gif");
				return;
			}

			PrintWriter print = response.getWriter();
			print.print("<script>\r\n<!--\r\n");
			print.print(Constants.getTreeJS());
			print.print("\r\n //-->\r\n</script>");
			String key = request.getParameter("id");
			
			Tree tree = TreeFactory.getInstance(Constants.IMP_TREE_CLASS);
			String name = tree.getSubTree(key);
			print.print("<script> \r\n");
			print.print("\r\ntree.addChildren(\"" + key + "\",\"" + name + "\");");
			print.print("\r\n</script>");
			print.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void outImg(OutputStream output, byte data[], String path) throws IOException
	{
		ClassLoader cl = getClass().getClassLoader();
		java.io.InputStream in = cl.getResourceAsStream(path);
		BufferedInputStream bis = new BufferedInputStream(in);
		BufferedOutputStream bos = new BufferedOutputStream(output);
		int size = 0;
		for (size = bis.read(data); size != -1; size = bis.read(data))
			bos.write(data, 0, size);

		bis.close();
		bos.flush();
		bos.close();
	}
}