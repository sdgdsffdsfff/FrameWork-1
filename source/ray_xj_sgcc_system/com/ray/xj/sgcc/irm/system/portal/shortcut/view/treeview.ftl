{items:[
<#list data.lists as treeview>
{name:'${treeview.cname}',id:'${treeview.id}'}<#if treeview_index+1 lt data.lists?size>,</#if>
</#list>
]}
