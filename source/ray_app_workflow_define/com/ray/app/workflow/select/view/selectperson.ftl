<!DOCTYPE HTML>
<html>
<head>
<title>选择人员操作</title>
<#include "/decorator/include/include.ftl">
<script language="javascript">
  function google()
  {
	key=document.forms[0].inputname.value;
	tar=document.forms[0].selectableall;
	max = tar.options.length;
	for(var j = 0; j<max; j++)
	{
      if (tar.options[j].selected)
      {tar.options[j].selected = false;}
	}
  
	for (i=0; i<max; i++)
	{
	 str1=tar.options[i].text;
	 n=str1.indexOf(key);
	if (n!=-1)
	{ 
	 tar.options[i].selected=true;
	 	  document.all.objname.value = tar.options[i].text;
	      document.all.objid.value = tar.options[i].value;
        return 
      }
	}
  }
function passvalue(tar)
{
	max = tar.options.length;
	var  sValue=new Array();
	var sValue1="";
	var sValue2="";

	for (i=0; i<max; i++)
	{   
        if (tar.options[i].selected)
        {
		   	if (sValue1=="")
		  	{
		  		sValue1 = tar.options[i].text;
		  	 	sValue2 = tar.options[i].value;
		  	}
			else
		  	{
		   		
		   		//单选实现 
		   		sValue1 = tar.options[i].text;
		   		sValue2 = tar.options[i].value;
		   	}
		}
    }
	
	document.all.objname.value = sValue1;
	document.all.objid.value = sValue2;
}
</script>
</head>
<body>
	<form>
		<input type=hidden name=objid value="">
		<input type=hidden name=objname value="">

		<table cellpadding="0" cellspacing="0" width="100%">
			<tr height="25">
				<td>
				<div style="float:left;line-height:25px;">待选人员 ：</div>
				<div style="float:right;"><input  name="inputname" Value="" class="text fullWidth"><button onclick="google();">人员查询</button></div>		 
				</td>
			</tr>
			<tr>
				<td style="vertical-align:top;">
				<select style="width:100%;height:241px;border:solid 1px #ccc;" multiple="multiple" name="selectableall" onchange="passvalue(document.forms[0].selectableall)" title="可以双击添加项目，也可以选择多个项目，点击添加按钮">
				<#list data.optstr as user>
					<option value="${user.id}">${user.cname}
				</#list> 
				</select>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>