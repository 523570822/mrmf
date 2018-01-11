<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/moduleweb/resources/common.jsp"%>
<c:set var="organId"
	value="${param.organId == null?sessionScope.organId:param.organId}" />
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
							<input id="organId" name="organId" type="hidden"
								value="${organId}">
							<div class="form-group">
								<div class="col-sm-2">
									<button id="search" class="btn btn-primary" type="submit">刷新</button>
								</div>
							</div>
						</form>
						<div class="jqGrid_wrapper">
							<table id="usersortTable"></table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		var manageType;
		$().ready(function() {

			$("#searchForm").submit(function() {
				$("#usersortTable").reloadGrid({
					postData : $("#searchForm").formobj()
				});
				return false;
			});

			$("#usersortTable").grid({
				url : _ctxPath + "/weixin/userpay/query.do",
				postData : $("#searchForm").formobj(),
				shrinkToFit : false,
				colNames : [ "姓名", "项目", "金额", "状态", "发生时间" ],
				colModel : [ {
					name : "userName",
					index : "userName",
					formatter : function(cellvalue, options, rowObject) {
						if (cellvalue)
							return cellvalue;
						else
							return "无";
					}
				}, {
					name : "organOrderTitle",
					index : "organOrderTitle",
					formatter : function(cellvalue, options, rowObject) {
						if (cellvalue)
							return cellvalue;
						else
							return "无";
					}
				}, {
					name : "price",
					index : "price"
				}, {
					name : "state",
					index : "state",
					align : "center",
					formatter : function(cellvalue, options, rowObject) {
						if (cellvalue == 0)
							return "未支付";
						else if (cellvalue == 1)
							return "已支付";
						else if (cellvalue == 2)
							return "已取消";
						else
							return "未知";
					}

				}, {
					name : "createTime",
					index : "createTime"
				} ]
			});
		});
	</script>
</body>
</html>
