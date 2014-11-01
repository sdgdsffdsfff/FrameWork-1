<!DOCTYPE HTML>
<html>
<head>
<#include "/decorator/include/include.ftl">
<script type="text/javascript" src="${base}/wfb/blueflowui/javascript/common.js"></script>
<SCRIPT type="text/javascript">
 var fs=0;
function getParent(el, pTagName) 
{ 
    // getParent object	

	while (el && (!el.tagName || el.tagName.toLowerCase() != pTagName.toLowerCase()) )
		{
			el = el.parentNode;
		}
		 
	return el;
}

function OpenFlowForm() {
    clsid=window.parent.document.all.classid.value;
    if((clsid=="")||(clsid=="null")) 
    {
    alert("请先选择流程类别！");
	    return;
    }
    clsid=window.parent.document.all.classid.value; 
	var el = getParent(window.event.srcElement,"button");
	var flowclass = window.showModalDialog("${base}/module/app/system/workflow/bflow/bflow_selectbform.action?_searchname=workflow.bflow.selectbform&classid="+clsid,el,"status:off;help:0;dialogWidth:"+pub_width_small+"px;dialogHeight:"+pub_height_small+"px;edge:sunken; ");
    if(typeof(flowclass)!="undefined") {
    document.all.formid.value=flowclass.id;
    document.all.formname.value=flowclass.name;
    document.all.createformid.value=flowclass.id;
    document.all.createformname.value=flowclass.name;
    window.parent.document.all.formid.value=flowclass.id;
    window.parent.document.all.formname.value=flowclass.name;
    window.parent.document.all.createformid.value=flowclass.id;
    window.parent.document.all.createformname.value=flowclass.name;
    
    window.parent.document.all.field.value=flowclass.field;
    
    
    }
}
function OpenFlowClass(oDestination,oDestination1) {
    oField1 = oDestination;
    oField2 = oDestination1; 
    wfclass = window.open("<%=PathToolKit.getRootPath(request)%>/WFBFlowClassForm?ACTION=SelectBFlowClass", "wfclass", "resizable=yes,dependent=yes,scrollbars=yes,left=90,top=45,width=" + screen.width * .7 + ",height=" + screen.height * .5);    
    wfclass.focus();
}

</SCRIPT>
</head>
<body>
<form id="flowForm">
<table>
 
	<tr>
		<td align="right" valign="top">表单名称：</td>
		<td valign="top" align="left"><input type="hidden" name="formid" value="${(data.bform.id)!}" /><input class="text" name="formname" value="${(data.bform.cname)!}" style="width:20em;" readonly/><BUTTON onclick='window.fs=0;OpenFlowForm(); '>选择</BUTTON></td>
	</tr>
	<tr>
		<td align="right" valign="top"></td>
		<td valign="top" align="left"><input type="hidden" name="createformid" value="" /><input type="hidden" name="createformname" value="" readonly="readonly" /><!--BUTTON   onclick='window.fs=1;OpenFlowForm("createformid","createformname")'>选择</BUTTON--></td>
	</tr>

 
 </table>
 
</form>
<SCRIPT type="text/javascript">
try{
document.forms[0].formid.value=window.parent.document.all.formid.value;
document.forms[0].formname.value=window.parent.document.all.formname.value;

document.forms[0].createformid.value=window.parent.document.all.createformid.value;
document.forms[0].createformname.value=window.parent.document.all.createformname.value;
}catch(Exception){}

</SCRIPT>
</body>

</html>

