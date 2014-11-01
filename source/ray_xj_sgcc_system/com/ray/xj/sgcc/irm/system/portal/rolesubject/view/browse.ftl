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

	url = "${base}/module/irm/system/portal/rolesubject/rolesubject_browsesubject.action?_searchname=system.portal.rolesubject.locate";
	
	for(i=0;i<keynames.length;i++)
	{
		url += "&" + keynames[i] + "=" + keyvalues[i];
	}
	window.location = url;
}
</script>
