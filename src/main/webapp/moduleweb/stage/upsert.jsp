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
								href="${ctxPath}/Stage/toQuery.do"
								class="btn-link"> <i class="fa fa-angle-double-left"></i>返回
							</a>
							<c:if test="${ffstaff._id == null}">
								新建分类
							</c:if>
							<c:if test="${ffstaff._id != null}">修改分类 <small>修改分类信息。</small>
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
							<input type="hidden" id="_id" name="_id">

							<div class="form-group">
								<label class="col-sm-2 control-label">分类名称</label>
								<div class="col-sm-10">
									<input id="name" name="name" type="text" class="form-control"
										minlength="2" maxlength="50" required>
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
                                            <input id="platRentSharing" name="platRentSharing"  type="text" value="75" release="asd();" class="dial m-r-sm" data-fgColor="#1AB394" data-width="85" data-height="85" />
                                            <br>    平台租金(%)
                                        </div>
                                        <div class="m-r-md inline">
                                            <input  id="shopRentSharing" name="shopRentSharing" type="text" value="25" class="dial m-r" data-fgColor="#1AB394" data-width="85" data-height="85" />
                                            <br>   店铺租金(%)
                                        </div>

                                    </div>
                                </div>
                            </div>






                            <div class="form-group">
                                <label class="col-sm-2 control-label">技师收益分成</label>
                                <div class="col-sm-10">
                                    <input id="staffEarningsSharing" name="staffEarningsSharing" type="text" class="form-control"
                                           required>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">平台收益分成</label>
                                <div class="col-sm-10">
                                    <input id="platEarningsSharing" name="platEarningsSharing" type="text" class="form-control"
                                           required>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">店铺收益分成</label>
                                <div class="col-sm-10">
                                    <input id="shopEarningsSharing" name="shopEarningsSharing" type="text" class="form-control"
                                           required>
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


        function asd(){
            alert("dd");
        }
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
