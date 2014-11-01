[
<#list data.roles as role>
{name:'${role.cname}',id:'${role.id}'}<#if role_index+1 lt data.roles?size>,</#if>
</#list>
]