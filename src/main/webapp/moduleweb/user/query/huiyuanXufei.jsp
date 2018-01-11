<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/moduleweb/resources/common.jsp"%>
<%@ include file="/moduleweb/user/query/search.jsp"%>
<c:set var="organId"
	value="${param.organId == null?sessionScope.organId:param.organId}" />
<html>
<head>
<title></title>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content  animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content">
						<form id="searchForm" method="get" class="form-horizontal">
							<input type="hidden" id="sortField" name="sortField"
								value="createTime"> <input type="hidden" id="sortOrder"
								name="sortOrder" value="DESC"> <input type="hidden"
								id="type|integer" name="type|integer" value="3">
							<div class="col-sm-2">
								<input id="regex:name" name="regex:name" type="text"
									class="form-control" placeholder="会员姓名">
							</div>
							<div class="col-sm-2">
								<input id="gte:createTime|date" name="gte:createTime|date"
									class="laydate-icon form-control layer-date" placeholder="起始日期"
									data-mask="9999-99-99"
									laydate="{format : 'YYYY-MM-DD',min : '1938-06-16 23:59:59'}">
							</div>
							<div class="col-sm-2">
								<input id="lte:createTime|date+1" name="lte:createTime|date+1"
									class="laydate-icon form-control layer-date" placeholder="结束日期"
									data-mask="9999-99-99"
									laydate="{format : 'YYYY-MM-DD',min : '1938-06-16 23:59:59'}">
							</div>
							<c:if test="${!empty organList }">
								<label class="col-sm-1 control-label" id="company_label" >子公司</label>
									<div class="col-sm-2" id="company_div">
										<select class="form-control" id="organId" name="organId">
											<option value="">请选择</option>
											<c:forEach items="${organList}" var="organ">
												<option value="${organ._id}">${organ.name}</option>
											</c:forEach>
										</select><span class="help-block m-b-none"></span>
									</div>
							</c:if>
							<div class="form-group">
								<div class="col-sm-3">
									<button id="search" class="btn btn-primary" type="submit">查询</button>
									<button id="multipleSearch" class="btn btn-primary" type="button">综合查询</button>
									<button id="download" class="btn btn-danger btn-outline" type="button">导出</button>
								</div>
							</div>
						</form>
						<div class="jqGrid_wrapper">
							<table id="myTable"></table>
						</div>
					</div>
					<form id="export" method="post"  action="${ctxPath}/user/userpart/download.do" class="form-horizontal">
						<input type="hidden" id="content" name="content">
					</form>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		var manageType;
		$()
				.ready(
						function() {
						$("#download").click(function(){
								if(jQuery.isEmptyObject(searchCriteria)){
									var o = $("#searchForm").formobj();
									searchCriteria={};
									for(var key in o){
										searchCriteria[key]=o[key];
									}
								}
                            $("#export").html("");
								var downloadstr="";
//								var form=$("<form>");//定义一个form表单
//									form.attr("style","display:none");
//									form.attr("target","");
//									form.attr("method","post");
//									form.attr("action",_ctxPath +"/user/userpart/download.do");
									var input1=$("<input>");
									input1.attr("type","hidden");
									input1.attr("name","queryType");
									input1.attr("value","xufei");
                            $("#export").append(input1);
									
								for(var key in searchCriteria){
									var input1=$("<input>");
									input1.attr("type","hidden");
									input1.attr("name",key);
									input1.attr("value",searchCriteria[key]);
                                    $("#export").append(input1);
								}
								//console.log(downloadstr);
                            $("#export").submit();
							});
							//综合查询方法
							$("#multipleSearch").click(function(){
								showMultipleSearch("searchForm","myTable","","会员号,会员姓名,续费日期,余额,续费额,总额,会员类型,是否删除");
							});
							$("#codeDiv").hide();

							$("#searchForm").submit(function() {
							var o = $("#searchForm").formobj();
									searchCriteria={};
									for(var key in o){
										searchCriteria[key]=o[key];
									}
								$("#myTable").reloadGrid({
									postData : $("#searchForm").formobj()
								});
								return false;
							});

							$("#myTable")
									.grid(
											{
												url : _ctxPath
														+ "/user/userpart/queryByFpi.do",
												postData : $("#searchForm")
														.formobj(),
												shrinkToFit : false,
												colNames : [ "操作", "会员号",
														"会员姓名", "会员类型", "续费日期",
														"余额", "续费额", "充值总额",
														"经手人", "是否删除" ],
												colModel : [
														{
															name : "idx",
															index : "idx",
															width : 60,
															sortable : false,
															formatter : function(
																	cellvalue,
																	options,
																	rowObject) {

																if (rowObject.delete_flag) {
																	return "";
																} else {
																	var v = "<a href='javascript:void(0);' onClick='delRow(\""
																			+ options.rowId
																			+ "\")'>删除</a>&nbsp;&nbsp;";
																	return v;
																}
															}
														},
														{
															name : "id_2",
															index : "id_2",
															width : 100
														},
														{
															name : "name",
															index : "name",
															width : 100
														},
														{
															name : "usersortName",
															index : "usersortName"
														},
														{
															name : "createTime",
															index : "createTime"
														},
														{
															name : "nowMoney4",
															index : "nowMoney4",
															width : 80
														},
														{
															name : "money1",
															index : "money1",
															width : 80
														},
														{
															name : "money_leiji",
															index : "money_leiji",
															width : 80
														},
														{
															name : "staffId1",
															sortable : false,
															index : "staffId1",
															formatter : function(
																	cellvalue) {
																var ms = fillmaps.staffs;
																for (var i = 0; i < ms.length; i++) {
																	if (ms[i]._id == cellvalue) {
																		return ms[i].name;
																	}
																}
																return "无";
															},
															width : 80
														},
														{
															name : "delete_flag",
															index : "delete_flag",
															width : 70,
															formatter : function(
																	cellvalue,
																	options,
																	rowObject) {
																if (cellvalue)
																	return "<font color=\"red\">是</font>";
																else
																	return "否";
															}
														} ]
											});
						});
		function delRow(idx) {
			var kd = $("#" + idx, $("#myTable")).data("rawData");
			layer
					.confirm(
							'确定要删除续费?',
							function(index) {
								layer.close(index);

								$
										.post(
												"${ctxPath}/user/userIncard/verifycode.do",
												{
													incardId : kd.incardId
												},
												function(data, status) {
													if (data.success) {
														$("#codeDiv").show();
														$("#code").val("");
														$("#phone").text(
																data.data);
														layer
																.open({
																	type : 1,
																	title : "短信验证码",
																	area : [
																			'400px',
																			'240px' ],
																	btn : [
																			'确定',
																			'取消' ],
																	yes : function(
																			index,
																			layero) {
																		var code = $(
																				"#code")
																				.val();
																		if (!code) {
																			toastr
																					.warning("请输入短信验证码");
																			$(
																					"#code")
																					.focus();
																			return;
																		}

																		$
																				.post(
																						"${ctxPath}/user/userIncard/xufeiRemove.do",
																						{
																							userpartId : kd._id,
																							code : code
																						},
																						function(
																								data,
																								status) {
																							if (data.success) {
																								layer
																										.closeAll();
																								$(
																										"#searchForm")
																										.submit();
																							} else {
																								layer
																										.alert(data.message);
																							}
																						});
																	},
																	cancel : function(
																			index) {
																		layer
																				.closeAll();
																	},
																	content : $("#codeDiv")
																});
													} else {
														layer
																.alert(data.message);
													}
												});
							});
		}
	</script>
	<!-- 验证码div -->
	<div id="codeDiv" class="wrapper wrapper-content"
		style="width: 95%; height: 95%">
		<div class="row">
			<form id="codeForm" method="post" class="form-horizontal"
				onSubmit="return false;">
				<div class="form-group">
					<label class="col-sm-4 control-label">短信验证码：</label>
					<div class="col-sm-7">
						<input id="code" name="code" type="text" class="form-control"><span
							class="help-block m-b-none">验证码已发送到手机号<span id="phone"></span>。
						</span>
					</div>
				</div>
				<input type="submit" value="提交" style="display: none">
			</form>
		</div>
	</div>
</body>
</html>
