<#--  

日期：2011/03/16
编辑：蒲剑
说明：IT运维界面模板。 
状态：

-->

<#macro displayurl vo>
  	<#list vo.searchurls as url>
  	<#if url.visible == "1">
  	<button id="bt_${url.pname}" class="btn2"><span class="${url.pname}">${url.title}</span></button>
  	</#if>
  	</#list>
</#macro>

﻿<#macro displaysearchitem vo arg apage>
<form id="form_quicksearch" method="post" action="${base}${vo.formaction}">

<input type="hidden" name="_searchname" value="${_searchname}">
<input type="hidden" name="page" value="${apage.currentpage}">
<input type="hidden" name="_sortfield" value="${apage.sortfield}" />
<input type="hidden" name="_sorttag" value="${apage.sorttag}" />
<input type="hidden" name="step" value="${apage.pagesize}" />

<input type="hidden" name="_valuefield" value="${arg.valuefield}" />
<input type="hidden" name="_returnfield" value="${arg.returnfield}" />


<#list vo.searchitems as searchitem>
	<#if searchitem.istop == "1">
	<input class="text ani2width" name="${searchitem.htmlfield}" placeholder="按${searchitem.caption}搜索" />
	</#if>
</#list>
<a href="javascript:void(0);" id="sbtn">搜索</a>
<a href="javascript:void(0);" class="showAdvSearch">高级</a> 
<div class="adv">
<div id="clearSearchStr"><span class="t">清除搜索</span></div>
	<#list vo.searchitems as searchitem>
		<#if searchitem.edittype == "hidden">
			<input cname="${searchitem.caption}" name="${searchitem.htmlfield}" type="hidden" value="${arg[searchitem.htmlfield]}"> 
		</#if>
	</#list>
	<table class="formGrid">
		<#assign vnum = 0>
		<#list vo.searchitems as searchitem>
			<#if searchitem.istop != "1">
			<#assign value = "arg.${searchitem.htmlfield}"?eval>
				<#if searchitem.edittype != "hidden">
				<#assign vnum = vnum + 1>
					<#if (vnum % 2) == 1>
					<tr>
					</#if>
					<td class="r">&nbsp;${searchitem.caption}：</td>
					<td>&nbsp;<@displayaitem option = searchitem value = "${value}" /></td>
				</#if>
			</#if>
		</#list>
	</table>
</div>
</form>
</#macro>


﻿<#macro displaypage vo arg>

<#list vo.searchitems as searchitem>
	<#-- <#if searchitem.edittype == "hidden">
	<input cname="${searchitem.caption}" name="${searchitem.htmlfield}" type="hidden" value="${arg[searchitem.field]}"> 
	</#if>-->
	<input cname="${searchitem.caption}" name="${searchitem.htmlfield}" type="hidden" value="${arg[searchitem.htmlfield]}">
</#list>
</#macro>

<#macro displayaitem option value>

<@compress>

	<#if option.edittype == "text">
	<input type="text" class="text" id="${option.htmlfield}" name="${option.htmlfield}" size="35"  dataType="${option.required}" msg="【${option.title}】未输入或格式不符合要求。" value="<@displayfieldvalue option = option value = encode_html_value(value) />" <#if option.readonly == "Y">readonly</#if>>
	<#elseif option.edittype == "select">
	<@displayafield_select option = option value = value />
	<#elseif option.edittype == "textarea">
	<textarea id="${option.htmlfield}" name="${option.htmlfield}" rows="<#if option.trows==0>5<#else>${option.trows}</#if>" cols="<#if option.tcols==0>120<#else>${option.tcols}</#if>">${value}</textarea>
	<#elseif option.edittype == "label">
	<@displayfieldvalue option = option value = value />
	<#elseif option.edittype == "selectinput">
	<@displayafield_selectinput option = option value = value />
	<#elseif option.edittype == "ajaxinput">
	<input class="ajax" type="text" id="${option.htmlfield}" name="${option.htmlfield}" size="35"  dataType="${option.required}" msg="【${option.title}】未输入或格式不符合要求。" value="<@displayfieldvalue option = option value = encode_html_value(value) />" <#if option.readonly == "Y">readonly</#if>>
	<#elseif option.edittype == "dateinput">
	<input class="text date" type="text" id="${option.htmlfield}" name="${option.htmlfield}" size="35"  dataType="${option.required}" msg="【${option.title}】未输入或格式不符合要求。" value="<@displayfieldvalue option = option value = encode_html_value(value) />" <#if option.readonly == "Y">readonly</#if>>
	<#elseif option.edittype == "reqopenwin">
	<input class="text required openwin" id="${option.htmlfield}" name="${option.htmlfield}" size="35"  dataType="${option.required}" msg="【${option.title}】未输入或格式不符合要求。" value="<@displayfieldvalue option = option value = encode_html_value(value) />" <#if option.readonly == "Y">readonly</#if>>
	</#if>


	<#-- 配置管理使用 -->
	<#if Session["SPRING_SECURITY_CONTEXT"]??>
	<#if Session["SPRING_SECURITY_CONTEXT"].authentication.principal.username=="admin">
	<a href="javascript:void(0)" onclick="openwin('${base}/module/app/system/query/search/searchitem_locate.action?_searchname=searchitem.locate&searchitemid=${option.searchitemid}',pub_width_small, pub_height_small)">定义</a>	
	</#if>
	</#if>
