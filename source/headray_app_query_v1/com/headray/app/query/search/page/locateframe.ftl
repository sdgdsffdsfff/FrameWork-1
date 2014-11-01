<#if Session["sys_style"]?exists>
<#assign sys_style = Session["sys_style"]>
<#else>
<#assign sys_style = "default">
</#if>

<link rel="stylesheet" type="text/css" href="${base}/resource/${sys_style}/css/ui.tabs.css" media="screen" id="forscreen" />
<script type="text/javascript" src="${base}/resource/default/script/ui.core.js"></script>
<script type="text/javascript" src="${base}/resource/default/script/ui.tabs.js"></script>
<div id="tabs">
    <ul>
	    <li><a href="#fragment-0"><span>查询定义</span></a></li>
        <li><a href="#fragment-1"><span>查询操作</span></a></li>
        <li><a href="#fragment-2"><span>查询条件</span></a></li>
        <li><a href="#fragment-3"><span>查询地址</span></a></li>
        <li><a href="#fragment-4"><span>查询链接</span></a></li>
    </ul>
    <div id="fragment-0">
    <iframe src="" width="100%" height="580" frameborder="0" border="0" scrolling="no" ></iframe>
    </div>
    <div id="fragment-1">
    <iframe src="" width="100%" height="780"  frameborder="0" border="0" scrolling="no" ></iframe>
    </div>
    <div id="fragment-2">
    <iframe src="" width="100%" height="580" frameborder="0" border="0" scrolling="no" ></iframe>
    </div>
    <div id="fragment-3">
    <iframe src="" width="100%" height="580" frameborder="0" border="0" scrolling="no" ></iframe>
    </div>
    <div id="fragment-4">
    <iframe src="" width="100%" height="580" frameborder="0" border="0" scrolling="no"></iframe>
    </div>
</div>
<#--
	<iframe src="${base}/module/app/system/query/search/invoke!locate.action?_searchname=search.locate&searchid=${arg.searchid}" width="100%" height="580" frameborder="0" border="0" scrolling="no" ></iframe>
	<iframe src="${base}/module/app/system/query/searchoption/invoke!page.action?_searchname=searchoption.browse&searchid=${arg.searchid}" width="100%" height="780"  frameborder="0" border="0" scrolling="no" ></iframe>
    <iframe src="${base}/module/app/system/query/searchitem/invoke!page.action?_searchname=searchitem.browse&searchid=${arg.searchid}" width="100%" height="580" frameborder="0" border="0" scrolling="no" ></iframe>
    <iframe src="${base}/module/app/system/query/searchurl/invoke!page.action?_searchname=searchurl.browse&searchid=${arg.searchid}" width="100%" height="580" frameborder="0" border="0" scrolling="no" ></iframe>
	<iframe src="${base}/module/app/system/query/searchlink/invoke!page.action?_searchname=searchlink.browse&searchid=${arg.searchid}" width="100%" height="580" frameborder="0" border="0" scrolling="no"></iframe>
-->
<script defer>
$(function() 
{
    $('#tabs > ul').tabs({ fx: { opacity: 'toggle' } }).tabs('tabs', 2000);
    window.frames["0"].location="${base}/module/app/system/query/search/invoke!locate.action?_searchname=search.locate&searchid=${arg.searchid}";
    window.frames["1"].location="${base}/module/app/system/query/searchoption/invoke!page.action?_searchname=searchoption.browse&searchid=${arg.searchid}";
    window.frames["2"].location="${base}/module/app/system/query/searchitem/invoke!page.action?_searchname=searchitem.browse&searchid=${arg.searchid}";
    window.frames["3"].location="${base}/module/app/system/query/searchurl/invoke!page.action?_searchname=searchurl.browse&searchid=${arg.searchid}";
    window.frames["4"].location="${base}/module/app/system/query/searchlink/invoke!page.action?_searchname=searchlink.browse&searchid=${arg.searchid}";
}
);
</script> 
        
<script>
$("#bt_save").click(function() {page_save()});
$("#bt_close").click(function() {page_close()});

function page_save()
{
	form.action = "invoke!update.action";
	form._searchname.value = "search.update";
	if (Validator.Validate(form, 2))
	{
		form.submit();
	}	
}

function page_close()
{
	window.location = "invoke!page.action?_searchname=search.browse";
}
</script>