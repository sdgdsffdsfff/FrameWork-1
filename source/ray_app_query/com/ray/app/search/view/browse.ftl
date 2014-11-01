<script type="text/javascript">
$("#bt_input").click(function() {page_input()});
$("#bt_del").click(function() {page_delete()});
$("#bt_arrange").click(function() {page_arrange()});
$("#bt_query").click(function() {page_query()});
$("#bt_querysubmit").click(function() {page_querysubmit()});
</script>

<script type="text/javascript">
var navigationJSON=[ {name:'系统管理',link:'${base}/module/irm/portal/portal/portal/portal_browse.action?ccate=admin'}, {name:'通用查询'}];
function page_view_row_click(keyvalue, index)
{
	currentkey = keyvalue;
}

function page_view_row_dbclick(keyname, keyvalue, index)
{
	currentkey = keyvalue;
	keynames = keyname.split(",");
	keyvalues = keyvalue.split(",");

	url = "search_locateframe.action";
	url += "?_temp=temp";
	
	for(i=0;i<keynames.length;i++)
	{
		url += "&" + keynames[i] + "=" + keyvalues[i];
	}
	
	// alert(url);
	window.location = url;
}

function page_input()
{
	url = "search_input.action?_searchname=search.input";
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
		url = "search_delete.action?searchid=" + currentkey;
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
	form_view.trigger('submit');
}
</script>