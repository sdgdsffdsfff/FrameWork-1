<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v="urn:schemas-microsoft-com:vml"> 
<head>
<#include "/decorator/include/include.ftl">
<title>流程设计　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　</title>
<style type="text/css">
<!--
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
-->
</style>

<script type="text/javascript" src="${base}/wfb/blueflowui/javascript/index.js"></script>
<body>

<noscript><iframe src=""></iframe></noscript>
<div id="ddiv" style=" table-layout:auto; ">
  
<table width="100%"  border="0" cellpadding="3" cellspacing="0"  align="center" id="naviTable">
<tr>
	<td align="left"><LABEL id="flowname" style="color: #1122bb"></LABEL></td>
	<td align="left"><LABEL id="fsno" style="color: #1122bb"></LABEL></td>
	<td align="left"><LABEL id="flowclass" style="color: #1122bb"></LABEL></td>
	<td align="left"><LABEL id="flowver" style="color: #1122bb"></LABEL></td>
	<td align="left"><LABEL id="flowstate" style=" display:none; color: #1122bb"></LABEL></td>
</tr>
<tr style="display: none;">
	<td  align="left">速度设定：
	<select id="speed" name="speed" onchange="speed=this.value;">
	<option value="3000">慢速</option>
	<option value="2000" selected="selected">常速</option>
	<option value="1000">快速</option>
	</select>
	</td> 
 	<td align="left">连续播放：<input type="checkbox"  name="cplay"  >
 	<td align="left">
 	<button id="play" onclick="play();this.style.display='none';document.all.suspend.style.display='';document.all.stop.style.display='';" style="display:">播放</button>
 	<button id="suspend" onclick="suspend();this.style.display='none';document.all.play.style.display='none';document.all.resume.style.display='';" style="display: none; ">暂停</button>
 	<button id="stop" onclick="stop();this.style.display='none';document.all.play.style.display='';document.all.suspend.style.display='none';document.all.resume.style.display='none'; " style="display: none;">停止</button>
  	<button id="resume" onclick="play();this.style.display='none';document.all.suspend.style.display='';document.all.stop.style.display='';" style="display: none;">继续</button>
	<button style="display: " onclick="self.close();">退出</button>
	</td>
	</tr>
	</table>
</div>
 
<table border=0 width=100% height=100% style="display: none;">
<tr>
	<td width=42px>
	<div id="toolbar" style="position:absolute; left:0 ;top:56px; width:42px;height:330px; z-index:255; background-image: url(${base}/wfb/blueflowui/images/toolback.png);text-align:center;" onmousemove=" showTool()">
		<img style="cursor:move" src="${base}/wfb/blueflowui/images/spacer.gif" width="42" height="17" />
		<img id="toolbar-select" src="${base}/wfb/blueflowui/images/icon-select.png" alt="选择" />
		<img id="toolbar-start" src="${base}/wfb/blueflowui/images/icon-start.png" alt="开始" />
		<img id="toolbar-end" src="${base}/wfb/blueflowui/images/icon-end.png" alt="结束" />
		<img id="toolbar-personal" src="${base}/wfb/blueflowui/images/icon-personal.png" alt="单人" />
		<img id="toolbar-serial" src="${base}/wfb/blueflowui/images/icon-serial.png" alt="串行" />
		<img id="toolbar-parallel" src="${base}/wfb/blueflowui/images/icon-parallel.png"  alt="并行" />
		<img id="toolbar-line" src="${base}/wfb/blueflowui/images/icon-line.png" alt="连线" />
	</div>
	</td>
</tr>
</table>

<div id="workarea" style='width:100%;height:100%;border:0 solid gray;color:black;font:12px tahoma;'></div>
<div id="cmenu" style="position:absolute; left:-1000px ;top:-1000px; width:42px; z-index:9999;text-align:center;background:white;border:solid 1px #333;filter:progid:dximagetransform.microsoft.dropshadow(offX=2,offY=2,color=#cccccc);cursor:default;padding:2px;" oncontextmenu="return false;">
<div onmouseover="this.style.backgroundColor='highlight';this.style.color='highlighttext';" onmouseout="this.style.backgroundColor='';this.style.color='';" style="padding:1px 2px 1px 2px;" onclick="contextOpen()">打开</div>
<div onmouseover="this.style.backgroundColor='highlight';this.style.color='highlighttext';" onmouseout="this.style.backgroundColor='';this.style.color='';" style="padding:1px 2px 1px 2px;" onclick="contextDelete()">删除</div>
</div>

