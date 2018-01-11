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
									<input id="regex:content" name="regex:content" type="text"
										class="form-control" maxlength="20"
										placeholder="评论内容">
								</div>
								<div class="col-sm-2">
									<button id="search" class="btn btn-primary" type="submit">查询</button>
								</div>
								
							</div>
						</form>
						<div class="jqGrid_wrapper">
							<table id="userCommentTable"></table>
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
						

							$("#searchForm").submit(function() {
								$("#userCommentTable").reloadGrid({
									postData : $("#searchForm").formobj()
								});
								return false;
							});
							
							$("#userCommentTable")
									.grid(
											{
												url : _ctxPath
														+ "/weixin/verify/usercomment/query.do",
												postData : $("#searchForm")
														.formobj(),
												colNames : [ "操作","订单号","用户名称","店铺名称","技师名称","评论内容","评论时间" ],
												colModel : [
															{
																name : "_id",
																index : "_id",
																align : "center",
																formatter : function(
																		cellvalue,
																		options,
																		rowObject) {
																		var v = "<a href=\"javascript:delUserComment("
																		+ "'"
																		+ rowObject._id
																		+ "')\">删除"
																		+ "</a>&nbsp;&nbsp;"
																		
	
																	return v;
																}
															},
															{
															name : "orderId",
															index : "orderId",
															align : "center"
														},{
															name : "userName",
															index : "userId",
															align : "center",
														},
														{
															name : "organName",
															index : "organId",
															align : "center"
														},
														{
															name : "staffName",
															index : "staffId",
															align : "center"
														},
														{
															name : "content",
															index : "content",
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
											});

						});
						
		
		
		
						
		function delUserComment(commentId) {
			var url = _ctxPath + "/weixin/verify/usercomment/delcomment.do";
			$.post(url, {'commentId':commentId}, function(data) {
				if (data) {
					if (data.success) {
						layer.closeAll();
						toastr.success("操作成功");
					} else {
						toastr.error(data.message);
					}
					$("#userCommentTable").trigger("reloadGrid");
				} else {
					toastr.error("操作失败");
				}
			});
		}				
						
						
		
	</script>
</body>
</html>
