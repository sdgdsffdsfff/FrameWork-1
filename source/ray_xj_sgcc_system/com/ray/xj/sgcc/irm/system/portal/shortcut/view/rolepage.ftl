<div id="pageContainer">
<form method="post" name="dataform" id="dataform" action="">
<input type="hidden" name="_searchname" value="${_searchname}">
<div class="oTable">
	<!--gGrid start-->
	<table class="dataGrid">
		<input type="hidden" id="id" name="id" value="${id}">
		<tr>
			<th width="1%" nowrap><nobr></nobr></th>
			<th align="center" width="18%" nowrap ondblclick="page_sort()">中文名称<span id="turntime" style="font-size:9px; font-weight:normal"></span></th>
			<th align="center" width="18%" nowrap ondblclick="page_sort()">英文名称<span id="turntime" style="font-size:9px; font-weight:normal"></span></th>
		</tr>

		<#list apage.result as role>
			<tr>
				<td>
					<#assign aclass="">
					<#assign check="0">
					<#list data.lists as rolesubject>
						<#if rolesubject.roleid == role.id>
							<#assign aclass=" checkboxChecked">
							<#assign check="1">
						</#if>
					</#list>
					<input class="checkbox ${aclass}" name="index" value="${check}">
					<input type="hidden" name="role" value="${role.id}">
				</td>
				<td>${role.cname}</td> 
				<td>${role.name}</td> 
			</tr>
		</#list>
	</table>
</div>

</form>
</div>
<script type="text/javascript">
	$("#bt_save").click(function() {page_save()});
</script>

<script type="text/javascript">	
	function page_save()
	{
		if(confirm("是否确定保存?"))
		{
			$("#dataform").attr("action","${base}/module/irm/system/portal/shortcut/shortcut_saverole.action");
			$("#dataform").trigger('submit');
		}
	}
</script>
