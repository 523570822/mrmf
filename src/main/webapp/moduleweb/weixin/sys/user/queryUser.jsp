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
										<label class="col-sm-2 control-label">用户手机号：</label>
										<div class="col-sm-3">
											<input id="regex:phone" name="regex:phone" type="text"
												class="form-control">
										</div>
										<!--<label class="col-sm-2 control-label">用户状态：</label>
										<div class="col-sm-3">
											<select type="select" id="regex:status" name="regex:status" class="form-control">
												   <option value="">全部</option>
												   <option value="0">无效</option>
												   <option value="1">有效</option>
											   </select>
										</div> -->

									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-12">
									<div class="form-group">
										<label class="col-sm-2 control-label">注册时间：</label>
										<div class="col-sm-3">
											<input id="gte:createTime|date" name="gte:createTime|date"
												type="text" class="form-control laydate-icon">
										</div>
										<label class="col-sm-2 control-label">至：</label>
										<div class="col-sm-3">
											<input id="lte:createTime|date" name="lte:createTime|date"
												type="text" class="form-control laydate-icon">
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
										<div class="col-sm-1">
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
							$('#export')
									.click(
											function() {
												var param = $("#searchForm")
														.formobj();
												var o = {};
												o.phone = param["regex:phone"];
												o.startTime = param["gte:createTime|date"];
												o.endTime = param["lte:createTime|date"];
												var parStr = jQuery.param(o);
												//console.log(param);
												window.location.href = _ctxPath
														+ "/weixin/sys/user/exportUserManagement.do?"
														+ parStr;
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

							$("#searchForm").click(function() {
								$("#userTable").reloadGrid({
									postData : $("#searchForm").formobj()
								});
								return false;
							});

							$("#userTable")
									.grid(
											{
												url : _ctxPath
														+ "/weixin/sys/user/queryUser.do",
												postData : $("#searchForm")
														.formobj(),
												colNames : [ "全选", "昵称", "手机号",
														"注册时间", "操作" ],
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
															name : "nick",
															index : "nick",
															align : "center"
														},
														{
															name : "phone",
															index : "phone",
															align : "center"
														},
														{
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
																var v = "<a href='${ctxPath}/weixin/sys/user/lookUser.do?userId="
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

		var sCreateTime = {
			elem : '#gte:createTime|date',
			format : 'YYYY-MM-DD',
			max : '2099-06-16 23:59:59', //最大日期
			istime : false,
			istoday : false,
			choose : function(datas) {
				eCreateTime.min = datas; //开始日选好后，重置结束日的最小日期
				eCreateTime.start = datas; //将结束日的初始值设定为开始日
			}
		};
		var eCreateTime = {
			elem : '#lte:createTime|date',
			format : 'YYYY-MM-DD',
			max : '2099-06-16 23:59:59',
			istime : false,
			istoday : false,
			choose : function(datas) {
				sCreateTime.max = datas; //结束日选好后，重置开始日的最大日期
			}
		};
		laydate(sCreateTime);
		laydate(eCreateTime);
	</script>
</body>
</html>
