<!DOCTYPE HTML>
<html>
<head>
<#include "/decorator/include/include.ftl">
<script   type="text/javascript" src="${base}/wfb/blueflowui/javascript/index.js"></script>
<script   type="text/javascript" src="${base}/wfb/blueflowui/javascript/common.js"></script>
<script   type="text/javascript" src="${base}/wfb/blueflowui/javascript/commonvalidate.js"></script>

<script type="text/javascript">
		function IF_IllegalForTree(s){
		  
		    for (var i=0; i<s.length; i++) {  
		        var Char = s.charAt(i);  
		        if (Char =="\"" || Char =="\'" || Char=="\“" ||Char == "\”" || Char=="\‘" ||Char == "\’"||Char == "-")
		        	return false;  
	   		 }  
		    return true;  
		}  
	
		function validateIllegalForTree(fieldname,fieldtitle){
			if (eval("document.form1."+fieldname+".value")==""){
			   	return true;
			}else{
				if (!IF_IllegalForTree(eval("document.form1."+fieldname+".value"))){
				    alert(fieldtitle+" 不能含有非法字符（ \", \' , \“,\”,\‘,\’,-）！");
		    	    return false;
			    }
			}
			return true;
		
		}
	
	function getParent(el, pTagName) 
	{ 
	    // getParent object	
	
		while (el && (!el.tagName || el.tagName.toLowerCase() != pTagName.toLowerCase()) )
			{
				el = el.parentNode;
			}
			 
		return el;
	}
	
	function OpenFlowClass() {
		var el = getParent(window.event.srcElement,"button");
		var flowclass = window.showModalDialog("<%=PathToolKit.getRootPath(request)%>/WFBFlowClassForm?ACTION=SelectBFlowClass&classMgrClassId="+document.all.classMgrClassId.value,el,"status:off;help:0;dialogWidth:600px;dialogHeight:410px;edge:sunken; ");
	   if(typeof(flowclass)!="undefined") {
	    document.all.classid.value=flowclass.id;    document.all.classname.value=flowclass.name;
	    }
	}
	function OpenFlowClassNew() {
	    window.document.forms[0].action="<%=PathToolKit.getRootPath(request)%>/WFBFlowClassForm?ACTION=SelectBFlowClass&classMgrClassId="+document.all.classMgrClassId.value;
	    window.document.forms[0].submit();
	 }
	function OpenFlowFormNew(id,text) {
	    clsid=window.document.all.classid.value;
	    if((clsid=="")||(clsid=="null")) 
	    {
	    alert("请先选择流程类别！");
		    return;
	    }
	
	    document.all.formid.value= this.id;
	    document.all.formname.value= this.text;
	    document.all.createformid.value= this.id;
	    document.all.createformname.value= this.text;
	    
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


$('#formsel').change(function(){

	document.all.classid.value=$("#formsel").val();  
	document.all.classname.value=$("#formsel option:selected").text();
})

//////////////////////////
})

</script>
<style>
	html,body {height:auto;overflow:hidden;}
</style>
</head>
	
<body>
	<form id="flowForm" name="form1">
	<input type="hidden" name="classMgrClassId" value="${classMgrClassId!'123'}"/>
	<input type="hidden" name="flowowner_ownerctx">
	<input type="hidden" name="flowowner_ctype">
	<input type="hidden" id="flowowner_cname" name="flowowner_cname">
	<input type="hidden" name="flowowner_ownerchoice" value=""  />
	<input type="hidden" name="flowreader_readctx">
	<input type="hidden" name="flowreader_ctype">
	<input type="hidden" name="flowreader_cname">
	<input type="hidden" name="field">
	<input type="hidden" name="formid" value="" />
	<input type="hidden" name="formname" value="" />
	<input type="hidden" name="createformid" value="" />
	<input type="hidden" name="createformname" value="" />
	<input type="hidden" name="mid" value="${(data.bflow.id)!}" />

	<table  width="100%" border="0" cellpadding="3" cellspacing="0">
		<tbody>
			<tr>
				<td>
					流程名称：<input type="text" name="cname" value="${(data.bflow.cname)!}" size="30"  style="display: none; "/>
					<label id="rdcname" style="display: none;"></label> 
				</td>
				<td>
					流程编号：<input type="text" name="sno" value="${(data.bflow.sno)!}" size="30" style="display: none; " /> 
					<label id="rdsno" style="display: none;"></label>
					<input type="hidden" name="ver" value="1" size="20" /> 
					<input type="hidden" name="state" value="1" size="20" /> 
					<input type="hidden" name="classid" value="" /><input id="clsname" type="hidden" name="classname" value="" readonly="readonly" />
				</td>
				<td id="rdcls1" style="display: none;">流程类别：<LABEL id="rdcls"></LABEL></td>
				<td id="cls1" style="display: none;">
					流程类别：
					<select id="formsel" >
					<option value=""   >请选择 </option>
		            <#list data.bflows as bflow>
		            	<option value="${bflow.id}">${bflow.cname} </option>
		            </#list>			
				</td>
			</tr>
			<tr>
				<td style="display: none;">启动选择方式：<input type="radio" name="startchoice" value="1"  >是<input type="radio" name="startchoice" checked="true" value="0">否</td>
				<td style="display: none;">读者选择方式：<input type="radio" name="readerchoice" value="1">是<input type="radio" name="readerchoice" checked="true" value="0">否</td>
			</tr>
		</tbody>
	</table>

