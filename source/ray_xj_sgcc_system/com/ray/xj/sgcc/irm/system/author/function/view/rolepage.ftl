<form method="post" name="form_view" id="form_view" action="">
<input type="hidden" name="_searchname" value="${_searchname}">
<input type="hidden" name="_currentpage" value="${_currentpage}">
<input type="hidden" name="_sortfield" value="${_sortfield}" />
<input type="hidden" name="_sorttag" value="${_sorttag}" />
<input type="hidden" name="_pagesize" value="${_pagesize}" />
	<div class="oTable">
		<!--gGrid start-->
		<table class="gGrid">
			<input type="hidden" id="id" name="id" value="${id}">
			<tr>
				<th width="1%" nowrap><nobr></nobr></th>
				<th align="center" width="18%" nowrap ondblclick="page_sort()">中文名称<span id="turntime" style="font-size:9px; font-weight:normal"></span></th>
				<th align="center" width="18%" nowrap ondblclick="page_sort()">英文名称<span id="turntime" style="font-size:9px; font-weight:normal"></span></th>
			</tr>
	
			<#list apage.result as role>
				<tr>
					<td>
					<#assign aclass = "">
					<#assign avalue = "0">	
					<#list data.lists as rolefunction>
						<#if rolefunction.rname == role.name>
							<#assign aclass = " checkboxChecked">
							<#assign avalue = "1">
						</#if>
					</#list>	
					
					<input class="checkbox ${aclass}" name="index" value="${avalue}">
					<input type="hidden" name="role" value="${role.id}">
					</td>
					<td>${role.cname}</td> 
					<td>${role.name}</td>
				</tr>
			</#list>
		</table>
	</div>
	
<table class="formtable" width="100%" height="24" align="left">
	<tr>
	<#if apage.size &gt; 0>
		<#if apage.haspre?string == "true">
		<td width="30"><a href="javascript:pub_query_selectpage(1);pub_query_go2page('${base}${vo.formaction}')">首页</a></td>
		<td width="30"><a href="javascript:pub_query_selectpage(${apage.currentpage-1});pub_query_go2page('${base}${vo.formaction}')">上页</a></td>
		<#else>
		<td width="30"><a href="javascript:pub_void()">首页</a></td>
		<td width="30"><a href="javascript:pub_void()">上页</a></td>
		</#if>
		<#if apage.hasnext?string == "true">
		<td width="30"><a href="javascript:pub_query_selectpage(${apage.currentpage+1});pub_query_go2page('${base}${vo.formaction}')">下页</a></td>
		<td width="30"><a href="javascript:pub_query_selectpage(${apage.maxpage});pub_query_go2page('${base}${vo.formaction}')">末页</a></td>
		<#else>
		<td width="30"><a href="javascript:pub_void()">下页</a></td>
		<td width="30"><a href="javascript:pub_void()">末页</a></td>
		</#if>
	</#if>
	<td width="50">跳转至第</td>
	<td width="30"><input id="jumppageindex" name="jumppageindex" size="${apage.maxpage?length}" value="${apage.currentpage}" onkeydown="pub_query_jump2page(jumppageindex.value, '${base}${vo.formaction}')"></td>
	<td width="30">页</td>
	<td width="150" align="left">共${apage.maxpage}页/${apage.rowcount}条记录</td>
	<td>&nbsp;</td>
	</tr>
</table>
</form>

<script type="text/javascript">
	$("#bt_save").click(function() {page_save()});
</script>

<script type="text/javascript">
	function page_save()
	{
		if(confirm("是否确实保存?"))
		{
			form_view.action = "function_saverole.action?supfnum=${arg.supfnum}";
			form_view.trigger('submit');
		}
	}
</script>