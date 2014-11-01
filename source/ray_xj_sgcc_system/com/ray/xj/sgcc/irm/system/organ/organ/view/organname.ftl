<!DOCTYPE HTML>
<html>
<head>
<#include "/decorator/include/include.ftl">
<script type="text/javascript">
var  rootID='R0';//单层选项

function ajax4Person(num){
var ohtml='';
$.ajax({
    url:'organ_selectfromorgan.action',
    data:{supid:num},
    cache:false,
    async:false,
    success:function(d){
        eval('var d='+d);
        $.each(d,function(j,k){
			var otype='folder';
			ohtml+='<li data-id="'+k.id+'" data-ctype="'+k.ctype+'" data-name="'+k.name+'" data-allname="'+k.allname+'" ><span class="'+otype+'" title="'+k.name+'">'+k.name+'</span></li>';
        })
        
    }
})
return ohtml;
}

//console.log(ajax4Person())

jQuery(function($){
///////////////////
var multiChoose=window.parent.multi;

$('#gAjaxTree li span').live('click',function(){

	
    var obj=$(this).parent();
    if(obj.find('ul').length==0){
	//	if(obj.attr('data-ctype')!=0){//如果不是终点 才 ajax
			obj.append('<ul>'+ajax4Person(obj.attr('data-id'))+'</ul>')
//		}
    }else{
		obj.find('>ul').toggle();			
	}
    $(this).toggleClass('open');	
	if(obj.attr('data-ctype')=='DEPT')
	{
		$('#gAjaxTree li span').removeClass('selected');
		$(this).toggleClass('selected')	
	}
}).live('mouseenter',function(){
    $(this).addClass('hover');
}).live('mouseleave',function(){
    $(this).removeClass('hover');
})

$('#gAjaxTree li span').live('dblclick',function(){
	//检查是否重复
	var ctype=$(this).parent().attr('data-ctype');
	var oname=$(this).parent().attr('data-name');
	var oid=$(this).parent().attr('data-id');
	var oallname=$(this).parent().attr('data-allname');
	//console.log($.inArray(oid,window.parent.chozenArray))
	if($.inArray(oid,window.parent.chozenArray)!=-1){
		
	}else{
		if(ctype=='DEPT')
		{
			if(multiChoose==1){
			$('#result',window.parent.document).append('<li data-id="'+oid+'" data-name="'+oname+'" data-allname="'+oallname+'" title="'+$(this).text()+'">'+$(this).text()+'</li>');
			window.parent.chozenArray.push(oid);	
			}else{
				//还要去掉 比较重复 的那个数组内容
				window.parent.chozenArray=[];
				$('#result',window.parent.document).empty().append('<li data-id="'+oid+'" data-name="'+oname+'" data-allname="'+oallname+'" title="'+$(this).text()+'">'+$(this).text()+'</li>');
			}			
		}
	}
	//如果被选择了 就不应该再有 select status
	$(this).removeClass('selected');
	
})

//默认打开第一季
$('#gAjaxTree').empty().append(ajax4Person('R0'));

//禁止选择
$('#gAjaxTree').disableSelection();

///////////////////
})


</script>
</head>
<body>


<ul id="gAjaxTree" style="">
</ul>


</body>
</html>