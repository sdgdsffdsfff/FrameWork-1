<script>
$("#bt_save").click(function() {page_save()});
$("#bt_copy").click(function() {page_copy()});
$("#bt_close").click(function() {page_close()});

function page_save()
{
	form.action = "invoke!update.action";
	form._searchname.value = "search.update";
	if (Validator.Validate(form, 2))
	{
		form.submit();
	}	
}

function page_close()
{
	window.parent.location = "invoke!page.action?_searchname=search.browse";
}

function page_copy()
{
	newsearchid = window.prompt("请输入新标识",form.searchid.value);
	window.location = "invoke!copy.action?searchid=${arg.searchid}&newsearchid=" + newsearchid;
}

</script>
