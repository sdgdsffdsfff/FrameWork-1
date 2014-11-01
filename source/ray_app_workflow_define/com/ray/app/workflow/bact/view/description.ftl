<!DOCTYPE HTML>
<html>
<head>
<#include "/decorator/include/include.ftl">
<script   type="text/javascript" src="${base}/wfb/blueflowui/javascript/common.js"></script>
<SCRIPT type="text/javascript">
$(function() {
/////////////////

if('SUBFLOW' != window.parent.document.all.ctype.value)
{
	$('#lb_subflow').hide();
}


$('#sel_ctype').bind('gchange',function()
{
	if(($(this).find('input:hidden').val())=='SUBFLOW')
	{
		$('#lb_subflow').show();
	}
	else
	{
		$('#lb_subflow').hide();
	}
})

$('#sel_handletype').bind('gchange',function()
{
	var handletype=$('#acthandletype').val();
	modechg(handletype);
})
	
///////////////
})
 var fs=0;

function OpenFlowForm(oDestination,oDestination1) {

    oField1 = oDestination;
    oField2 = oDestination1; 
    wffclass = window.open("${base}/module/app/system/workflow/bform/bform_selectbform.action?classid="+clsid, "wffclass", "resizable=yes,dependent=yes,scrollbars=yes,left=90,top=45,width=" + screen.width * .7 + ",height=" + screen.height * .5);    
    
    wffclass.focus();
}
function OpenFlowClass(oDestination,oDestination1) {
    oField1 = oDestination;
    oField2 = oDestination1; 
    wfclass = window.open("${base}/module/app/system/workflow/bflowclass/bflowclass_selectbflowclass.action", "wfclass", "resizable=yes,dependent=yes,scrollbars=yes,left=90,top=45,width=" + screen.width * .7 + ",height=" + screen.height * .5);    
    wfclass.focus();
}

function modechg(vlue)
{

if((vlue=='普通') ||(vlue=='多部门串行'))
{
	document.all.selmode.style.display='';
	
	//window.parent.document.all.ctype1.innerText="普通"; 
	//window.parent.document.all.ctype.innerText="NORMAL"; 
}
else
{
	document.all.selmode.style.display='none'; 
}

if((vlue=='指定专人')) 
{
    // 只允许单人
	$('#selectstyle').val('单人');
	
	document.all.switmode.style.display='';
	//window.parent.document.all.ctype1.innerText="普通"; 
	//window.parent.document.all.ctype.innerText="NORMAL"; 
	window.parent.hdltype=vlue;
}
else document.all.switmode.style.display='none';  

if((vlue=='多人串行')|| (vlue=='多人并行'))
{
 
//window.parent.document.all.ctype1.innerText="普通"; 
//window.parent.document.all.ctype.innerText="NORMAL"; 

}

}

function page_selectsubflow()
{
	url="${base}/module/app/system/workflow/bact/bact_selectsubflow.action?_searchname=workflow.bact.selectsubflow";
	openwin(url,'selectsubflow',pub_width_mid,pub_height_mid);
}

function page_opensubflow()
{
	var sno=$('#subflowsno').val();
	//url="${base}/module/app/system/workflow/define/define_main.action?flowid="+flowid;
	url="${base}/module/app/system/workflow/bact/bact_opensubflow.action?sno="+sno;
	openwin(url,'opensubflow',pub_width_mid,pub_height_mid);
}
</SCRIPT>
</head>
<body><div id="gContainer">
<form id="flowForm">
<table class="formGrid">
<tr>
	<td align="right" valign="top" width="1%">活动类型：</td>
	<td align="left" valign="top">
		<#assign ctypes = stack.findValue("@com.ray.app.query.function.view.SQLSelect@get_data(\"select new map(t.dtext as ctext,t.dvalue as cvalue) from Dictionary t where t.dkey='app.workflow.act.ctype'\")") >
		<#assign finalvalue = "">
		<#assign finaltext = "">
		<#list ctypes as ctype>
		<#if finalvalue =="">
		<#assign finalvalue = ctype.cvalue>
		<#assign finaltext = ctype.ctext>
		<#else>
		<#assign finalvalue = finalvalue + "||" + ctype.cvalue>
		<#assign finaltext = finaltext + "||" + ctype.ctext>
		</#if>
		</#list>
		<span class="selectSpan" id="sel_ctype">
		<input type="hidden" id="h_ctype" name="ctype" value="${(data.bact.ctype)!}" >
		<input id="actctype" class="select" data-options="<#if finaltext!="">${finaltext}</#if>" data-values="<#if finalvalue!="">${finalvalue}</#if>" data-default="${(data.bact.ctype)!}">
		</span>
	</td>
