<!DOCTYPE HTML>
<html>
<head>
<#include "/decorator/include/include.ftl">
　　
<script type="text/javascript">
var ownerctx="",ownertype="",ownername="";


var page=new Array(),type="ROLE";;
page[0]="${base}/module/app/system/workflow/select/select_selectdept.action";
page[1]="${base}/module/app/system/workflow/select/select_selectdept.action";
<#--
page[2]="${base}/module/app/system/workflow/select/select_selectworkgroup.action";
-->
page[3]="${base}/module/app/system/workflow/select/select_selectrole.action";
page[4]="${base}/module/app/system/workflow/select/select_selectperson.action";
page[5]="${base}/module/app/system/workflow/bact/bact_formal.action";
page[6]="${base}/module/app/system/workflow/select/select_selectrole.action";


function detail_page(url)
{
   
  switch (url) {
  case "机构（部门）":window.frames[1].location=page[0];
               type="ORG";
              break;
  case "部门":window.frames[1].location=page[1];
               type="DEPT";break;
  <#--
  case "工作组":window.frames[1].location=page[2];
               type="WORKGROUP";break;
  -->
  case "角色":window.frames[1].location=page[3];
              type="ROLE";break;
  case "人员":window.frames[1].location=page[4];
               type="PERSON";break;
  case "公式":window.frames[1].location=page[5];
               type="FORMULA";break;
  case "部门角色":window.frames[1].location=page[6];
               type="DEPTROLE";break;
  }
}
</script>
</head>
<body onload="ldvalue()">
<input name="outstyleValue" type="hidden" value="N">
<input name="outstyleText" type="hidden" value="否">

<form method="post" action="" name="form1">
<table width="100%" height="320" border="0" cellpadding="0" cellspacing="0" >
	<tbody>
		<tr>
			<td valign="top">
			<div class="oSpan"> 所选所有者：</div>
			<iframe src="${base}/module/app/system/workflow/bact/bact_nodeowner.action" id="objlist" height="300" width="100%" name="objlist" scrolling="auto" frameborder="0" style="border:solid 1px #ccc;"></iframe></td>

			<td width="1%">
			<button onclick="addvalue();">　　添加</button>
			<div style="height:10px;"></div>
			<button onclick="deleteTblRow()">　　删除</button>
			<div style="height:10px;"></div>
			<button onclick="deleteTblAll()">删除全部</button>
			</td>


			<td valign="top">
			<div id="td1">
			<div class="oSpan">待选所有者：
			
			<select id="obj"
				onchange="detail_page(this.value);if(this.value=='人员') document.all.outright1.style.display=''; else document.all.outright1.style.display='none';">
				<option value="角色">角色</option>
				<option value="人员">人员</option>
				<#--
				<option value="工作组">工作组</option>
				-->
				<option value="机构（部门）">机构（部门）</option>
				<option value="部门角色">部门角色</option>
				<option value="公式">公式</option>
			</select> <span id="outright1" style="display: none"> 具有转出权：<select
				id="sfzc"
				onchange="if(this.value=='Y') {document.all.outstyleValue.value='Y';document.all.outstyleText.value='是';} else {document.all.outstyleValue.value='N';document.all.outstyleText.value='否';} ">
				<option value="N">否</option>
				<option value="Y">是</option>
			</select> </span> <iframe id="objectlist" border="1" height="300" width="100%"
				scrolling="auto"
				src="${base}/module/app/system/workflow/select/select_selectrole.action"
				name="objectlist"  frameborder="0"></iframe>
			
			</div>
			
			</div>
			</td>
<#--
			<td valign="top">
			<div id="td2">
			<div class="oSpan">待选所有者：
			<select id="obj">
				<option value="人员">人员</option>
			</select> 具有转出权：<select id="sfzc"
				onchange="if(this.value=='Y') {document.all.outstyleValue.value='Y';document.all.outstyleText.value='是';} else {document.all.outstyleValue.value='N';document.all.outstyleText.value='否';} ">
				<option value="N">否</option>
				<option value="Y">是</option>
			</select> <iframe id="objectlist1" height="300" border="0" width="100%"
				scrolling="auto"
				src="${base}/module/app/system/workflow/select/select_selectperson.action"
				name="objectlist1" frameborder="0"></iframe>
			</div>
			</div>
			</td>
-->			

		</tr>
	</tbody>
</table>
</form>


