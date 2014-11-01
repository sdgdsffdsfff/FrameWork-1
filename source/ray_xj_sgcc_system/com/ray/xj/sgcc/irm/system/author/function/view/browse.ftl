<script type="text/javascript">
$("#bt_input").click(function() {page_input()});
$("#bt_del").click(function() {page_delete()});
$("#bt_findsubfunction").click(function() {page_findsubfunction()});
</script>

<script type="text/javascript">
var navigationJSON=[ {name:'系统管理',link:'${base}/module/irm/portal/portal/portal/portal_browse.action?ccate=admin'}, {name:'功能'}];
function page_delete()
{
	var oids=[];
	$('.dataGrid tbody .checkbox').each(function(j,k){		
		if($(this).val()==1){
			oids.push($(this).attr('data-id'))	
		}		
	})
	
	if(oids.length<=0){
		alert("请先选择要删除的数据！");
		return;
	}
	if(confirm("确实要删除选中的记录?"))
	{
		window.location = "function_delete.action?functionid=${arg.functionid}&ids="+oids.join(',');
	}
}

function page_input()
{
	url = "${base}/module/irm/system/author/function/function_input.action?_searchname=system.author.function.input&fnum=${arg.fnum}";
	//window.location = url;
	openwin(url,"inputfunction",pub_width_large,pub_height_large);
}

function page_findsubfunction()
{
	url = "${base}/module/irm/system/author/function/function_findsubfunction.action?_searchname=system.author.function.findsubfunction&fnum=${arg.fnum}";
	window.location = url;
}

</script>
