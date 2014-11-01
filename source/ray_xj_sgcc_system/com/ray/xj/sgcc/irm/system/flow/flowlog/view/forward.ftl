<!DOCTYPE HTML>
<html>
<head>
<#include "/decorator/include/include.ftl">
<script>
$(function(){
///////////////
<#if arg.backurl??>
var url="${arg.backurl}";
<#if arg.ctype??>

	if(url.indexOf("?")>0)
	{
		url=url+"&ctype=${arg.ctype}"
	}
	else
	{
		url=url+"?ctype=${arg.ctype}"
	}
</#if>
<#if arg.resultstate=="finish" && arg.businesstype=="change">
	$('#resultstate').val('${arg.resultstate}');
	$('#forwardfrom').attr('action',url);
	$('#forwardfrom').trigger('submit');
	window.close();
<#else>
	window.opener.$('#resultstate').val('${arg.resultstate}');
	window.opener.$('#forwardfrom').attr('action',url);
	window.opener.$('#forwardfrom').trigger('submit');
	alert("已成功将变更单转发给了"+"${arg.dusername}"+"！");
	window.close();
</#if>

</#if>
///////////////
});
</script>
</head>
<body>
<form id="forwardfrom" method="post" target="_parent">
<input type="hidden" id="resultstate" name="resultstate" value="">
</form>
</body>
</html>