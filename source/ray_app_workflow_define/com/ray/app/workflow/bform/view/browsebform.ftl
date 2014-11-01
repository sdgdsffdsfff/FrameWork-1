<script type="text/javascript">
var navigationJSON=[ {name:'系统设置',link:'${base}/module/irm/portal/portal/portal/portal_browse.action?ccate=admin'}, {name:'流程表单信息'}];

$('#bt_input').click(function (){page_input()});
$('#bt_del').click(function (){page_delete()});
</script>
<script type="text/javascript">
function page_input()
{
	url="bform_input.action";
	openwin(url,'inputbform',pub_width_mid,pub_height_mid);
}

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
		window.location = "bform_delete.action?ids="+oids.join(',');
	}
}
</script>