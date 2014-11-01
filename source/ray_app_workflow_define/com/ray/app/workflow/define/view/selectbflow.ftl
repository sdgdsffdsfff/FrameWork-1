<form id="selectform">
<input id='flowid' type="hidden" name="flowid">
</form>
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
//alert(dataJSON.tbody[a][0].id)//流程编号
//alert(dataJSON.tbody[a][5].id)//流程编号
//alert(dataJSON.tbody[a][3].name)//版本
//alert(dataJSON.tbody[a][1].name)//流程名称
//alert(dataJSON.tbody[a][4].name)//当前状态
//alert(dataJSON.tbody[a][5].name)//流程类别名称


value2back.push(dataJSON.tbody[a][1].name,dataJSON.tbody[a][0].id,dataJSON.tbody[a][3].name,dataJSON.tbody[a][4].name,dataJSON.tbody[a][5].name)
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
	
	//var rekeynames = "cname,sno,verson,state,classname".split(",");

	//for(i=0;i<rekeynames.length;i++)
	//{
		//$('#' + rekeynames[i],window.opener.document).val(value2back[i]);
	//}
	//alert(value2back[1]);
	//alert(window.opener.name);
	$('#flowid').val(value2back[1]);
	
	url="${base}/module/app/system/workflow/define/define_main.action?flowid="+value2back[1];
	//$("#selectform").attr("action",url);
	//$("#selectform").attr("target",window.opener.name);
	//$("#selectform").trigger('submit');
	
	window.opener.location=url;
	window.close();
	
}


function page_close()
{
	window.close();

}
</script>