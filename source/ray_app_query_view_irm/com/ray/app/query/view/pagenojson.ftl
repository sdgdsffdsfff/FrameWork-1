<#assign apage = {"currentpage":data.page.pageNo,"pagesize":data.page.pageSize,"maxpage":data.page.totalPages,"size":data.page.result?size,"hasnext":data.page.hasNext,"haspre":data.page.hasPre,"rowcount":data.page.totalCount,"sorttag":data.page.order,"sortfield":data.page.orderBy,"result":data.page.result}>
<#assign vo = data.vo>
<#import "com/ray/app/query/view/pagenojson_macros.ftl" as pub_macros>

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

<#if vo.divbutton == "Y">
	<div id="fixedOp">
	<#if (Session.sys_login_token)??>
	<#if Session.sys_login_token.sys_login_user=="admin">	
		<button onclick="page_config()">配置信息</button>
	</#if>
	</#if>

	<button onclick="page_submit()">确定</button>	
	</div>
</#if>
	
<div id="pageContainer">
<#if vo.divsearch == "Y">
<div id="searchDivContainer">
	<@pub_macros.displaysearchitem vo = vo arg = arg apage = apage />
</div>
</#if>


<table class="dataGrid">
	<!-- 显示标题-->
	<@pub_macros.displayheader vo = vo arg = arg></@pub_macros.displayheader>
	
	<!-- 显示数据-->
	<@pub_macros.displaylist apage = apage vo = vo></@pub_macros.displaylist>
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

</form>
</div>

<script>

var currentkey = "";

function page_view_row_click(keyvalue, index)
{
	currentkey = keyvalue;
}

function page_view_row_dbclick(keyname, keyvalue, index)
{
	currentkey = keyvalue;
	alert(currentkey);
}

function page_radio_row_click(obj, keyname, keyvalue, index)
{
	if(obj.checked == "true")
	{
		currentkey = keyvalue;
	}
	else
	{
		currentkey = "";
	}
}

function page_submit()
{
	if(currentkey=="")
	{
		alert("未选择记录，请选择记录！");
		return;
	}

	var rekeynames = "${arg.returnfield}".split(",");
	var	rekeyvalues = currentkey.split(",");

	for(i=0;i<rekeynames.length;i++)
	{
		$('#' + rekeynames[i],window.opener.document).val(rekeyvalues[i]);
	}
	
	window.close();
}

</script>

</body>
</html>
