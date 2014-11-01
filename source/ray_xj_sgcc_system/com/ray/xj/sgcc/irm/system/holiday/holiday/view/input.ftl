<!DOCTYPE HTML>
<html>
<head>
<#include "/decorator/include/include.ftl">
<script type="text/javascript" src="${base}/themes/default/jquery.limit-1.2.source.js"></script>
<script type="text/javascript">


$(function(){
/////////////////////////////////////////

$("#bt_save").click(function() {page_save()});
$("#bt_del").click(function() {page_del()});
$("#bt_close").click(function() {page_close()});

/////////////////////////////////////////
})
function page_save()
	{
		
	    $("#aform").attr('action','${base}/module/irm/system/holiday/holiday/holiday_insert.action');
	    $("#aform").trigger('submit');
	}

function page_close()
	{
		window.close();
	}
	
jQuery(function($){
///////////////////////////////

$('#ctype').bind('gchange',function(){
	var intobj = $('#remain');
	var myobj = $('#hdate');
	if($('#ctype').val() =="非固定日期"){
		//console.log(intobj);
		intobj.css("display","none");
		myobj.css("display","");
	}else{
		intobj.css("display","");
		myobj.css("display","none");
	}	
})
///////////////////////////////
})

</script>



</head>
<body>
<div id="fixedOp">
<button id="bt_save">保存</button>
<button id="bt_close">关闭</button>
</div>

<div id="pageContainer">

<form id="aform" method="post">

		<table class="formGrid">
			<tr>
				<td class="r"><label for="htype">节假日名称：</label></td>
				<td>
					<input class="text required" id="htype" name="htype" value="" >
				</td>
			
				<td class="r"><label for="ctype">节假日类型：</label></td>
				<td>
				<#assign sblxs = stack.findValue("@com.ray.app.query.function.view.SQLSelect@get_data(\"select new map(t.dtext as ctext,t.dvalue as cvalue) from Dictionary t where t.dkey='app.system.hoilday.ctype'\")") >
				<#assign finalvalue = "">
				<#assign finaltext = "">
				<#list sblxs as ctype>
				<#if finalvalue =="">
				<#assign finalvalue = ctype.cvalue>
				<#assign finaltext = ctype.ctext>
				<#else>
				<#assign finalvalue = finalvalue + "||" + ctype.cvalue>
				<#assign finaltext = finaltext + "||" + ctype.ctext>
				</#if>
				</#list>
				<span class="selectSpan">
				<input type="hidden" name="ctype" value="" >
				<input class="select required" id="ctype" data-options="<#if finaltext!="">${finaltext}</#if>" data-values="<#if finalvalue!="">${finalvalue}</#if>" data-default="">
				</span>
				</td>
			</tr>
			<tr id="hdate">
					<td class="r"><label for="hdate">节假日时间：</label></td>
					<td>
						
						<input class="date required"  name="hdate" value="">
					</td>
			</tr>
			  <tr id="remain">
				<td class="r"><label for="cmonth">月份：</label></td>
				<td>
					<input class="required int" id="cmonth" name="cmonth" value="">
				</td>
				<td class="r"><label for="cday">日：</label></td>
				<td>
					<input class="required int" id="cday" name="cday" value="">
				</td>
			</tr>
			
			
			
		
		</table>
</form>
</div>
</body>
</html>
