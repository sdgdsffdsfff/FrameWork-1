<!DOCTYPE HTML>
<html>
<head>
<#include "/decorator/include/include.ftl">
<script type="text/javascript" src="${base}/themes/default/jquery.limit-1.2.source.js"></script>
<style>
textarea {	}
.textLimit {position:relative;background:red;}
.textLimit .numDiv {position:absolute;right:0;margin-right:20px;top:-30px;}
.textLimit .numDiv span {display:inline;white-space:nowrap;font-size:18px;color:#666;font-size:700;font-style:italic}
</style>
<script type="text/javascript" src="${base}/wfb/blueflowui/javascript/common.js"></script>
<SCRIPT type="text/javascript">
$(function(){
/////////////////////////////////////////

$('textarea').each(function(){
	if(!isNaN($(this).attr('maxlength'))){
		$(this).after('<span class="textLimit" title="字数限制为：'+$(this).attr('maxlength')+'"><div class="numDiv"><span></span><span class="num"></span></div></span>');
		var textLeft=$(this).parent().find('.textLimit span.num')
		$(this).limit($(this).attr('maxlength'),textLeft);
		//textLeft.after('个字符').before('还剩')
	}
})

/////////////////////////////////////////
})
 var fs=0;

function OpenFlowForm(oDestination,oDestination1) {
    oField1 = oDestination;
    oField2 = oDestination1; 
    wffclass = window.open("<%=PathToolKit.getRootPath(request)%>/WFBFormForm?ACTION=SelectBForm", "wffclass", "resizable=yes,dependent=yes,scrollbars=yes,left=90,top=45,width=" + screen.width * .7 + ",height=" + screen.height * .5);    
    
    wffclass.focus();
}
function OpenFlowClass(oDestination,oDestination1) {
    oField1 = oDestination;
    oField2 = oDestination1; 
    wfclass = window.open("<%=PathToolKit.getRootPath(request)%>/WFBFlowClassForm?ACTION=SelectBFlowClass", "wfclass", "resizable=yes,dependent=yes,scrollbars=yes,left=90,top=45,width=" + screen.width * .7 + ",height=" + screen.height * .5);    
    wfclass.focus();
}

</script>
</head>
<body style="margin:0;padding:0;">
<div style="padding-top:10px;"></div>
<form id="flowForm"> 
<table width="100%"> 
	<tr>
		<td align="right" valign="top" width="1%" nowrap>类型：</td>
		<td valign="top" align="left">
			<#assign objs = stack.findValue("@com.ray.app.query.function.view.SQLSelect@get_data(\"select new map(t.dtext as ctext,t.dvalue as cvalue) from Dictionary t where t.dkey='app.workflow.route.routetype'\")") >
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
			<span class="selectSpan" id="sel_routetype">
			<input type="hidden" id="h_routetype" name="routetype" value="${(data.broute.routetype)!}" >
			<input id="op_routetype" class="select" data-options="<#if finaltext!="">${finaltext}</#if>" data-values="<#if finalvalue!="">${finalvalue}</#if>" data-default="${(data.broute.routetype)!}">
			</span>
		</td>
	</tr>
	<tr>
		<td align="right" valign="top">条件：</td>
		<td valign="top" align="left">
		<TEXTAREA cols="80" rows="10" maxlength="500" name="conditions">${(data.broute.conditions)!}</TEXTAREA>
		 
		 </td>
	</tr>
	<tr>
		<td align="right" valign="top">方向：</td>
		<td valign="top" align="left">
			<#assign objs = stack.findValue("@com.ray.app.query.function.view.SQLSelect@get_data(\"select new map(t.dtext as ctext,t.dvalue as cvalue) from Dictionary t where t.dkey='app.workflow.route.direct'\")") >
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
			<span class="selectSpan" id="sel_direct">
			<input type="hidden" id="h_direct" name="direct" value="${(data.broute.direct)!}" >
			<input id="op_direct" class="select" data-options="<#if finaltext!="">${finaltext}</#if>" data-values="<#if finalvalue!="">${finalvalue}</#if>" data-default="${(data.broute.direct)!}">
			</span>
		</td>
	</tr>

 
 </table>
 
</form>
<SCRIPT type="text/javascript">
//=======根据action中传递过来的对象取值====================
$('#h_routetype').val(window.parent.document.all.routetype.value);
$('#op_routetype').attr('data-default',window.parent.document.all.routetype.value);
document.forms[0].conditions.value=window.parent.document.all.conditions.value;

$('#h_direct').val(window.parent.document.all.direct.value);
$('#op_direct').attr('data-default',window.parent.document.all.direct.value); 

</SCRIPT>
</body>

</html>

