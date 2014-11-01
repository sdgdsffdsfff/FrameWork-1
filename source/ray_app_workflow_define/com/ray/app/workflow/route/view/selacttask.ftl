<!DOCTYPE HTML>
<html>
<head>
<#include "/decorator/include/include.ftl">
<title>选择所有者</title>
<script type="text/javascript" src="${base}/wfb/blueflowui/javascript/common.js"></script>
<SCRIPT>
var ownerctx="",ownertype="",ownername="";

var page=new Array(),type="机构";;
page[0]="<%=PathToolKit.getRootPath(request)%>/jsp/system/public/SelOrg.htm";
page[1]="<%=PathToolKit.getRootPath(request)%>/jsp/system/public/SelDept.htm";
page[2]="<%=PathToolKit.getRootPath(request)%>/SelWorkGroupForWFForm?SearchName=WorkGroupObject&ACTION=Browse";
page[3]="<%=PathToolKit.getRootPath(request)%>/SelRoleForWFForm?SearchName=RoleObject&ACTION=Browse";
page[4]="<%=PathToolKit.getRootPath(request)%>/SelPersonForWFForm?SearchName=PersonObject&ACTION=Maintain";


function detail_page(url)
{
  switch (url) {
  case "机构":window.frames[1].location=page[0];
               type="机构";
              break;
  case "部门":window.frames[1].location=page[1];
               type="部门";break;
  <#--
  case "工作组":window.frames[1].location=page[2];
               type="工作组";break;
  -->
  case "角色":window.frames[1].location=page[3];
              type="角色";break;
  case "人员":window.frames[1].location=page[4];
               type="人员";break;
  }
}
</SCRIPT>
</head>
<body onload="ldvalue()" style="height:auto;">

<form method="post" action="" name="form1"  >

<table width="99%" height="300" bgcolor="" >
		<tr>
			<td height="1" colspan="2" ><div class="oSpan">所选任务：</div></td>
			<td height="1"><div class="oSpan">待选任务：</div></td>

		</tr>	
		<td>					
		<iframe src="${base}/module/app/system/workflow/route/route_linetasklist.action" id="objlist"  height="300" width="100%" name="objlist" scrolling="auto" frameborder="0" style="border:solid 1px #ccc;"></iframe> 
		</td>
		<td width="8%">
			<button onclick="addvalue();">　　添加</button>
			<div style="height:10px;"></div>
			<button onclick="deleteTblRow()">　　删除</button>
			<div style="height:10px;"></div>
			<button onclick="deleteTblAll()">删除全部</button>
		</td>
				
		
		<td width="50%">
			<table width="100%" height="100%" cellspacing="0" cellpadding="0">									
						   <tr>
								<td width="100%" height="100%">
									<iframe id="objectlist" src="${base}/module/app/system/workflow/route/route_nodetasklist.action" height="300" width="100%" name="objectlist" scrolling="auto"　frameborder="0" style="border:solid 1px #ccc;"></iframe>
								</td>
							</tr>
						</table>						
					</td>
						
			</table>
		</td>			
</table>