<script type="text/javascript">
      var oldTr=null;
      var selectedTr;
	function ldvalue()
	{
      try{
           ownerctx=window.parent.document.all.actionowner_ownerctx.value;
           ownertype=window.parent.document.all.actionowner_ctype.value;
           ownername=window.parent.document.all.actionowner_cname.value;
           owneroutstyle=window.parent.document.all.actionowner_outstyle.value;
      
      var row;
      var cell;
	
	   octx=ownerctx.split(",");
	   otype=ownertype.split(",");
	   oname=ownername.split(",");
	   
	   ostyle=owneroutstyle.split(",");

       if((ownerctx.substr(ownerctx.length-1,ownerctx.length))==",") ll=octx.length-1; 
       else ll=octx.length; 

	   
       tblUpdate=document.frames[0].document.getElementById("showTable");	   
        
       if(ownerctx!="")	   
	   for(i=0;i<ll;i++)
	   {
	   
     row = tblUpdate.insertRow();
     selectedTr=row.rowIndex;
     if(oldTr!=null){oldTr.bgColor=""}
     oldTr=row;
     
    cell = row.insertCell();
    cell.innerText =oname[i];
     
   cell = row.insertCell();
    cell.innerText =otype[i];
    cell = row.insertCell();
    if(ostyle[i]=="Y") cell.innerText ="是";   
    if(ostyle[i]=="N")  cell.innerText ="否";
    cell = row.insertCell();
    cell.innerText = octx[i];
    cell.style.display="none";
    
  eval("row.onclick=function(){ oldTr=this;selectedTr=this.rowIndex;}")  
    
    
	     
	   }
	   }catch(Exception){}
	   
	}

	/*	
	function addvalue()
	{
	      var row;
    	  var cell;
	      var id,name;
      
       if((window.parent.hdltype=="多人并行")||(window.parent.hdltype=="指定专人"))    
       {
        type="PERSON";
        
       id= document.frames[2].document.all.objid.value; 
       name= document.frames[2].document.all.objname.value;
       
       }
       else 
       {
       if  (type=="FORMULA") document.frames[1].document.all.objid.value=document.frames[1].document.all.objname.value;
       if(type=="PERSON") 
       {
       id= document.frames[1].document.all.objid.value;
       name= document.frames[1].document.all.objname.value;
       
       }
       else{
       id= document.frames[1].document.all.objid.value;
       name= document.frames[1].document.all.objname.value;
       }
       }
       
     if(id==""){ alert("请先选择！");return}
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
     oldTr=row;
     
     row.attachEvent("onclick",function(){
     oldTr=row;selectedTr=row.rowIndex; });
 
  
    cell = row.insertCell();
    cell.innerText = name;;
    a1=window.parent.document.all.actionowner_cname.value;
    if(a1!="") {if(a1.substr(a1.length-1,a1.length)!=",") window.parent.document.all.actionowner_cname.value=a1+",";}
    window.parent.document.all.actionowner_cname.value+= name +",";
    
    
    cell = row.insertCell();
    cell.innerText =    type ;
    a1=window.parent.document.all.actionowner_ctype.value;
    if(a1!="") {if(a1.substr(a1.length-1,a1.length)!=",") window.parent.document.all.actionowner_ctype.value=a1+",";}
    window.parent.document.all.actionowner_ctype.value+=type+",";
  
  
    cell = row.insertCell();
    cell.innerText =  document.all.outstyleText.value;;
    a1=window.parent.document.all.actionowner_outstyle.value;
    if(a1!="") {if(a1.substr(a1.length-1,a1.length)!=",") window.parent.document.all.actionowner_outstyle.value=a1+",";}
    window.parent.document.all.actionowner_outstyle.value+= document.all.outstyleValue.value+",";
   
    cell = row.insertCell();
    cell.innerText =  id ;;
    a1=window.parent.document.all.actionowner_ownerctx.value;
    if(a1!="") {if(a1.substr(a1.length-1,a1.length)!=",") window.parent.document.all.actionowner_ownerctx.value=a1+",";}
    window.parent.document.all.actionowner_ownerctx.value+= id +",";
     cell.style.display="none";
    

	}
	*/
	


