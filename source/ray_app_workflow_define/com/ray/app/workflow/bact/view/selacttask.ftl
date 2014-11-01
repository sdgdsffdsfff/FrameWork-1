<!DOCTYPE HTML>
<html>
<head>
<#include "/decorator/include/include.ftl">
<SCRIPT type="text/javascript">
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
  case "工作组":window.frames[1].location=page[2];
               type="工作组";break;
  case "角色":window.frames[1].location=page[3];
              type="角色";break;
  case "人员":window.frames[1].location=page[4];
               type="人员";break;
  }
}
</SCRIPT>
</head>
<body   onload="ldvalue()"  >
<FORM Method="Post" action="" name="form1"  >

<table width="99%" height="320" border="0" cellpadding="0" cellspacing="0" >
	<tr>
		<td>
		<div class="oSpan">所选任务：</div>
		<iframe
			src="${base}/module/app/system/workflow/bact/bact_nodetasklist.action"
			id="objlist" height="300" width="100%" name="objlist"
			scrolling="auto"  frameborder="0" style="border:solid 1px #ccc;"></iframe></td>

		<td width="1%">
			<button onclick="addvalue();">　　添加</button>
			<div style="height:10px;"></div>
			<button onclick="deleteTblRow()">　　删除</button>
			<div style="height:10px;"></div>
			<button onclick="deleteTblAll()">删除全部</button>
		</td>


		<td>
		<div class="oSpan">待选任务：</div>
 <iframe id="objectlist" height="300" width="100%" scrolling="auto"
			src="${base}/module/app/system/workflow/bact/bact_task.action" name="objectlist" frameborder="0" style="border:solid 1px #ccc;"></iframe>
		</td>

	</tr>
</table>
</form>

 

