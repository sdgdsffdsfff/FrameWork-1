<chart palette='2' caption='${data.vo.chart.title}' dateFormat='yyyy/mm/dd' outputDateFormat='ddds mns' >
   <categories>
      <category start='${data.mindate}' end='${data.maxdate}' label='月份' />
   </categories>
   <categories>
	  <#list data.categories as acate>	
      <category start='${acate.start}' end='${acate.end}' label='${acate.label}' />
      </#list>
   </categories>
   
   <processes headerText='任务' headerFontSize='12' fontSize='9'>
   <#list data.datas as aobj>
	<#if aobj.sign == "P"> 	
      <process label='${aobj.content}' id='${aobj.id}' />
	</#if>
   </#list>
   </processes>
   
   <tasks showEndDate='1'>

   <#list data.datas as aobj>
	<#if aobj.sign == "P"> 	
		<task processId='${aobj.id}' start='${aobj.date1}' end='${aobj.date2}' id='${aobj.id}-1' color='4567aa' height='25%' topPadding='20%' />
	<#else>
		<task processId='${aobj.id}' start='${aobj.date1}' end='${aobj.date2}' id='${aobj.id}' color='EEEEEE' height='25%' topPadding='55%' />
    </#if>					  	
   </#list>

   </tasks>
 
   <legend>
      <item label='计划' color='4567aa' />
      <item label='执行' color='999999' />
   </legend>
   <styles>
      <definition>
         <style type='font' name='legendFont' size='13' />
      </definition>
      <application>
         <apply toObject='Legend' styles='legendFont' />
      </application>
   </styles>
</chart>

<#--

<chart palette='2' caption='Construction Timeline' dateFormat='dd/mm/yyyy' outputDateFormat='ddds mns' >
   <categories>
      <category start='1/5/2011' end='31/8/2011' label='Months' />
   </categories>
   <categories>
      <category start='1/5/2011' end='31/5/2011' label='May' />
      <category start='1/6/2011' end='30/6/2011' label='June' />
      <category start='1/7/2011' end='31/7/2011' label='July' />
      <category start='1/8/2011' end='31/8/2011' label='August' />
   </categories>
   <processes headerText='Task' headerFontSize='16' fontSize='12'>
      <process label='Terrace' id='TRC' />
      <process label='Inspection' id='INS' />
      <process label='Wood Work' id='WDW' />
      <process label='Interiors' id='INT' />
   </processes>
   
   <tasks showEndDate='1'>
      <task processId='TRC' start='5/5/2011' end='2/6/2011' id='5-1' color='4567aa' height='25%' topPadding='20%' />
      <task processId='TRC' start='10/5/2011' end='2/6/2011' id='5' color='EEEEEE' height='25%' topPadding='55%'/>
      <task processId='INS' start='11/5/2011' end='12/6/2011' id='6-1' color='4567aa' height='25%' topPadding='20%' />
      <task processId='INS' start='13/5/2011' end='19/6/2011' id='6' color='EEEEEE' height='25%' topPadding='55%'/>
      <task processId='WDW' start='1/6/2011' end='12/7/2011' id='7-1' color='4567aa' height='25%' topPadding='20%' />
      <task processId='WDW' start='2/6/2011' end='19/7/2011' id='7' color='EEEEEE' height='25%' topPadding='55%'/>
      <task processId='INT' start='1/6/2011' end='12/8/2011' id='8-1' color='4567aa' height='25%' topPadding='20%' />
      <task processId='INT' start='1/6/2011' end='19/8/2011' Id='8' color='EEEEEE' height='25%' topPadding='55%' /> 
   </tasks>
   <legend>
      <item label='Planned' color='4567aa' />
      <item label='Actual' color='999999' />
   </legend>
   <styles>
      <definition>
         <style type='font' name='legendFont' size='13' />
      </definition>
      <application>
         <apply toObject='Legend' styles='legendFont' />
      </application>
   </styles>
</chart>

-->
