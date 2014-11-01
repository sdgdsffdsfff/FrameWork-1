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

var navigationJSON=[ {name:'系统管理',link:'${base}/module/irm/portal/portal/portal/portal_browse.action?ccate=config'}, {name:'填写意见',link:'${base}/module/irm/base/config/zdrjsx/zdrjsx_browse.action?_searchname=base.config.zdrjsx.browse'},{name:'填写意见'}];

$(function(){

$("#bt_add").click(function() {page_add()});
$("#bt_clear").click(function(){page_clear()});
$("#bt_close").click(function() {page_close()});
$("#bt_save").click(function(){page_save()});

})

jQuery(function($){
///////////////////////////////
$('textarea').each(function(){
	if(!isNaN($(this).attr('maxlength'))){
		$(this).after('<span class="textLimit" title="字数限制为：'+$(this).attr('maxlength')+'"><div class="numDiv"><span></span><span class="num"></span></div></span>');
		var textLeft=$(this).parent().find('.textLimit span.num')
		$(this).limit($(this).attr('maxlength'),textLeft);
		//textLeft.after('个字符').before('还剩')
	}
	})
///////////////////////////////
})
 

//页面加载时下拉框默认为public的意见列表
$(function(){
//////////////////////////////
	$("#opinionclass").val("public");
	$.post("opiniontemplate_opinionlist.action",
		  {cclass:"public"},
	  		function(data){
		  	$("#opinionlist").empty();
		  	$("#opinionlist").append(data);
		  }	);
		  
	
//////////////////////////
})

//点击确定按钮的操作
function page_save()
{
	var opinion = $("#opinion").val();
	var opinionclass=$("#opinionclass").val();
	var runflowkey=$("#runflowkey").val();
	var runactkey=$("#runactkey").val();
	var runactname=$("#runactname").val();
	var actdefid=$("#actdefid").val();
	var dataid=$("#dataid").val();
	
	$.post("${base}/module/irm/system/flow/opinion/opinion_save.action",
				{opinionclass:opinionclass,opinion:opinion,runflowkey:runflowkey,
				runactkey:runactkey,runactname:runactname,actdefid:actdefid,dataid:dataid},
				function(data){
					eval("var data="+data);
					if(data == true){
						alert("填写意见成功！");
					}else{
						alert("填写意见失败！");
					}				
						window.close();
						window.opener.location.reload();
				});
}

//添加到意见列表
function page_add()
{	
	var opinionclass=$("#opinionclass").val();
	var opinion = $("#opinion").val();
	if(opinion == null || opinion == ""){
		alert("请填写意见");
		return;
	}
	$.post("opiniontemplate_insertopinion.action",
				{cclass:opinionclass,opinion:opinion},
				function(data){
					eval("var data="+data);
					if(data == true){
						changeopinionlist(opinionclass);
						alert("添加意见成功！");
					}else{
						alert("添加意见失败！");
					}
				});
				
}

//清空当前意见
function page_clear()
{
	$("#opinion").val("");
}

function page_close(){
	window.close();
}

//单选按钮触发事件
function changeopinionlist(cclass)
{
	$("#opinionclass").val(cclass);
	$.post("opiniontemplate_opinionlist.action",
			  {cclass:cclass},
		  		function(data){
			  	$("#opinionlist").empty();
			  	$("#opinionlist").append(data);
			  }	);
}

//下拉框触发事件
function changeopinion()
{	
	var opinion = $("#opinionlist").val();
	$("#opinion").val(opinion);
}
</script>
</head>
<body>

<div id="fixedOp">
	<button id="bt_save" class="btn2">保存意见</button>
	<button id="bt_clear">清除意见</button>
	<button id="bt_add">添加意见列表</button>
	<button id="bt_close" class="btn2">关闭</button>
</div>

<div id="pageContainer">
<form id="locateform" method="post" action="/irm/">
<input type="hidden" id="runflowkey" name="runflowkey" value="${data.runflowkey}">
<input type="hidden" id="runactkey" name="runactkey" value="${data.runactkey}">
<input type="hidden" id="runactname" name="runactname" value="${data.runactname}">
<input type="hidden" id="actdefid" name="actdefid" value="${data.actdefid}">
<input type="hidden" id="dataid" name="actdefid" value="${data.dataid}">
<table class="formGrid">	
	<tr>
		<td class="r"><label for="opinionclass">选择：</label></td>
		<td>
			<span class="allRadios">
					<input type="hidden" name="opinionclass" id="opinionclass" value=""/>
					<#assign opinionclasses = stack.findValue("@com.ray.app.query.function.view.SQLSelect@get_data(\"select new map(t.dtext as ctext,t.dvalue as cvalue) from Dictionary t where t.dkey='system.opinion' \")") >
					<#list opinionclasses as opinionclass>
					<label><input class="radio <#if opinionclass.cvalue =='public'>radioChecked</#if>" value="${opinionclass.cvalue}" onclick="changeopinionlist('${opinionclass.cvalue}')">${opinionclass.ctext}</label>
					</#list>
				</span>
			</span>
		</td>
		<td class="r">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>		
	<tr>
		<td class="r hidden"><label for="opinionlist">意见列表：</label></td>
		<td>
			<input type="hidden" name="curopinion" id="curopinion" value=""/>	
			<select id="opinionlist" name="opinionlist" style="width:200px" onchange="changeopinion();">     
			 	<option value="">请选择</option>  
			</select>
		</td>
		<td class="r">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>	
	<tr>
		<td class="r">
			<label for="opinion">当前意见：</label>
		</td>
		<td colspan="3">
			<textarea maxlength="500" value="" id="opinion" name="opinion" maxlength="100">同意</textarea>
		</td>
		<td class="r">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>	
</table>
</form>
	<div>

	</div>
</div>
</body>
</html>