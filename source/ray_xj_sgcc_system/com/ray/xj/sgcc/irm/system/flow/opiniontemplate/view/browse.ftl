<script type="text/javascript">
$("#bt_input").click(function() {page_input()});
$("#bt_del").click(function() {page_delete()});
</script>

<script type="text/javascript">
var navigationJSON=[ {name:'系统管理',link:'${base}/module/irm/portal/portal/portal/portal_browse.action?ccate=admin'}, {name:'意见管理'}];
function page_view_row_click(keyvalue, index)
{
	currentkey = keyvalue;
}

function page_view_row_dbclick(keyname, keyvalue, index)
{
	currentkey = keyvalue;
	keynames = keyname.split(",");
	keyvalues = keyvalue.split(",");


	url = "${base}/module/irm/system/flow/opiniontemplate/opiniontemplate_locate.action?a=a";
	

	for(i=0;i<keynames.length;i++)
	{
		url += "&" + keynames[i] + "=" + keyvalues[i];
	}

	window.location = url;
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
	
	  window.location = "opiniontemplate_delete.action?ids="+oids.join(',');
	}
}

function page_input()
{    
	openwin('opiniontemplate_input.action','projectclassinput',pub_width_large,pub_height_large);
}

</script>