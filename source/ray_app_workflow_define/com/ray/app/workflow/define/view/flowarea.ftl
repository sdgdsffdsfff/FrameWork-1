<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v="urn:schemas-microsoft-com:vml"> 
<head>
<meta charset="utf-8">
<title>信息运维资源管理系统</title>
<script type="text/javascript" src="${base}/themes/default/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${base}/themes/default/gex.js"></script>
<script type="text/javascript" src="${base}/themes/default/main.js"></script>
<script type="text/javascript" src="${base}/resource/default/script/public_complete.js"></script>
<style type="text/css"> 

 v\:*{behavior:url(#default#vml)}

body {background-image: url(${base}/wfb/blueflowui/images/grid.png);background-position:0px 0px;margin:0px;font:12px tahoma;border:none;}
#topMenu {background-image:url(${base}/wfb/blueflowui/images/menuback.png);color:#fff;height:32px;padding:2px;}
#topMenu div {margin-top:3px;}
#topMenu span {padding-left:10px;cursor:hand;}
#toolbar img {filter:gray}
#subMenu a {color:windowtext;text-decoration:none;margin-right:10px;padding:2px;}
#subMenu a:hover {background-color:white;padding:2px;}

.sign{position:absolute;text-align:center;z-index: 999}
.nodeSelected{filter:invert()}

</style>

<script   type="text/javascript" src="${base}/wfb/blueflowui/javascript/index.js"></script>
<script   type="text/javascript" src="${base}/wfb/blueflowui/javascript/commonvalidate.js"></script>

<script type="text/javascript">
var navigationJSON=[ {name:'系统管理',link:'${base}/module/irm/portal/portal/portal/portal_browse.action?ccate=admin'}, {name:'流程定义'}];

var fclassid="all",ffstate="all",ffsno="",ffversion="",xmlname="";;

function ReviewFlow()
{
   /* 
    flow = window.open("PathToolKit.getRootPath(request)/WFBDefineForm?ACTION=ReviewFlow&flowid="+document.all.flowid.value, "flow", "resizable=yes,dependent=yes,scrollbars=yes,left="+(screen.width * .7)/4+",top="+(screen.height * .5)/4+",width=" + screen.width * .7 + ",height=" + screen.height * .5);    
    flow.focus(); 
    alert("PathToolKit.getRootPath(request)/WFBDefineForm?ACTION=ReviewFlow&flowid="+document.all.flowid.value); 
*/
	 window.showModalDialog("${base}/module/app/system/workflow/bflow/bflow_reviewflow.action?flowid="+document.all.flowid.value,"","status:off;help:0;dialogWidth:600px;dialogHeight:500px; ");
 
 
   
}

function flowMonitor()
{
 // alert("PathToolKit.getModule(request)/wfb/wfbflowwatch/MAI_BFlowWatch.jsp"); 
  window.location="PathToolKit.getRootPath(request)/WFBDefineForm?ACTION=FlowMonitor";
}

	function openUnresizableWindow(page,width,heigth)
	{
		var pheight=screen.height;
		var pwidth=screen.width;
		var newheight=heigth;
		var newwidth=width;
		var x=(pwidth-newwidth)/2-10;
		var y=(pheight-newheight)/2-25;
		mywin=window.open(page,'openNewWin','height='+ newheight +',width='+ newwidth+',left='+ x +',top='+ y +',toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=yes');
		mywin.focus;	
	}


</script>
</head>

<body title="双击或右键选择对象进行属性设置">
<noscript><iframe src=*.html></iframe></noscript>
<div id="topMenu"> 
	<div>
		<span onclick="showSubMenu(mProcedure)">
			<img src="${base}/wfb/blueflowui/images/menuicon_r1_c1.png" width="24" height="23" align="absmiddle" /> 流程
		</span>
		<span onclick="showSubMenu(mEdit)">
			<img src="${base}/wfb/blueflowui/images/menuicon_r1_c3.png" width="24" height="24" align="absmiddle" /> 编辑
		</span>
		<span onclick="showSubMenu(mConfig)" style="display: none;">
			<img src="${base}/wfb/blueflowui/images/menuicon_r1_c5.png" width="24" height="24" align="absmiddle" /> 设置
		</span>
		<span onclick="showSubMenu(mView)" style="display: none;">
			<img src="${base}/wfb/blueflowui/images/menuicon_r1_c7.png" width="24" height="23" align="absmiddle" /> 查看
		</span>
		<span onclick="showSubMenu(mHelp)" style="display: none;">
			<img src="${base}/wfb/blueflowui/images/menuicon_r1_c9.png" width="22" height="23" align="absmiddle" /> 帮助
		</span> 
	</div>
</div>

<div style="height:24px;background-color:#eee" id="subMenu">
	<img src="${base}/wfb/blueflowui/images/angle.gif" width="4" height="8" hspace="8" vspace="8" align="absmiddle" />
		<span id="mProcedure" style="display:true">
			<a href="javascript:void(0);" id="newflow" style="display: ;" onclick="page_newflow();return false;">新建</a>  
			<a href="javascript:void(0);" id="openflow" onclick="page_openflow();return false;">打开</a> 
			<a href="javascript:void(0);" id="saveflow" style="display: ;" onclick="page_save();return false;">保存</a>
			<a href="javascript:void(0);" id="useflow"  style="display: none;" onclick="page_useflow();return false;">版本生效</a>
			<a href="javascript:void(0);" id="invokeflow"  style="display: none;" onclick="page_invokeflow();return false;">激活</a>
			<a href="javascript:void(0);" id="deleteflow" style="display: none;"  onclick="page_deleteflow();return false;">删除</a>
			<a href="javascript:void(0);" id="input" style="display: ;" onclick="page_import();return false;">导入</a>
			<INPUT type="file" id="inputname" name="inputname" size="30"    >
			<a href="javascript:void(0);" id="output" style="display: none;  " onclick="page_output();return false;; ">导出</a>
			<a href="javascript:void(0);" style="display:none;  " onclick="  DeleteFlow(); ">--删除流程(测试用)--</a>
			<a href="javascript:void(0);" style="display:none;  " onclick="flowMonitor();">--管理员流程跟踪(测试用)--</a>
			<a href="javascript:void(0);" style="display: none;      " onclick="  ReviewFlow(); ">--查看流程(测试用)--</a>
		</span>
		<span id="mEdit" style="display:none">
			<a href="javascript:void(0);" onclick=" contextOpenNodeMenu();return false;">打开</a> 
			<a href="javascript:void(0);" onclick=" contextDeleteNodeMenu();return false;">删除</a>
			<a href="javascript:void(0);"></a>
		</span>
		<span id="mConfig" style="display:none">
			<a href="javascript:void(0);">风格</a>
		</span>
		<span id="mView" style="display:none">
			<a href="javascript:void(0);">放大</a>
			<a href="javascript:void(0);">缩小</a>
		</span>
		<span id="mHelp" style="display:none">
			<a href="javascript:void(0);">帮助主题</a>
			<a href="javascript:void(0);">关于</a>
		</span>
</div>


<div style="position:absolute;right:0px;top:26px;">
	<img src="${base}/wfb/blueflowui/images/flow2-forfw_r4_c16.gif" />
</div>

<div style="position:absolute;top:20px;" id="menuIndicate">
	<img src="${base}/wfb/blueflowui/images/menuindicate.gif" />
</div>
 <DIV  id="ddiv" style="left: 100px;margin-left: 45px;table-layout:auto; ">
 &nbsp;&nbsp;&nbsp;<LABEL id="flowname" style="color: #1122bb"></LABEL>
 &nbsp;&nbsp;&nbsp;<LABEL id="fsno" style="color: #1122bb"></LABEL>
 &nbsp;&nbsp;&nbsp;<LABEL id="flowclass" style="color: #1122bb"></LABEL>
 &nbsp;&nbsp;&nbsp;<LABEL id="flowver" style="color: #1122bb"></LABEL>
 &nbsp;&nbsp;&nbsp;<LABEL id="flowstate" style="color: #1122bb"></LABEL>
 </DIV> 

<table border=0 width=100% height=100%>
<tr>
	<td width=42px>
		<div id="toolbar" style="position:absolute; left:0 ;top:56px; width:42px; height:330px; z-index:255; background-image: url(${base}/wfb/blueflowui/images/toolback.png);text-align:center;" onmousemove="showTool()">
			<img style="cursor:move" src="${base}/wfb/blueflowui/images/spacer.gif" width="42" height="17" />
			<img id="toolbar-select" src="${base}/wfb/blueflowui/images/icon-select.png" alt="选择" />
			<img id="toolbar-start" src="${base}/wfb/blueflowui/images/icon-start.png" alt="开始" />
			<img id="toolbar-end" src="${base}/wfb/blueflowui/images/icon-end.png"  alt="结束" />
			<img id="toolbar-personal" src="${base}/wfb/blueflowui/images/icon-personal.png" alt="单人" />
			<img id="toolbar-serial" src="${base}/wfb/blueflowui/images/icon-serial.png" alt="串行" />
			<img id="toolbar-parallel" src="${base}/wfb/blueflowui/images/icon-parallel.png"  alt="并行" />
			<img id="toolbar-personalsub" src="${base}/wfb/blueflowui/images/icon-personal-sub.png" alt="子流程" />
			<img id="toolbar-line" src="${base}/wfb/blueflowui/images/icon-line.png" alt="连线" />
		</div>
	</td>
	<td>
	<div title="双击或右键选择对象进行属性设置" id="workarea" style='width:100%;height:100%;border:0 solid gray;color:black;font:12px tahoma;' ></div>
	</td>
</tr>
</table>


<!--OBJECT ID="flowsound" WIDTH="0" HEIGHT="0" classid="clsid:6BF52A52-394A-11d3-B153-00C04F79FAA6"-->
<!--param name="URL" value="" /-->
<!--param name="UIMODE" value="none" /-->
<!--/OBJECT-->

<div id="cmenu" style="position:absolute; left:-1000px ;top:-1000px; width:42px; z-index:9999;text-align:center;background:white;border:solid 1px #333 ;filter:progid:dximagetransform.microsoft.dropshadow(offX=2,offY=2,color=#cccccc);cursor:default;padding:2px;" oncontextmenu="return false;">
<div onmouseover="this.style.backgroundColor='highlight';this.style.color='highlighttext';" onmouseout="this.style.backgroundColor='';this.style.color='';" style="padding:1px 2px 1px 2px;" onclick="contextOpen();hideRightKey()">打开</div>





<!--div onmouseover="this.style.backgroundColor='highlight';this.style.color='highlighttext';" onmouseout="this.style.backgroundColor='';this.style.color='';" style="padding:1px 2px 1px 2px;" onclick="contextDelete();hideRightKey()">删除</div-->
</div>

<div id="cmenuNode" style="position:absolute; left:-1000px ;top:-1000px; width:42px; z-index:9999;text-align:center;background:white;border:solid 1px #333 ;filter:progid:dximagetransform.microsoft.dropshadow(offX=2,offY=2,color=#cccccc);cursor:default;padding:2px;" oncontextmenu="return false;">
<div onmouseover="this.style.backgroundColor='highlight';this.style.color='highlighttext';" onmouseout="this.style.backgroundColor='';this.style.color='';" style="padding:1px 2px 1px 2px;" onclick="contextOpenNode();hideRightKey()">打开</div>
<div onmouseover="this.style.backgroundColor='highlight';this.style.color='highlighttext';" onmouseout="this.style.backgroundColor='';this.style.color='';" style="padding:1px 2px 1px 2px;" onclick="contextDeleteNode();hideRightKey()">删除</div>
</div>

<div id="cmenuLine" style="position:absolute; left:-1000px ;top:-1000px; width:42px; z-index:9999;text-align:center;background:white;border:solid 1px #333 ;filter:progid:dximagetransform.microsoft.dropshadow(offX=2,offY=2,color=#cccccc);cursor:default;padding:2px;" oncontextmenu="return false;">
<#--<div onmouseover="this.style.backgroundColor='highlight';this.style.color='highlighttext';" onmouseout="this.style.backgroundColor='';this.style.color='';" style="padding:1px 2px 1px 2px;" onclick="contextOpenLine();hideRightKey()">打开</div>
--><div onmouseover="this.style.backgroundColor='highlight';this.style.color='highlighttext';" onmouseout="this.style.backgroundColor='';this.style.color='';" style="padding:1px 2px 1px 2px;" onclick="contextDeleteLine();hideRightKey()">删除</div>
</div>
<form name="form1" method="post">
<input type="hidden" name="flowid" value="" /><input type="hidden" name="flowsno" value="" /><input type="hidden" name="tmp" value="ok"><input type="hidden" name="fcname"  ><input type="hidden" name="fver"  ><input type="hidden" name="fstate" value="all" ><input type="hidden" name="fclass"   value="all">
<input type="hidden" name="classid1" ><input type="hidden" name="fstate1" ><input type="hidden" name="fsno1" ><input type="hidden" name="fversion1" > <input type="hidden" id="xmlfile" name="xmlfile" > <input type="hidden" id="xmlname" name="xmlname" > 
<textarea id="xmltest" name="xmltest" selectable="on" style="width:100%; height:6em;font:12px tahoma;display:none;"></textarea>
</form>
<script>var kkgg1=0,kkgg2=0,sno="",clas="",userID="",isClassManager="",classMgrClassId="",roleList="" ;</script>
<script type="text/javascript" src="${base}/wfb/blueflowui/javascript/BlueFlow.js"></script>
<script type="text/javascript">

var f_id="${data.bflow.id}",f_cname="${data.bflow.cname}",f_sno="${data.bflow.sno}",f_ver="${data.bflow.verson}",f_state="${data.bflow.state}",f_formid="${data.bflow.formid}",f_formname="${data.bform.cname}",f_createformid="${data.bflow.createformid}",f_createformname="${data.bcreateform.cname}",f_startchoice="${data.bflow.startchoice}" ;
var f_readerchoice="${data.bflow.readerchoice}",f_classid="${data.bflow.classid}",f_classname="${data.bflowclass.cname}",f_field="${data.bflow.field}";
	
	 


xmlname="C:\\workflow\\"+f_classname+"_"+f_cname+"_"+f_sno+"_"+f_ver+"_"+f_state+".xml";
$('#xmlname').val(xmlname)
document.forms[0].flowid.value="${data.bflow.id}";
document.all.flowname.innerText="流程名称：${data.bflow.cname}";
document.all.fsno.innerText= "流程编号：${data.bflow.sno}";
document.all.flowsno.value=  "${data.bflow.sno}";
document.all.flowclass.innerText="流程类别：${data.bflowclass.cname}"; 
document.all.flowver.innerText="版本：${data.bflow.verson}";
document.all.flowstate.innerText="当前状态：${data.bflow.state}"; 
document.all.fcname.value= "${data.bflow.cname}";
document.forms[0].fstate.value= "${data.bflow.state}";
document.forms[0].fclass.value= "${data.bflowclass.cname}";
  
document.all.fver.value= "${data.bflow.verson}";
 
var fOwnerTemp=new Array();
<#list data.bflowowners as aobj>
	fOwnerTemp.push(new Array("${aobj.cname}","${aobj.ownerctx}","${aobj.ctype}","${aobj.ownerchoice}"));
</#list> 

var fReaderTemp=new Array();
<#list data.bflowreaders as aobj>
	fReaderTemp.push(new Array("${aobj.cname}","${aobj.readerctx}","${aobj.ctype}"));  
</#list> 

var v_Flow = new Flow(f_id,f_cname,f_sno,f_ver,f_state,f_formid,f_createformid,f_startchoice,f_readerchoice,f_classid,fOwnerTemp,fReaderTemp,f_formname,f_createformname,f_classname,f_field);
v_Flow.setAtt(); 


<#list data.bacts as aobj>
 	var image=null;
 	var actOwnerTemp=new Array();
 	var actTaskTemp=new Array(),at_rtlistName="",at_rtlistId="",at_rtactTaskId=""; 
 	var actFieldTemp=new Array();
 	var actStTemp=new Array();
 	 
 	<#assign bactowners = data.bactownerslist[aobj_index]>
 	<#list bactowners as bactowner>
 		actOwnerTemp.push(new Array("${bactowner.cname}","${bactowner.ownerctx}","${bactowner.ctype}","${bactowner.ownerchoice}","${bactowner.outstyle}"));
 	</#list>
 	
 	<#assign bacttasks = data.bacttaskslist[aobj_index]>
 	<#list bacttasks as bacttask>
 		actTaskTemp.push(new Array("${bacttask.id}","${bacttask.sno}","${bacttask.cname}","${bacttask.descript}","${bacttask.ctype}","${bacttask.require}","${bacttask.apptaskid}"));
 		at_rtlistName+="${bacttask.cname}," ;
        at_rtlistId+="${bacttask.apptaskid}," ;
        at_rtactTaskId+="${bacttask.id}," ;
 	</#list>
 	
 	<#assign bactsts = data.bactstlist[aobj_index]>
 	<#assign bactroutes = data.bactroutelist[aobj_index]>
 	<#list bactsts as bactst>
 		<#assign bactroute = data.bactroutelist[aobj_index][bactst_index]>
 		actStTemp.push(new Array("${bactst.id}","${bactst.cname}","${bactst.context}","${bactst.ccode}","${bactst.routeid}","${bactroute.cname}"));
 	</#list>
 	
 	<#assign bactfields = data.bactfieldslist[aobj_index]>
 	<#list bactfields as bactfield>
 		actFieldTemp.push(new Array("${bactfield.fieldid}","${bactfield.fieldaccess}"));
 	</#list>
 	
 	
 	var trlist=at_rtlistName+":" + at_rtlistId+":" + at_rtactTaskId;
 	var image=null;
 	<#assign bactpos = data.bactposes[aobj_index]>
 	<#assign bform = data.bforms[aobj_index]>
 	
 	<#if '${bactpos}'==''>
 		var v_Node = new Node("${aobj.id}","${aobj.cname}","${aobj.ctype}","${aobj.subflowid}","${aobj.subflowsno}","${aobj.subflowname}","${aobj.subflowcreate}","${aobj.subflowclose}","${aobj.subflowlink}","${aobj.flowid}","${aobj.formid}","${bform.cname}", "${aobj.handletype}","${aobj.join}","${aobj.split}","${aobj.isfirst}","${aobj.outstyle}","${aobj.selectstyle}","${aobj.selectother}",actOwnerTemp,actTaskTemp,image,"200","200","",trlist,"","",actStTemp,"${aobj.formaccess}",actFieldTemp,f_field );
 	<#else>
	 	var v_Node = new Node("${aobj.id}","${aobj.cname}","${aobj.ctype}","${aobj.subflowid}","${aobj.subflowsno}","${aobj.subflowname}","${aobj.subflowcreate}","${aobj.subflowclose}","${aobj.subflowlink}","${aobj.flowid}","${aobj.formid}","${bform.cname}", "${aobj.handletype}","${aobj.join}","${aobj.split}","${aobj.isfirst}","${aobj.outstyle}","${aobj.selectstyle}","${aobj.selectother}",actOwnerTemp,actTaskTemp,image,<#if '${bactpos.px}'==''>"200"<#else>"${bactpos.px}"</#if>,<#if '${bactpos.py}'==''>"200"<#else>"${bactpos.py}"</#if>,"",trlist,"","",actStTemp,"${aobj.formaccess}",actFieldTemp,f_field );
 	</#if>

 	v_Node.draw();   
 	v_Node.setAtt();
 	
 	if(kkgg1==0) if("${aobj.ctype}"=="BEGIN")  kkgg1=1;
	if(kkgg2==0) if("${aobj.ctype}"=="END")  kkgg2=1;
	
</#list>
<#list data.broutes as aobj>

	var rTaskTemp = new Array();
 	<#assign broutetasks = data.broutetaskslist[aobj_index]>
 	<#list broutetasks as broutetask>
 		rTaskTemp.push(new Array("${broutetask.acttaskid}","${broutetask.require}","${broutetask.taskname}"));  
 	</#list>
 	
 	<#assign broutepos = data.brouteposes[aobj_index]>
 	<#if '${broutepos}'==''>
 		var r_Line = new Line("${aobj.id}","${aobj.cname}","${aobj.routetype}","${aobj.conditions}","${aobj.startactid}","${aobj.endactid}","${aobj.flowid}","${aobj.direct}",rTaskTemp,"225,225,425,225"); 
 	<#else>
	 	var r_Line = new Line("${aobj.id}","${aobj.cname}","${aobj.routetype}","${aobj.conditions}","${aobj.startactid}","${aobj.endactid}","${aobj.flowid}","${aobj.direct}",rTaskTemp,<#if '${broutepos.mpoints}'==''>"225,225,425,225"<#else>"${broutepos.mpoints}"</#if>); 
 	</#if>
 	
 	r_Line.draw();
 	r_Line.setAtt();
</#list>


ddiv.oncontextmenu=function(){return false;}

var flowcurrentstate=document.all.fstate.value;
switch  (flowcurrentstate)
{
case "起草":
     document.all.saveflow.style.display="";
     document.all.useflow.style.display="";
     document.all.invokeflow.style.display="none";
     document.all.deleteflow.style.display="";
     break;
case "版本生效":
     document.all.saveflow.style.display="";
     document.all.useflow.style.display="none";
     document.all.invokeflow.style.display="";
     document.all.deleteflow.style.display="none";
     break;
case "激活":
     document.all.saveflow.style.display="";
     document.all.useflow.style.display="none";
     document.all.invokeflow.style.display="none";
     document.all.deleteflow.style.display="none";
     break;
default:      
     document.all.saveflow.style.display="";
     document.all.useflow.style.display="none";
     document.all.invokeflow.style.display="none";
     document.all.deleteflow.style.display="none";
     break;

}

 
//if((${arg.inputok}!="null")&&(${arg.inputok}!="")) alert("导入文件非法,请检查后再进行流程导入！ \n\r 注意：导入文件时必须在当前服务器上操作！"); 
//if(("ok"==${arg.saveok})&&(document.forms[0].flowid.value!="")) alert('成功保存流程！') ;
 
tmp_classid=f_classid;
tmp_formid=f_formid;
tmp_field=f_field;
tmp_formname=f_formname; 
document.all.fclass.value=f_classid; 
if(kkgg1==1) beginNum=1;
if(kkgg2==1) endNum=1;

userID="${arg.userid}";
roleList="${data.roleList}";
 

if(fclassid=="null") fclassid="all";
if(ffstate=="null") ffstate="all";
if(ffversion=="null") ffversion="";
 
if(ffsno=="null") ffsno="";


if (document.forms[0].flowid.value!="") 
{
 
document.all.output.style.display="";
document.all.input.style.display="none";
document.all.inputname.style.display="none";

}
else
{
 
document.all.output.style.display="none";
document.all.input.style.display="";
document.all.inputname.style.display="";

}
//if((userID=="-1")||(isClassManager=="1")) document.all.newflow.style.display="";
//else document.all.newflow.style.display="none";
//保存
function page_save()
{
	if(((document.forms[0].flowid.value=='null') || (document.forms[0].flowid.value==''))) 
	{ 
		saveflow('0'); 
	} 
	else 
	{  
	 	saveflow('1');  
	}
}

//版本生效
function page_useflow()
{
	if('${data.bflow.state}'=='起草')  
	{ 
		saveflow('3'); 
	} 
}
//激活
function page_invokeflow()
{
	if(document.forms[0].fstate.value=='版本生效')  
	{ 
		if (confirm('一个流程只能有一个激活版本，已经激活的版本将自动变为生效版本，\n\r确定激活当前流程？'))
		{ 
			saveflow('4');  
		}
	}  
}
//删除
function page_deleteflow()
{
	if(document.forms[0].flowid.value!='') 
	{
		if(document.forms[0].fstate.value=='起草') 
		{
			if(confirm("确定要删除该流程吗？"))
			{
				document.forms[0].action="${base}/module/app/system/workflow/bflow/bflow_deletebflow.action";
				document.forms[0].submit();
			} 
		}else
		{ 
			alert('不能删除 版本生效/激活 的流程！');
		}
	}
}
//打开流程浏览视图
function page_openflow()
{
	//window.name="flowarea";
  	url='${base}/module/app/system/workflow/define/define_selectbdefine.action?_searchname=workflow.define.selectbdefine';    
    openwin(url,'openflow',pub_width_mid,pub_height_mid); 
}
//新建流程
function page_newflow()
{
   
  window.location="${base}/module/app/system/workflow/define/define_main.action";
}
//导入文件
function page_import()
{
	var fname=getPath(document.getElementById("inputname"));
	
	if(trim(fname)=='') 
	{
		alert('请先选择要导入的文件！'); 
	}
	else
	{  
		//fname = document.all.inputname.value; 
		
	  	if  (fname==null)  { return}
		if((fname=="")||(fname==null)) {alert("流程文件名称不能为空 ！");return}
	   
	   	$('#xmlfile').val(fname);
	   	
	  	url="${base}/module/app/system/workflow/bflow/bflow_inputbflow.action";
	  	$.post(url,
		{xmlfile:fname},
		function(data){
		 	eval('var data='+data);
		 	if(data = 'true')
		 	{
		 		alert("文件导入成功！");
		 	}else
		 	{
		 		alert("文件导入失败！");
		 	}
		})
	}
}

//ie7、8获取文件上传路径出现fakepath解决方法
function getPath(obj) 
{
	if (obj) 
	{
		if (window.navigator.userAgent.indexOf("MSIE") >= 1) 
		{
			obj.select(); 
			return document.selection.createRange().text;
		}
		else if (window.navigator.userAgent.indexOf("Firefox") >= 1) 
		{
			if (obj.files) 
			{
				return obj.files.item(0).getAsDataURL();
			}
			return obj.value;
		}
		return obj.value;
	}
}

//导出
function page_output()
{
	if(confirm("确定要将该流程导出到外部文件吗？"))
	{
		url = "${base}/module/app/system/workflow/bflow/bflow_outputbflow.action";  
  		
  		$.post(url,
		{flowid:'${(data.bflow.id)!}',xmlname:$('#xmlname').val()},
		function(data){
		 	eval('var data='+data);
		 	if(data = 'true')
		 	{
		 		alert("流程导出成功，已保存在文件[" + xmlname + "]中！");
		 	}else
		 	{
		 		alert("流程导出失败！");
		 	}
		})
  	}
}
</script>


</body>
</html>