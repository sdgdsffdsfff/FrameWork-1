<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>运维资源管理系统</title>
<link rel="shortcut icon" href="favicon.ico">
<script type="text/javascript" src="${base}/themes/default/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${base}/themes/default/gex.js"></script>
<script type="text/javascript" src="${base}/themes/default/main.js"></script>
<script type="text/javascript" src="${base}/resource/default/script/public_complete.js"></script>
<style type="text/css">@import url(${base}/themes/default/main.css);</style>
<style type="text/css">
html,body {height:100%;margin:0;padding:0;overflow:hidden;}
table {height:100%;}
</style>

<script type="text/javascript">
var navigationJSON=[ {name:'事件查看'}];


function initialTabNsHeight(){
	//console.log($('#gTabsContainterN .topTr').height())
	var avialHeight=$('body').height()-60;
	$('#gTabsContainterN iframe').css({height:avialHeight+'px'});
	$('.bottomTr').css({height:avialHeight+'px'})
}

$(function(){
/////////////////////////////////////////

//解决ie6 bottomTr 高度问题；ff iframe 100% 问题
initialTabNsHeight()
$(window).resize(function(){initialTabNsHeight();})

//点击标签页
$('.topTr li').click(function(){
	var oindex=$(this).index()+1;
	$('.topTr li').removeClass('c')
	$(this).addClass('c');
	$('.bottomTr li').removeClass('c')
	$('.bottomTr li:nth-child('+oindex+')').addClass('c');
	
	
	var oFrame=$('.bottomTr').find('iframe')
	
	//点击标签页去哪里，在这里写 开始-------
		oFrame.attr('src',$(this).attr('data-url'));
	
}).hoverClass('hover');


//radius border
$('#gTabsContainterN .topTr li').wrapInner('<span class="r"><span class="m"></span></span>')

$('.back2grid').click(function(){
	window.close();
	window.opener.location.reload();
})




/////////////////////////////////////////
})
</script>

</head>
<body class="event">

<table id="gTabsContainterN">
<tr class="topTr">
<td>
<ul class="gTabs">
<li class="current icon info c" data-url="search_locate.action?_searchname=search.locate&searchid=${searchid}">视图定义</li>
<li data-url="searchoption_browse.action?_searchname=searchoption.browse&searchid=${searchid}">视图列项</li>
<li data-url="searchitem_browse.action?_searchname=searchitem.browse&searchid=${searchid}">视图条件</li>
<li data-url="searchurl_browse.action?_searchname=searchurl.browse&searchid=${searchid}">相关功能</li>
<li data-url="searchlink_browse.action?_searchname=searchlink.browse&searchid=${searchid}">视图列项链接</li>

</ul>
</td>
</tr>

<tr class="bottomTr">
<td>
<iframe src="search_locate.action?_searchname=search.locate&searchid=${searchid}" style="width:100%;height:100%;" frameborder="0" allowtransparency="true"></iframe>
</td>
</tr>

</table>

</body>
</html>