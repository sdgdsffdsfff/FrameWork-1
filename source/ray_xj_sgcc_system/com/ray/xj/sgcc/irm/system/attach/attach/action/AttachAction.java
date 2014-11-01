package com.ray.xj.sgcc.irm.system.attach.attach.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.blue.ssh.core.action.ActionSessionHelper;
import com.blue.ssh.core.action.SimpleAction;
import com.blue.ssh.core.orm.Page;
import com.blue.ssh.core.utils.web.struts2.Struts2Utils;
import com.headray.framework.common.generator.UUIDGenerator;
import com.headray.framework.services.db.dybeans.DynamicObject;
import com.headray.framework.services.function.StringToolKit;
import com.headray.framework.spec.GlobalConstants;
import com.ray.app.dictionary.entity.Dictionary;
import com.ray.app.dictionary.service.DictionaryService;
import com.ray.app.query.action.QueryActionHelper;
import com.ray.app.query.entity.Search;
import com.ray.app.query.service.QueryService;
import com.ray.xj.sgcc.irm.system.attach.attach.entity.Attach;
import com.ray.xj.sgcc.irm.system.attach.attach.service.AttachService;
import com.ray.xj.sgcc.irm.system.author.userrole.entity.UserRole;
import com.ray.xj.sgcc.irm.system.author.userrole.service.UserRoleService;

public class AttachAction extends SimpleAction
{
	private File fupload;

	private String fuploadFileName;

	private String _searchname;

	private String realpath;

	private String inputpath;

	private InputStream inputStream;

	private String downFileName;

	@Autowired
	private AttachService attachService;

	@Autowired
	private QueryService queryService;

	@Autowired
	private DictionaryService dictionaryService;

	@Autowired
	private UserRoleService userRoleService;

	public String browseall() throws Exception
	{
		String loginname = ActionSessionHelper._get_loginname();
		List<UserRole> userroles = userRoleService.getUserRoles("userid", loginname);
		String buttonok = "";
		for (int j = 0; j < userroles.size(); j++)
		{
			String roleid = userroles.get(j).getRoleid();
			if (roleid.equals("SYSTEM"))
			{
				buttonok = "ok";
			}
		}
		if (loginname.equals("admin"))
		{
			buttonok = "ok";
		}
		QueryActionHelper helper = new QueryActionHelper();
		Search search = (Search) BeanUtils.cloneBean(queryService.findUniqueByOfSearch("searchname", _searchname));
		arg.putAll(helper.mockArg(_searchname, queryService));

		Map amap = new HashMap();
//		amap = (HashMap) ((HashMap) arg).clone();
		amap.putAll(arg);

		search.setMysql(attachService.get_browseall_sql(amap));

		Page page = helper.mockJdbcPage(search, queryService);
		Map vo = helper.mockVO(_searchname, queryService);

		arg.put("buttonok", buttonok);
		data.put("vo", vo);
		data.put("page", page);
		return "browseall";
	}

	public String deleteattachs() throws Exception
	{

		String[] ids = Struts2Utils.getRequest().getParameter("ids").split(",");
		for (int i = 0; i < ids.length; i++)
		{
			attachService.deleteAttachById(ids[i]);
		}

		return "deleteattachs";
	}

	public String input() throws Exception
	{
		String kid = Struts2Utils.getRequest().getParameter("kid");
		String cclass = Struts2Utils.getRequest().getParameter("cclass");
		String readonly = Struts2Utils.getRequest().getParameter("readonly");

		List<Attach> attachs = attachService.findAttachesByKid(kid, cclass);

		arg.put("kid", kid);
		arg.put("cclass", cclass);
		arg.put("readonly", readonly);
		data.put("attachs", attachs);
		return "input";
	}

