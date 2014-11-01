<chart caption='${data.vo.title}' shownames="1" decimals="2" baseFontSize="11" baseFontColor="666666">

<categories>

<#list data.datas as aobj>


<#if aobj_index == 0>
<#else>
<category label="${aobj[data.vo.chart.fs]}"/>
</#if>
</#list>

</categories>

<#assign akeys = data.datas[0]?keys>
<#assign akeys_size = akeys?size>


<#list akeys as akey>
<#if akey_index &lt; akeys_size - 1>
<dataset seriesName="${(data.datas[0])['c${akey_index}']}" showValues="0">
	<#list data.datas as aobj>
	<#if aobj_index == 0>
	<#else>
	<set value="${aobj['c${akey_index}']}"/>
	</#if>
	</#list>
</dataset>
</#if>

</#list>

</chart>

