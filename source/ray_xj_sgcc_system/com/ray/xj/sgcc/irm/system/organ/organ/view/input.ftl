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
var navigationJSON=[ {name:'系统管理',link:'${base}/module/irm/portal/portal/portal/portal_browse.action?ccate=admin'},{name:'组织机构',link:'${base}/module/irm/system/organ/organ/organ_mainframe.action?parentorganid=R0'}, {name:'新增组织机构'}];
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
$("#bt_save").click(function() {page_save()});
$("#bt_close").click(function() {page_close()});
/////////////////////////////////////////
})
	
function page_save()
{
	$("#inputform").attr('action','organ_insert.action');
    $("#inputform").trigger('submit');
}

function page_close()
{
	//window.location = "${base}/module/irm/system/organ/organ/organ_browse.action?_searchname=system.organ.organ.browse&parentorganid=${arg.parentorganid}";
	window.opener.location.reload();
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
			<input type="hidden" id="_searchname" name="_searchname" value="">
			<input type="hidden" id="internal" name="internal" value="">
			<input type="hidden" id="cno" name="cno" value="">
			<input type="hidden" id="ordernum" name="ordernum" value="">
			<input type="hidden" id="parentorganid" name="parentorganid" value="${arg.parentorganid}">
		
			<table class="formGrid">
				<#--<tr>
					<td class="r"><label for="allname">机构部门完整名称：</label></td>
					<td colspan="3"><input type="text" readonly class="text " id="allname" name="allname" style="width:50em;" value="" ></td>
				</tr>-->
				<tr>
					<td class="r"><label for="cname">机构部门全称：</label></td>
					<td><input type="text" class="text required" id="cname" name="cname" style="width:20em;" value="" ></td>
				 
					<td class="r"><label for="shortname">机构部门简称：</label></td>
					<td><input type="text" class="text required" id="shortname" name="shortname" style="width:20em;" value="" ></td>
				</tr>
				
				<tr>
					<td class="r"><label for="orgname">机构名称：</label></td>
					<td><input type="text" class="text" id="orgname" name="orgname" style="width:20em;" value="" ></td>
					<td class="r"><label for="ctype">类型：</label></td>
					<td>
						<#assign finalvalue = "">
						<#assign finaltext = "">
						<#assign selectnum = "">
						<#assign options = stack.findValue("@com.ray.app.query.function.view.SQLSelect@get_data(\"select new map(t.dvalue as cvalue,t.dtext as ctext) from Dictionary t where t.dkey='app.system.organ.ctype'\")") >
						<#list options as aoption>
						<#if finalvalue =="">
							<#assign finalvalue = aoption.cvalue>
							<#assign finaltext = aoption.ctext>
						<#else>
							<#assign finalvalue = finalvalue + "||" + aoption.cvalue>
							<#assign finaltext = finaltext + "||" + aoption.ctext>
						</#if>
						</#list>
						<span class="selectSpan">
						<input type="hidden" name="ctype" value="" />
						<input class="select required" data-options="<#if finaltext!="">${finaltext}</#if>" data-values="<#if finalvalue!="">${finalvalue}</#if>" data-default="">
						</span>					
					</td>
				 
				 <#--
					<td class="r"><label for="allname">机构完整名称：</label></td>
					<td><input type="text" class="text" id="allname" name="allname" style="width:20em;" value="" ></td>
			-->
				</tr>			
				 
				<tr>
					<td class="r"><label for="phone">电话：</label></td>
					<td><input type="text" class="text " id="phone" name="phone" style="width:20em;" value="" ></td>
					<td class="r"><label for="email">电子邮件：</label></td>
					<td><input type="text" class="text " id="email" name="email" style="width:20em;" value="" ></td>
				</tr>			
				 
				<tr>
					 <td class="r"><label for="address">地址：</label></td>
					 <td colspan="3"><input type="text" class="text " id="address" name="address" style="width:49em;" value="" ></td>
				</tr>	
				<#--
				<tr>
					<td class="r"><label for="clevel">层次：</label></td>
					<td><input type="text" class="text " id="clevel" name="clevel" style="width:20em;" value="" ></td>
				 
					<td class="r"><label for="internal">内部编码：</label></td>
					<td><input type="text" class="text " id="internal" name="internal" style="width:20em;" value="" ></td>
				</tr>		
				 -->
				<tr>
					<td class="r"><label for="memo">备注：</label></td>
					<td colspan="3"><textarea input type="text" class="text " id="memo" name="memo" style="width:50em;" maxlength='500' value="" ></textarea></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>
