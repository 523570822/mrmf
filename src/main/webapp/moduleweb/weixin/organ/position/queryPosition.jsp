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
							<table id="positionCheckTable"></table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$().ready(
		 	function() {
				 $("#positionCheckTable").grid(
						{
							url : _ctxPath+ "/weixin/organ/user/queryPositionList.do",
							colNames : [ {name:"全选",align:"center"}, "技师名称", "预约时间段", "预约总天数","申请时间","申请状态","操作" ],
							colModel : [  {name:'id',index:'id',width:0,hidden:true, hidedlg:true,align:"left",
							                formatter : function(cellvalue,options,rowObject) {
																var v = rowObject._id;
																return v;}
														},
										{name : "staffName", index : "staffName",align : "center"},
										{name : "timeString",index : "timeString",align : "center"},
										{name : "totalDay",index : "totalDay" ,align: "center"},
										{name : "createTime",index : "createTime",align : "center",},
										{name : "state",index : "state",align : "center",
                                            formatter : function(cellvalue,options,rowObject) {
                                                if( '0' == rowObject.state){
                                                    return "待审核";
												}if('1' ==rowObject.state ){
                                                    return "已通过";
												}else {
                                                    return "未通过";
												}
                                            }
										},
										{name : "_id",index : "_id",align : "center",
											 formatter : function(cellvalue,options,rowObject) {
												 var v = "<a href='${ctxPath}/weixin/organ/user/toCheckPosition.do?id="
															+ cellvalue + "'>审核</a>&nbsp;&nbsp;";

                                                 if( '0' == rowObject.state){
                                                     return v;
                                                 }else{
                                                     var f = "";
                                                     return f;
												 }

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
