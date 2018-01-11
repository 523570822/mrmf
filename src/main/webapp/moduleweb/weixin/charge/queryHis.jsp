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
					<c:if test="${param.organId != null && param.organId != ''}">
						<div class="ibox-title">
							<h5>
								<a href="${ctxPath}/weixin/charge/toQuery.do" class="btn-link"><i
									class="fa fa-angle-double-left"></i>返回</a> 充值记录
							</h5>
						</div>
					</c:if>
					<div class="ibox-content">
						<form id="searchForm" method="get" class="form-horizontal">
							<input id="organId" name="organId" type="hidden"
								value="${organId}">
						</form>
						<div class="jqGrid_wrapper">
							<table id="myTable"></table>
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
				$("#myTable").reloadGrid({
					postData : $("#searchForm").formobj()
				});
				getSum();
				return false;
			});

			$("#myTable").grid({
				url : _ctxPath + "/weixin/charge/queryHis.do",
				postData : $("#searchForm").formobj(),
				shrinkToFit : false,
				colNames : [ "金额", "时间" ],
				colModel : [ {
					name : "amount",
					index : "amount"
				}, {
					name : "createTime",
					index : "createTime"
				} ]
			});
		});
	</script>
</body>
</html>
