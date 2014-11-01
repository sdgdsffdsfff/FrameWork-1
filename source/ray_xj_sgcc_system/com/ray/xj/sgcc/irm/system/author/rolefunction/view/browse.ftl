<script type="text/javascript">
$("#bt_input").click(function() {page_input()});
$("#bt_del").click(function() {page_delete()});
</script>

<script type="text/javascript">

function page_view_row_click(keyvalue, index)
{
	currentkey = keyvalue;
}

function page_view_row_dbclick(keyname, keyvalue, index)
{
	currentkey = keyvalue;
	keynames = keyname.split(",");
	keyvalues = keyvalue.split(",");


	url = "${base}/module/irm/system/author/rolefunction/rolefunction_locate.action?_searchname=system.author.rolefunction.locate";

	for(i=0;i<keynames.length;i++)
	{
		url += "&" + keynames[i] + "=" + keyvalues[i];
	}

	window.location = url;
}

function page_delete()
{
	if(typeof(currentkey)=="undefined")
	{
		alert("请选择要删除的记录！");
		return;
	}
	if(confirm("确实要删除选中的记录?"))
	{
		url = "${base}/module/irm/system/author/rolefunction/rolefunction_delete.action?id="+currentkey;
		window.location = url;
	}
}

function page_input()
{
	url = "${base}/module/irm/system/author/rolefunction/rolefunction_input.action?_searchname=system.author.rolefunction.input";
	window.location = url;
}

</script>