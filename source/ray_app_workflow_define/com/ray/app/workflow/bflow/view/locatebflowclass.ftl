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

var navigationJSON=[ {name:'配置资源',link:'${base}/module/irm/portal/portal/portal/portal_browse.action?ccate=config'}, {name:'字典软件属性维护',link:'${base}/module/irm/base/config/zdrjsx/zdrjsx_browse.action?_searchname=base.config.zdrjsx.browse'},{name:'修改项目'}];

$(function(){	
	$('textarea').each(function(){
		if(!isNaN($(this).attr('maxlength'))){
			$(this).after('<span class="textLimit" title="字数限制为：'+$(this).attr('maxlength')+'"><div class="numDiv"><span></span><span class="num"></span></div></span>');
			var textLeft=$(this).parent().find('.textLimit span.num')
			$(this).limit($(this).attr('maxlength'),textLeft);
			//textLeft.after('个字符').before('还剩')
		}
	})	
	
	$("#bt_save").click(function(){page_save()});
	$("#bt_close").click(function() {page_close()});
})

function page_save()
{
	$('#locateform').attr('action','${base}/module/app/system/workflow/bflow/bflow_updatebflowclass.action');
	$('#locateform').trigger('submit');
	window.opener.location.reload();	
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

<form id="locateform" method="post" action="">
<input type="hidden" id="id" name="id" value="${data.bflowclass.id}">

<table class="formGrid">
	<tr> 
		<td class="r"><label for="cname">分类中文名称：</label></td>
		<td><input type="text" class="text required" id="cname" name="cname" style="width:20em;" value="${data.bflowclass.cname}" ></td>
		<td class="r">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>	
	<tr>
		<td class="r"><label for="cclass">分类英文名称：</label></td>
		<td><input type="text" class="text required" id="cclass" name="cclass" style="width:20em;" value="${data.bflowclass.cclass}" ></td>				
		<td class="r">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>	
	<tr>
		<td class="r"><label for="memo">说明：</label></td>
		<td colspan="3"><textarea class="text " id="memo" name="memo" value="${data.bflowclass.memo}" style="width:50em;" maxlength="500">${data.bflowclass.memo}</textarea></td>				
		<td class="r">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>		
</table>
</input>
</div>
</body>
</html>