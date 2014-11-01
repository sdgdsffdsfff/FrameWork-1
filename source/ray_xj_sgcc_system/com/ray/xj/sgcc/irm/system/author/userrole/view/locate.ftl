<script type="text/javascript" src="${base}/themes/default/jquery.js"></script>

<script type="text/javascript">
$("#bt_save").click(function() {page_save()});
$("#bt_close").click(function() {page_close()});

</script>

<script type="text/javascript">
	function page_save()
	{
		$("#locateform").attr('action','userrole_update.action');
	    $("#locateform").trigger('submit');
	}

	function page_close()
	{
		window.location = "userrole_browse.action?_searchname=system.author.userrole.browse";
	}
	
</script>
