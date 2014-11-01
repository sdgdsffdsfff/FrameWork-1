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

/////////////////////////////////////////
})
</script>

<script type="text/javascript">

jQuery(function($){
///////////////////////////////

$("#bt_save").click(function() {page_save()});
$("#bt_cancel").click(function() {page_cancel()});

///////////////////////////////
})

function page_save()
{
	$("#aform").attr("action","opiniontemplate_insert.action");
    $("#aform").trigger('submit');
    window.opener.location.reload();
}

function page_cancel()
{
	window.opener.location.reload();
	window.close();
}

</script>

<style type="text/css">
#cron li {display:inline-block;*display:inline;*zoom:1;}
#cron ul {display:inline;}
#cron li input {width:3em;}
</style>
</head>
<body>
	<div id="fixedOp">
	<button id="bt_save">保存</button>
	<button id="bt_cancel">关闭</button>
	</div>

	<div id="pageContainer">
		<form id="aform" method="post">
		<table class="formGrid">
   			 <tr id="repeatedTask">
				<td class="r">意见分类：</td>
				<td>
					<span class="selectSpan"><input type="hidden" id ="cclass" name="cclass" />
						<input class="select" data-options="公共||个人" data-values="public||private" data-default="public" style="width:10em"/>
					</span>
				</td>
			</tr>
   			 <tr>
		    	<td class="r" ><label for="">意见内容：</label></td>
		    	<td><textarea class="required" id= "opinion" name="opinion" maxlength='500' rows="2"></textarea></td>
   			 </tr> 
		</table>
		</form>
	</div>
</body>
</html>	