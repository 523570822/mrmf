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
<!-- 								<div class="col-sm-2"> -->
<!-- 									<button id="newBumen" class="btn btn-outline btn-danger" -->
<!-- 										type="button">新增</button> -->
<!-- 								</div> -->
							</div>
						</form>
						<div class="jqGrid_wrapper">
							<table id="childleibieTable"></table>
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
							$("#childleibieTable")
									.grid(
											{
												url : _ctxPath
														+ "/kucun/childCategory/query.do",
														shrinkToFit : false,
												postData : $("#searchForm")
														.formobj(),
												colNames : [ "产品编码","产品名称","终端销售价格",<c:if test='${filiale!=true}'>"进货价格",</c:if>"子公司代理价格","部门","员工提成(%)","报警数量","报警天数","助记符","每瓶净含量","单位","规格","组盘号","品牌" ],
												colModel : [
															{
															name : "code",
															index : "code"
														},
														{
															name : "mingcheng",
															index : "mingcheng"
														},
														{
															name : "price_xs",
															index : "price_xs",
															formatter : function(
																	cellvalue,
																	options,
																	rowObject) {
																var v = "<a href=\"javascript:editPriceXs("
																		+ cellvalue
																		+ ",'"
																		+ rowObject._id
																		+ "')\">"
																		+ cellvalue
																		+ "</a>&nbsp;&nbsp;";

																return v;
															}
														},
														<c:if test='${filiale!=true}'>
														{
															name : "price",
															index : "price"
														},
														</c:if>
														{
															name : "price_ch",
															index : "price_ch"
														},
														{
															name : "bumenName",
															index : "bumen"
														},
														{
															name : "ticheng",
															index : "ticheng"
														},
														{
															name : "jingjie",
															index : "jingjie"
														},
														{
															name : "jingjieday",
															index : "jingjieday"
														},
														{
															name : "zjfCode",
															index : "zjfCode"
														},
														{
															name : "weight",
															index : "weight"
														},
														{
															name : "danweiName",
															index : "danwei"
														},
														{
															name : "guige",
															index : "guige"
														},
														{
															name : "zupanhao",
															index : "zupanhao"
														},{
															name : "pinpaiName",
															index : "pinpai"
														}
														 ]
											});

						});
						function editPriceXs(price_xs, leibieId) {
							 layer
									.open({
										type : 1,
										skin : 'layui-layer-rim', //加上边框
										area : [ '420px', '240px' ], //宽高
										content : '<form>'
												+ '<div class="form-group"><input type="hidden" id="leibieId" name="leibieId" value="' + leibieId + '"><label class="col-sm-4 control-label" style="text-align: right;">终端销售价格：</label>'
												+ '<div class="col-sm-3"><input id="pricexs" name="pricexs" type="text" class="form-control" value="' + price_xs + '"></div>'
												+ '<div class="col-sm-3"><button id="search" class="btn btn-primary" type="button" onclick="savePriceXs()">保存</button></div>'
												+ '</div></from>'
									});
						}

		function savePriceXs() {
			var leibieId = $("#leibieId").val();
			var pricexs = $("#pricexs").val();
			var url = _ctxPath + "/kucun/childleibie/update.do";
			$.get(url, {'leibieId':leibieId,'pricexs':pricexs}, function(data) {
				if (data) {
					if (data.success) {
						layer.closeAll();
						toastr.success("操作成功");
					} else {
						toastr.error(data.message);
					}
					$("#childleibieTable").trigger("reloadGrid");
				} else {
					toastr.error("操作失败");
				}
			});

		}
		function removeLeibie(leibieId) {
			var url = _ctxPath + "/kucun/leibie/remove/" + leibieId + ".do";
			$.get(url, {}, function(data) {
				if (data) {
					if (data.success) {
						toastr.success("操作成功");
					} else {
						toastr.error(data.message);
					}
					$("#childleibieTable").trigger("reloadGrid");
				} else {
					toastr.error("操作失败");
				}
			});
		}
	</script>
</body>
</html>
