<#assign apage = {"currentpage":data.page.pageNo,"pagesize":data.page.pageSize,"maxpage":data.page.totalPages,"size":data.page.result?size,"hasnext":data.page.hasNext,"haspre":data.page.hasPre,"rowcount":data.page.totalCount,"softtag",data.page.order,"softfield", data.page.orderBy,"result":data.page.result}>
<#assign vo = data.vo>

<#import "com/ray/app/query/view/pageajax_macros.ftl" as pub_macros>

{
	th:[<@pub_macros.displayheader vo = vo arg = arg></@pub_macros.displayheader>],
	items:
    [
        <@pub_macros.displaylist apage = apage vo = vo arg = arg></@pub_macros.displaylist>
    ]
}