<div id="cmenuLine" style="position:absolute; left:-1000px ;top:-1000px; width:42px; z-index:9999;text-align:center;background:white;border:solid 1px #333 ;filter:progid:dximagetransform.microsoft.dropshadow(offX=2,offY=2,color=#cccccc);cursor:default;padding:2px;" oncontextmenu="return false;">
<div onmouseover="this.style.backgroundColor='highlight';this.style.color='highlighttext';" onmouseout="this.style.backgroundColor='';this.style.color='';" style="padding:1px 2px 1px 2px;" onclick="contextOpenLine()">打开</div>
<div onmouseover="this.style.backgroundColor='highlight';this.style.color='highlighttext';" onmouseout="this.style.backgroundColor='';this.style.color='';" style="padding:1px 2px 1px 2px;" onclick="contextDeleteLine()">删除</div>
</div>

<form name="flowForm" method="post">
<input type="hidden" name="flowid" value="" /><input type="hidden" name="tmp" value="ok">
<textarea id="xmltest"name="xmltest" selectable="on" style="width:100%;height:6em;font:12px tahoma;display: none; "></textarea>
</form>
<script src="${base}/wfb/blueflowui/javascript/BlueFlowForView.js"></script>

<script type="text/javascript">

<#assign tableid= arg.tableid>
<#assign dataid= arg.dataid>
<#assign actdefid= arg.actdefid>
<#assign runflowkey= arg.runflowkey>

<#assign saveok = "">
<#assign tmpformid = "">
<#assign tmpformname = ""> 
<#assign tmpclassid = ""> 
<#assign currentActId = "${arg.currentActions}"> 
<#assign flowObject = data.flowObject>
<#assign routeTrailList = data.routeTrail>
<#assign routeEventList = data.routeEvent>

<#assign flowBean = flowObject.flowBean>
<#assign f_id = flowBean.mid>
<#assign f_cname = flowBean.cname>
<#assign f_ver = flowBean.ver>
<#assign f_state = flowBean.state>
<#assign f_sno = flowBean.sno>
<#assign f_formid = flowBean.formid>
<#assign tmpformid =f_formid>
<#assign f_formname = flowBean.formname>
<#assign tmpformname = f_formname>
<#assign f_createformid = flowBean.createformid>
<#assign f_createformname = flowBean.createformname>
<#assign f_startchoice = flowBean.startchoice>
<#assign f_readerchoice = flowBean.readerchoice>
<#assign f_classid = flowBean.classid>
<#assign tmpclassid = f_classid>
<#assign f_classname = flowBean.classname>
<#assign flowOwnerList = flowBean.flowOwnerBean>
<#assign flowReaderList = flowBean.flowReaderBean>

var kkgg1=0,kkgg2=0,currentpath="",walkedRoute="",eventSActid="",eventEActid="",eventEActidList="",routeid="",speed=2000 ,conplay=0; 
var tableid="",dataid="",actdefid="",rootpath="",runflowkey="";
  
document.forms[0].flowid.value="${f_id}";
document.all.flowname.innerText="流程名称："+"${f_cname}";
document.all.fsno.innerText= "流程编号："+"${f_sno}";
document.all.flowver.innerText="版本："+"${f_ver}";
document.all.flowstate.innerText="当前状态："+"${f_state}";
document.all.flowclass.innerText="流程类别："+"${f_classname}";  
 
var f_id="${f_id}",f_cname="${f_cname}",f_formid="${f_formid}",f_formname="${f_formname}",f_createformid="${f_createformid}",f_createformname="${f_createformname}",f_startchoice="${f_startchoice}";
var f_readerchoice="${f_readerchoice}",f_classid="${f_classid}",f_classname="${f_classname}";

var fOwnerTemp=new Array();
<#list flowOwnerList as flowOwnerBean>
	<#assign fo_id = flowOwnerBean.id>
	<#assign fo_Cname = flowOwnerBean.cname>
	<#assign fo_Flowid = flowOwnerBean.flowid>
	<#assign fo_Ctype = flowOwnerBean.ctype>
	<#assign fo_Ownerchoice = flowOwnerBean.ownerchoice>
	<#assign fo_Ownerctx = flowOwnerBean.ownerctx>

	fOwnerTemp.push(new Array("${fo_Cname}","${fo_Ownerctx}","${fo_Ctype}","${fo_Ownerchoice}"));
</#list>

var fReaderTemp=new Array();
<#list flowReaderList as FlowReaderBean>
	<#assign fr_id = FlowReaderBean.id>
	<#assign fr_Cname = FlowReaderBean.cname>
	<#assign fr_Flowid = FlowReaderBean.flowid>
	<#assign fr_Ctype = FlowReaderBean.ctype>
	<#assign fr_Readerctx = FlowReaderBean.readerctx>
	
	fReaderTemp.push(new Array("${fr_Cname}","${fr_Readerctx}","${fr_Ctype}"));  
</#list>

