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
<#if vo.macro!="">
<#include "${vo.macro}">
</#if>
</body>
</html>