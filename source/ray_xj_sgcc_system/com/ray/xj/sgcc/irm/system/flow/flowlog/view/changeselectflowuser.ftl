<!DOCTYPE HTML>
<html>
<head>
<#include "/decorator/include/include.ftl">
<script type="text/javascript">

//是否允许多选的标志
var multi=0;
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

$('button.add').click(function(){
	//$('#gAjaxTree li span',window.)
	//console.log($('body',$('iframe:visible').contents()))
	$('#gAjaxTree li span.selected',$('iframe:visible').contents()).each(function(){
		var oid=$(this).parent().attr('data-id')
		if($.inArray(oid,chozenArray)==-1){
			
			if(multi==1){//允许多选
				$('#result').append('<li data-id="'+oid+'" title="'+$(this).text()+'">'+$(this).text()+'</li>');
				chozenArray.push(oid);
			}else{//只能单选
				$('#result').empty().append('<li data-id="'+oid+'" title="'+$(this).text()+'">'+$(this).text()+'</li>');				
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
$('#bt_confirm').click(function(){
	var arr1=[];
	var arr2=[];
	$('#result li').each(function(){
		arr1.push($(this).text());
		arr2.push($(this).attr('data-id'));
	})
	//console.log(arr1,arr2)	
	//console.log($('#choosenPeople',window.opener.document))
	$("#dusername").val(arr1.join(','));
	$("#duserid").val(arr2.join(','));
	
	if($("#duserid").val()=="")
	{
		alert("请选择人员！");
	}else
	{
		var businesstype = "${arg.businesstype}" ;
		var ctype = "${arg.ctype}"
		if(businesstype == "change")
		{
			$("#form").attr("action","${base}/module/irm/system/flow/flowlog/flowlog_forward.action");
    	}
    	else if(businesstype == "project")
    	{
    	    if(ctype == "send")
    	    {
    	    	alert("将项目特送给"+$('#dusername').val()+"。");
    			$("#form").attr("action","${base}/module/irm/project/project/project/project_sendforward.action");
    		}
    		else if(ctype == "transfer")
    		{
    			alert("将项目移交给"+$('#dusername').val()+"。");
    			$("#form").attr("action","${base}/module/irm/project/project/project/project_transfer.action");
    		}
    	}
    	else if(businesstype == "task")
    	{
    	    if(ctype == "send")
    	    {
    	    	alert("将任务特送给"+$('#dusername').val()+"。");
    			$("#form").attr("action","${base}/module/irm/project/task/task/task_sendforward.action");
    		}
    		else if(ctype == "transfer")
    		{
    			alert("将任务移交给"+$('#dusername').val()+"。");
    			$("#form").attr("action","${base}/module/irm/project/task/task/task_transfer.action");
    		}
    	}
    	else if(businesstype == "tasktail")
    	{
    		alert("将任务跟踪特送给"+$('#dusername').val()+"。");
    	    if(ctype == "send")
    	    {
    			$("#form").attr("action","${base}/module/irm/project/task/tasktail/tasktail_sendforward.action");
    		}
    	}
    	$("#form").trigger('submit');
    }
})

$("#bt_prev").click(function() {page_prev()});
$("#bt_close").click(function() {page_close()});
///////////////////
})

function page_prev()
{
	//window.location="${base}/module/irm/system/flow/flowlog/flowlog_changecomment.action?id=${arg.id}&state=${arg.state}";
	history.go(-1);
}
function page_close()
{
	window.close();
}
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
#result {height:300px;width:180px;}
#resultTd {width:30%;}

</style>
</head>
<body id="layoutPage">
<div id="fixedOp">
<#if "${arg.businesstype}" == "change">
<button class="wizard_prev" id="bt_prev">上一步</button>
</#if>
<button id="bt_confirm">确认</button>
<button id="bt_close">关闭</button>
</div>

<div id="oTab">
<form id="form" method="post" action="">
<input type="hidden" id="businessid" name="businessid" value="${arg.id}">
<input type="hidden" id="state" name="state" value="${arg.state}">
<input type="hidden" id="ctype" name="ctype" value="${arg.ctype}">
<input type="hidden" id="duserid" name="duserid" value="">
<input type="hidden" id="dusername" name="dusername" value="">
<input type="hidden" id="businesstype" name="businesstype" value="${arg.businesstype}">
<input type="hidden" id="adescription" name="adescription" value="${arg.adescription}">
<#if "${arg.businesstype}" == "change">
<input type="hidden" id="backurl" name="backurl" value="${base}/module/irm/config/change/configchange/configchange_forward.action?id=${arg.id}">
<#elseif "${arg.businesstype}" == "project">
<input type="hidden" id="backurl" name="backurl" value="${base}/module/irm/project/project/project/project_browsewaitwork.action?_searchname=project.project.project.browsewaitwork">
<#elseif "${arg.businesstype}" == "task">
<input type="hidden" id="backurl" name="backurl" value="${base}/module/irm/project/task/task/task_browsewaitwork.action?_searchname=project.task.task.browsewaitwork">
<#elseif "${arg.businesstype}" == "tasktail">
<input type="hidden" id="backurl" name="backurl" value="${base}/module/irm/project/task/tasktail/tasktail_readpage.action?_searchname=task.browse_tasktail.locate&id=${arg.id}">
</#if>
</form>

<table>
<tr>
<td><ul class="tabTitle"><li>按流程</li><li>按部门</li><li>按角色</li><li>按姓名</li></ul>
</td>
<td>
</td>
<td>
</td>
</tr>
<tr>
<td>
<ul class="tabContent">
	<li><iframe src="${base}/module/irm/system/flow/flowlog/flowlog_flowuserpage.action?businesstype=${arg.businesstype}&state=${arg.state}&bid=${arg.id}" frameborder="0"></iframe></li>
	<li><iframe src="${base}/module/irm/system/flow/flowlog/flowlog_organpage.action" frameborder="0"></iframe></li>
	<li><iframe src="${base}/module/irm/system/flow/flowlog/flowlog_rolepage.action" frameborder="0"></iframe></li>
	<li><iframe src="${base}/module/irm/system/flow/flowlog/flowlog_changeuserpage.action" frameborder="0"></iframe></li>
</ul>
</td>
<td  width="50" style="text-align:center;">
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