</@compress>

</#macro>


<#macro displaylisttable apage vo arg>
<#assign listtableid = "listtable">
<#if vo.listtableid != "">
<#assign listtableid = vo.listtableid>
</#if>
<table id="${listtableid}" width="100%" border="0" cellpadding="0" cellspacing="1">
<@displayheader vo = vo arg = arg></@displayheader>
<@displaylist apage = apage vo = vo></@displaylist>
</table>
</#macro>

<#macro displayheader vo arg>

<#assign sortfield = arg._sortfield>
<#assign sorttag = arg._sorttag>
<#assign flag = "true">

<thead>
<tr>
<#if vo.ischeck == "Y">
<th width="1%" nowrap><nobr></nobr></th>
</#if>

<#if vo.isno == "Y">
<th align="center">&nbsp;序号&nbsp;</th>
</#if>

<#list vo.searchoptions as option>
	<#if option.visible == "1">
	<#if option.field==sortfield>
	<th align="center" width="${option.displaywidth}%" nowrap>${option.title}<span id="turntime" style="font-family:Webdings; font-size:9px; font-weight: normal">${sorttag}</span>
	<#--
	<#-- 配置管理使用 -->
	<#if Session["SPRING_SECURITY_CONTEXT"]??>
	<#if Session["SPRING_SECURITY_CONTEXT"].authentication.principal.username=="admin">
	<a href="javascript:void(0)" onclick="openwin('${base}/module/app/system/query/search/searchoption_locate.action?_searchname=searchoption.locate&searchoptionid=${option.searchoptionid}',pub_width_mid, pub_height_mid)">定义</a>	
	</#if>
	</#if>
	-->
	</th>
	<#else>
	<th align="center" width="${option.displaywidth}%">${option.title}</th>
	</#if>
	</#if>
</#list>
	
</tr>
</thead>

</#macro>

<#macro displaylist apage vo>
<#assign ischecks = "true">
<#assign flag = "true">

<#list apage.result as hs>

	<#assign keyvalues = "">
	<#assign keynames = "">

	<#assign attrvalues = "">
	<#assign attrnames = "">
	
	<#list vo.searchoptions as searchoption>
	
		<#if searchoption.pkey == "1">
			<#if keynames !="">
			<#assign keynames = keynames + ",">
			</#if>
			<#assign keynames = keynames + searchoption.field>
			
			<#if keyvalues !="">
			<#assign keyvalues = keyvalues + ",">
			</#if>
			<#assign keyvalues = keyvalues + hs[searchoption.field]>
		</#if>
		
		<#if searchoption.trattr == "Y">
			<#if attrnames !="">
			<#assign attrnames = attrnames + ",">
			</#if>
			<#assign attrnames = attrnames + searchoption.field>
			
			<#if attrvalues !="">
			<#assign attrvalues = attrvalues + ",">
			</#if>
			<#assign attrvalues = attrvalues + hs[searchoption.field]>
		</#if>
		
	</#list>
	
	<tbody>
	<tr id="tr_${hs_index + 1}" <#if attrnames!="">${attrnames}="${attrvalues}"</#if> ondblclick="page_view_row_dbclick('${keynames}', '${keyvalues}', ${hs_index + 1})" onclick="page_view_row_click('${keyvalues}',${hs_index + 1})">
	<#if vo.ischeck == "Y">

	<#-- 复选框改为CSS实现，修改原复选框控制 -->
	<td nowrap class="checkboxIn">

	<input class="checkbox" id="sindex_${hs_index +1}" name="sindex" value="0">
	<input type="hidden" id="index_${hs_index + 1}" name="index" value="${keyvalues}">
	
	</td>
	</#if>
	<input type="hidden" onclick="" id="vindex_${hs_index + 1}" name="vindex" class="myinput" value="${keyvalues}">
	<#assign aflag = "left">
	<#if vo.isno == "Y">
	<td width="1%" nowrap  style= "cursor:hand ">${((apage.currentpage-1)*apage.pagesize) + (hs_index + 1)}</td>
	</#if>
	
	<#list vo.searchoptions as option>
	<#-- <#assign value = hs[option.field]> -->
	
	<#-- 关键功能升级替换，实现多级属性 -->
	<#assign value = "hs.${option.field}"?eval>
	<#escape value as value!""></#escape>
	
	<@displayfield option = option value = value />

	</#list>
	</tr>
	</tbody>
