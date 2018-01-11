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
							<a href="${ctxPath}/organ/toQuery.do" class="btn-link"> <i
								class="fa fa-angle-double-left"></i>返回
							</a> 设置会员卡通用机构 <small>设置在哪些分子公司和总部之间的会员卡可通用。</small>
						</h5>
					</div>
					<div class="ibox-content">
						<c:if test="${returnStatus.status == 0}">
							<div class="alert alert-danger">
								<i class="fa fa-exclamation-triangle"></i>${returnStatus.message}</div>
						</c:if>
						<form id="organ" method="post"
							action="${ctxPath}/organ/changeCardOrganIds.do"
							class="form-horizontal">
							<div class="form-group">
								<label class="col-sm-2 control-label">通用机构</label>
								<div class="col-sm-10">
									<select class="form-control" id="cardOrganIds"
										name="cardOrganIds" multiple size="4">
										<c:forEach items="${organList}" var="organ">
											<option value="${organ._id}">${organ.name}</option>
										</c:forEach>
									</select> <span class="help-block m-b-none">按住Ctrl键可进行多选；要取消选择，可按住Ctrl键，点击已选中项即可。</span>
								</div>
							</div>
							<div class="hr-line-dashed"></div>
							<div class="form-group">
								<div class="col-sm-4 col-sm-offset-2">
									<button class="btn btn-primary" type="submit">提交</button>
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

		});
	</script>
</body>
</html>