</tr>
<tbody id="lb_subflow">
<tr>
	<td align="right" valign="top">子流程名称：</td>
	<td align="left" valign="top">
		<input type="hidden" id="subflowid" name="subflowid" value="${(data.bact.subflowid)!}" />
		<input type="hidden" id="subflowsno" name="subflowsno" value="${(data.bact.subflowsno)!}" />
		<a href="javascript:void(0)" onclick="page_opensubflow()"><input type="text" id="subflowname" class="linktext" name="subflowname" value="${(data.subflow.cname)!}" readonly/></a>
		<BUTTON onclick='page_selectsubflow()'>选择</BUTTON>
	</td>
</tr>
<tr>
	<td align="right" valign="top">子流程创建模式：</td>
	<td align="left" valign="top">
		<#assign objs = stack.findValue("@com.ray.app.query.function.view.SQLSelect@get_data(\"select new map(t.dtext as ctext,t.dvalue as cvalue) from Dictionary t where t.dkey='app.workflow.act.subflowcreate'\")") >
		<#assign finalvalue = "">
		<#assign finaltext = "">
		<#list objs as obj>
		<#if finalvalue =="">
		<#assign finalvalue = obj.cvalue>
		<#assign finaltext = obj.ctext>
		<#else>
		<#assign finalvalue = finalvalue + "||" + obj.cvalue>
		<#assign finaltext = finaltext + "||" + obj.ctext>
		</#if>
		</#list>
		<span class="selectSpan" id="sel_subflowcreate">
		<input type="hidden" id="h_subflowcreate" name="subflowcreate" value="${(data.bact.subflowcreate)!}" >
		<input id="actsubflowcreate" class="select" data-options="<#if finaltext!="">${finaltext}</#if>" data-values="<#if finalvalue!="">${finalvalue}</#if>" data-default="${(data.bact.subflowcreate)!}">
		</span>
	</td>
</tr>
<tr>
	<td align="right" valign="top">子流程关闭模式：</td>
	<td align="left" valign="top">
		<#assign objs = stack.findValue("@com.ray.app.query.function.view.SQLSelect@get_data(\"select new map(t.dtext as ctext,t.dvalue as cvalue) from Dictionary t where t.dkey='app.workflow.act.subflowclose'\")") >
		<#assign finalvalue = "">
		<#assign finaltext = "">
		<#list objs as obj>
		<#if finalvalue =="">
		<#assign finalvalue = obj.cvalue>
		<#assign finaltext = obj.ctext>
		<#else>
		<#assign finalvalue = finalvalue + "||" + obj.cvalue>
		<#assign finaltext = finaltext + "||" + obj.ctext>
		</#if>
		</#list>
		<span class="selectSpan" id="sel_subflowclose">
		<input type="hidden" id="h_subflowclose" name="subflowclose" value="${(data.bact.subflowclose)!}" >
		<input id="actsubflowclose" class="select" data-options="<#if finaltext!="">${finaltext}</#if>" data-values="<#if finalvalue!="">${finalvalue}</#if>" data-default="${(data.bact.subflowclose)!}">
		</span>
	</td>
</tr>
<tr>
	<td align="right" valign="top">子流程关联模式：</td>
	<td align="left" valign="top">
		<#assign objs = stack.findValue("@com.ray.app.query.function.view.SQLSelect@get_data(\"select new map(t.dtext as ctext,t.dvalue as cvalue) from Dictionary t where t.dkey='app.workflow.act.subflowlink'\")") >
		<#assign finalvalue = "">
		<#assign finaltext = "">
		<#list objs as obj>
		<#if finalvalue =="">
		<#assign finalvalue = obj.cvalue>
		<#assign finaltext = obj.ctext>
		<#else>
		<#assign finalvalue = finalvalue + "||" + obj.cvalue>
		<#assign finaltext = finaltext + "||" + obj.ctext>
		</#if>
		</#list>
		<span class="selectSpan" id="sel_subflowlink">
		<input type="hidden" id="h_subflowlink" name="subflowlink" value="${(data.bact.subflowlink)!}" >
		<input id="actsubflowlink" class="select" data-options="<#if finaltext!="">${finaltext}</#if>" data-values="<#if finalvalue!="">${finalvalue}</#if>" data-default="${(data.bact.subflowlink)!}">
		</span>
	</td>
</tr>
	
</tbody>

