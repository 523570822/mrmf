<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/moduleweb/resources/common.jsp"%>
<c:set var="organId"
	value="${param.organId == null?sessionScope.organId:param.organId}" />
<html>
<head>
<title></title>
<style type="text/css">
.form-group {
	margin-bottom: 5px;
}
</style>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content">
						<div class="row">
							<div class="col-sm-4">
								<button id="btnRefresh" class="btn btn-info" type="submit">刷新</button>
							</div>
						</div>
						<div class="jqGrid_wrapper">
							<table id="myTable"></table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$()
				.ready(
						function() {
							$("#btnRefresh").click(function() {
								reloadTable();
							});
							$("#myTable")
									.grid(
											{
												pager : null,
												shrinkToFit : false,
												datatype : "local",
												colNames : [ "操作", "顾客姓名",
														"顾客电话", "顾客欠费", "物品名称",
														"规格", "单价", "数量", "折扣",
														"总价", "售出日期", "售出人员",
														"购买人", "售出人员2", "业绩1",
														"业绩2", "免单", "是否挂账" ],
												colModel : [
														{
															name : "_id",
															index : "_id",
															width : 60,
															align : "center",
															formatter : function(
																	cellvalue,
																	options,
																	rowObject) {
																var v = "<a href='javascript:void(0);' onclick='handle(\""
																		+ cellvalue
																		+ "\")'>处理</a>";

																return v;
															}
														},
														{
															name : "name",
															width : 80,
															index : "name"
														},
														{
															name : "phone",
															width : 100,
															index : "phone"
														},
														{
															name : "money_qian",
															width : 80,
															index : "money_qian"
														},
														{
															name : "wupinName",
															index : "wupinName"
														},
														{
															name : "guige",
															width : 80,
															index : "guige"
														},
														{
															name : "money2",
															width : 80,
															index : "money2"
														},
														{
															name : "num",
															width : 80,
															index : "num"
														},
														{
															name : "zhekou",
															width : 80,
															index : "zhekou"
														},
														{
															name : "money1",
															width : 80,
															index : "money1"
														},
														{
															name : "createTime",
															index : "createTime"
														},
														{
															name : "staffId1",
															sortable : false,
															width : 100,
															index : "staffId1",
															formatter : function(
																	cellvalue) {
																var ms = fillmaps.staffs;
																for (var i = 0; i < ms.length; i++) {
																	if (ms[i]._id == cellvalue) {
																		return ms[i].name;
																	}
																}
																return "无";
															}
														},
														{
															name : "buyname",
															width : 100,
															index : "buyname"
														},
														{
															name : "staffId2",
															sortable : false,
															index : "staffId2",
															width : 100,
															formatter : function(
																	cellvalue) {
																var ms = fillmaps.staffs;
																for (var i = 0; i < ms.length; i++) {
																	if (ms[i]._id == cellvalue) {
																		return ms[i].name;
																	}
																}
																return "无";
															}
														},
														{
															name : "yeji1",
															width : 60,
															index : "yeji1"
														},
														{
															name : "yeji2",
															width : 60,
															index : "yeji2"
														},
														{
															name : "miandan",
															index : "miandan",
															width : 60,
															formatter : function(
																	cellvalue,
																	options,
																	rowObject) {
																if (cellvalue)
																	return "是";
																else
																	return "否";
															}
														},
														{
															name : "guazhang_flag",
															index : "guazhang_flag",
															width : 70,
															formatter : function(
																	cellvalue,
																	options,
																	rowObject) {
																if (cellvalue)
																	return "是";
																else
																	return "否";
															}
														} ]
											});
							reloadTable();

						});
		function reloadTable() {
			$.get("${ctxPath}/waimai/queryDinggou.do", {}, function(data) {
				if (data) {
					$("#myTable").jqGrid('clearGridData');
					for (var i = 0; i <= data.length; i++) {
						$("#myTable").jqGrid('addRowData', i + 1, data[i]);
					}
				} else {
					toastr.error("操作失败");
				}
			});
		}
		function handle(waimaiId) {
			layer.confirm('确定要处理这笔外卖订购?', function(index) {
				layer.close(index);
				var url = _ctxPath + "/waimai/handleDinggou/" + waimaiId
						+ ".do";
				$.post(url, {}, function(data) {
					if (data) {
						if (data.success) {
							toastr.success("操作成功");
							reloadTable();
						} else {
							toastr.error(data.message);
						}
					} else {
						toastr.error("操作失败");
					}
				});
			});

		}
	</script>
</body>
</html>
