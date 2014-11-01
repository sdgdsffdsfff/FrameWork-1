<!DOCTYPE HTML>
<html>
<head>
<#include "/decorator/include/include.ftl">
<script type="text/javascript">

//是否允许多选的标识
var multi=${arg.multi};
//是否显示全称的标识
var full=${arg.full};
//chozen item array to check 
var chozenArray=[];

jQuery(function($){
///////////////////

$('#result').sortable({placeholder:'ui-sortable-placeholder',axis:'y',delay:200})
$('#result li').live('click',function(){
	$(this).toggleClass('c');	
})

$('button.remove').click(function(){
	if(multi==1){
	$('#result li.c').each(function(){
		var thisVal=$(this).attr('data-id');
		chozenArray = jQuery.grep(chozenArray, function(value) {
		return value !=thisVal;
		});
		$(this).remove();
	})}else{
		chozenArray=[]	
		$('#result').empty();
	}
	
})

$('#result li').live('dblclick',function(){
	
	var thisVal=$(this).attr('data-id');
		chozenArray = jQuery.grep(chozenArray, function(value) {
		return value !=thisVal;
		});
		
	$(this).remove();

})

$('button.add').click(function(){

	$('#gAjaxTree li span.selected',$('iframe:visible').contents()).each(function(){
		var oname=$(this).parent().attr('data-name');
		var oallname=$(this).parent().attr('data-allname');
		var oid=$(this).parent().attr('data-id');
		if($.inArray(oid,chozenArray)==-1){
			
			if(multi==1){//允许多选
				$('#result').append('<li  data-name="'+oname+'" data-allname="'+oallname+'" data-id="'+oid+'" title="'+$(this).text()+'">'+$(this).text()+'</li>');
				chozenArray.push(oid);
			}else{//只能单选
				$('#result').empty().append('<li data-name="'+oname+'" data-allname="'+oallname+'" data-id="'+oid+'" title="'+$(this).text()+'">'+$(this).text()+'</li>');				
				chozenArray=[];
				chozenArray.push(oid);	
			}
		
		}else{
			//
		}
		$(this).removeClass('selected');
	})
})

//返回最终选择人员id
$('#getChosen').click(function(){

	if($('#result').find('li').length==0)
	{
		alert("请选择部门！");
		return;
	}
	var arr1=[];
	var arr2=[];
	var arr3=[];
	var pdeptname = '${arg.pdeptname}';
	var pdeptid = '${arg.pdeptid}';
	$('#result li').each(function(){
		arr1.push($(this).attr('data-name'));
		arr2.push($(this).attr('data-id'));
		arr3.push($(this).attr('data-allname'));
	})
		if(full=='0')
		{
			$('#${arg.pdeptname}',window.opener.document).val(arr1.join(','))
		}
		else
		{
			$('#${arg.pdeptname}',window.opener.document).val(arr3.join(','))
		}
		if(pdeptid!=null)
		{
			$('#${arg.pdeptid}',window.opener.document).val(arr2.join(','))
		}
	else
	{
		//
	}
	
	window.close();
})
$('#bt_close').click(function(){
	window.close();
})
///////////////////
})
</script>
<style type="text/css">
html,body {overflow:hidden;}
body {background:#fff;}
#layoutPage {margin:6px;}
#layoutPage table {border-collapse:collapse;borde:0;width:100%;}
#layoutPage table td {padding:0;}
#oTab .tabContent iframe {width:100%;border:0;float:left;border:solid 1px #ccc;border-top:0;}

#result li {line-height:22px;padding-left:8px;cursor:pointer;white-space:nowrap;text-overflow:ellipsis;overflow:hidden;_width:172px;}
#result li:hover {background:#eee;}
#result li.c {background-color:#21759b;color:#fff;}
.ui-sortable-placeholder {width:160px;}

/*size config*/
#oTab .tabContent iframe {height:300px;}
#result {height:300px;width:180px;}
#resultTd {width:180px;}

</style>
</head>
<body id="layoutPage">
<div class="opleft" style="margin-top:6px;padding-right:1em;"><button id="getChosen">确定</button><button id="bt_close">取消</button></div>
<div id="oTab">


<table>
<tr>
<td><ul class="tabTitle"><li>部门</li></ul>
</td>
<td>
</td>
<td>
</td>
</tr>
<tr>
<td>
<ul class="tabContent">
	<li><iframe src="organ_organname.action" frameborder="0"></iframe></li>
</ul>
</td>
<td  width="50" style="text-align:center;">
<div><button class="add">-&gt;</button></div><div><button class="remove">&lt;-</button></div>
</td>
<td id="resultTd">
<ul id="result" style="overflow:auto;border:solid 1px #ccc;">
</ul>
</td>
</tr>
</table>
</div>

</body>
</html>