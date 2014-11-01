
<script>
$("#bt_save").click(function() {page_save()});
$("#bt_close").click(function() {page_close()});
</script>

<script>
	function page_save()
	{
		form.action="tasklogsdetail_insert.action?tasklogsid=${arg.tasklogsid}";
		form.submit();
	}

	function page_close()
	{
		window.location = "tasklogsdetail_browse.action?_searchname=tasklogsdetail.browse&tasklogsid=${arg.tasklogsid}";
	}
</script>
