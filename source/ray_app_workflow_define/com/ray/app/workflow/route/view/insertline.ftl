<!DOCTYPE HTML>
<html>
<head>
<#include "/decorator/include/include.ftl">
<title>路由属性</title>
<script type="text/javascript" src="${base}/wfb/blueflowui/javascript/common.js"></script>
<script type="text/javascript" src="${base}/wfb/blueflowui/javascript/commonvalidate.js"></script>
<script type="text/javascript">
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
<input type="hidden" name="trtl" value=""  > 
<form id="lineForm" name="form1">
<input type="hidden" name="routetype"> 
<input type="hidden" name="conditions">
<input type="hidden" name="direct">
<input type="hidden" name="line_acttaskid"	  />

<input type="hidden" name="line_require">
<input type="hidden" name="line_taskname">


<input type="hidden" name="mid" value="" />
<input type="hidden" name="startactid" value="" />
<input type="hidden" name="endactid" value="" />
<input type="hidden" name="flowid" value="" />
<input type="hidden" name="points" value="" />

<input type="hidden" name="nodeTaskListId"   >
<input type="hidden" name="nodeTaskListName"   >
<input type="hidden" name="nodeActTaskId"   >



<table width="100%" border="0" cellpadding="3" cellspacing="10">
<tbody>
<tr>
<td>路由名称：<input type="text" name="cname" value="${data.broute.cname}" size="42"/></TD>
</tr>
</tbody>
</table>

<ul id="puTabsTB">
	<li class="c">路由属性</li>
	<#--<li>路由任务</li>
	-->
</ul>

<ul id="puTabsTBContent">
	<li><iframe  src="${base}/module/app/system/workflow/route/route_linetype.action?routeid=${data.broute.id}" height="330" scrolling="auto" name="frame0" frameborder="0"></iframe></li>
	<li><iframe  src="${base}/module/app/system/workflow/route/route_selacttask.action?routeid=${data.broute.id}"  height="330" width="100%" scrolling="auto" name="frame1" frameborder="0"></iframe></li>
</ul>

<#--
<table width="100%" border="0" cellpadding="3" cellspacing="0">
<tr>
<td nowrap class="selected" onclick="for(i=0;i<this.parentElement.children.length;i++){this.parentElement.children[i].className='unselected'}this.className='selected';for(j=0;j<document.all.tags('fieldset').length;j++){document.all.tags('fieldset')[j].style.display='none';mobile.style.display='';} ; document.all.ff1.style.display='';document.all.ff2.style.display='none';    ">&nbsp;路由属性&nbsp;</td>
<td nowrap class="unselected" onclick="for(i=0;i<this.parentElement.children.length;i++){this.parentElement.children[i].className='unselected'}this.className='selected';;document.all.ff1.style.display='none';document.all.ff2.style.display=''; ">&nbsp;路由任务&nbsp;</td>

<td width="100%" style="border:none;border-bottom:solid 1px inset;">&nbsp;</td>
</tr>
</table>

<table width="100%" border="0" cellpadding="3" cellspacing="0" style="width:100%;border:outset 1px;border-top:none;" bgcolor="#eeeeee">
<tr>
<td>
 	<table width="100%" height="350" bgcolor="">
		<tr>
			<td id="f1">
				<div id="ff1">
				<iframe src="${base}/module/app/system/workflow/route/route_linetype.action" height="100%" width="100%" name="frame0"></iframe>
				</div>
			</td>
			<td  id="f2"  >
				<div id="ff2">
				<iframe  src="${base}/module/app/system/workflow/route/route_selacttask.action" height="100%" width="100%" name="frame1"></iframe>
				</div>
			</td>
		</tr>
	</table>

</td>
</tr>
</table>
-->


</form>

<div style="text-align:right;padding-right:20px;">
	<button onclick="sendTo()">确定</button> <button onclick="window.close()">取消</button>	
</div>

<script>

//document.all.ff2.style.display="none";

var line = window.dialogArguments;
var form=document.getElementById("lineForm");

form.mid.value = rvalue(line.id);

form.cname.value = rvalue(line.cname);
form.routetype.value = rvalue(line.routetype);
form.conditions.value = rvalue(line.conditions); 
form.startactid.value = rvalue(line.startactid);
form.endactid.value = rvalue(line.endactid);
form.flowid.value = rvalue(line.flowid);
form.direct.value = rvalue(line.direct);

setMultiValue('line_acttaskid,line_require,line_taskname',line.routetask);

form.points.value = rvalue(line.mPoints);

tmprtlist=rvalue(line.rtlist).split(":"); 
document.all.nodeTaskListName.value=tmprtlist[0];
document.all.nodeTaskListId.value=tmprtlist[1];
document.all.nodeActTaskId.value=tmprtlist[2];

document.all.trtl.value=rvalue(line.rtlist);

function sendTo()
{
	if (! (validateNull('cname','路由名称') &&validateLong('cname','路由名称','60') )) return;
    document.all.routetype.value=window.frames[0].document.all.routetype.value;
    document.all.conditions.value=window.frames[0].document.all.conditions.value;
    document.all.direct.value=window.frames[0].document.all.direct.value;
    

	line.id = form.mid.value;
	line.cname = form.cname.value;
	line.routetype = form.routetype.value;
	line.conditions = form.conditions.value;
	line.startactid = form.startactid.value;
	line.endactid = form.endactid.value;
	line.flowid = form.flowid.value;
	line.direct = form.direct.value;
	 
	line.routetask =  getMultiValue('line_acttaskid,line_require,line_taskname');

	line.mPoints = form.points.value;

	window.returnValue = line;
	window.close()
}

function selectTask()
{
	var rv=window.showModalDialog('linetask.htm','');

}

function rvalue(el)
{
	if(typeof(el) == "undefined")
	{
		return "";
	}
	return el;
}

</script>
</body>

</html>
