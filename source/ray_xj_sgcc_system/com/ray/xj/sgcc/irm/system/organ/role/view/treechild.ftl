{items:[
<#list data.list as treeview>
{name:'${treeview.cname}',leaf:${treeview.islast?default('0')},id:'${treeview.fnum}',type:'folder'}<#if treeview_index+1 lt data.list?size>,</#if>
</#list>
]}