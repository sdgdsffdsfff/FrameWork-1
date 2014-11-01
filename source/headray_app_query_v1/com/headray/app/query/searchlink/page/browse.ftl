<script>
$("#bt_input").click(function() {page_input()});
$("#bt_del").click(function() {page_delete()});
$("#bt_arrange").click(function() {page_arrange()});
$("#bt_sort").click(function() {page_sort()});
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
	action = "invoke!locate.action";
	url = action + "?_oldsearchname=searchlink.browse&_searchname=searchlink.locate";
	for(i=0;i<keynames.length;i++)
	{
		url += "&" + keynames[i] + "=" + keyvalues[i];
	}
	//alert(url);
	window.location = url;
}

function page_input()
{
	url = "${base}/module/app/system/query/searchlink/invoke!input.action?_searchname=searchlink.input&searchid=${arg.searchid}";
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
		url = "${base}/module/app/system/query/searchlink/invoke!delete.action?searchlinkid=" + currentkey;
		//alert(url);
		window.location = url;
	}
	
}
function page_arrange()
{
	url = "${base}/module/app/system/query/searchlink/invoke!arrange.action?searchid=${arg.searchid}";
	//alert(url);
	window.location = url;
}
function page_sort()
{
	url = "${base}/module/app/system/query/searchlink/invoke!sort.action?searchid=${arg.searchid}&flag=S";
	//alert(url);
	openquerywindow(url);
}
</script>