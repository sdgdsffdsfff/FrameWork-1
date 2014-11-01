<script type="text/javascript">
$("#bt_save").click(function() {page_save()});
$("#bt_close").click(function() {page_close()});

</script>

<script type="text/javascript">
	function page_save()
	{
	    $("#inputform").attr('action','rolefunction_insert.action');
	    $("#inputform").trigger('submit');
	}

	function page_close()
	{
		window.location = "rolefunction_browse.action?_searchname=system.author.rolefunction.browse";
	}
	
	
	
</script>
