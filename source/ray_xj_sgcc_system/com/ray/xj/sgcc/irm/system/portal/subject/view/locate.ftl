<script type="text/javascript">
$("#bt_save").click(function() {page_save()});
$("#bt_close").click(function() {page_close()});
</script>

<script type="text/javascript">
var navigationJSON=[ {name:'系统管理',link:'${base}/module/irm/portal/portal/portal/portal_browse.action?ccate=admin'},{name:'门户栏目',link:'${base}/module/irm/system/portal/subject/subject_browse.action?_searchname=system.portal.subject.browse'}, {name:'修改门户栏目'}];
	function page_save()
	{
	    $("#locateform").attr('action','subject_update.action');
	    $("#locateform").trigger('submit');
	}

	function page_close()
	{
		//window.location = "subject_browse.action?_searchname=system.portal.subject.browse&bdzbz=${data.bdzbz}";
		window.opener.location.reload();
		window.close();
	}
	
	$('#bdzm').val('${data.bdzbz}');
	
</script>
