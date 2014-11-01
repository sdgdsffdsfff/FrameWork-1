<!DOCTYPE HTML>
<html>
<head>
<#include "/decorator/include/include.ftl">
<script type="text/javascript">
var navigationJSON=[ {name:'系统管理',link:'${base}/module/irm/portal/portal/portal/portal_browse.action?ccate=admin'},{name:'人员',link:'${base}/module/irm/system/organ/user/user_mainframe.action?organid=R0'}, {name:'修改人员'}];

$(function(){
/////////////////////////////////////////

$("#bt_save").click(function() {page_save()});
$("#bt_reset").click(function() {page_reset()});

/////////////////////////////////////////
})

function page_save()
{
    $("#locateform").attr('action','user_update.action');
    $("#locateform").trigger('submit');
}

function page_reset()
{
	window.location = "user_reset.action?id=${data.aobj.id}";
}
	
</script>
</head>
<body>
<div id="fixedOp">
  	<button id="bt_save">保存</button>
  	<button id="bt_reset">重置密码</button>
</div>
<div id="pageContainer">

<form id="locateform" method="post" action="/irm/">
<input type="hidden" id="_searchname" name="_searchname" value="">
<input type="hidden" id="password" name="password" value="${data.aobj.password}">
<input type="hidden" id="ownerorg" name="ownerorg" value="${data.aobj.ownerorg}">
<input type="hidden" id="id" name="id" value="${data.aobj.id}">
<input type="hidden" id="cno" name="cno" value="${data.aobj.cno}">

<table class="formGrid">
	<tr>
		<td class="r"><label for="cname">姓名：</label></td>
		<td><input type="text" class="text required" id="cname" name="cname" style="width:20em;" value="${data.aobj.cname}" ></td>
	 
		<td class="r"><label for="sex">性别：</label></td>
		<td><span class="selectSpan">
		<input type="hidden" name="sex" value="${data.aobj.sex}" />
		<input class="select required" data-options="男||女" data-values="M||F" data-default="${data.aobj.sex}">
		</span>
		</td>
	</tr>			
	 
	<tr>
		<td class="r"><label for="birthday">出生日期：</label></td>
		<td><input class="date " id="birthday" name="birthday" size="35" value="${(data.aobj.birthday?string('yyyy-MM-dd'))!}" ></td>
	 
		<td class="r"><label for="position">职位：</label></td>
		<td><input type="text" class="text required" id="position" name="position" style="width:20em;" value="${data.aobj.position}" ></td>
	</tr>			
	 
	<tr>
		<td class="r"><label for="phone">电话：</label></td>
		<td><input type="text" class="text required" id="phone" name="phone" style="width:20em;" value="${data.aobj.phone}" ></td>
	 
		<td class="r"><label for="mobile">手机：</label></td>
		<td><input type="text" class="text " id="mobile" name="mobile" style="width:20em;" value="${data.aobj.mobile}" ></td>
	</tr>			
	 
	<tr>
		<td class="r"><label for="email">电子邮件：</label></td>
		<td><input type="text" class="text " id="email" name="email" style="width:20em;" value="${data.aobj.email}" ></td>
	 
		<td class="r"><label for="address">地址：</label></td>
		<td><input type="text" class="text " id="address" name="address" style="width:20em;" value="${data.aobj.address}" ></td>
	</tr>			
	 
	<tr>
	<#if data.aobj.loginname !=''>
		<td class="r"><label for="loginname">登录名：</label></td>
		<td><input type="text" class="text required" id="loginname" name="loginname" style="width:20em;" value="${data.aobj.loginname}" readonly></td>
	<#else>
		<td class="r"><label for="loginname">登录名：</label></td>
		<td><input type="text" class="text required" id="loginname" name="loginname" style="width:20em;" value="${data.aobj.loginname}"></td>
	</#if>
		<td class="r"><label for="memo">备注：</label></td>
		<td><input type="text" class="text " id="memo" name="memo" style="width:20em;" value="${data.aobj.memo}" ></td>
	</tr>			
	 
	<tr>
		<td class="r"><label for="pinyin">姓名全拼：</label></td>
		<td><input type="text" class="text " id="pinyin" name="pinyin" style="width:20em;" value="${data.aobj.pinyin}" ></td>
	 
		<td class="r"><label for="pyjp">姓名简拼：</label></td>
		<td><input type="text" class="text " id="pyjp" name="pyjp" style="width:20em;" value="${data.aobj.pyjp}" ></td>
	</tr>			
	 
	<tr>
		<td class="r"><label for="pysm">姓名生母简拼：</label></td>
		<td><input type="text" class="text " id="pysm" name="pysm" style="width:20em;" value="${data.aobj.pysm}" ></td>
	 
		<td class="r"><label for="owneorgname">所属组织机构名称：</label></td>
		<td><input type="text" class="text " id="owneorgname" name="owneorgname" style="width:20em;" value="${data.aobj.owneorgname}" readonly></td>
	</tr>
	
	<tr>
		<td class="r"><label for="rtxaccount">rtx帐号：</label></td>
		<td><input type="text" class="text " id="rtxaccount" name="rtxaccount" style="width:20em;" value="${data.aobj.rtxaccount}" ></td>
		<td></td>
		<td></td>
	</tr>
<!--	
	<tr>
		<td class="r"><label for="isspecial">是否特送人员：</label></td>
			<td><span class="selectSpan">
			<input type="hidden"  id="isspecial" name="isspecial" style="width:20em;" value="${data.aobj.isspecial}" >
			<input class="select" data-options="是||否" data-values="1||0" data-default="${data.aobj.isspecial}">
			</td> 
		<td class="r"><label for="isduty">是否值班人员：</label></td>
			<td><span class="selectSpan">
			<input type="hidden" id="isduty" name="isduty" style="width:20em;" value="${data.aobj.isduty}" >
			<input class="select" data-options="是||否" data-values="1||0" data-default="${data.aobj.isduty}">
		</td>
	</tr>			
-->	 
</table>
</form>
</div>
</body>
</html>