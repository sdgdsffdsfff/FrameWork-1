{items:[
<#list data.list as treeview>
{name:'${treeview.dname}',leaf:${treeview.islast?default('0')},id:'${treeview.id}',type:'<#if treeview.islast?default('0')==1>folder<#else>file</#if>'}<#if treeview_index+1 lt data.list?size>,</#if>
</#list>
]}