</#list>

<#-- 输出合计列 -->
<@displaysums vo = vo apage = apage />

</#macro>


<#-- 用于通用弹窗选择页面 -->
<#macro displaylistselect apage vo arg>
<#assign ischecks = "true">
<#assign flag = "true">

<tbody>
<#list apage.result as hs>

	<#assign keyvalues = "">
	<#assign keynames = arg.valuefield>

	<#assign attrvalues = "">
	<#assign attrnames = "">
	<#assign num = 0>
	<#list keynames?split(",") as keyname>
		<#if num!=0>
		<#assign keyvalues = keyvalues + ",">
		</#if>
		<#assign keyvalues = keyvalues + hs[keyname]>
		<#assign num = num + 1>
	</#list>
		
	<#-- <tr id="tr_${hs_index + 1}" ondblclick="page_view_row_dbclick('${keynames}', '${keyvalues}', ${hs_index + 1})" onclick="page_view_row_click('${keyvalues}',${hs_index + 1})"> -->
	
	<tr id="tr_${hs_index + 1}">
	
	<#if vo.ischeck == "Y">

	<#-- 复选框改为CSS实现，修改原复选框控制 -->
	<td nowrap>

	<input class="checkbox" id="sindex_${hs_index +1}" name="sindex" value="0" data-id="${keyvalues}">
	<input type="hidden" id="index_${hs_index + 1}" name="index" value="${keyvalues}">
	
	</td>
	</#if>
	<input type="hidden" onclick="" id="vindex_${hs_index + 1}" name="vindex" class="myinput" value="${keyvalues}">
	<#assign aflag = "left">
	<#if vo.isno == "Y">
	<td width="1%" nowrap  style="cursor:hand">${((apage.currentpage-1)*apage.pagesize) + (hs_index + 1)}</td>
	</#if>
	
	<#list vo.searchoptions as option>
	<#-- <#assign value = hs[option.field]> -->
	
	<#-- 关键功能升级替换，实现多级属性 -->
	<#assign value = "hs.${option.field}"?eval>
	<#escape value as value!""></#escape>
	
	<@displayfield option = option value = value />

	</#list>
	</tr>
</#list>
</tbody>
	
<#-- 输出合计列 -->
<@displaysums vo = vo apage = apage />

</#macro>

<#macro displaysums vo, apage>

	<#assign hassum = "N">
	<#list vo.searchoptions as option>
		<#if option.issum == "Y">
		<#assign hassum = "Y">
		<#break>
		</#if>
	</#list>

	<#if hassum == "Y">
	<tr bgcolor="F4F4F4" class="hand">
	<#if vo.ischeck == "Y">
	<td>&nbsp;</td>
	</#if>
	<#if vo.isno == "Y">
	<td>&nbsp;</td>
	</#if>
	
	<#list vo.searchoptions as option>
	<#assign asum = 0>
	<#if option.visible == "1">
	<#if option.issum == "Y">
		<#list apage.list as hs>
		<#if hs[option.field] != "">
			<#assign asum = asum + hs[option.field]?number>
		</#if>
		</#list>
	<td align="right">${asum?string(option.displayformat)}</td>
	<#else>
	<td>&nbsp;</td>
	</#if>
	</#if>
	</#list>
	</tr>
	</#if>
	
</#macro>

<#macro displaysimplelist apage vo>

