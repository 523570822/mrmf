<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/moduleweb/resources/common.jsp"%>
<c:set var="organId"
	value="${param.organId == null?sessionScope.organId:param.organId}" />
<html>
<head>
<title></title>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content">
						<form id="myForm" method="post" class="form-horizontal">
							<input type="hidden" id="organId" name="organId"
								value="${organId }"> <input type="hidden"
								id="userpartId" name="userpartId" value="${param.userpartId }">
							<input type="hidden" id="delete_flag" name="delete_flag"
								value="false">
							<div class="form-group">
								<label class="col-sm-2 control-label">物品</label>
								<div class="col-sm-2">
									<input type="text" class="form-control suggest" id="wupinId"
										name="wupinId"
										suggest="{data :fillmaps.leibies,searchFields : [ '_id' ],keyField : 'mingcheng',style2:true}">
								</div>
								<label class="col-sm-2 control-label">用量</label>
								<div class="col-sm-2">
									<input id="yongliang" name="yongliang" type="number"
										class="form-control" min="1" maxlength="50">
								</div>
								<div class="col-sm-4">
									<button id="btnNew" class="btn btn-success" type="button">重置</button>
									<button id="btnSave" class="btn btn-info" type="submit">保存</button>
									<button id="btnBack" class="btn btn-outline btn-danger"
										type="button">返回</button>
								</div>
							</div>
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
		var userpartId = "${param.userpartId}";
		$()
				.ready(
						function() {
							$("#btnBack")
									.click(
											function() { // 返回
												if (parent.takeChanpinLayerId) {
													parent.layer
															.close(parent.takeChanpinLayerId);
												} else {
													parent.layer.closeAll();
												}
											});
							$("#btnNew").click(function() { // 重置
								$("#myForm").clearform();
							});

							$("#myForm")
									.submit(
											function() { // 保存
												var wupinId = $("#wupinId")
														.val();
												if (wupinId === "") {
													toastr.warning("请选择物品");
													$("#sg_wupinId").focus();
													return false;
												}
												var yongliang = $("#yongliang")
														.val();
												if (yongliang === "") {
													toastr.warning("请输入产品用量");
													$("#yongliang").focus();
													return false;
												}
												if (wupinId)
													$
															.post(
																	"${ctxPath}/usewupin/upsert.do",
																	$("#myForm")
																			.formobj(),
																	function(
																			data) {
																		if (data) {
																			if (data.success) {
																				toastr
																						.success("操作成功");
																				if (userpartId === "") {
																					userpartId = data.data.userpartId;
																					$(
																							"#userpartId")
																							.val(
																									userpartId);
																					parent
																							.$(
																									"#userpartId")
																							.val(
																									userpartId);
																				}
																				reloadTable();
																				$(
																						"#btnNew")
																						.click();
																			} else {
																				toastr
																						.error(data.message);
																			}
																		} else {
																			toastr
																					.error("操作失败");
																		}
																	});
												return false;
											});
							$("#myTable")
									.grid(
											{
												pager : null,
												shrinkToFit : false,
												datatype : "local",
												colNames : [ "光临日期", "产品名称",
														"用量", "价格", "操作" ],
												colModel : [
														{
															name : "createTime",
															index : "createTime"
														},
														{
															name : "wupinName",
															index : "wupinName"
														},
														{
															name : "yongliang",
															index : "yongliang"
														},
														{
															name : "money1",
															index : "money1"
														},
														{
															name : "_id",
															index : "_id",
															align : "center",
															formatter : function(
																	cellvalue,
																	options,
																	rowObject) {
																var v = "<a href='javascript:void(0);' onclick='removeUserwupin(\""
																		+ cellvalue
																		+ "\")'>删除</a>";

																return v;
															}
														} ]
											});
							reloadTable();

						});
		function reloadTable() {
			if (userpartId !== "") {
				$.get("${ctxPath}/usewupin/query.do", {
					userpartId : userpartId
				}, function(data) {
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
		}
		function removeUserwupin(usewupinId) {
			layer.confirm('确定要删除产品使用?', function(index) {
				layer.close(index);
				var url = _ctxPath + "/usewupin/remove/" + usewupinId + ".do";
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