function addvalue()
{
	var row;
	var cell;
	var id,name;
      
	if ((window.parent.hdltype=="多人并行"))    
	{
		type="PERSON";
		id= document.frames[2].document.all.objid.value; 
		name= document.frames[2].document.all.objname.value;
	}
	else 
	{
		if  (type=="FORMULA") 
		{
			document.frames[1].document.all.objid.value=document.frames[1].document.all.objname.value;
		}

       	if(type=="PERSON") 
		{
			id= document.frames[1].document.all.objid.value;
			name= document.frames[1].document.all.objname.value;
		}
		else
		{
			id= document.frames[1].document.all.objid.value;
			name= document.frames[1].document.all.objname.value;
		}
	}
       
    if(id=="")
    { 
    	alert("请先选择！");
    	return
    }
    
    tblUpdate=document.frames[0].document.getElementById("showTable");
	for (var i = 0; i < tblUpdate.rows.length; i++) 
	{
		for (var j = 0; j < tblUpdate.rows[i].cells.length; j++) 
		{
			if(id==tblUpdate.rows[i].cells[j].innerText)	 
			{
				alert("这个对象已经加入！");return ;
			}	
		}
	}
     
	tblUpdate=document.frames[0].document.getElementById("showTable");
	row = tblUpdate.insertRow();
	selectedTr=row.rowIndex;
	if(oldTr!=null)
	{
		oldTr.bgColor=""
	}

	oldTr=row;
     
	row.attachEvent("onclick",function() { oldTr=row;selectedTr=row.rowIndex; });
  
    cell = row.insertCell();
    cell.innerText = name;
    a1=window.parent.document.all.actionowner_cname.value;

    if(a1!="") 
    {
    	if(a1.substr(a1.length-1,a1.length)!=",") 
    	{
	    	window.parent.document.all.actionowner_cname.value=a1+",";
	    }
    }

    window.parent.document.all.actionowner_cname.value+= name +",";
    
    cell = row.insertCell();
    cell.innerText =    type ;
    a1=window.parent.document.all.actionowner_ctype.value;
    if(a1!="") 
    {
    	if(a1.substr(a1.length-1,a1.length)!=",") 
    	{
    		window.parent.document.all.actionowner_ctype.value=a1+",";
    	}
    }

    window.parent.document.all.actionowner_ctype.value+=type+",";
  
    cell = row.insertCell();
    cell.innerText =  document.all.outstyleText.value;;
    a1=window.parent.document.all.actionowner_outstyle.value;
	if(a1!="") 
    {
    	if(a1.substr(a1.length-1,a1.length)!=",") 
    	{
    		window.parent.document.all.actionowner_outstyle.value=a1+",";
    	}
    }
    
    window.parent.document.all.actionowner_outstyle.value+= document.all.outstyleValue.value+",";
   
    cell = row.insertCell();
    cell.innerText =  id ;;
    a1=window.parent.document.all.actionowner_ownerctx.value;
    if(a1!="") 
    {
    	if(a1.substr(a1.length-1,a1.length)!=",") 
    	{
    		window.parent.document.all.actionowner_ownerctx.value=a1+",";
    	}
    }
    
	window.parent.document.all.actionowner_ownerctx.value+= id +",";
	cell.style.display="none";
}
	
	
	function deleteTblRow()
	{
     try{
      var row;
      var cell;
 
     tblUpdate=document.frames[0].document.getElementById("showTable");
     
     if(typeof(selectedTr)!="undefined") tblUpdate.deleteRow(selectedTr);
  	
     ss1="",ss2="",ss3="",ss4="";
       
     
      
  	
	for (var i = 1; i < tblUpdate.rows.length; i++) {
		for (var j = 0; j < tblUpdate.rows[i].cells.length; j++) {
			   id=tblUpdate.rows[i].cells[j].innerText;	 
			 {
			  if(j==0) ss1=ss1+id+",";
			  if(j==1) ss2=ss2+id+",";
			  if(j==2) {if(id=="是") id="Y"; else id="N";ss4=ss4+id+",";}
			  if(j==3) ss3=ss3+id+",";
			 }	
		}
	}
	 window.parent.document.all.actionowner_ownerctx.value=ss3 ;
     window.parent.document.all.actionowner_ctype.value=ss2 ;
      window.parent.document.all.actionowner_cname.value=ss1 ;
	 window.parent.document.all.actionowner_outstyle.value=ss4 ; 	
	 
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
        window.parent.document.all.actionowner_ownerctx.value="";
        window.parent.document.all.actionowner_ctype.value="";
        window.parent.document.all.actionowner_cname.value="";
        window.parent.document.all.actionowner_outstyle.value="";
        

	}
	
 </script>


</body>
</html>
