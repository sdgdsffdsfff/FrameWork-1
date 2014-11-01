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
<script language="javascript" src="${base}/resource/default/script/public.js"></script>
<script language="javascript" src="${base}/resource/default/script/public_query.js"></script>
<script language="javascript" src="${base}/resource/default/script/public_validator.js"></script>
<script type="text/javascript" src="${base}/resource/default/script/public_skynetcomplete.js"></script>
</head>
<body>
<script>
function page_config()
{
	pub_openunresizablewindow("${base}/module/app/system/query/search/invoke!forward.action?_searchname=search.locate&_forward=locateframe-success&_argnames=searchid&searchid=${vo.searchid}", pub_width_large_5v3, pub_height_large_5v3);
}
</script>
<#import "page_macros.ftl" as pub_macros>
<div id="navigation"><span id="op"></span><strong>当前位置：</strong>${vo.title}</div>
<div id="buttondiv">
<#if Session["sys_login_token"].sys_login_user?upper_case == "ADMIN">
<button onclick="page_config()">配置信息</button>
</#if>
<@pub_macros.displayurl vo = vo></@pub_macros.displayurl>
</div>

<#if vo.macro!="">
<#include "${vo.macro}">
</#if>

</body>
</html>