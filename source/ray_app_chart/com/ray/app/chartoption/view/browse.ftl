<script type="text/javascript">
var navigationJSON=[ {name:'系统管理',link:'${base}/module/irm/portal/portal/portal/portal_browse.action?ccate=admin'}, {name:'图表项维护'}];
$("#bt_input").click(function() {page_input()});
$("#bt_del").click(function() {page_delete()});
</script>
<script>

function page_view_row_click(keyvalue, index)
{
	currentkey = keyvalue;
}

function page_view_row_dbclick(keyname, keyvalue, index)
{
	currentkey = keyvalue;
	keynames = keyname.split(",");
	keyvalues = keyvalue.split(",");
	action = "${base}/module/app/system/chart/chartoption/chartoption_locate.action";
	url = action + "?_searchname=chartoption.locate";
	for(i=0;i<keynames.length;i++)
	{
		url += "&" + keynames[i] + "=" + keyvalues[i];
	}
	window.location = url;
}


function page_input()
{
	url = "${base}/module/app/system/chart/chartoption/chartoption_input.action?_searchname=chartoption.input&id=${arg.id}";
	openwin(url,"chartoption",pub_width_large,pub_height_large);

}

function page_delete()
{
	 var oids=[];
    $('.dataGrid tbody .checkbox').each(function(j,k)
    {
        if($(this).val()==1)
        {
           oids.push($(this).attr('data-id'));
        }
    
    })

	if (oids.length<=0)
	{
		alert("请选择要删除的记录！");
		return;
	}
	
	if(confirm("确实要删除选中的记录吗？"))
	{
		url = "${base}/module/app/system/chart/chartoption/chartoption_delete.action?id=" + oids.join(',');
		window.location = url;
	}

}

</script>
