<!DOCTYPE HTML>
<html>
<head>
<#include "/decorator/include/include.ftl">
	<script type="text/javascript">
		var ownerctx="",ownertype="",ownername="";
		var page=new Array(),type="PERSON";;

		page[0]="${base}/module/app/system/workflow/select/select_selectdept.action";
		page[1]="${base}/module/app/system/workflow/select/select_selectdept.action";
		<#--
		page[2]="${base}/module/app/system/workflow/select/select_selectworkgroup.action";
		-->
		page[3]="${base}/module/app/system/workflow/select/select_selectrole.action";
		page[4]="${base}/module/app/system/workflow/select/select_selectperson.action";
		
		page[5]="${base}/module/app/system/workflow/define/bact_formal.html";

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
		  }
		}
	</script>
</head>
<body onload="ldvalue()">
	<form Method="Post" action="" name="form1"  >
		<table width="98%" height="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td  >
					<div class="oSpan">所选读者：</div>
					<iframe src="${base}/module/app/system/workflow/bflow/bflow_owner.action" id="objlist" height="300" width="100%" name="objlist" frameborder="0" style="border:solid 1px #ccc;"
						scrolling="auto"></iframe>
				</td>
				<td>
					<button onclick="addvalue();">　　添加</button>
					<div style="height:10px;"></div>
					<button onclick="deleteTblRow()">　　删除</button>
					<div style="height:10px;"></div>
					<button onclick="deleteTblAll()">删除全部</button>
				</td>
				<td  >
					<div class="oSpan">待选读者：</div>
					<select id="obj" onchange="detail_page(this.value);">
						<option value="人员">人员</option>
						<option value="角色">角色</option>
						<#--
						<option value="工作组">工作组</option>
						-->
						<option value="机构（部门）">机构（部门）</option>
					</select> 
					<iframe id="objectlist" height="270" width="100%" scrolling="auto" frameborder="0" 
						src="${base}/module/app/system/workflow/select/select_selectperson.action" name="objectlist"></iframe>
				</td>
			</tr>
	</table>
	</form>
	<script type="text/javascript">
		ownerctx=window.parent.document.all.flowreader_readctx.value;
		ownertype=window.parent.document.all.flowreader_ctype.value;
		ownername=window.parent.document.all.flowreader_cname.value;
		var oldTr=null;
		var selectedTr;

	 	function ldvalue()
		{
	     try{ var row;
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
		     cell.innerText =oname[i];
		     
    		cell = row.insertCell();
		    cell.innerText =otype[i];
		    cell = row.insertCell();
		    cell.innerText = octx[i];cell.style.display="none";
		    eval("row.onclick=function(){oldTr=this;selectedTr=this.rowIndex;}") 
		}
	  }catch(Exception){} 
	}
	
	function addvalue()
	{
      var row;
      var cell;
      if  (type=="FORMULA")  document.frames[1].document.all.objid.value=document.frames[1].document.all.objname.value;
      var id= document.frames[1].document.all.objid.value;
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
    cell.innerText = document.frames[1].document.all.objname.value;;
    a1=window.parent.document.all.flowreader_cname.value;
    if(a1!="") {if(a1.substr(a1.length-1,a1.length)!=",") window.parent.document.all.flowreader_cname.value=a1+",";}
    window.parent.document.all.flowreader_cname.value+=document.frames[1].document.all.objname.value+",";
  
    cell = row.insertCell();
    cell.innerText =    type ;
    a1=window.parent.document.all.flowreader_ctype.value;
    if(a1!="") {if(a1.substr(a1.length-1,a1.length)!=",") window.parent.document.all.flowreader_ctype.value=a1+",";}
    window.parent.document.all.flowreader_ctype.value+=type+",";
  
     cell = row.insertCell();
    cell.innerText = document.frames[1].document.all.objid.value;;cell.style.display="none";
    a1=window.parent.document.all.flowreader_readctx.value;
    if(a1!="") {if(a1.substr(a1.length-1,a1.length)!=",") window.parent.document.all.flowreader_readctx.value=a1+",";}
    window.parent.document.all.flowreader_readctx.value+=document.frames[1].document.all.objid.value+",";
	}
	function deleteTblRow()
	{
      try{
      var row;
      var cell;
 
     tblUpdate=document.frames[0].document.getElementById("showTable");
     if(typeof(selectedTr)!="undefined") tblUpdate.deleteRow(selectedTr);
  	
    ss1="",ss2="",ss3="" ;
       
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
	 window.parent.document.all.flowreader_readctx.value=ss3 ;
     window.parent.document.all.flowreader_ctype.value=ss2 ;
      window.parent.document.all.flowreader_cname.value=ss1 ;
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
        window.parent.document.all.flowreader_readctx.value="";
        window.parent.document.all.flowreader_ctype.value="";
        window.parent.document.all.flowreader_cname.value="";
        }
</script>
</body>
</html>