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
								<div class="col-sm-2">
									<input id="regex:mingcheng" name="regex:mingcheng" type="text"
										class="form-control">
								</div>
								<label class="col-sm-1 control-label">单号</label>
								<div class="col-sm-2">
									<input id="regex:danhao" name="regex:danhao" type="text"
										class="form-control">
								</div>
								<div class="col-sm-2">
									<button id="search" class="btn btn-primary" type="submit">查询</button>
								</div>
								<div class="col-sm-2">
									<button id="newBumen" class="btn btn-outline btn-danger"
										type="button">入库</button>
								</div>
							</div>
						</form>
						<div class="jqGrid_wrapper">
							<table id="rukuTable"></table>
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
												+ "/kucun/ruku/toUpsert.do";
									});

							$("#searchForm").submit(function() {
								$("#rukuTable").reloadGrid({
									postData : $("#searchForm").formobj()
								});
								return false;
							});
							$("#rukuTable")
									.grid(
											{
												url : _ctxPath
														+ "/kucun/ruku/query.do",
												shrinkToFit : false,
												postData : $("#searchForm")
														.formobj(),
												colNames : [ "操作", "单号",<c:if test='${HQ==true}'>"退货","退货公司名称",</c:if>
														"物品类别", "产品编码", "产品名称",
														"进货价格", "进货数量", "总价",
														"产品产地", "联系人",
														"产品公司地址", "电话", "传真",
														"邮箱", "进货人", "备注",
														"产品状态", "净含量", "总量",
														"部门", "助记符", "进货时间",
														"有效期", "单位", "进货单位名称",
														"组盘号", "规格", "入库审核",
														"销售价格", "销售总价", "品牌",
														"分公司入库审核", "日期" ],
												colModel : [
														{
															name : "_id",
															index : "_id",
															align : "center",
															formatter : function(
																	cellvalue,
																	options,
																	rowObject) {
																// 																	var v = "<a href='${ctxPath}/kucun/leibie/toUpsert.do?leibieId="
																// 																			+ cellvalue
																// 																			+ "'>详情</a>&nbsp;&nbsp;";
																var v = "";
																if(rowObject.return_flag!=true){
																if (rowObject.shenhe_fen == null) {
																	v = "<a href='javascript:void(0);' onclick='removeRuku(\""
																			+ cellvalue
																			+ "\")'>删除</a>";
																}
																}
																return v;
															}
														},
														{
															name : "danhao",
															index : "danhao",
															align : "center"
														},
														<c:if test='${HQ==true}'>
														{name : "return_flag",
															index : "return_flag",
															align : "center",
															formatter : function(
																	cellvalue,
																	options,
																	rowObject) {
																 	if(cellvalue==true)	{
																 		return "退货";
																 	}else{
																 		return "否";
																 	}																	+ "'>详情</a>&nbsp;&nbsp;";
																
															}
															},
															{name : "return_organName",
																	index : "return_organId",
																	align : "center"
															},</c:if>
														{
															name : "wupinName",
															index : "wupinId",
															align : "center"
														},
														{
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
															name : "price",
															index : "price",
															align : "center"
														},
														{
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
															name : "place1",
															index : "place1",
															align : "center"
														},
														{
															name : "lianxiren",
															index : "lianxiren",
															align : "center"
														},
														{
															name : "place2",
															index : "place2",
															align : "center"
														},
														{
															name : "phone",
															index : "phone",
															align : "center"
														},
														{
															name : "chuanzhen",
															index : "chuanzhen",
															align : "center"
														},
														{
															name : "email",
															index : "email",
															align : "center"
														},
														{
															name : "staff",
															index : "staff",
															align : "center"
														},
														{
															name : "note",
															index : "note",
															align : "center"
														},
														{
															name : "flag",
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
															name : "zjfCode",
															index : "zjfCode",
															align : "center"
														},
														{
															name : "come_time",
															index : "come_time",
															align : "center"
														},
														{
															name : "useful_life",
															index : "useful_life",
															align : "center"
														},
														{
															name : "danweiName1",
															index : "danwei",
															align : "center"
														},
														{
															name : "danweiname",
															index : "danweiname",
															align : "center"
														},
														{
															name : "zupanhao",
															index : "zupanhao",
															align : "center"
														},
														{
															name : "guige",
															index : "guige",
															align : "center"
														},
														{
															name : "shenheName",
															index : "shenhe",
															align : "center"
														},
														{
															name : "price_xs",
															index : "price_xs",
															align : "center"
														},
														{
															name : "price_all_xs",
															index : "price_all_xs",
															align : "center"
														},
														{
															name : "pinpaiName",
															index : "pinpai",
															align : "center"
														},
														{
															name : "shenhefenName",
															index : "shenhe_fen",
															align : "center"
														},
														{
															name : "createTime",
															index : "createTime",
															align : "center"
														} ],
												gridComplete : function() {
													$(".ui-jqgrid-sortable")
															.css("text-align",
																	"center");
												}
											});

						});
		function removeRuku(rukuId) {
			var url = _ctxPath + "/kucun/ruku/remove/" + rukuId + ".do";
			$.get(url, {}, function(data) {
				if (data) {
					if (data.success) {
						toastr.success("操作成功");
					} else {
						toastr.error(data.message);
					}
					$("#rukuTable").trigger("reloadGrid");
				} else {
					toastr.error("操作失败");
				}
			});
		}
	</script>
</body>
</html>