	public String upload() throws Exception
	{
		DynamicObject login_token = (DynamicObject) Struts2Utils.getRequest().getSession().getAttribute(GlobalConstants.sys_login_token);
		String loginname = login_token.getFormatAttr(GlobalConstants.sys_login_user);
		String username = login_token.getFormatAttr(GlobalConstants.sys_login_username);
		Timestamp nowtime = new Timestamp(System.currentTimeMillis());

		String cclassid = Struts2Utils.getRequest().getParameter("cclassid");
		String cclass = Struts2Utils.getRequest().getParameter("cclass");

		// 保存上传附件
		String dir = String.valueOf(new GregorianCalendar().get(Calendar.YEAR));

		try
		{
			Map<String, String> map = uploaddoc(dir);
			// 保存附件记录;

			Attach attach = new Attach();
			attach.setFilename(map.get("filename"));
			attach.setFileextname(map.get("fileextname"));
			attach.setCname(fuploadFileName);
			attach.setCurl(dir.replace('\\', '/'));

			attach.setCclass(cclass);
			attach.setCreateuser(username);
			attach.setCreateuserid(loginname);
			attach.setCreatetime(nowtime);

			attachService.save(attach);

			// 返回
			HttpServletResponse response = ServletActionContext.getResponse();
			response.getWriter().append("['" + attach.getCurl() + "','" + attach.getId() + "','" + attach.getCname() + "','" + attach.getFilename() + "']");

		}
		catch (Exception e)
		{
			// 返回
			HttpServletResponse response = ServletActionContext.getResponse();
			response.getWriter().append(e.getMessage());
		}

		return null;
	}

	public String uploadbusiness() throws Exception
	{
		DynamicObject login_token = (DynamicObject) Struts2Utils.getRequest().getSession().getAttribute(GlobalConstants.sys_login_token);
		String loginname = login_token.getFormatAttr(GlobalConstants.sys_login_user);
		String username = login_token.getFormatAttr(GlobalConstants.sys_login_username);
		Timestamp nowtime = new Timestamp(System.currentTimeMillis());

		String kid = Struts2Utils.getRequest().getParameter("kid");
		String cclass = Struts2Utils.getRequest().getParameter("cclass");

		// 保存上传附件
		String dir = String.valueOf(new GregorianCalendar().get(Calendar.YEAR));

		try
		{
			Map<String, String> map = uploaddoc(dir);
			// 保存附件记录;

			Attach attach = new Attach();
			attach.setFilename(map.get("filename"));
			attach.setFileextname(map.get("fileextname"));
			attach.setCname(fuploadFileName);
			attach.setCurl(dir.replace('\\', '/'));

			attach.setCreateuser(username);
			attach.setCreateuserid(loginname);
			attach.setCreatetime(nowtime);

			attachService.uploadbusiness(attach, kid, cclass);

			// 返回
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			HttpServletResponse response = ServletActionContext.getResponse();
			// response.getWriter().flush();
			response.getWriter().append("['" + attach.getCurl() + "','" + attach.getId() + "','" + attach.getCname() + "','" + attach.getCreateuser() + "','" + simpleDateFormat.format(attach.getCreatetime()) + "']");

		}
		catch (Exception e)
		{
			// 返回
			HttpServletResponse response = ServletActionContext.getResponse();
			response.getWriter().append(e.getMessage());
		}

		return null;
	}
	
	public String uploaddefect() throws Exception
	{
		DynamicObject login_token = (DynamicObject) Struts2Utils.getRequest().getSession().getAttribute(GlobalConstants.sys_login_token);
		String loginname = login_token.getFormatAttr(GlobalConstants.sys_login_user);
		String username = login_token.getFormatAttr(GlobalConstants.sys_login_username);
		Timestamp nowtime = new Timestamp(System.currentTimeMillis());

		String kid = Struts2Utils.getRequest().getParameter("kid");
		String cclass = Struts2Utils.getRequest().getParameter("cclass");

		// 保存上传附件
		String dir = String.valueOf(new GregorianCalendar().get(Calendar.YEAR));

		try
		{
			Map<String, String> map = uploaddefectdoc();
			// 保存附件记录;

			Attach attach = new Attach();
			attach.setFilename(map.get("filename"));
			attach.setFileextname(map.get("fileextname"));
			attach.setCname(fuploadFileName);
			attach.setCurl(dir.replace('\\', '/'));

			attach.setCreateuser(username);
			attach.setCreateuserid(loginname);
			attach.setCreatetime(nowtime);

			attachService.uploadbusiness(attach, kid, cclass);

			// 返回
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			HttpServletResponse response = ServletActionContext.getResponse();
			// response.getWriter().flush();
			response.getWriter().append("['" + attach.getCurl() + "','" + attach.getId() + "','" + attach.getCname() + "','" + attach.getCreateuser() + "','" + simpleDateFormat.format(attach.getCreatetime()) + "']");

		}
		catch (Exception e)
		{
			// 返回
			HttpServletResponse response = ServletActionContext.getResponse();
			response.getWriter().append(e.getMessage());
		}

		return null;
	}

