<!DOCTYPE HTML>
<html>
<head>
<#include "/decorator/include/include.ftl">

<script type="text/javascript" src="${base}/wfb/blueflowui/javascript/common.js"></script>
<SCRIPT type="text/javascript">
 var fs=0;
 //--==========================
 
function fieldProcess1()
{
     var oldTr=null;
      var selectedTr;
      var form=document.getElementById("flowForm");

try{
          actfield_id=  document.all.fieldid.value;
          actfield_cname= document.all.fieldcname.value;
          actfield_ename= document.all.fieldename.value;
          actfield_ctype= document.all.fieldtype.value;
          actfield_access= document.all.fieldaccess.value;
      var row;
      var cell;
	
         fieldid=actfield_id.split(",");
         cname=actfield_cname.split(",");
         ename=actfield_ename.split(",");
         access=actfield_access.split(",");
         ctype=actfield_ctype.split(","); 

	    
tblUpdate= document.getElementById("showTable");	
        	
     ll=fieldid.length; 
     
     if(actfield_id!="")      
	   for(i=0;i<ll;i++)
   
	    
	   {
	   
     row = tblUpdate.insertRow();
      
     selectedTr=row.rowIndex;
     if(oldTr!=null){oldTr.bgColor=""}
     oldTr=row;
      
     
     cell = row.insertCell();
      cell.innerText =fieldid[i];
       cell.style.display="none";
       
    cell = row.insertCell();
    cell.innerText =cname[i];
    
    cell = row.insertCell();
    cell.innerText =ename[i];
    
    
   cell = row.insertCell();
    cell.innerText =ctype[i];
   cell = row.insertCell();
   accessRight =access[i];
    
     
     select=document.createElement("select");
     select.id="slct"+(i+1);
    
     ooption=document.createElement("option");
     ooption.value="读";
     ooption.text="读";
     select.add(ooption);
     ooption=document.createElement("option");
     ooption.value="写";
     ooption.text="写";
     select.add(ooption);
      cell.appendChild(select);
      for(j=0;j<select.length;j++)
     {
       if(accessRight==select.options[j].value) 
       {
          select.options.selectedIndex=j;
          break;
       }
     }
     
     
  

    
    eval("row.onclick=function(){oldTr=this;selectedTr=this.rowIndex;}")
         
    
	     
	   }
	   }catch(Exception){}
  
}
 //-============================

function getParent(el, pTagName) 
{ 
    // getParent object	

	while (el && (!el.tagName || el.tagName.toLowerCase() != pTagName.toLowerCase()) )
		{
			el = el.parentNode;
		}
		 
	return el;
}
	function deleteTblAll()
	{
		tblUpdate=document.getElementById("showTable");
		
       try{
       
         while(tblUpdate!=null)
        {
        
          tblUpdate.deleteRow(1);	
          tblUpdate= document.getElementById("showTable");
          
        } 
        } catch(Exception){}

	}

function OpenFlowForm() { 
  oldformid=document.all.formid.value;
  clsid=window.parent.document.all.classid.value; 
	var el = getParent(window.event.srcElement,"button");
	var flowclass = window.showModalDialog("${base}/module/app/system/workflow/bflow/bflow_selectbform.action?_searchname=workflow.bflow.selectbform&classid="+clsid,el,"status:off;help:0;dialogWidth:"+pub_width_small+"px;dialogHeight:"+pub_height_small+"px;edge:sunken; ");
   if(typeof(flowclass)!="undefined") {
    document.all.formid.value=flowclass.id;
    document.all.formname.value=flowclass.name;
    window.parent.document.all.formid.value=flowclass.id;
    window.parent.document.all.formname.value=flowclass.name;
    
     if(oldformid!=document.all.formid.value)
    {
       tempfield=flowclass.field;
       tempfield1=tempfield.split(":");
 
      document.all.fieldid.value=tempfield1[0];
      document.all.fieldcname.value=tempfield1[1];
      document.all.fieldename.value=tempfield1[2];
      document.all.fieldtype.value=tempfield1[3];
      document.all.fieldaccess.value=tempfield1[4];
      deleteTblAll();
      fieldProcess1();
      document.all.rwtype.value="全读";
      document.all.showTable.style.display="none";
     } 
    }
}
function OpenFlowClass(oDestination,oDestination1) {
    oField1 = oDestination;
    oField2 = oDestination1; 
    wfclass = window.open("${base}/module/app/system/workflow/bflowclass/bflowclass_selectbflowclass.action", "wfclass", "resizable=yes,dependent=yes,scrollbars=yes,left=90,top=45,width=" + screen.width * .7 + ",height=" + screen.height * .5);    
    wfclass.focus();
}

