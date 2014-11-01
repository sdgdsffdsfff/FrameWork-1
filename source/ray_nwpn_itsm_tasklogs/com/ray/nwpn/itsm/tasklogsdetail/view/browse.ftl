
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

	url = "tasklogsdetail_locate.action?_searchname=tasklogsdetail.locate&tasklogsid=${arg.tasklogsid}";
	
	for(i=0;i<keynames.length;i++)
	{
		url += "&" + keynames[i] + "=" + keyvalues[i];
	}
	window.location = url;
}

function page_input()
{	
	url = "tasklogsdetail_input.action?_searchname=tasklogsdetail.input&tasklogsid=${arg.tasklogsid}";
	window.location = url;
}

</script>
