<html>
<head>
<meta charset="utf-8" />
<title></title>
<script type="text/javascript" src="${base}/themes/default/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${base}/themes/default/gex.js"></script>
<script type="text/javascript" src="${base}/themes/default/main.js"></script>
<style type="text/css">@import url(${base}/themes/default/main.css);</style>
</head>
<body>
<#--
<div id="fixedOp">
<button id="bt_addperson" class="btn2">确定</button>
</div>
-->

<table id="tb_persons" class="dataGrid">
<thead>
<tr>
<th width="1"><input class="checkbox"></th>
<th>姓名</th>
<th>部门</th>
<th>办公电话</th>
<th>职务</th>
		
</tr>
</thead>
<tbody>
<#list data.bean_owners as p>
<tr>
<td><input class="checkbox" value="0" name="pindex" ownerctx="${p.ownerctx}" cname="${p.cname}" ctype="${p.ctype}"></td>
<td>${p.cname}</td>
<td></td>
<td></td>
<td></td>
</tr>
</#list>
</tbody>
</table>

<script>

$("#tb_persons tbody").on("click",".checkbox", function(){
	var cs = $("#tb_persons tbody").find(".checkbox");
	cs.removeClass("checkboxChecked");
	cs.val("0");
	$(this).val("1");
	page_addperson();
})

$("#bt_addperson").click(function() {page_addperson()});

function page_addperson()
{
	window.parent.page_addperson();
}

// 自动加人
function page_autoaddperson()
{
	if($("#tb_persons tbody tr").length>=1)
	{
		// alert($("#tb_persons tbody tr:eq(0) .checkbox").attr("ownerctx"));
		$("#tb_persons tbody tr:eq(0) .checkbox").val("1");
		window.parent.page_addperson();
	}
}

page_autoaddperson();



</script>

</body>
</html>