<!DOCTYPE HTML>
<html>
<head>
<#include "/decorator/include/include.ftl">
<title>流程 </title>
<script type="text/javascript" src="${base}/wfb/blueflowui/javascript/common.js"></script>
<script>
function OpenFlowForm(oDestination,oDestination1) {
    oField1 = oDestination;
    oField2 = oDestination1; 
    wffclass = window.open("<%=PathToolKit.getRootPath(request)%>/WFBFormForm?ACTION=SelectBForm", "wffclass", "resizable=yes,dependent=yes,scrollbars=yes,left=90,top=45,width=" + screen.width * .7 + ",height=" + screen.height * .5);    
    wffclass.focus();
}
function OpenFlowClass(oDestination,oDestination1) {
    oField1 = oDestination;
    oField2 = oDestination1; 
    wfclass = window.open("<%=PathToolKit.getRootPath(request)%>/WFBFlowClassForm?ACTION=SelectBFlowClass", "wfclass", "resizable=yes,dependent=yes,scrollbars=yes,left=90,top=45,width=" + screen.width * .7 + ",height=" + screen.height * .5);    
    wfclass.focus();
}

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
<body onload="ldvalue()">
<form id="flowForm">
<input type="hidden" name="line_acttaskid">
<input type="hidden" name="line_name">
<input type="hidden" name="line_number">
<select name="line_require" style="display: none;">
	<option value="非必需">非必需</option>
	<option value="必需" selected="selected">必需</option >
</select>
		<table width="100%" id="showTable">
<tbody><tr><th>任务名称</th></tr></tbody>
 </table>
	
</form>

<script>
      var oldTr=null;
      var selectedTr;
//============从action中传递过来的对象中取值==============
	function ldvalue()
	{
	
ownerid=window.parent.parent.document.all.nodeTaskListId.value;
ownername=window.parent.parent.document.all.nodeTaskListName.value;
atid=window.parent.parent.document.all.nodeActTaskId.value;
 	
      var row;
      var cell;
	
	   octx=ownerid.split(",");
	   otype=ownername.split(",");
	   oid=atid.split(",");
	    
       tblUpdate= document.getElementById("showTable");	  
       if((atid.substr(atid.length-1,atid.length))==",") ll=oid.length-1; 
       else ll=oid.length; 

 
	   for(i=0;i<ll;i++)
	   {
	   
     row = tblUpdate.insertRow();
     selectedTr=row.rowIndex;
     if(oldTr!=null){oldTr.bgColor=""}
     oldTr=row;
     
    cell = row.insertCell();
   cell.innerText =oid[i];
   cell.style.display="none";
     
    
   cell = row.insertCell();
   cell.innerText =octx[i]; 
    cell.style.display="none"; 
     
    cell = row.insertCell();
    cell.innerText =  otype[i];
    
     
    
    eval(" row.onclick=function(){ oldTr=this;selectedTr=this.rowIndex; document.all.line_number.value=selectedTr;document.all.line_acttaskid.value=this.cells[0].innerText;document.all.line_name.value=this.cells[2].innerText;;}")  
	     
	   }
	  document.all.line_number.value=selectedTr; 
	   document.all.line_acttaskid.value= oid[i-1];
	   document.all.line_name.value= otype[i-1];
	}

</script>

</body>
 
</html>

