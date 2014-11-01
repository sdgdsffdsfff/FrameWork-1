<script type="text/javascript">
$(function(){
///////////////////////////////

$("#bt_save").click(function() {page_save()});
$("#bt_close").click(function() {page_close()});

form.searchid.value = "${searchid}";

//////////////////////////////
})

function page_save()
{
	$("#inputform").attr("action","searchitem_insert.action?_searchname=searchitem.insert");
    $("#inputform").trigger('submit');	
}

function page_close()
{
	window.close();
}
</script>