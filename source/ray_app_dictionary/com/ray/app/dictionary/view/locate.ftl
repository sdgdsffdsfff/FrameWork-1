<script type="text/javascript" src="${base}/themes/default/jquery.js"></script>

<script type="text/javascript">
var navigationJSON=[ {name:'系统管理',link:'${base}/module/irm/portal/portal/portal/portal_browse.action?ccate=admin'},{name:'数据字典',link:'${base}/module/app/system/dictionary/dictionary/dictionary_mainframe.action?dclassid=R0'}, {name:'修改数据字典'}];

$("#bt_save").click(function() {page_save()});
$("#bt_close").click(function() {page_close()});

</script>

<script type="text/javascript">
	function page_save()
	{
		$("#locateform").attr('action','dictionary_update.action?dclassid=${arg.dclassid}');
	    $("#locateform").trigger('submit');
	}

	function page_close()
	{
		//window.location = "dictionary_browse.action?_searchname=system.dictionary.dictionary.browse&dclassid=${arg.dclassid}";
		window.opener.location.reload();
		window.close();
	}
	
</script>