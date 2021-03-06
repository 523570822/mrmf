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
								href="${ctxPath}/stage/toQuerySort.do"
								class="btn-link"> <i class="fa fa-angle-double-left"></i>返回
							</a>
							<c:if test="${ffstageCategoryFees._id == null}">
								新建分类
							</c:if>
							<c:if test="${ffstageCategoryFees._id != null}">收费类别管理<small>修改分类信息。</small>
							</c:if>
						</h5>
					</div>
					<div class="ibox-content">
						<c:if test="${returnStatus.status == 0}">
							<div class="alert alert-danger">
								<i class="fa fa-exclamation-triangle"></i>${returnStatus.message}</div>
						</c:if>

						<form id="stageCategoryFees" method="post" action="${ctxPath}/stage/upsert.do"
							class="form-horizontal">
							<input type="hidden" id="_id" name="_id">
							<input type="hidden"
								   id="createTime" name="createTime">
							<div class="form-group">
								<label class="col-sm-2 control-label">分类名称</label>
								<div class="col-sm-10">
									<input id="name" name="name" type="text" class="form-control"
										minlength="2" maxlength="50" required>
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
							<%--<div class="form-group">
								<label class="col-sm-2 control-label">助记符</label>
								<div class="col-sm-10">
									<input id="zjfCode" name="zjfCode" type="text"
										class="form-control" maxlength="30" readonly>
								</div>
							</div>--%>


							<div class="form-group">
								<label class="col-sm-2 control-label">单日镜台（租金）</label>
								<div class="col-sm-10">
									<input  id="leaseMoney" name="leaseMoney" type="number" class="form-control"
									 required>
								</div>
							</div>


                            <div class="form-group">
                                <label class="col-sm-2 control-label">租金分成</label>
                                <div class="col-sm-10">
                                    <div class="m-r-md inline">
                                        <div class="m-r-md inline">
                                            <input id="platRentSharing" name="platRentSharing"  type="text" value="100" class="dial m-r-sm" data-fgColor="#1AB394" data-width="85" data-height="85" />
                                            <br>    平台租金(%)
                                        </div>
                                        <div class="m-r-md inline">
                                            <input  id="shopRentSharing" name="shopRentSharing" type="text" value="0" class="dial m-r" data-fgColor="#1AB394" data-width="85" data-height="85" />
                                            <br>   店铺租金(%)
                                        </div>

                                    </div>

                                </div>
                            </div>

							<div class="form-group">
								<label class="col-sm-2 control-label">收益分成</label>
								<div class="col-sm-10">
									<div class="m-r-md inline">
										<div class="m-r-md inline">
											<input id="platEarningsSharing" name="platEarningsSharing"  type="text" value="0" class="dial m-r-sm" data-fgColor="#1AB394" data-width="85" data-height="85" />
											<br>   平台收益(%)
										</div>
										<div class="m-r-md inline">
											<input  id="staffEarningsSharing" name="staffEarningsSharing" type="text" value="0" class="dial m-r" data-fgColor="#1AB394" data-width="85" data-height="85" />
											<br>  技师收益(%)
										</div>
										<div class="m-r-md inline">
											<input  id="shopEarningsSharing" name="shopEarningsSharing" type="text" value="100" class="dial m-r" data-fgColor="#1AB394" data-width="85" data-height="85" />
											<br> 店铺收益(%)
										</div>
									</div>

								</div>
							</div>






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


        var layerId,addressComponents;
		$().ready(function() {

            $("#platRentSharing").knob({
            //    max: 940,
              //  min: 500,
                thickness: .3,
            //    fgColor: '#2B99E6',
            //    bgColor: '#303030',
                'change':function(e){

                   $('#shopRentSharing').val(100-e).trigger("change");

                },
                'release':function(e){

                    $('#shopRentSharing').val(100-e).trigger("release");

                }
            });
            $("#shopRentSharing").knob({
                //    max: 940,
                //  min: 500,
                thickness: .3,
                //    fgColor: '#2B99E6',
                //    bgColor: '#303030',
                'change':function(e){

                    $('#platRentSharing').val(100-e).trigger("change");

                },
                'release':function(e){

                    $('#platRentSharing').val(100-e).trigger("release");

                }
            });
            $("#staffEarningsSharing").knob({
                //    max: 940,
                //  min: 500,
                thickness: .3,
                //    fgColor: '#2B99E6',
                //    bgColor: '#303030',
                'change':function(e){

                    $('#shopEarningsSharing').val(100-e-$("#platEarningsSharing").val()).trigger("change");


                },
                'release':function(e){

                    $('#shopEarningsSharing').val(100-e-$("#platEarningsSharing").val()).trigger("release");

                }
            });
            $("#platEarningsSharing").knob({
                //    max: 940,
                //  min: 500,

                thickness: .3,
                //    fgColor: '#2B99E6',
                //    bgColor: '#303030',
                'change':function(e){

                    $('#shopEarningsSharing').val(100-e-$("#staffEarningsSharing").val()).trigger("change");

                },
                'release':function(e){

                    $('#shopEarningsSharing').val(100-e-$("#staffEarningsSharing").val()).trigger("release");

                }
            });
            $("#shopEarningsSharing").knob({
                //    max: 940,
                //  min: 500,
                readOnly:'readOnly',
                thickness: .3,
                //    fgColor: '#2B99E6',
                //    bgColor: '#303030',

            });

							if (fillmaps && fillmaps.staff) {

							}

							$("#idcard").change(
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



            $
                .post(
                    '${ctxPath}/weixin/s/queryCity.do',
                    {},
                    function(data, status) {
                        console.info(fillmaps);
                        var c = $("#city")[0];
                        for (var i = 0; i < data.length; i++) {
                            var d = data[i];
                            var option = new Option(
                                d.name, d.name);
                            option.tmp = d._id;
                            c.options[c.options.length] = option;
                            if (fillmaps
                                && fillmaps.stageCategoryFees
                                && fillmaps.stageCategoryFees.city == d.name) {
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
                                                && fillmaps.stageCategoryFees
                                                && fillmaps.stageCategoryFees.district == d.name) {
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

						});
	</script>
</body>
</html>
