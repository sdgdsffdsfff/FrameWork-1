<!DOCTYPE HTML>
<html>
<head>
<#include "/decorator/include/include.ftl">
<script type="text/javascript" src="${base}/themes/default/jquery.limit-1.2.source.js"></script>
<style>
textarea {	}
.textLimit {position:relative;background:red;}
.textLimit .numDiv {position:absolute;right:0;margin-right:20px;top:-30px;}
.textLimit .numDiv span {display:inline;white-space:nowrap;font-size:18px;color:#666;font-size:700;font-style:italic}
</style>
<script type="text/javascript">
$(function(){
/////////////////////////////////////////
$('textarea').each(function(){
	if(!isNaN($(this).attr('maxlength'))){
		$(this).after('<span class="textLimit" title="字数限制为：'+$(this).attr('maxlength')+'"><div class="numDiv"><span></span><span class="num"></span></div></span>');
		var textLeft=$(this).parent().find('.textLimit span.num')
		$(this).limit($(this).attr('maxlength'),textLeft);
		//textLeft.after('个字符').before('还剩')
	}
})	

$("#bt_confirm").click(function() {page_confirm()});
$("#bt_close").click(function() {page_close()});
/////////////////////////////////////////
})
function page_confirm()
{
	$("#form").attr("action","${base}/module/irm/system/flow/flowlog/flowlog_back.action");
    $("#form").trigger('submit');
}
function page_close()
{
	window.close();
	//window.opener.location.reload();
}
</script>

</head>
<body>
<div id="fixedOp">
<button id="bt_confirm">确认</button>
<button id="bt_close">关闭</button>
</div>
<div id="pageContainer">
<h1>填写意见</h1>
<form id="form" method="post" action="">
<input type="hidden" id="businessid" name="businessid" value="${arg.id}">
<input type="hidden" id="state" name="state" value="${arg.state}">
<input type="hidden" id="duserid" name="duserid" value="${arg.duserid}">
<input type="hidden" id="dusername" name="dusername" value="${arg.dusername}">
<input type="hidden" id="businesstype" name="businesstype" value="${arg.businesstype}">
<input type="hidden" id="taskid" name="taskid" value="${arg.taskid}">
<#if "${arg.businesstype}" == "change">
	<input type="hidden" id="backurl" name="backurl" value="${base}/module/irm/config/change/configchange/configchange_back.action?id=${arg.id}">
<#elseif "${arg.businesstype}" == "project">
	<input type="hidden" id="backurl" name="backurl" value="${base}/module/irm/project/project/project/project_back.action?id=${arg.id}">
<#elseif "${arg.businesstype}" == "task">
	<input type="hidden" id="backurl" name="backurl" value="${base}/module/irm/project/task/task/task_back.action?id=${arg.id}">
<#elseif "${arg.businesstype}" == "tasktail">
	<input type="hidden" id="backurl" name="backurl" value="${base}/module/irm/project/task/tasktail/tasktail_back.action?id=${arg.id}">
<#elseif "${arg.businesstype}" == "businessact">
	<input type="hidden" id="backurl" name="backurl" value="${base}/module/irm/business/businessact/businessact/businessact_back.action?id=${arg.id}">
</#if>
<p>当前流程环节：<strong>${arg.sname}</strong> <img src="${base}/themes/default/images/arrow_large_right.png" style="vertical-align:middle;" /> 下一流程环节：<strong>${arg.dname}</strong></p>
<table class="formGrid">
<tr>
<td id="yijianTd"></td>
</tr>
<tr>
<tr>
<td>当前意见：</td>
</tr>
<tr>
<td><textarea class = "required" id="adescription" name="adescription" style="width:650px;" maxlength="500"></textarea></td>
</tr>
</table>


</form>
</div>

</body>
</html>
