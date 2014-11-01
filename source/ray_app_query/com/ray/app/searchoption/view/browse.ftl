<script type="text/javascript">
$(function(){
//////////////////////

$("#bt_input").click(function() {page_input()});
$("#bt_del").click(function() {page_delete()});
$("#bt_arrange").click(function() {page_arrange()});
$("#bt_sort").click(function() {page_sort()});

///////////////////////
})

function page_input()
{
	url = "searchoption_input.action?_searchname=searchoption.input&searchid=${searchid}";
	//alert(url);
	openwin(url,pub_height_mid, pub_width_mid);
}

function page_delete()
{
	if (typeof(currentkey)== "undefined")
	{
		alert("请选择要删除的记录！");
		return;
	}
	
	if(confirm("确实要删除选中的记录吗？"))
	{
		url = "searchoption_delete.action?searchid=${searchid}&searchoptionid=" + currentkey;
		//alert(url);
		window.location = url;
	}
}

function page_arrange()
{
	url = "searchoption_arrange.action?searchid=${searchid}";
	//alert(url);
	window.location = url;
}

function page_sort()
{
	url = "searchoption_sort.action?searchid=${searchid}&flag=S";
	//alert(url);
	openquerywindow(url);
}

</script>