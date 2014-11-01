<chart caption='${data.vo.title}' shownames="1" decimals="2" baseFontSize="10" baseFontColor="666666" showCanvasBg="0" showCanvasBase="1" canvasBaseDepth="5" canvasBgDepth="5" divLineColor="eeeeee" legendBgColor="ffffff" legendBorderThickness="0" legendShadow="0" legendBorderColor="ffffff" >

<categories>
<#list data.datas as aobj>
<category label="${aobj[data.vo.chart.fs]}"/>
</#list>
</categories>

<#list data.vo.chartoptions as aoption>
<dataset seriesName="${aoption.fycname}" showValues="1">
	<#list data.datas as aobj>
	<set value="${aobj[aoption.fy]}"/>
	</#list>
</dataset>
</#list>

<styles>
	 <definition>
	<style name='myGlow' type='font' size='12'/>
	 </definition>
	 <application>
	     <apply toObject='DataLabels' styles='myGlow' />
	      <apply toObject='legend' styles='myGlow' />
	 </application>
</styles>

</chart>