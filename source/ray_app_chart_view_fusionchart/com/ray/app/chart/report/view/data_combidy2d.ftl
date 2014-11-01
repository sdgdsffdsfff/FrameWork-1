<chart palette="2" caption="${data.vo.title}" subCaption="March 2006" showValues="0" divLineDecimalPrecision="1" limitsDecimalPrecision="1" 
PYAxisName="${data.vo.chart.fycname}" SYAxisName="${data.vo.chart.sfycname}" numberPrefix="$" formatNumberScale="0">

<categories>
<#list data.datas as aobj>
<category label="${aobj[data.vo.chart.fs]}"/>
</#list>
</categories>

<#list data.vo.chartoptions as aoption>
<dataset seriesName="${aoption.fycname}" renderAs="${aoption.renderas}" parentYAxis="${aoption.parentyaxis}">
	<#list data.datas as aobj>
	<set value="${aobj[aoption.fy]}"/>
	</#list>
</dataset>
</#list>