<ul id="puTabsTB">
	<li class="c">管理员</li>
	<li>读者</li>
	<#--<li>表单</li>
	-->
</ul>


<ul id="puTabsTBContent">
	<li><iframe  src="${base}/module/app/system/workflow/select/select_selowner.action" height="350" scrolling="auto" name="frame0" frameborder="0"></iframe></li>
	<li><iframe  src="${base}/module/app/system/workflow/select/select_selreader.action" height="350" scrolling="auto" name="frame0" frameborder="0"></iframe></li>
	<li><iframe  src="${base}/module/app/system/workflow/bflow/bflow_form.action?flowid=${(data.bflow.id)!}" height="350" scrolling="auto" name="frame0" frameborder="0"></iframe></li>
</ul>

<#--

<table width="100%" border="0" cellpadding="3" cellspacing="0">
	<tr>
		<td nowrap class="selected"   onclick="for(i=0;i<this.parentElement.children.length;i++){this.parentElement.children[i].className='unselected'} this.className='selected';for(j=0;j<document.all.tags('fieldset').length;j++){document.all.tags('fieldset')[j].style.display='none';mobile.style.display='';} ; document.all.ff1.style.display='';document.all.ff2.style.display='none';document.all.ff3.style.display='none';  ">&nbsp;管理员&nbsp;</td>
		<td nowrap class="unselected" onclick="for(i=0;i<this.parentElement.children.length;i++){this.parentElement.children[i].className='unselected'} this.className='selected'; document.all.ff1.style.display='none';document.all.ff2.style.display='';document.all.ff3.style.display='none';   ">&nbsp;&nbsp;读者&nbsp;&nbsp;</td>
		<td nowrap class="unselected" onclick="for(i=0;i<this.parentElement.children.length;i++){this.parentElement.children[i].className='unselected'} this.className='selected'; document.all.ff1.style.display='none';document.all.ff2.style.display='none';document.all.ff3.style.display='';   ">&nbsp;&nbsp;表单&nbsp;&nbsp;</td>
		<td width="100%" style="border:none;border-bottom:solid 1px inset;">&nbsp;</td>
	</tr>
</table>
	
	
	
	<table width="100%" border="0" cellpadding="3" cellspacing="0" style="width:100%;border:outset 1px;border-top:none;" bgcolor="#eeeeee">
		<tr>
			<td width="100%" height="450">
				<table height="100%" width="100%" >
					<tr>
						<td   id="f1" >
						<div id="ff1">
							<iframe   src="${base}/module/app/system/workflow/select/select_selowner.action" width="100%"  height="400"  scrolling="auto" name="frame0"></iframe>
						</div>
						</td >
						<td  id="f2">
							<div id="ff2" style="display: none;">
							<iframe src="${base}/module/app/system/workflow/select/select_selreader.action" width="100%"  height="400" scrolling="auto"name="frame1"></iframe>
							</div>
						</td>
						<td  id="f3">  
							<div id="ff3" style="display: none;">
							<iframe src="${base}/module/app/system/workflow/bflow/bflow_form.action" width="100%"  height="400" scrolling="auto" name="frame2"></iframe>
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
	

