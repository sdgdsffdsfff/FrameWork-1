<script type="text/javascript">
var navigationJSON=[ {name:'系统管理',link:'${base}/module/irm/portal/portal/portal/portal_browse.action?ccate=admin'}, {name:'工作流程监控'}];

$('#bt_suspend').click(function() {page_suspend()});
$('#bt_resume').click(function() {page_resume()});
$('#bt_terminate').click(function() {page_terminate()});
$('#bt_relocate').click(function() {page_relocate()});
</script>
<script type="text/javascript">

//挂起
function page_suspend()
{
	var oids=[];
	$('.dataGrid tbody .checkbox').each(function(j,k){		
		if($(this).val()==1){
			oids.push($(this).attr('data-id'))	
		}		
	})
	
	if(oids.length<=0)
	{
		alert("请先选择要挂起的数据！");
		return;
	}
	
	if(confirm("确实要挂起选中的记录?"))
	{
		$.post("bflowwatch_issuspend.action?runflowkeys="+oids,
		{},
		function(data){
			if(data)
			{
				window.location="bflowwatch_suspend.action?runflowkeys="+oids;
			}else
			{
				alert("只有运行的流程才能挂起！");
			}
		})
	}
}

//继续
function page_resume()
{
	var oids=[];
	$('.dataGrid tbody .checkbox').each(function(j,k){		
		if($(this).val()==1){
			oids.push($(this).attr('data-id'))	
		}		
	})
	
	if(oids.length<=0)
	{
		alert("请先选择要继续的数据！");
		return;
	}
	
	if(confirm("确实要继续选中的记录?"))
	{
		$.post("bflowwatch_isresume.action?runflowkeys="+oids,
		{},
		function(data){
			if(data)
			{
				window.location="bflowwatch_resume.action?runflowkeys="+oids;
			}else
			{
				alert("只有挂起的流程才能继续！");
			}
		})
	}
}

//终止
function page_terminate()
{
	var oids=[];
	var isover = false;
	var sign = false;
	$('.dataGrid tbody .checkbox').each(function(j,k){		
		if($(this).val()==1){
			oids.push($(this).attr('data-id'));
			var state = $(this).parent().parent().children().eq(7).text();
			var changeflow = $(this).parent().parent().children().eq(4).text();
			var ischange = $(this).parent().parent().children().eq(2).text();
			if(state == "结束" || state == "终止")
			{
				isover = true;
			}		
			if(ischange=="ConfigChange" && changeflow=="执行")
			{
				sign = true;
			}
		}		
	})
	
	if(oids.length<=0)
	{
		alert("请先选择要终止的数据！");
		return;
	}
	
	if(isover)
	{
		alert("所选流程已经结束或终止，请重新选择！");
		return;
	}
	
	if(sign)
	{
		alert("所选的配置变更流程，已处于【执行】阶段，不可以终止！");//只要是已经转了"执行",就不能在终止了
		return;
	}
	
	if(confirm("确实要终止选中的记录?"))
	{
		$.post("${base}/module/app/system/workflow/ui/terminateflow_isterminate.action?runflowkeys="+oids,
		{},
		function(data){
			if(data)
			{
				window.location="${base}/module/app/system/workflow/ui/terminateflow_terminate.action?runflowkeys="+oids;
			}
			else
			{
				alert("所选流程包含子流程，请先终止子流程后再试！");
			}
		})
	}
}

//重定向
function page_relocate()
{
	var oids=[];
	var isover = false;
	var sign = false;
	$('.dataGrid tbody .checkbox').each(function(j,k){
		
		
		if($(this).val()==1){
			oids.push($(this).attr('data-id'))	
			var state = $(this).parent().parent().children().eq(7).text();	
			var changeflow = $(this).parent().parent().children().eq(4).text();
			var ischange = $(this).parent().parent().children().eq(2).text();
			if(state == "结束" || state == "终止")
			{
				isover = true;
			}	
			if(ischange=="ConfigChange" && changeflow=="执行")
			{
				sign = true;
			}
		}		
	})
	
	if(oids.length<=0)
	{
		alert("请先选择要重定向的数据！");
		return;
	}	
    else if(oids.length>1)
	{
		alert("只能选择一条重定位的数据！");
		return;
	}
	if(isover)//结束和终止的不能进行重定向
	{
		alert("当前流程已经结束或者终止，不能重定向！");
		return;
	}
	if(sign)
	{
		alert("所选的配置变更流程，已处于【执行】阶段，不可以终止！");//只要是已经转了"执行",就不能在终止了
		return;
	}
	if(confirm("确实要重定位选中的记录?"))
	{
		url="${base}/module/app/system/workflow/ui/selectact_browseroute.action?runflowkey="+oids;
		openwin(url,'openflow',pub_width_mid,pub_height_mid); 
	}
}
</script>