<script>

$("#bt_save").click(function() {page_save()});
$("#bt_close").click(function() {page_close()});

function page_save()
{
<!--	form.action = "${base}/module/app/system/query/search/invoke!insert.action"; -->

    form.action = "invoke!insert.action";
	form._searchname.value = "search.insert";
	if (Validator.Validate(form, 2))
	{
		form.submit();
	}	
}

function page_close()
{
	window.location = "invoke!mainframe.action";
}
</script>
