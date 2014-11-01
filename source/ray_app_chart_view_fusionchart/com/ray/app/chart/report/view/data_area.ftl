<chart palette="2">

<#list data.datas as aobj>
<set label='${aobj[data.vo.chart.fx]}' value='${aobj[data.vo.chart.fs]}' />
</#list>

<styles>
		<definition>
			<style name='Anim1' type='animation' param='_xscale' start='0' duration='1' />
			<style name='Anim2' type='animation' param='_alpha' start='0' duration='1' />
			<style name='DataShadow' type='Shadow' alpha='20'/>
		</definition>
		<application>
			<apply toObject='DIVLINES' styles='Anim1' />
			<apply toObject='HGRID' styles='Anim2' />
			<apply toObject='DATALABELS' styles='DataShadow,Anim2' />
	</application>	
</styles>

</chart>