[
<#list data.organs as organ>
{name:'${organ.cname}',id:'${organ.id}',ctype:'${organ.ctype}',allname:'${organ.allname}'}<#if organ_index+1 lt data.organs?size>,</#if>
</#list>
]