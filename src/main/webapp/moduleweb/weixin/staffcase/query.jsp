<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/moduleweb/resources/common.jsp"%>
<html>
<head>
<title></title>
<style>
.calendar{text-align:center;}
</style>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content  animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content">
						<form id="searchForm" method="get" class="form-horizontal">
							<div class="form-group">
							<label style="width:70px"class="col-sm-1 control-label">时间:</label>
								<div style="width:250px" class="col-sm-3">
									<input id="gte:createTime|datetime" name="gte:createTime|datetime"
										class="laydate-icon form-control" placeholder="开始日期">
								</div>
								<label style="width:10px" class="col-sm-1 control-label">至</label>
								<div  style="width:250px" class="col-sm-3">
									<input id="lte:createTime|datetime" name="lte:createTime|datetime"
										class="laydate-icon form-control" placeholder="结束日期">
								</div>
							<!-- <label class="col-sm-1 control-label"></label>
								<div id="date_pre" class="col-sm-1 btn btn-small btn-info glyphicon glyphicon-chevron-left"></div>
								<div id='calendar' class="col-sm-2 calendar">
									<label id="month" class=" control-label">十一月</label>
									<label id="year" class=" control-label">2016</label>
									<input type="hidden" id="hid_year" name="hid_year"/>
									<input type="hidden" id="hid_month" name="hid_month"/>
									<input type="hidden" id="type|integer" name="type|integer" value="0"/>
								</div>
								<div id="date_next" class="col-sm-1 btn btn-small btn-info glyphicon glyphicon-chevron-right"></div>
								 -->
								<div class="col-sm-3">
									<input id="regex:title" name="regex:title" type="text"
										class="form-control" maxlength="20"
										placeholder="案例标题">
								</div>
								<div class="col-sm-2">
									<button id="search" class="btn btn-primary" type="submit">查询</button>
								</div>
								
							</div>
						</form>
						<div class="jqGrid_wrapper">
							<table id="staffCaseTable"></table>
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
						var start = {
								elem : '#gte:createTime|datetime',
								format : 'YYYY-MM-DD hh:mm:ss',
							/* 	min : laydate.now(), //设定最小日期为当前日期 */
								max : '2099-06-16 23:59:59', //最大日期
								istime : true,
								istoday : false,
								choose : function(datas) {
									end.min = datas; //开始日选好后，重置结束日的最小日期
									end.start = datas; //将结束日的初始值设定为开始日
								}
							};
							var end = {
								elem : '#lte:createTime|datetime',
								format : 'YYYY-MM-DD hh:mm:ss',
							/* 	min : laydate.now(), */
								max : '2099-06-16 23:59:59',
								istime : true,
								istoday : false,
								choose : function(datas) {
									start.max = datas; //结束日选好后，重置开始日的最大日期
								}
							};
							laydate(start);
							laydate(end);
// 						getDate();
// 						$("#date_pre").click(function(){
// 							var year=$("#hid_year").val();
// 							var month=$("#hid_month").val();
// 							getDate("pre",year,month);
							
// 						});
// 						$("#date_next").click(function(){
// 							var year=$("#hid_year").val();
// 							var month=$("#hid_month").val();
// 							getDate("next",year,month);
							
// 						});
						
							$("#newBumen").click(
									function() {
										document.location.href = _ctxPath
												+ "/kucun/leibie/toUpsert.do";
									});

							$("#searchForm").submit(function() {
								$("#staffCaseTable").reloadGrid({
									postData : $("#searchForm").formobj()
								});
								return false;
							});
							
							$("#staffCaseTable")
									.grid(
											{
												url : _ctxPath
														+ "/weixin/verify/staffcase/query.do",
												postData : $("#searchForm")
														.formobj(),
												autowidth: true,
												colNames : [ "操作","技师姓名","案例类型","标题","案例介绍","案例价格","服务耗时","创建时间" ],
												colModel : [
															{
																name : "_id",
																index : "_id",
																align : "center",
																autowidth: true,
																formatter : function(
																		cellvalue,
																		options,
																		rowObject) {
																		var v="<a href='${ctxPath}/weixin/verify/staffcase/detail.do?caseId="
																			+ cellvalue
																			+ "'>详情</a>&nbsp;&nbsp;";
																		 v += "<a href=\"javascript:delStaffCase("
																		+ "'"
																		+ rowObject._id
																		+ "')\">删除"
																		+ "</a>&nbsp;&nbsp;"
																		
	
																	return v;
																}
															},
															{
															name : "staffName",
															index : "staffId",
															align : "center"
														},
														{
															name : "type",
															index : "type",
															align : "center"
														},
														{
															name : "title",
															index : "title",
															align : "center"
														},
														{
															name : "desc",
															index : "desc",
															align : "center"
														},{
															name : "price",
															index : "price",
															align : "center"
														},{
															name : "consumeTime",
															index : "consumeTime",
															align : "center"
														}/*,{
															name : "logo",
															index : "logo",
															align : "left",
															formatter : function(
																		cellvalue,
																		options,
																		rowObject) {
																		var img="";
																		for(var i=0;i<cellvalue.length;i++){
																			img+="<img style='width:200px;height:200px;' src='${ossImageHost}/"
																			+ cellvalue[i] + "@!style400'/>";
																			
																		}
																		return img;
																},width: 1500
														}*/
														,{ 
															name:"createTime",
															index:"createTime",
															align:"center"
														
														}
														 ],
														 gridComplete: function(){
							             	    $(".ui-jqgrid-sortable").css("text-align", "center");	
							             	    }
											});

						});
						
		
		
		
						
		function delStaffCase(caseId) {
			var url = _ctxPath + "/weixin/verify/staffcase/delcase.do";
			$.post(url, {'caseId':caseId}, function(data) {
				if (data) {
					if (data.success) {
						layer.closeAll();
						toastr.success("操作成功");
					} else {
						toastr.error(data.message);
					}
					$("#staffCaseTable").trigger("reloadGrid");
				} else {
					toastr.error("操作失败");
				}
			});
		}				
						
						
		function getDate(handle,year,month){
		var date;
		var m="";
			if(typeof(year)=='undefined'&&typeof(month)=='undefined'){
				date = new Date();
				year = date.getFullYear();
				 month = date.getMonth();
			}
			year=parseInt(year);
			month=parseInt(month);
		    if("next"==handle){
		    	if(month ==11){
		    		month =0;
		    		year = ++year;
		    	}else{
		    	   month = ++month;
		    	}
		    }else if("pre" == handle){
		    	if(month == 0){
		    		month = 11;
		    		year = --year;
		    	}else{
		    		month = --month;
		    	}
		    }
			switch(month){
					case 0:m='一月';break;
					case 1:m='二月';break;
					case 2:m='三月';break;
					case 3:m='四月';break;
					case 4:m='五月';break;
					case 5:m='六月';break;
					case 6:m='七月';break;
					case 7:m='八月';break;
					case 8:m='九月';break;
					case 9:m='十月';break;
					case 10:m='十一月';break;
					case 11:m='十二月';break;
				}
			$("#hid_year").val(year);
			$("#hid_month").val(month);
			$("#year").html(year);
			$("#month").html(m);
		}
	</script>
</body>
</html>