<#list apage.list as hs>
	<tr>
	<#if vo.ischeck != "N">
	<td nowrap>
	<input type="checkbox" onclick="" id="" name="index" class="" value="${keyvalues}">
	</td>
	</#if>

	<#assign aflag = "left">
	
	<#if vo.isno != "N">
	<td width="1%" nowrap>${((apage.currentpage-1)*apage.pagesize) + (hs_index + 1)}</td>
	</#if>
	
	<#list vo.searchoptions as option>
	<@displayfield option = option value = hs[option.field] />
	
	</#list>
	</tr>

</#list>

</#macro>


<#macro displaynavigation apage vo arg>

<#assign action = "">
<#assign flag = "true">
<#assign searchname = _searchname>
<#assign pagesize = apage._pagesize>
<#assign sorttag = apage._sorttag>
<#assign sortfield = apage._sortfield>

<#if apage.maxpage &gt; 1>
	<#if apage.size &gt; 0>
		<#if apage.haspre?string == "true">
		<td width="30"><a class="page" href="javascript:pub_query_selectpage(1);pub_query_go2page('${base}${vo.formaction}')"><em>首页</em></a></td>
		<td width="30"><a class="page" href="javascript:pub_query_selectpage(${apage.currentpage-1});pub_query_go2page('${base}${vo.formaction}')"><em>上页</em></a></td>
		<#else>
		<td width="30"><a class="page" href="javascript:pub_void()"><em>首页</em></a></td>
		<td width="30"><a class="page" href="javascript:pub_void()"><em>上页</em></a></td>
		</#if>
		<#if apage.hasnext?string == "true">
		<td width="30"><a class="page" href="javascript:pub_query_selectpage(${apage.currentpage+1});pub_query_go2page('${base}${vo.formaction}')"><em>下页</em></a></td>
		<td width="30"><a class="page" href="javascript:pub_query_selectpage(${apage.maxpage});pub_query_go2page('${base}${vo.formaction}')"><em>末页</em></a></td>
		<#else>
		<td width="30"><a class="page" href="javascript:pub_void()">下页</a></td>
		<td width="30"><a class="page" href="javascript:pub_void()">末页</a></td>
		</#if>
	</#if>
	<td width="50">跳转至第</td>
	<td width="30"><input id="jumppageindex" name="jumppageindex" size="${apage.maxpage?length}" value="${apage.currentpage}"></td>
	<td width="30">页</td>
	<td width="150" align="left">共${apage.maxpage}页/${apage.rowcount}条记录</td>
	<td>&nbsp;</td>
</#if>	
	
</#macro>

<#-- 显示格式显示字段内容 -->
<#macro displayfield option value>

<@compress>

	<#assign aflag = "left">
	
	<#if option.visible == "1">
		<#if option.fieldtype == "number" || option.fieldtype == "money" || option.fieldtype == "integer">
		<#assign aflag = "right">
		</#if>
	
		<#if option.displaywidth == "">
		<td nowrap align="${aflag}" style= "cursor:hand ">	
		<#else>
		<td nowrap align="${aflag}" width="" style= "cursor:hand ">&nbsp;
		</#if>

		<#if option.edittype == "label">
		<@displayfieldvalue option = option value = value />
		<#elseif option.edittype == "text">
		<input type="text" class="text" name="${option.field}" id="${option.field}" size="35" value="<@displayfieldvalue option = option value = value />"   style="width:90%;border-width:.09em;border-style:solid;margin:1px 3px 1px 1px!important;border: 0;border-bottom:1px solid #7F9DB9;">
		<#elseif option.edittype == "checkbox">
		<input type="checkbox" name="${option.field}" id="${option.field}" value="${value}" <#if value==option.param1.split(",")[0]>checked</#if> />	
		<#elseif option.edittype == "select">
		<@displayafield_select option = option value = value />
		<#elseif option.edittype == "selectinput">
		<@displayafield_selectinput option = option value = value />
		<#elseif option.edittype == "reqopenwin">
		<input class="text required openwin" type="text" id="${option.htmlfield}" name="${option.htmlfield}" size="35"  dataType="${option.required}" msg="【${option.title}】未输入或格式不符合要求。" value="<@displayfieldvalue option = option value = encode_html_value(value) />" <#if option.readonly == "Y">readonly</#if>>
		</#if>
	
	</td>
	</#if>

</@compress>

</#macro>