function changeField(val)
{
    window.parent.document.all.rwtype.value=val;
    if(val=='读写')  document.all.showTable.style.display="";
    else document.all.showTable.style.display="none";
}
//-------------
function ldvalue()
{
try{
actfield_id=window.parent.document.all.actfield_id.value;
actfield_cname=window.parent.document.all.actfield_cname.value;
actfield_ename=window.parent.document.all.actfield_ename.value;
actfield_access=window.parent.document.all.actfield_access.value;
actfield_ctype=window.parent.document.all.actfield_ctype.value; 
      var row;
      var cell;
	
fieldid=actfield_id.split(",");
cname=actfield_cname.split(",");
ename=actfield_ename.split(",");
access=actfield_access.split(",");
ctype=actfield_ctype.split(","); 


	    
tblUpdate=document.frames[0].document.getElementById("showTable");	
        	
     ll=fieldid.length; 
     if(actfield_id!="")      
	   for(i=0;i<ll;i++)
   
	    
	   {
	   
     row = tblUpdate.insertRow();
      
     selectedTr=row.rowIndex;
     if(oldTr!=null){oldTr.bgColor=""}
     oldTr=row;
     
     
     cell = row.insertCell();
      cell.innerText =fieldid[i];
       cell.style.display="none";
       
    cell = row.insertCell();
    cell.innerText =cname[i];
    
    cell = row.insertCell();
    cell.innerText =ename[i];
    
   cell = row.insertCell();
    cell.innerText =access[i];
    
   cell = row.insertCell();
    cell.innerText =ctype[i];
    
    eval("row.onclick=function(){oldTr=this;selectedTr=this.rowIndex;}")
        
    
	     
	   }
	   }catch(Exception){}
	   
	}

//-------------
function fieldProcess()
{
     var oldTr=null;
      var selectedTr;
      var form=document.getElementById("flowForm");

try{
         
          actfield_id=window.parent.document.all.fieldid.value;
          actfield_cname=window.parent.document.all.fieldcname.value;
          actfield_ename=window.parent.document.all.fieldename.value;
          actfield_ctype=window.parent.document.all.fieldtype.value;
          actfield_access=window.parent.document.all.fieldaccess.value;
          
          if(actfield_id=="")//初始值
          {
          actfield_id=window.parent.document.all.fieldid1.value;
          actfield_cname=window.parent.document.all.fieldcname1.value;
          actfield_ename=window.parent.document.all.fieldename1.value;
          actfield_ctype=window.parent.document.all.fieldtype1.value;
          actfield_access=window.parent.document.all.fieldaccess1.value;
          
           }
           
      var row;
      var cell;
	
         fieldid=actfield_id.split(",");
         cname=actfield_cname.split(",");
         ename=actfield_ename.split(",");
         access=actfield_access.split(",");
         ctype=actfield_ctype.split(","); 


	    
tblUpdate= document.getElementById("showTable");	
        	
     ll=fieldid.length; 
     
     if(actfield_id!="")      
	   for(i=0;i<ll;i++)
   
	    
	   {
	   
     row = tblUpdate.insertRow();
      
     selectedTr=row.rowIndex;
     if(oldTr!=null){oldTr.bgColor=""}
      oldTr=row;
     
     
     cell = row.insertCell();
      cell.innerText =fieldid[i];
       cell.style.display="none";
       
    cell = row.insertCell();
    cell.innerText =cname[i];
    
    cell = row.insertCell();
    cell.innerText =ename[i];
    
    
   cell = row.insertCell();
    cell.innerText =ctype[i];
   cell = row.insertCell();
   accessRight =access[i];
    
     
     select=document.createElement("select");
     select.id="slct"+(i+1);
    
     ooption=document.createElement("option");
     ooption.value="读";
     ooption.text="读";
     select.add(ooption);
     ooption=document.createElement("option");
     ooption.value="写";
     ooption.text="写";
     select.add(ooption);
      cell.appendChild(select);
      for(j=0;j<select.length;j++)
     {
       if(accessRight==select.options[j].value) 
       {
          select.options.selectedIndex=j;
          break;
       }
     }
     
     
  

    
    eval("row.onclick=function(){oldTr=this;selectedTr=this.rowIndex;}")
         
    
	     
	   }
	   }catch(Exception){}
  
}


