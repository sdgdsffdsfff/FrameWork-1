<script type="text/javascript">
var navigationJSON=[ {name:'系统管理',link:'${base}/module/irm/portal/portal/portal/portal_browse.action?ccate=admin'},{name:'数据字典分类',link:'${base}/module/app/system/dictionary/dictionaryclass/dictionaryclass_mainframe.action?supid=R0'}, {name:'修改数据字典分类'}];

$("#bt_save").click(function() {page_save()});
$("#bt_close").click(function() {page_close()});

</script>

<script type="text/javascript">
	function page_save()
	{
		$("#locateform").attr('action','dictionaryclass_update.action');
	    $("#locateform").trigger('submit');
	}

	
	function page_close()
	{
		//window.location = "${base}/module/app/system/dictionary/dictionaryclass/dictionaryclass_browse.action?_searchname=system.dictionary.dictionaryclass.browse&supid=${arg.supid}";
		window.close();
	}
</script>