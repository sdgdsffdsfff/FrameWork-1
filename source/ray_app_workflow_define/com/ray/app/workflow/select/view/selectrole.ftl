<!DOCTYPE HTML>
<html>
<head>
<title>选择角色操作</title>
<#include "/decorator/include/include.ftl">
<script language="Javascript">

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
     //  if (max == 0) return alert("请至少选择一个！");

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
		  	    //多选尚未实现
		  		//sValue1 = sValue1 + "," + tar.options[i].text;
		   		//sValue2 = sValue2 + "," + tar.options[i].value;
		   		
		   		//单选实现 
		   		sValue1 = tar.options[i].text;
		   		sValue2 = tar.options[i].value;
		   		
		   	}
		}
    }
	
	document.all.objname.value = sValue1;
	document.all.objid.value = sValue2;
	//alert(document.all.objname.value);
	//sValue[0]=sValue1;
	//sValue[1]= sValue2;
	//alert (sValue)  
    
    
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
<div style="float:left;line-height:25px;">待选角色 ：</div>
<div style="float:right;"><input  name="inputname" Value="" class="text fullWidth"><button onclick="google();">角色查询</button></div>
</td>
</tr>
<tr>
<td>
<select style="width:100%;height:241px;border:solid 1px #ccc;" multiple="multiple" name="selectableall" onchange="passvalue(document.forms[0].selectableall)" onhelp="showHelp()" help="可以双击添加项目，也可以选择多个项目，点击添加按钮">
<#list data.optstr as roles>
	<option value="${roles.id}">${roles.cname}
</#list>
</select>
</td>
</tr>
</table>

</form>
</body>
</html>