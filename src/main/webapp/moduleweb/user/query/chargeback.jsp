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
								<input type="hidden" value="" id="selectType" name="selectType" />
								<div class="col-sm-5">
									<div class="form-group">
										<label class="control-label col-sm-9">非会员退单</label> <input
											class="col-sm-3" checked="checked" name="searchType"
											type="radio" value="user">
									</div>
									<div class="form-group">
										<label class="control-label col-sm-9">会员消费明细退单</label> <input
											class="col-sm-3" name="searchType" type="radio"
											value="vipuser">
									</div>
									<div class="form-group">
										<label class="control-label col-sm-9">外卖品消费退单</label> <input
											class="col-sm-3" name="searchType" type="radio"
											value="takeout">
									</div>
									<div class="form-group">
										<label class="control-label col-sm-9">卡中卡消费明细退单</label> <input
											class="col-sm-3" name="searchType" type="radio"
											value="incard">
									</div>
									<div class="form-group">
										<label class="control-label col-sm-9">单纯打折卡消费退单</label> <input
											class="col-sm-3" name="searchType" type="radio"
											value="salecard">
									</div>



								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label class="col-sm-6 control-label">请输入流水单号:</label>
									</div>
									<div class="form-group col-sm-10">
										<input id="xiaopiao" name="xiaopiao" type="text"
											class="col-sm-6 form-control " />
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<button id="search" class="btn btn-primary" type="submit">查询</button>
									</div>
									<div class="form-group">
										<button id="newBumen" class="btn btn-outline btn-danger"
											type="button">退单</button>
									</div>
								</div>

							</div>
						</form>
						<div class="jqGrid_wrapper">
							<table id="chargeTable"></table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		var manageType;
		$().ready(function() {
			$("#newBumen").click(function() {
				chargeBack();
			});

			$("#searchForm").submit(function() {
				var xiaopiao = $("#xiaopiao").val().trim();
				if (xiaopiao == "") {
					toastr.error("流水号不能为空！");
					return false;
				}
				var searchType = $('input[name="searchType"]:checked ').val();
				if (searchType == "user") {
					$("#selectType").val("user");
					$.jgrid.gridUnload('#chargeTable');
					initUser();
					loadAll();
					return false;
				} else if (searchType == "vipuser") {
					$("#selectType").val("vipuser");
					$.jgrid.gridUnload('#chargeTable');
					initVipUser();
					loadVipUser();
					return false;
				} else if (searchType == "takeout") {
					$("#selectType").val("takeout");
					$.jgrid.gridUnload('#chargeTable');
					initTakeOut();
					loadAll();
					return false;
				} else if (searchType == "incard") {
					$("#selectType").val("incard");
					$.jgrid.gridUnload('#chargeTable');
					initInCard();
					loadAll();
					return false;
				} else if (searchType == "salecard") {
					$("#selectType").val("salecard");
					$.jgrid.gridUnload('#chargeTable');
					initSaleCard();
					loadAll();
					return false;

				}

			});
			initUser();
		});
		function initUser() {
			$("#chargeTable").grid(
					{
						datatype : "local",
						shrinkToFit : false,
						postData : $("#searchForm").formobj(),
						rowNum : 'all',
						//pgbuttons:false,
						pager : null,
						scroll : true,
						loadonce : false,
						multiselect : true,
						// 												onSelectRow : function(id) {
						// 													console.log(id);
						// 													$("#selectId").val(id);
						// 												},
						colNames : [ "ID", "流水号", "会员号", "会员姓名", "性别", "会员类型",
								"服务大类", "服务明细", "提成", "光临日期", "应交款额", "实交款额",
								"找零", "余额", "是否美容会员", "是否交款", "现金", "银行卡",
								"代金券" ],
						colModel : [ {
							name : "_id",
							index : "_id"
						}, {
							name : "xiaopiao",
							index : "xiaopiao"
						}, {
							name : "id_2",
							index : "id_2"
						}, {
							name : "name",
							index : "name"
						}, {
							name : "sex",
							index : "sex"
						}, {
							name : "usersortName",
							index : "usersortName"
						}, {
							name : "bigsortName",
							index : "bigsortName"
						}, {
							name : "smallsortName",
							index : "smallsortName"
						}, {
							name : "havemoney1",
							index : "havemoney1"
						}, {
							name : "createTime",
							index : "createTime"
						}, {
							name : "money1",
							index : "money1"
						}, {
							name : "money2",
							index : "money2"
						}, {
							name : "money3",
							index : "money3"
						}, {
							name : "money4",
							index : "money4"
						}, {
							name : "flag1",
							index : "flag1"
						}, {
							name : "flag2",
							index : "flag2"
						}, {
							name : "money_cash",
							index : "money_cash"
						}, {
							name : "money_yinhang",
							index : "money_yinhang"
						}, {
							name : "money_lijuan",
							index : "money_lijuan"
						}

						]
					});
			$("#chargeTable").jqGrid('hideCol', [ "_id" ]);
		}
		//会员消费查询
		function initVipUser() {
			$("#chargeTable").grid(
					{
						datatype : "local",
						shrinkToFit : false,
						postData : $("#searchForm").formobj(),
						rowNum : 'all',
						//pgbuttons:false,
						pager : null,
						scroll : true,
						multiselect : true,
						loadonce : false,
						colNames : [ "ID", "流水号", "会员号", "会员姓名", "会员类型",
								"服务大类", "服务明细", "服务日期", "总次数", "剩余次数", "提成",
								"单次款额", "余额", "消费次数" ],
						colModel : [ {
							name : "_id",
							index : "_id"
						}, {
							name : "xiaopiao",
							index : "xiaopiao"
						}, {
							name : "id_2",
							index : "id_2"
						}, {
							name : "name",
							index : "name"
						}, {
							name : "usersortName",
							index : "usersortName"
						}, {
							name : "bigsort",
							index : "bigsort"
						}, {
							name : "smallsortName",
							index : "smallsortName"
						}, {
							name : "createTime",
							index : "createTime"
						}, {
							name : "allcishu",
							index : "allcishu"
						}, {
							name : "nowShengcishu",
							index : "nowShengcishu"
						},

						{
							name : "havemoney1",
							index : "havemoney1"
						}, {
							name : "danci_money",
							index : "danci_money"
						}, {
							name : "nowMoney4",
							index : "nowMoney4"
						}, {
							name : "cishu",
							index : "cishu"
						}

						]
					});
			$("#chargeTable").jqGrid('hideCol', [ "_id" ]);
		}
		function initTakeOut() {
			$("#chargeTable").grid(
					{
						datatype : "local",
						shrinkToFit : false,
						postData : $("#searchForm").formobj(),
						rowNum : 'all',
						//pgbuttons:false,
						pager : null,
						scroll : true,
						loadonce : false,
						multiselect : true,
						colNames : [ "ID", "流水号", "单价", "数量", "销售人员", "购买人",
								"价钱", "外卖时间" ],
						colModel : [ {
							name : "_id",
							index : "_id"
						}, {
							name : "xiaopiao",
							index : "xiaopiao"
						}, {
							name : "money2",
							index : "money2"
						}, {
							name : "num",
							index : "num"
						}, {
							name : "staffId1",
							sortable : false,
							index : "staffId1",
							formatter : function(cellvalue) {
								var ms = fillmaps.staffs;
								for (var i = 0; i < ms.length; i++) {
									if (ms[i]._id == cellvalue) {
										return ms[i].name;
									}
								}
								return "无";
							}
						}, {
							name : "buyname",
							index : "buyname"
						}, {
							name : "money1",
							index : "money1"
						}, {
							name : "createTime",
							index : "createTime"
						} ]
					});

			$("#chargeTable").jqGrid('hideCol', [ "_id" ]);
		}
		function initInCard() {
			$("#chargeTable").grid(
					{
						datatype : "local",
						shrinkToFit : false,
						postData : $("#searchForm").formobj(),
						rowNum : 'all',
						//pgbuttons:false,
						pager : null,
						scroll : true,
						loadonce : false,
						multiselect : true,
						colNames : [ "ID", "流水号", "会员号", "会员姓名", "会员类型",
								"服务明细", "服务日期", "总次数", "剩余次数", "提成", "单次款额",
								"余额", "消费次数" ],
						colModel : [ {
							name : "_id",
							index : "_id"
						}, {
							name : "xiaopiao",
							index : "xiaopiao"
						}, {
							name : "id_2",
							index : "id_2"
						}, {
							name : "name",
							index : "name"
						}, {
							name : "usersortName",
							index : "usersortName"
						}, {
							name : "smallsortName",
							index : "smallsortName"
						}, {
							name : "createTime",
							index : "createTime"
						}, {
							name : "allcishu",
							index : "allcishu"
						}, {
							name : "nowShengcishu",
							index : "nowShengcishu"
						},

						{
							name : "havemoney1",
							index : "havemoney1"
						}, {
							name : "danci_money",
							index : "danci_money"
						}, {
							name : "nowMoney4",
							index : "nowMoney4"
						}, {
							name : "cishu",
							index : "cishu"
						}

						]
					});
			$("#chargeTable").jqGrid('hideCol', [ "_id" ]);
		}
		function initSaleCard() {
			$("#chargeTable").grid(
					{
						datatype : "local",
						shrinkToFit : false,
						postData : $("#searchForm").formobj(),
						rowNum : 'all',
						//pgbuttons:false,
						pager : null,
						scroll : true,
						loadonce : false,
						multiselect : true,
						colNames : [ "ID", "流水号", "会员号", "会员姓名", "性别", "会员类型",
								"服务大类", "服务明细", "单价", "折后价", "服务人员", "服务日期" ],
						colModel : [
								{
									name : "_id",
									index : "_id"
								},
								{
									name : "xiaopiao",
									index : "xiaopiao"
								},
								{
									name : "id_2",
									index : "id_2"
								},
								{
									name : "name",
									index : "name"
								},
								{
									name : "sex",
									index : "sex"
								},
								{
									name : "usersortName",
									index : "usersortName"
								},
								{
									name : "bigsortName",
									index : "bigsortName"
								},
								{
									name : "smallsortName",
									index : "smallsortName"
								},
								{
									name : "money6",
									index : "money6",
									width : 80,
									formatter : function(cellvalue, option,
											cellobj) {
										var m = parseFloat(cellobj.money1)
												/ parseFloat(cellvalue) * 100;
										return m.toFixed(2);
									}
								}, {
									name : "money1",
									index : "money1",
									width : 80
								}, {
									name : "staffId1",
									sortable : false,
									index : "staffId1",
									formatter : function(cellvalue) {
										var ms = fillmaps.staffs;
										for (var i = 0; i < ms.length; i++) {
											if (ms[i]._id == cellvalue) {
												return ms[i].name;
											}
										}
										return "无";
									},
									width : 80
								},

								{
									name : "createTime",
									index : "createTime"
								} ]
					});
			$("#chargeTable").jqGrid('hideCol', [ "_id" ]);
		}
		function loadAll() {
			$.post("${ctxPath}/chargeback/query.do",
					$("#searchForm").formobj(), function(data) {
						$.shade.hide();
						$("#chargeTable").jqGrid('clearGridData');
						if (data) {
							for (var i = 0; i < data.length; i++) {
								var d = data[i];
								$("#chargeTable").jqGrid('addRowData', i, d);
							}
						}
					});
		}
		function loadVipUser() {
			$.post("${ctxPath}/chargeback/query.do",
					$("#searchForm").formobj(), function(data) {
						$.shade.hide();
						$("#chargeTable").jqGrid('clearGridData');
						if (data) {
							for (var i = 0; i < data.length; i++) {
								var d = data[i];
								$("#chargeTable").jqGrid('addRowData', i, d);
							}
						}
					});
		}
		function chargeBack() {
			//var table = $("#chargeTable").jqGrid("getRowData");
			var ids = $("#chargeTable").jqGrid("getGridParam", "selarrrow");//获取选中行的id信息
			if (ids == "") {
				toastr.error("请选择一条记录进行退单！");
			} else {
				layer.confirm('确定退单操作?', function(index) {
					layer.close(index);
					var selids = [];
					for (var k = 0; k < ids.length; k++) {
						var rowData = $("#chargeTable").jqGrid('getRowData',
								ids[k]);
						var rowID = rowData._id;
						selids.push(rowID);
					}
					var selectType = $("#selectType").val();

					var url = _ctxPath + "/chargeback/charge.do";
					//console.log(selids);
					$.get(url, {
						'chargeIds' : selids,
						'selectType' : selectType
					}, function(data) {
						if (data) {
							if (data.success) {
								toastr.success("操作成功");
								loadAll();
							} else {
								toastr.error(data.message);
							}

						} else {
							toastr.error("操作失败");
						}
					});
				});
			}

		}
	</script>
</body>
</html>
