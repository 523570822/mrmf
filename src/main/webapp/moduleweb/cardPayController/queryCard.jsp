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
								
								<label class="col-sm-2 control-label">城市</label>
								<div class="col-sm-2">
									<select class="form-control" id="city" name="city">
										<option value="">请选择</option>
										<c:forEach items="${ffcitys}" var="city">
										<option value="${city._id }">${city.name }</option>
										</c:forEach>
									</select>
								</div>
								<label class="col-sm-1 control-label">区域</label>
								<div class="col-sm-2">
									<select class="form-control" id="district" name="district">
										<option value="">请选择</option>
									</select>
								</div>
								<label class="col-sm-1 control-label">商圈</label>
								<div class="col-sm-2">
									<select class="form-control" id="region" name="region">
										<option value="">请选择</option>
									</select>
								</div>
								
							</div>
							
							<div class="form-group">
							<label class="col-sm-2 control-label">店铺名称</label>
								<div class="col-sm-2">
									<input id="name" name="name" type="text"
										class="form-control">
								</div>
								<label class="col-sm-1 control-label"></label>
								<div class="col-sm-2">
									<button id="search" class="btn btn-primary" type="submit">查询</button>
								</div>
								</div>
						</form>
						<div class="jqGrid_wrapper">
							<table id="cardTable"></table>
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
							$("#newCode")
									.click(
											function() {
												document.location.href = _ctxPath
														+ "/code/toUpsert.do?type=${param.type}";
											});

							$("#searchForm").submit(function() {
								$("#cardTable").reloadGrid({
									postData : $("#searchForm").formobj()
								});
								return false;
							});
							$("#cardTable")
									.grid(
											{
												url : _ctxPath
														+ "/card/organCard/query.do",
												postData : $("#searchForm")
														.formobj(),
												colNames : [ "公司名称", "删除数量","正在使用数量" ],
												colModel : [
														{
															name : "name",
															index : "name"
														},{
															name : "delNum",
															index : "delNum"
														},{
															name : "num",
															index : "num"
														}
														]
											});
											
											
						$("#city").change(
											function() {
												var t = $("#city")[0];
												var v = t.options[t.selectedIndex];
												if (v.value) {
													$.post('${ctxPath}/weixin/s/queryDistrict.do',
																	{
																		cityId : v.value
																	},
																	function(
																			data,
																			status) {
																			console.log(data.length);
																		var c = $("#district")[0],ds=[];
																		c.options.length = 1;
																		for (var i = 0; i < data.length; i++) {
																			var d = data[i];
																			var option = new Option(
																					d.name,
																					d.name);
																			option.value = d._id;
																			c.options[c.options.length] = option;
																			$(c).trigger('change');
																		}
																	});
												} else {
													$("#district")[0].options.length = 1;
												}
											});
											
											
											$("#district").change(function() {
												var t = $("#district")[0];
												var v = t.options[t.selectedIndex];
												console.log(v);
												if (v.value) {
													$.post('${ctxPath}/weixin/s/queryRegion.do',
																	{
																		districtId : v.value
																	},
																	function(
																			data,
																			status) {
																		var c = $("#region")[0];
																		c.options.length = 1;
																		for (var i = 0; i < data.length; i++) {
																			var d = data[i];
																			var option = new Option(
																					d.name,
																					d.name);
																			option.value = d._id;
																			c.options[c.options.length] = option;
																		}
																	});
												} else {
													$("#region")[0].options.length = 1;
												}
											});					

						});
		function removeCode(codeId) {
			layer.confirm('确定要删除代码?', function(index) {
				layer.close(index);
				var url = _ctxPath + "/code/remove/" + codeId + ".do";
				$.get(url, {}, function(data) {
					if (data) {
						if (data.success) {
							toastr.success("操作成功");
						} else {
							toastr.error(data.message);
						}
						$("#codeTable").trigger("reloadGrid");
					} else {
						toastr.error("操作失败");
					}
				});
			});

		}
	</script>
</body>
</html>
