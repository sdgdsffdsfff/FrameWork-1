<#macro displayurl vo>
  	<#list vo.searchurls as url>
  	<#if url.visible == "1">
  	<button id="bt_${url.pname}" class="mysubmit">${url.title}</button>
  	</#if>
  	</#list>
</#macro>

<#macro displaysearchurl vo arg>
	<#--
	<input name="inputSearch" type="hidden" value="">
	<input type="text" name="inputSearchName" onkeypress="if(event.keyCode==13){document.all.btdosearch.fireEvent('onclick');}" onclick="SelClickEvent(document.all.searchSelect[document.all.searchSelect.selectedIndex].clickevent)" value="" class="mySearch">
	<select name="searchSelect" id="searchSelect" onChange="setDoSearch()">
	
	<#if vo.searchItems?exists>
		<#list vo.searchItems as searchitem>
			<#if searchitem.userTag?exists && searchitem.userTag != "hidden">
			<option clickevent="{searchitem.clickEvent}" htmlfield="{searchitem.htmlField}">按{searchitem.caption}</option>
			</#if>
		</#list>
	</#if>
	</select>
	<button onclick="page_advancesearch('${base}/query.action?action=${action}&detail=${flag}&searchname=${searchname}','')">高级搜索</button>
	-->
</#macro>

﻿<#macro displaysearchitem vo arg>
<table width="100%" class="formtable">
	<#list vo.searchitems as searchitem>
		<#if searchitem.sfield == "Y">
		<#break>
		</#if>
		<#if (searchitem_index+1) % 2 == 1>
		<tr>
		</#if>
		<#if searchitem.htmlfield?exists && searchitem.htmlfield != "">
		</#if>
		<th width="15%" align="right">&nbsp;${searchitem.caption}：</th>
		<td width="35%">&nbsp;
		
		<#if searchitem.usertag == "hidden">
		<input cname="${searchitem.caption}" type="text" id="${searchitem.htmlfield}" name="${searchitem.htmlfield}" value="${arg[searchitem.htmlfield]}" clickevent="">
		<#else>
			<#if searchitem.edittype == "dateinput">
				<input cname="${searchitem.caption}" class="dateiteminput" display="true" type="text"  id="${searchitem.htmlfield}" name="${searchitem.htmlfield}"  value="${arg[searchitem.htmlfield]}" >
			<#elseif searchitem.edittype == "select">

				<#if searchitem.dstype == "sql">
				<select cname="${searchitem.caption}" id="${searchitem.htmlfield}" name="${searchitem.htmlfield}" class="fullwidth">
				<#assign options = stack.findValue("@com.headray.app.query.function.view.SQLSelect@get_data(\"${searchitem.param1}\")") >
				<option value="">&nbsp;&nbsp;</opiton>
				<#list options as option>
				<option value="${option.cvalue}" <#if arg[searchitem.htmlfield]==option.cvalue>selected</#if>>${option.ctext}</option>
				</#list>					
				</select>
				<#else>
				<select cname="${searchitem.caption}" id="${searchitem.htmlfield}" name="${searchitem.htmlfield}" class="fullwidth">
				<option value="">&nbsp;&nbsp;</opiton>
				<#assign onames = searchitem.param1.split(",")>
				<#assign ovalues = searchitem.param2.split(",")>
				<#list onames as oname>
				<option value = "${ovalues[oname_index]}" <#if arg[searchitem.htmlfield]==ovalues[oname_index]>selected</#if>>${oname}</option>
				</#list>
				</select>
				</#if>
			<#else>
			<input cname="${searchitem.caption}" class="fullwidth" display="true" type="text" id="${searchitem.htmlfield}" name="${searchitem.htmlfield}" value="${arg[searchitem.htmlfield]}" clickevent="${searchitem.clickevent}">				
			</#if>
			<#if Session["sys_login_token"].sys_login_user?upper_case == "ADMIN">
			<a href="#" onclick="pub_openunresizablewindow('${base}/module/app/system/query/searchitem/invoke!locate.action?_searchname=searchitem.locate&searchitemid=${searchitem.searchitemid}',pub_width_mid_5v3, pub_height_mid_5v3)">定义</a>	
			</#if>
		</#if>
		</td>
		<#if ((searchitem_index+1)%2) == 1 && (searchitem_index==vo.searchitems?size-1)>
		<th width="15%">&nbsp;</th>
		<td width="35%">&nbsp;</td>
		</tr>			
		</#if> 
		
		<#if (searchitem_index+1) % 2 == 0>
		</tr>
		</#if>
	</#list>
</table>
<div id="buttondiv">
<button  class="mysubmit" id="bt_querysubmit" >确定</button>
</div>
<br>
</#macro>