<script type="text/javascript">

 
      var oldTr=null;
      var selectedTr;

	function ldvalue()
	{

try{
acttask_id=window.parent.document.all.acttask_id.value;
acttask_sno=window.parent.document.all.acttask_sno.value;
acttask_cname=window.parent.document.all.acttask_cname.value;
acttask_descript=window.parent.document.all.acttask_descript.value;
acttask_ctype=window.parent.document.all.acttask_ctype.value;
acttask_require=window.parent.document.all.acttask_require.value;
acttask_apptaskid=window.parent.document.all.acttask_apptaskid.value;

      var row;
      var cell;
	
atid=acttask_id.split(",");
sno=acttask_sno.split(",");
cname=acttask_cname.split(",");
descript=acttask_descript.split(",");
ctype=acttask_ctype.split(",");
require=acttask_require.split(",");
apptaskid=acttask_apptaskid.split(",");

       if((acttask_apptaskid.substr(acttask_apptaskid.length-1,acttask_apptaskid.length))==",") ll=apptaskid.length-1; 
       else ll=apptaskid.length; 

	    
tblUpdate=document.frames[0].document.getElementById("showTable");	
        	
     if(acttask_apptaskid!="")      
	   for(i=0;i<ll;i++)
   
	    
	   {
	   
     row = tblUpdate.insertRow();
      
     selectedTr=row.rowIndex;
     if(oldTr!=null){oldTr.bgColor=""}
     oldTr=row;
     
     
     cell = row.insertCell();
      cell.innerText =apptaskid[i];
       cell.style.display="none";
    
     cell = row.insertCell();
      cell.innerText =atid[i];
       cell.style.display="none";
     
     
    cell = row.insertCell();
    cell.innerText = sno[i]; 
    cell.style.display="none";
   cell = row.insertCell();
    cell.innerText =cname[i];
   cell = row.insertCell();
    cell.innerText =ctype[i];
   cell = row.insertCell();
    cell.innerText =require[i];
   cell = row.insertCell();
    cell.innerText =descript[i];
    cell.style.display="none";
    eval("row.onclick=function(){oldTr=this;selectedTr=this.rowIndex;}")
	   }
	   }catch(Exception){}
	   
	}
	
	function addvalue()
	{
      var row;
      var cell;
      tblUpdate=document.frames[0].document.getElementById("showTable");
       var id= document.frames[1].document.all.acttask_apptaskid.value;
      if(id==""){ alert("请先选择！");return}
     
	 	for (var i = 0; i < tblUpdate.rows.length; i++) {
			for (var j = 0; j < tblUpdate.rows[i].cells.length; j++) {
				 if(id==tblUpdate.rows[i].cells[j].innerText)	 
				 {
				   alert("该任务已经加入！");return ;
				 }	
			}
		}
	
     
     row = tblUpdate.insertRow();
     selectedTr=row.rowIndex;
     if(oldTr!=null){oldTr.bgColor=""}
     oldTr=row;
     
     row.attachEvent("onclick",function(){
      oldTr=row;selectedTr=row.rowIndex; });
 
  
    cell = row.insertCell();
     cell.innerText = document.frames[1].document.all.acttask_apptaskid.value; 
    a1=window.parent.document.all.acttask_apptaskid.value;
    if(a1!="") {if(a1.substr(a1.length-1,a1.length)!=",") window.parent.document.all.acttask_apptaskid.value=a1+",";}
    window.parent.document.all.acttask_apptaskid.value+=document.frames[1].document.all.acttask_apptaskid.value+",";
    cell.style.display="none";
   
  
    cell = row.insertCell();
     cell.innerText =window.parent.document.all.mid.value+ document.frames[1].document.all.acttask_apptaskid.value;; 
    a1=window.parent.document.all.acttask_id.value;
    if(a1!="") {if(a1.substr(a1.length-1,a1.length)!=",") window.parent.document.all.acttask_id.value=a1+",";}
    window.parent.document.all.acttask_id.value+=window.parent.document.all.mid.value+document.frames[1].document.all.acttask_apptaskid.value+",";
    cell.style.display="none";
    
 
 
 
 
    sno=document.frames[1].document.all.acttask_sno.value;;
    cell = row.insertCell();
    cell.innerText = sno;cell.style.display="none";
    a1=window.parent.document.all.acttask_sno.value;
    if(a1!="") {if(a1.substr(a1.length-1,a1.length)!=",") window.parent.document.all.acttask_sno.value=a1+",";}
    window.parent.document.all.acttask_sno.value+=sno+",";
  
    cell = row.insertCell();
    cell.innerText = document.frames[1].document.all.acttask_cname.value;;
    a1=window.parent.document.all.acttask_cname.value;
    if(a1!="") {if(a1.substr(a1.length-1,a1.length)!=",") window.parent.document.all.acttask_cname.value=a1+",";}
    window.parent.document.all.acttask_cname.value+=document.frames[1].document.all.acttask_cname.value+",";
  
    cell = row.insertCell();
    cell.innerText = document.frames[1].document.all.acttask_ctype.value;;
    a1=window.parent.document.all.acttask_ctype.value;
    if(a1!="") {if(a1.substr(a1.length-1,a1.length)!=",") window.parent.document.all.acttask_ctype.value=a1+",";}
    window.parent.document.all.acttask_ctype.value+=document.frames[1].document.all.acttask_ctype.value+",";
  
    cell = row.insertCell();
    cell.innerText = document.frames[1].document.all.acttask_require.value;;
    a1=window.parent.document.all.acttask_require.value;
    if(a1!="") {if(a1.substr(a1.length-1,a1.length)!=",") window.parent.document.all.acttask_require.value=a1+",";}
    window.parent.document.all.acttask_require.value+=document.frames[1].document.all.acttask_require.value+",";
  
    cell = row.insertCell();
    cell.innerText = sno;cell.style.display="none";
    cell.innerText = document.frames[1].document.all.acttask_descript.value;;
    a1=window.parent.document.all.acttask_descript.value;
    if(a1!="") {if(a1.substr(a1.length-1,a1.length)!=",") window.parent.document.all.acttask_descript.value=a1+",";}
    window.parent.document.all.acttask_descript.value+=document.frames[1].document.all.acttask_descript.value+",";
    

	}
	function deleteTblRow()
	{
	try{
      var row;
      var cell;
 
     tblUpdate=document.frames[0].document.getElementById("showTable");
      ss1="",ss2="",ss3="",ss4="",ss5="",ss6="",ss7="";
       
     
    if(typeof(selectedTr)!="undefined")  tblUpdate.deleteRow(selectedTr);
	for (var i = 1; i < tblUpdate.rows.length; i++) {
		for (var j = 0; j < tblUpdate.rows[i].cells.length; j++) {
			   id=tblUpdate.rows[i].cells[j].innerText;	 
			 {
			  if(j==0) ss1=ss1+id+",";
			  if(j==1) ss2=ss2+id+",";
			  if(j==2) ss3=ss3+id+",";
			  if(j==3) ss4=ss4+id+",";
			  if(j==4) ss5=ss5+id+",";
			  if(j==5) ss6=ss6+id+",";
			  if(j==6) ss7=ss7+id+",";
			 }	
		}
	}
	 window.parent.document.all.acttask_apptaskid.value=ss1  ;
	 window.parent.document.all.acttask_id.value=ss2  ;
	 
	 window.parent.document.all.acttask_sno.value=ss3  ;
     window.parent.document.all.acttask_cname.value=ss4  ;
      window.parent.document.all.acttask_ctype.value=ss5  ;
	 window.parent.document.all.acttask_require.value=ss6  ;
	 window.parent.document.all.acttask_descript.value=ss7  ;
	 
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
 window.parent.document.all.acttask_sno.value="";
 window.parent.document.all.acttask_cname.value="";
 window.parent.document.all.acttask_descript.value="";
 window.parent.document.all.acttask_ctype.value="";
 window.parent.document.all.acttask_require.value="";
 window.parent.document.all.acttask_apptaskid.value="";
  window.parent.document.all.acttask_id.value="";
         

	}
	

</Script>
 </body>
</html>
