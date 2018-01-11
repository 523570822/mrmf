<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/moduleweb/resources/common.jsp"%>
<html>
<head>
<title></title>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content  animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content">
						<form id="searchForm" method="get" class="form-horizontal">
							<div class="form-group">
								<div class="col-sm-1">
									<button class="btn btn-danger" id="sendRedPacket" type="button"><strong>发送红包</strong></button>
								</div>
								<label  class="col-sm-1 control-label">红包金额:</label>
								<div class="col-sm-2">
									<input id="amount|double" name="amount|double"
										type="text" class="form-control">
								</div>
								<label style="width:70px"class="col-sm-1 control-label">时间:</label>
								<div style="width:250px" class="col-sm-3">
									<input id="gte:createTime|datetime" name="gte:createTime|datetime"
										class="laydate-icon form-control" placeholder="开始日期">
								</div>
								<label style="width:10px" class="col-sm-1 control-label">至</label>
								<div  style="width:250px" class="col-sm-3">
									<input id="lte:createTime|datetime" name="lte:createTime|datetime"
										class="laydate-icon form-control" placeholder="结束日期">
								</div>
								<div class="col-sm-1" >
									<button id="search" class="btn btn-primary" type="submit">查询</button>
								</div>
							</div>
						</form>
						 <div class="jqGrid_wrapper">
								<table id="redpacketTable"></table>
						 </div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$().ready(
				function() {
							var start = {
								elem : '#gte:createTime|datetime',
								format : 'YYYY-MM-DD hh:mm:ss',
							/* 	min : laydate.now(), //设定最小日期为当前日期 */
								max : '2099-06-16 23:59:59', //最大日期
								istime : true,
								istoday : false,
								choose : function(datas) {
									end.min = datas; //开始日选好后，重置结束日的最小日期
									end.start = datas; //将结束日的初始值设定为开始日
								}
							};
							var end = {
								elem : '#lte:createTime|datetime',
								format : 'YYYY-MM-DD hh:mm:ss',
							/* 	min : laydate.now(), */
								max : '2099-06-16 23:59:59',
								istime : true,
								istoday : false,
								choose : function(datas) {
									start.max = datas; //结束日选好后，重置开始日的最大日期
								}
							};
							laydate(start);
							laydate(end);
							$("#searchForm").submit(function() {
								$("#redpacketTable").reloadGrid({
									postData : $("#searchForm").formobj()
								});
								return false;
							});
							$("#redpacketTable")
									.grid(
											{
												url : _ctxPath
														+ "/weixin/redPacket/queryRedPacket.do",
												shrinkToFit : false,
												colNames : [ "操作","红包总金额", "红包总个数",
														"红包剩余金额", "红包剩余数", "红包状态","红包发送时间",
														"红包描述"],
												colModel : [
														{
															name : "_id",
															index : "_id",
															align : "center",
															formatter : function(cellvalue,options,rowObject) {
															var v = "<a href='${ctxPath}/weixin/redPacket/toRedPacketDetail.do?redId="
															  + cellvalue + "'>查看红包详情</a>";
															   return v;
															 }
														},
														{
															name : "amount",
															index : "amount",
															align : "center",
														},
														{
															name : "count",
															index : "count",
															align : "center"
														},
														{
															name : "restAmount",
															index : "restAmount",
															align : "center"
														},
														{
															name : "restCount",
															index : "restCount",
															align : "center"
														},
														{
															name : "state",
															index : "state",
															align : "center",
															formatter : function(cellvalue,options,rowObject) {
																    if(cellvalue == 0) {
																    	return "无效";
																    } else if(cellvalue == 1){
																        return "进行中";
																    } else if(cellvalue == 2) {
																        return "结束";
																    }
															}
														},
														{
															name : "createTime",
															index : "createTime",
															align : "center"
														},
														{
															name : "desc",
															index : "desc",
															align : "center"
														}],
												gridComplete: function(){
							             	$(".ui-jqgrid-sortable").css("text-align", "center");  
							         	}
								});
						});
						 $("#sendRedPacket").click(function(){
							window.location.href = _ctxPath + "/weixin/redPacket/sendRedPacket.do";
						}); 
	</script>
</body>
</html>
