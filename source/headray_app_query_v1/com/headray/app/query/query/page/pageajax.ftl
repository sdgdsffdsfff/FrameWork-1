<#import "pageajax_macros.ftl" as pub_macros>
<#assign vo = data.vo>
<#assign apage = data.apage>

<!--列表区-->
<table id="listTable" class="ajaxTable" border="1" cellpadding="0" cellspacing="1">
<!-- 显示标题-->

<@pub_macros.displayheader vo = vo arg = arg></@pub_macros.displayheader>

<!-- 显示数据-->
<@pub_macros.displaylist apage = apage vo = vo arg = arg></@pub_macros.displaylist>

</table>