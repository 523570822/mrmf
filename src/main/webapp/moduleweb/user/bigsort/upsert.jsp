<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/moduleweb/resources/common.jsp"%>
<html>
<head>
<title></title>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>
							<a href="${ctxPath}/user/bigsort/toQuery.do" class="btn-link">
								<i class="fa fa-angle-double-left"></i>返回
							</a>
							<c:if test="${ffbigsort._id == null}">
								新建服务大类
							</c:if>
							<c:if test="${ffbigsort._id != null}">修改服务大类
							</c:if>
						</h5>
					</div>
					<div class="ibox-content">
						<c:if test="${returnStatus.status == 0}">
							<div class="alert alert-danger">
								<i class="fa fa-exclamation-triangle"></i>${returnStatus.message}</div>
						</c:if>
						<input type="hidden" name="codeName" id="codeName" value="${ffbigsort.codeName}"/>
						<form id="bigsort" method="post"
							action="${ctxPath}/user/bigsort/upsert.do"
							class="form-horizontal">
							<input type="hidden" id="_id" name="_id"> <input
								type="hidden" id="organId" name="organId"
								value="${param.organId}">
							<input type="hidden"  id="codeIda" value="${ffbigsort.codeId}"/>
							<div class="form-group">
								<label class="col-sm-2 control-label">名称</label>
								<div class="col-sm-10">
									<input id="name" name="name" type="text" class="form-control"
										minlength="2" maxlength="50" required>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">店面类型</label>
								<div class="col-sm-10">
									<select name="typeName" id="typeName" class="form-control">
										<c:forEach items="${typeName}" var="tpName">
											<option value="${tpName}">${tpName}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">平台大类</label>
								<div class="col-sm-10">
									<select name="codeId" id="codeId" class="form-control">
									</select>
								</div>
							</div>
							<div class="hr-line-dashed"></div>
							<div class="form-group">
								<div class="col-sm-4 col-sm-offset-2">
									<button class="btn btn-primary" type="submit">提交</button>
									<button id="cancelBtn" class="btn btn-white" type="button">取消</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		var layerId;
		$().ready(function() {
			var functionIds = [], functionNames = [];
			if (fillmaps && fillmaps.bigsort) {
				var bigsort = fillmaps.bigsort;

			}

			$("#bigsort").validate({
				submitHandler : function(form) {
					$.shade.show();
					$("#bigsort").enable();

					form.submit();
				}
			});

			$("#cancelBtn").click(function() {
				document.location.href = _ctxPath + "/user/bigsort/toQuery.do";
			});
            $("#typeName").change(function () {
                var typeName = $("#typeName").val();
                var aa = $("#codeIda").val();
                $("#codeId").html( "<option value="+"请选择"+" >"+"请选择"+"</option>" );
                $.post("${ctxPath}/coupon/OperationController/queryCode.do",{"typeName":typeName},function (data) {//查店面服务大类
                    for (var i = 0; i < data.length; i++) {
                        $("#codeId").append( "<option value="+data[i]._id+">"+data[i].name+"</option>" );
                        if(data[i]._id==aa){
                            $("#codeId option[value="+data[i]._id+"]").attr("selected",true);
                        }
                    }
                })
            })
		});
		$("#codeId").change(function () {
			var codeName = $("#codeId option:selected").text();
		    $("#codeName").val(codeName);
        })

	</script>
</body>
</html>
