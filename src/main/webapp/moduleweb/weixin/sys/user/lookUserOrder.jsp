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
					<div class="ibox-title">
						<h5>
							<a href="${ctxPath}/weixin/sys/user/userToQuery.do" class="btn-link"> <i
								class="fa fa-angle-double-left"></i>返回
							</a>
						</h5>
					</div>
					<div class="ibox-content">
						<div class="row" style="margin-top:20px;">  
							<div class="col-sm-12">                      
				                <ul class="nav nav-tabs">
								   <li><a href="${ctxPath}/weixin/sys/user/lookUser.do?userId=${userId }">基本信息</a></li>
								   <li class="active"><a href="#">预约信息</a></li>
								</ul>
							</div> 
				        </div>
						<div class="jqGrid_wrapper" style="margin-top: 20px;">
							<table id="userTable"></table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
		$()
		.ready(
				function() {

					$("#userTable")
							.grid(
									{
										url : _ctxPath
												+ "/weixin/sys/user/queryUserOrder.do?userId=${userId}",
										colNames : [ "预约时间", "预约技师", "预约店铺", "预约项目", "订单价格", "状态" ],
										colModel : [
												{
													name : "orderTime",
													index : "orderTime",
													align : "center"
												},
												{
													name : "staffName",
													index : "staffName",
													align : "center"
												},
												{
													name : "organName",
													index : "organName",
													align : "center"
												},
												{
													name : "serviveName",
													index : "serviveName",
													align : "center"
												},
												{
													name : "orderPrice",
													index : "orderPrice",
													align : "center"
												},
												{
													name : "state",
													index : "state",
													align : "center",
													formatter : function(
															cellvalue,
															options,
															rowObject) {
														var v = "";
														switch(cellvalue)
														{
														case -1:
														  	v = "已拒绝";
														  	break;
														case 0:
														  	v = "已取消";
															break;
														case 1:
														  	v = "已预约";
															break;
														case 2:
														  	v = "已确认";
															break;
														case 3:
														  	v = "已支付";
															break;
														case 10:
														  	v = "已评价(已完成)";
															break;
														}
														return v;
													}
												}],
									});

						});
		
		
	</script>
</html>
