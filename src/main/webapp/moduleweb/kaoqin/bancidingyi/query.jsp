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
						 <button class="btn btn-primary" id="addBanci" type="button"><strong>新增</strong></button>
						 <div class="jqGrid_wrapper">
								<table id="banciTable"></table>
						 </div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		
		$().ready(
						function() {
							$("#banciTable")
									.grid(
											{
												url : _ctxPath
														+ "/kaoqin/queryBanci.do",
												shrinkToFit : false,
												colNames : [ "序号", "班次名称", "上班时间",
														"是否考勤", "下班时间", "是否考勤",
														"是否跨天", "操作"],
												colModel : [
														{
															name : "_id",
															index : "_id",
															align : "center",
															width:"50%",
															hidden:true, hidedlg:true
														},
														{
															name : "names",
															index : "names",
															align : "center",
														},
														{
															name : "time_a1",
															index : "time_a1",
															align : "center"
														},
														{
															name : "kaoqin_a1",
															index : "kaoqin_a1",
															align : "center",
															formatter:function(cellvalue,options,rowObject) {
																		    if(cellvalue == true) {
																		    	return "考勤";
																		    } else {
																		       return "不考勤";
																		    }	
																		}
														},
														{
															name : "time_a2",
															index : "time_a2",
															align : "center"
														},
														{
															name : "kaoqin_a2",
															index : "kaoqin_a2",
															align : "center",
															formatter : function(cellvalue,options,rowObject) {
																		    if(cellvalue == true) {
																		    	return "考勤";
																		    } else {
																		       return "不考勤";
																		    }	
																		}
														},
														{
															name : "kuatian",
															index : "kuatian",
															align : "center",
															formatter : function(cellvalue,options,rowObject) {
																		    if(cellvalue == true) {
																		    	return "跨天";
																		    } else {
																		       return "不跨天";
																		    }	
																		}
														},
														{ name : "_id",index : "_id",align : "center",
											               formatter : function(cellvalue,options,rowObject) {
															var v = "<a href='${ctxPath}/kaoqin/addBanci.do?banciId="
															+ cellvalue
															+ "'>修改</a>&nbsp;&nbsp;";
															v += "<a style='color:red' onclick='deleteBanci(\""+ cellvalue
												              + "\")'>删除</a>&nbsp;&nbsp;";
															return v;
															 }, width:"120px"
														}],
												gridComplete: function(){
							             	$(".ui-jqgrid-sortable").css("text-align", "center");  
							         	}
								});
						});
						$("#addBanci").click(function(){
							window.location.href = _ctxPath + "/kaoqin/addBanci.do";
						});
						/*  删除班次  */
						function deleteBanci(banciId) {
							layer.confirm('你确定要删除这个班次吗?', function(index){
								window.location.href =  _ctxPath + "/kaoqin/deleteBanci.do?banciId="+ banciId;
  								layer.close(index);
  							}); 
						}
	</script>
</body>
</html>
