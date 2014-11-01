<script>
$("#bt_save").click(function() {page_save()});
$("#bt_close").click(function() {page_close()});

function page_save()
{
	form.action = "invoke!update.action";
	form._searchname.value = "searchoption.update";
	if (Validator.Validate(form, 2))
	{
		form.submit();
	}	
}

function page_close()
{
	window.location = "invoke!page.action?_searchname=searchoption.browse&searchid=${arg.searchid}";
}
</script>