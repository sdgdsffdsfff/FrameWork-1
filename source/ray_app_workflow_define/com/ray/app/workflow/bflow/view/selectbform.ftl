<script type="text/javascript">
$('#bt_save').click(function() {page_save()});
$('#bt_close').click(function() {page_close()});
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


value2back.push(dataJSON.tbody[a][0].id,dataJSON.tbody[a][1].name)
//alert(value2back)
})

})

//确定
var flowclass = window.dialogArguments;
function page_save()
{
	if(value2back.length==0)
	{
		alert("未选择记录，请选择记录！");
		return;
	}
	flowclass.id=value2back[0]; 
    flowclass.name=value2back[1];
    //flowclass.field=document.all.fieldid.value+":"+document.all.fieldcname.value+":"+document.all.fieldename.value+":"+document.all.fieldtype.value+":"+document.all.fieldaccess.value ;
    
    window.returnValue = flowclass;
    window.close();
}
//关闭
function page_close()
{
	window.close();
}
</script>