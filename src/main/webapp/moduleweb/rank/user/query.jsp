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
	<div class="wrapper wrapper-content  animated fadeInRight" style="height:99%;">
		<div class="row" style="height:100%;">
			<div class="col-sm-12" style="height:100%;">
				<div class="ibox float-e-margins" style="height:100%;">
					<div class="ibox-content" style="height:100%;">
						<form id="searchForm" method="get" class="form-horizontal" style="height:10%;">
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
									<input id="condition" name="condition" type="text"
										class="form-control" maxlength="20"
										placeholder="姓名、昵称、电话进行查询">
								</div>
								<div class="col-sm-2">
									<button id="search" class="btn btn-primary" type="submit">查询</button>
								
									<button class="btn btn-danger" id="export" name="export"
										type="button">
										<strong>导出</strong>
									</button>
						
								</div>
								
							</div>
						</form>
						<div class="jqGrid_wrapper" style="height:90%;">
							<table id="userRankTable" ></table>
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
							
							 $('#export')
							.click(
									function() {
										var param = $("#searchForm")
												.formobj();
										var o = {};
										o.condition = param["condition"];
										o.startTime = param["gte:createTime|datetime"];
										o.endTime = param["lte:createTime|datetime"];
										var parStr = jQuery.param(o);
										//console.log(param);
										window.location.href = _ctxPath
												+ "/rank/exportUserRank.do?"
												+ parStr;
										//$("#searchForm").attr("action", _ctxPath + "/weixin/sys/user/exportUser.do").submit();
									});
							 
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
								$("#userRankTable").reloadGrid({
									postData : $("#searchForm").formobj()
								});
								return false;
							});
							$("#userRankTable")
									.grid(
											{
												url : _ctxPath
														+ "/rank/user/query.do",
												postData : $("#searchForm")
														.formobj(),
												rownumbers:true,
												colNames : [ "操作","姓名","店铺名称","消费金额","消费时间","用户电话" ],
												colModel : [
															{
																name : "_id",
																index : "_id",
																align : "center",
																formatter : function(
																		cellvalue,
																		options,
																		rowObject) {
																	/*var v = "<a href='javascript:void(0);' onclick='sendMessage(\""
																			+ cellvalue
																			+ "\")'>推送消息</a>";*/
																		var v = "<a href=\"javascript:toSendMessage("
																		+ "'"
																		+ rowObject._id
																		+ "')\">发送消息"
																		+ "</a>&nbsp;&nbsp;"
																		+ "<a href=\"javascript:toRecharge("
																		+ "'"
																		+ rowObject._id
																		+ "')\">奖励"
																		+ "</a>&nbsp;&nbsp;";
	
																	return v;
																}
															},
															{
															name : "userName",
															index : "userId",
															align : "center"
														},
														{
															name : "organName",
															index : "organId",
															align : "center"
														},
														{
															name : "price",
															index : "price",
															align : "center"
														},
														{
															name : "createTime",
															index : "createTime",
															align : "center"
														},{
															name : "phone",
															index : "phone",
															align : "center"
														}
														 ],
												gridComplete: function(){
							             	    $(".ui-jqgrid-sortable").css("text-align", "center");	
							             	    }	
											});
											var newHeight = $(".jqGrid_wrapper").height()*0.85;
											$(".ui-jqgrid .ui-jqgrid-bdiv").css("cssText","height: "+newHeight+"px!important;");

						});
						
		function toSendMessage(orderId) {
							 layer
									.open({
										type : 1,
										skin : 'layui-layer-rim', //加上边框
										area : [ '420px', '240px' ], //宽高
										content : '<div class="row"><div class="col-sm-12"><div class="ibox float-e-margins"><form class="form-horizontal"><div class="ibox-content">'
												+'<div class="form-group"><input type="hidden" id="orderId" name="orderId" value="'+orderId+'"/><label class="col-sm-3 control-label">活动</label>'
												+'<div class="col-sm-5"><input id="activity" name="activity" type="text" class="form-control"minlength="2" maxlength="50" required></div>'
												+'</div><div class="form-group"><label class="col-sm-3 control-label">奖品</label><div class="col-sm-5"><input id="prize" name="prize" '
												+'type="text" class="form-control" minlength="2" maxlength="50" required></div><div class="col-sm-3"><button id="search" class="btn btn-primary" type="button" onclick="sendMessage()">发送</button></div></div>'
												+'</div></from></div></div></div>'
									});
						}
		function sendMessage() {
		var orderId=$("#orderId").val();
		var activity=$("#activity").val();
		var prize=$("#prize").val();
		if(activity==""){
			toastr.error("活动不能为空");
			return;
		}
		if(prize==""){
		 toastr.error("奖品不能为空");
			return;
		 }
			var url = _ctxPath + "/rank/user/sendMessage.do";
			$.post(url, {'orderId':orderId,'activity':activity,'prize':prize}, function(data) {
				if (data) {
					if (data.success) {
						layer.closeAll();
						toastr.success("操作成功");
					} else {
						toastr.error(data.message);
					}
					//$("#leibieTable").trigger("reloadGrid");
				} else {
					toastr.error("操作失败");
				}
			});
		}
		function toRecharge(rorderId) {
							 layer
									.open({
										type : 1,
										skin : 'layui-layer-rim', //加上边框
										area : [ '420px', '240px' ], //宽高
										content : '<form>'
												+ '<div class="form-group"><input type="hidden" id="rorderId" name="rorderId" value="' + rorderId + '"><label class="col-sm-3 control-label" style="text-align: right;">奖励金额：</label>'
												+ '<div class="col-sm-4"><input id="price" name="price" type="number" min="0" class="form-control" value=""></div>'
												+ '<div class="col-sm-3"><button id="search" class="btn btn-primary" type="button" onclick="recharge()">确定</button></div>'
												+ '</div></from>'
									});
						}
						
		function recharge() {
		var rorderId=$("#rorderId").val();
		var price=$("#price").val();
		var regex = /^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/;
		if(!regex.test(price)){
		 toastr.error("金额格式不正确");
			return;
		 }
			var url = _ctxPath + "/rank/user/recharge.do";
			$.post(url, {'rorderId':rorderId,'price':price}, function(data) {
				if (data) {
					if (data.success) {
						layer.closeAll();
						toastr.success("操作成功");
					} else {
						toastr.error(data.message);
					}
					//$("#leibieTable").trigger("reloadGrid");
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
