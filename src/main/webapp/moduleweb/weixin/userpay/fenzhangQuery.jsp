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
				<div class="ibox float-e-margins">
					<c:if test="${param.organId != null && param.organId != ''}">
						<div class="ibox-title">
							<h5>
								<a href="${ctxPath}/weixin/charge/toQuery.do" class="btn-link"><i
									class="fa fa-angle-double-left"></i>返回</a> 分账记录
							</h5>
						</div>
					</c:if>
					<div class="ibox-content">
						<form id="searchForm" method="get" class="form-horizontal">
							<input id="organId" name="organId" type="hidden"
								value="${organId}"> <label
								class="col-sm-2 control-label">处理状态</label>
							<div class="col-sm-3">
								<select class="form-control" id="state|integer"
									name="state|integer">
									<option value="">请选择</option>
									<option value="0">未处理</option>
									<option value="1">已处理</option>
								</select>
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
							<div class="form-group">
								<div class="col-sm-2">
									<button id="search" class="btn btn-primary" type="submit">查询</button>
								</div>
							</div>
							<div class="form-group">
								<div id="totalRegion" class="col-sm-12">
									总金额汇总：<font color="red" id="total"></font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;店铺汇总：<font
										color="red" id="totalOrgan"></font>
									<c:if test="${param.organId != null && param.organId != ''}">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;平台汇总：<font
											color="red" id="totalSys"></font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;用户汇总：<font
											color="red" id="totalUser"></font>
									</c:if>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;平台卡余额：<font
										color="red" id="cardRest"></font>
								</div>
							</div>
						</form>
						<div class="jqGrid_wrapper">
							<table id="usersortTable"></table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		var manageType;
		$().ready(function() {
			
			function getSum() {
				var o = $("#searchForm").formobj();
				o.startTime = o["gte:createTime|date"];
				o.endTime = o["lte:createTime|date+1"];
				o.state = o["state|integer"];
				$.post("${ctxPath}/weixin/userpayFenzhang/totalOrgan.do",
						o, function(data, status) {
							$("#totalRegion").fillform(data);
						});
			}

			getSum();

			$("#searchForm").submit(function() {
				$("#usersortTable").reloadGrid( {
					postData : $("#searchForm").formobj()
				});
				getSum();
				return false;
			});

			$("#usersortTable").grid({
				url : _ctxPath + "/weixin/userpayFenzhang/query.do",
				postData : $("#searchForm").formobj(),
				shrinkToFit : false,
				colNames : [ "姓名", "总金额", "微信支付","钱包支付","店铺", <c:if test="${!sessionScope.isOrganAdmin}">"平台", "用户", </c:if>"状态", "发生时间"<c:if test="${sessionScope.isOrganAdmin}">,"操作"</c:if> ],
				colModel : [ {
					name : "userName",
					index : "userName",
					width : 100,
					formatter : function(cellvalue, options, rowObject) {
						if (cellvalue)
							return cellvalue;
						else
							return "无";
					}
				}, {
					name : "price",
					width : 100,
					index : "price"
				},{
					name : "payWeixin",
					width : 100,
					index : "payWeixin"
				},{
					name : "payWallet",
					width : 100,
					index : "payWallet"
				}, {
					name : "organAmount",
					width : 100,
					index : "organAmount",
					formatter : function(
							cellvalue) {
						return parseFloat(
								cellvalue)
								.toFixed(
										2);
					}
				},<c:if test="${!sessionScope.isOrganAdmin}"> {
					name : "sysAmount",
					index : "sysAmount",
					width : 100,
					formatter : function(
							cellvalue) {
						return parseFloat(
								cellvalue)
								.toFixed(
										2);
					}
				}, {
					name : "userAmount",
					index : "userAmount",
					width : 100,
					formatter : function(
							cellvalue) {
						return parseFloat(
								cellvalue)
								.toFixed(
										2);
					}
				}, </c:if>{
					name : "state",
					index : "state",
					align : "center",
					width : 100,
					formatter : function(cellvalue, options, rowObject) {
						if (cellvalue == 0)
							return "未处理";
						else if (cellvalue == 1)
							return "已处理";
						else
							return "未知";
					}

				}, {
					name : "createTime",
					index : "createTime"
				}<c:if test="${sessionScope.isOrganAdmin}">,{
					name : "_id",
					index : "_id",
					align : "center",
					formatter : function(
							cellvalue,
							options,
							rowObject) {
						if(rowObject.state == 0) {
							var v = "<a href='${ctxPath}/weixin/userpayFenzhang/handleFenzhangEnter.do?fenzhangId="
									+ cellvalue
									+ "'>处理</a>&nbsp;&nbsp;";
							return v;
						}else {
							return "";
						}
					}
				}</c:if> ]
			});
		});
	</script>
</body>
</html>