<#macro displayatitle option field>
<#if option.required == "Y"></#if><label id="lb_${option.field}">${option.title}<#if option.edittype!="checkbox">：</#if></label>
</#macro>

<#--  

日期：2011/02/25
编辑：蒲剑
任务：重要升级，支持字段与页面字段名不同 
状态：测试完成


日期：2011/03/23
编辑：蒲剑
任务：修改，SELECT类型改为INPUT类型实现。 
状态：

-->
<#macro displayafield option value>

<@compress>

	<#if option.visible == "1">
		<#if option.edittype == "text">
		<input type="text" class="text <#if option.required=='Y'>required</#if>" id="${option.htmlfield}" name="${option.htmlfield}" size="<#if option.displaywidth??>${option.displaywidth}<#else>35</#if>" value="<@displayfieldvalue option = option value = encode_html_value(value) />" <#if option.readonly == "Y">readonly</#if>>
		<#elseif option.edittype == "password">
		<input type="password" class="text <#if option.required=='Y'>required</#if>" id="${option.htmlfield}" name="${option.htmlfield}" size="<#if option.displaywidth??>${option.displaywidth}<#else>35</#if>" value="">
		<#elseif option.edittype == "select">
		<@displayafield_select option = option value = value />
		<#elseif option.edittype == "textarea">
		<textarea id="${option.htmlfield}" class="text <#if option.required=='Y'>required</#if>" name="${option.htmlfield}" rows="<#if option.trows==0>5<#else>${option.trows}</#if>" cols="<#if option.tcols==0>120<#else>${option.tcols}</#if>" <#if option.readonly == "Y">readonly</#if>>${value}</textarea>
		<#elseif option.edittype == "label">
		<@displayfieldvalue option = option value = value />
		<#elseif option.edittype == "selectinput">
		<@displayafield_selectinput option = option value = value />
		<#elseif option.edittype == "select2input">
		<@displayafield_select2input option = option value = value />
		<#elseif option.edittype == "ajaxinput">
		<input class="ajax <#if option.required=='Y'>required</#if>" type="text" id="${option.htmlfield}" name="${option.htmlfield}" size="35" value="<@displayfieldvalue option = option value = encode_html_value(value) />" <#if option.readonly == "Y">readonly</#if>>
		<#elseif option.edittype == "dateinput">
		<input class="text <#if option.required=='Y'>required</#if> date" id="${option.htmlfield}" name="${option.htmlfield}" size="35" value="<@displayfieldvalue option = option value = encode_html_value(value) />" <#if option.readonly == "Y">readonly</#if>>
		<#elseif option.edittype == "reqopenwin">
		<input class="text <#if option.required=='Y'>required</#if> openwin" id="${option.htmlfield}" name="${option.htmlfield}" size="35" value="<@displayfieldvalue option = option value = encode_html_value(value) />" <#if option.readonly == "Y">readonly</#if>>
		<#elseif option.edittype == "emergency">
		<input name="${option.htmlfield}"  id="${option.htmlfield}" class="emergency <#if option.required=='Y'>required</#if>" value="<@displayfieldvalue option = option value = encode_html_value(value) />" <#if option.readonly == "Y">readonly</#if>>
		<#elseif option.edittype == "priority">
		<input name="${option.htmlfield}"  id="${option.htmlfield}" class="priority <#if option.required=='Y'>required</#if>" value="<@displayfieldvalue option = option value = encode_html_value(value) />" <#if option.readonly == "Y">readonly</#if>>
		<#elseif option.edittype == "checkbox">
		<input name="${option.htmlfield}"  id="${option.htmlfield}" class="checkbox <#if option.required=='Y'>required</#if>" value="<@displayfieldvalue option = option value = encode_html_value(value) />" <#if option.readonly == "Y">readonly</#if>>
		<#elseif option.edittype == "timeinput">
		<input type="hidden" name="${option.htmlfield}" id="${option.htmlfield}" class="hiddendatetime" value="<@displayfieldvalue option = option value = encode_html_value(value)/>">
		<input name="${option.htmlfield}-d"  id="${option.htmlfield}-d" class="text <#if option.required=='Y'>required</#if> datetime" value="" <#if option.readonly == "Y">readonly</#if>>
		<span class="hour-minute">
		<input id="${option.htmlfield}-h" name="${option.htmlfield}-h" class="hour" value="" /><input id="${option.htmlfield}-m" name="${option.htmlfield}-m" class="minute" value="" />
		</span>		
		</#if>
	</#if>

	<#-- 配置管理使用 -->
	<#if Session["SPRING_SECURITY_CONTEXT"]??>
	<#if Session["SPRING_SECURITY_CONTEXT"].authentication.principal.username=="admin">
	<a href="javascript:void(0)" onclick="openwin('${base}/module/app/system/query/search/searchoption_locate.action?_searchname=searchoption.locate&searchoptionid=${option.searchoptionid}',pub_width_mid, pub_height_mid)">定义</a>	
	</#if>
	</#if>