	protected Map<String, String> uploaddoc(String dir) throws Exception
	{
		// 根据classid查找分类目录并按照层级转换为完整目录名；
		// 根据完整目录名创建附件目录，并上传附件至该目录；
		Map map = new HashMap();

		Dictionary dictionary = dictionaryService.findUniqueBy("dkey", "app.system.attach.root");
		String root = dictionary.getDvalue();

		if (StringToolKit.isBlank(root))
		{
			// throw new Exception("系统未设定上传文档目录，无法上传，请联系系统管理员！");
			throw new Exception("error");
		}

		String rootdir = root + "upload";
		String webrootdir = "/upload";

		File rootdirfile = new File(rootdir);
		if (!rootdirfile.isDirectory())
		{
			// throw new Exception("系统未创建上传文档目录，无法上传，请联系系统管理员！");
			throw new Exception("error");
		}

		String extName = "";
		String newFileName = "";
		String nowTimeStr = "";

		Random r = new Random();

		HttpServletResponse response = ServletActionContext.getResponse();

		response.setCharacterEncoding("utf-8");

		if (fuploadFileName.lastIndexOf(".") >= 0)
		{
			extName = fuploadFileName.substring(fuploadFileName.lastIndexOf(".") + 1);
		}

		newFileName = UUIDGenerator.getInstance().getNextValue() + "." + extName;

		File dirname = new File(rootdir + "\\" + dir);

		if (!dirname.isDirectory())
		{
			// 目录不存在
			dirname.mkdir(); // 创建目录
		}

		fupload.renameTo(new File(rootdir + "\\" + dir + "\\" + newFileName));

		map.put("filename", newFileName);
		map.put("fileextname", extName);

		return map;
	}
	
	protected Map<String, String> uploaddefectdoc() throws Exception
	{
		// 根据classid查找分类目录并按照层级转换为完整目录名；
		// 根据完整目录名创建附件目录，并上传附件至该目录；
		Map map = new HashMap();

		Dictionary dictionary = dictionaryService.findUniqueBy("dkey", "app.system.attach.root");
		String root = dictionary.getDvalue();

		if (StringToolKit.isBlank(root))
		{
			// throw new Exception("系统未设定上传文档目录，无法上传，请联系系统管理员！");
			throw new Exception("error");
		}

		String rootdir = root + "历史导入";
		
		File rootdirfile = new File(rootdir);
		if (!rootdirfile.isDirectory())
		{
			// throw new Exception("系统未创建上传文档目录，无法上传，请联系系统管理员！");
			throw new Exception("error");
		}

		String extName = "";
		String newFileName = "";
		String nowTimeStr = "";

		Random r = new Random();

		HttpServletResponse response = ServletActionContext.getResponse();

		response.setCharacterEncoding("utf-8");
		
		File dirname = new File(rootdir);

		if (!dirname.isDirectory())
		{
			// 目录不存在
			dirname.mkdir(); // 创建目录
		}
	    File file = new File(rootdir + "\\" + fuploadFileName);
	    file.delete();
		fupload.renameTo(new File(rootdir + "\\" + fuploadFileName));

		map.put("filename", newFileName);
		map.put("fileextname", extName);

		return map;
	}

	public String mainframe() throws Exception
	{
		return "mainframe";
	}

