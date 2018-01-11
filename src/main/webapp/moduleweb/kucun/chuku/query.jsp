<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/moduleweb/resources/common.jsp"%>
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
							<div class="form-group">
								<label class="col-sm-2 control-label">产品名称</label>
								<div class="col-sm-2">
									<input id="regex:mingcheng" name="regex:mingcheng" type="text"
										class="form-control">
								</div>
								<label class="col-sm-1 control-label">单号</label>
								<div class="col-sm-2">
									<input id="regex:danhao" name="regex:danhao" type="text"
										class="form-control">
								</div>
								<div class="col-sm-2">
									<button id="search" class="btn btn-primary" type="submit">查询</button>
								</div>
								<div class="col-sm-2">
									<button id="newBumen" class="btn btn-outline btn-danger"
										type="button">出库</button>
								</div>
							</div>
						</form>
						<div class="jqGrid_wrapper">
							<table id="chukuTable"></table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		var manageType;
		$()
				.ready(
						function() {
							$("#newBumen").click(
									function() {
										document.location.href = _ctxPath
												+ "/kucun/chuku/toUpsert.do";
									});

							$("#searchForm").submit(function() {
								$("#chukuTable").reloadGrid({
									postData : $("#searchForm").formobj()
								});
								return false;
							});
							$("#chukuTable")
									.grid(
											{
												url : _ctxPath
														+ "/kucun/chuku/query.do",
												shrinkToFit : false,
												postData : $("#searchForm")
														.formobj(),
												colNames : [ "操作","单号","物品类别","产品编码","产品名称","进货价格","数量","总价","产品产地","员工","电话","产品公司地址","领用人","备注","外卖出库","净含量","总量","部门","助记符","出库时间","是否挂账","出货到公司","组盘号","规格","出库审核","销售价格","销售总价","品牌","分公入库审核","日期" ],
												colModel : [
															{
																name : "_id",
																index : "_id",
																align : "center",
																formatter : function(
																		cellvalue,
																		options,
																		rowObject) {
																		//console.log(options);
																		//console.log(rowObject.shenhe);
																		var v="";
																		if(!rowObject.shenhe){
// 																	var v = "<a href='${ctxPath}/kucun/leibie/toUpsert.do?leibieId="
// 																			+ cellvalue
// 																			+ "'>详情</a>&nbsp;&nbsp;";
	
																	 v = "<a href='javascript:void(0);' onclick='removeChuku(\""
																			+ cellvalue
																			+ "\")'>删除</a>";
																	}
																	return v;
																}
															},
															{
															name : "danhao",
															index : "danhao",
															align : "center"
														},
														{
															name : "wupinName",
															index : "wupinId",
															align : "center"
														},
														{
															name : "code",
															index : "code",
															align : "center"
														},{
															name : "mingcheng",
															index : "mingcheng",
															align : "center"
														},
														{
															name : "price",
															index : "price",
															align : "center"
														},
														{
															name : "num",
															index : "num",
															align : "center"
														},
														{
															name : "price_all",
															index : "price_all",
															align : "center"
														},
														{
															name : "place1",
															index : "place1",
															align : "center"
														},
														{
															name : "staff",
															index : "staff",
															align : "center"
														},
														{
															name : "phone",
															index : "phone",
															align : "center"
														},
														{
															name : "place2",
															index : "place2",
															align : "center"
														},
														{
															name : "lingyong",
															index : "lingyong",
															align : "center"
														},
														{
															name : "note",
															index : "note",
															align : "center"
														},
														{
															name : "flagName",
															index : "flag",
															align : "center"
														},
														{
															name : "weight",
															index : "weight",
															align : "center"
														},{
															name : "weight_all",
															index : "weight_all",
															align : "center"
														},{
															name : "bumenName",
															index : "bumen",
															align : "center"
														},{
															name : "zjfCode",
															index : "zjfCode",
															align : "center"
														},{
															name : "come_time",
															index : "come_time",
															align : "center"
														},{
															name : "guizhangName",
															index : "guazhang_flag",
															align : "center"
														},{
															name : "danweiname",
															index : "danweiname",
															align : "center"
														},{
															name : "zupanhao",
															index : "zupanhao",
															align : "center"
														},{
															name : "guige",
															index : "guige",
															align : "center"
														},{
															name : "shenheName",
															index : "shenhe",
															align : "center"
														},{
															name : "price_xs",
															index : "price_xs",
															align : "center"
														},{
															name : "price_all_xs",
															index : "price_all_xs",
															align : "center"
														},{
															name : "pinpaiName",
															index : "pinpai",
															align : "center"
														},{
															name : "shenhefenName",
															index : "shenhe_fen",
															align : "center"
														},{
															name : "createTime",
															index : "createTime",
															align : "center"
														}
												 ],
												gridComplete: function(){
							             	    $(".ui-jqgrid-sortable").css("text-align", "center");	
							             	    }		 
											}
											);
											 
						});
		function removeChuku(chukuId) {
			var url = _ctxPath + "/kucun/chuku/remove/" + chukuId + ".do";
			$.get(url, {}, function(data) {
				if (data) {
					if (data.success) {
						toastr.success("操作成功");
					} else {
						toastr.error(data.message);
					}
					$("#chukuTable").trigger("reloadGrid");
				} else {
					toastr.error("操作失败");
				}
			});
		}
	</script>
</body>
</html>
