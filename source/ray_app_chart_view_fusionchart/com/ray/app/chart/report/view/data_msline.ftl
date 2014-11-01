<chart palette='2' <#--caption='${data.vo.title}' xAxisName='${data.vo.fxcname}' yAxisName='${data.vo.fscname}' --> lineThickness="1" showValues="0" formatNumberScale="0" anchorRadius="2" divLineAlpha="20" divLineColor="CC3300" divLineIsDashed="1" showAlternateHGridColor="1" alternateHGridAlpha="5" alternateHGridColor="CC3300" shadowAlpha="40" labelStep="10" numvdivlines="5" chartRightMargin="35" bgColor="FFFFFF,CC3300" bgAngle="270" bgAlpha="10,10">

<categories>
<#list data.datas as aobj>
<category label="${aobj[data.vo.chart.fx]}"/>
</#list>
</categories>

<#list data.vo.chartoptions as aoption>
<dataset seriesName="${aoption.fycname}">
	<#list data.datas as aobj>
	<set value="${aobj[aoption.fy]}"/>
	</#list>
</dataset>
</#list>
</chart>

