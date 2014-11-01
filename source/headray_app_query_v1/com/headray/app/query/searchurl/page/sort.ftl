<script type="text/javascript" src="${base}/resource/default/script/public_up_down.js"></script>

<div id="navigation"><span id="op"></span><strong>当前位置：</strong>管理平台.基础数据管理.查询信息管理.查询地址.排序</div>
 <form  name="form" id="form" action="invoke!sort.action?searchid=${arg.searchid}&flag=D" method="post" >
<table width="100%" border="0" cellpadding="0" cellspacing="1"   id="listtable">  
	  <tr>
       		<th>标识</th>
			<th>字段名</th>
			<th>标题</th>
			<th>排序号</th>
       </tr>  
       <span id="trIds">
       <#list data.urls as urls>  
       <tr name="trs" id="${urls_index}" class="hand" bgcolor="<#if urls_index % 2 == 0>F4F4F4<#else>FDFDFD</#if>" onclick="select(${urls_index})">    
            <td  align="left"><input type="radio" name="radios"  id="radio_${urls_index}"/>${urls.searchurlid}<input type="hidden" id="oorder" name="oorder"  value="${urls_index+1}"></td> 
            <td>${urls.pname} <input type="hidden" id="searchurlid" name="searchurlid" value="${urls.searchurlid}"  ></td>   
            <td>${urls.title} </td> 
            <td>${urls.oorder} </td>
       </tr>  
       </#list> 
       </span>
 </table>  
 <table>
   <tr><td></td></tr>
   <tr><td></td></tr>
   <tr><td></td></tr>
   <tr><td></td></tr>
   <tr><td></td></tr>
   <tr><td></td></tr>
   <tr><td></td></tr>
   <tr><td></td></tr>
   <tr><td></td></tr>
 </table>
 <div id="buttondiv" class="btn-container" align="center">	
		<button class="mySubmit" onclick="upList()">上  移</button>
		<button class="mySubmit" onclick="downList()">下  移</button>
		<button class="mySubmit" onclick="page_confirm()">确  定</button>
</div> 
</form >
 <script type="text/javascript">
    function page_confirm()
	{
		window.opener.location.reload(); 
		window.close(); 
		form.submit();
	}
	function select(id){
 		document.getElementById("radio_"+id).checked=true
 	}
</script> 