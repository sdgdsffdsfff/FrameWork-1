<script type="text/javascript">
$("#bt_input").click(function() {page_input()});
$("#bt_del").click(function() {page_delete()});
$("#bt_arrange").click(function() {page_arrange()});
$("#bt_sort").click(function() {page_sort()});
</script>

<script type="text/javascript">
function page_view_row_click(keyvalue, index)
{
	currentkey = keyvalue;

}

function page_view_row_dbclick(keyname, keyvalue, index,searchid)
{
	currentkey = keyvalue;
	keynames = keyname.split(",");
	keyvalues = keyvalue.split(",");
	
	url = "searchlink_locate.action";
	url += "?_searchname=searchlink.locate&searchid=${searchid}";
	for(i=0;i<keynames.length;i++)
	{
		url += "&" + keynames[i] + "=" + keyvalues[i];
	}
    //alert(url);
	window.location = url;
}

function page_input()
{
	url = "searchlink_input.action?_searchname=searchlink.input&searchid=${searchid}";
	//alert(url);
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
		url = "searchlink_delete.action?searchid=${searchid}&searchlinkid=" + currentkey;
		//alert(url);
		window.location = url;
	}
}

function page_arrange()
{
	url = "searchlink_arrange.action?searchid=${searchid}";
	//alert(url);
	window.location = url;
}

function page_sort()
{
	url = "searchlink_sort.action?searchid=${searchid}&flag=S";
	//alert(url);
	openquerywindow(url);
}

</script>