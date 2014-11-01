<#--  

日期：2011/03/16
编辑：蒲剑
说明：IT运维界面模板。 
状态：

日期：2012/04/16
编辑：闫长永
任务：重构，按项目界面设计建立模板页面。 
状态：
-->

<#assign vo = data.vo>
<#assign aobj = data.aobj>
<#import "com/ray/app/query/view/page_macros.ftl" as pub_macros>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>运维资源管理系统</title>
<link rel="shortcut icon" href="favicon.ico">
<script type="text/javascript" src="${base}/themes/default/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${base}/themes/default/gex.js"></script>
<script type="text/javascript" src="${base}/themes/default/main.js"></script>
<script type="text/javascript" src="${base}/resource/default/script/public_complete.js"></script>
<style type="text/css">@import url(${base}/themes/default/main.css);</style>
</head>
<body>
<script type="text/javascript">
function page_config()
{
	openwin("${base}/module/app/system/query/search/search_locateframe.action?_searchname=search.locate&searchid=${vo.searchid}", pub_width_small, pub_height_small);
}
</script>
<#if vo.divbutton == "Y">
	<div id="fixedOp">
	<#if (Session.sys_login_token)??>
	<#if Session.sys_login_token.sys_login_user=="admin">
		<button onclick="page_config()">配置信息</button>
	</#if>
	</#if>
	<@pub_macros.displayurl vo = vo></@pub_macros.displayurl>
	</div>
	</#if>
<h1>${vo.title}</h1>
<div id="pageContainer">

<form id="inputform" method="post" action="${base}/${vo.formaction}">
<input type="hidden" id="_searchname" name="_searchname" value="">
<#list vo.searchoptions as option>
<#if option.edittype == "hidden">
<input type="hidden" id="${option.htmlfield}" name="${option.htmlfield}" value="${arg[option.field]}">
</#if>
</#list>
<table class="formGrid">

<#assign vnum = 0>
<#list vo.searchoptions as option>
<#if option.edittype != "hidden">
<#assign vnum = vnum + 1>
<#if (vnum % 2) == 1>
<tr>
</#if>
<#if option.edittype = "textarea">
	<td class="r"><label for="${option.htmlfield}">${option.title}：</label></td>
	<td colspan="3"><@pub_macros.displayafield option = option value = "" /></td>
	<#assign vnum = vnum + 1>
<#else>
	<td class="r"><label for="${option.htmlfield}">${option.title}：</label></td>
	<td><@pub_macros.displayafield option = option value = "" /></td>
</#if>
<#if (vnum % 2) == 1 && (option_index==vo.searchoptions?size-1)>
<td class="r">&nbsp;</td>
<td>&nbsp;</td>
</tr>			
</#if> 

<#if (vnum % 2) == 0 && vnum &gt; 0>
</tr>			
</#if> 
</#if>
</#list>		

</table>

<#if vo.macro!="">
<#include "${vo.macro}">
</#if>
</form>
</div>
</body>
</html>