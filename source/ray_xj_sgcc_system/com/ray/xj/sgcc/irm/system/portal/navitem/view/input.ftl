<script type="text/javascript">
$("#bt_save").click(function() {page_save()});
$("#bt_close").click(function() {page_close()});
</script>

<script type="text/javascript">
var navigationJSON=[ {name:'系统管理',link:'${base}/module/irm/portal/portal/portal/portal_browse.action?ccate=admin'},{name:'导航菜单',link:'${base}/module/irm/system/portal/navitem/navitem_browse.action?_searchname=system.portal.navitem.browse'}, {name:'新增导航菜单'}];
	function page_save()
	{
	    $("#inputform").attr('action','navitem_insert.action');
	    $("#inputform").trigger('submit');
	}

	function page_close()
	{
		window.close();
	}
	
	
	
</script>
