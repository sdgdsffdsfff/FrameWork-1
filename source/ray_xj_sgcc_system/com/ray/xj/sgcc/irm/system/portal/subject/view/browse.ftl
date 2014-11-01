<script type="text/javascript">
$("#bt_input").click(function() {page_input()});
$("#bt_del").click(function() {page_delete()});
</script>

<script type="text/javascript">
var navigationJSON=[ {name:'系统管理',link:'${base}/module/irm/portal/portal/portal/portal_browse.action?ccate=admin'}, {name:'门户栏目'}];

function page_view_row_click(keyvalue, index)
{
	currentkey = keyvalue;
}

function page_view_row_dbclick(keyname, keyvalue, index)
{
	currentkey = keyvalue;
	keynames = keyname.split(",");
	keyvalues = keyvalue.split(",");


	url = "${base}/module/irm/system/portal/subject/subject_locateframe.action?_searchname=system.portal.subject.locate";

	for(i=0;i<keynames.length;i++)
	{
		url += "&" + keynames[i] + "=" + keyvalues[i];
	}

	window.location = url;
}

function page_delete()
{
	var oids=[];
	$('.dataGrid tbody .checkbox').each(function(j,k){		
		if($(this).val()==1){
			oids.push($(this).attr('data-id'))	
		}		
	})
	
	if(oids.length<=0)
	{
		alert("请先选择要删除的数据！");
		return;
	}
	
	if(confirm("确实要删除选中的记录?"))
	{
		url="${base}/module/irm/system/portal/subject/subject_delete.action?ids="+oids.join(',');
		
		window.location = url;
	}
} 

function page_input()
{
	url = "${base}/module/irm/system/portal/subject/subject_input.action?_searchname=system.portal.subject.input";
	openwin(url,"input",pub_width_large,pub_height_large);
}

</script>