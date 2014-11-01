<#import "com/ray/app/query/view/page_macros.ftl" as pub_macros>

<#macro displaylisttable apage vo arg>
<#assign listtableid = "listtable">
<#if vo.listtableid != "">
<#assign listtableid = vo.listtableid>
</#if>

<div class="oTable">

<table class="gGrid" id="${listtableid}">
<@pub_macros.displayheader vo = vo arg = arg></@pub_macros.displayheader>
<@displaylist apage = apage vo = vo></@displaylist>
</table>

</div>

</#macro>

<#macro displaylist apage vo>
<#assign flag = "true">

<#list apage.result as hs>

	<#assign keyvalues = "">
	<#assign keynames = "">

	<#assign attrvalues = "">
	<#assign attrnames = "">

	<#list vo.searchoptions as searchoption>
		<#if searchoption.pkey == "1">
			<#if keynames !="">
			<#assign keynames = keynames + ",">
			</#if>
			<#assign keynames = keynames + searchoption.field>
			
			<#if keyvalues !="">
			<#assign keyvalues = keyvalues + ",">
			</#if>
			<#assign keyvalues = keyvalues + hs[searchoption.field]>
		</#if>
		
		<#if searchoption.trattr == "Y">
			<#if attrnames !="">
			<#assign attrnames = attrnames + ",">
			</#if>
			<#assign attrnames = attrnames + searchoption.field>
			
			<#if attrvalues !="">
			<#assign attrvalues = attrvalues + ",">
			</#if>
			<#assign attrvalues = attrvalues + hs[searchoption.field]>
		</#if>
		
	</#list>
	
	<tr id="tr_${hs_index + 1}" keyname="${keynames}" key="${keyvalues}" index="${hs_index + 1}" <#if attrnames!="">${attrnames}="${attrvalues}"</#if> bgcolor="<#if hs_index % 2 == 0>F4F4F4<#else>FDFDFD</#if>" class="hand">
	<#if vo.ischeck == "Y">
	<td nowrap>
	<input type="checkbox" id="index_${hs_index + 1}" name="index" class="" value="${keyvalues}">
	</td>
	</#if>
	<input type="hidden" id="vindex_${hs_index + 1}" name="vindex" value="${keyvalues}">
	<#assign aflag = "left">
	<#if vo.isno == "Y">
	<td width="1%" nowrap>${((apage.currentpage-1)*apage.pagesize) + (hs_index + 1)}</td>
	</#if>
	

	<#list vo.searchoptions as option>
	<#assign value = "hs.${option.field}"?eval>
	<#escape value as value!""></#escape>
	<@pub_macros.displayfield option = option value = value />
	</#list>
	</tr>

</#list>

</#macro>


