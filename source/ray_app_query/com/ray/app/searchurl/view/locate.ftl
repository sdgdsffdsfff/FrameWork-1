<script type="text/javascript">
$(function(){
///////////////////////////////

$("#bt_save").click(function() {page_save()});
$("#bt_close").click(function() {page_close()});

form.searchid.value = "${searchid}";

///////////////////////////
})

function page_save()
{
	$("#locateform").attr("action","searchurl_update.action?_searchname=searchurl.update");
    $("#locateform").trigger('submit');		
}

function page_close()
{
	window.close();
}
</script>