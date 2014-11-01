<script type="text/javascript">
$("#bt_save").click(function() {page_save()});
$("#bt_close").click(function() {page_close()});
</script>

<script type="text/javascript">
	function page_save()
	{
	    $("#inputform").attr('action','role_insert.action');
	    $("#inputform").trigger('submit');
	}

	function page_close()
	{
		window.location = "role_browse.action?_searchname=system.organ.role.browse";
	}
	
	
	
</script>
