<script type="text/javascript">
$("#bt_save").click(function() {page_save()});
$("#bt_close").click(function() {page_close()});
</script>

<script type="text/javascript">
var navigationJSON=[ {name:'系统管理',link:'${base}/module/irm/portal/portal/portal/portal_browse.action?ccate=admin'},{name:'快捷项',link:'${base}/module/irm/system/portal/shortcut/shortcut_browse.action?_searchname=system.portal.shortcut.browse'}, {name:'新增快捷项'}];
	function page_save()
	{
	    $("#inputform").attr('action','shortcut_insert.action');
	    $("#inputform").trigger('submit');
	}

	function page_close()
	{
		window.close();
	}
	
	$(function(){
		$('#supid').val('${arg.supid}');
		$('#supname').val('${arg.supname}');
		$("#opentarget").val('mainIframe');
		if('${arg.supid}'=='R0')
		{
			$('#ctype').val('group');
		}
		else
		{
			$('#ctype').val('item');
		}
	});
	
</script>
