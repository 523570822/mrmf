<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/moduleweb/resources/common.jsp"%>
<html>
<head>
<title></title>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>
							<a
								href="${ctxPath}/role2/toQuery.do?parentId=${param.parentId}&organId=${param.organId}"
								class="btn-link"> <i class="fa fa-angle-double-left"></i>返回
							</a>
							<c:if test="${ffrole._id == null}">
								新建角色
							</c:if>
							<c:if test="${ffrole._id != null}">修改角色
							</c:if>
						</h5>
					</div>
					<div class="ibox-content">
						<c:if test="${returnStatus.status == 0}">
							<div class="alert alert-danger">
								<i class="fa fa-exclamation-triangle"></i>${returnStatus.message}</div>
						</c:if>
						<form id="role" method="post" action="${ctxPath}/role2/upsert.do"
							class="form-horizontal">
							<input type="hidden" id="_id" name="_id"><input
								type="hidden" id="parentId" name="parentId"
								value="${param.parentId}">
							<div class="form-group">
								<label class="col-sm-2 control-label">名称</label>
								<div class="col-sm-10">
									<input id="name" name="name" type="text" class="form-control"
										minlength="2" maxlength="50" required>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">功能地址</label>
								<div class="col-sm-10">
									<div class="input-group">
										<input id="functionNames" name="functionNames" type="text"
											class="form-control" disabled> <span
											class="input-group-btn">
											<button id="chooseFunction" type="button"
												class="btn btn-primary">选择</button>
										</span>
									</div>
									<span class="help-block m-b-none">选择角色所关联的菜单/资源。</span>
								</div>
							</div>
							<div class="hr-line-dashed"></div>
							<div class="form-group">
								<div class="col-sm-4 col-sm-offset-2">
									<button class="btn btn-primary" type="submit">提交</button>
									<button id="cancelBtn" class="btn btn-white" type="button">取消</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		var layerId;
		$()
				.ready(
						function() {
							var functionIds = [], functionNames = [];
							if (fillmaps && fillmaps.role) {
								var role = fillmaps.role;
								if (role.functionIds) {
									functionIds = role.functionIds;
								}
								if (role.functionNames) {
									functionNames = role.functionNames;
								}
							}

							$("#role")
									.validate(
											{
												submitHandler : function(form) {
													$.shade.show();
													$("#role").enable();
													$(".functionIds").remove();
													// 生成所在部门提交域
													for (var i = 0; i < functionIds.length; i++) {
														var dd = $(
																"<input type='hidden'>")
																.attr(
																		"name",
																		"functionIds["
																				+ i
																				+ "]")
																.addClass(
																		"functionIds")
																.val(
																		functionIds[i]);
														$("#role").append(dd);
													}
													form.submit();
												}
											});

							$("#cancelBtn")
									.click(
											function() {
												document.location.href = _ctxPath
														+ "/role2/toQuery.do?parentId=${param.parentId}&organId=${param.organId}";
											});

							$("#chooseFunction").click(function() {
								$("#functionSelectDiv").show();
								layer.open({
									type : 1,
									title : "选择部门",
									area : [ '250px', '300px' ],
									shadeClose : true,
									closeBtn : 1,
									content : $("#functionSelectDiv")
								});
							});

							$("#functionSelectDiv").hide();

							var selectedRowId;
							$("#function").tree({
								onClick : clk,
								idKey : "_id",
								pIdKey : "parentId",
								nameKey : "name",
								selectedMulti : true,
								nodeHandler : function(n) {
									if (n._id == "0") // 展开根节点
										n.open = true;
								}
							});
							$("#functionSelectDiv").hide();

							// 选择目前选定的节点
							var treeObj = $.fn.zTree.getZTreeObj("function");
							for (var i = 0; i < functionIds.length; i++) {
								if (functionIds[i] != "0") {
									var node = treeObj.getNodeByParam("_id",
											functionIds[i]);
									if (node) {
										treeObj.selectNode(node, true);
									}
								}
							}

							function clk(e, treeId, node, flag) {
								if (node._id == "0") {
									return;
								}
								if (flag == 2) { // 增加
									functionNames.remove("无");
									functionIds.push(node._id);
									functionNames.push(node.name);
								} else if (flag == 0) { // 减少
									functionIds.remove(node._id);
									functionNames.remove(node.name);
								}
								if (functionNames.length == 0) {
									$("#functionNames").val("无");
								} else {
									$("#functionNames").val(functionNames);
								}
							}

						});
	</script>

	<div id="functionSelectDiv" class="treeDiv">
		<div id="function"></div>
	</div>
</body>
</html>
