<script type="text/javascript">
$("#bt_save").click(function() {page_save()});
$("#bt_close").click(function() {page_close()});

</script>

<script type="text/javascript">
var navigationJSON=[ {name:'系统管理',link:'${base}/module/irm/portal/portal/portal/portal_browse.action?ccate=admin'},{name:'功能',link:'${base}/module/irm/system/author/function/function_mainframe.action?organid=R0'}, {name:'修改功能'}];
	function page_save()
	{
	    $("#locateform").attr('action','function_update.action');
	    $("#locateform").trigger('submit');
	}

	function page_close()
	{
		//window.location = "function_browse.action?_searchname=system.author.function.browse&fnum=${arg.fnum}";
		window.opener.location.reload();
		window.close();
	}
	
</script>
