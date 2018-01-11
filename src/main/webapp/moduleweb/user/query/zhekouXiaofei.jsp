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
								id="type|integer" name="type|integer" value="1"> <input
								type="hidden" id="in:usersortType|array" name="usersortType"
								value="1001">
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
								<div class="col-sm-5">
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
				</div>
				<form id="export" method="post"  action="${ctxPath}/user/userpart/download.do" class="form-horizontal">
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
									input1.attr("value","zhekou");
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
								showMultipleSearch("searchForm","myTable","","会员姓名,性别,会员类型,服务大类,服务明细,折后价,光临日期,是否删除,挂账,");
							});
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
												colNames : [ "流水号","卡号", "会员姓名",
														"性别", "会员类型", "服务明细",
														"单价", "折后价", "光临日期",
														"服务人员1", "挂账", "删除" ],
												colModel : [
														{
															name : "xiaopiao",
															index : "xiaopiao",
															width : 100
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
															name : "sex",
															index : "sex",
															width : 60
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
															name : "money6",
															index : "money6",
															width : 80,
															formatter : function(
																	cellvalue,
																	option,
																	cellobj) {
																var m = parseFloat(cellobj.money1)
																		/ parseFloat(cellvalue)
																		* 100;
																return m
																		.toFixed(2);
															}
														},
														{
															name : "money1",
															index : "money1",
															width : 80
														},
														{
															name : "createTime",
															index : "createTime"
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
															name : "guazhang_flag",
															index : "guazhang_flag",
															width : 60,
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
															name : "delete_flag",
															index : "delete_flag",
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
														} ]
											});
						});
	</script>
</body>
</html>
