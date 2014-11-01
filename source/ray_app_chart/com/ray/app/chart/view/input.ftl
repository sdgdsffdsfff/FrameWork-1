<script>
$("#bt_save").click(function() {page_save()});
$("#bt_close").click(function() {page_close()});

</script>

<script>	
function page_save()
{
	$('#inputform').attr("action","${base}/module/app/system/chart/chart/chart_insert.action");
	$('#inputform').trigger('submit');
}


function page_close()
{
	window.close();
}
</script>

