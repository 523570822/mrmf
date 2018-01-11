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
						<div class="jqGrid_wrapper">
							<table id="leibieTable"></table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		var manageType;
		$()
				.ready(
						function() {
							$("#newBumen").click(
									function() {
										document.location.href = _ctxPath
												+ "/kucun/leibie/toUpsert.do";
									});

							$("#searchForm").submit(function() {
								$("#leibieTable").reloadGrid({
									postData : $("#searchForm").formobj()
								});
								return false;
							});
							$("#leibieTable")
									.grid(
											{
												url : _ctxPath
														+ "/weixin/comment/query.do",
												postData : $("#searchForm")
														.formobj(),
												colNames : [ "用户","店铺","技师","评价内容","评价时间" ],
												colModel : [
															
															{
															name : "userName",
															index : "userId"
														},
														{
															name : "organName",
															index : "organId"
														},
														{
															name : "staffName",
															index : "staffId"
														},
														{
															name : "content",
															index : "content"
														},{
														   name:"createTime",
														   index:"createTime"
														}
														 ]
											});

						});
		function removeLeibie(leibieId) {
			var url = _ctxPath + "/kucun/leibie/remove/" + leibieId + ".do";
			$.get(url, {}, function(data) {
				if (data) {
					if (data.success) {
						toastr.success("操作成功");
					} else {
						toastr.error(data.message);
					}
					$("#leibieTable").trigger("reloadGrid");
				} else {
					toastr.error("操作失败");
				}
			});
		}
	</script>
</body>
</html>
