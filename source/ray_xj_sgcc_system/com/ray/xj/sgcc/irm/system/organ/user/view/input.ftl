<script type="text/javascript">
$("#bt_save").click(function() {page_save()});
$("#bt_close").click(function() {page_close()});
</script>

<script type="text/javascript">
var navigationJSON=[ {name:'系统管理',link:'${base}/module/irm/portal/portal/portal/portal_browse.action?ccate=admin'},{name:'人员',link:'${base}/module/irm/system/organ/user/user_mainframe.action?organid=R0'}, {name:'新增人员'}];

	function page_save()
	{
	    $("#inputform").attr('action','user_insert.action');
	    $("#inputform").trigger('submit');
	}

	function page_close()
	{
		//window.location = "user_browse.action?_searchname=system.organ.user.browse&organid=${arg.organid}";
	    window.opener.location.reload();
	    window.close();
	}
	
	$(function(){
		$('#ownerorg').val('${arg.organ.internal}');
		$('#owneorgname').val('${arg.organ.cname}');
	});
	
</script>