var v_Flow = new Flow(f_id,f_cname,f_formid,f_createformid,f_startchoice,f_readerchoice,f_classid,fOwnerTemp,fReaderTemp,f_formname,f_createformname,f_classname);

v_Flow.setAtt(); 

<#assign actList = flowObject.actBean>
<#list actList as actBean>
	<#assign a_id = actBean.id>
	<#assign a_Cname = actBean.cname>
	<#assign a_Ctype = actBean.ctype>
	<#assign a_Flowid = actBean.flowid>
	<#assign a_Formid = actBean.formid>
	<#assign a_FormName = actBean.formname>
	<#assign a_Isfirst = actBean.isfirst>
	<#assign a_join = actBean.join>
	<#assign a_Outstyle = actBean.outstyle>
	<#assign a_Selectstyle = actBean.selectstyle>
	<#assign a_Selectother = actBean.selectother>
	<#assign a_Split = actBean.split>
	<#assign a_Handletype = actBean.handletype>
	<#assign a_x = actBean.x>
	<#assign a_y = actBean.y>
	<#assign actOwnerList = actBean.actOwnerBean>	
	<#assign actTaskList = actBean.actTaskBean>	
	<#assign actStList = actBean.actStBean>	

	var actOwnerTemp=new Array();
	<#list actOwnerList as actOwnerBean>
		<#assign ao_id=actOwnerBean.id>
		<#assign ao_Cname=actOwnerBean.cname>
		<#assign ao_Ctype=actOwnerBean.ctype>
		<#assign ao_Actid=actOwnerBean.actid>
		<#assign ao_Outstyle=actOwnerBean.outstyle>
		<#assign ao_Ownerchoice=actOwnerBean.ownerchoice>
		<#assign ao_Ownerctx=actOwnerBean.ownerctx>
		<#assign la="\"">
		<#assign ra="\"">
		
		<#if a_id == ao_actid>
		actOwnerTemp.push(new Array("${ao_Cname}","${ao_Ownerctx}","${ao_Ctype}","${ao_Ownerchoice}","${ao_Outstyle}"));
		</#if>
	</#list>
	
   	var actTaskTemp=new Array(),at_rtlistName="",at_rtlistId="",at_rtactTaskId=""; 
   	
	<#list actTaskList as actTaskBean>
		<#assign at_id=actTaskBean.id>
		<#assign at_Cname=actTaskBean.cname>
		<#assign at_Ctype=actTaskBean.ctype>
		<#assign at_Actid=actTaskBean.actid>
		<#assign at_Apptaskid=actTaskBean.apptaskid>
		<#assign at_Descript=actTaskBean.descript>
		<#assign at_Require=actTaskBean.require>
		<#assign at_Sno=actTaskBean.sno>

		<#if a_id == at_actid>
   		actTaskTemp.push(new Array("${at_id}","${at_Sno}","${at_Cname}","${at_Descript}","${at_Ctype}","${at_Require}","${at_Apptaskid}"));
 		at_rtlistName+="${at_Cname}," ;
        at_rtlistId+="${at_Apptaskid}," ;
        at_rtactTaskId+="${at_id}," ;
		</#if>
	</#list>
   	
   	var actStTemp=new Array();
	<#list actStList as actStBean>
		<#assign ast_id=actStBean.id>
		<#assign ast_Cname=actStBean.cname>
		<#assign ast_Code=actStBean.code>
		<#assign ast_Actid=actStBean.actid>
		<#assign ast_Context=actStBean.context>
		<#assign ast_Routeid=actStBean.routeid>
		<#assign ast_RouteName=actStBean.routename>

		<#if a_id == ast_actid>
   		actStTemp.push(new Array("${ast_id}","${ast_Cname}","${ast_Context}","${ast_Code}","${ast_Routeid}","${ast_RouteName}"));
		</#if>
	</#list>
	
   	   	
var trlist=at_rtlistName+":" + at_rtlistId+":" + at_rtactTaskId;

var currentActId="${currentActId}";tableid="${tableid}",dataid="${dataid}",actdefid="${a_id}",rootpath="${rootpath}",runflowkey="${runflowkey}";
var image=null;
var v_Node = new Node("${a_id}","${a_Cname}","${a_Ctype}","${a_Flowid}","${a_Formid}","${a_FormName}", "${a_Handletype}","${a_join}","${a_Split}","${a_Isfirst}","${a_Outstyle}","${a_Selectstyle}","${a_Selectother}",actOwnerTemp,actTaskTemp,image,"${a_x}","${a_y}","","","",actStTemp,trlist,rootpath,tableid,dataid,actdefid);

v_Node.draw();  
v_Node.setAtt(); 

