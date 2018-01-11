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
				<div class="ibox ">
					<div class="ibox-title">
						<h5>登陆日志查询</h5>
					</div>
					<div class="ibox-content">
						<form id="searchForm" method="get" class="form-horizontal">
							<input type="hidden" id="parentId" name="parentId"
								value="${parentId}"> <input type="hidden" id="sortField"
								name="sortField" value="createTime"> <input
								type="hidden" id="sortOrder" name="sortOrder" value="DESC">
							<div class="form-group">
								<div class="col-sm-2">
									<input id="gte:createTime|date" name="gte:createTime|date"
										class="laydate-icon form-control layer-date"
										placeholder="起始日期" data-mask="9999-99-99"
										laydate="{format : 'YYYY-MM-DD',min : '1938-06-16 23:59:59'}">
								</div>
								<div class="col-sm-2">
									<input id="lte:createTime|date+1" name="lte:createTime|date+1"
										class="laydate-icon form-control layer-date"
										placeholder="结束日期" data-mask="9999-99-99"
										laydate="{format : 'YYYY-MM-DD',min : '1938-06-16 23:59:59'}">
								</div>
								<label class="col-sm-1 control-label">账号</label>
								<div class="col-sm-2">
									<input id="regex:accountName" name="regex:accountName"
										type="text" class="form-control">
								</div>
								<label class="col-sm-1 control-label">描述</label>
								<div class="col-sm-2">
									<input id="regex:memo" name="regex:memo" type="text"
										class="form-control">
								</div>

								<div class="col-sm-1">
									<button id="search" class="btn btn-primary" type="submit">查询</button>
								</div>
							</div>
						</form>
						<div class="jqGrid_wrapper">
							<table id="loginLogTable"></table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$().ready(function() {
			$("#searchForm").submit(function() {
				$("#loginLogTable").reloadGrid({
					data : $("#searchForm").formobj()
				});
				return false;
			});
			$("#loginLogTable").grid({
				url : _ctxPath + "/loginLog/query.do",
				colNames : [ "操作账号", "IP地址", "操作时间", "操作功能名称", "操作描述" ],
				postData : $("#searchForm").formobj(),
				colModel : [ {
					name : "accountName",
					index : "accountName"
				},

				{
					name : "ip",
					index : "ip",
					align : "center"
				}, {
					name : "createTime",
					index : "createTime"
				}, {
					name : "functionName",
					index : "functionName"
				}, {
					name : "memo",
					index : "memo"
				} ]
			});

		});
	</script>
</body>
</html>
