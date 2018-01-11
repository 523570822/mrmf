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
						<h5>店铺业务参数设置</h5>
					</div>
					<div class="ibox-content">
						<c:if test="${returnStatus.status == 0}">
							<div class="alert alert-danger">
								<i class="fa fa-exclamation-triangle"></i>${returnStatus.message}</div>
						</c:if>
						<form id="setting" method="post"
							action="${ctxPath}/organ/upsertSetting.do"
							class="form-horizontal">
							<input type="hidden" id="_id" name="_id"> <input
								type="hidden" id="organId" name="organId">
							<div class="form-group">
								<label class="col-sm-4 control-label">是否允许提现</label>
								<div class="col-sm-6">
									<input id="canTixian" name="canTixian" type="checkbox"
										class="switcher" /><span class="help-block m-b-none">控制会员管理界面是否显示提现“提取现金”按钮。</span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">是否显示提成</label>
								<div class="col-sm-6">
									<input id="displayTicheng" name="displayTicheng"
										type="checkbox" class="switcher" /><span
										class="help-block m-b-none">控制是否显示员工提成。</span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">提成扣减成本</label>
								<div class="col-sm-6">
									<input id="tichengDeductCost" name="tichengDeductCost"
										type="checkbox" class="switcher" /><span
										class="help-block m-b-none">控制是否扣减成本之后再计算提成。</span>
								</div>
							</div>
							<div class="hr-line-dashed"></div>
							<div class="form-group">
								<label class="col-sm-4 control-label">打印机名称</label>
								<div class="col-sm-6">
									<input id="printerName" class="form-control" name="printerName"
										type="text" /><span class="help-block m-b-none">设置小票打印机名称，[控制面板]-->[设备和打印机]中，打印机名称，空表示使用系统默认打印机。</span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">连续打印次数</label>
								<div class="col-sm-6">
									<select class="form-control" id="printCount" name="printCount">
										<option value="1">1次</option>
										<option value="2">2次</option>
										<option value="3">3次</option>
									</select><span class="help-block m-b-none">打印小票时的连续打印次数（只在自动静默打印时有效）。</span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">是否显示打印预览</label>
								<div class="col-sm-6">
									<input id="printPreview" name="printPreview" type="checkbox"
										class="switcher" /><span class="help-block m-b-none">打印小票时是否显示打印预览。</span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">打印备注</label>
								<div class="col-sm-6">
									<textarea id="printNote" name="printNote" class="form-control"
										placeholder="" rows="4"></textarea>
									<span class="help-block m-b-none">打印小票结尾显示的一段话。</span>
								</div>
							</div>
							<div class="hr-line-dashed"></div>
							<div class="form-group">
								<label class="col-sm-4 control-label">入库是否需要审核</label>
								<div class="col-sm-6">
									<input id="rukuShenhe" name="rukuShenhe" type="checkbox"
										class="switcher" /><span class="help-block m-b-none">控制入库是否需要审核过程。</span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">出库是否需要审核</label>
								<div class="col-sm-6">
									<input id="chukuShenhe" name="chukuShenhe" type="checkbox"
										class="switcher" /><span class="help-block m-b-none">控制出库是否需要审核过程。</span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">产品使用是否扣减库存</label>
								<div class="col-sm-6">
									<input id="chanpinDeductKucun" name="chanpinDeductKucun"
										type="checkbox" class="switcher" /><span
										class="help-block m-b-none">控制产品使用是否扣减相应库存。</span>
								</div>
							</div>
							<div class="hr-line-dashed"></div>
							<div class="form-group">
								<label class="col-sm-4 control-label">电子卡号长度</label>
								<div class="col-sm-6">
									<input id="elecCardLength" class="form-control"
										name="elecCardLength" type="text" min="3" max="30" /><span
										class="help-block m-b-none">电子卡卡号长度设置，从1开始自增，前面不足位数补“0”。</span>
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
			$("#setting").validate({
				submitHandler : function(form) {
					$.shade.show();
					form.submit();
				}
			});
		});
	</script>
</body>
</html>
