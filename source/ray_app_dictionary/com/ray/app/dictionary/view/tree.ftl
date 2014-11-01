{items:[
<#list data.list as treeview>
{name:'${treeview.dname}',leaf:${treeview.islast},id:'${treeview.id}',type:'root'}<#if treeview_index+1 lt data.list?size>,</#if>
</#list>
]}