if(kkgg1==0) if("${a_Ctype}"=="BEGIN")  kkgg1=1;
if(kkgg2==0) if("${a_Ctype}"=="END")  kkgg2=1;  


</#list>

<#assign routeList = flowObject.routeBean>
<#assign id = "">
<#assign points="">
<#assign sactid="">
<#assign eactid="">
<#assign eactidList="">
<#assign routeid="">
<#assign rtid="">
<#assign r_id="">
	
<#list routeList as routeBean>
	<#assign r_id=routeBean.id>
	<#assign r_Cname=routeBean.cname>
	<#assign r_Conditions=routeBean.conditions>
	<#assign r_Direct=routeBean.direct>
	<#assign r_Flowid=routeBean.flowid>
	<#assign r_Routetype=routeBean.routetype>
	<#assign r_Startactid=routeBean.startactid> 
	<#assign r_Endactid=routeBean.endactid> 
	<#assign r_MPoints=routeBean.mPoints> 

	<#assign routeTaskList=routeBean.routeTaskBean>
	<#assign id = id + r_id+";">
	<#assign points= points + r_MPoints+";">
	
	var rTaskTemp = new Array();
	<#list routeTaskList as routeTaskBean>
		<#assign rt_id=routeTaskBean.id>
		<#assign rt_Acttaskid=routeTaskBean.acttaskid>
		<#assign rt_Require=routeTaskBean.require>
		<#assign rt_Routeid=routeTaskBean.routeid>
		<#assign rt_TaskName=routeTaskBean.taskname>
		<#if rt_Routeid==r_id>
			rTaskTemp.push(new Array("${rt_Acttaskid}","${rt_Require}","${rt_TaskName}"));  
		</#if>
	</#list>
	
	<#list routeTrailList as routeWalkBean>
		<#assign rtid=rtid+routeWalkBean.rtid+";">
	</#list>

var r_Line = new Line("${r_id}","${r_Cname}","${r_Routetype}","${r_Conditions}","${r_Startactid}","${r_Endactid}","${r_Flowid}","${r_Direct}",rTaskTemp,"${r_MPoints}"); 
rtid="${rtid}";
rrid="${r_id}";

if(rtid.indexOf(rrid)>=0) 
{
	r_Line.drawForView();
}
else
{
	r_Line.draw();
}

r_Line.setAtt();

</#list>	

<#list routeTrailList as routeWalkBean>
	<#assign rtid=rtid+routeWalkBean.rtid+";">
</#list>

<#list routeEventList as routeEventBean>
      <#assign sactid=sactid+ routeEventBean.sactid+";">
      <#assign eactid=eactid+routeEventBean.eactid+";">
      <#assign eactidList=eactidList + routeEventBean.eactidList+";">
      <#if routeEventBean.routeid==""> 
      	<#assign routeid=routeid+"-1;">
      <#else>
      	<#assign routeid=routeid + routeEventBean.routeid+";">
      </#if>	
</#list>

	var rid="${id}",rpoints="${points}",rtid="${rtid}";;
	var rid1=rid.split(";"),rpoints1=rpoints.split(";");
	walkedRoute="${rtid}";
	 
	eventSActid="${sactid}";
	eventEActid="${eactid}";
	eventEActidList="${eactidList}";
	routeid="${routeid}";
    
    //input 
    var sactList=eventSActid.split(";");
    var eactList=eventEActid.split(";");
    var allEactList=eventEActidList.split(";");
   
    var ridList=routeid.split(";");
    var stepLength=ridList.length;
    tempList=eventSActid ;
    var allactList=tempList.split(";");;
    tal="";talt="";
    
	//play
    for(i=0;i<allactList.length ;i++)
    {
         tal+=allactList[i]+";"+allEactList[i]+";";
    }
    tal=tal.substring(0,tal.length-1);
     
    temp="";
    for(i=0;i<tal.length;i++)
    {
		if(tal.substring(i,i+1)==":") 
		{
			temp+=";"; 
		}
      	else
      	{
			temp+=tal.substring(i,i+1);
		}
    }
	var tempallactList=temp.substring(0,temp.length-1);
	allactList=tempallactList.split(";");
	temp=allactList[0];
	s3=temp+";";
 
	for(i=0;i<allactList.length;i++)
	{  
		if(allactList[i]!=temp)  
		{
			s3+=allactList[i]+";"; 
		}
	  	temp=allactList[i];
	}
    
    allactList=s3.split(";");
    stepLength=allactList.length-1;
    

ddiv.oncontextmenu=function(){return false;}
 
tmp_formid="${tmpformid}"; 
tmp_formname="${tmpformname}"; 
tmp_classid="${tmpclassid}"; 

if(kkgg1==1) beginNum=1;
if(kkgg2==1) endNum=1;
</script>

</body>
</html>