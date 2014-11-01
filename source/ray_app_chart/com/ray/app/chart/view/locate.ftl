<script>
$("#bt_save").click(function() {page_save()});
$("#bt_close").click(function() {page_close()});
function page_save()
{
	$('#locateform').attr("action", "${base}/module/app/system/chart/chart/chart_update.action");
	$('#locateform').trigger('submit');
}
function page_close()
{
	window.opener.location.reload();
	window.close();
}
</script>