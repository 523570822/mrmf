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
						<form id="searchForm" method="get" class="form-horizontal">
							<div class="form-group">
								<label class="col-sm-2 control-label">距离生日天数区间</label>
								<div class="col-sm-3">
									<input id="day" name="day" type="number" min="0"
										class="form-control">
								</div>
								<div class="col-sm-2">
									<button id="search" class="btn btn-primary" type="submit">查询</button>
								</div>
								
								<div class="col-sm-2">
									<button id="newBumen" class="btn btn-outline btn-danger"
										type="button">发送信息</button>
								</div>
							</div>
						</form>
						<div class="jqGrid_wrapper">
							<table id="vipUserTable"></table>
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
							$("#newBumen").click(
									function() {
										sendMessage();
									});

							$("#searchForm").submit(function() {
								var day=$("#day").val();
								if(day<0){
									toastr.error("距离生日的天数不正确！");
									return;
								}
								loadAll();
								return false;
							});
							function loadAll(){
							$.post("${ctxPath}/vipuser/query.do", $(
								"#searchForm").formobj(), function(data) {
							$.shade.hide();
							$("#vipUserTable").jqGrid('clearGridData');
							if (data) {
								for (var i = 0; i < data.length; i++) {
									var d = data[i];
									$("#vipUserTable").jqGrid('addRowData', i, d);
								}
							}
						});
						}
							$("#vipUserTable")
									.grid(
											{
												datatype : "local",
														shrinkToFit : false,
												postData : $("#searchForm")
														.formobj(),
												rowNum  :'all',
												//pgbuttons:false,
												pager : null,
												scroll:true,
												colNames : ["会员姓名","性别","生日","首次办卡时间","电话","居住地","备注" ],
												colModel : [
															/*{
																name : "_id",
																index : "_id",
																align : "center",
																formatter : function(
																		cellvalue,
																		options,
																		rowObject) {
																	var v = "<a href='${ctxPath}/kucun/leibie/toUpsert.do?leibieId="
																			+ cellvalue
																			+ "'>详情</a>&nbsp;&nbsp;";
	
																	v += "<a href='javascript:void(0);' onclick='removeLeibie(\""
																			+ cellvalue
																			+ "\")'>删除</a>";
	
																	return v;
																}
															},*/
															
														/*<c:choose> 
										        				<c:when test="${nick==null}">
										        				{
																	name : "name",
																	index : "name"
																},
										        				</c:when>
																<c:otherwise>
																{
																	name : "nick",
																	index : "nick"
																},
																</c:otherwise>
														</c:choose>*/
														{
																	name : "name",
																	index : "name"
																},
														{
															name : "sex",
															index : "sex"
														},
														{
															name : "birthday",
															index : "birthday"
														},
														{
															name : "createTime",
															index : "createTime"
														},
														{
															name : "phone",
															index : "phone"
														},
														{
															name : "place",
															index : "place"
														},
														{
															name : "love",
															index : "love"
														}
														 ]
											});
										loadAll();
						});
		function sendMessage() {
		var table = $("#vipUserTable").jqGrid("getRowData");
		
		if(table.length==0){
			toastr.error("没有可发送消息的用户！");
			return;
		}
		var day=$("#day").val();
		
			var url = _ctxPath + "/vipuser/sendMessage.do.do";
			$.get(url, {'day':day}, function(data) {
				if (data) {
					if (data.success) {
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
	</script>
</body>
</html>