<tr>
	<td align="right" valign="top" width="1%">处理模式：</td>
	<td align="left" valign="top">
		<#assign objs = stack.findValue("@com.ray.app.query.function.view.SQLSelect@get_data(\"select new map(t.dtext as ctext,t.dvalue as cvalue) from Dictionary t where t.dkey='app.workflow.act.handletype'\")") >
		<#assign finalvalue = "">
		<#assign finaltext = "">
		<#list objs as obj>
		<#if finalvalue =="">
		<#assign finalvalue = obj.cvalue>
		<#assign finaltext = obj.ctext>
		<#else>
		<#assign finalvalue = finalvalue + "||" + obj.cvalue>
		<#assign finaltext = finaltext + "||" + obj.ctext>
		</#if>
		</#list>
		<span class="selectSpan" id="sel_handletype">
		<input type="hidden" id="h_handletype" name="handletype" value="${(data.bact.handletype)!}" >
		<input id="acthandletype" class="select" data-options="<#if finaltext!="">${finaltext}</#if>" data-values="<#if finalvalue!="">${finalvalue}</#if>" data-default="${(data.bact.handletype)!}" >
		</span>
	</td>
</tr>
<tr>
	<td align="right" valign="top">转入模式：</td>
	<td align="left" valign="top">
		<#assign objs = stack.findValue("@com.ray.app.query.function.view.SQLSelect@get_data(\"select new map(t.dtext as ctext,t.dvalue as cvalue) from Dictionary t where t.dkey='app.workflow.act.join'\")") >
		<#assign finalvalue = "">
		<#assign finaltext = "">
		<#list objs as obj>
		<#if finalvalue =="">
		<#assign finalvalue = obj.cvalue>
		<#assign finaltext = obj.ctext>
		<#else>
		<#assign finalvalue = finalvalue + "||" + obj.cvalue>
		<#assign finaltext = finaltext + "||" + obj.ctext>
		</#if>
		</#list>
		<span class="selectSpan" id="sel_join">
		<input type="hidden" id="h_join" name="join" value="${(data.bact.join)!}" >
		<input id="actjoin" class="select" data-options="<#if finaltext!="">${finaltext}</#if>" data-values="<#if finalvalue!="">${finalvalue}</#if>" data-default="${(data.bact.join)!}">
		</span>
	</td>
</tr>
<tr>
	<td align="right" valign="top">转出模式：</td>
	<td align="left" valign="top">
		<#assign objs = stack.findValue("@com.ray.app.query.function.view.SQLSelect@get_data(\"select new map(t.dtext as ctext,t.dvalue as cvalue) from Dictionary t where t.dkey='app.workflow.act.split'\")") >
		<#assign finalvalue = "">
		<#assign finaltext = "">
		<#list objs as obj>
		<#if finalvalue =="">
		<#assign finalvalue = obj.cvalue>
		<#assign finaltext = obj.ctext>
		<#else>
		<#assign finalvalue = finalvalue + "||" + obj.cvalue>
		<#assign finaltext = finaltext + "||" + obj.ctext>
		</#if>
		</#list>
		<span class="selectSpan" id="sel_split">
		<input type="hidden" id="h_split1" name="split1" value="${(data.bact.split)!}" >
		<input id="actsplit1" class="select" data-options="<#if finaltext!="">${finaltext}</#if>" data-values="<#if finalvalue!="">${finalvalue}</#if>" data-default="${(data.bact.split)!}">
		</span>
	</td>
</tr>
<tr style="display: none;">
	<td align="right" valign="top">是否启动活动：</td>
	<td align="left" valign="top">
		<span class="allRadios">
			<input type="hidden" name="isfirst" value="${(data.bact.isfirst)!}"/>
			<#assign objs = stack.findValue("@com.ray.app.query.function.view.SQLSelect@get_data(\"select new map(t.dtext as ctext,t.dvalue as cvalue) from Dictionary t where t.dkey='system.yorn' \")") >
			<#list objs as obj>
			<label><input class="radio" value="${obj.cvalue}">${obj.ctext}</label>
			</#list>
		</span>		
	</td>
</tr>
<tr id="switmode">
	<td align="right" valign="top">转出方式：</td>
	<td align="left" valign="top"> 
		<#assign objs = stack.findValue("@com.ray.app.query.function.view.SQLSelect@get_data(\"select new map(t.dtext as ctext,t.dvalue as cvalue) from Dictionary t where t.dkey='app.workflow.act.outstyle'\")") >
		<#assign finalvalue = "">
		<#assign finaltext = "">
		<#list objs as obj>
		<#if finalvalue =="">
		<#assign finalvalue = obj.cvalue>
		<#assign finaltext = obj.ctext>
		<#else>
		<#assign finalvalue = finalvalue + "||" + obj.cvalue>
		<#assign finaltext = finaltext + "||" + obj.ctext>
		</#if>
		</#list>
		<span class="selectSpan" id="sel_outstyle">
		<input type="hidden" id="h_outstyle" name="outstyle" value="${(data.bact.outstyle)!}" >
		<input id="actoutstyle" class="select" data-options="<#if finaltext!="">${finaltext}</#if>" data-values="<#if finalvalue!="">${finalvalue}</#if>" data-default="${(data.bact.outstyle)!}">
		</span>
	</td>
