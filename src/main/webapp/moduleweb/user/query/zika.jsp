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
								name="sortOrder" value="DESC">
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
								<label class="col-sm-1 control-label" id="company_label">子公司</label>
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
								<div class="col-sm-4">
									<button id="search" class="btn btn-primary" type="submit">查询</button>
									<button id="multipleSearch" class="btn btn-primary"
										type="button">综合查询</button>
									<button id="download" class="btn btn-danger btn-outline"
										type="button">导出</button>
								</div>
							</div>
						</form>
						<div class="jqGrid_wrapper">
							<table id="myTable"></table>
						</div>
					</div>
				</div>
				<form id="export" method="post"  action="${ctxPath}/user/userInincard/download.do" class="form-horizontal">
					<input type="hidden" id="content" name="content">
				</form>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		var manageType;
		$()
				.ready(
						function() {
							$("#download")
									.click(
											function() {
												if (jQuery
														.isEmptyObject(searchCriteria)) {
													var o = $("#searchForm")
															.formobj();
													searchCriteria = {};
													for ( var key in o) {
														searchCriteria[key] = o[key];
													}
												}
                                                $("#export").html("");
												var downloadstr = "";
//												var form = $("<form>");//定义一个form表单
//												form.attr("style",
//														"display:none");
//												form.attr("target", "");
//												form.attr("method", "post");
//												form
//														.attr(
//																"action",
//																_ctxPath
//																		+ "/user/userInincard/download.do");
												for ( var key in searchCriteria) {
													var input1 = $("<input>");
													input1.attr("type",
															"hidden");
													input1.attr("name", key);
													input1
															.attr(
																	"value",
																	searchCriteria[key]);
                                                    $("#export").append(input1);
												}
												//console.log(downloadstr);
                                                $("#export").submit();
											});
							//综合查询方法
							$("#multipleSearch")
									.click(
											function() {
												showMultipleSearch(
														"searchForm",
														"myTable", "",
														"会员号,会员姓名,卡表面号,会员类型,总次数,是否删除,单次款额,建卡日期,余额");
											});
							$("#searchForm").submit(function() {
								var o = $("#searchForm").formobj();
								searchCriteria = {};
								for ( var key in o) {
									searchCriteria[key] = o[key];
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
														+ "/user/userInincard/queryByFpi.do",
												postData : $("#searchForm")
														.formobj(),
												shrinkToFit : false,
												colNames : [ "流水号", "建卡日期",
														"会员姓名", "卡表面号",
														"卡中卡类型", "服务项目",
														"子卡金额", "总次数", "剩余次数",
														"子卡状态", "会员号", "免单",
														"单次款额", "换算金钱" ],
												colModel : [
														{
															name : "xiaopiao",
															index : "xiaopiao",
															width : 90
														},
														{
															name : "createTime",
															index : "createTime"
														},
														{
															name : "name",
															index : "name",
															width : 100
														},
														{
															name : "cardno",
															index : "cardno",
															width : 100
														},
														{
															name : "usersortName",
															index : "usersortName"
														},
														{
															name : "smallsortName",
															index : "smallsortName"
														},
														{
															name : "money_leiji",
															index : "money_leiji",
															width : 100
														},
														{
															name : "allcishu",
															index : "allcishu",
															width : 100
														},
														{
															name : "shengcishu",
															index : "shengcishu",
															width : 100
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
																	return "<font color=\"red\">删除</font>";
																else
																	return "正常";
															}
														},
														{
															name : "id_2",
															index : "id_2",
															width : 100
														},
														{
															name : "miandan",
															index : "miandan",
															width : 70,
															formatter : function(
																	cellvalue,
																	options,
																	rowObject) {
																if (cellvalue)
																	return "是";
																else
																	return "否";
															}
														},
														{
															name : "danci_money",
															index : "danci_money",
															width : 70
														},
														{
															name : "shengcishu",
															index : "shengcishu",
															width : 70,
															formatter : function(
																	cellvalue,
																	options,
																	rowObject) {
                                                                var v = parseFloat(cellvalue)
                                                                    * parseFloat(rowObject.danci_money);
                                                                return v.toFixed(2);
															}
														} ]
											});
						});
	</script>
</body>
</html>
