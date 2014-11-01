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
<script   type="text/javascript" src="${base}/wfb/blueflowui/javascript/common.js"></script>
<SCRIPT type="text/javascript">
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
 var fs=0;

function OpenFlowForm(oDestination,oDestination1) {
    oField1 = oDestination;
    oField2 = oDestination1; 
    wffclass = window.open("${base}/module/app/system/workflow/bflowclass/bflowclass_selecttask.action", "wffclass", "resizable=yes,dependent=yes,scrollbars=yes,left=90,top=45,width=" + screen.width * .7 + ",height=" + screen.height * .5);    
    
    wffclass.focus();
}
function OpenFlowClass(oDestination,oDestination1) {
    oField1 = oDestination;
    oField2 = oDestination1; 
    wfclass = window.open("${base}/module/app/system/workflow/bflowclass/bflowclass_selectbflowclass.action", "wfclass", "resizable=yes,dependent=yes,scrollbars=yes,left=90,top=45,width=" + screen.width * .7 + ",height=" + screen.height * .5);    
    wfclass.focus();
}

</SCRIPT>
</head>
<body>
<form id="flowForm"><input type="hidden" name="objid"  value="" />
<table>
	<tr>

		<td align="left" valign="top">
		<textarea  name="objname" style="width:40em;height:20em;" maxlength="500"></textarea>
		 
		</td>
	</tr>
</table>

</form>
<SCRIPT type="text/javascript">
//document.forms[0].formid.value=window.parent.document.all.formid.value;
//document.forms[0].formname.value=window.parent.document.all.formname.value;



</SCRIPT>
</body>

</html>

