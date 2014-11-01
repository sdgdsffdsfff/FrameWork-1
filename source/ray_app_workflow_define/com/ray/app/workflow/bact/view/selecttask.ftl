<!DOCTYPE HTML>
<html>
<head>
<#include "/decorator/include/include.ftl">

<script type="text/javascript">
// 初始化鼠标所点的行的键值
var currentKey;
// 根目录，其他javascript可能会用到

//获取所有选择的键值
//add by blue 2004/03/25
function getAllCheckedKey()
{
	var form1 = document.forms[0];
	var len = form1.elements.length;
	
	//校验是否选中记录
	for (i=0;i<len ;i++ )
	{
		if (form1.elements[i].checked==true)
		{
		  return true;
			break;
		}
	}
	return false;
}
function page_selectAll()
{
	var form1 = document.forms[0];
	var len = form1.elements.length;
	
	//校验是否选中记录
	for (i=0;i<len ;i++ )
	{
		 if( form1.elements[i].checked==true)  form1.elements[i].checked=false;
		 else form1.elements[i].checked=true;
 	}
 
}

function go2page(url)
{
	window.location = url;
}

function on_page_change()
{
   var target = event.srcElement.options[event.srcElement.selectedIndex].target;
   var url = event.srcElement.options[event.srcElement.selectedIndex].value;
   parent.viewSelected = event.srcElement.selectedIndex;
   
//   alert(url);
		switch (target)
		{
				case "self" :
				  location = url+"&viewSelected="+(parent.viewSelected+1);
					break;
				case "parent" :
				  window.parent.location = url+"&viewSelected="+(parent.viewSelected+1);
					break;					
				default :
				  location = url+"&viewSelected="+(parent.viewSelected+1);
					break;
		}   
}

function showBottomFrame(my_rows)
{
	window.parent.document.getElementsByTagName("frameset")[0].rows= my_rows;
}
</script>
<script>
//
function page_insert()
{
	window.parent.frames[1].location = "<%=PathToolKit.getRootPath(request)%>/WFBFlowClassForm?ACTION=PreInsBFlowClass";
	showBottomFrame("0,*");
}
function page_delete()
{
	if(!getAllCheckedKey())
	{  
   	alert("没有选中记录！"); 
   	return ; 
  }
	window.parent.frames[1].location = "<%=PathToolKit.getRootPath(request)%>/WFBFlowClassForm?ACTION=DeleteBFlowClass&id="+currentKey;
	showBottomFrame("0,*");
}
// 进入编辑记录页面
function page_update()
{
	if(!getAllCheckedKey())
	{  
   	alert("没有选中记录！"); 
   	return ; 
  }

	window.parent.frames[1].location = rootpath + "/WFBFlowClassForm?ACTION=UpdLocBFlowClass&id=" + currentKey  ;
	showBottomFrame("0,*");
}

function page_change()
{
	var viewtype = event.srcElement.options[event.srcElement.selectedIndex].value;
  var url;
	switch (viewtype)
	{
		case "draft" :
		{
			url = "<%=PathToolKit.getRootPath(request)%>/BFlowClassForm?ACTION=SelectTask&viewtype=draft";
			break;
		}
		case "wait" :
		{
			url = "<%=PathToolKit.getRootPath(request)%>/BFlowClassForm?ACTION=SelectTask&viewtype=wait";
			break;					
		}
		case "handle" :
		{
			url = "<%=PathToolKit.getRootPath(request)%>/BFlowClassForm?ACTION=SelectTask&viewtype=handle";
			break;					
		}
		case "complete" :
		{
			url = "<%=PathToolKit.getRootPath(request)%>/BFlowClassForm?ACTION=SelectTask&viewtype=complete";
			break;					
		}
		default :
		{
			url = "<%=PathToolKit.getRootPath(request)%>/BFlowClassForm?ACTION=BrowseBFlowClassWait&viewtype=wait";
			break;
		}   
	}
	window.location = url;
}



function clk(sPath) {
   
   strt = sPath.split(":"); 
  window.parent.document.all.acttask_apptaskid.value=strt[0]; 
  window.parent.document.all.acttask_cname.value=strt[1]; 
  window.parent.document.all.acttask_descript.value=strt[2]; 
   
 }
 
function fanhui(){self.close();} 

jQuery(function($){
/////////////////////

$('#showTable tbody tr').live('click',function(){
	$('#showTable tbody tr').removeClass('selectedTr');
	$(this).addClass('selectedTr');
})

/////////////////////
})
</script>
<style>
#showTable tr.selectedTr td {background: #69c;color:#fff;}
</style>

</head>



<body>
<form name="viewForm" action="" method="post">

<!-- 数据浏览表格 -->
<table id="showTable">
	<tbody>
		<tr>
			<!--th width="1%"><nobr>选择</nobr></th-->
			<!--th width="1%">流程类别</th>
			<th width="1%">任务标识</th-->
			<th width="1%">任务名称</th>
			<!--th width="1%" style="display: none;">说明</th-->
				 
		</tr>
		<#list data.tasks as aobj>
			<tr onclick="currentKey='${aobj.taskid}'+':'+'${aobj.taskname}'+':'+'${aobj.shm}';clk(currentKey);" >
				<!--td nowrap><input type="radio" name="index" class="checkbox" value="${aobj.taskid}"></td-->
				<td nowrap>${aobj.taskname}</td>
				<!--td nowrap style="display: none;"><%=obj.getFormatAttr("shm")%></td-->
				
			</tr>	
		</#list>
		
	</tbody>
	</table>

		<!-- 数据翻页控制 (动态内容，标签产生)-->
		 


</form>

</body>
</html>
