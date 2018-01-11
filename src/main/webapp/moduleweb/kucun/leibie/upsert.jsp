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
							<a href="${ctxPath}/kucun/leibie/toQuery.do" class="btn-link"> <i
								class="fa fa-angle-double-left"></i>返回
							</a>
							<c:if test="${ffpinpai._id == null}">
								新建商品类别
							</c:if>
							<c:if test="${ffpinpai._id != null}">修改商品类别
							</c:if>
						</h5>
					</div>
					<div class="ibox-content">
						<c:if test="${returnStatus.status == 0}">
							<div class="alert alert-danger">
								<i class="fa fa-exclamation-triangle"></i>${returnStatus.message}</div>
						</c:if>
						<form id="leibie" method="post" action="${ctxPath}/kucun/leibie/upsert.do"
							class="form-horizontal">
							<input type="hidden" id="_id" name="_id">
							<input type="hidden" id="organId" name="organId">
							<div class="form-group">
								<label class="col-sm-2 control-label">产品编码</label>
								<div class="col-sm-4">
									<input id="code" name="code" type="text" class="form-control"
										minlength="2" maxlength="50" required>
								</div>
								<label class="col-sm-2 control-label">产品名称</label>
								<div class="col-sm-4">
									<input id="mingcheng" name="mingcheng" type="text" class="form-control"
										minlength="2" maxlength="50" required>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">终端销售价格</label>
								<div class="col-sm-4">
									<input id="price_xs" name="price_xs" type="text" class="form-control"
										 min="0" maxlength="50" >
								</div>
								<label class="col-sm-2 control-label">进货价格</label>
								<div class="col-sm-4">
									<input id="price" name="price" type="text" class="form-control"
										min="0" maxlength="50" >
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">出货价格</label>
								<div class="col-sm-4">
									<input id="price_ch" name="price_ch" type="text" class="form-control"
										min="0" maxlength="50" >
								</div>
								<label class="col-sm-2 control-label">部门</label>
								<div class="col-sm-4">
									<select class="form-control" id="bumen" name="bumen">
										<option value="">请选择</option>
										<c:forEach items="${bumens}" var="bumen">
											<option value="${bumen._id}">${bumen.name}</option>
										</c:forEach>
									</select><span class="help-block m-b-none"></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">员工提成（%）</label>
								<div class="col-sm-4">
									<input id="ticheng" name="ticheng" type="number" class="form-control"
										min="0"  >
								</div>
								<label class="col-sm-2 control-label">报警数量</label>
								<div class="col-sm-4">
									<input id="jingjie" name="jingjie" type="number" class="form-control"
										min="0" maxlength="50" >
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">报警天数</label>
								<div class="col-sm-4">
									<input id="jingjieday" name="jingjieday" type="number" class="form-control"
										 min="0" maxlength="50"  >
								</div>
								<label class="col-sm-2 control-label">品牌</label>
								<div class="col-sm-4">
									<select class="form-control" id="pinpai" name="pinpai" 	>
										<option value="">请选择</option>
										<c:forEach items="${pinpais}" var="pinpai">
											<option value="${pinpai._id}">${pinpai.name}</option>
										</c:forEach>
									</select><span class="help-block m-b-none"></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">净含量</label>
								<div class="col-sm-4">
									<input id="weight" name="weight" type="number" class="form-control"
										min="0" maxlength="50" >
								</div>
								<label class="col-sm-2 control-label">单位</label>
								<div class="col-sm-4">
									<select class="form-control" id="danwei" name="danwei">
										<option value="">请选择</option>
										<c:forEach items="${codes}" var="code">
											<option value="${code._id}">${code.name}</option>
										</c:forEach>
									</select><span class="help-block m-b-none"></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">规格</label>
								<div class="col-sm-4">
									<input id="guige" name="guige" type="text" class="form-control"
										minlength="2" maxlength="50" >
								</div>
								<label class="col-sm-2 control-label">组盘号</label>
								<div class="col-sm-4">
									<input id="zupanhao" name="zupanhao" type="text" class="form-control"
										minlength="2" maxlength="50" >
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
		$().ready(function() {
			$("#leibie").validate({
				submitHandler : function(form) {
					$.shade.show();
					$("#leibie").enable();
					form.submit();
				}
			});

			$("#cancelBtn").click(function() {
				document.location.href = _ctxPath + "/kucun/leibie/toQuery.do";
			});

		});
	</script>
</body>
</html>
