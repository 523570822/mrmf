<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/moduleweb/resources/common.jsp"%>
<c:set var="organId"
	value="${param.organId == null?sessionScope.organId:param.organId}" />
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
								href="${ctxPath}/staff/toQuery.do?organId=${organId}&parentId=${param.parentId}"
								class="btn-link"> <i class="fa fa-angle-double-left"></i>返回
							</a>
							<c:if test="${ffstaff._id == null}">
								新建员工
							</c:if>
							<c:if test="${ffstaff._id != null}">修改员工 <small>修改员工基本信息。</small>
							</c:if>
						</h5>
					</div>
					<div class="ibox-content">
						<c:if test="${returnStatus.status == 0}">
							<div class="alert alert-danger">
								<i class="fa fa-exclamation-triangle"></i>${returnStatus.message}</div>
						</c:if>
						<input type="hidden" value="${oldOrganId}">
						<form id="staff" method="post" action="${ctxPath}/staff/upsert.do"
							class="form-horizontal">
							<input type="hidden" id="_id" name="_id"><input
								type="hidden" id="logo" name="logo"> <input
								type="hidden" id="parentId" name="parentId"> <input
								type="hidden" id="organId" name="organId"
								value="${param.organId}"> <input type="hidden"
								id="deleteFlag" name="deleteFlag"> <input type="hidden"
								id="createTime" name="createTime"> <input type="hidden"
								id="evaluateCount" name="evaluateCount"> <input
								type="hidden" id="gpsPoint.longitude" name="gpsPoint.longitude"
								class="gpsPoint"> <input type="hidden"
								id="gpsPoint.latitude" name="gpsPoint.latitude" class="gpsPoint">
							<div class="form-group">
								<label class="col-sm-2 control-label">姓名</label>
								<div class="col-sm-10">
									<input id="name" name="name" type="text" class="form-control"
										minlength="2" maxlength="50" required>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">助记符</label>
								<div class="col-sm-10">
									<input id="zjfCode" name="zjfCode" type="text"
										class="form-control" maxlength="30" readonly>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">性别</label>
								<div class="col-sm-10">
									<select class="form-control" id="sex" name="sex">
										<option value="">--请选择--</option>
										<option value="男">男</option>
										<option value="女">女</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">身份证号</label>
								<div class="col-sm-10">
									<input id="idcard" name="idcard" type="text" isIdCardNo="true"
										class="form-control">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">出生日期</label>
								<div class="col-sm-10">
									<input id="birthday" name="birthday"
										class="laydate-icon form-control layer-date" placeholder=""
										data-mask="9999-99-99"
										laydate="{start:'1980-01-01 00:00:00',min:'1950-01-01 00:00:00',format : 'YYYY-MM-DD'}">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">手机号</label>
								<div class="col-sm-10">
									<input id="phone" name="phone" type="text" class="form-control"
										mobile="true" required><span
										class="help-block m-b-none">此手机号将作为微信账号绑定时的验证手机号。</span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">联系地址</label>
								<div class="col-sm-10">
									<input id="home" name="home" class="form-control"
										placeholder="" maxlength="20">
								</div>
							</div>
							<div class="hr-line-dashed"></div>
							<div class="form-group">
								<label class="col-sm-2 control-label">是否开通微信服务</label>
								<div class="col-sm-10">
									<input id="weixin" name="weixin" type="checkbox"
										class="switcher" /><span class="help-block m-b-none">开通微信服务后，将可以在微信中与用户进行互动。</span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">资格证号</label>
								<div class="col-sm-10">
									<input id="certNumber" name="certNumber" class="form-control"
										placeholder="" maxlength="20">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">技师特长</label>
								<div class="col-sm-10">
									<input id="jishiTechang" name="jishiTechang"
										class="form-control" placeholder="" maxlength="30">
								</div>
							</div>
							<div class="hr-line-dashed"></div>
							<div class="form-group">
								<label class="col-sm-2 control-label">入职日期</label>
								<div class="col-sm-10">
									<input id="accessDay" name="accessDay"
										class="laydate-icon form-control layer-date" placeholder=""
										data-mask="9999-99-99"
										laydate="{min:'1950-01-01 00:00:00',format : 'YYYY-MM-DD'}">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">所属部门</label>
								<div class="col-sm-10">
									<select class="form-control" id="bumenId" name="bumenId"
										required>
										<option value="">请选择</option>
										<c:forEach items="${bumens}" var="bumen">
											<option value="${bumen._id}">${bumen.name}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">岗位</label>
								<div class="col-sm-10">
									<select class="form-control" id="dutyId" name="dutyId" required>
										<option value="">请选择</option>
										<c:forEach items="${staffposts}" var="staffpost">
											<option value="${staffpost._id}">${staffpost.name}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">是否发型师</label>
								<div class="col-sm-10">
									<input id="faxingshiFlag" name="faxingshiFlag" type="checkbox"
										class="switcher" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">是否助理</label>
								<div class="col-sm-10">
									<input id="zhuliFlag" name="zhuliFlag" type="checkbox"
										class="switcher" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">基本工资</label>
								<div class="col-sm-10">
									<input id="gongzi" name="gongzi" type="number"
										class="form-control" min="0" placeholder="" maxlength="50">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">是否已发放员工卡</label>
								<div class="col-sm-10">
									<input id="card" name="card" type="checkbox" class="switcher" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">离职日期</label>
								<div class="col-sm-10">
									<input id="leaveDate" name="leaveDate" class="form-control"
										placeholder="" maxlength="50">
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
		$()
				.ready(
						function() {
							if (fillmaps && fillmaps.staff) {

							}

							$("#idcard")
									.change(
											function() {
												var i = idCardNoUtil
														.getIdCardInfo($(
																"#idcard")
																.val());
												if (i.gender
														&& !$("#sex").val()) {
													$("#sex").val(i.gender);
												}
												if (i.birthday
														&& !$("#birthday")
																.val()) {
													$("#birthday").val(
															i.birthday);
												}
											});

							$("#leaveDate").disable();

							$("#staff").validate({
								submitHandler : function(form) {
									$.shade.show();
									$("#staff").enable();
									form.submit();
								}
							});

							$("#cancelBtn")
									.click(
											function() {
												document.location.href = _ctxPath
														+ "/staff/toQuery.do?organId=${organId}&parentId=${param.parentId}";
											});

						});
	</script>
</body>
</html>
