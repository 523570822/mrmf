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
							<table id="userTable"></table>
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
							$("#userTable")
									.grid(
											{
												url : _ctxPath
														+ "/weixin/organ/user/staffToAudit.do",
												colNames : [ "全选", "技师手机号", "技师姓名", "性别", "工作年限", "所属店铺", "操作" ],
												colModel : [
														{name:'id',index:'id',width:0,hidden:true, hidedlg:true,align:"left",
															formatter : function(
																	cellvalue,
																	options,
																	rowObject) {
																var v = rowObject._id;

																return v;
															}
														},
														{
															name : "phone",
															index : "phone",
															align : "center"
														},
														{
															name : "name",
															index : "name",
															align : "center"
														},
														{
															name : "sex",
															index : "sex",
															align : "center"
														},
														{
															name : "workYears",
															index : "workYears",
															align : "center"
														},
														{
															name : "organName",
															index : "organName",
															align : "center"
														},
														{
															name : "_id",
															index : "_id",
															align : "center",
															formatter : function(
																	cellvalue,
																	options,
																	rowObject) {
																var v = "<a href='${ctxPath}/account/toUpsert.do?accountId="
																			+ cellvalue
																			+ "'>详情</a>&nbsp;&nbsp;";

																return v;
															}
														}],
            									multiselect: true, // 是否显示复选框
            									gridComplete: function(){
							            			 $(".ui-jqgrid-sortable").css("text-align", "center");  
							         			 }
											});

						});
						
		
	</script>
</body>
</html>