<script type="text/javascript">
	var flowid='${(data.bflow.id)!}';
 	if(flowid=="")  
	{
	  document.all.cls1.style.display="";  
	  document.all.rdsno.style.display="none"; 
	  document.all.sno.style.display=""; 
	  document.all.rdcname.style.display="none"; 
	  document.all.cname.style.display=""; 
	  document.all.rdcls1.style.display="none"; 
	  document.all.clsname.style.display="none";
	 }
	else
	{
	  document.all.rdsno.style.display=""; 
	  document.all.sno.style.display="none"; 
	  document.all.rdcname.style.display=""; 
	  document.all.cname.style.display="none"; 
	  document.all.rdcls1.style.display=""; 
	  document.all.clsname.style.display="none"; 
	  document.all.cls1.style.display="none"; 
	 }
	 
	//document.all.ff2.style.display="none";document.all.ff3.style.display="none"; 
 	function rvalue(el)
	{
		if(typeof(el) == "undefined")
		{
			return "";
		}
		return el;
	}

	var flow = window.dialogArguments;
	var form=document.getElementById("flowForm");

	form.mid.value = rvalue(flow.mid);
	
	form.cname.value = rvalue(flow.cname);
	form.sno.value = rvalue(flow.sno);
	form.ver.value = rvalue(flow.ver);
	form.state.value = rvalue(flow.state);
	form.formid.value = rvalue(flow.formid);
	form.formname.value = rvalue(flow.formname); 
	form.createformid.value = rvalue(flow.createformid);
	form.createformname.value =rvalue(flow.createformname);

	setRadioValue(form.startchoice,flow.startchoice)
	setRadioValue(form.readerchoice,flow.readerchoice)

	form.classid.value = rvalue(flow.classid);
	form.classname.value=rvalue(flow.classname);
	form.field.value=rvalue(flow.field);

	setMultiValue('flowowner_cname,flowowner_ownerctx,flowowner_ctype,flowowner_ownerchoice',flow.flowowner);
	setMultiValue('flowreader_cname,flowreader_readctx,flowreader_ctype',flow.flowreader);

	function sendTo()
	{
		
		/*if(form.formid.value=="") 
		{
		  alert("流程表单不能为空！");
		  return;
		}*/
		if(form.classid.value=="") 
		{
		  alert("流程类别不能为空！");
		  return;
		}
	 	if (! (validateNull('cname','流程名称') &&validateLong('cname','流程名称','60')&&validateIllegal('cname','流程名称') )) return;
		if(form.sno.value=="") 
		{
		  alert("流程编号不能为空！");
		  return;
		}
	  	if (! (validateNull('sno','流程编号') &&validateLong('sno','流程编号','60')&&validateIllegalForTree('sno','流程编号') )) return;
	
		flow.mid = form.mid.value;
		flow.cname = form.cname.value;
		flow.sno = form.sno.value;
		flow.ver = form.ver.value;
		flow.state = form.state.value;
		if(flowid=="")  
		{
			flow.ver="1";
			flow.state="起草";
		}
		flow.formid = form.formid.value;
		flow.formname = form.formname.value;
		flow.createformid = form.formid.value;
		flow.createformname = form.formname.value;
		flow.field = form.field.value;

		flow.startchoice = getRadioValue(form.startchoice);
		flow.readerchoice =getRadioValue(form.readerchoice);

		flow.classid = form.classid.value;
		
		flow.classname= form.classname.value;
	    document.all.flowowner_ownerchoice.value=document.all.flowowner_ctype.value;
		flow.flowowner =  getMultiValue('flowowner_cname,flowowner_ownerctx,flowowner_ctype,flowowner_ownerchoice');
		flow.flowreader =  getMultiValue('flowreader_cname,flowreader_readctx,flowreader_ctype');
	
		window.returnValue = flow;
		window.close();
	}
	var flowid='${(data.bflow.id)!}';
	document.all.rdsno.innerText= document.all.sno.value;
	document.all.rdcname.innerText= document.all.cname.value;
	document.all.rdcls.innerText= document.all.classname.value;
	 
	

	//---------------locate flowclass--
	  fname=document.all.classid.value;
	  fsel=document.getElementById("formsel");
	  for(i=0;i<fsel.length;i++)
	  {
	    if(fname==fsel.options[i].value)
	       {
	       fsel.options.selectedIndex=i;
	       break;
	       }
	  }


</SCRIPT>
	
</body>
</html>
