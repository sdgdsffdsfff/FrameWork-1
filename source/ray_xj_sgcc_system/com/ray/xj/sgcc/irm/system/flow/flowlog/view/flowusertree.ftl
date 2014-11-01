[
<#list data.users as user>
{name:'${user.cname}',id:'${user.loginname}',leaf:0,sex:'${user.sex}',deptname:'${user.owneorgname}',deptid:'${user.ownerorg}'}<#if user_index+1 lt data.users?size>,</#if>
</#list>
]