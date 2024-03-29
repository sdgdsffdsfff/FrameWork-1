<!DOCTYPE HTML>
<html>
<head>
<#include "/decorator/include/include.ftl">
<script type="text/javascript">
$(function(){
/////////////////////////////////////////

var uploadQueue=[];
var uploadNames=[];

$('#file_upload').uploadify({
    'uploader'  : '${base}/uploadify/uploadify.swf',
    'script'    : '${base}/module/irm/system/attach/attach/attach_uploadbusiness.action;jsessionid=${request.session.id}',
    'scriptData': {'kid':'${arg.kid}','cclass':'${arg.cclass}'},
	'buttonImg': '${base}/uploadify/upload.png',
    'cancelImg' : '${base}/uploadify/cancel.png',
    'folder'    : 'uploads',
    'fileDataName':'fupload',
	'multi':true,
	'width':107,
	'height':30,
	'auto':true,
	'onComplete':function(e,ID, fileObj, response, data){
		if(response!='error'){
			eval('response='+response);
			uploadQueue.push(response);
			uploadNames.push(fileObj.name)
			var ohtml='<ul>';
			var oid=[];
			var oname=[];
			
			var k2;
			$.each(uploadQueue,function(j,k){				
				<#--ohtml+='<li data-id="'+k[1]+'"><a target="_blank" class="attachment" href="${base}/module/irm/system/attach/attach/attach_downloadbyid.action?attachid='+k[1]+'">'+uploadNames[j]+'</a><span class="del" title="删除">x</span></li>';
				oid.push(k[1])
				oname.push(k[2])
				-->
				k2=k;
			})
			$('.dataGrid').append('<tr><td><li data-id="'+k2[1]+'"><a target="_blank" class="attachment" href="${base}/module/irm/system/attach/attach/attach_downloadbyid.action?attachid='+k2[1]+'">'+k2[2]+'</a><span class="del" title="删除">x</span></li></td><td>'+k2[3]+'</td><td>'+k2[4]+'</td></tr>');
			<#--ohtml+='</ul>';
			$('.gUploaded').empty().append(ohtml);
			$('#attachid').val(oid.join(','))
			$('#attachname').val(oname.join(','))
			-->

			$('.gUploaded span.del').click(function(){
				oid=[];//清空重算
				var oParent=$(this).parents('.gUploaded');
				var oindex=$(this).index()-1;
				
				$(this).parent().remove();
				oParent.find('li').each(function(){
					oid.push($(this).attr('data-id'))
					oname.push($(this).attr('data-cname'))
				})
				$('#attachid').val(oid.join(','))
				$('#attachname').val(oname.join(','))				
				
				uploadQueue.splice(oindex,1);
				uploadNames.splice(oindex,1);
				
				
			})
		}
	}
  }); 

$('.attachmentUl .del').live('click',function(){
	
	if(window.confirm('你确认要删除这个附件吗？')){
		var oid=$(this).parent().attr('data-id');
		var othis=$(this);
		var oparent=othis.parent();
		$.ajax({
			url:'${base}/module/irm/system/attach/attach/attach_isDelete.action',
			data:{attachid:oid},
			success:function(d){
				if(d=='done'){//后台确认已经删除	
				    			
					oparent.animate({opacity:0},'fast','swing',function(){						
						oparent.empty().css({opacity:1,color:'#c00'}).append('该附件已经被删除！');
						setTimeout(function(){
							oparent.parent().parent().remove()
						},1000)							
					})
				}else{ //删除失败
					oparent.find('.error').remove();
					oparent.append(' <span class="error" style="color:#c00">删除操作失败：该文件已经被删除，或者你没有权限</span>');
				}
				
			}	
		})
	}
})


  /////////////////////////////////////////
})
</script>
</head>
<body>
	<div id="pageContainer">
	<table class="dataGrid">
	<thead>
		<tr>
			<th class="r"><label for="">附件名称</label></th>
			<th class="r"><label for="">创建人</label></th>
			<th class="r"><label for="">创建时间</label></th>
		</tr>
	</thead>
	<tbody class="attachmentUl">
		
		
	 	<#list data.attachs as aobj>
	 		<tr>
				<td>
					<#if arg.readonly!="true"><li data-id="${aobj.id}"></#if>
					<a target="_blank" class="attachment" href="${base}/module/irm/system/attach/attach/attach_downloadbyid.action?attachid=${aobj.id}">${aobj.cname}</a>
					<#if arg.readonly!="true"><span class="del">X</span></#if><br>
					<#if arg.readonly!="true"></li></#if>
				</td>
				<td>${aobj.createuser}</td>
				<td>${aobj.createtime}</td>
			</tr>
		</#list>
		

		
	</tbody>
			
	</table>
	<#if arg.readonly!="true">
	<table>
		<tr>
			<td class="r"><label for="">附件：</label></td>
			<td colspan="3">
			<span id="file_upload"></span>
			<div class="gUploaded"></div> 
		    <#--
		    <ul class="attachmentUl">
			 	<#list data.attachs as aobj>
					<li data-id="${aobj.id}">
					<a target="_blank" class="attachment" href="${base}/module/irm/system/attach/attach/attach_downloadbyid.action?attachid=${aobj.id}">${aobj.cname}</a><span class="del">X</span><br>
					</li>
				</#list>
			</ul>
			</td>
			-->
		</tr>
	</table>
	</#if>
	</div>
</body>
</html>