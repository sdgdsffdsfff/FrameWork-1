<script type="text/javascript">
$("#bt_save").click(function() {page_save()});
$("#bt_close").click(function() {page_close()});
</script>

<script type="text/javascript">
var navigationJSON=[ {name:'系统管理',link:'${base}/module/irm/portal/portal/portal/portal_browse.action?ccate=admin'},{name:'快捷项',link:'${base}/module/irm/system/portal/shortcut/shortcut_browse.action?_searchname=system.portal.shortcut.browse'}, {name:'修改快捷项'}];
	function page_save()
	{
	    $("#locateform").attr('action','shortcut_update.action');
	    $("#locateform").trigger('submit');
	}

	function page_close()
	{
		//window.location = "shortcut_browse.action?_searchname=system.portal.shortcut.browse&supid=${data.aobj.supid}";
		window.close();
		window.opener.location.reload();
	}
	
	$('#bdzm').val('${data.bdzbz}');
	
</script>
