<script type="text/javascript">

$("#bt_save").click(function() {page_save()});
$("#bt_close").click(function() {page_close()});

function page_save()
{
	$("#inputform").attr("action","search_insert.action?_searchname=search.insert");
    $("#inputform").trigger('submit');	
}

function page_close()
{
	window.location = "invoke!mainframe.action";
}
</script>
