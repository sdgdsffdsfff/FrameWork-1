<#import "pagesimple_macros.ftl" as pub_macros>
<#assign vo = data.vo>
<#assign apage = data.apage>

<@pub_macros.displaylisttable apage = apage vo = vo arg = arg></@pub_macros.displaylisttable>

<#if vo.macro!="">
<#include "${vo.macro}">
</#if>
