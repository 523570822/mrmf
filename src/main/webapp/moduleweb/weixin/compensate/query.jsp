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
				 		<div class="jqGrid_wrapper">
							<table id="compensateTable"></table>
						</div>
				 	</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	$().ready(   function() {
							$("#compensateTable")
									.grid(
											{
												url : _ctxPath
														+ "/weixin/sysConfig/queryCompensate.do",
												shrinkToFit : false,
												colNames : [ "操作","赔付项目", "店铺/技师名称","申请者电话","赔付类型","赔付状态"
														,"申请赔付时间"],
												colModel : [
												         {
															name : "_id",
															index : "_id",
															align : "center",
															formatter : function(cellvalue,options,rowObject) {
															var v = "<a href='${ctxPath}/weixin/sysConfig/toCompensateDetail.do?compensateId="
															  + cellvalue + "'>赔付详情</a>";
															   return v;
															 }
														},
														{
															name : "service",
															index : "service",
															align : "center",
														},
														{
															name : "providerName",
															index : "providerName",
															align : "center"
														},
														{
															name : "phone",
															index : "phone",
															align : "center"
														},
														{
															name : "type",
															index : "type",
															align : "center",
														},
														{
															name : "state",
															index : "state",
															align : "center",
															formatter : function(cellvalue,options,rowObject) {
															     if(cellvalue == 0) {
															        return cellvalue = "待处理"; 
															     } else if(cellvalue == 1) {
															     	return cellvalue = "已处理"; 
															     }
															}
														}
														,
														{
															name : "createTime",
															index : "createTime",
															align : "center",
														}
														],
												 		gridComplete: function(){
							             				$(".ui-jqgrid-sortable").css("text-align", "center");  
							         		    },
								});
								
						});
	</script>
</body>
</html>
