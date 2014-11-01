<chart yAxisName='${data.vo.chart.fscname}' palette='1' maxColWidth='40' bgColor='ffffff' chartTopMargin='20' chartBottomMargin='0' chartLeftMargin='0' chartRightMargin='0' baseFontSize="10" baseFontColor="666666">

<#list data.datas as aobj>
<set label='${aobj[data.vo.chart.fs]}' value='${aobj[data.vo.chartoptions[0].fy]}' />
</#list>

<styles>
	  <definition>
 	    <style name='myValuesFont' type='font' size='11' color='FFFFFF' bold='1' bgColor='666666' borderColor='666666'/>
 	 </definition>
 	 <application>
    	 <apply toObject='DataValues' styles='myValuesFont' />
  	</application>
	<definition> 
		 <style name='myGlow' type='font' size='11'/>
	 </definition>
	 <application>
	     <apply toObject='DataLabels' styles='myGlow' />
	 </application>
</styles>
   
</chart>