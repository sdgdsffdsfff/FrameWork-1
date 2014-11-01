<script>
$("#bt_save").click(function() {page_save()});
$("#bt_close").click(function() {page_close()});

function page_save()
{
	form.action = "chartcolorrange_insert.action";
	form.submit();
}

function page_close()
{
	window.location = "chartcolorrange_browse.action?_searchname=chartcolorrange.browse";
}
</script>