</SCRIPT>
</head>
<body onload="ld();fieldProcess()">
<form id="flowForm">

<table>
     <br>  
	<tr>
		<td align="right" valign="top">表单名称:</td>
		<td valign="top" align="left"><input type="text" name="formname"  value="" readonly="readonly"  size="30"/><input type="hidden" name="formid"   ><BUTTON onclick='window.fs=0;OpenFlowForm(); '>选择</BUTTON></td>
	</tr>
	<tr>
		<td align="right" valign="top">读写类型:</td>
		<td valign="top" align="left">
			<#assign objs = stack.findValue("@com.ray.app.query.function.view.SQLSelect@get_data(\"select new map(t.dtext as ctext,t.dvalue as cvalue) from Dictionary t where t.dkey='app.workflow.act.rwtype'\")") >
			<#assign finalvalue = "">
			<#assign finaltext = "">
			<#list objs as obj>
			<#if finalvalue =="">
			<#assign finalvalue = obj.cvalue>
			<#assign finaltext = obj.ctext>
			<#else>
			<#assign finalvalue = finalvalue + "||" + obj.cvalue>
			<#assign finaltext = finaltext + "||" + obj.ctext>
			</#if>
			</#list>
			<span class="selectSpan" id="sel_rwtype">
			<input type="hidden" id="h_rwtype" name="rwtype" value="" >
			<input id="actrwtype" class="select" data-options="<#if finaltext!="">${finaltext}</#if>" data-values="<#if finalvalue!="">${finalvalue}</#if>" data-default="">
			</span>
		</td>
	</tr>


 </table>
 
<table width="100%" id="showTable" align="center" rules="rows" 
			frame="hsides"
			style="border: none; border-bottom: solid 2px #369; border-top: solid 2px #369;display:none; ">
	<tr>

		<th nowrap="nowrap">中文名称</th>
		<th nowrap="nowrap">英文名称</th>
		<th nowrap="nowrap">字段显示风格</th>
		<th nowrap="nowrap">读写类型</th>
 
	</tr>

</table>
 <input type="hidden" name="fieldid"  /><input type="hidden" name="fieldcname"  /><input type="hidden" name="fieldename"  /><input type="hidden" name="fieldtype"  /><input type="hidden" name="fieldaccess"  />
</form>
<SCRIPT type="text/javascript">
function ld()
{
try
{
document.forms[0].formid.value=window.parent.document.all.formid.value;
document.forms[0].formname.value=window.parent.document.all.formname.value;
document.forms[0].rwtype.value=window.parent.document.all.rwtype.value;
$('#h_rwtype').val(window.parent.document.all.rwtype.value);
$('#actrwtype').attr('data-default',window.parent.document.all.rwtype.value);
if($('#h_rwtype').val()=="读写") document.all.showTable.style.display="";
else document.all.showTable.style.display="none";

}catch(Exception){ }
}
 function window.onerror()
{ 
	return true
}

$('#sel_rwtype').bind('gchange',function()
{
	var rwtype=$('#actrwtype').val();
	changeField(rwtype);
})
</SCRIPT>
</body>

</html>

