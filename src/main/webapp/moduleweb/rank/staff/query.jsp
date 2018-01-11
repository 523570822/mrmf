<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/moduleweb/resources/common.jsp"%>
<html>
<head>
<title></title>
<style>
.calendar{text-align:center;}
</style>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content  animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content">
					
						<form id="searchForm" method="get" class="form-horizontal">
						<div class="form-group">
							<label class="col-sm-1 control-label">城市</label>
								<div class="col-sm-2">
									<select class="form-control" id="city" name="city">
										<option value="">请选择</option>
										<c:forEach items="${ffcitys}" var="city">
										<option value="${city._id }">${city.name }</option>
										</c:forEach>
									</select>
								</div>
								<label class="col-sm-1 control-label">区域</label>
								<div class="col-sm-2">
									<select class="form-control" id="district" name="district">
										<option value="">请选择</option>
									</select>
								</div>
								<label class="col-sm-1 control-label">商圈</label>
								<div class="col-sm-2">
									<select class="form-control" id="region" name="region">
										<option value="">请选择</option>
									</select>
								</div>
								
								
							</div>
							<div class="form-group">
							
							<label class="col-sm-1 control-label"></label>
								<div class="col-sm-3">
									<input id="condition" name="condition" type="text"
										class="form-control" maxlength="20"
										placeholder="姓名、昵称、电话进行查询">
								</div>
								<div class="col-sm-2">
									<input id="startTime" name="startTime"
										class="laydate-icon form-control layer-date"
										placeholder="起始日期" data-mask="9999-99-99"
										laydate="{format : 'YYYY-MM-DD',min : '1938-06-16 23:59:59'}">
								</div>
								<div class="col-sm-2">
									<input id="endTime" name="endTime"
										class="laydate-icon form-control layer-date"
										placeholder="结束日期" data-mask="9999-99-99"
										laydate="{format : 'YYYY-MM-DD',min : '1938-06-16 23:59:59'}">
								</div>
								<div class="col-sm-2">
								<input id="limit" name="limit" type="number"
										class="form-control" min="1" value="100"
										placeholder="记录数">
										</div>
								<div class="col-sm-2">
									<button id="search" class="btn btn-primary" type="submit">查询</button>
									<button class="btn btn-danger" id="export" name="export"
										type="button">
										<strong>导出</strong>
									</button>
								</div>
								
							</div>
							
						</form>
					
						<div class="jqGrid_wrapper">
							<table id="staffRankTable"></table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		var manageType,addressComponents;
		$()

		.ready(
						function() {
							$('#export')
									.click(
											function() {
												var param = $("#searchForm")
														.formobj();
												var o = {};
												o.city = param["city"];
												o.district = param["district"];
												o.region = param["region"];
												o.condition = param["condition"];
												o.startTime = param["startTime"];
												o.endTime = param["endTime"];
												o.limit=param["limit"];

												var parStr = jQuery.param(o);
												//console.log(param);
												window.location.href = _ctxPath
														+ "/rank/exportStaffRank.do?"
														+ parStr;
												//$("#searchForm").attr("action", _ctxPath + "/weixin/sys/user/exportUser.do").submit();
											});

							queryStaffRank();
							$("#newBumen").click(
									function() {
										document.location.href = _ctxPath
												+ "/kucun/leibie/toUpsert.do";
                        });

							$("#searchForm").submit(function() {
								var regNum = /^[1-9][0-9]*$/;
								var num = $("#limit").val().trim();
								if (num != "") {
									if (!regNum.test(num)) {
										toastr.error("记录数的格式不正确");
										return false;
									}
								}
								//$("#staffRankTable").reloadGrid({
								//	postData : $("#searchForm").formobj()
								//});
								queryStaffRank();
								return false;
							});
							$("#staffRankTable")
									.grid(
											{
												//url : _ctxPath
												//		+ "/rank/staff/query.do",
												postData : $("#searchForm")
														.formobj(),
												rownumbers : true,
												pager : null,

												datatype : "local",
												colNames : [ "操作", "技师姓名",
														"店铺名称", "消费金额",
														"30以上订单数量" ],
												colModel : [
														{
															name : "_id",
															index : "_id",
															align : "center",
															formatter : function(
																	cellvalue,
																	options,
																	rowObject) {
																/*var v = "<a href='javascript:void(0);' onclick='sendMessage(\""
																		+ cellvalue
																		+ "\")'>推送消息</a>";*/
																var v = "<a href=\"javascript:toSendMessage("
																		+ "'"
																		+ rowObject._id
																		+ "')\">奖励"
																		+ "</a>&nbsp;&nbsp;";

																return v;
															},
															sortable : false
														},
														{
															name : "staffName",
															index : "staffName",
															align : "center",
															sortable : false
														},
														{
															name : "organName",
															index : "organName",
															align : "center",
															sortable : false
														},
														{
															name : "totalPrice",
															index : "totalPrice",
															align : "center",
															sortable : false
														}, {
															name : "orderNum",
															index : "orderNum",
															align : "center",
															sortable : false
														} ],
												gridComplete : function() {
													$(".ui-jqgrid-sortable")
															.css("text-align",
																	"center");
												}
											});

							$("#city")
									.change(
											function() {
												var t = $("#city")[0];
												var v = t.options[t.selectedIndex];
												if (v.value) {
													$
															.post(
																	'${ctxPath}/weixin/s/queryDistrict.do',
																	{
																		cityId : v.value
																	},
																	function(
																			data,
																			status) {
																		var c = $("#district")[0], ds = [];
																		c.options.length = 1;
																		for (var i = 0; i < data.length; i++) {
																			var d = data[i];
																			var option = new Option(
																					d.name,
																					d.name);
																			option.value = d._id;
																			c.options[c.options.length] = option;
																			$(c)
																					.trigger(
																							'change');
																		}
																	});
												} else {
													$("#district")[0].options.length = 1;
												}
											});

							$("#district")
									.change(
											function() {
												var t = $("#district")[0];
												var v = t.options[t.selectedIndex];
												if (v.value) {
													$
															.post(
																	'${ctxPath}/weixin/s/queryRegion.do',
																	{
																		districtId : v.value
																	},
																	function(
																			data,
																			status) {
																		var c = $("#region")[0];
																		c.options.length = 1;
																		for (var i = 0; i < data.length; i++) {
																			var d = data[i];
																			var option = new Option(
																					d.name,
																					d.name);
																			option.value = d._id;
																			c.options[c.options.length] = option;
																		}
																	});
												} else {
													$("#region")[0].options.length = 1;
												}
											});

						});

		function toSendMessage(staffId) {
			layer
					.open({
						type : 1,
						skin : 'layui-layer-rim', //加上边框
						area : [ '420px', '240px' ], //宽高
						content : '<form>'
								+ '<div class="form-group"><input type="hidden" id="staffId" name="staffId" value="' + staffId + '"><label class="col-sm-3 control-label" style="text-align: right;">奖励金额：</label>'
								+ '<div class="col-sm-4"><input id="price" name="price" type="number" min="0" class="form-control" value=""></div>'
								+ '<div class="col-sm-3"><button id="search" class="btn btn-primary" type="button" onclick="sendMessage()">确定</button></div>'
								+ '</div></from>'
					});
		}
		function sendMessage() {
			var staffId = $("#staffId").val();
			var price = $("#price").val();
			var regex = /^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/;
			if (!regex.test(price)) {
				toastr.error("金额格式不正确");
				return;
			}
			var url = _ctxPath + "/rank/staff/recharge.do";
			$.post(url, {
				'staffId' : staffId,
				'price' : price
			}, function(data) {
				if (data) {
					if (data.success) {
						layer.closeAll();
						toastr.success("操作成功");
					} else {
						toastr.error(data.message);
					}
					//$("#leibieTable").trigger("reloadGrid");
				} else {
					toastr.error("操作失败");
				}
			});
		}
		function queryStaffRank() {
			var obj = $("#searchForm").formobj();
			$.post("${ctxPath}/rank/staff/query.do", obj,
					function(data, status) {
						$("#staffRankTable").jqGrid('clearGridData');
						for (var i = 0; i <= data.length; i++) {
							$("#staffRankTable").jqGrid('addRowData', i + 1,
									data[i]);
						}

					});
		}
	</script>
</body>
</html>
