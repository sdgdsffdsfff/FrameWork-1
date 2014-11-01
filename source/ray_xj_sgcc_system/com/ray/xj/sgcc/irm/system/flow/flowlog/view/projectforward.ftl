<!DOCTYPE HTML>
<html>
<head>
<#include "/decorator/include/include.ftl">
<script>
$(function(){
/////////////////////////////////////////

	window.opener.$('#resultstate').val('${arg.resultstate}');
	window.opener.$('#forwardfrom').attr('action',"${arg.backurl}");
	window.opener.$('#forwardfrom').trigger('submit');
	window.close();

/////////////////////////////////////////
})

</script>
</head>
<body>
<form id="forwardfrom" method="post" target="_parent">
<input type="hidden" id="resultstate" name="resultstate" value="">

</form>
</body>
</html>