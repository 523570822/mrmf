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
								<a href="${ctxPath}/organ/toQuerySort.do"
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

								<div class="col-sm-1">
									<button id="search" class="btn btn-primary" type="submit">查询</button>
								</div>
								<div class="col-sm-1">
									<button id="newStaff" class="btn btn-danger" type="button">
										新增</button>
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

	<script type="text/javascript">
		$()
				.ready(
						function() {
							$("#newStaff")
									.click(
											function() {
												document.location.href = _ctxPath
														+ "/stage/toUpsert.do";
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
                                                rownumbers: true,
												url : _ctxPath
														+ "/stage/query.do",
												shrinkToFit : false,
												colNames : [  "名称","镜台租金(元/天", "平台租金分成（%）",
														"店铺租金分成（%）", "技师收益分成", "平台收益分成",
														"店铺收益分成","操作"],
												postData : $("#searchForm")
														.formobj(),
												colModel : [
													{
                                                        name : "name",
                                                        index : "name",
                                                        width : 100
													},

														{
															name : "leaseMoney",
															index : "leaseMoney",
															width : 150,
                                                            align : "center"
														},
														{
															name : "platRentSharing",
															index : "platRentSharing",

															align : "center"
														},
														{
															name : "shopRentSharing",
															index : "shopRentSharing",
                                                            align : "center"

														},
														{
															name : "staffEarningsSharing",
															index : "staffEarningsSharing",
															width : 120,
															align : "center"
														},
														{
															name : "platEarningsSharing",
															index : "platEarningsSharing",
															align : "center"
														},
														{
															name : "shopEarningsSharing",
															index : "shopEarningsSharing",
															align : "center"
														} ,
                                                    {
                                                        name : "_id",
                                                        index : "_id",
                                                        align : "center",
                                                        width : 150,
                                                        formatter : function(
                                                            cellvalue,
                                                            options,
                                                            rowObject) {
                                                            var v = "<a href='${ctxPath}/staff/toUpsert.do'>详情</a>&nbsp;&nbsp;";
//                                                                v +="<a href='javascript:void(0);' onclick='relevanceUser(\""
//                                                                    + cellvalue
//                                                                    + "\")'>关联用户</a>&nbsp;&nbsp;";
                                                            v += "<a href='javascript:void(0);' onclick='removeStaff(\""
                                                                + cellvalue
                                                                + "\")'>删除</a>";
                                                            return v;
                                                        }
                                                    }
												]
											});
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