<#-- 绘制流程状态字段 -->
﻿<#macro displaysfield vo arg>
<#list vo.searchitems as searchitem>
<#if searchitem.sfield == "Y">
<div id="statusdiv">
	<select id="${searchitem.field}" name="${searchitem.field}" onchange="page_query()">
	<#assign onames = searchitem.sparam1.split(",")>
	<#assign ovalues = searchitem.sparam2.split(",")>
	<#list onames as oname>
	<option value = "${ovalues[oname_index]}" <#if ovalues[oname_index]?string == arg[searchitem.htmlfield]?string>selected</#if>>&nbsp;&nbsp;${oname}&nbsp;&nbsp;</option>
	</#list>
	</select>
</div>	
</#if>
</#list>
</#macro>

<#macro displaylisttable apage vo arg>
<#assign listtableid = "listtable">
<#if vo.listtableid != "">
<#assign listtableid = vo.listtableid>
</#if>
<table id="${listtableid}" width="100%" border="0" cellpadding="0" cellspacing="1">
<@displayheader vo = vo arg = arg></@displayheader>
<@pub_macros.displaylist apage = apage vo = vo></@pub_macros.displaylist>
</table>
</#macro>

<#macro displayheader vo arg>

<#assign sortfield = arg._sortfield>
<#assign sorttag = arg._sorttag>
<#assign flag = "true">

<thead class="titletr">
<tr>
<#if vo.ischeck == "Y">
<th width="1%" nowrap><nobr><input type="checkbox" onclick="" name="indexall" id="indexall" /></nobr></th>
</#if>

<#if vo.isno == "Y">
<th align="center">&nbsp;No.&nbsp;</th>
</#if>

<#list vo.searchoptions as option>
	<#if option.visible == "1">
	<#if option.field==sortfield>
	<th align="center" width="${option.displaywidth}%" nowrap ondblclick="page_sort('${base}/${vo.formaction}', '${sorttag}','${option.field}')">${option.title}<span id="turntime" style="font-family:Webdings; font-size:9px; font-weight: normal">${sorttag}</span></th>
	<#else>
	<th align="center" width="${option.displaywidth}%" nowrap ondblclick="page_sort('${base}/${vo.formaction}','5','${option.field}')">${option.title}<span id="turntime" style="font-family:Webdings; font-size:9px; font-weight:normal"></span></th>
	</#if>
	</#if>
</#list>
	
</tr>
</thead>

</#macro>

<#macro displaylist apage vo>
<#assign ischecks = "true">
<#assign flag = "true">

<#list apage.list as hs>

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
	
	<tr id="tr_${hs_index + 1}" <#if attrnames!="">${attrnames}="${attrvalues}"</#if> bgcolor="<#if hs_index % 2 == 0>F4F4F4<#else>FDFDFD</#if>" class="hand" ondblclick="page_view_row_dbclick('${keynames}', '${keyvalues}', ${hs_index + 1})" onclick="page_view_row_click('${keyvalues}',${hs_index + 1})">
	<#if vo.ischeck == "Y">
	<td nowrap>
	<input type="checkbox" id="index_${hs_index + 1}" name="index" class="" value="${keyvalues}">
	<input type="hidden" id="sindex_${hs_index +1}" name="sindex">
	</td>
	</#if>
	<input type="hidden" onclick="" id="vindex_${hs_index + 1}" name="vindex" class="myinput" value="${keyvalues}">
	<#assign aflag = "left">
	<#if vo.isno == "Y">
	<td width="1%" nowrap>${((apage.currentpage-1)*apage.pagesize) + (hs_index + 1)}</td>
	</#if>
	
	<#list vo.searchoptions as option>
	<@displayfield option = option value = hs[option.field] />
	</#list>
	</tr>
</#list>

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
<#assign searchname = arg._searchname>
<#assign pagesize = arg._pagesize>
<#assign sorttag = arg._sorttag>
<#assign sortfield = arg._sortfield>

