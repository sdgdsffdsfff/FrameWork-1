<#--  
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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>运维资源管理系统</title>
<link rel="shortcut icon" href="favicon.ico" />
<script type="text/javascript" src="${base}/themes/default/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="${base}/themes/default/gex.js"></script>
<script type="text/javascript" src="${base}/themes/default/main.js"></script>
<script type="text/javascript" src="${base}/resource/default/script/public_complete.js"></script>
<style type="text/css">@import url(${base}/themes/default/main.css);</style>
</head>

<body>
<script type="text/javascript">
function page_config()
{
	openwin("${base}/module/app/system/query/search/search_locateframe.action?_searchname=search.locate&searchid=${vo.searchid}", pub_width_large, pub_height_large);
}
</script>
<div id="pageContainer">
<h1>${vo.title}</h1>
<table class="viewGrid">
<#assign vnum = 0>
<#list vo.searchoptions as option>
<#if option.edittype != "hidden">
<#assign vnum = vnum + 1>
	<#if (vnum % 2) == 1>
	<tr>
	</#if>
	<#assign value = "aobj.${option.field}"?eval>
	<#if option.edittype = "textarea">
		<td class="r"><label for="">${option.title}：</label></td>
		<td colspan="3">${value}</td>
		<#assign vnum = vnum + 1>
	<#else>
		<td class="r"><label for="">${option.title}：</label></td>
		<td>${value}</td>
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

<#if vo.divbutton == "Y">
	<div class="op">
	<#if Session["SPRING_SECURITY_CONTEXT"]??>
	<#if Session["SPRING_SECURITY_CONTEXT"].authentication.principal.username=="admin">
	<button class="btn2" onclick="page_config()">配置信息</button>
	</#if>
	</#if>
		<@pub_macros.displayurl vo = vo></@pub_macros.displayurl>
	</div>
</#if>

<#if vo.macro!="">
<#include "${vo.macro}">
</#if>
</form>
</div>
</body>
</html>