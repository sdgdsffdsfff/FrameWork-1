<script type="text/javascript">
var navigationJSON=[ {name:'系统管理',link:'${base}/module/irm/portal/portal/portal/portal_browse.action?ccate=admin'},{name:'人员',link:'${base}/module/irm/system/organ/user/user_mainframe.action?organid=R0'}, {name:'角色'}];
</script>
<div id="pageContainer">
<form id="dataform" name="dataform" method="post" action="user_saverole.action">
	<table class="dataGrid" style="width:500px;">
	<thead>
		<tr>
			<th>中文名称</th><th>英文名称</th><th style="width:2em">角色</th>
		</tr>
	</thead>
	<tbody>
		<#assign roles=apage.result?sort_by("cname")>
		<#list roles as role>
			<tr>
				<td>${role.cname}</td> 
				<td>${role.name}</td> 
				<td>
					<#assign aclass="">
					<#assign check = "0">
					<#list data.userRoles as userole>
						<#if userole.rname == role.name>
							<#assign aclass = " checkboxChecked">
							<#assign check = "1">
						</#if>
					</#list>
					<input data-id="${role.id}" class="checkbox ${aclass}" name="index" value="${check}">
					<input type="hidden" name="role" value="${role.id}">
				</td>
			</tr>
		</#list>
		</tbody>
	</table>
<input id="id" name="id" type="hidden" value="${id}" />
<input id="organid" name="organid" type="hidden" value="${arg.organid}" />
<input id="result" name="result" type="hidden" value="" />
</form>
</div>

<script>
	$("#bt_save").click(function() {page_save()});
</script>

<script>	
function page_save()
{
	if(confirm("是否确定保存?"))
	{
		checkbox();
	    $("#dataform").attr("action","${base}/module/irm/system/organ/user/user_saverole.action");
	    $("#dataform").trigger('submit');
	}
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
