[
	<#list data.bean_owners as p>
	{"ownerctx" : "${p.ownerctx}", "cname" : "${p.cname}", "ctype" : "${p.ctype}"}
	<#if p_index &lt; data.bean_owners?size - 1>,</#if>
	</#list>	
]
