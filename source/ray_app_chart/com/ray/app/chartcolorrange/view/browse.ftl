<script>
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
	action = "${base}/module/app/system/chart/chartcolorrange/chartcolorrange_locate.action";
	url = action + "?_searchname=chartcolorrange.locate";
	for(i=0;i<keynames.length;i++)
	{
		url += "&" + keynames[i] + "=" + keyvalues[i];
	}
	window.location = url;
}


function page_input()
{
	url = "${base}/module/app/system/chart/chartcolorrange/chartcolorrange_input.action?_searchname=chartcolorrange.input&id=${arg.id}";
	window.location = url;
}

function page_delete()
{
	if (typeof(currentkey)== "undefined")
	{
		alert("请选择要删除的记录！");
		return;
	}
	
	if(confirm("确实要删除选中的记录吗？"))
	{
		url = "${base}/module/app/system/chart/chartcolorrange/chartcolorrange_delete.action?pid=${arg.id}&id=" + currentkey;
		window.location = url;
	
	}

}

</script>
