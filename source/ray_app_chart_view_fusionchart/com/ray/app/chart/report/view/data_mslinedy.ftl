<chart caption='${data.vo.title}' showValues='0' labelStep='${data.datas?size/5+1}' rotateYAxisName='0'>

<categories>
<#list data.datas as aobj>
<#if aobj_index == 0>
<#else>
<category label="${aobj[data.vo.chart.fs]}"/>
</#if>
</#list>

</categories>

<#list data.datas[0]?keys as akey>

<#if akey == data.vo.chart.fs>
<#else>
<dataset seriesName="${(data.datas[0])['${akey}']}" showValues="0">
	<#list data.datas as aobj>
	<#if aobj_index == 0>
	<#else>
	<set value="${aobj[akey]?default(0)}"/>
	</#if>
	</#list>
</dataset>
</#if>

</#list>

</chart>