
<script>
$("#bt_input").click(function() {page_input()});
$("#bt_del").click(function() {page_delete()});
$("#bt_flow").click(function() {page_flow()});
</script>

<script>
<#if arg.taskid=="">
$("#bt_flow").remove();
</#if>



function page_flow()
{
	var url = "${base}/module/app/jbpm/flow_showImage.action?taskid=${arg.taskid}";
	gPopWin(url,700,650,'流程图','iframe1');		
}


function page_view_row_click(keyvalue, index)
{
	currentkey = keyvalue;
}

function page_view_row_dbclick(keyname, keyvalue, index)
{
	currentkey = keyvalue;
	keynames = keyname.split(",");
	keyvalues = keyvalue.split(",");

	url = "tasklogs_locateframe.action?_searchname=tasklogs.locate&bussinessid=${arg.bussinessid}";
	
	for(i=0;i<keynames.length;i++)
	{
		url += "&" + keynames[i] + "=" + keyvalues[i];
	}
//	window.location = url;
}

function page_input()
{	
	url = "tasklogs_input.action?_searchname=tasklogs.input";
	window.location = url;
}

</script>
