<!DOCTYPE HTML>
<html>
<head>
<#include "/decorator/include/include.ftl">
<script   type="text/javascript" src="${base}/wfb/blueflowui/javascript/common.js"></script>
<script   type="text/javascript" src="${base}/wfb/blueflowui/javascript/commonvalidate.js"></script>
<script type="text/javascript">
 var strategy="";
 //人员选择专用  
 
 function trim(strText){ 
 try
 { 
  while (strText.substring(0,1) == ' ') 
    strText = strText.substring(1, strText.length);
  while (strText.substring(strText.length-1,strText.length) == ' ')
    strText = strText.substring(0, strText.length-1);
  return strText;
  }catch(Exception){return "";}
}
 
var hdltype="";  
 
/* 
function swit()
{ 
	 if(hdltype=="指定专人") 
	 {
		  window.frames[1].document.all.td1.style.display="none";
		  
		  window.frames[1].document.all.td2.style.display="";;
	  
	 }
	 else
	 { 
		  window.frames[1].document.all.td1.style.display="";
		  
		  window.frames[1].document.all.td2.style.display="none";;
	 }
 }
*/
 
function swit()
{ 
	  window.frames[1].document.all.td1.style.display="";
	  window.frames[1].document.all.td2.style.display="none";	
}
 


function swittask()
{
	  hddtype=  "${(data.bact.handletype)!}";
	   
	 //alert(hddtype);
	 if(hddtype=="普通") 
	 {
	   window.frames[2].window.frames[1].document.all.ifnec1.style.display="";
	   window.frames[2].window.frames[1].document.all.ifnec2.style.display="";
	 }
	 else
	 { 
	   window.frames[2].window.frames[1].document.all.ifnec1.style.display="none";
	   window.frames[2].window.frames[1].document.all.ifnec2.style.display="none";
	 }
}

/*puTabsTB*/
$(function(){
//////////////////////////
var puTabsTBContent=$('#puTabsTBContent');
puTabsTBContent.find('li:first').show();

$('#puTabsTB li').wrapInner('<span class="r"><span class="m"></span></span>').click(function(){
	$('#puTabsTB li').removeClass('c');
	$(this).addClass('c');
	puTabsTBContent.find('li').hide();
	puTabsTBContent.find('li:eq('+$(this).index()+')').show();	
});		

//tabTb


//////////////////////////
})
</script>
<style>
	html,body {height:auto;overflow:hidden;}
</style>
</head>
<body>
<form id="nodeForm" name="form1">
<TABLE  width="100%" border="0" cellpadding="3" cellspacing="10">
<TR>
<TD>活动名称：<input class="text" name="cname"  value="${data.bact.cname}" style="width:35em;"></TD>
</TR>
</TABLE>

<ul id="puTabsTB">
	<li class="c">基本属性</li>
	<li>所有者</li>
	<#--<li>任务</li>
	<li>表单</li>
	<li>决策</li>
	-->
</ul>

<ul id="puTabsTBContent">
	<li><iframe  src="${base}/module/app/system/workflow/bact/bact_description.action?actid=${data.bact.id}" height="350" scrolling="auto" name="frame0" frameborder="0"></iframe></li>
	<li><iframe  src="${base}/module/app/system/workflow/bact/bact_selnodeowner.action?actid=${data.bact.id}"  height="350" width="100%" scrolling="auto" name="frame1" frameborder="0"></iframe></li>
	<li><iframe  src="${base}/module/app/system/workflow/bact/bact_selacttask.action?actid=${data.bact.id}" height="350" scrolling="auto" name="frame2" frameborder="0"></iframe></li>
	<li><iframe  src="${base}/module/app/system/workflow/bact/bact_form.action?actid=${data.bact.id}" height="350" width="100%" scrolling="auto" name="frame3" frameborder="0"></iframe></li>
	<li><iframe  src="${base}/module/app/system/workflow/bact/bact_strategic.action?actid=${data.bact.id}" height="350" scrolling="auto" name="frame4" frameborder="0"></iframe></li>
</ul>

<div style="text-align:right;padding-right:20px;">
	<button onclick="sendTo()">确定</button> <button onclick="window.close()">取消</button>
