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
							<c:if test="${param.back == null}">
								<a href="${ctxPath}/organ/toQuery.do?parentId=${param.parentId}"
									class="btn-link"> <i class="fa fa-angle-double-left"></i>返回
								</a>
							</c:if>
							<c:if test="${fforgan._id == null}">
								<c:if test="${param.parentId != \"0\"}">新建子公司</c:if>
								<c:if test="${param.parentId == \"0\"}">新建公司</c:if>
							</c:if>
							<c:if test="${fforgan._id != null}">修改公司 <small>修改公司基本信息，也可修改管理员登录口令。</small>
							</c:if>
						</h5>
					</div>
					<div class="ibox-content">
						<c:if test="${returnStatus.status == 0}">
							<div class="alert alert-danger">
								<i class="fa fa-exclamation-triangle"></i>${returnStatus.message}</div>
						</c:if>
						<form id="organ" method="post" action="${ctxPath}/organ/upsert.do"
							class="form-horizontal">
							<input type="hidden" id="_id" name="_id"><input
								type="hidden" id="logo" name="logo"><input type="hidden"
								id="cert" name="cert"> <input type="hidden" id="back"
								name="back"> <input type="hidden" id="parentId"
								name="parentId" value="${param.parentId}"> <input
								type="hidden" id="createTime" name="createTime"> <input
								type="hidden" id="evaluateCount" name="evaluateCount"> <input
								type="hidden" id="valid" name="valid"><input
								type="hidden" id="ips" name="ips"> <input type="hidden"
								id="gpsPoint.longitude" name="gpsPoint.longitude"
								class="gpsPoint"> <input type="hidden"
								id="gpsPoint.latitude" name="gpsPoint.latitude" class="gpsPoint">
								<input type="hidden" name="organPositionState" id="organPositionState"/>
							<div class="form-group">
								<label class="col-sm-2 control-label">名称</label>
								<div class="col-sm-10">
									<input id="name" name="name" type="text" class="form-control"
										minlength="2" maxlength="50" required>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">简称</label>
								<div class="col-sm-10">
									<input id="abname" name="abname" type="text"
										class="form-control" maxlength="30">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">LOGO</label>
								<div class="col-sm-10">
									<div style="width: 120px; height: 120px;" class="f-left">
										<img id="logoimg" src="${ossImageHost}6021824470630641894.jpg"
											style="width: 100%; height: 100%;" />
									</div>
									<div class="f-left" style="width: 120px; padding-top: 10px">
										<input type="button" class="btn btn-primary btn-block"
											onclick="selectImg()"
											style="width: 100%; height: 34px; padding: 0" value="选择上传图片" />
									</div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">轮播图</label>
								<div class="col-sm-10">
									<div id="attachesDiv" class="f-left"></div>
									<div class="f-left" style="width: 120px; padding-top: 10px">
										<input type="button" class="btn btn-primary btn-block"
											onclick="selectFile()"
											style="width: 100%; height: 34px; padding: 0" value="选择上传轮播图" />
									</div>
								</div>
							</div>
							<c:if test="${fforgan._id != null}">
								<div class="form-group">
									<label class="col-sm-2 control-label">二维码</label>
									<div class="col-sm-10">
										<div class="f-left">
											<img id="qrimage"
												src="${ctxPath }/organ/qr/${fforgan._id}.do" />
										</div>
										<div class="f-left" style="width: 120px; padding-top: 10px">
											<input type="button" class="btn btn-primary btn-block"
												onclick="print()"
												style="width: 100%; height: 34px; padding: 0" value="打印" />
										</div>
									</div>
								</div>
							</c:if>
							<div class="form-group">
								<label class="col-sm-2 control-label">营业执照</label>
								<div class="col-sm-10">
									<div style="width: 200px; height: 150px;" class="f-left">
										<img id="certimg" src="" onclick="clickCert(this);"
											style="width: 100%; height: 100%;" />
									</div>
									<div class="f-left" style="width: 120px; padding-top: 10px">
										<input type="button" class="btn btn-primary btn-block"
											onclick="selectImg2()"
											style="width: 100%; height: 34px; padding: 0" value="选择上传图片"
											<c:if test="${sessionScope.isOrganAdmin}">disabled</c:if> />
									</div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">联系人</label>
								<div class="col-sm-10">
									<input id="contactMan" name="contactMan" type="text"
										class="form-control" maxlength="30">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">手机号</label>
								<div class="col-sm-10">
									<input id="phone" name="phone" type="text" class="form-control"
										minlength="2" maxlength="30" mobile="true" required><span
										class="help-block m-b-none">此手机号将作为微信管理员账号绑定时的验证手机号。</span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">保证金</label>
								<div class="col-sm-10">
									<input id="deposit" name="deposit" type="digits"
										class="form-control" min="0" value="0" required
										<c:if test="${sessionScope.isOrganAdmin}">disabled</c:if>>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">座机号码</label>
								<div class="col-sm-10">
									<input id="tel" name="tel" type="text" class="form-control"
										maxlength="30">
								</div>
							</div>
							<div class="hr-line-dashed"></div>
							<div class="form-group">
								<label class="col-sm-2 control-label">银行账户名称</label>
								<div class="col-sm-10">
									<input id="bankAccountName" name="bankAccountName" type="text"
										class="form-control" minlength="2" maxlength="30">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">银行账号</label>
								<div class="col-sm-10">
									<input id="bankAccount" name="bankAccount" type="text"
										class="form-control" minlength="2" maxlength="30">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">开户银行</label>
								<div class="col-sm-10">
									<input id="bankKaihu" name="bankKaihu" type="text"
										class="form-control" minlength="2" maxlength="30">
								</div>
							</div>
							<div class="hr-line-dashed"></div>
							<div class="form-group">
								<label class="col-sm-2 control-label">负责人</label>
								<div class="col-sm-10">
									<input id="master" name="master" type="text"
										class="form-control" minlength="2" maxlength="30">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">邮编</label>
								<div class="col-sm-10">
									<input id="zip" name="zip" class="form-control" placeholder=""
										maxlength="20">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">传真</label>
								<div class="col-sm-10">
									<input id="fax" name="fax" class="form-control" placeholder=""
										maxlength="20">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">地址</label>
								<div class="col-sm-10">
									<div class="input-group">
										<input id="address" name="address" class="form-control"
											placeholder="" maxlength="100"> <span
											class="input-group-btn">
											<button id="chooseMap" type="button" class="btn btn-primary">地图定位</button>
										</span>
									</div>
									<span class="help-block m-b-none">请在地图上选择公司地理位置。如果地址框内容为空，则选择地图位置后会自动回填地址信息，否则将保留原地址信息不变！</span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">助记符</label>
								<div class="col-sm-10">
									<input id="ab" name="ab" class="form-control" placeholder=""
										maxlength="20">
								</div>
							</div>
							<div class="hr-line-dashed"></div>
							<div class="form-group">
								<label class="col-sm-2 control-label">是否微信支付非预存</label>
								<div class="col-sm-10">
									<input id="isNotPrepay" name="isNotPrepay" type="checkbox"
										class="switcher"
										<c:if test="${sessionScope.isOrganAdmin}">disabled</c:if> /><span
										class="help-block m-b-none">是否用户微信支付采用非预存模式，无店铺平台卡预存金。</span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">是否可微信充卡</label>
								<div class="col-sm-10">
									<input id="canCharge" name="canCharge" type="checkbox"
										class="switcher"
										<c:if test="${sessionScope.isOrganAdmin}">disabled</c:if> /><span
										class="help-block m-b-none">是否可以通过微信充值会员卡。</span>
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-2 control-label">是否开通微信服务</label>
								<div class="col-sm-10">
									<input id="weixin" name="weixin" type="checkbox"
										class="switcher"
										<c:if test="${sessionScope.isOrganAdmin}">disabled</c:if> /><span
										class="help-block m-b-none">开通微信服务后，将可以在微信中与用户进行互动。</span>
								</div>
							</div>
							
							
							<div class="form-group">
								<label class="col-sm-2 control-label">是否允许微信绑定</label>
								<div class="col-sm-10">
									<input id="wxBanding" name="wxBanding" type="checkbox"
										class="switcher"
										<c:if test="${sessionScope.isOrganAdmin}">disabled</c:if> /><span
										class="help-block m-b-none">绑定微信后，将可以在微信中进行管理。</span>
								</div>
							</div>
							
							
							<div class="form-group">
								<label class="col-sm-2 control-label">店铺类型</label>
								<div class="col-sm-10">
									<select class="form-control" id="type" name="type" multiple
										size="4"
										<c:if test="${sessionScope.isOrganAdmin}">disabled</c:if>>
										<option value="美发">美发</option>
										<option value="美容">美容</option>
										<option value="养生">养生</option>
										<option value="美甲">美甲</option>
									</select> <span class="help-block m-b-none">按住Ctrl可多选。</span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">城市</label>
								<div class="col-sm-10">
									<select class="form-control" id="city" name="city"
										<c:if test="${sessionScope.isOrganAdmin}">disabled</c:if>>
										<option value="">请选择</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">区域</label>
								<div class="col-sm-10">
									<select class="form-control" id="district" name="district"
										<c:if test="${sessionScope.isOrganAdmin}">disabled</c:if>>
										<option value="">请选择</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">商圈</label>
								<div class="col-sm-10">
									<select class="form-control" id="region" name="region"
										<c:if test="${sessionScope.isOrganAdmin}">disabled</c:if>>
										<option value="">请选择</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">简介</label>
								<div class="col-sm-10">
									<textarea id="desc" name="desc" type="text"
										class="form-control" rows="5"></textarea>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">优惠信息</label>
								<div class="col-sm-10">
									<textarea id="discountInfo" name="discountInfo" type="text"
										class="form-control" rows="5"></textarea>
								</div>
							</div>
							<div class="hr-line-dashed"></div>
							<div class="form-group">
								<label class="col-sm-2 control-label">管理员账号</label>
								<div class="col-sm-10">
									<input id="adminId" name="adminId" type="text"
										class="form-control" minlength="2" maxlength="30" required>
								</div>
							</div>
							<div id="passwordDiv" class="form-group">
								<label class="col-sm-2 control-label">密码</label>
								<div class="col-sm-10">
									<input id="passwd" name="passwd" type="password"
										class="form-control" minlength="6" maxlength="30" required>
									<c:if test="${fforgan._id != null}">
										<span class="help-block m-b-none">如果不需要修改口令，请保持为空。</span>
									</c:if>
								</div>
							</div>
							<div class="hr-line-dashed"></div>
							<div class="form-group">
								<div class="col-sm-4 col-sm-offset-2">
									<button class="btn btn-primary" type="submit">提交</button>
									<c:if test="${param.back == null}">
										<button id="cancelBtn" class="btn btn-white" type="button">取消</button>
									</c:if>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		var layerId,addressComponents;
		$()
				.ready(
						function() {
							if (fillmaps && fillmaps.organ) {
								var img = fillmaps.organ.logo;
								if (img)
									$("#logoimg").attr("src",
											_ossImageHost + img);
								var cert = fillmaps.organ.cert;
								if (cert)
									$("#certimg").attr("src",
											_ossImageHost + cert);
								if (fillmaps.organ._id) {
									$("#adminId").disable();
									$("#passwd").attr("required", null);
									//$("#passwordDiv").remove();
								}
								if (fillmaps.organ.weixin) {
									$(".weixinr").show();
								} else {
									$(".weixinr").hide();
								}
								
								if (fillmaps.organ.wxBanding) {
									$(".wxBanding").show();
								} else {
									$(".wxBanding").hide();
								}



								for (var i = 0; i < fillmaps.organ.images.length; i++) {
									fs.push(fillmaps.organ.images[i]);
								}
								showAttach();
							}

							$("#weixin").change(function() {
								if (this.checked) {
									$(".weixinr").show();
								} else {
									$(".weixinr").hide();
								}
							});
							
							$("#wxBanding").change(function() {
								if (this.checked) {
									$(".wxBanding").show();
								} else {
									$(".wxBanding").hide();
								}
							});

							$("#organ")
									.validate(
											{
												submitHandler : function(form) {
													if (!$(
															"#gpsPoint\\.latitude")
															.val()
															|| !$(
																	"#gpsPoint\\.longitude")
																	.val()) {
														layer.alert("请进行地图定位");
														return;
													}
													$.shade.show();
													$("#organ").enable();
													form.submit();
												}
											});

							$("#cancelBtn")
									.click(
											function() {
												document.location.href = _ctxPath
														+ "/organ/toQuery.do?parentId=${param.parentId}+";
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

							$
									.post(
											'${ctxPath}/weixin/s/queryCity.do',
											{},
											function(data, status) {
												var c = $("#city")[0];
												for (var i = 0; i < data.length; i++) {
													var d = data[i];
													var option = new Option(
															d.name, d.name);
													option.tmp = d._id;
													c.options[c.options.length] = option;
													if (fillmaps
															&& fillmaps.organ
															&& fillmaps.organ.city == d.name) {
														option.selected = true;
														$(c).trigger('change');
													}
												}
											});
							$("#city")
									.change(
											function() {
												var t = $("#city")[0];
												var v = t.options[t.selectedIndex];
												if (v.tmp) {
													$
															.post(
																	'${ctxPath}/weixin/s/queryDistrict.do',
																	{
																		cityId : v.tmp
																	},
																	function(
																			data,
																			status) {
																		var c = $("#district")[0],ds=[];
																		c.options.length = 1;
																		for (var i = 0; i < data.length; i++) {
																			var d = data[i];
																			var option = new Option(
																					d.name,
																					d.name);
																			option.tmp = d._id;
																			c.options[c.options.length] = option;
																			if (fillmaps
																					&& fillmaps.organ
																					&& fillmaps.organ.district == d.name) {
																				option.selected = true;
																				$(
																						c)
																						.trigger(
																								'change');
																			}else if(addressComponents && addressComponents.district == d.name){
																				option.selected = true;
																				$(
																						c)
																						.trigger(
																								'change');
																			}
																		}
																	});
												} else {
													$("#district")[0].options.length = 1;
												}
											});

							$("#district")
									.change(
											function() {
												var t = $("#district")[0];
												var v = t.options[t.selectedIndex];
												if (v.tmp) {
													$
															.post(
																	'${ctxPath}/weixin/s/queryRegion.do',
																	{
																		districtId : v.tmp
																	},
																	function(
																			data,
																			status) {
																		var c = $("#region")[0];
																		c.options.length = 1;
																		for (var i = 0; i < data.length; i++) {
																			var d = data[i];
																			var option = new Option(
																					d.name,
																					d.name);
																			option.tmp = d._id;
																			c.options[c.options.length] = option;
																			if (fillmaps
																					&& fillmaps.organ
																					&& fillmaps.organ.region == d.name) {
																				option.selected = true;
																			}
																		}
																	});
												} else {
													$("#region")[0].options.length = 1;
												}
											});

						});

		function clickCert(t) {
			var ig = $("#cert").val();
			if (ig)
				window.open(t.src);
		}
		var it;
		var fileName;
		var fs = [];
		function selectImg() {
			it = "logo";
			$('#onlyFile').click();
		}
		function selectImg2() {
			it = "cert";
			$('#onlyFile').click();
		}
		function selectFile() { // 选择轮播图
			it = "img";
			if(fs && fs.length >= 3) {
				toastr.warning("轮播图最多三张！");
			}
			$('#onlyFile').click();
		}
		function changeFile(_this) {
			$.shade.show();
			fileName = getFileName(_this);
			$("#fileForm").ajaxSubmit({
				type : 'post',
				headers : {
					'type' : $(_this).attr("rel"),
					'isPublic' : 'true'
				},
                dataType :'json',
				url : URL.fileupload,
				success : function(data) {
					$.shade.hide();
					if (it == "logo") {
						var imgpath = _ossImageHost + data.data[0];
						$("#logoimg").attr("src", imgpath);
						$("#logo").val(data.data[0]);
					} else if (it == "cert") {
						var imgpath = _ossImageHost + data.data[0];
						$("#certimg").attr("src", imgpath);
						$("#cert").val(data.data[0]);
					}else if(it == "img") {
						var fileId = data.data[0];
						var f = fileId + "|" + fileName;
						fs.push(f);

						showAttach();

						$("#onlyFile2").val("");
					}
					$("#onlyFile").val("");
					toastr.success("图片上传成功");
				},
				error : function(XmlHttpRequest, textStatus, errorThrown) {
					$.shade.hide();
					toastr.success("图片上传失败");
				}
			});

		}

		// 地图选择回调
		function selectAddress(obj) {
			if (!$("#address").val())
				$("#address").val(obj.address);
			addressComponents = obj.addressComponents;
			if (!$("#city").val())
				$("#city").val(addressComponents.city);
			$("#city")
					.trigger(
							'change');
			$("#gpsPoint\\.latitude").val(obj.point.lat);
			$("#gpsPoint\\.longitude").val(obj.point.lng);
		    	layer.close(layerId);
		}
		
		<c:if test="${fforgan._id != null}"> 
		function print() {
			var iWidth=200; //弹出窗口的宽度;
			var iHeight=240; //弹出窗口的高度;
			var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
			var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
			window.open ('${ctxPath }/organ/print.do?organId=${fforgan._id}&organName=${fforgan.name}','prtWindow','height='+iHeight+',width='+iWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=yes,scrollbars=no, resizable=no,location=no, status=no') 
		}
		</c:if>
		function showAttach() {
			$(".attach").remove();
			for (var i = 0; i < fs.length; i++) {
				var s = fs[i].split('|');
				var url = _ossImageHost + s[0] + "@!style400";
				fileName = s[1];
				var a = $("<img src=\""+url+"\" class=\"attach\" style=\"margin:10px\" width=\"100\" height=\"100\" onClick=\"delImage(" + i + ")\"/>");
				$("#attachesDiv").append(a);
				$("#attachesDiv")
						.append(
								$("<input type=\"hidden\" id=\"images["
							+ i
							+ "]\" name=\"images["
							+ i + "]\" value=\"" + fs[i]
							+ "\" class=\"attach\">"));
			}
		}
		function getFileName(obj) {
			var val = obj.value;
			var idx = val.lastIndexOf("/");
			if (idx == -1)
				idx = val.lastIndexOf("\\");
			return val.substring(idx + 1);
		}
		function delImage(ih) {
			if(fs.length > ih) {
				layer.confirm('确定要删除此轮播图?', function(index) {
					layer.close(index);
					var fst = [];
					for (var i = 0; i < fs.length; i++) {
						if(i != ih) {
							fst.push(fs[i]);
						}
					}
					fs = fst;
					showAttach();
				});
			}
		}
	</script>
	<div class="hidden">
		<form action="" id="fileForm">
			<input type="file" rel="msgImage" autocomplete="off" name="onlyFile"
				id="onlyFile" onchange="changeFile(this)" placeholder="File here" />
		</form>
	</div>
</body>
</html>
