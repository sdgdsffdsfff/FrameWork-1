package com.ray.app.workflow.select.action;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.blue.ssh.core.action.SimpleAction;
import com.ray.app.workflow.service.BFlowService;
import com.ray.app.workflow.service.IOrganService;
import com.ray.app.workflow.service.IPersonService;
import com.ray.app.workflow.service.IRoleService;

public class SelectAction extends SimpleAction
{
	private static final long serialVersionUID = -4667995261502645845L;

	@Autowired
	private BFlowService dao_bflow;

	@Autowired
	private IRoleService dao_irole;

	@Autowired
	private IPersonService dao_iperson;

	@Autowired
	private IOrganService dao_iorgan;

	public String selowner() throws Exception
	{
		return "selowner";
	}

	/**
	 * 流程属性的子页面 --读者页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String selreader() throws Exception
	{
		return "selreader";
	}

	/*
	 * 按工作组查看
	 */
	public String selectworkgroup() throws Exception
	{
		return "selectworkgroup";
	}

	/*
	 * 按角色查看
	 */
	public String selectrole() throws Exception
	{
		List<Map> roles = dao_irole.getAllRole();
		data.put("optstr", roles);
		return "selectrole";
	}

	/*
	 * 按人员查看
	 */
	public String selectperson() throws Exception
	{
		List<Map> users = dao_iperson.getAllUser();
		for (int i = 0; i < users.size(); i++)
		{
			Map aobj = users.get(i);
			if ("N".equalsIgnoreCase((String) aobj.get("isusing")))
			{
				users.remove(i);
			}
		}
		data.put("optstr", users);
		return "selectperson";
	}

	/*
	 * 按部门查看
	 */
	public String selectdept() throws Exception
	{
		List<Map> organs = dao_iorgan.getAllOrgan();
		data.put("optstr", organs);
		return "selectdept";
	}

	public String formal() throws Exception
	{
		return "formal";
	}

	public String form() throws Exception
	{
		return "form";
	}

	public BFlowService getDao_bflow()
	{
		return dao_bflow;
	}

	public void setDao_bflow(BFlowService daoBflow)
	{
		dao_bflow = daoBflow;
	}

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

	public IRoleService getDao_irole()
	{
		return dao_irole;
	}

	public void setDao_irole(IRoleService daoIrole)
	{
		dao_irole = daoIrole;
	}

	public IPersonService getDao_iperson()
	{
		return dao_iperson;
	}

	public void setDao_iperson(IPersonService daoIperson)
	{
		dao_iperson = daoIperson;
	}

	public IOrganService getDao_iorgan()
	{
		return dao_iorgan;
	}

	public void setDao_iorgan(IOrganService daoIorgan)
	{
		dao_iorgan = daoIorgan;
	}

}
