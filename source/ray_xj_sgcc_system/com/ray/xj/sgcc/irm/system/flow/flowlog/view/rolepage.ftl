<!DOCTYPE HTML>
<html>
<head>
<#include "/decorator/include/include.ftl">
<script type="text/javascript">
var  rootID=0;//单层选项

function ajax4Person(num,rolename){
var ohtml='';
$.ajax({
    url:'flowlog_roletree.action',
    data:{id:num,rolename:rolename},
    cache:false,
    async:false,
    success:function(d){
        eval('var d='+d);
        $.each(d,function(j,k){
			var otype='folder';
			if(k.leaf==0){otype='file person'}
			if(k.sex=='female'){otype='file person woman'}
			ohtml+='<li data-id="'+k.id+'" data-leaf="'+k.leaf+'" data-deptname="'+k.deptname+'" data-deptid="'+k.deptid+'"><span class="'+otype+'" title="'+k.name+'">'+k.name+'</span></li>';
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
		if(obj.attr('data-leaf')!=0){//如果不是终点 才 ajax
			obj.append('<ul>'+ajax4Person(obj.attr('data-id'),'${arg.rolename}')+'</ul>')
		}
    }else{
		obj.find('>ul').toggle();			
	}
    $(this).toggleClass('open');	
	if(obj.attr('data-leaf')==0){
		if(multiChoose==0){
			$('#gAjaxTree li span').removeClass('selected')
			$(this).addClass('selected');
		}else{
			$(this).toggleClass('selected')	
		}	
	}
}).live('mouseenter',function(){
    $(this).addClass('hover');
}).live('mouseleave',function(){
    $(this).removeClass('hover');
})

$('#gAjaxTree li span.file').live('dblclick',function(){
	//检查是否重复
	var oid=$(this).parent().attr('data-id');
	var leaf=$(this).parent().attr('data-leaf');
	var odeptname=$(this).parent().attr('data-deptname');
	var odeptid=$(this).parent().attr('data-deptid');
	//console.log($.inArray(oid,window.parent.chozenArray))
	if($.inArray(oid,window.parent.chozenArray)!=-1){
		
	}else{
		if(leaf==0)
		{
			if(multiChoose==1){
			$('#result',window.parent.document).append('<li data-id="'+oid+'" data-deptname="'+odeptname+'" data-deptid="'+odeptid+'" title="'+$(this).text()+'">'+$(this).text()+'</li>');
			window.parent.chozenArray.push(oid);	
			}else{
				//还要去掉 比较重复 的那个数组内容
				window.parent.chozenArray=[];
				$('#result',window.parent.document).empty().append('<li data-id="'+oid+'" data-deptname="'+odeptname+'" data-deptid="'+odeptid+'" title="'+$(this).text()+'">'+$(this).text()+'</li>');
			}
		}						
	}
	//如果被选择了 就不应该再有 select status
	$(this).removeClass('selected');
	
})

//默认打开第一季
$('#gAjaxTree').empty().append(ajax4Person('R0','${arg.rolename}'));

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