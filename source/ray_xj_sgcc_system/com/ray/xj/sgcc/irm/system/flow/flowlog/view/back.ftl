<!DOCTYPE HTML>
<html>
<head>
<#include "/decorator/include/include.ftl">
<script>
$(function(){
///////////////
<#if arg.backurl??>
window.opener.$('#resultstate').val('${arg.resultstate}');
window.opener.$('#forwardfrom').attr('action','${arg.backurl}');
window.opener.$('#forwardfrom').trigger('submit');
window.close();
</#if>
///////////////
});
</script>
</head>
<body>
</body>
</html>