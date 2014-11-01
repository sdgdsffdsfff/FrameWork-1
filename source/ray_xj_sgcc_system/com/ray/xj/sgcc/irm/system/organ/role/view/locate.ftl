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
var navigationJSON=[ {name:'系统管理',link:'${base}/module/irm/portal/portal/portal/portal_browse.action?ccate=admin'},{name:'角色',link:'${base}/module/irm/system/organ/role/role_browse.action?_searchname=system.organ.role.browse'}, {name:'角色修改'}];

$('textarea').each(function(){
	if(!isNaN($(this).attr('maxlength'))){
		$(this).after('<span class="textLimit" title="字数限制为：'+$(this).attr('maxlength')+'"><div class="numDiv"><span></span><span class="num"></span></div></span>');
		var textLeft=$(this).parent().find('.textLimit span.num')
		$(this).limit($(this).attr('maxlength'),textLeft);
		//textLeft.after('个字符').before('还剩')
	}
})
$("#bt_save").click(function() {page_save()});
/////////////////////////////////////////
})
function page_save()
	{
	    $("#locateform").attr('action','role_update.action');
	    $("#locateform").trigger('submit');
	}
</script>
</head>
<body>

<script type="text/javascript">
</script>
<div id="fixedOp">
  	<button id="bt_save">保存</button>
</div>

<div id="pageContainer">

<form id="locateform" method="post" action="">
<input type="hidden" id="_searchname" name="_searchname" value="">
<input type="hidden" id="id" name="id" value="${data.aobj.id}">

<table class="formGrid">
	<tr>
		<td class="r"><label for="cname">角色中文名：</label></td>
		<td><input type="text" class="text request" id="cname" name="cname" style="width:20em;" value="${data.aobj.cname}" ></td>
	
		<td class="r"><label for="name">角色英文名：</label></td>
		<td><input type="text" class="text request" id="name" name="name" style="width:20em;" value="${data.aobj.name}" ></td>
	</tr>			
	<tr>
		<td class="r"><label for="isintrinsicrole">是否内置角色：</label></td>
		<td><span class="selectSpan">
			<input type="hidden" id="isintrinsicrole" name="isintrinsicrole" value="${data.aobj.isintrinsicrole}" />
			<input class="select required" style="width:110px;" data-options="是||否" data-values="Y||N" data-default="${data.aobj.isintrinsicrole}">
		</span>
		</td>
	</tr> 
	<tr>
		<td class="r"><label for="memo">备注：</label></td>
		<td colspan="3"><input type="hidden" class="text" id="memo"><textarea name="memo" value="${data.aobj.memo}" maxlength="500" >${data.aobj.memo}</textarea></td>
	</tr>			
	 
</table>
</form>
</div>
</body>
</html>



	
	

