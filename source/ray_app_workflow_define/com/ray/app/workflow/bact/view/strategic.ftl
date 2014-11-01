<!DOCTYPE HTML>
<html>
<head>
<#include "/decorator/include/include.ftl">

<script   type="text/javascript" src="${base}/wfb/blueflowui/javascript/common.js"></script>
<SCRIPT type="text/javascript">
function OpenFlowForm(oDestination,oDestination1) {
    oField1 = oDestination;
    oField2 = oDestination1; 
    wffclass = window.open("${base}/module/app/system/workflow/bform/bform_selectbform.action", "wffclass", "resizable=yes,dependent=yes,scrollbars=yes,left=90,top=45,width=" + screen.width * .7 + ",height=" + screen.height * .5);    
    wffclass.focus();
}
function OpenFlowClass(oDestination,oDestination1) {
    oField1 = oDestination;
    oField2 = oDestination1; 
    wfclass = window.open("${base}/module/app/system/workflow/bflowclass/bflowclass_selectbflowclass.action", "wfclass", "resizable=yes,dependent=yes,scrollbars=yes,left=90,top=45,width=" + screen.width * .7 + ",height=" + screen.height * .5);    
    wfclass.focus();
}

</SCRIPT>

</head>
<body onload="ldvalue()">
<form id="flowForm">
<div style="padding:8px;"><button onclick="cancelst();">清除决策内容</button></div>
<!--table  width="100%"  >
	<tr>

		<td >序号</td>
		<td >名称</td>
		<td >类型</td>
		<td >必需</td>
		<td >说明</td>
		<td >标识</td>

	</tr>
</table>
-->
		<table width="100%" id="showTable">
  	<tr>
 		<th style="text-align:left;width:40%;"> 决策内容</th><th style="text-align:left">路由名称</th>

	</tr>
</table>
</form>

<script type="text/javascript">


      var oldTr=null;
      var selectedTr;
      var len=0;
 	  var tblUpdate=null; 
	function ldvalue()
	{ 
       try{
       
       
       stname=window.parent.document.all.stname.value.split(",");
       stctx=window.parent.document.all.stctx.value.split(",");
       stcode=window.parent.document.all.stcode.value.split(",");
       
                
       stId=window.parent.document.all.stId.value.split(",");
       stName=window.parent.document.all.stName.value.split(",");
       len= stId.length;
       f1=window.parent.document.all.stId.value;
       if(f1.substr(f1.length-1,f1.length)==",") len=len-1;
       
            var row;
            var cell;
            tblUpdate= document.getElementById("showTable");	
             
                      window.parent.document.all.stid.value='';
                      window.parent.document.all.stname.value='';
                      window.parent.document.all.stctx.value='';
                      window.parent.document.all.stcode.value='';
                      window.parent.document.all.strouteid.value='';
                      window.parent.document.all.stroutename.value='';		 


	        for(i=0;i<len ;i++)
       	    {
              {
              row = tblUpdate.insertRow();
              selectedTr=row.rowIndex;
              if(oldTr!=null){oldTr.bgColor=""}
              oldTr=row;
              
              ctx=stctx[i];if( typeof(ctx) =="undefined") ctx=""; 
              code=stcode[i];if( typeof(code) =="undefined") code="";
              name=stname[i];if( typeof(name) =="undefined") name="";  
              
              
              
              cell = row.insertCell();
              cell.innerText =stId[i]; 
              cell.style.display="none";

               cell = row.insertCell();
               radio = document.createElement("<INPUT  TYPE='text' NAME='ctx' size='32' class='fullwidth' VALUE='"+ctx+"' >") 
               cell.appendChild(radio); 
                
    
               cell = row.insertCell();
               cell.innerText =stName[i]; 

                       window.parent.document.all.stid.value+=stId[i]+','; 
                       window.parent.document.all.stname.value+=  name +','; 
                       window.parent.document.all.stctx.value+=  ctx +',';
                       window.parent.document.all.stcode.value+=  code +','; 
                       window.parent.document.all.strouteid.value+=stId[i]+','; 		 
                       window.parent.document.all.stroutename.value+=stName[i]+','; 		 

              }
	        }
	        
	        
	      }catch(Exception){}
	   
	}


function cancelst()
{
try{
   stn= document.getElementsByTagName("input"); 
   for(i=0;i<stn.length;i++)
   stn[i].value="";
}catch(Exception){}   
}	
</script>	

</body>

</html>

