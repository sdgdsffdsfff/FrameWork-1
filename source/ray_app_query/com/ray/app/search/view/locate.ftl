<script type="text/javascript">

$(function(){
/////////////////////////////////////////

$("#bt_save").click(function() {page_save()});
$("#bt_copy").click(function() {page_copy()});
$("#bt_close").click(function() {page_close()});

/////////////////////////////////////////
})

function page_save()
{
	$("#locateform").attr("action","search_update.action?_searchname=search.update");
    $("#locateform").trigger('submit');
}

function page_close()
{
	window.close();
}

function page_copy()
{
	newsearchid = window.prompt("请输入新标识",form.searchid.value);
	window.location = "search_copy.action?searchid=${arg.searchid}&newsearchid=" + newsearchid;
}

</script>
