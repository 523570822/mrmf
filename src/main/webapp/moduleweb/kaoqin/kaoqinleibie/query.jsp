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
				 		<button id="upsertKaoqinleibie"  class="btn btn-primary" >新增</button>
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
														+ "/kaoqin/queryKaoqinleibie.do",
												shrinkToFit : false,
												colNames : [  "操作","考勤类别名称", "基数1(分钟)",
														"扣罚(元)", "基数2(分钟)", "扣罚(元)",
														"是否一次性罚款","基数3(分钟)", "一次性扣罚(元)","备注"],
												colModel : [
														{ name : "_id",index : "_id",align : "center",width:"150px",
											               formatter : function(cellvalue,options,rowObject) {
																  var v = "<a href='${ctxPath}/kaoqin/upsertKaoqinleibie.do?kaoqinleibieId="
																      + cellvalue + "'>修改</a>&nbsp;&nbsp;";
																  if(rowObject.code==0) {
														              v += "<a style='color:red' onclick='deleteKaoqinleibie(\""+cellvalue+"\");'>删除</a>&nbsp;&nbsp;"; 
																  }
														          return v;
															 }
														},
														{
															name : "names",
															index : "names",
															align : "center",
															 width:"100px"
														},
														{
															name : "base1",
															index : "base1",
															align : "center",
															width:"100px"
														},
														{
															name : "money1",
															index : "money1",
															align : "center",
															width:"100px"
														},
														{
															name : "base2",
															index : "base2",
															align : "center",
															width:"100px"
														},
														{
															name : "money2",
															index : "money2",
															align : "center",
															width:"100px"
														},
														{
															name : "guding_flag",
															index : "guding_flag",
															align : "center",
															width:"100px",
															formatter : function(cellvalue,options,rowObject) {
																var v = "";
																if(cellvalue == true) {
																	v="是";
																} else {
																   v="否";
																}
															     return v;
															 }
														},
														{
															name : "base3",
															index : "base3",
															align : "center",
															width:"100px"
														},
														{
															name : "money3",
															index : "money3",
															align : "center",
															width:"100px"
														},
														{
															name : "note",
															index : "note",
															align : "center",
															width:"500px"
														}],
												 		gridComplete: function(){
							             				$(".ui-jqgrid-sortable").css("text-align", "center");  
							         		    },
								});
								
						});
						$("#upsertKaoqinleibie").click(function(){
							window.location.href = _ctxPath +"/kaoqin/upsertKaoqinleibie.do";
						});
					  function deleteKaoqinleibie(kaoqinleibieId) {
					 	   layer.confirm("你确定要删除该考勤类别码？",function(index) {
					 	   		window.location.href = _ctxPath +"/kaoqin/deleteKaoqinleibie.do?kaoqinleibieId=" + kaoqinleibieId;
					 	   });
					  }
	</script>
</body>
</html>
