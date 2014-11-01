<body>
<link rel="stylesheet" href="${base}/resource/default/swf/Style.css" type="text/css" />
<script language="JavaScript" src="${base}/resource/default/swf/FusionCharts.js"></script>

<div id="div_${arg._div}"></div>
<script type="text/javascript">

	<#assign url = "${base}/chart/charthis_data.action?_chartname=${arg._chartname}">
	
	var chart = new FusionCharts("${base}/resource/default/swf/${arg._type}.swf", "ChartId", "${data.vo.chart.width}", "${data.vo.chart.height}", "0", "0");
	chart.setTransparent(true);

	<#if data.vo.chart.ctype == "Gantt">

	var url = "${base}/chart/chart_datagantt.action";
	url += "?_chartname=${arg._chartname}";
	url += "&_type=${arg._type}";	
	<#list data.items as item>
	url += "&${item.name}=${item.value}";
	</#list>
	url += "&random=" + Math.random();
	chart.setDataURL(escape(url));		

	<#else>
	
	var url = "${base}/chart/charthis_data.action";
	url += "?_chartname=${arg._chartname}";
	url += "&_type=${arg._type}";
	<#list data.items as item>
	url += "&${item.name}=${item.value}";
	</#list>
	url += "&random=" + Math.random();
			
	chart.setDataURL(escape(url));		   
	</#if>
	
	chart.render("div_${arg._div}");
	
</script>

</body>