<script type="text/javascript">
$("#bt_save").click(function() {page_save()});
$("#bt_close").click(function() {page_close()});
</script>

<script type="text/javascript">
var navigationJSON=[ {name:'系统管理',link:'${base}/module/irm/portal/portal/portal/portal_browse.action?ccate=admin'},{name:'门户栏目',link:'${base}/module/irm/system/portal/subject/subject_browse.action?_searchname=system.portal.subject.browse'}, {name:'新增门户栏目'}];
	function page_save()
	{
	    $("#inputform").attr('action','subject_insert.action');
	    $("#inputform").trigger('submit');
	}

	function page_close()
	{
		window.opener.location.reload();
		window.close();
	}
	
	
	
</script>
