<#assign apage = {"currentpage":data.page.pageNo,"pagesize":data.page.pageSize,"maxpage":data.page.totalPages,"size":data.page.result?size,"hasnext":data.page.hasNext,"haspre":data.page.hasPre,"rowcount":data.page.totalCount,"sorttag":data.page.order,"sortfield":data.page.orderBy,"result":data.page.result}>
<#assign vo = data.vo>
<#import "com/ray/app/query/view/pageselect_macros.ftl" as pub_macros>

<!DOCTYPE HTML>
<html>
<head>
<#include "/decorator/include/include.ftl">
</head>
<body>

<div id="fixedOp">
  	<button id="bt_save">保存</button>
</div>
<div id="pageContainer">
<form method="post" name="dataform" id="dataform" action="">
<input type="hidden" name="_searchname" value="${_searchname}">
<input type="hidden" name="page" value="${page}">
<input type="hidden" name="_sortfield" value="${_sortfield}" />
<input type="hidden" name="_sorttag" value="${_sorttag}" />
<input type="hidden" name="step" value="${step}" />

<input type="hidden" name="roleid" value="${arg.roleid}">
<input id="result" name="result" type="hidden" value="" />

	<div class="oTable">
		<table class="dataGrid">
		<thead>
		<tr>
			<th class="toggleCheckboxAll" width="1"><input class="checkbox" /></th>
			<th>快捷项（组）标题</th>
			<th>快捷链接地址</th>
		</tr>
	</thead>
	<tbody>
		<!-- 显示数据-->
		<#list data.page.result as shortcut>
			<tr>
				<td>
					<#assign aclass = "">
					<#assign check = "0">	
					<#list data.lists as roleshortcut>
						<#if roleshortcut.shortcutid == shortcut.id>
							<#assign aclass = " checkboxChecked">
							<#assign check = "1">
						</#if>
					</#list>
					<input data-id="${shortcut.id}" class="checkbox ${aclass}" name="index" value="${check}">
					<input type="hidden" name="oshortcutid" value="${shortcut.id}">
					<input type="hidden" name="shortcutid" value="${shortcut.id}">
				</td>
				<td>${shortcut.cname}</td> 
				<td>${shortcut.url}</td> 
			</tr>
		</#list>
		</tbody>
		</table>
		<input type="hidden" name="roleid" value="${arg.roleid}">
		<input id="result" name="result" type="hidden" value="" />
</form>

	<form method="post" name="form_view" id="form_view" action="${base}${vo.formaction}">
	<input type="hidden" name="_searchname" value="${_searchname}">
	<input type="hidden" name="page" value="${page}">
	<input type="hidden" name="_sortfield" value="${_sortfield}" />
	<input type="hidden" name="_sorttag" value="${_sorttag}" />
	<input type="hidden" name="step" value="${step}" />
	<input type="hidden" name="roleid" value="${arg.roleid}">
	
	</form>
	
</div>


<script type="text/javascript">
	$("#bt_save").click(function() {page_save()});
	$("#bt_close").click(function() {page_close()});
</script>

<script type="text/javascript">
var navigationJSON=[ {name:'系统管理',link:'${base}/module/irm/portal/portal/portal/portal_browse.action?ccate=admin'},{name:'角色',link:'${base}/module/irm/system/organ/role/role_browse.action?_searchname=system.organ.role.browse'}, {name:'角色快捷项'}];

function page_save()
{
	if(confirm("是否确定保存?"))
	{
		checkbox();
		$("#dataform").attr("action","${base}/module/irm/system/organ/role/role_saveshortcut.action");
  		$("#dataform").trigger('submit');
	}
}

function page_close()
{
	window.location = "${base}/module/irm/system/organ/role/role_browse.action?_searchname=system.organ.role.browse";
}

$(function()
{
	$('.dataGrid .checkbox').click(
	function()
	{
		setTimeout(function()
		{
			checkbox();
		},200);
	});
});

function checkbox()
{
	var oArray=[];
	$('.dataGrid .checkbox').each(
	function()
	{
		if($(this).attr('data-id')==undefined)
		{
			return;
		}
	
		if($(this).hasClass('checkboxChecked'))
		{
			if($(this).attr('data-id')!='')
			{
				oArray.push($(this).attr('data-id'));
			}
		}	
	});
	$('#result').val(oArray.join(','))
}	
</script>
</body>
</html>
