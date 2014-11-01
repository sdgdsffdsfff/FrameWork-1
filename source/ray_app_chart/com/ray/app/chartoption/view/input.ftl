<script>
$("#bt_save").click(function() {page_save()});
$("#bt_close").click(function() {page_close()});

function page_save()
{ 

	$('#inputform').attr("action","chartoption_insert.action");
	$('#inputform').trigger('submit');
}

function page_close()
{
	//window.location = "chartoption_browse.action?_searchname=chartoption.browse";
    window.close();
}
</script>