</div>
 
	<input type="hidden" name="actionowner_ownerctx">
	<input type="hidden" name="actionowner_ctype">
	<input type="hidden" name="actionowner_cname">
	<input type="hidden" name="actionowner_ownerchoice"	  />
	<input type="hidden" name="actionowner_outstyle"	  />
	<input type="hidden" name="acttask_id"   />
	<input type="hidden" name="acttask_sno"   />
	<input type="hidden" id="acttask_cname" name="acttask_cname"   />
	<input type="hidden" name="acttask_descript"   />
	<input type="hidden" name="acttask_ctype"   />
	<input type="hidden" name="acttask_require"   />
	<input type="hidden" name="acttask_apptaskid"   />
	<input type="hidden" name="formid"   />
	<input type="hidden" name="formname"    readonly="readonly"/>
	<input type="hidden" name="handletype"   /> 
	<input type="hidden" name="ctype"   /> 
	<input type="hidden" name="subflowid"   /> 
	<input type="hidden" name="subflowsno"   /> 
	<input type="hidden" name="subflowname"   /> 
	<input type="hidden" name="subflowcreate"   /> 
	<input type="hidden" name="subflowclose"   /> 
	<input type="hidden" name="subflowlink"   /> 
	<input type="hidden" name="join"  /> 
	<input type="hidden" name="split1"  />
	<input type="hidden" name="isfirst"  />
	<input type="hidden" name="outstyle"  />
	<input type="hidden" name="selectstyle"  />
	<input type="hidden" name="selectother"  />
	<input type="hidden" name="mid"  />
	<input type="hidden" name="flowid"  />
	<input type="hidden" name="imagename"  />
	<input type="hidden" name="x"  />
	<input type="hidden" name="y"  />
	<input type="hidden" name="classid"  />
	<input type="hidden" name="stId"  />
	<input type="hidden" name="stName"  /> 
	<input type="hidden" name="stid"  />
	<input type="hidden" name="stname"  />
	<input type="hidden" name="stctx"  />
	<input type="hidden" name="stcode"  />
	<input type="hidden" name="strouteid" value="" />
	<input type="hidden" name="stroutename"  />
	<input type="hidden" name="verif"  />
	<input type="hidden" name="field"  />
	<input type="hidden" name="rwtype"  />
	<input type="hidden" name="fieldid"  />
	<input type="hidden" name="fieldcname"  />
	<input type="hidden" name="fieldename"  />
	<input type="hidden" name="fieldtype"  />
	<input type="hidden" name="fieldaccess"  />
	<input type="hidden" name="fieldid1"  />
	<input type="hidden" name="fieldcname1"  />
	<input type="hidden" name="fieldename1"  />
	<input type="hidden" name="fieldtype1"  />
	<input type="hidden" name="fieldaccess1"  />

</form>
<SCRIPT type="text/javascript">
//document.all.ff2.style.display="none";
//document.all.ff3.style.display="none";
//document.all.ff4.style.display="none";
//document.all.ff5.style.display="none";

 
var node = window.dialogArguments;
var form=document.getElementById("nodeForm");
form.stId.value=rvalue(node.stId)+","+form.strouteid.value; 
form.stName.value=rvalue(node.stName)+","+form.stroutename.value;;

//alert(rvalue(node.split1));
   
form.mid.value = rvalue(node.id);
form.cname.value = rvalue(node.cname);
form.ctype.value = rvalue(node.ctype);
form.subflowid.value = rvalue(node.subflowid);
form.subflowsno.value = rvalue(node.subflowsno);
form.subflowname.value = rvalue(node.subflowname);
form.subflowcreate.value = rvalue(node.subflowcreate);
form.subflowclose.value = rvalue(node.subflowclose);
form.subflowlink.value = rvalue(node.subflowlink);
form.flowid.value = rvalue(node.flowid);
form.formid.value = rvalue(node.formid);
form.formname.value = rvalue(node.formname); 
form.handletype.value = rvalue(node.handletype);
form.join.value = rvalue(node.join);  
form.split1.value = rvalue(node.split1);
form.isfirst.value = rvalue(node.isfirst);
form.outstyle.value = rvalue(node.outstyle);
form.selectstyle.value = rvalue(node.selectstyle);
form.rwtype.value = rvalue(node.rwtype);
form.field.value = rvalue(node.field);
 
