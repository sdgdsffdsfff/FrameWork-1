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
var navigationJSON=[ {name:'系统管理',link:'${base}/module/irm/portal/portal/portal/portal_browse.action?ccate=admin'},{name:'角色',link:'${base}/module/irm/system/organ/role/role_browse.action?_searchname=system.organ.role.browse'}, {name:'角色新增'}];

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

/////////////////////////////////////////
})

function page_save()
{
    $("#inputform").attr('action','role_insert.action');
    $("#inputform").trigger('submit');
}

function page_close()
{
	window.opener.location.reload();
	window.close();
	//window.location = "role_browse.action?_searchname=system.organ.role.browse";
}
	
	
	
</script>

</head>
<body>

<script type="text/javascript">
</script>
	<div id="fixedOp">
  	<button id="bt_save">保存</button>
  	<button id="bt_close">关闭</button>
	</div>
<h1></h1>
<div id="pageContainer">

<form id="inputform" method="post" action="/irm/">
<input type="hidden" id="_searchname" name="_searchname" value="">
<table class="formGrid">

<tr>
	<td class="r"><label for="cname">角色中文名：</label></td>
	<td><input type="text" class="text required" id="cname" name="cname" style="width:20em;" value="" >
	</td>
	
	<td class="r"><label for="name">角色英文名：</label></td>
	<td><input type="text" class="text required" id="name" name="name" style="width:20em;" value="" >
	</td>
</tr>			
<tr>
	<td class="r"><label for="isintrinsicrole">是否内置角色：</label></td>
	<td>
		<span class="selectSpan">
			<input type="hidden" id="isintrinsicrole" name="isintrinsicrole" value="" />
			<input class="select required" style="width:110px;" data-options="是||否" data-values="Y||N" data-default="">
		</span>
	</td>
</tr>
<tr>
	<td class="r"><label for="memo">备注：</label></td>
	<td colspan="3"><textarea id="memo" class="text " name="memo" rows="" cols="" maxlength="500"></textarea>
</td>

</tr>			

</table>
</form>
</div>
</body>
</html>
