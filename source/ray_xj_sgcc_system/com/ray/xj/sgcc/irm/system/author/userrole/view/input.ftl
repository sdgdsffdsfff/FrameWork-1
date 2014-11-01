<script type="text/javascript" src="${base}/themes/default/jquery.js"></script>

<script type="text/javascript">
$("#bt_save").click(function() {page_save()});
$("#bt_close").click(function() {page_close()});

</script>

<script type="text/javascript">
	function page_save()
	{
	    $("#inputform").attr('action','${base}/module/irm/system/author/userrole/userrole_insert.action');
	    $("#inputform").trigger('submit');
	}

	function page_close()
	{
		window.location = "${base}/module/irm/system/author/userrole/userrole_browse.action?_searchname=system.author.userrole.browse";
	}
	
</script>