<#if apage.maxpage &gt; 1>
<table class="formtable" width="100%" height="24" align="left">
	<tr>
	<#if apage.size &gt; 0>
		<#if apage.startindex &gt; 0>
		<td width="30"><a href="javascript:page_selectpage(1);page_go2page('${base}${vo.formaction}')">首页</a></td>
		<td width="30"><a href="javascript:page_selectpage(${apage.currentpage-1});page_go2page('${base}${vo.formaction}')">上页</a></td>
		<#else>
		<td width="30"><a href="javascript:page_void()">首页</a></td>
		<td width="30"><a href="javascript:page_void()">上页</a></td>
		</#if>
		<#if apage.hasnext?string == "true">
		<td width="30"><a href="javascript:page_selectpage(${apage.currentpage+1});page_go2page('${base}${vo.formaction}')">下页</a></td>
		<td width="30"><a href="javascript:page_selectpage(${apage.maxpage});page_go2page('${base}${vo.formaction}')">末页</a></td>
		<#else>
		<td width="30"><a href="javascript:page_void()">下页</a></td>
		<td width="30"><a href="javascript:page_void()">末页</a></td>
		</#if>
	</#if>
	<td width="50">跳转至第</td>
	<#--<td width="30"><input id="jumppageindex" name="jumppageindex" size="${apage.maxpage?length}" value="${apage.currentpage}" onkeydown="page_selectpage(jumppageindex.value);page_go2page('${base}${vo.formaction}')"></td>-->
	<td width="30"><input id="jumppageindex" name="jumppageindex" size="${apage.maxpage?length}" value="${apage.currentpage}" onkeydown="page_jump2page(jumppageindex.value, '${base}${vo.formaction}')"></td>
	<td width="30">页</td>
	<td width="100" align="left">共${apage.rowcount}条记录</td>
	<td>&nbsp;</td>
	</tr>
</table>	
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
	<td nowrap align="${aflag}">	
	<#else>
	<td nowrap align="${aflag}" width="${option.displaywidth}">&nbsp;
	</#if>

	<#if option.edittype == "label">
	<@displayfieldvalue option = option value = value />
	<#elseif option.edittype == "text">
	<input type="text" name="${option.field}" id="${option.field}"  size="35" value="<@displayfieldvalue option = option value = value />"   style="width:90%;border-width:.09em;border-style:solid;margin:1px 3px 1px 1px!important;border: 0;border-bottom:1px solid #7F9DB9;">
	<#elseif option.edittype == "checkbox">
	<input type="checkbox" name="${option.field}" id="${option.field}" value="${value}" <#if value==option.param1.split(",")[0]>checked</#if> />	
	</#if>
	</td>
	</#if>

</@compress>

</#macro>

<#macro displayatitle option field>
<#if option.required != "X"><span class="atten">*</span></#if><label id="lb_${option.field}" for="${option.field}">${option.title}：</label>
</#macro>

<#macro displayafield option value>
<@compress>

	<#if option.visible == "1">

		<#if option.edittype == "text">
		<input type="text" id="${option.field}" name="${option.field}" size="35"  dataType="${option.required}" msg="【${option.title}】未输入或格式不符合要求。" value="<@displayfieldvalue option = option value = encode_html_value(value) />" <#if option.readonly == "Y">readonly</#if>>
		<#elseif option.edittype == "ajaxinput">
		<input class = "ajaxinput" type="text" id="${option.field}" name="${option.field}" size="35"  dataType="${option.required}" msg="【${option.title}】未输入或格式不符合要求。" value="<@displayfieldvalue option = option value = encode_html_value(value) />" <#if option.readonly == "Y">readonly</#if>>
		<#elseif option.edittype == "dateinput">
		<input class = "dateinput" type="text" id="${option.field}" name="${option.field}" size="35"  dataType="${option.required}" msg="【${option.title}】未输入或格式不符合要求。" value="<@displayfieldvalue option = option value = encode_html_value(value) />" <#if option.readonly == "Y">readonly</#if>>
	
		<#elseif option.edittype == "select">
		<select id="${option.field}" name="${option.field}" style="width:245px"  dataType="${option.required}" msg="【${option.title}】未输入或格式不符合要求。">
			<#if option.dstype == "sql">
			<option value=""></option>
			<#assign options = stack.findValue("@com.headray.app.query.function.view.SQLSelect@get_data(\"${option.param1}\")") >
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
		<#elseif option.edittype == "label">
		<@displayfieldvalue option = option value = value />
		</#if>
		<#if Session["sys_login_token"].sys_login_user?upper_case == "ADMIN">
		<a href="#" onclick="pub_openunresizablewindow('${base}/module/app/system/query/searchoption/invoke!locate.action?_searchname=searchoption.locate&searchoptionid=${option.searchoptionid}',pub_width_mid_5v3, pub_height_mid_5v3)">定义</a>	
		</#if>
	</#if>
</@compress>

</#macro>


<#macro displayfieldvalue option value>
	<@compress>
	<#if option.url != "">
	<a href="#" onclick="pub_openunresizablewindow(encodeURI('${base}/${option.url}&${option.linkfield}=${value}'))">
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
<a href="#" onclick="page_link('${searchlink.url}?_searchname=${searchlink.linksearchname}')">${searchlink.linkname}</a>
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

