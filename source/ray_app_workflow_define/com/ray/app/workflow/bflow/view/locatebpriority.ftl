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
	
	$('textarea').each(function(){
		if(!isNaN($(this).attr('maxlength'))){
			$(this).after('<span class="textLimit" title="字数限制为：'+$(this).attr('maxlength')+'"><div class="numDiv"><span></span><span class="num"></span></div></span>');
			var textLeft=$(this).parent().find('.textLimit span.num')
			$(this).limit($(this).attr('maxlength'),textLeft);
			//textLeft.after('个字符').before('还剩')
		}
	})	
	
	$("#bt_save").click(function() {page_save()});
	$("#bt_close").click(function() {page_close()});
})

function page_save()
{
	$('#locateform').attr('action','${base}/module/app/system/workflow/bflow/bflow_updatebpriority.action');
	$('#locateform').trigger('submit');
}
function page_close()
{
	window.close();
	window.opener.location.reload();
}
	
</script>
</head>
<body>
<div id="fixedOp">
  	<button id="bt_save">保存</button>
  	<button id="bt_close">关闭</button>
</div>

<div id="pageContainer">

<form id="locateform" method="post" action="">
<input type="hidden" id="id" name="id" value="${data.bpriority.id}">

<table class="formGrid">
	<tr> 
		<td class="r"><label for="cname">缓急程度名称：</label></td>
		<td><input type="text" class="text required" id="cname" name="cname" style="width:20em;" value="${data.bpriority.cname}" ></td>
		<td class="r"><label for="worktime">工作时间（小时）：</label></td>
		<td><input type="text" class="text required num" id="worktime" name="worktime" style="width:20em;" value="${data.bpriority.worktime}" ></td>				
	</tr>	
	<tr>
		<td class="r"><label for="agenttime">催办时间（小时）：</label></td>
		<td><input type="text" class="text required num" id="agenttime" name="agenttime" style="width:20em;" value="${data.bpriority.agenttime}" ></td>				
		<td class="r"><label for="outtime">超时时间（小时）：</label></td>
		<td><input type="text" class="text required num" id="outtime" name="outtime" style="width:20em;" value="${data.bpriority.outtime}" ></td>				
	</tr>	
	<tr>
		<td class="r"><label for="agentnum">催办频率：</label></td>
		<td><input type="text" class="text required num" id="agentnum" name="agentnum" style="width:20em;" value="${data.bpriority.agentnum}" ></td>				
		<td class="r">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>		
	<tr>
		<td class="r"><label for="descript">说明：</label></td>
		<td colspan="3"><textarea class="text" id="descript" name="descript" value="${data.bpriority.descript}" style="width:50em;" maxlength="500">${data.bpriority.descript}</textarea></td>				
	</tr>	
</table>
</form>
</div>
</body>
</html>