form.selectother.value = rvalue(node.selectother);
form.classid.value = rvalue(node.classid);
//alert(node.acttask);
setMultiValue('actionowner_cname,actionowner_ownerctx,actionowner_ctype,actionowner_ownerchoice,actionowner_outstyle',node.actionowner);
setMultiValue('acttask_id,acttask_sno,acttask_cname,acttask_descript,acttask_ctype,acttask_require,acttask_apptaskid',node.acttask);
 
setMultiValue('stid,stname,stctx,stcode,strouteid,stroutename',node.actstratege);
setMultiValue('fieldid,fieldcname,fieldename,fieldtype,fieldaccess',node.actfield);

//form.actionowner.value = rvalue(node.actionowner);
//form.acttask.value = rvalue(node.acttask);
form.imagename.value = rvalue(node.imagename);
form.x.value = rvalue(node.x);
form.y.value = rvalue(node.y);

ttm= rvalue(node.handletype);
//if(ttm=="普通") document.all.ctype1.innerText="普通";
//if(ttm=="多人串行") document.all.ctype1.innerText="普通";
//if(ttm=="多人并行") document.all.ctype1.innerText="普通";
//if(ttm=="多部门串行") document.all.ctype1.innerText="普通";
//if(ttm=="指定专人") document.all.ctype1.innerText="普通";

if(form.split1.value=="OR") strategy="";else strategy="none";

 //--------field processing start

 tempfield=form.field.value;
 tempfield1=tempfield.split(":");
 
 document.all.fieldid1.value=tempfield1[0];
 document.all.fieldcname1.value=tempfield1[1];
 document.all.fieldename1.value=tempfield1[2];
 document.all.fieldtype1.value=tempfield1[3];
 document.all.fieldaccess1.value=tempfield1[4];
//  alert(document.all.fieldid.value+"\n"+document.all.fieldid1.value+"\n"+document.all.fieldcname1.value);  
 //----------field processing end



