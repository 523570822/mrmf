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
								href="${ctxPath}/weixin/s/toQuery.do?type=3&cityId=${ffregion.cityId}&districtId=${ffregion.districtId}"
								class="btn-link"> <i class="fa fa-angle-double-left"></i>返回
							</a>
							<c:if test="${ffregion._id == null}">
								新建商圈
							</c:if>
							<c:if test="${ffregion._id != null}">修改商圈
							</c:if>
						</h5>
					</div>
					<div class="ibox-content">
						<c:if test="${returnStatus.status == 0}">
							<div class="alert alert-danger">
								<i class="fa fa-exclamation-triangle"></i>${returnStatus.message}</div>
						</c:if>
						<form id="region" method="post"
							action="${ctxPath}/weixin/s/upsertRegion.do"
							class="form-horizontal">
							<input type="hidden" id="_id" name="_id"><input
								type="hidden" id="type" name="type"><input type="hidden"
								id="cityId" name="cityId"> <input type="hidden"
								id=districtId name="districtId"> <input type="hidden"
								id="gpsPoint.longitude" name="gpsPoint.longitude"
								class="gpsPoint"> <input type="hidden"
								id="gpsPoint.latitude" name="gpsPoint.latitude" class="gpsPoint">
							<div class="form-group">
								<label class="col-sm-2 control-label">名称</label>
								<div class="col-sm-10">
									<input id="name" name="name" type="text" class="form-control"
										minlength="2" maxlength="50" required>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">定位</label>
								<div class="col-sm-10">
									<button id="chooseMap" type="button" class="btn btn-primary">地图定位</button>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">排序编码</label>
								<div class="col-sm-10">
									<input id="order" name="order" type="text" class="form-control"
										min="0" max="10000000" value="0" required><span
										class="help-block m-b-none">商圈显示排序编码，将从小到大排列。</span>
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
							$("#region")
									.validate(
											{
												submitHandler : function(form) {
													if (!$(
															"#gpsPoint\\.latitude")
															.val()
															|| !$(
																	"#gpsPoint\\.longitude")
																	.val()) {
														toastr
																.warning("请进行地图定位");
														$("#chooseMap").click();
														return;
													}
													$.shade.show();
													form.submit();
												}
											});

							$("#chooseMap")
									.click(
											function() {
												var url = _ctxPath
														+ "/organ/toMap.do?TB_iframe=true&lat="
														+ $(
																"#gpsPoint\\.latitude")
																.val()
														+ "&lng="
														+ $(
																"#gpsPoint\\.longitude")
																.val();
												layerId = layer
														.open({
															type : 2,
															title : '选择坐标',
															shadeClose : false, //点击遮罩关闭层
															area : [ '640px',
																	'450px' ],
															content : url
														});
											});

							$("#cancelBtn")
									.click(
											function() {
												document.location.href = _ctxPath
														+ "/weixin/s/toQuery.do?type=3&cityId=${ffregion.cityId}&districtId=${ffregion.districtId}";
											});

						});
		// 地图选择回调
		function selectAddress(obj) {
			$("#gpsPoint\\.latitude").val(obj.point.lat);
			$("#gpsPoint\\.longitude").val(obj.point.lng);
			layer.close(layerId);
		}
	</script>
</body>
</html>
