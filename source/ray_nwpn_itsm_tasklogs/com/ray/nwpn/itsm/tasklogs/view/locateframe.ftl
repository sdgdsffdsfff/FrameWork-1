<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>信息系统运行维护管理系统-right</title>
<script type="text/javascript" src="${base}/themes/default/jquery.js"></script>
<script type="text/javascript" src="${base}/themes/default/gex.js"></script>
<script type="text/javascript" src="${base}/themes/default/content.js"></script>
<style type="text/css">@import url(${base}/themes/default/content.css);</style>
<style type="text/css">
body {overflow:auto;margin:0;}
h1 {margin:0 10px;}
ul.gTabs {margin:0 10px;height:29px;border-bottom:solid 1px #d1e29c;}
.firstTrcontent {vertical-align:top;}
.dataTable {margin:10px;}
.iframeTr {display:none;}
</style>
</head>
<body class="">

<table class="layoutTable">
<tr>
<td>
<ul class="gTabs">

<li class="current icon info url|tasklogs_locate.action?_searchname=tasklogs.locate&id=${arg.id}&bussinessid=${arg.bussinessid}">基本信息</li>
<li class="url|${base}/module/app/business/tasklogs/tasklogsdetail/tasklogsdetail_browse.action?_searchname=tasklogsdetail.browse&tasklogsid=${arg.id}&bussinessid=${arg.bussinessid}">明细信息</li>

</ul>
</td>
</tr>

<tr style="height:90%;">
<td>
<iframe id="iframe1" src="tasklogs_locate.action?_searchname=tasklogs.locate&id=${arg.id}&bussinessid=${arg.bussinessid}" style="width:100%;height:100%;" frameborder="0" allowtransparency="true"></iframe>
</td>
</tr>
</table>
</body>
</html>

<#--
<link rel="stylesheet" type="text/css" href="/ray_nwpn_itsm/resource/default/css/ui.tabs.css" media="screen" id="forscreen" />
<script type="text/javascript" src="/ray_nwpn_itsm/resource/default/script/ui.core.js"></script>
<script type="text/javascript" src="/ray_nwpn_itsm/resource/default/script/ui.tabs.js"></script>
<div id="tabs">
    <ul>
	    
        <li><a href="#fragment-0"><span>基本信息</span></a></li>
        <li><a href="#fragment-1"><span>处理信息</span></a></li>
        <li><a href="#fragment-2"><span>相关问题</span></a></li>
        <li><a href="#fragment-3"><span>相关变更</span></a></li>
        <li><a href="#fragment-4"><span>相关配置</span></a></li>
        <li><a href="#fragment-5"><span>知识库</span></a></li>
        
    </ul>
    
    <div id="fragment-0">
   		<iframe src="" width="100%" height="580" frameborder="0" border="0" scrolling="no" ></iframe>
    </div>
    <div id="fragment-1">
  	 	<iframe src="" width="100%" height="580"  frameborder="0" border="0" scrolling="no" ></iframe>
    </div>
    <div id="fragment-2">
    	<iframe src="" width="100%" height="580"  frameborder="0" border="0" scrolling="no" ></iframe>
    </div>
    <div id="fragment-3">
    	<iframe src="" width="100%" height="580"  frameborder="0" border="0" scrolling="no" ></iframe>
    </div>
    <div id="fragment-4">
    	<iframe src="" width="100%" height="580"  frameborder="0" border="0" scrolling="no" ></iframe>
    </div>
    <div id="fragment-5">
    	<iframe src="" width="100%" height="580"  frameborder="0" border="0" scrolling="no" ></iframe>
    </div>
</div>
<script>
$(function() 
{
    $('#tabs > ul').tabs({ fx: { opacity: 'toggle' } }).tabs('tabs', 800);
    window.frames["0"].location="event_locate.action?_searchname=event.locate&taskid=${arg.taskid}";
    window.frames["1"].location="${base}/module/app/business/event/eventdisposal/eventdisposal_browse.action?_searchname=eventDisposal.browse&taskid=${arg.taskid}";      
    window.frames["2"].location="${base}/module/app/business/problem/problem/problem_browse.action?_searchname=problem.browse";      
    window.frames["3"].location="${base}/module/app/business/change/change/change_browse.action?_searchname=change.browse";      
    window.frames["4"].location="${base}/module/app/business/config/config/config_browse.action?_searchname=config.browse";
    window.frames["5"].location="${base}/module/app/business/knowledge/knowledge/knowledge_browse.action?_searchname=knowledge.browse";
}
);
</script>

-->