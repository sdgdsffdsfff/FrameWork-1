
<script type="text/javascript">
var navigationJSON=[ {name:'系统管理',link:'${base}/module/irm/portal/portal/portal/portal_browse.action?ccate=admin'}, {name:'角色'}];

$("#bt_input").click(function() {page_input()});
$("#bt_del").click(function() {page_delete()});
</script>

<script type="text/javascript">

function page_delete()
{
	var oids=[];
	$('.dataGrid tbody .checkbox').each(function(j,k){		
		if($(this).val()==1){
			oids.push($(this).attr('data-id'))	
		}		
	})
	
	if(oids.length<=0)
	{
		alert("请先选择要删除的数据！");
		return;
	}
	
	if(confirm("确实要删除选中的记录?"))
	{
		window.location = "${base}/module/irm/system/organ/role/role_delete.action?ids="+oids.join(',');
	}
}

function page_input()
{
	url = "${base}/module/irm/system/organ/role/role_input.action?_searchname=system.organ.role.input";
	openwin(url,"inputrole",pub_width_large,pub_height_large);
}

</script>