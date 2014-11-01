<!DOCTYPE HTML>
<html>
<head>
<#include "/decorator/include/include.ftl">
<script type="text/javascript">
var navigationJSON=[ {name:'个性设置',link:'${base}/module/irm/portal/portal/portal/portal_preferences.action'}, {name:'修改密码'}];

$(function(){
/////////////////////////////////////////

$("#bt_save").click(function() {page_save()});
$("#bt_close").click(function() {page_close()});
/////////////////////////////////////////
})
function page_save()
{
	if($('#confirmpassword').val()!=$('#changepassword').val())
	{
		alert("两次输入密码不一致！");
		return;
	}
	var password = $('#password').val();
	var changepassword = $('#changepassword').val();
	
	$.ajax({
	  url: '${base}/module/irm/system/organ/user/user_savepassword.action',
	  data: {password:password,changepassword:changepassword},
	  type: 'post',
	  success: function() {   
		alert("修改成功！");
	  }
	});
	
	//$("#locateform").attr('action','user_savepassword.action');
 	//$("#locateform").trigger('submit');
}
function page_close()
{
	window.location="${base}/module/irm/portal/portal/portal/portal_preferences.action";
}
</script>
</head>
<body>
	<div id="fixedOp">
		<button id="bt_save">保存</button>
		<button id="bt_close">关闭</button>
	</div>
	<div id="pageContainer">
		<h1>修改密码</h1>
		<form id="locateform" method="post" action="">
			<table class="formGrid">
				<tr>
					<td class="r"><label for="">用户名：</label></td>
					<td><input type="text" class="text" readonly id="loginname" name="loginname" value="${data.aobj.loginname}" style="width:50em;"></td>
				</tr>
				<tr>
					<td class="r"><label for="">原密码：</label></td>
					<td><input type="password" class="text required" id="password" name="password" value="" style="width:50em;"></td>
				</tr>
				<tr>
					<td class="r"><label for="">修改密码：</label></td>
					<td><input type="password" class="text required" id="changepassword" name="changepassword" value="" style="width:50em;"></td>
				</tr>
				<tr>
					<td class="r"><label for="">确认密码：</label></td>
					<td><input type="password" class="text required" id="confirmpassword" name="confirmpassword" value="" style="width:50em;"></td>
				</tr>
			</table>
		</form>	
	</div>

</body>
</html>