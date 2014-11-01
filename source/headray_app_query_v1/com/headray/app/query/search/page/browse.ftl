<script>
$("#bt_input").click(function() {page_input()});
$("#bt_del").click(function() {page_delete()});
$("#bt_arrange").click(function() {page_arrange()});
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
	action = "${base}/module/app/system/query/search/invoke!forward.action";
	url = action + "?_searchname=search.locate&_forward=locateframe-success&_argnames=searchid";
	for(i=0;i<keynames.length;i++)
	{
		url += "&" + keynames[i] + "=" + keyvalues[i];
	}
	//alert(url);
	window.location = url;
}

function page_input()
{
	url = "${base}/module/app/system/query/search/invoke!input.action?_searchname=search.input";
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
		url = "${base}/module/app/system/query/search/invoke!delete.action?searchid=" + currentkey;
		window.location = url;
	}
	
}
function page_arrange()
{
	if (typeof(currentkey)== "undefined")
	{
		alert("请选择要整理的记录！");
		return;
	}
	if(confirm("确实要整理选中的记录吗？"))
	{
		url = "${base}/module/app/system/query/search/invoke!arrange.action?searchid="+ currentkey;
		//alert(url);
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