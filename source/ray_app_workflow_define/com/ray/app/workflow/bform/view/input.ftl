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

var navigationJSON=[ {name:'配置资源',link:'${base}/module/irm/portal/portal/portal/portal_browse.action?ccate=config'}, {name:'流程表单信息',link:'${base}/module/app/system/workflow/bform/bform_browsebform?_searchname=workflow.form.browsebform'},{name:'查看表单'}];

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
	var classname = $("#classname").val();
	
	$.post("${base}/module/app/system/workflow/bform/bform_isclassid.action",
				{classname:classname},
				function(data){
					eval("var data = "+data + ";");
					if(data == true){
						$("#inputform").attr('action','bform_insert.action');
						$("#inputform").trigger('submit');
					}else{
						alert("您所填的分类名称不存在！");
					}				
	});
	//$("#inputform").attr('action','bform_insert.action');
	//$("#inputform").trigger('submit');
}

function page_close()
{
	window.close();
}
	
</script>
</head>
<body>
<div id="fixedOp">
  	<button id="bt_save">保存</button>
  	<button id="bt_close">关闭</button>
</div>

<div id="pageContainer">

<form id="inputform" method="post" action="">

<table class="formGrid">
	<tr> 
		<td class="r"><label for="cname">表单名称：</label></td>
		<td><input type="text" class="text required" id="cname" name="cname" style="width:20em;" value="" ></td>
		<td class="r">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>	
	<tr>
		<td class="r"><label for="url">表单URL地址：</label></td>
		<td><input type="text" class="text required" id="url" name="url" style="width:20em;" value="" ></td>				
		<td class="r">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>	
	<tr>
		<td class="r"><label for="classname">分类中文名称：</label></td>
		<td><input type="text" class="text required" id="classname" name="classname" style="width:20em;" value="" ></td>				
		<td class="r">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>		
	 <tr>
		<td class="r"><label for="descript">说明：</label></td>
		<td colspan="3"><textarea class="text" id="descript" name="descript" value="" style="width:50em;" maxlength="500"></textarea></td>				
		<td class="r">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>		
</table>
</form>
</div>
</body>
</html>