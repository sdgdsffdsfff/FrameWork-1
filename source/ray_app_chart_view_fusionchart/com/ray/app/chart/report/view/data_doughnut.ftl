<chart palette='2' showBorder='1'>

<#list data.datas as aobj>
<set label='${aobj[data.vo.chart.fs]}' value='${aobj[data.vo.chartoptions[0].fy]}' />
</#list>

</chart>