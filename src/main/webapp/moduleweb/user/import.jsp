<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/moduleweb/resources/common.jsp"%>
<html>
<head>
<title></title>
<style type="text/css">
.contentDiv {
	height: 500px;
}
</style>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content contentDiv">
						<c:if test="${returnStatus.status == 0}">
							<div class="alert alert-danger">${returnStatus.message}</div>
						</c:if>
						<div class="middle-box text-center">
							<h2 class="font-bold">请选择导入会员数据的Excel文件</h2>

							<div class="error-desc">
								可导入会员信息和会员卡数据，请按照指定格式编制Excel，参考"<a
									href="${ctxPath}/importUser.xlsx">导入模板</a>"<br />
								<button id="importUser" class="btn btn-success m-t"
									type="button">
									<i class="fa fa-upload"></i>&nbsp;&nbsp;<span class="bold">选择导入Excel</span>
								</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$().ready(function() {
			$("#importUser").click(function() {
				$('#onlyFile').click();
			});
		});
		function changeFile(_this) {
			$.shade.show();
			$("#fileForm").ajaxSubmit(
					{
						type : 'post',
						headers : {
							'type' : $(_this).attr("rel"),
							'isPublic' : 'true'
						},
						url : URL.fileupload,
						success : function(data) {
							//$.shade.hide();
							var fileId = data.data[0];
							toastr.success("文件上传成功，开始导入...");

							window.location.href = _ctxPath
									+ "/user/importUser/" + fileId + ".do";
						},
						error : function(XmlHttpRequest, textStatus,
								errorThrown) {
							$.shade.hide();
							toastr.success("文件上传失败");
						}
					});

		}
	</script>
	<div class="hidden">
		<form action="" id="fileForm">
			<input type="file" rel="msgImage" autocomplete="off" name="onlyFile"
				id="onlyFile" onchange="changeFile(this)" placeholder="File here" />
		</form>
	</div>
</body>
</html>
