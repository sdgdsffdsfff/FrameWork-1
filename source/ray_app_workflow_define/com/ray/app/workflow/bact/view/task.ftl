<!DOCTYPE HTML>
<html>
<head>
<#include "/decorator/include/include.ftl">

<script   type="text/javascript" src="${base}/wfb/blueflowui/javascript/common.js"></script>
<SCRIPT type="text/javascript">
 var fs=0;

function OpenFlowForm(oDestination,oDestination1) {
clsid=window.parent.parent.document.all.classid.value; 
    oField1 = oDestination;
    oField2 = oDestination1; 
    wffclass = window.open("<%=PathToolKit.getRootPath(request)%>/WFBFlowClassForm?ACTION=SelectTask&classid="+clsid, "wffclass", "resizable=yes,dependent=yes,scrollbars=yes,left=90,top=45,width=" + screen.width * .7 + ",height=" + screen.height * .5);    
    
    wffclass.focus();
}
function OpenFlowClass(oDestination,oDestination1) {
    oField1 = oDestination;
    oField2 = oDestination1; 
    wfclass = window.open("<%=PathToolKit.getRootPath(request)%>/WFBFlowClassForm?ACTION=SelectBFlowClass", "wfclass", "resizable=yes,dependent=yes,scrollbars=yes,left=90,top=45,width=" + screen.width * .7 + ",height=" + screen.height * .5);    
    wfclass.focus();
}

function onld()
{
  //alert(window.parent.parent.document.all.classid.value);
document.frames[0].src="<%=PathToolKit.getRootPath(request)%>/WFBFlowClassForm?ACTION=SelectTask&classid="+window.parent.parent.document.all.classid.value; 
  
 
}
</SCRIPT>
 
</head>
<body>
 <form id="flowForm"><input type="hidden" name="acttask_sno"	 >
 <input type="hidden"	name="acttask_apptaskid" value="" />
 <input type="hidden"		name="acttask_cname" value="" readonly />
 <input type="hidden"		name="acttask_descript" value="" readonly />
<table width="100%" id="showTable">
	<tr >
		<td align="right" valign="top" >任务类型： </td><td>
		<SELECT name="acttask_ctype">
		<OPTION value="人工">人工</OPTION>
		<OPTION value="自动">自动</OPTION>
		</SELECT></td>
		<td align="right" valign="top" id="ifnec1"  >是否必需：</td><td id="ifnec2"  >
		<SELECT name="acttask_require">
		<OPTION value="非必需">非必需</OPTION>
		<OPTION value="必需">必需</OPTION>
		</SELECT></td>
	</tr>
	<tr>
		<td align="left" valign="top"  colspan="4" width="100%" height="100%" >
		<IFRAME id="seltask" width="100%"  height="270" scrolling="auto" frameborder="0"  ></IFRAME>
		 </td>
	</tr>
 </table>

</form>

<SCRIPT type="text/javascript">
try{ 
tt=document.getElementById("seltask");
tt.src="";
//alert(window.parent.parent.document.all.classid.value); 
tt.src="${base}/module/app/system/workflow/bact/bact_selecttask.action?classid="+window.parent.parent.document.all.classid.value; 
}catch(Exception){}

function swittask()
{
  hddtype= window.parent.parent.document.all.ctype1.innerText;
   
 //alert(hddtype);
 if(hddtype=="普通") 
 {
   document.all.ifnec1.style.display="";
   document.all.ifnec2.style.display="";
  
   
  
 }
 else
 { 
  
  
   document.all.ifnec1.style.display="none";;
   document.all.ifnec2.style.display="none";;
 }
 }
 
</SCRIPT>
</body>

</html>

