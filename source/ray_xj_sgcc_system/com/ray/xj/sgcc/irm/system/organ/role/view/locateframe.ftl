<!DOCTYPE HTML>
<html>
<head>
<#include "/decorator/include/include.ftl">

<script type="text/javascript">
var navigationJSON=[ {name:'系统管理',link:'${base}/module/irm/portal/portal/portal/portal_browse.action?ccate=admin'},{name:'角色',link:'${base}/module/irm/system/organ/role/role_browse.action?_searchname=system.organ.role.browse'}, {name:'角色信息'}];

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
	if(oindex==1)
	{//第一个标签页
		oFrame.attr('src','role_locate.action?_searchname=system.organ.role.locate&id=${id}');
	}else if(oindex==2){
		oFrame.attr('src','${base}/module/irm/system/organ/role/role_mainframe.action?_searchname=system.organ.role.browseuser&roleid=${id}');
	}else if(oindex==3){
		oFrame.attr('src','${base}/module/irm/system/organ/role/role_functionmainframe.action?roleid=${id}');
	}
	<#--
	else if(oindex==4){
		oFrame.attr('src','${base}/module/irm/system/organ/role/role_browseportalitem.action?_searchname=system.organ.role.browseportalitem&roleid=${id}');
	}
	else if(oindex==5){
		oFrame.attr('src','${base}/module/irm/system/organ/role/role_browsesubject.action?_searchname=system.organ.role.browsesubject&roleid=${id}');
	}
	else if(oindex==6){
		oFrame.attr('src','${base}/module/irm/system/organ/role/role_browseshortcut.action?_searchname=system.organ.role.browseshortcut&roleid=${id}');
	}
	-->
}).hoverClass('hover');

//radius border
$('#gTabsContainterN .topTr li').wrapInner('<span class="r"><span class="m"></span></span>')

$('.back2grid').click(function(){
	//window.location = "role_browse.action?_searchname=system.organ.role.browse";
	window.close();
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
		<li class="c">角色信息</li>
		<li>角色人员</li>
		<li>角色功能</li>
		<#--
		<li>角色门户菜单</li>		
		<li>角色栏目</li>		
		<li>角色快捷项</li>
		-->		
	</ul>	
</td>
</tr>
<tr class="bottomTr">
<td>
<iframe src="role_locate.action?_searchname=system.organ.role.locate&id=${id}" frameborder="0"></iframe>


</table>
</body>
</html>