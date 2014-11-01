<script type="text/javascript">
var navigationJSON=[ {name:'系统管理',link:'${base}/module/irm/portal/portal/portal/portal_browse.action?ccate=admin'}, {name:'数据字典'}];

$("#bt_input").click(function() {page_input()});
$("#bt_del").click(function() {page_delete()});
</script>

<script type="text/javascript">
$(function(){
	$('.dataGrid a').each(function(){
		var url = $(this).attr('href');
		url=url+"&dclassid=${arg.dclassid}";
		$(this).attr('href',url);
		//$(this).attr('target','_parent');
	});
});

function page_delete()
{
	if(confirm("确实要删除选中的记录?"))
	{
		var oids=[];
		$('.dataGrid tbody .checkbox').each(function(j,k){		
			if($(this).val()==1){
				oids.push($(this).attr('data-id'))	
			}		
		})
		window.location = "dictionary_delete.action?dclassid=${arg.dclassid}&ids="+oids.join(',');
	}
}

function page_input()
{
	url = "${base}/module/app/system/dictionary/dictionary/dictionary_input.action?_searchname=system.dictionary.dictionary.input&dclassid=${arg.dclassid}";
	window.openwin(url,'inputdictionary',pub_width_large,pub_height_large);
}

</script>