</@compress>

</#macro>

<#macro displayafield_select option value>
	<select class="text" id="${option.htmlfield}" name="${option.htmlfield}" dataType="${option.required}" msg="【${option.title}】未输入或格式不符合要求。">
		<option value="">&nbsp;&nbsp;</opiton>

		<#if option.dstype == "sql">
		<#assign options = stack.findValue("@com.ray.app.query.function.view.SQLSelect@get_data(\"${option.param1}\")") >
					
		<#list options as aoption>
		<option value="${aoption.cvalue}" <#if value == aoption.cvalue>selected</#if>>${aoption.ctext}</option>
		</#list>
			
		<#else>
		<#assign onames = option.param1.split(",")>
		<#assign ovalues = option.param2.split(",")>
		<#list onames as oname>
		<option value = "${ovalues[oname_index]}" <#if value == ovalues[oname_index]>selected</#if>>${oname}</option>
		</#list>
		</#if>
	</select>
</#macro>

<#-- 文本框实现下拉列表框控件 
	 日期： 20110506
	 编辑：闫长永
	 任务：修改select遮盖问题，实现select显示值和实际值不一致功能
	 
-->
<#macro displayafield_selectinput option value>

			<#assign finalvalue = "">
			<#assign finaltext = "">
			<#assign selectnum = "">
			<#if option.dstype == "sql">
				<#assign options = stack.findValue("@com.ray.app.query.function.view.SQLSelect@get_data(\"${option.param1}\")") >
					<#if options?size &lt;= 0>
						<input id="${option.htmlfield}" name="${option.htmlfield}" class="text" readonly>
					<#else>
						<#list options as aoption>
						<#assign finalvalue = finalvalue + "|" + aoption.cvalue>
						<#assign finaltext = finaltext + "|" + aoption.ctext>
						<#if value == aoption.cvalue>
							<#assign selectnum = aoption_index + 1>
						</#if>	
						</#list>
						<input class="select <#if option.required=='Y'>required</#if>" id="${option.htmlfield}" name="${option.htmlfield}" data-options="<#if finaltext!="">${finaltext}</#if>" data-values="<#if finalvalue!="">${finalvalue}</#if>" data-selected="${selectnum}" value="${value}">
					</#if>
			<#else>
				<#assign onames = option.param1.split(",")>
				<#assign ovalues = option.param2.split(",")>
				<#list onames as oname>
					<#assign finalvalue = finalvalue + "|" + ovalues[oname_index]> 
					<#assign finaltext = finaltext + "|" + onames[oname_index]>
					<#if value == ovalues[oname_index]>
						<#assign selectnum = oname_index>
					</#if>
				</#list>
				<input class="select <#if option.required=='Y'>required</#if>" id="${option.htmlfield}" name="${option.htmlfield}" data-options="<#if finaltext!="">${finaltext}</#if>" data-values="<#if finalvalue!="">${finalvalue}</#if>" data-selected="${selectnum}" value="${value}">							
			</#if>
			
</#macro>

<#-- 
	下拉框可以手动输入
