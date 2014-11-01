<!DOCTYPE HTML>
<html>
<head>
<#include "/decorator/include/include.ftl">
<script type="text/javascript">
var  pinyin='';

function ajax4Person(pinyin){
var ohtml='';
$.ajax({
    url:'user_selectuser.action',
    data:{pinyin:pinyin},
    cache:false,
    async:false,
    success:function(d){
        eval('var d='+d);
		if(d.length==0){
			ohtml='<li>没有找到</li>';
			$('#pinyinInput').trigger('focus').select();
		}else{
			$.each(d,function(j,k){
				var otype='person';
				if(k.sex=='female'){otype='person woman'}
				ohtml+='<li data-id="'+k.id+'" data-deptname="'+k.deptname+'" data-deptallname="'+k.deptallname+'" data-deptid="'+k.deptid+'"><span class="'+otype+'" title="'+k.name+'">'+k.name+'</span></li>';
			})
		}
        
    }
})
return ohtml;
}

//console.log(ajax4Person())

jQuery(function($){
///////////////////
var multiChoose=window.parent.multi;

$('#gAjaxTree li span').live('click',function(){
	if(multiChoose==0){
		$('#gAjaxTree li span').removeClass('selected')
		$(this).addClass('selected');
	}else{
		$(this).toggleClass('selected')	
	}	
}).live('mouseenter',function(){
    $(this).addClass('hover');
}).live('mouseleave',function(){
    $(this).removeClass('hover');
})

$('#gAjaxTree li span').live('dblclick',function(){
	//检查是否重复
	var oid=$(this).parent().attr('data-id');
	var odeptname=$(this).parent().attr('data-deptname');
	var odeptallname=$(this).parent().attr('data-deptallname');
	var odeptid=$(this).parent().attr('data-deptid');
	//console.log($.inArray(oid,window.parent.chozenArray))
	if($.inArray(oid,window.parent.chozenArray)!=-1){
		//
	}else{
		if(multiChoose==1){
			$('#result',window.parent.document).append('<li data-id="'+oid+'" data-deptname="'+odeptname+'" data-deptallname="'+odeptallname+'" data-deptid="'+odeptid+'" title="'+$(this).text()+'">'+$(this).text()+'</li>');
			window.parent.chozenArray.push(oid);	
		}else{
			//还要去掉 比较重复 的那个数组内容
			window.parent.chozenArray=[];
			$('#result',window.parent.document).empty().append('<li data-id="'+oid+'" data-deptname="'+odeptname+'" data-deptallname="'+odeptallname+'" data-deptid="'+odeptid+'" title="'+$(this).text()+'">'+$(this).text()+'</li>');
		}
					
	}
	//如果被选择了 就不应该再有 select status
	$(this).removeClass('selected');
	
})

//默认打开第一季
$('#gAjaxTree').empty().append(ajax4Person(pinyin));

//禁止选择
$('#gAjaxTree').disableSelection();

//#pinyinInput
$('#pinyinInput').keyup(function(){
	$('#gAjaxTree').empty().append(ajax4Person($(this).val()));	
})

///////////////////
})


</script>
</head>
<body style="height:auto;">

<div style="padding:6px;position:fixed;left:6px;top:0;background:#fff;_position:absolute;_top:expression(documentElement.scrollTop);width:100%;border-bottom:solid 1px #ccc;"><input id="pinyinInput" class="text"  style="font-size:18px;font-weight:700;width:4em;" title="输入姓名首字母" /> <span style="color:#666">输入姓名首字母检索，如 张三 zhs</span></div>

<ul id="gAjaxTree" style="margin-top:36px;">

</ul>

</body>
</html>