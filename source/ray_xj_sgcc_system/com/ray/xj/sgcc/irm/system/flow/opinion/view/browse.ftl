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

<table class="dataGrid">
<thead>
	<th width="100">时间</th>
	<th width="100">业务节点</th>
	<th width="100">意见人</th>
	<th>意见内容</th>
</thead>
<#list data.opinions as aobj>
<tr>
<tbody>
	<td>${aobj.createtime?string("yyyy-MM-dd hh:mm")}</td>
	<td>${aobj.runactname}</td>
	<td>${aobj.username}</td>
	<td>${aobj.opinion}</td>
</tr>
</tbody>
</#list>
</table>
</body>
</html>