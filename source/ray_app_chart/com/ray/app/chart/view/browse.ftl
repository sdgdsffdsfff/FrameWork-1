<script type="text/javascript" src="${base}/resource/default/script/ui.core.js"></script>
<script type="text/javascript" src="${base}/resource/default/script/ui.tabs.js"></script>

<script type="text/javascript">
var navigationJSON=[ {name:'系统管理',link:'${base}/module/irm/portal/portal/portal/portal_browse.action?ccate=admin'}, {name:'图表维护'}];
$("#bt_input").click(function() {page_input()});
$("#bt_del").click(function() {page_delete()});
$("#bt_query").click(function() {page_query()});
$("#bt_querysubmit").click(function() {page_querysubmit()});

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
	action = "${base}/module/app/system/chart/chart/chart_locate.action";
	url = action + "?_oldsearchname=chart.browse&_searchname=chart.locate";
	for(i=0;i<keynames.length;i++)
	{
		url += "&" + keynames[i] + "=" + keyvalues[i];
	}
	window.location = url;
}


function page_input()
{
	url = "${base}/module/app/system/chart/chart/chart_input.action?_searchname=chart.input";
	openwin(url,"chart",pub_width_large,pub_height_large);

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
		url = "${base}/module/app/system/chart/chart/chart_delete.action?id=" + oids.join(',');
		window.location = url;
	}

}
function page_query()
{
	div_queryinput.style.display = "block";
}
function page_querysubmit()
{
	form_view.submit();
}
</script>