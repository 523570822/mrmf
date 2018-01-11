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
					<c:if test="${!sessionScope.isOrganAdmin}">
						<input id="isorgan" type="hidden" value="false" />
						<div class="ibox-title"></div>
					</c:if>
					<div class="ibox-content">
						<form id="searchForm" method="get" class="form-horizontal">
							<c:if test="${sessionScope.isOrganAdmin}">
								<input id="organId" name="organId" type="hidden"
									value="${organId}">
							</c:if>
							<div class="form-group">
								<label class="col-sm-2 control-label">处理状态</label>
								<div class="col-sm-3">
									<c:choose>
										<c:when test="${!sessionScope.isOrganAdmin}">
											<select class="form-control" id="state|integer"
												name="state|integer">
												<option value="">请选择</option>
												<option value="0">未处理</option>
												<option value="1">已处理</option>
											</select>
										</c:when>
										<c:otherwise>
											<select class="form-control" id="organState|integer"
												name="organState|integer">
												<option value="">请选择</option>
												<option value="0">未处理</option>
												<option value="1">已处理</option>
											</select>
										</c:otherwise>
									</c:choose>

								</div>


								<div class="col-sm-3">
									<input id="gte:createTime|date" name="gte:createTime|date"
										class="laydate-icon form-control layer-date"
										placeholder="起始日期" data-mask="9999-99-99"
										laydate="{format : 'YYYY-MM-DD',min : '1938-06-16 23:59:59'}">
								</div>
								<div class="col-sm-3">
									<input id="lte:createTime|date+1" name="lte:createTime|date+1"
										class="laydate-icon form-control layer-date"
										placeholder="结束日期" data-mask="9999-99-99"
										laydate="{format : 'YYYY-MM-DD',min : '1938-06-16 23:59:59'}">
								</div>
							</div>
							<div class="form-group">
								<c:if test="${!sessionScope.isOrganAdmin}">
									<label class="col-sm-2 control-label">公司名称</label>
									<div class="col-sm-3">
										<input id="organName" name="organName" type="text"
											class="form-control">
									</div>
								</c:if>
								<div class="form-group">
									<div class="col-sm-3">
										<label class="col-sm-5 control-label"></label>
										<button id="search" class="btn btn-primary" type="submit">查询</button>
									</div>
									<div class="col-sm-3">
										<button id="dealwith" class="btn btn-outline btn-danger"
											type="button">批量处理</button>
									</div>
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-5">
									总金额汇总：<font color="red" id="total"></font>
								</div>
						</div>
						</form>
						
						<div class="jqGrid_wrapper">
							<table id="cardTable"></table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		var manageType;
		$().ready(function() {
			$("#dealwith").click(
									function() {
										var table = $("#cardTable").jqGrid("getRowData");
										if(table.length==0){
											toastr.error("没有可处理的信息！");
											return;
										}
										dealwith();
									});
			/*function getSum() {
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
			*/
			getSum();
			$("#searchForm").submit(function() {
				$("#cardTable").reloadGrid( {
					postData : $("#searchForm").formobj()
				});
				getSum();
				return false;
			});

			$("#cardTable").grid({
				url : _ctxPath + "/card/payOnline/query.do",
				postData : $("#searchForm").formobj(),
				shrinkToFit : false,
				colNames : [ "用户", "店铺名称", "充值金额" ,"银行账户名称","银行账号","开户银行","状态", "发生时间","操作" ],
				colModel : [ {
					name : "userName",
					width : 80,
					align : "center"
				}, {
					name : "organName",
					index : "organId",
					align : "center"
				}, {
					name : "money1",
					width : 80,
					align : "center"
				}, {
					name : "bankAccountName",
					width : 180,
					align : "center"
				}, {
					name : "bankAccount",
					align : "center"
				}, {
					name : "bankKaihu",
					width : 180,
					align : "center"
				},<c:choose> 
        				<c:when test="${!sessionScope.isOrganAdmin}">
        							{
										name : "state",
										width : 60,
										align : "center",
										formatter : function(cellvalue, options, rowObject) {
											if (cellvalue == 0)
												return "未处理";
											else if (cellvalue == 1)
												return "已处理";
											else
												return "未知";
										}
						</c:when>
						<c:otherwise>
									{
										name : "organState",
										width : 80,
										align : "center",
										formatter : function(cellvalue, options, rowObject) {
											if (cellvalue == 0)
												return "未处理";
											else if (cellvalue == 1)
												return "已处理";
											else
												return "未知";
										}
						</c:otherwise>
				</c:choose>
				

				}, {
					name : "createTime",
					index : "createTime",
					align : "center"
				},{
					name : "_id",
					index : "_id",
					align : "center",
					formatter : function(
							cellvalue,
							options,
							rowObject) {
							var v="";
							<c:choose> 
	        				<c:when test="${!sessionScope.isOrganAdmin}">
									if(rowObject.state==0){
								 v= "<a href='javascript:void(0);'onclick='dealwith(\""
										+ cellvalue
										+ "\")'>处理</a>&nbsp;&nbsp;";
								}
							</c:when>
							<c:otherwise>
								if(rowObject.organState==0){
								 v= "<a href='javascript:void(0);'onclick='dealwith(\""
										+ cellvalue
										+ "\")'>处理</a>&nbsp;&nbsp;";
								}
							</c:otherwise>
							</c:choose>
							
								
							return v;
					}
				} ],gridComplete : function() {
													$(".ui-jqgrid-sortable")
															.css("text-align",
																	"center");
												}
			});
		});
		function dealwith(cardId){
			var url = _ctxPath + "/card/dealwith.do";
			$.post(url, getHandleData(cardId), function(data) {
				if (data) {
					if (data.success) {
						toastr.success("操作成功");
					} else {
						toastr.error(data.message);
					}
					$("#cardTable").trigger("reloadGrid");
				} else {
					toastr.error("操作失败");
				}
			});
		}
		function getHandleData(id) {
								var o = $("#searchForm").formobj(), oo = {};
								console.log(o);
								if(typeof(id)!='undefined'){
								    oo.cardId=id;
								    oo.only=true;
								}
								if ($("#isorgan").val() == "false") {
									oo.stateType="state";
									oo.state = o["state|integer"];
									oo.organName=o.organName;
								} else {
									oo.stateType="organState";
									oo.state = o["organState|integer"];
									
								}
								
								oo.startTime = o["gte:createTime|date"];
								oo.endTime = o["lte:createTime|date+1"];
								console.log(oo);
								return oo;
							}
	function getSum(){
	var o = $("#searchForm").formobj();
				o.startTime = o["gte:createTime|date"];
				o.endTime = o["lte:createTime|date+1"];
				o.state = o["state|integer"];
				o.organState=o["organState|integer"];
				o.organName=o["organName"];
				$.post("${ctxPath}/card/totalOrgan.do",
						o, function(data, status) {
							$("#total").text(data.total);
						});
	}
	</script>
</body>
</html>
