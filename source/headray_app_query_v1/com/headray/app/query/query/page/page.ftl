<#if Session["sys_style"]?exists>
<#assign sys_style = Session["sys_style"]>
<#else>
<#assign sys_style = "default">
</#if>

<#assign mo = data.mo>
<#assign vo = data.vo>
<#assign apage = data.apage>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${vo.title}</title>
<link rel="stylesheet" href="${base}/resource/${sys_style}/css/main.css" type="text/css">
<script language="javascript" src="${base}/resource/default/script/jquery.js"></script>
<script language="javascript" src="${base}/resource/default/script/public.js"></script>
<script language="javascript" src="${base}/resource/default/script/public_validator.js"></script>
<script language="javascript" src="${base}/resource/default/script/public_query.js"></script>
</head>
<body>
<script>
function page_config()
{
	pub_openunresizablewindow("${base}/module/app/system/query/search/invoke!forward.action?_searchname=search.locate&_forward=locateframe-success&_argnames=searchid&searchid=${vo.searchid}", pub_width_large_5v3, pub_height_large_5v3);
}
</script>

<#import "page_macros.ftl" as pub_macros>

<div id="main_" name="main_">

<#if vo.divnav == "Y">
<div id="navigation"><span id="op"></span><strong>当前位置：</strong>${vo.title}</div>
</#if>
<br>

<form method="post" name="form_view" id="form_view" action="${base}${vo.formaction}">
<input type="hidden" name="_searchname" value="${arg._searchname}">
<input type="hidden" name="_currentpage" value="${arg._currentpage}">
<input type="hidden" name="_sortfield" value="${arg._sortfield}" />
<input type="hidden" name="_sorttag" value="${arg._sorttag}" />
<input type="hidden" name="_pagesize" value="${arg._pagesize}" />

<#if vo.divbutton == "Y">
<div id="buttondiv">
<#if Session["sys_login_token"].sys_login_user?upper_case == "ADMIN">
<button onclick="page_config()">配置信息</button>
</#if>
<@pub_macros.displayurl vo = vo></@pub_macros.displayurl>
<@pub_macros.displaysfield vo = vo arg = arg></@pub_macros.displaysfield>
</div>
</#if>

<div id="div_queryinput" style="display:none">
<@pub_macros.displaysearchitem vo = vo arg = arg></@pub_macros.displaysearchitem>
</div>

<!--列表区-->
<table id="listtable" width="100%" border="0" cellpadding="0" cellspacing="1">

<!-- 显示标题-->
<@pub_macros.displayheader vo = vo arg = arg></@pub_macros.displayheader>

<!-- 显示数据-->
<@pub_macros.displaylist apage = apage vo = vo></@pub_macros.displaylist>
</table>

<@pub_macros.displaynavigation apage = apage vo = vo arg = arg></@pub_macros.displaynavigation>

</form>

<#--
<form method="post" name="inputform" id="inputform">
</form>
-->

</div>

<form id="dform">
</form>

<#if vo.macro!="">
<#include "${vo.macro}">
</#if>

</body>
</html>