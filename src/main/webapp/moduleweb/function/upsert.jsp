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
								href="${ctxPath}/function/toQuery.do?parentId=${param.parentId}"
								class="btn-link"> <i class="fa fa-angle-double-left"></i>返回
							</a>
							<c:if test="${fforgan._id == null}">
								新建菜单/资源
							</c:if>
							<c:if test="${fforgan._id != null}">修改菜单/资源
							</c:if>
						</h5>
					</div>
					<div class="ibox-content">
						<c:if test="${returnStatus.status == 0}">
							<div class="alert alert-danger">
								<i class="fa fa-exclamation-triangle"></i>${returnStatus.message}</div>
						</c:if>
						<form id="function" method="post"
							action="${ctxPath}/function/upsert.do" class="form-horizontal">
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
									<input id="action" name="action" type="text"
										class="form-control" maxlength="50"><span
										class="help-block m-b-none">功能地址(如:/xxx/xxx.do)，创建父级菜单节点时可为空。</span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">排序编码</label>
								<div class="col-sm-10">
									<input id="order" name="order" type="text" class="form-control"
										min="0" max="10000000" value="0" required><span
										class="help-block m-b-none">菜单/资源显示排序编码，将从小到大排列。</span>
								</div>
							</div>
							<c:if test="${param.parentId ==\"0\" }">
								<div class="form-group">
									<label class="col-sm-2 control-label">标识代码</label>
									<div class="col-sm-10">
										<input id="code" name="code" type="text" class="form-control"
											maxlength="30" required><span
											class="help-block m-b-none">用于菜单图标显示，具体请参考<a
											href="http://fortawesome.github.io/Font-Awesome/icons/"
											target="_blank">Font Awesome</a></span>
									</div>
								</div>
							</c:if>
							<div class="form-group">
								<label class="col-sm-2 control-label">是否仅总部</label>
								<div class="col-sm-10">
									<input id="isZongbuOnly" name="isZongbuOnly" type="checkbox"
										class="switcher" /><span class="help-block m-b-none">是否只有总部才可以使用此功能。</span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">是否仅子公司</label>
								<div class="col-sm-10">
									<input id="isFenbuOnly" name="isFenbuOnly" type="checkbox"
										class="switcher" /><span class="help-block m-b-none">是否只有子公司才可以使用此功能。</span>
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
							$("#function").validate({
								submitHandler : function(form) {
									var obj = $("#function").formobj();
									if (obj.isZongbuOnly && obj.isFenbuOnly) {
										toastr.warning("是否仅总部和是否仅子公司两者只能选一项");
										return;
									}
									$.shade.show();
									$("#function").enable();
									form.submit();
								}
							});

							$("#cancelBtn")
									.click(
											function() {
												document.location.href = _ctxPath
														+ "/function/toQuery.do?parentId=${param.parentId}";
											});

						});
	</script>
</body>
</html>
