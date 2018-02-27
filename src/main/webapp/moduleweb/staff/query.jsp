<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/moduleweb/resources/common.jsp"%>
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
				<div class="ibox ">
					<c:if test="${param.parentId != null && param.parentId != ''}">
						<div class="ibox-title">
							<h5>
								<a href="${ctxPath}/organ/toQuery.do?parentId=${param.parentId}"
									class="btn-link"><i class="fa fa-angle-double-left"></i>返回</a>
								员工管理
							</h5>
						</div>
					</c:if>
					<div class="ibox-content">
						<form id="searchForm" method="get" class="form-horizontal">
							<input type="hidden" id="organId" name="organId"
								value="${organId}">
							<div class="form-group">
								<label class="col-sm-1 control-label">名称</label>
								<div class="col-sm-2">
									<input id="regex:name" name="regex:name" type="text"
										class="form-control">
								</div>
								<label class="col-sm-1 control-label">身份证号</label>
								<div class="col-sm-2">
									<input id="regex:idcard" name="regex:idcard" type="text"
										class="form-control">
								</div>
								<label class="col-sm-1 control-label">手机号</label>
								<div class="col-sm-2">
									<input id="regex:phone" name="regex:phone" type="text"
										   class="form-control">
								</div>
								<div class="col-sm-1">
									<button id="search" class="btn btn-primary" type="submit">查询</button>
								</div>
								<div class="col-sm-1">
									<button id="newStaff" class="btn btn-danger" type="button">
										新增</button>
								</div>
								<div class="col-sm-1">
									<%--<button id="twoCode" class="btn btn-danger" type="button">--%>
										<%--扫码</button>--%>
								</div>
							</div>
						</form>
						<div class="jqGrid_wrapper">
							<table id="staffTable"></table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="form-group ibox-content" style="display: none" id="show">
		<img src="${ctxPath}/staff/organCode.do?organId=${organId}">
	</div>
	<script type="text/javascript">
		$()
				.ready(
						function() {
							$("#newStaff")
									.click(
											function() {
												document.location.href = _ctxPath
														+ "/staff/toUpsert.do?organId=${organId}&parentId=${param.parentId}";
											});
							$("#searchForm").submit(function() {
								$("#staffTable").reloadGrid({
									postData : $("#searchForm").formobj()
								});
								return false;
							});
							$("#staffTable")
									.grid(
											{
												url : _ctxPath
														+ "/staff/query.do",
												shrinkToFit : false,
												colNames : [ "操作", "姓名", "性别",
														"开通微信", "入职日期", "身份证号",
														"资格证号","联系电话", "岗位",
														"部门" ],
												postData : $("#searchForm")
														.formobj(),
												colModel : [
														{
															name : "_id",
															index : "_id",
															align : "center",
															width : 150,
															formatter : function(
																	cellvalue,
																	options,
																	rowObject) {
																var v = "<a href='${ctxPath}/staff/toUpsert.do?organId=${organId}&parentId=${param.parentId}&staffId="
																		+ cellvalue
																		+ "'>详情</a>&nbsp;&nbsp;";
//                                                                v +="<a href='javascript:void(0);' onclick='relevanceUser(\""
//                                                                    + cellvalue
//                                                                    + "\")'>关联用户</a>&nbsp;&nbsp;";
																v += "<a href='javascript:void(0);' onclick='removeStaff(\""
																		+ cellvalue
																		+ "\")'>删除</a>";
																return v;
															}
														},
														{
															name : "name",
															index : "name",
															width : 100
														},
														{
															name : "sex",
															index : "sex",
															width : 60,
															align : "center"
														},
														{
															name : "weixin",
															index : "weixin",
															align : "center",
															width : 80,
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
															name : "accessDay",
															index : "accessDay",
															width : 120,
															align : "center"
														},
														{
															name : "idcard",
															index : "idcard",
															align : "center"
														},
														{
															name : "certNumber",
															index : "certNumber",
															align : "center"
														},
														{
															name : "phone",
															index : "phone",
															align : "center"
														},
														{
															name : "dutyName",
															index : "dutyName",
															align : "center"
														},
														{
															name : "bumenName",
															index : "bumenName",
															align : "center"
														} ]
											});
						});
		$("#twoCode").click(function () {
            layer.open({
                title:false,
                type: 1,
                skin: 'layui-layer-nobg', //加上边框
                area: '276px',
//                area: ['870px', '540px'], //宽高
                content: $("#show"),
                shadeClose:true,
            });
            $("#show").show();
        });
//		function relevanceUser(staffId) {
//		    var organId = $("#organId").val();
//            layer.open({
//                title:false,
//                type: 2,
//                skin: 'layui-layer-nobg', //加上边框
////                area: '276px',
//                area: ['870px', '540px'], //宽高
//                content: _ctxPath+"/staff/toQueryUserByStaff.do?staffId="+staffId+"&organId="+organId,
//                shadeClose:true,
//            });
//        }
		function removeStaff(staffId) {
			layer.confirm('确定要删除员工?', function(index) {
				layer.close(index);
				var url = _ctxPath + "/staff/removeStaff.do";
				$.get(url, {
					'staffId' : staffId
				}, function(data) {
					if (data) {
						if (data.success) {
							toastr.success("操作成功");
						} else {
							toastr.error(data.message);
						}
						$("#staffTable").trigger("reloadGrid");
					} else {
						toastr.error("操作失败");
					}
				});
			});

		}
	</script>
</body>
</html>
