<!DOCTYPE HTML>
<html>
<head>
<#include "/decorator/include/include.ftl">
<script type="text/javascript">

//是否允许多选的标志
var multi=${arg.multi};
var flag=${arg.flag};
//是否显示全称的标识
var full=${arg.full};
//chozen item array to check 
var chozenArray=[];

jQuery(function($){
///////////////////

$('#result').sortable({placeholder:'ui-sortable-placeholder',axis:'y',delay:200})
$('#result li').live('click',function(){
	$(this).toggleClass('c');	
})

$('button.remove').click(function(){
	if(multi==1){
	$('#result li.c').each(function(){
		var thisVal=$(this).attr('data-id');
		chozenArray = jQuery.grep(chozenArray, function(value) {
		return value !=thisVal;
		});
		$(this).remove();
	})}else{
		chozenArray=[]	
		$('#result').empty();
	}
	
})

$('#result li').live('dblclick',function(){
	
	var thisVal=$(this).attr('data-id');
		chozenArray = jQuery.grep(chozenArray, function(value) {
		return value !=thisVal;
		});
		
	$(this).remove();

})

<#if arg.user!=null>
	if(multi==1){//允许多选
		$('#result').append('<li data-id="'+${arg.user.loginname}+'" data-deptname="'+${arg.user.owneorgname}+'" data-deptallname="'+${arg.user.allname}+'" data-deptid="'+${arg.user.ownerorg}+'" title="'+${arg.user.loginname}+'">'+${arg.user.loginname}+'</li>');
		chozenArray.push(oid);
	}else{//只能单选
	   
		$('#result').empty().append('<li data-id="${arg.user.loginname}" data-deptname="${arg.user.owneorgname}"  data-deptallname="'+${arg.user.allname}+'" data-deptid="${arg.user.ownerorg}" title="${arg.user.cname}" >${arg.user.cname}</li>');				
	}
</#if>

$('button.add').click(function(){
	$('#gAjaxTree li span.selected',$('iframe:visible').contents()).each(function(){
		var oid=$(this).parent().attr('data-id')
		var odeptname=$(this).parent().attr('data-deptname');
		var odeptallname=$(this).parent().attr('data-deptallname');
		var odeptid=$(this).parent().attr('data-deptid');
		if($.inArray(oid,chozenArray)==-1){
			
			if(multi==1){//允许多选
				$('#result').append('<li data-id="'+oid+'" data-deptname="'+odeptname+'" data-deptallname="'+odeptallname+'" data-deptid="'+odeptid+'" title="'+$(this).text()+'">'+$(this).text()+'</li>');
				chozenArray.push(oid);
			}else{//只能单选
				$('#result').empty().append('<li data-id="'+oid+'" data-deptname="'+odeptname+'" data-deptallname="'+odeptallname+'" data-deptid="'+odeptid+'" title="'+$(this).text()+'">'+$(this).text()+'</li>');				
				chozenArray=[];
				chozenArray.push(oid);	
			}
		
		}else{
			//
		}
		$(this).removeClass('selected');
	})
})

//返回最终选择人员id
$('#getChosen').click(function(){


if($('#result').find('li').length==0)
{
	alert("请选择人员！");
	return;
}

	var arr1=[];
	var arr2=[];
	var arr3=[];
	var arr4=[];
	var arr5=[];
	var pid = '${arg.pid}';
	var pdeptname = '${arg.pdeptname}';
	var pdeptid = '${arg.pdeptid}';
	var addissuer = '${arg.addissuer}';
	var audituserid = '${arg.audituserid}';
	$('#result li').each(function(){
		arr1.push($(this).text());
		arr2.push($(this).attr('data-id'));
		arr3.push($(this).attr('data-deptname'));
		arr4.push($(this).attr('data-deptid'));
		arr5.push($(this).attr('data-deptallname'));
	})
	
	
	if(addissuer=='Y')
	{
		$("#chargeuser").val(arr1.join(','));
		$("#chargeuserid").val(arr2.join(','));
		$("#chargedept").val(arr3.join(','));
		$("#chargedeptid").val(arr4.join(','));
		
		var chargeuserid = $("#chargeuserid").val() ;
		
		if(chargeuserid == audituserid)
		{
			alert("责任人与审核人不能为同一人！请重新选择！");
			return false;
		}
		else
		{
				
			$.post("${base}/module/irm/task/task/task/task_addchargeuser.action",
			{taskid:"${arg.taskid}",chargeuser:$('#chargeuser').val(),chargeuserid:$('#chargeuserid').val(),chargedept:$('#chargedept').val(),chargedeptid:$('#chargedeptid').val()},
			function(data){
		 	
		 		if(data)
		 		{
		 			alert("追加责任人成功！");
			 		window.close();
		    		window.opener.location.reload();
		    	}
		    	else
		    	{
		    	 	alert("追加责任人失败，请重新选择！");
		    	}
			})
	//		$("#form").attr("action","${base}/module/irm/project/task/task/task_addchargeuser.action");
	//   	$("#form").trigger('submit');
    	
    	}
	}
	else
	{
		$('#${arg.pname}',window.opener.document).val(arr1.join(','))	
		if(pid!=null)
		{
			$('#${arg.pid}',window.opener.document).val(arr2.join(','))
		}
		else
		{
			//
		}
		if(pdeptname!=null)
		{
			if(full==0)
			{
				$('#${arg.pdeptname}',window.opener.document).val(arr3.join(','))
			}
			else
			{
				$('#${arg.pdeptname}',window.opener.document).val(arr5.join(','))
			}
		}
		else
		{
			//
		}
		if(pdeptid!=null)
		{
			$('#${arg.pdeptid}',window.opener.document).val(arr4.join(','))
		}
		else
		{
			//
		}
		window.close();
	}
	
})

$('#bt_close').click(function(){
	window.close();
})


//radius border
$('#gTabsContainterN .topTr li').wrapInner('<span class="r"><span class="m"></span></span>')



//点击标签页
$('.topTr li').click(function(){
	var oindex=$(this).index()+1;
	$('.topTr li').removeClass('c')
	$(this).addClass('c');
	var oFrame=$('.bottomTr').find('iframe')
	
	//点击标签页去哪里，在这里写 开始-------
	<#if arg.choose??>
<#if arg.choose == 'dept'>
	oFrame.attr('src','user_organname.action')
</#if>
<#if arg.choose == 'role'>
	oFrame.attr('src','user_rolename.action?rolename=${arg.rolename}')
</#if>
<#if arg.choose == 'user'>
	oFrame.attr('src','user_personname.action')
</#if>

<#else>
	
	if(oindex==1){//第一个标签页
		oFrame.attr('src','user_organname.action')
	}else if(oindex==2){
		oFrame.attr('src','user_rolename.action')
	}else if(oindex==3){
		oFrame.attr('src','user_personname.action')
	}
</#if>	
	//点击标签页去哪里，在这里写 结束-------
}).hoverClass('hover');


//$('.topTr li:first').trigger('click')


///////////////////
})
</script>
<style type="text/css">
html,body {overflow:hidden;}
body {background:#fff;}
#layoutPage {margin:6px;}
#layoutPage table {border-collapse:collapse;borde:0;width:100%;}
#layoutPage table td {padding:0;}
#oTab .tabContent iframe {width:100%;border:0;float:left;border:solid 1px #ccc;border-top:0;}

#result li {line-height:22px;padding-left:8px;cursor:pointer;white-space:nowrap;text-overflow:ellipsis;overflow:hidden;_width:172px;}
#result li:hover {background:#eee;}
#result li.c {background-color:#21759b;color:#fff;}
.ui-sortable-placeholder {width:160px;}

/*size config*/
#oTab .tabContent iframe {height:300px;}
#result {height:300px;}
#resultTd {width:40%;}


#gTabsContainterN .topTr ul {background:none}
#gTabsContainterN {height:auto;}
#gTabsContainterN .bottomTr li {display:block;}
#gTabsContainterN iframe {height:300px;}

</style>
</head>
<body id="layoutPage">

<div id="fixedOp">
<button id="getChosen">确定</button><button id="bt_close">取消</button>
</div>

<div id="gTabsContainterN">

<h1>选择人员</h1>
<form id="form" method="post" action="">
<input type="hidden" id="chargeuser" name="chargeuser">
<input type="hidden" id="chargeuserid" name="chargeuserid">
<input type="hidden" id="chargedept" name="chargedept">
<input type="hidden" id="chargedeptid" name="chargedeptid">
<input type="hidden" id="taskid" name="taskid" value="${arg.taskid}">
</form>
<table>
<tr class="topTr">
<td colspan="3"><ul class="tabTitle">
<#if arg.choose??>
<#if arg.choose == 'dept'>
	<li>按部门</li>
</#if>
<#if arg.choose == 'role'>
	<li>按角色</li>
</#if>
<#if arg.choose == 'user'>
	<li>按姓名</li>
</#if>

<#else>
<li class="c" >按部门</li><li>按角色</li><li>按姓名</li>
</#if>
</ul>
</td>
</tr>
<tr class="bottomTr">
<td style="vertical-align:top;border:solid 1px #ccc;">
<ul class="tabContent">
<#if arg.choose??>
<#if arg.choose == 'dept'>
	<li><iframe src="user_organname.action" frameborder="0" height="300"></iframe></li>
</#if>
<#if arg.choose == 'role'>
	<li><iframe src="user_rolename.action?rolename=${arg.rolename}" frameborder="0" height="300"></iframe></li>
</#if>
<#if arg.choose == 'user'>
	<li><iframe src="user_personname.action" frameborder="0" height="300"></iframe></li>
</#if>

<#else>
	<li><iframe src="user_organname.action" frameborder="0" height="300"></iframe></li>
</#if>
	
</ul>
</td>
<td width="50" style="text-align:center;">
<div><button class="add">-&gt;</button></div><div><button class="remove">&lt;-</button></div><!--<div><button>&gt;&gt;</button></div><div><button>&lt;&lt;</button>--></div>
</td>
<td id="resultTd">
<ul id="result" style="overflow:auto;border:solid 1px #ccc;">
</ul>
</td>
</tr>
</table>
</div>


</body>
</html>