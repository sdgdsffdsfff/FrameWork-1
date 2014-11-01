<script type="text/javascript">
$("#bt_save").click(function() {page_save()});
$("#bt_close").click(function() {page_close()});
</script>
<script type="text/javascript">
var value2back=[];
$(function(){

$('.checkbox').click(function(){
	$('.checkbox').each(function(){
		$(this).removeClass("checkboxChecked");
	});

});

$('.toggleCheckboxAll .checkbox').hide();

$('.dataGrid input.checkbox').live('click',function(){

value2back=[];
var a=$(this).parents('tr').index();

value2back.push(dataJSON.tbody[a][1].name,dataJSON.tbody[a][0].id,dataJSON.tbody[a][2].name)
//alert(value2back)
})

})

function page_save()
{

	if(value2back.length==0)
	{
		alert("未选择记录，请选择记录！");
		return;
	}
	
	$('#subflowid',window.opener.document).val(value2back[1]);
	$('#subflowsno',window.opener.document).val(value2back[2]);
	$('#subflowname',window.opener.document).val(value2back[0]);
	
	window.close();
	
}


function page_close()
{
	window.close();

}
</script>