<script>
 

      var oldTr=null;
      var selectedTr;

 	function ldvalue()
	{
     try{
   
ownerctx=window.parent.document.all.line_acttaskid.value;
ownertype=window.parent.document.all.line_require.value;
ownername=window.parent.document.all.line_taskname.value;
     
     
      var row;
      var cell;
	
	   octx=ownerctx.split(",");
	   otype=ownertype.split(",");
	   oname=ownername.split(",");
	    
tblUpdate=document.frames[0].document.getElementById("showTable");	

       if((ownerctx.substr(ownerctx.length-1,ownerctx.length))==",") ll=octx.length-1; 
       else ll=octx.length; 

   
       
       if(ownerctx!="")	   
	   for(i=0;i<ll;i++)
	   {
	   
     row = tblUpdate.insertRow();
     selectedTr=row.rowIndex;
     if(oldTr!=null){oldTr.bgColor=""}
     oldTr=row;
     
     
    cell = row.insertCell();
    cell.innerText = oname[i];
    cell = row.insertCell();
    cell.innerText =  otype[i];
   cell = row.insertCell();
    cell.innerText =octx[i]; 
     cell.style.display="none";
       
   eval("row.onclick=function(){oldTr=this;selectedTr=this.rowIndex;}")  
   //if(oldTr!=null){oldTr.bgColor=''}this.bgColor='red';
   
	     
	   }
	   }catch(Exception){}
	}
	
	function addvalue()
	{
      var row;
      var cell;
        id= document.frames[1].document.all.line_acttaskid.value;
     
      if((id=="")||(id=="undefined")){ alert("请先选择！");return}
   
     tblUpdate=document.frames[0].document.getElementById("showTable");
	for (var i = 0; i < tblUpdate.rows.length; i++) {
		for (var j = 0; j < tblUpdate.rows[i].cells.length; j++) {
			 if(id==tblUpdate.rows[i].cells[j].innerText)	 
			 {
			   alert("这个对象已经加入！");return ;
			 }	
		}
	}
     
     tblUpdate=document.frames[0].document.getElementById("showTable");
     row = tblUpdate.insertRow();
     selectedTr=row.rowIndex;
     if(oldTr!=null){oldTr.bgColor=""}
    // row.bgColor="red";
     oldTr=row;
     
     row.attachEvent("onclick",function(){
      oldTr=row;selectedTr=row.rowIndex; });
 
    cell = row.insertCell();
    cell.innerText =document.frames[1].document.all.line_name.value;;
    a1=window.parent.document.all.line_taskname.value;
    if(a1!="") {if(a1.substr(a1.length-1,a1.length)!=",") window.parent.document.all.line_taskname.value=a1+",";}
    window.parent.document.all.line_taskname.value+=document.frames[1].document.all.line_name.value+",";
    
    
    cell = row.insertCell();
    cell.innerText =document.frames[1].document.all.line_require.value;;
    a1=window.parent.document.all.line_require.value;
    if(a1!="") {if(a1.substr(a1.length-1,a1.length)!=",") window.parent.document.all.line_require.value=a1+",";}
    window.parent.document.all.line_require.value+=document.frames[1].document.all.line_require.value+",";
 
  
    cell = row.insertCell();
    cell.innerText = document.frames[1].document.all.line_acttaskid.value;;    cell.style.display="none";
    a1=window.parent.document.all.line_acttaskid.value;
    if(a1!="") {if(a1.substr(a1.length-1,a1.length)!=",") window.parent.document.all.line_acttaskid.value=a1+",";}
    window.parent.document.all.line_acttaskid.value+=document.frames[1].document.all.line_acttaskid.value+",";
    
 
	}
	function deleteTblRow()
	{
      try{
      var row;
      var cell;
 
     tblUpdate=document.frames[0].document.getElementById("showTable");
     
    if(typeof(selectedTr)!="undefined") tblUpdate.deleteRow(selectedTr);
  	
     ss1="",ss2="",ss3="";
	for (var i = 1; i < tblUpdate.rows.length; i++) {
		for (var j = 0; j < tblUpdate.rows[i].cells.length; j++) {
			   id=tblUpdate.rows[i].cells[j].innerText;	 
			 {
			  if(j==0) ss1=ss1+id+",";
			  if(j==1) ss2=ss2+id+",";
			  if(j==2) ss3=ss3+id+",";
			 }	
		}
	}
	 window.parent.document.all.line_taskname.value=ss1 ;
     window.parent.document.all.line_require.value=ss2 ;
      window.parent.document.all.line_acttaskid.value=ss3 ; 	
	 
     }catch(Exception){}  
     
	}
	function deleteTblAll()
	{
		tblUpdate=document.frames[0].document.getElementById("showTable");
       try{
        while(tblUpdate!=null)
        {
        
          tblUpdate.deleteRow(1);	
          tblUpdate=document.frames[0].document.getElementById("showTable");
          
        } 
        } catch(Exception){}
        window.parent.document.all.line_acttaskid.value="";
        window.parent.document.all.line_require.value="";
        window.parent.document.all.line_taskname.value="";
 
	}
	
</script>
</form>
</body>
</html>
