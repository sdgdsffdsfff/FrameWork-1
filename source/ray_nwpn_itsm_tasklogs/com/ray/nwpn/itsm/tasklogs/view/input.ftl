
<script>
$("#bt_save").click(function() {page_save()});
$("#bt_close").click(function() {page_close()});
</script>

<script>
	function page_save()
	{
		form.action="tasklogs_insert.action";
		form.submit();
	}

	function page_close()
	{
		window.location = "tasklogs_browse.action?_searchname=tasklogs.browse";
	}
</script>