-->
<#macro displayafield_select2input option value>

			<#assign finalvalue = "">
			<#assign finaltext = "">
			<#assign selectnum = "">
			<#if option.dstype == "sql">
				<#assign options = stack.findValue("@com.ray.app.query.function.view.SQLSelect@get_data(\"${option.param1}\")") >
					<#if options?size &lt;= 0>
						<input id="${option.htmlfield}" name="${option.htmlfield}" class="text" readonly>
					<#else>
						<#list options as aoption>
						<#assign finalvalue = finalvalue + "|" + aoption.cvalue>
						<#assign finaltext = finaltext + "|" + aoption.ctext>
						<#if value == aoption.cvalue>
							<#assign selectnum = aoption_index + 1>
						</#if>	
						</#list>
						<input class="select2 <#if option.required=='Y'>required</#if>" id="${option.htmlfield}" name="${option.htmlfield}" data-options="<#if finaltext!="">${finaltext}</#if>" data-values="<#if finalvalue!="">${finalvalue}</#if>" data-selected="${selectnum}" value="${value}">
					</#if>
			<#else>
				<#assign onames = option.param1.split(",")>
				<#assign ovalues = option.param2.split(",")>
				<#list onames as oname>
					<#assign finalvalue = finalvalue + "|" + ovalues[oname_index]> 
					<#assign finaltext = finaltext + "|" + onames[oname_index]>
					<#if value == ovalues[oname_index]>
						<#assign selectnum = oname_index>
					</#if>
				</#list>
				<input class="select2" id="${option.htmlfield}" name="${option.htmlfield}" data-options="<#if finaltext!="">${finaltext}</#if>" data-values="<#if finalvalue!="">${finalvalue}</#if>" data-selected="${selectnum}" value="${value}">							
			</#if>
			
</#macro>


<#macro displayfieldvalue option value>
	<@compress>
	<#if option.url != "">
	<a href="javascript:void(0)" onclick="openwin(encodeURI('${base}/${option.url}&${option.linkfield}=${value}'))">
	</#if>
	<#if value!= "">
	<#if option.fieldtype == "date" || option.fieldtype == "datetime">
		<#if option.displayformat != "">
			${(value)?datetime?string(option.displayformat)}
		<#else>
			${value}
		</#if>
	<#elseif option.fieldtype == "money" || option.fieldtype == "integer" || option.fieldtype == "number">
		<#if option.displayformat != "">
			${(value)?number?string(option.displayformat)}
		<#else>
			${value}
		</#if>
	<#--闫长永添加长度控制20110905-->
	<#elseif option.edittype == "label">
		<#if value?length gt 26>
			${value?substring(0,25)}...
		<#else>
			${value}
		</#if>
	<#else>
		<#if option.displayformat != "">
			${(value)?string(option.displayformat)}
		<#else>
			${value}
		</#if>	
	</#if>
	</#if>		
	
	<#if option.url?exists && option.url != "">
	</a>
	</#if>
	</@compress>
</#macro>


<#-- 根据字段名称获取该列数据合计-->
<#function get_sum datas field>
<#assign sum = 0>
<#list datas as hs>
<#assign sum = sum + hs[field]?number>
</#list>
<#return sum>
</#function>

<#-- 根据字段名称获取该列数据格式-->
<#function get_sum_format options field>
<#assign format = "">
<#list options as hs>
<#if hs["field"] == field>
<#assign format = hs["displayformat"]>
</#if>
</#list>
<#return format>
</#function>

<#function output_sum datas, options field>
<#assign format = get_sum_format(options, field)>
<#assign sum = get_sum(datas, field)>
<#return sum?string(format)>
</#function>

<#-- 根据字段名称获取视图定义信息-->
<#function get_field_option options field>
<#list options as hs>
<#if hs["field"] == field>
<#return hs>
</#if>
</#list>
</#function>

<#function encode_html_value value>
<#assign nvalue = value>
<#assign nvalue = nvalue?replace("<", "&lt;")>
<#assign nvalue = nvalue?replace(">", "&gt;")>
<#return nvalue>
</#function>


<#-- 报表链接 -->
<#macro displayreportlink vo arg>
<#list vo.searchlinks as searchlink>
<a href="javascript:void(0)" onclick="page_link('${searchlink.url}?_searchname=${searchlink.linksearchname}')">${searchlink.linkname}</a>
</#list>
</#macro>


<#-- 报表表头 -->
<#macro displayreportheader vo arg>

<#assign sortfield = arg._sortfield>
<#assign sorttag = arg._sorttag>
<#assign flag = "true">

<thead class="titletr">
<tr>
<#list vo.searchoptions as option>
<th align="center" width="${option.displaywidth}%" nowrap ondblclick="page_sort('${base}/${vo.formaction}', '${sorttag}','${option.field}')">${option.title}</th>
</#list>
	
</tr>
</thead>

</#macro>

<#-- 报表表体 -->
<#macro displayreportlist apage vo>

<#list apage.list as hs>
	<tr>
	<#list vo.searchoptions as option>
	<@displayfield option = option value = hs[option.field] />
	</#list>
	</tr>
</#list>

</#macro>