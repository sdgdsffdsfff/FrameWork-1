<#macro showheader repname>

<script>
jQuery(function($)
{
	$('#site-nav ul li').click(function()
	{
		if($(this).hasClass('changetype'))
		{
			$(this).parent().find('li').removeClass('current');
			$(this).addClass('current');
		}
	})
});	
</script>

<#--
<div id="site-model">
<ul>
<div class="tabline1"></div>
<div class="tab taby" onclick="page_change_model('manage')">${repname}</div>
<div class="tabline2"></div>
</ul>
</div>
-->

<h2 class="noBorder">${repname}</h2>

</#macro>

<#macro showspan chartname pname>
<#--
<span id="span_${pname}" class="ct">
<span onclick="page_load_charthis('his.${chartname}', '${pname}', 0, 'MSLine', '', '')">数据趋势&nbsp;|&nbsp;</span>
<span onclick="page_load_chartct('${chartname}', '${pname}', 0, 'MSColumn3D', '', '')">数据比对&nbsp;|&nbsp;</span>
-->
<#--
<select id="sel_${pname}" onchange="page_load_chart('${chartname}', '${pname}', 0, this.value, '', '')">
<option value="Column2D">二维直方图</option>
<option value="Column3D" selected>三维直方图</option>
<option value="Pie2D">二维饼图</option>
<option value="Pie3D">三维饼图</option>
<option value="Doughnut2D">二维环形图</option>
<option value="Doughnut3D">三维环形图</option>
<option value="Bar2D">条形图</option>
<option value="Area2D">平面图</option>
</select>
-->
</span>
</#macro>

<#macro showspan_v1 chartname pname argnames argvalues>
<span id="span_${pname}" class="ct">
<span onclick="page_load_charthis('his.${chartname}', '${pname}', 0, 'MSLine', '', '')">数据趋势&nbsp;|&nbsp;</span>
<span onclick="page_load_chartct('${chartname}', '${pname}', 0, 'MSColumn3D', '', '')">数据比对&nbsp;|&nbsp;</span>
<select id="sel_${pname}" onchange="page_load_chart('${chartname}', '${pname}', 0, this.value, '${argnames}'.split(','), '${argvalues}'.split(','))">
<option value="Column2D">二维直方图</option>
<option value="Column3D" selected>三维直方图</option>
<option value="Pie2D">二维饼图</option>
<option value="Pie3D">三维饼图</option>
<option value="Doughnut2D">二维环形图</option>
<option value="Doughnut3D">三维环形图</option>
<option value="Bar2D">条形图</option>
<option value="Area2D">平面图</option>
</select>
</span>
</#macro>