<script type="text/javascript">
$("#bt_save").click(function() {page_save()});
$("#bt_close").click(function() {page_close()});

</script>

<script type="text/javascript">
var navigationJSON=[ {name:'系统管理',link:'${base}/module/irm/portal/portal/portal/portal_browse.action?ccate=admin'},{name:'门户菜单',link:'${base}/module/irm/system/portal/portalitem/portalitem_mainframe.action?organid=R0'}, {name:'新增门户菜单'}];
	function page_save()
	{
	    $("#inputform").attr('action','portalitem_insert.action');
	    $("#inputform").trigger('submit');
	}

	function page_close()
	{
		window.close();
//		window.location = "portalitem_browse.action?_searchname=system.portal.portalitem.browse&supid=${arg.supid}";
	}
	$(function(){
		$('#supid').val('${arg.supid}');
		$('#supname').val('${arg.supname}');
		$("#opentarget").val('mainIframe');
	});
	
	
</script>
