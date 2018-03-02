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

	<link href="${ctxPath}/moduleweb/resources/css/chosen/chosen.css" type="text/css" rel="stylesheet" />
	<link href="${ctxPath}/moduleweb/resources/css/datapicker/datepicker3.css" rel="stylesheet">

	<link href="${ctxPath}/moduleweb/resources/webuploader-0.1.5/webuploader.css" type="text/css" rel="stylesheet" />


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
								href="video/toQueryVideo.do"
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

					<%--	<form id="stageCategoryFees" method="post" action="${ctxPath}/stage/upsert.do" class="form-horizontal">--%>
							<form id="stageCategoryFees" action=""  class="form-horizontal">
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
                            <label class="col-sm-2 control-label"> 店铺名称</label>
                            <div class="col-sm-10">
                                <select id="organIds" name="organIds" data-placeholder="请选择" class="chosen-select" multiple  style="width:450px;"tabindex="4" required>

                                    <c:forEach items="${organlist}" var="organ" varStatus="row">


                                        <option value="${organ._id}">${organ.name}(${organ.address})</option>


                                    </c:forEach>
                                </select>
							</div>


                            </div>
							<div class="form-group">
								<label class="col-sm-2 control-label"> 文件上传</label>
								<div class="col-sm-10">
									<input type="file" rel="msgImage" autocomplete="off" name="onlyFile" id="onlyFile"  placeholder="File here" accept="audio/*, video/*"  />
								</div>


							</div>
							<div class="form-group">
									<label class="col-sm-2  control-label"> 视频状态</label>
									<div class="col-sm-10">
									<div class="onoffswitch">
										<input type="checkbox" checked class="onoffswitch-checkbox" id="flag" name="flag">
										<label class="onoffswitch-label" for="flag">
											<span class="onoffswitch-inner"></span>
											<span class="onoffswitch-switch"></span>
										</label>
									</div>
									</div>


							</div>
							<div class="form-group" id="date">
								<label class="col-sm-2  control-label  font-noraml">时间范围选择</label>
								<div class="col-sm-15 input-daterange input-group" id="datepicker">
									<input type="text" class="input-sm form-control" name="startTime" value="2014-11-11" />
									<span class="input-group-addon">到</span>
									<input type="text" class="input-sm form-control" name="endTime" value="2014-11-17" />
								</div>


							</div>

				<%--			<div class="form-group wu-example"  id="uploader">

									<!--用来存放文件信息-->
									<div id="thelist" class="col-sm-2 control-label uploader-list"></div>
									<div class=  "  col-sm-2 control-label btns">
										<div id="picker">选择文件</div>
										<button id="ctlBtn" class="btn btn-default">开始上传</button>
									</div>

							</div>--%>


                            <div class="form-group">
                                <div class="col-sm-4 col-sm-offset-2">
                                    <button class="btn btn-primary" type="submit" name="sub">提交</button>
									<button class="btn " type="reset" name="res">重置</button>
                                    <button id="cancelBtn" class="btn btn-white" type="button">取消</button>
                                </div>
                            </div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script src="${ctxPath}/moduleweb/resources/webuploader-0.1.5/webuploader.js" type="text/javascript"></script>
	<script src="${ctxPath}/moduleweb/resources/webuploader-0.1.5/baidu.js" type="text/javascript"></script>
	<script src="${ctxPath}/moduleweb/resources/js/chosen.jquery.js" type="text/javascript"></script>
	<script
			src="${ctxPath}/moduleweb/resources/plugins/datapicker/bootstrap-datepicker.js">

	</script>
	<script
			src="${ctxPath}/moduleweb/resources/plugins/validate/additional-methods.js">

	</script>
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
												document.location.href = "video/toQueryVideo.do";
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


        $.post('${ctxPath}/weixin/s/queryCity.do',
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
        $("#city").change(
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

        $("#district").change(
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

	</script>
</body>
</html>
