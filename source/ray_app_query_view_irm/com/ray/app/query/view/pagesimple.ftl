<#assign apage = {"currentpage":data.page.pageNo,"pagesize":data.page.pageSize,"maxpage":data.page.totalPages,"size":data.page.result?size,"hasnext":data.page.hasNext,"haspre":data.page.hasPre,"rowcount":data.page.totalCount,"softtag",data.page.order,"softfield", data.page.orderBy,"result":data.page.result}>
<#assign vo = data.vo>

<#import "com/ray/app/query/view/pagesimple_macros.ftl" as pub_macros>

<@pub_macros.displaylisttable apage = apage vo = vo arg = arg></@pub_macros.displaylisttable>

<#if vo.macro!="">
<#include "${vo.macro}">
</#if>