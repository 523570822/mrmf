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
							<a href="${ctxPath}/account/toQuery.do" class="btn-link"> <i
								class="fa fa-angle-double-left"></i>返回
							</a>
							<c:if test="${fforgan._id == null}">
								新建账号
							</c:if>
							<c:if test="${fforgan._id != null}">修改账号
							</c:if>
						</h5>
					</div>
					<div class="ibox-content">
						<c:if test="${returnStatus.status == 0}">
							<div class="alert alert-danger">
								<i class="fa fa-exclamation-triangle"></i>${returnStatus.message}</div>
						</c:if>
						<form id="account" method="post"
							action="${ctxPath}/account2/upsert.do" class="form-horizontal">
							<input type="hidden" id="_id" name="_id"><input
								type="hidden" id="accountType" name="accountType" value="admin">
							<input type="hidden" id="status" name="status" value="1"><input
								type="hidden" id="entityID" name="entityID" value="0">
							<div class="form-group">
								<label class="col-sm-2 control-label">账号名称</label>
								<div class="col-sm-10">
									<input id="accountName" name="accountName" type="text"
										class="form-control" minlength="2" maxlength="50" required>
								</div>
							</div>
							<div id="passwordDiv" class="form-group">
								<label class="col-sm-2 control-label">密码</label>
								<div class="col-sm-10">
									<input id="accountPwd" name="accountPwd" type="password"
										class="form-control" minlength="6" maxlength="30" required>
									<c:if test="${ffaccount._id != null}">
										<span class="help-block m-b-none">如果不需要修改口令，请保持为空。</span>
									</c:if>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">角色</label>
								<div class="col-sm-10">
									<c:if test="${fn:length(roleList) == 0}">
										<span class="help-block m-b-none">没有可选角色</span>
									</c:if>
									<c:if test="${fn:length(roleList) > 0}">
										<select class="form-control" id="roleIds" name="roleIds"
											size="5" multiple>
											<c:forEach items="${roleList}" var="role">
												<option value="${role._id}">${role.name}</option>
											</c:forEach>
										</select>
									</c:if>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">城市</label>
								<div class="col-sm-10">
									<c:if test="${fn:length(cityList) == 0}">
										<span class="help-block m-b-none">没有可选城市</span>
									</c:if>
									<c:if test="${fn:length(cityList) > 0}">
										<select class="form-control" id="cityList" name="cityList"
											size="5" multiple>
											<c:forEach items="${cityList}" var="city">
												<option value="${city.name}">${city.name}</option>
											</c:forEach>
										</select>
										<span class="help-block m-b-none">管理员可管理的城市，不选则默认全部。按住ctr键可多选。</span>
									</c:if>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">区域</label>
								<div class="col-sm-10">
									<c:if test="${fn:length(districtList) == 0}">
										<span class="help-block m-b-none">没有可选区域</span>
									</c:if>
									<c:if test="${fn:length(districtList) > 0}">
										<select class="form-control" id="districtList"
											name="districtList" size="5" multiple>
											<c:forEach items="${districtList}" var="district">
												<option value="${district.name}">${district.name}</option>
											</c:forEach>
										</select>
										<span class="help-block m-b-none">管理员可管理的区域，不选则默认全部。按住ctr键可多选。</span>
									</c:if>
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
							if (fillmaps && fillmaps.account) {
								var account = fillmaps.account;
								if (account.functionIds) {
									functionIds = account.functionIds;
								}
								if (account.functionNames) {
									functionNames = account.functionNames;
								}

								if (fillmaps.account._id) {
									$("#accountName").disable();
									$("#accountPwd").attr("required", null);
									$("#accountPwd").val(""); // 默认清空口令
								}
							}

							$("#account")
									.validate(
											{
												submitHandler : function(form) {
													$.shade.show();
													$("#account").enable();
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
														$("#account")
																.append(dd);
													}
													form.submit();
												}
											});

							$("#cancelBtn")
									.click(
											function() {
												document.location.href = _ctxPath
														+ "/account2/toQuery.do?parentId=${param.parentId}&organId=${param.organId}";
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
