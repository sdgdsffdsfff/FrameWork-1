<chart palette='2' caption='${data.vo.title}' xAxisName='${data.vo.fxcname}' yAxisName='${data.vo.fscname}' showValues='0' decimals='0' formatNumberScale='0' useRoundEdges='1'>

<#list data.datas as aobj>
<set label='${aobj[data.vo.chart.fx]}' value='${aobj[data.vo.chartoptions[0].fy]}' />
</#list>

</chart>