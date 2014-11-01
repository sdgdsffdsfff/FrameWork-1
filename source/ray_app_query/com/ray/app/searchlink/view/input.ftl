<script type="text/javascript">

$("#bt_save").click(function() {page_save()});
$("#bt_close").click(function() {page_close()});

form.searchid.value = "${searchid}";

function page_save()
{
	form.action = "searchlink_insert.action";
	form._searchname.value = "searchlink.insert";
	// if (Validator.Validate(form, 2))
	{
		form.trigger('submit');
	}	
}

function page_close()
{
	window.location = "searchlink_browse.action?_searchname=searchlink.browse&searchid=${searchid}";
}
</script>