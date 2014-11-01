<#--  

日期：2011/03/16
编辑：蒲剑
说明：IT运维界面模板。 
状态：

日期：2011/03/02
编辑：蒲剑
任务：升级，将现有Page对象属性与原有Page对象属性映射，减少代码修改。 
状态：测试通过

日期：2011/03/16
编辑：蒲剑
任务：重构，按项目界面设计建立模板页面。 
状态：

日期：2012/04/16
编辑：闫长永
任务：重构，按项目界面设计建立模板页面。 
状态：

-->
<#assign apage = {"currentpage":data.page.pageNo,"pagesize":data.page.pageSize,"maxpage":data.page.totalPages,"size":data.page.result?size,"hasnext":data.page.hasNext,"haspre":data.page.hasPre,"rowcount":data.page.totalCount,"sorttag":data.page.order,"sortfield":data.page.orderBy,"result":data.page.result}>
<#assign vo = data.vo>
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

var dataJSON={
    order:'${data.page.order}',
    cOrderby:'${data.page.orderBy}',
	thead:<@pub_macros.displayheaderjson vo = vo arg = arg />,
	tbody:<@pub_macros.displaylistjson apage = apage vo = vo />
	}
</script>

<script type="text/javascript">
function page_config()
{
	openwin("${base}/module/app/system/query/search/search_locateframe.action?_searchname=search.locate&searchid=${vo.searchid}", pub_height_small,pub_width_small);
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


<div id="pageContainer">
<#if vo.divsearch == "Y">
<div id="searchDivContainer">
	<@pub_macros.displaysearchitem vo = vo arg = arg apage = apage />
</div>
</#if>

<table class="dataGrid">
</table>

<div class="pager" id="thisPager">
<@pub_macros.displaynavigation apage = apage vo = vo arg = arg></@pub_macros.displaynavigation>
</div>

<#if vo.macro!="">
<#include "${vo.macro}">
</#if>

<form method="post" name="form_view" id="form_view" action="${base}${vo.formaction}">
<input type="hidden" name="_searchname" value="${_searchname}">
<input type="hidden" name="page" value="${apage.currentpage}">
<input type="hidden" name="_sortfield" value="${apage.sortfield}" />
<input type="hidden" name="_sorttag" value="${apage.sorttag}" />
<input type="hidden" name="step" value="${apage.pagesize}" />

<@pub_macros.displaypage vo = vo arg = arg />

</form>
</div>

</div>
</body>
</html>
