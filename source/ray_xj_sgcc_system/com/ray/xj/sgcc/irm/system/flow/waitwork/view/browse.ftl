<script type="text/javascript">

$("#bt_input").click(function() {page_input()});
$("#bt_del").click(function() {page_delete()});
$("#bt_close").click(function() {page_close()});
</script>


<script type="text/javascript">
var navigationJSON=[ {name:'系统管理',link:'${base}/module/irm/portal/portal/portal/portal_browse.action?ccate=admin'},{name:'所有待办'} ];

function page_view_row_click(keyvalue, index)
{
	currentkey = keyvalue;
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
	///alert(oids.join(','));
	
	  window.location = "waitwork_batchDelete.action?ids="+oids.join(',');
	}
}

function page_input()
{    
	url = "waitwork_input.action?viewtype=${arg.viewtype}";
	
	window.location = url;
}
function page_close()
{    
	
	window.close();
}

</script>