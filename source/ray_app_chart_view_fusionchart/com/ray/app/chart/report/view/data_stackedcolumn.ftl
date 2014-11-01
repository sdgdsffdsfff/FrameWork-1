<chart caption='${data.vo.title}' xAxisName='${data.vo.fxcname}' yAxisName='${data.vo.fscname}' palette='1' maxColWidth='40' canvasBgColor='FFFFFF' bgColor='A0E0C0' borderColor='E0E0E0' showBorder='1' borderThickness='1' showValues='0' showLabels="0" decimals='0' formatNumberScale='0' useRoundEdges='1' rotateValues='1'>

<categories>
<#list data.datas as aobj>
<category label="${aobj[data.vo.chart.fx]}"/>
</#list>
</categories>

<#list data.vo.chartoptions as aoption>
<test value="${aoption.fy}"/>
<dataset seriesName="${aoption.fycname}" showValues="0">
	<#list data.datas as aobj>
	<set value="${aobj[aoption.fy]}"/>
	</#list>
</dataset>
</#list>
</chart>

