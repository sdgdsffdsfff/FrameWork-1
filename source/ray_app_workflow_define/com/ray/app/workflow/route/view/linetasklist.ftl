<!DOCTYPE HTML>
<html>
<head>
<#include "/decorator/include/include.ftl">
<title>流程</title>
<script type="text/javascript" src="${base}/wfb/blueflowui/javascript/common.js"></script>
<script type="text/javascript">
jQuery(function($){
/////////////////////

$('#showTable tbody tr').live('click',function(){
	$('#showTable tbody tr').removeClass('selectedTr');
	$(this).addClass('selectedTr');
})

/////////////////////
})
</script>
<style>
#showTable tr.selectedTr td {background: #69c;color:#fff;}
</style>
</head>
<body>

<form id="flowForm">
<table width="100%" id="showTable" class="dataGrid">
<thead>
<tr>
<th>任务名称</th><th>是否必须</th> 
</tr>

</thead>
<tbody>

</tbody>

</table>
</form>

</body> 
</html>