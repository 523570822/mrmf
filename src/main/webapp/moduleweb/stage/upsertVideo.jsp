<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/moduleweb/resources/common.jsp"%>


<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

	pageContext.setAttribute("basePath",basePath);

/*    String basePathS =request.getServerName()+":"+request.getServerPort()+path+"/";
    String scheme=request.getScheme();

    pageContext.setAttribute("basePathS",basePathS);
    pageContext.setAttribute("scheme",scheme);*/

%>
<c:set var="organId"
	value="${param.organId == null?sessionScope.organId:param.organId}" />

<html>
<head>
	<base href="<%=basePath%>">
	<script src="moduleweb/resources/js/chosen.jquery.js" type="text/javascript"></script>

    <link href="moduleweb/resources/css/chosen/chosen.css" type="text/css" rel="stylesheet" />


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
								href="${ctxPath}/video/toQueryVideo.do"
								class="btn-link"> <i class="fa fa-angle-double-left"></i>返回
							</a>
							<c:if test="${ffstageCategoryFees._id == null}">
								新建视频
							</c:if>
							<c:if test="${ffstageCategoryFees._id != null}">视频管理<small>修改视频信息。</small>
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
								<label class="col-sm-2 control-label">视频名称</label>
								<div class="col-sm-10">
									<input id="name" name="name" type="text" class="form-control"
										minlength="2" maxlength="50" required>
								</div>
							</div>

							<div class="form-group">
						<%--		<label class="col-sm-2 control-label">店铺选择</label>
								<div class="col-sm-10">
								&lt;%&ndash;	<select class="form-control" id="city" name="city"
											<c:if test="${sessionScope.isOrganAdmin}">disabled</c:if>>&ndash;%&gt;


                                        <select id="city" name="city" data-placeholder="Choose a Country" class="chzn-select" multiple style="width:350px;"tabindex="4">
										<option value="">请选择</option>
									</select>
								</div>--%>

                            <label class="col-sm-2 control-label"> 店铺名称</label>
                            <div class="col-sm-10">
                                <select id="organId" name="organId" data-placeholder="请选择" class="chosen-select" multiple  style="width:450px;"tabindex="4">

                                    <c:forEach items="${organlist}" var="organ" varStatus="row">

                                        <%-- <c:when test="${file.fid eq obj.service.docUrl}">
                                             <option value="${file._id}" selected="selected">${file.name }</option>
                                         </c:when>
                                         <c:otherwise>
                                             <option value="${file._id}">${file.name}</option>
                                         </c:otherwise>--%>
                                        <option value="${organ._id}">${organ.name}(${organ.address})</option>


                                    </c:forEach>
                                </select>
							</div>


							<%--<div class="form-group">
								<label class="col-sm-2 control-label">助记符</label>
								<div class="col-sm-10">
									<input id="zjfCode" name="zjfCode" type="text"
										class="form-control" maxlength="30" readonly>
								</div>
							</div>--%>





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

            var config = {
                '.chosen-select': {
                    search_contains:true
                }

            }
            for (var selector in config) {
                $(selector).chosen(config[selector]);
            }




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






						});
	</script>
</body>
</html>