	public String downloadbyid() throws Exception
	{
		String id = Struts2Utils.getRequest().getParameter("id");
		String attachid = Struts2Utils.getRequest().getParameter("attachid");
		if(StringToolKit.isBlank(attachid))
		{
			attachid = id;
		}		
		Attach attach = attachService.getAttach(attachid);
		fuploadFileName = attach.getFilename();
		String cname = attach.getCname();
		inputpath = attach.getFilename();

		Dictionary dictionary = dictionaryService.findUniqueBy("dkey", "app.system.attach.root");
		String root = dictionary.getDvalue();

		realpath = root + "upload\\";
		inputpath = attach.getCurl() + "\\" + inputpath;

		try
		{
			System.out.println(realpath + inputpath);
			inputStream = new FileInputStream(realpath + inputpath);
		}
		catch (Exception e)
		{
			System.out.println(e);
			throw new Exception("未找到该文件！");
		}

		this.downFileName = cname;

	   HttpServletRequest request = ServletActionContext.getRequest();  
        String Agent = request.getHeader("User-Agent");  
        if (null != Agent) {  
            Agent = Agent.toLowerCase();  
            if (Agent.indexOf("firefox") != -1) {  
            	downFileName = new String(downFileName.getBytes(),"iso8859-1");  
            } else if (Agent.indexOf("msie") != -1) {  
            	//downFileName = java.net.URLEncoder.encode(downFileName,"UTF-8");  
            } else {  
            	downFileName = java.net.URLEncoder.encode(downFileName,"UTF-8");  
            }  
        }  
		return SUCCESS;
	}

	// 删除上传文件(包括数据库记录以及服务器文件)
	public String isDelete() throws Exception
	{
		String attachid = Struts2Utils.getRequest().getParameter("attachid");
		String flag = "";
		try
		{
			attachService.delete(attachid);
			flag = "done";
		}
		catch (Exception e)
		{
			System.out.println("删除文件操作出错");
			e.printStackTrace();
			flag = "erre！";
		}

		arg.put("flag", flag);

		return "delete";

	}

	public File getFupload()
	{
		return fupload;
	}

	public void setFupload(File fupload)
	{
		this.fupload = fupload;
	}

	public String getFuploadFileName()
	{
		return fuploadFileName;
	}

	public void setFuploadFileName(String fuploadFileName)
	{
		this.fuploadFileName = fuploadFileName;
	}

	public String get_searchname()
	{
		return _searchname;
	}

	public void set_searchname(String searchname)
	{
		_searchname = searchname;
	}

	public AttachService getAttachService()
	{
		return attachService;
	}

	public void setAttachService(AttachService attachService)
	{
		this.attachService = attachService;
	}

	public QueryService getQueryService()
	{
		return queryService;
	}

	public void setQueryService(QueryService queryService)
	{
		this.queryService = queryService;
	}

	public String getRealpath()
	{
		return realpath;
	}

	public void setRealpath(String realpath)
	{
		this.realpath = realpath;
	}

	public String getInputpath()
	{
		return inputpath;
	}

	public void setInputpath(String inputpath)
	{
		this.inputpath = inputpath;
	}

	public String getDownFileName()
	{
		return downFileName;
	}

	public void setDownFileName(String downFileName) 
	{
//		this.downFileName = new String(downFileName.getBytes("ISO-8859-1"),"UTF-8");
		
		this.downFileName = downFileName;
	}

	public InputStream getInputStream() throws UnsupportedEncodingException
	{
		
		HttpServletResponse response = ServletActionContext.getResponse();
		
		String agent = Struts2Utils.getRequest().getHeader("user-agent");
	
		StringTokenizer st = new StringTokenizer(agent,";");

		st.nextToken();

		//得到用户的浏览器名

		String userbrowser = st.nextToken().toLowerCase();
		
		if(userbrowser.indexOf("firefox") == -1 ){
			response.setHeader("Content-Disposition", "attachment;fileName="
	                + java.net.URLEncoder.encode(downFileName,"UTF-8"));
		}
		
		return inputStream;
	}

	public void setInputStream(InputStream inputStream)
	{	
		
		this.inputStream = inputStream;
	}
}
