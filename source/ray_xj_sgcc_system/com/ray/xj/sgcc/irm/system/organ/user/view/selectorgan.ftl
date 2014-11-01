[
<#if data.users ?size &gt; 0>
<#list data.users as user>
{name:'${user.cname}',id:'${user.loginname}',leaf:0,sex:'${user.sex}',deptname:'${user.owneorgname}',deptallname:'${user.allname}',deptid:'${user.ownerorg}'}<#if user_index+1 &lt; data.users?size>,</#if>
</#list>
<#if data.users?size &gt; 0>
,
</#if>
</#if>
<#list data.organs as organ>
{name:'${organ.cname}',id:'${organ.id}',leaf:1,sex:'',deptname:'${organ.cname}' ,deptallname:'${organ.allname}',deptid:'${organ.id}'}<#if organ_index+1 &lt; data.organs?size>,</#if>
</#list>
]
