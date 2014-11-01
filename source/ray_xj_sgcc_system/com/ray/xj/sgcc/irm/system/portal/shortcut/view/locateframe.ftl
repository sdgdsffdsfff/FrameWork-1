<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>运维资源管理系统-right</title>
<script type="text/javascript" src="${base}/themes/default/jquery.js"></script>
<script type="text/javascript" src="${base}/themes/default/gex.js"></script>
<script type="text/javascript" src="${base}/themes/default/content.js"></script>
<style type="text/css">@import url(${base}/themes/default/content.css);</style>
<style type="text/css">
html,body {height:100%;margin:0;padding:0;overflow:hidden;}
table {height:100%;}
</style>
</head>
<body class="other">

<table class="layoutTable">
<tr style="height:1%"><td><h1><span class="other">当前位置：快捷项管理</span></h1></td></tr>
<tr>
<td>
<ul class="gTabs">
<li class="current icon info url|shortcut_locate.action?_searchname=system.portal.shortcut.locate&id=${arg.id}">基本信息</li>
<li class="url|shortcut_browserole.action?_searchname=system.portal.shortcut.browserole&id=${arg.id}">角色信息</li>

</ul>
</td>
</tr>

<tr style="height:90%;">
<td>
<iframe id="iframe1" src="shortcut_locate.action?_searchname=system.portal.shortcut.locate&id=${arg.id}" style="width:100%;height:100%;" frameborder="0" allowtransparency="true"></iframe>
</td>
</tr>

</table>
</body>
</html>
