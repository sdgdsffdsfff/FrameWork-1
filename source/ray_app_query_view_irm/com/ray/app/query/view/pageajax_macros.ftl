<#import "com/ray/app/query/view/page_macros.ftl" as pub_macros>

<#macro displayheader vo arg>
<#assign clistnames = arg.clistnames.split(",")>
<#assign swapnames = arg.swapnames.split(",")>
	<#list clistnames as clistname>
		<#assign title="">
		<#list data.vo.searchoptions as searchoption>
			<#if searchoption.field == clistname>
				<#assign title=searchoption.title>
			</#if>
		</#list>
		'${title}'<#if clistname_index+1 lt clistnames?size>,</#if>
	</#list>
</#macro>

<#macro displaylist apage vo arg>
<#assign clistnames = arg.clistnames.split(",")>
<#assign swapnames = arg.swapnames.split(",")>
<#if (apage.result?size>5)>
<#assign results=apage.result[0..4]>
<#else>
<#assign results=apage.result>
</#if>
<#list results as hs>
	{
	<#list swapnames as swapname>
	<#assign option=pub_macros.get_field_option(vo.searchoptions, swapname)>
	<#assign revalue = fieldvalue(option, (hs[swapname])!?string)>
	${swapname?string}:'${revalue}',
	</#list>
	cells:[
	<#list clistnames as clistname>
	<#assign option = pub_macros.get_field_option(vo.searchoptions, clistname)>
	<#assign value = "hs.${option.field}"?eval>
	<#escape value as value!""></#escape>
	'<@pub_macros.displayfield option = option value = value />'<#if clistname_has_next>,</#if>
	</#list>
	]
	}<#if hs_has_next>,</#if>
</#list>

</#macro>


<#function fieldvalue option value>
	<#assign newvalue = "">
	<#if value!= "">
		<#if option.fieldtype == "datetime">
			<#if option.displayformat != "">
				<#assign newvalue = (value)?datetime?string(option.displayformat)>
			<#else>
				<#assign newvalue = (value) + "">
			</#if>
		<#elseif option.fieldtype == "money" || option.fieldtype == "integer" || option.fieldtype == "number">
			<#if option.displayformat != "">
				<#assign newvalue = (value)?number?string(option.displayformat)>
			<#else>
				<#assign newvalue = (value) + "">
			</#if>
		<#else>
			<#if option.displayformat != "">
				<#assign newvalue = (value)?string(option.displayformat)>
			<#else>
				<#assign newvalue = (value) + "">
			</#if>	
		</#if>
	</#if>
	<#return newvalue>
</#function>