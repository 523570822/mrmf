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
								
								<label class="col-sm-2 control-label">品牌:</label>
										<div class="col-sm-2">
											<input type="text" class="form-control suggest"
												id="pinpai" name="pinpai"
												suggest="{data :fillmaps.pinpai,style2:true,searchFields : [ '_id' ],keyField : 'name'}">
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
							<table id="kucunTable"></table>
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
								$("#kucunTable").reloadGrid({
									postData : $("#searchForm").formobj()
								});
								return false;
							});
							$("#kucunTable")
									.grid(
											{
												url : _ctxPath
														+ "/kucun/kucun/query.do",
														shrinkToFit : false,
												postData : $("#searchForm")
														.formobj(),
												colNames : [ "品牌","产品名称","单价","数量","总价","备注","净含量","总量","部门","助记符","报警数量","有效期","报警日期","规格" ],
												colModel : [
// 															{
// 																name : "_id",
// 																index : "_id",
// 																align : "center",
// 																formatter : function(
// 																		cellvalue,
// 																		options,
// 																		rowObject) {
// 																	var v = "<a href='${ctxPath}/kucun/leibie/toUpsert.do?leibieId="
// 																			+ cellvalue
// 																			+ "'>详情</a>&nbsp;&nbsp;";
	
// 																	v += "<a href='javascript:void(0);' onclick='removeLeibie(\""
// 																			+ cellvalue
// 																			+ "\")'>删除</a>";
	
// 																	return v;
// 																}
// 															},
															{
															name : "pinpaiName",
															index : "pinpai"
															},
															{
															name : "mingcheng",
															index : "mingcheng"
														},
														{
															name : "price",
															index : "price"
														},
														{
															name : "num",
															index : "num"
														},
														{
															name : "price_all",
															index : "price_all"
														},
														{
															name : "note",
															index : "note"
														},
														{
															name : "weight",
															index : "weight"
														},
														{
															name : "weight_all",
															index : "weight_all"
														},
														{
															name : "bumenName",
															index : "bumen"
														},
														{
															name : "zjfCode",
															index : "zjfCode"
														},
														{
															name : "jingjie",
															index : "jingjie"
														},
														{
															name : "useful_life",
															index : "useful_life"
														},
														{
															name : "jingjiedate",
															index : "jingjiedate"
														},
														{
															name : "guige",
															index : "guige"
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
