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
							<table id="staffCheckTable"></table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$().ready(
		 	function() { 
				 $("#staffCheckTable").grid(
						{
							url : _ctxPath+ "/weixin/organ/user/queryStaffCheckList.do",
							colNames : [ {name:"全选",align:"center"}, "技师手机号", "技师姓名", "性别","申请时间","操作" ],
							colModel : [  {name:'id',index:'id',width:0,hidden:true, hidedlg:true,align:"left", 
							                formatter : function(cellvalue,options,rowObject) {
																var v = rowObject._id;
																return v;}
														},
										{name : "phone", index : "phone",align : "center"},
										{name : "name",index : "name",align : "center"},
										{name : "sex",index : "sex",align : "center"},
										{name : "updateTime",index : "updateTime",align : "center",},
										{name : "_id",index : "_id",align : "center",
											 formatter : function(cellvalue,options,rowObject) {
												var v = "<a href='${ctxPath}/weixin/organ/user/toCheckStaff.do?staffId="
															+ cellvalue
															+ "'>审核</a>&nbsp;&nbsp;";
												return v;
											 }
										}],
										//额外的参数配置都写在这里
							    		gridComplete: function(){
							             	$(".ui-jqgrid-sortable").css("text-align", "center");  
							         	}
							});
		                 }); 
	 </script>
</body>
</html>
