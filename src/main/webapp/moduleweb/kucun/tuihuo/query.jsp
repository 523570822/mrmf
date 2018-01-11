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
								<label class="col-sm-2 control-label">产品名称</label>
								<div class="col-sm-3">
									<input id="regex:mingcheng" name="regex:mingcheng" type="text"
										class="form-control">
								</div>
								
								<div class="col-sm-2">
									<button id="search" class="btn btn-primary" type="submit">查询</button>
								</div>
								<!-- <div class="col-sm-2">
									<button id="rukuButton" class="btn btn-outline btn-danger"
										type="button">入库</button>
								</div>
								<div class="col-sm-2">
									<button id="chukuButton" class="btn btn-outline btn-danger"
										type="button">出库</button>
								</div> -->
							</div>
						</form>
						<div class="jqGrid_wrapper">
							<table id="tuihuoTable"></table>
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
							$("#rukuButton").click(
									function() {
										document.location.href = _ctxPath
												+ "/kucun/ruku/toUpsert.do";
									});

							$("#searchForm").submit(function() {
								$("#tuihuoTable").reloadGrid({
									postData : $("#searchForm").formobj()
								});
								return false;
							});
							$("#tuihuoTable")
									.grid(
											{
												url : _ctxPath
														+ "/kucun/tuihuo/query.do",
														shrinkToFit : false,
												postData : $("#searchForm")
														.formobj(),
												colNames : [ "产品编码","产品名称","物品类别","单价","数量","总价","退货人","外卖出库","净含量","总量","部门","备注","退货时间" ],
												colModel : [{
															name : "code",
															index : "code",
															align : "center"
															},
															{
															name : "mingcheng",
															index : "mingcheng",
															align : "center"
														},
														{
															name : "wupinName",
															index : "wupinId",
															align : "center"
														},
														{
															name : "price",
															index : "price",
															align : "center"
														},{
															name : "num",
															index : "num",
															align : "center"
														},
														{
															name : "price_all",
															index : "price_all",
															align : "center"
														},
														{
															name : "staff",
															index : "staff",
															align : "center"
														},
														{
															name : "flagName",
															index : "flag",
															align : "center"
														},
														{
															name : "weight",
															index : "weight",
															align : "center"
														},
														{
															name : "weight_all",
															index : "weight_all",
															align : "center"
														},
														{
															name : "bumenName",
															index : "bumen",
															align : "center"
														},
														{
															name : "note",
															index : "note",
															align : "center"
														},
														{
															name : "createTime",
															index : "createTime",
															align : "center"
														}
														 ],gridComplete: function(){
							             	    $(".ui-jqgrid-sortable").css("text-align", "center");	
							             	    }
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