</tr>
 
<tr id="selmode"> 
	<td align="right" valign="top">人员选择方式：</td>
	<td align="left" valign="top">
		<span class="allRadios">
			<input type="hidden" name="selectstyle" value="${(data.bact.selectstyle)!}"/>
			<#assign objs = stack.findValue("@com.ray.app.query.function.view.SQLSelect@get_data(\"select new map(t.dtext as ctext,t.dvalue as cvalue) from Dictionary t where t.dkey='app.workflow.act.selectstyle' \")") >
			<#list objs as obj>
			<label><input class="radio" value="${obj.cvalue}">${obj.ctext}</label>
			</#list>
		</span>
	 </td>
	  
</tr>  
<tr>
	<td align="right" valign="top">选择其他人：</td>
	<td align="left" valign="top">
		<span class="allRadios">
			<input type="hidden" name="selectother" value="${(data.bact.selectother)!}"/>
			<#assign objs = stack.findValue("@com.ray.app.query.function.view.SQLSelect@get_data(\"select new map(t.dtext as ctext,t.dvalue as cvalue) from Dictionary t where t.dkey='system.yorn' \")") >
			<#list objs as obj>
			<label><input class="radio" value="${obj.cvalue}">${obj.ctext}</label>
			</#list>
		</span>
	</td>
</tr>
 </table>
 
</form>
<SCRIPT type="text/javascript">
function setRadioValue(radioel,s)
{ 
	for(var i=0; i<radioel.length; i++)
	{
		if(radioel[i].value == s)
		{
			radioel[i].checked = 1;
			return;
		}
	}
}

$('#h_ctype').val(window.parent.document.all.ctype.value);
$('#actctype').attr('data-default',window.parent.document.all.ctype.value);
$('#h_subflowcreate').val(window.parent.document.all.subflowcreate.value);
$('#actsubflowcreate').attr('data-default',window.parent.document.all.subflowcreate.value);
$('#h_subflowclose').val(window.parent.document.all.subflowclose.value);
$('#actsubflowclose').attr('data-default',window.parent.document.all.subflowclose.value);
$('#h_subflowlink').val(window.parent.document.all.subflowlink.value);
$('#actsubflowlink').attr('data-default',window.parent.document.all.subflowlink.value);
$('#h_handletype').val(window.parent.document.all.handletype.value);
$('#acthandletype').attr('data-default',window.parent.document.all.handletype.value);
$('#h_join').val(window.parent.document.all.join.value);
$('#actjoin').attr('data-default',window.parent.document.all.join.value);
$('#h_split1').val(window.parent.document.all.split1.value);
$('#actsplit1').attr('data-default',window.parent.document.all.split1.value);
$('#h_outstyle').val(window.parent.document.all.outstyle.value);
$('#actoutstyle').attr('data-default',window.parent.document.all.outstyle.value);

document.forms[0].subflowid.value=window.parent.document.all.subflowid.value;
document.forms[0].subflowsno.value=window.parent.document.all.subflowsno.value;
document.forms[0].subflowname.value=window.parent.document.all.subflowname.value;
//document.forms[0].subflowcreate.value=window.parent.document.all.subflowcreate.value;
//document.forms[0].subflowclose.value=window.parent.document.all.subflowclose.value;
//document.forms[0].subflowlink.value=window.parent.document.all.subflowlink.value;
//document.forms[0].handletype.value=window.parent.document.all.handletype.value;
//document.forms[0].join.value=window.parent.document.all.join.value;
//document.forms[0].split1.value=window.parent.document.all.split1.value;
//document.forms[0].outstyle.value=window.parent.document.all.outstyle.value;
if(document.forms[0].outstyle.value=="") document.forms[0].outstyle.value="EVERYTIME"; 
document.forms[0].isfirst.value="";
document.forms[0].selectstyle.value="";
document.forms[0].selectother.value="";
document.forms[0].isfirst.value=window.parent.document.all.isfirst.value;
document.forms[0].selectstyle.value=window.parent.document.all.selectstyle.value;
document.forms[0].selectother.value=window.parent.document.all.selectother.value;

modechg($('#h_handletype').val()); 

</SCRIPT>
</div></body>

</html>

