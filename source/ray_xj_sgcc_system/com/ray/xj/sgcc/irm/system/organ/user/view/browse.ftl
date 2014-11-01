
<script type="text/javascript">
$("#bt_input").click(function() {page_input()});
$("#bt_del").click(function() {page_delete()});
$("#bt_useable").click(function() {page_useable()});
$("#bt_unused").click(function() {page_unused()});

//如果在根节点上新增人员，后台会报错，所以在此过滤掉按钮
<#if arg.organid='R0'>
$("#bt_input").remove();
$("#bt_del").remove();
$("#bt_useable").remove();
$("#bt_unused").remove();
</#if>
</script>

<script type="text/javascript">
var navigationJSON=[ {name:'系统管理',link:'${base}/module/irm/portal/portal/portal/portal_browse.action?ccate=admin'}, {name:'人员'}];
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

		window.location = "user_delete.action?organid=${arg.organid}&ids="+oids.join(',');
	}
}

function page_input()
{

	url = "${base}/module/irm/system/organ/user/user_input.action?_searchname=system.organ.user.input&organid=${arg.organid}";
	openwin(url,"inputuser",pub_width_large,pub_height_large);
}

function page_useable()
{
	var oids=[];
	$('.dataGrid tbody .checkbox').each(function(j,k){		
		if($(this).val()==1){
			oids.push($(this).attr('data-id'))	
		}		
	})
	
	if(oids.length<=0)
	{
		alert("请先选择要启用的数据！");
		return;
	}
	
	if(confirm("确实要启用选中的记录?"))
	{

		window.location = "user_useable.action?organid=${arg.organid}&ids="+oids.join(',');
	}

}
function page_unused()
{
	var oids=[];
	$('.dataGrid tbody .checkbox').each(function(j,k){		
		if($(this).val()==1){
			oids.push($(this).attr('data-id'))	
		}		
	})
	
	if(oids.length<=0)
	{
		alert("请先选择要禁用的数据！");
		return;
	}
	
	if(confirm("确实要禁用选中的记录?"))
	{

		window.location = "user_unused.action?organid=${arg.organid}&ids="+oids.join(',');
	}

}
</script>