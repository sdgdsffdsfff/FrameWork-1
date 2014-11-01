<#import "page_macros.ftl" as pub_macros>

<#macro displayheader vo arg>
<#assign clistnames = arg.clistnames.split(",")>
<#assign swapnames = arg.swapnames.split(",")>

<thead class="titleTr">
<tr>
	<th>&nbsp;No.&nbsp;</th>
	<#list clistnames as clistname>
	<#-- 增加根据指定显示列显示对应数据 -->
	<#assign title="">
	<#list data.vo.searchoptions as searchoption>
		<#if searchoption.field = clistname>
			<#assign title=searchoption.title>
		</#if>
	</#list>
		<th nowrap>${title}</th>
	</#list>
</tr>
</thead>
</#macro>

<#macro displaylist apage vo arg>
<#assign clistnames = arg.clistnames.split(",")>
<#assign swapnames = arg.swapnames.split(",")>

<#list apage.list as hs>

	<#-- 增加查询返回列值处理 -->
	<#assign revalue = "">
	<#assign keyname = "">
	<#list swapnames as swapname>
		
	<#assign option=pub_macros.get_field_option(vo.searchoptions, swapname)>
		
		<#-- <#assign revalue = revalue + hs[swapname]?string> -->
		<#assign revalue = revalue + fieldvalue(option, hs[swapname]?string)>
		<#if swapname_has_next><#assign revalue = revalue + ","></#if>
		<#assign keyname = keyname + swapname?string>
		<#if swapname_has_next><#assign keyname = keyname + ","></#if>
	</#list>
	<tr key="${revalue}" keyname="${keyname}">
	<#assign aflag = "left">
	<td width="1%" nowrap align="${aflag}">${hs_index+1}</td>
		
	<#-- 增加根据指定显示列显示对应数据 -->
	<#list clistnames as clistname>
	<#assign option = pub_macros.get_field_option(vo.searchoptions, clistname)>
	<@pub_macros.displayfield option = option value = hs[option.field] />
	</#list>
	</tr>
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