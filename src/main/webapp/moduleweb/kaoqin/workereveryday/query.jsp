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
				 		<button id="add" class="btn btn-primary" type="button">新增请假登记</button>
				 		<div class="jqGrid_wrapper">
							<table id="kaoqinleibieTable"></table>
						</div>
				 	</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	          $().ready(   function() {
							$("#kaoqinleibieTable")
									.grid(
											{
												url : _ctxPath
														+ "/kaoqin/queryQingjiadengji.do",
												shrinkToFit : false,
												colNames : [ "操作", "姓名", "请假开始日期",
														"请假结束日期", "请假类型", "是否早班",
														"是否晚班", "扣罚金额","请假理由"],
												colModel : [
														{ name : "_id",index : "_id",align : "center",
											               formatter : function(cellvalue,options,rowObject) {
															var v = "<a href='${ctxPath}/kaoqin/upsertQingjiadengji.do?qingjiadengjiId="
															+ cellvalue + "'>修改</a> &nbsp;&nbsp;";
													          v += "<a style='color:red' onclick='deleteQingjiadengji(\""+cellvalue+"\");'>删除</a>&nbsp;&nbsp;"; 
															   return v;
															 }
														},
														{
															name : "staffNames",
															index : "staffNames",
															align : "center",
														},
														{
															name : "date1",
															index : "date1",
															align : "center"
														},
														{
															name : "date3",
															index : "date3",
															align : "center"
														},
														{
															name : "type1Names",
															index : "type1Names",
															align : "center"
														},
														{
															name : "zao_flag",
															index : "zao_flag",
															align : "center",
															formatter : function(cellvalue,options,rowObject) {
																   var v;
															       if(cellvalue == true) {
															        	v = "是";
															       } else {
															       		v = "否";
															       }
																   return v;
															 }
														},
														{
															name : "wan_flag",
															index : "wan_flag",
															align : "center",
															formatter : function(cellvalue,options,rowObject) {
																   var v;
															       if(cellvalue == true) {
															        	v = "是";
															       } else {
															       		v = "否";
															       }
																   return v;
															 }
														},
														{
															name : "money_koufa",
															index : "money_koufa",
															align : "center",
														},
														{
															name : "reason",
															index : "reason",
															align : "center",
														}],
												gridComplete: function(){
							             				$(".ui-jqgrid-sortable").css("text-align", "center");  
							         		    },
								});
						});
				$('#add').click(function() {
					window.location.href = _ctxPath +"/kaoqin/upsertQingjiadengji.do";
				});
			
				function deleteQingjiadengji(qingjiadengjiId) {
					layer.confirm("你确定要删除这条请假登记吗？",function(index) {
						window.location.href = _ctxPath +"/kaoqin/deleteQingjiadengji.do?qingjiadengjiId="+ qingjiadengjiId;
						layer.close(index);
					});			
		     	}
	</script>
</body>
</html>