//确定操作
function sendTo()
{ 
	//if(form.formid.value=="") {alert("表单不能为空！");return;}
	if(form.cname.value=="") {alert("活动名称不能为空！");return;}
	if (! (validateNull('cname','活动名称') &&validateLong('cname','活动名称','60')&&validateIllegal('cname','活动名称') )) return;

    document.all.handletype.value=window.frames[0].document.all.handletype.value;
    document.all.ctype.value=window.frames[0].document.all.ctype.value;
    document.all.subflowid.value=window.frames[0].document.all.subflowid.value;
    document.all.subflowsno.value=window.frames[0].document.all.subflowsno.value;
    document.all.subflowname.value=window.frames[0].document.all.subflowname.value;
    document.all.subflowcreate.value=window.frames[0].document.all.subflowcreate.value;
    document.all.subflowclose.value=window.frames[0].document.all.subflowclose.value;
    document.all.subflowlink.value=window.frames[0].document.all.subflowlink.value;
    
    document.all.join.value=window.frames[0].document.all.join.value;
    document.all.split1.value=window.frames[0].document.all.split1.value;
    //document.all.isfirst.value=window.frames[0].document.all.isfirst.value;
    
    document.all.outstyle.value=window.frames[0].document.all.outstyle.value;
    document.all.selectstyle.value=window.frames[0].document.all.selectstyle.value;
    document.all.selectother.value=window.frames[0].document.all.selectother.value;
 
 	if(form.ctype.value=="SUBFLOW"&&form.subflowname.value==""){alert("子流程名称不能为空！");return;}
 	
	node.id = form.mid.value;
	node.cname = form.cname.value;
	node.ctype = form.ctype.value;
	node.subflowid = form.subflowid.value;
	node.subflowsno = form.subflowsno.value;
	node.subflowname = form.subflowname.value;
	node.subflowcreate = form.subflowcreate.value;
	node.subflowclose = form.subflowclose.value;
	node.subflowlink = form.subflowlink.value;
	
	
	node.flowid = form.flowid.value;
	node.formid = form.formid.value;
	node.formname = form.formname.value;
	node.handletype = form.handletype.value;
	node.join = form.join.value;
	node.split1 = form.split1.value;
	node.isfirst = form.isfirst.value;
	node.outstyle = form.outstyle.value;
	node.selectstyle = form.selectstyle.value;
	node.selectother = form.selectother.value;
	node.rwtype = form.rwtype.value;


//st
if(form.split1.value=="OR")
{
 //决策处理开始
 document.all.stid.value=''; document.all.stname.value='';
 document.all.stctx.value=''; document.all.stcode.value='';
 document.all.strouteid.value=''; document.all.stroutename.value='';
   tblUpdate= window.frames[4].document.getElementById("showTable");
   stcontext=window.frames[4].document.getElementsByTagName("input"); 
   var ifempty=1,iffull=1;;
   for (var i  = 1; i  < tblUpdate.rows.length; i++)  
   {                    
   stctx=stcontext[i-1].value; 
   document.all.verif.value=stctx;
   	  if (! ( validateLong('verif','决策内容','100')&&validateIllegal('verif','决策内容') ))
   	  {
   	    
   	   return;
   	   }
   
   
                        document.all.stid.value+=tblUpdate.rows[i].cells[0].innerText+','; 
                        document.all.stctx.value+=stctx+','; 
                        document.all.stname.value+=stctx.substr(0,60)+','; 
                        document.all.stcode.value+=stctx.substr(0,32)+','; 
                        if(trim(stctx)=="") {ifempty=0;} else{ iffull=0;}
                        document.all.strouteid.value+=tblUpdate.rows[i].cells[0].innerText+','; 		 
                        document.all.stroutename.value+=tblUpdate.rows[i].cells[2].innerText+','; 		 
     
   }
  
   if((ifempty==0)&&(iffull==0)) {alert("决策内容说明不完整，请填写完整或清除！"); return;}
   if((ifempty==1)&&(iffull==0)) 
   node.actstratege = getMultiValue('stid,stname,stctx,stcode,strouteid,stroutename');
 }    
else
{
 document.all.stid.value=''; document.all.stname.value='';
 document.all.stctx.value=''; document.all.stcode.value='';
 document.all.strouteid.value=''; document.all.stroutename.value='';

}
//决策处理结束
 
 //表单处理开始
   document.all.fieldid.value=''; document.all.fieldcname.value='';
   document.all.fieldename.value=''; document.all.fieldtype.value='';
   document.all.fieldaccess.value='';  
tblUpdate= window.frames[3].document.getElementById("showTable");
 
 if((document.all.rwtype.value=="读写")&&(tblUpdate.rows.length>1))
 {
   
   for (var i  = 1; i  < tblUpdate.rows.length; i++)  
   {                    
                        document.all.fieldid.value+=tblUpdate.rows[i].cells[0].innerText+','; 
                        document.all.fieldcname.value+=tblUpdate.rows[i].cells[1].innerText+','; 
                        document.all.fieldename.value+=tblUpdate.rows[i].cells[2].innerText+','; 
                        document.all.fieldtype.value+=tblUpdate.rows[i].cells[3].innerText+','; 
                         
                        select= window.frames[3].document.getElementById("slct"+i); 
                        
                        document.all.fieldaccess.value+=select.options[select.selectedIndex].value+',';
                        
   }
    t1=document.all.fieldid.value ; t2= document.all.fieldcname.value; t3= document.all.fieldename.value; t4= document.all.fieldtype.value; t5= document.all.fieldaccess.value; 
    t1=t1.substr(0,t1.length-1); document.all.fieldid.value=t1;
    t2=t2.substr(0,t2.length-1); document.all.fieldcname.value=t2;
    t3=t3.substr(0,t3.length-1); document.all.fieldename.value=t3;
    t4=t4.substr(0,t4.length-1); document.all.fieldtype.value=t4;
    t5=t5.substr(0,t5.length-1); document.all.fieldaccess.value=t5;
    node.actfield = getMultiValue('fieldid,fieldcname,fieldename,fieldtype,fieldaccess');  

 }
//表单处理结束

  	node.actionowner = getMultiValue('actionowner_cname,actionowner_ownerctx,actionowner_ctype,actionowner_ownerchoice,actionowner_outstyle');

 	node.acttask = getMultiValue('acttask_id,acttask_sno,acttask_cname,acttask_descript,acttask_ctype,acttask_require,acttask_apptaskid');
    
    node.rlist=form.acttask_cname.value+":"+form.acttask_apptaskid.value+":"+form.acttask_id.value;
	node.imagename = form.imagename.value;
	node.x = form.x.value;
	node.y = form.y.value;

	window.returnValue = node;
	window.close()
}
 

</SCRIPT>
</body>

</html>