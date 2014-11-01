<script type="text/javascript" src="${base}/themes/default/jquery.js"></script>

<script type="text/javascript">
var navigationJSON=[ {name:'系统管理',link:'${base}/module/irm/portal/portal/portal/portal_browse.action?ccate=admin'}, {name:'数据字典分类'}];

$("#bt_input").click(function() {page_input()});
$("#bt_del").click(function() {page_delete()});
</script>

<script type="text/javascript">

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
		window.location = "dictionaryclass_delete.action?supid=${arg.supid}&ids="+oids.join(',');
	}
}

function page_input()
{
	url = "${base}/module/app/system/dictionary/dictionaryclass/dictionaryclass_input.action?_searchname=system.dictionary.dictionaryclass.input&supid=${arg.supid}";
	window.location = url;
}

</script>