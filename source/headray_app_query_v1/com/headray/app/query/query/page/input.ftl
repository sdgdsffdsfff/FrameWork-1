<@compress>
<#if Session["sys_style"]?exists>
<#assign sys_style = Session["sys_style"]>
<#else>
<#assign sys_style = "default">
</#if>
<#assign vo = data.vo>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vo.title}</title>
<link rel="stylesheet" href="${base}/resource/${sys_style}/css/main.css" type="text/css">
<script language="javascript" src="${base}/resource/default/script/jquery.js"></script>
<script language="javascript" src="${base}/resource/default/script/jquery.bgiframe.js"></script>
<script language="javascript" src="${base}/resource/default/script/public.js"></script>
<script language="javascript" src="${base}/resource/default/script/public_query.js"></script>
<script language="javascript" src="${base}/resource/default/script/public_validator.js"></script>
<script language="javascript" src="${base}/resource/default/script/public_skynetcomplete.js"></script>
</head>
<body>
<script>
function page_config()
{
	pub_openunresizablewindow("${base}/module/app/system/query/search/invoke!forward.action?_searchname=search.locate&_forward=locateframe-success&_argnames=searchid&searchid=${vo.searchid}", pub_width_large_5v3, pub_height_large_5v3);
}
</script>
<#import "page_macros.ftl" as pub_macros>
<#if vo.divnav == "Y">
<div id="navigation"><span id="op"></span><strong>当前位置：</strong>${vo.title}</div>
</#if>
<br>
<#if vo.divbutton == "Y">
<div id="buttondiv">
<#if Session["sys_login_token"].sys_login_user?upper_case == "ADMIN">
<button onclick="page_config()">配置信息</button>
</#if>
<@pub_macros.displayurl vo = vo></@pub_macros.displayurl>
</div>
</#if>

<form id="form" name="form" method="post" <#if vo.formaction!="">action="${base}/${vo.formaction}"</#if>>
<input type="hidden" id="_searchname" name="_searchname" value="">

<#list vo.searchoptions as option>
<#if option.edittype == "hidden">
<#if option.dstype == "request">
<input type="hidden" id="${option.field}" name="${option.field}" value="${arg[option.field]}">
<#else>
<input type="hidden" id="${option.field}" name="${option.field}" value="${option.defvalue}">
</#if>
</#if>
</#list>

<table class="formtable">

<#assign vnum = 0>
<#list vo.searchoptions as option>
<#if option.edittype != "hidden">
<#assign vnum = vnum + 1>


<#if (vnum % 2) == 1>
<tr>
</#if>

	<th width="15%" align="right"><#if option.required!="X"><span class="atten">*&nbsp;</span></#if><label id="lb_${option.field}" for="${option.field}">${option.title}：</th>
	<td width="35%" align="left">
	<@pub_macros.displayafield option = option value="" /></td>

<#if (vnum % 2) == 1 && (option_index==vo.searchoptions?size-1)>
<th width="15%">&nbsp;</th>
<td width="35%">&nbsp;</td>
</tr>			
</#if> 

<#if (vnum % 2) == 0 && vnum &gt; 0>
</tr>			
</#if> 

</#if>
</#list>

</table>
</form>

<div id="fulllengthdiv" class="fullwidth" style="display:none">
</div>

</@compress>

<#if vo.macro!="">
<#include "${vo.macro}">
</#if>
</body>
</html>
