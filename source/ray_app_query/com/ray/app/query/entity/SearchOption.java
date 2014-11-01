package com.ray.app.query.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "T_SYS_SEARCHOPTION")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Proxy(lazy = false)
public class SearchOption implements java.io.Serializable
{
//	@JoinColumn(name = "searchid", referencedColumnName = "searchid", nullable = false)
//	@ManyToOne
//	private Search search;

	private String searchid;
	
	@Id
	@Column(length = 32)
	private String searchoptionid;

	private String field;

	private String title;

	private Short visible;

	private Short oorder;

	private String displayformat;

	private String fieldtype;

	private String displaywidth;

	private Short printable;

	private String edittype;

	@Column(length = 2000)
	private String param1;

	private String param2;

	private String calctype;

	private String url;

	private String linkfield;

	private String fieldkey;

	private Short pkey;

	private String required;

	private String dstype;

	private String defvalue;

	private String trattr;

	private String readonly;

	private String issum;

	private String help;

	private String userflag;

	private String htmlfield;

	private Short tcols;

	private Short trows;
	
	private Short valuewidth;	

	public SearchOption()
	{
	}

	public String getSearchid()
	{
		return searchid;
	}

	public void setSearchid(String searchid)
	{
		this.searchid = searchid;
	}

	public String getSearchoptionid()
	{
		return searchoptionid;
	}

	public void setSearchoptionid(String searchoptionid)
	{
		this.searchoptionid = searchoptionid;
	}

	public String getField()
	{
		return field;
	}

	public void setField(String field)
	{
		this.field = field;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public Short getVisible()
	{
		return visible;
	}

	public void setVisible(Short visible)
	{
		this.visible = visible;
	}

	public Short getOorder()
	{
		return oorder;
	}

	public void setOorder(Short oorder)
	{
		this.oorder = oorder;
	}

	public String getDisplayformat()
	{
		return displayformat;
	}

	public void setDisplayformat(String displayformat)
	{
		this.displayformat = displayformat;
	}

	public String getFieldtype()
	{
		return fieldtype;
	}

	public void setFieldtype(String fieldtype)
	{
		this.fieldtype = fieldtype;
	}

	public String getDisplaywidth()
	{
		return displaywidth;
	}

	public void setDisplaywidth(String displaywidth)
	{
		this.displaywidth = displaywidth;
	}

	public Short getPrintable()
	{
		return printable;
	}

	public void setPrintable(Short printable)
	{
		this.printable = printable;
	}

	public String getEdittype()
	{
		return edittype;
	}

	public void setEdittype(String edittype)
	{
		this.edittype = edittype;
	}

	public String getParam1()
	{
		return param1;
	}

	public void setParam1(String param1)
	{
		this.param1 = param1;
	}

	public String getParam2()
	{
		return param2;
	}

	public void setParam2(String param2)
	{
		this.param2 = param2;
	}

	public String getCalctype()
	{
		return calctype;
	}

	public void setCalctype(String calctype)
	{
		this.calctype = calctype;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getLinkfield()
	{
		return linkfield;
	}

	public void setLinkfield(String linkfield)
	{
		this.linkfield = linkfield;
	}

	public String getFieldkey()
	{
		return fieldkey;
	}

	public void setFieldkey(String fieldkey)
	{
		this.fieldkey = fieldkey;
	}

	public Short getPkey()
	{
		return pkey;
	}

	public void setPkey(Short pkey)
	{
		this.pkey = pkey;
	}

	public String getRequired()
	{
		return required;
	}

	public void setRequired(String required)
	{
		this.required = required;
	}

	public String getDstype()
	{
		return dstype;
	}

	public void setDstype(String dstype)
	{
		this.dstype = dstype;
	}

	public String getDefvalue()
	{
		return defvalue;
	}

	public void setDefvalue(String defvalue)
	{
		this.defvalue = defvalue;
	}

	public String getTrattr()
	{
		return trattr;
	}

	public void setTrattr(String trattr)
	{
		this.trattr = trattr;
	}

	public String getReadonly()
	{
		return readonly;
	}

	public void setReadonly(String readonly)
	{
		this.readonly = readonly;
	}

	public String getIssum()
	{
		return issum;
	}

	public void setIssum(String issum)
	{
		this.issum = issum;
	}

	public String getHelp()
	{
		return help;
	}

	public void setHelp(String help)
	{
		this.help = help;
	}

	public String getUserflag()
	{
		return userflag;
	}

	public void setUserflag(String userflag)
	{
		this.userflag = userflag;
	}

	public String getHtmlfield()
	{
		return htmlfield;
	}

	public void setHtmlfield(String htmlfield)
	{
		this.htmlfield = htmlfield;
	}

	public Short getTcols()
	{
		return tcols;
	}

	public void setTcols(Short tcols)
	{
		this.tcols = tcols;
	}

	public Short getTrows()
	{
		return trows;
	}

	public void setTrows(Short trows)
	{
		this.trows = trows;
	}

	public Short getValuewidth()
	{
		return valuewidth;
	}

	public void setValuewidth(Short valuewidth)
	{
		this.valuewidth = valuewidth;
	}

}
