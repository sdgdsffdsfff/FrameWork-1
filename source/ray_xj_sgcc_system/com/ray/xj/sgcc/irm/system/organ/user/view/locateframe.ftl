<!DOCTYPE HTML>
<html>
<head>
<#include "/decorator/include/include.ftl">

<script type="text/javascript">
var navigationJSON=[ {name:'系统管理',link:'${base}/module/irm/portal/portal/portal/portal_browse.action?ccate=admin'},{name:'人员',link:'${base}/module/irm/system/organ/user/user_mainframe.action?organid=R0'}, {name:'用户信息'}];
function initialTabNsHeight(){
	//console.log($('#gTabsContainterN .topTr').height())
	var avialHeight=$('body').height()-90;
	$('#gTabsContainterN iframe').css({height:avialHeight+'px'});
	$('.bottomTr').css({height:avialHeight+'px'})
}


$(function(){
/////////////////////////////////////////

//解决ie6 bottomTr 高度问题；ff iframe 100% 问题
initialTabNsHeight()
$(window).resize(function(){initialTabNsHeight();})

//点击标签页去哪里，在这里写 开始-------
$('.topTr li').click(function(){
	var oindex=$(this).index()+1;
	$('.topTr li').removeClass('c')
	$(this).addClass('c');
	$('.bottomTr li').removeClass('c')
	$('.bottomTr li:nth-child('+oindex+')').addClass('c');
	
	var oFrame=$('.bottomTr').find('iframe')
	
	if(oindex==1)
	{//第一个标签页
		oFrame.attr('src','user_locate.action?_searchname=system.organ.user.locate&id=${arg.id}&organid=${arg.organid}');
	}else if(oindex==2){
		oFrame.attr('src','${base}/module/irm/system/organ/user/user_browserole.action?_searchname=system.organ.user.browserole&id=${id}&organid=${arg.organid}');
	}
	
}).hoverClass('hover');



//radius border
$('#gTabsContainterN .topTr li').wrapInner('<span class="r"><span class="m"></span></span>')

$('.back2grid').click(function(){
	//window.location = "user_browse.action?_searchname=system.organ.user.browse&organid=${arg.organid}";
	window.close();
	window.opener.location.reload();
})

/////////////////////////////////////////
})
</script>
</head>
<body style="overflow:hidden;">
<table id="gTabsContainterN">
<tr class="topTr">
<td>
	<a class="back2grid" href="javascript:void(0)">关闭</a>
	<ul>
		<li class="c">用户信息</li>
		<#if arg.isusing=="Y">
		<li>角色</li>
		</#if>
	</ul>	
</td>
<tr class="bottomTr">
<td>
<iframe src="user_locate.action?_searchname=system.organ.user.locate&id=${arg.id}&organid=${arg.organid}" frameborder="0"></iframe>
</td>
</tr>

</table>
</body>
</html>