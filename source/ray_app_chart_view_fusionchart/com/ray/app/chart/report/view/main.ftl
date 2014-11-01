<script language="JavaScript" src="${base}/resource/default/swf/FusionCharts.js"></script>

<div id="div_${arg._div}"></div>
<script type="text/javascript">

	<#assign url = "${base}/chart/chart_data.action?_chartname=${arg._chartname}">
	
	
	var chart = new FusionCharts("${base}/resource/default/swf/${arg._type}.swf?ChartNoDataText=没有数据！", "ChartId", "100%", "100%", "0", "0");
	chart.setTransparent(true);
	//chart.addParam('WMode', 'Opaque');

	<#if data.vo.chart.ctype == "Gantt">

	var url = "${base}/chart/chart_datagantt.action";
	url += "?_chartname=${arg._chartname}";
	<#list data.items as item>
	url += "&${item.name}=${item.value}";
	</#list>
	url += "&random=" + Math.random();
	chart.setDataURL(escape(url));		

	<#else>
	
	var url = "${base}/chart/chart_data.action";
	url += "?_chartname=${arg._chartname}";
	<#list data.items as item>
	url += "&${item.name}=${item.value}";
	</#list>
	url += "&random=" + Math.random();
			
	chart.setDataURL(escape(url));		   
	</#if>
	
	chart.render("div_${arg._div}");
	
</script>