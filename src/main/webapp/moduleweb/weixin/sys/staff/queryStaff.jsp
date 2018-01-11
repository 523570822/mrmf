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
							<div class="row">
								<div class="col-sm-12">
									<div class="form-group">
										<label class="col-sm-2 control-label">技师手机号：</label>
										<div class="col-sm-3">
											<input id="regex:phone" name="regex:phone" type="text"
												class="form-control">
										</div>
										<!-- <label class="col-sm-2 control-label">技师状态：</label>
										<div class="col-sm-3">
											<select type="select" id="regex:status" name="regex:status" class="form-control">
												   <option value="">全部</option>
												   <option value="0">无效</option>
												   <option value="1">有效</option>
											   </select>
										</div>
										 -->
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-12">
									<div class="form-group">
										<label class="col-sm-2 control-label">所属店铺：</label>
										<div class="col-sm-3">
											<input id="regex:organ" name="regex:organ" type="text"
												class="form-control">
										</div>
										<label class="col-sm-2 control-label">技师姓名：</label>
										<div class="col-sm-3">
											<input id="regex:name" name="regex:name" type="text"
												class="form-control">
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-12">
									<div class="form-group">
										<label class="col-sm-2 control-label">性别：</label>
										<div class="col-sm-3">
											<select type="select" id="sex" name="sex"
												class="form-control">
												<option value="">全部</option>
												<option value="男">男</option>
												<option value="女">女</option>
											</select>
										</div>
										<label class="col-sm-2 control-label">工作年限：</label>
										<div class="col-sm-1">
											<input id="gte:workYears|integer"
												name="gte:workYears|integer" type="text"
												class="form-control">
										</div>
										<div class="col-sm-1" style="text-align: center;">至</div>
										<div class="col-sm-1">
											<input id="lte:workYears|integer"
												name="lte:workYears|integer" type="text"
												class="form-control">
										</div>


									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-12">
									<div class="form-group">
										<div class="col-sm-2"></div>
										<div class="col-sm-2">
											<button id="search" class="btn btn-primary" type="submit">查询</button>
									
										</div>
										<div class="col-sm-2">
											
											<button class="btn btn-danger" id="export" name="export"
												type="button">
												<strong>导出</strong>
											</button>
										</div>
										<!-- <div class="col-sm-2">
											<button id="setUser" class="btn btn-outline btn-primary"
												type="button">启用</button>
										</div>
										<div class="col-sm-2">
											<button id="delUser" class="btn btn-outline btn-danger"
												type="button">禁用</button>
										</div> -->
									</div>
								</div>
							</div>
						</form>
						<div class="jqGrid_wrapper">
							<table id="userTable"></table>
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
							$('#export').click(function() {
								var param = $("#searchForm")
														.formobj();
								var o={};
								o.phone = param["regex:phone"];
								o.organ = param["regex:organ"];
								o.name = param["regex:name"];
								o.sex = param["sex"];
								
								o.startYear = param["gte:workYears|integer"];
								o.endYear = param["lte:workYears|integer"];
								var parStr = jQuery.param(o);
								
								//console.log(param);
								window.location.href=_ctxPath + "/weixin/sys/user/exportStaffManagement.do?"+parStr;
								//$("#searchForm").attr("action", _ctxPath + "/weixin/sys/user/exportUser.do").submit();
							});
							$("#setUser")
									.click(
											function() {
												if (!confirm("启用后，该用户将处于有效状态，是否启用？")) {
													return false;
												}
												var ids = $("#userTable")
														.jqGrid("getGridParam",
																"selarrrow");//获取选中行的id信息
												if (ids == "") {
													alert("请选择一条记录进行启用！");
												} else {
													var _ids = [];
													for (var k = 0; k < ids.length; k++) {
														var rowData = $(
																"#userTable")
																.jqGrid(
																		'getRowData',
																		ids[k]);
														var rowName = rowData.id;
														_ids.push(rowName);
													}

													$
															.post(
																	_ctxPath
																			+ "/weixin/sys/user/setUser.do?userIds="
																			+ _ids,
																	function(
																			data) {
																		$(
																				"#msg")
																				.html(
																						data);
																		$(
																				"#userTable")
																				.reloadGrid(
																						{
																							postData : $(
																									"#searchForm")
																									.formobj()
																						});
																		return false;
																	});
												}
											});
							$("#delUser")
									.click(
											function() {
												if (!confirm("禁用后，该用户将处于无效状态，是否启用？")) {
													return false;
												}
												var ids = $("#userTable")
														.jqGrid("getGridParam",
																"selarrrow");//获取选中行的id信息
												if (ids == "") {
													alert("请选择一条记录进行启用！");
												} else {
													var _ids = [];
													for (var k = 0; k < ids.length; k++) {
														var rowData = $(
																"#userTable")
																.jqGrid(
																		'getRowData',
																		ids[k]);
														var rowName = rowData.id;
														_ids.push(rowName);
													}

													$
															.post(
																	_ctxPath
																			+ "/weixin/sys/user/delUser.do?userIds="
																			+ _ids,
																	function(
																			data) {
																		$(
																				"#msg")
																				.html(
																						data);
																		$(
																				"#userTable")
																				.jqGrid(
																						"setGridParam",
																						{
																							postData : $(
																									"#searchForm")
																									.formobj()
																						})
																				.trigger(
																						"reloadGrid");
																		return false;
																	});
												}
											});

							$("#searchForm").submit(function() {
								$("#userTable").jqGrid("setGridParam", {
									postData : $("#searchForm").formobj()
								}).trigger("reloadGrid");
								return false;
							});
							$("#userTable")
									.grid(
											{
												url : _ctxPath
														+ "/weixin/sys/user/queryStaff.do",
												postData : $("#searchForm")
														.formobj(),
												colNames : [ "全选", "技师手机号",
														"技师姓名", "性别", "头像",
														"颜值", "工作年限", "所属店铺","加入时间",
														"操作" ],
												colModel : [
														{
															name : 'id',
															index : 'id',
															width : 0,
															hidden : true,
															hidedlg : true,
															align : "left",
															formatter : function(
																	cellvalue,
																	options,
																	rowObject) {
																var v = rowObject._id;

																return v;
															}
														},
														{
															name : "phone",
															index : "phone",
															align : "center"
														},
														{
															name : "name",
															index : "name",
															align : "center"
														},
														{
															name : "sex",
															index : "sex",
															align : "center"
														},
														{
															name : "logo",
															index : "logo",
															align : "center",
															formatter : function(
																	cellvalue,
																	options,
																	rowObject) {
																var v = "<img width=\"50px;\" height=\"50px;\" src=\"${ossImageHost}"
																		+ cellvalue
																		+ "@!avatar\">";

																return v;
															}
														},
														{
															name : "faceScore",
															index : "faceScore",
															align : "center",
															formatter : function(
																	cellvalue,
																	options,
																	rowObject) {
																var v = "<a href=\"javascript:editFaceScore("
																		+ cellvalue
																		+ ",'"
																		+ rowObject._id
																		+ "')\">"
																		+ cellvalue
																		+ "</a>&nbsp;&nbsp;";

																return v;
															}
														},
														{
															name : "workYears",
															index : "workYears",
															align : "center"
														},
														{
															name : "organName",
															index : "organName",
															align : "center"
														},{
															name : "createTime",
															index : "createTime",
															align : "center"
														},
														{
															name : "_id",
															index : "_id",
															align : "center",
															formatter : function(
																	cellvalue,
																	options,
																	rowObject) {
																var v = "<a href='${ctxPath}/weixin/sys/user/lookStaff.do?staffId="
																		+ cellvalue
																		+ "'>详情</a>&nbsp;&nbsp;";

																return v;
															}
														} ],
												gridComplete : function() {
													$(".ui-jqgrid-sortable")
															.css("text-align",
																	"center");
												}
											});

						});

		function editFaceScore(faceScore, staffId) {
			layer
					.open({
						type : 1,
						skin : 'layui-layer-rim', //加上边框
						area : [ '420px', '240px' ], //宽高
						content : '<form>'
								+ '<div class="form-group"><input type="hidden" id="staffId" name="staffId" value="' + staffId + '"><label class="col-sm-3 control-label" style="text-align: right;">颜值：</label>'
								+ '<div class="col-sm-3"><input id="faceScore" name="faceScore" type="text" class="form-control" value="' + faceScore + '"></div>'
								+ '<div class="col-sm-3"><button id="search" class="btn btn-primary" type="button" onclick="saveFaceScore()">保存</button></div>'
								+ '</div></from>'
					});
		}

		function saveFaceScore() {
			var staffId = $("#staffId").val();
			var faceScore = $("#faceScore").val();
			$.post(_ctxPath + "/weixin/sys/user/editStaffFaceScore.do", {
				'staffId' : staffId,
				'faceScore' : faceScore
			}, function(data) {
				if (data == 'true') {
					layer.closeAll();
					$("#userTable").jqGrid("setGridParam", {
						postData : $("#searchForm").formobj()
					}).trigger("reloadGrid");
					return false;
				}
			}, "text");

		}
	</script>
</body>
</html>
