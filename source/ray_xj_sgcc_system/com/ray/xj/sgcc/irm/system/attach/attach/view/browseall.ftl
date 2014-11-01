<script type="text/javascript">
var navigationJSON=[ {name:'系统管理',link:'${base}/module/irm/portal/portal/portal/portal_browse.action?ccate=admin'}, {name:'附件记录'}];
var buttonok = '${arg.buttonok}';
if(buttonok != 'ok')
{
	$("#bt_del").hide();
	//$("#bt_del").remove();
}
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
		alert("请选择要删除的记录！");
		return;
	}
	
	if(confirm("确实要删除选中的记录?"))
	{
		window.location = "${base}/module/irm/system/attach/attach/attach_deleteattachs.action?ids="+oids.join(',');
	}
}
</script>