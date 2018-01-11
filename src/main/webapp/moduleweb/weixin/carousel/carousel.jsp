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
						<button class="btn btn-primary" onclick="selectImg()">上传轮播图</button>
						<div class="jqGrid_wrapper">
						  <table id="carouselTable"></table>
					    </div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div style="display:none" >
		<form action="" id="fileForm">
			<input type="file" rel="msgImage" autocomplete="off" name="onlyFile"
				id="onlyFile" onchange="changeFile(this)" placeholder="File here" />
		</form>
	</div>
	<script type="text/javascript">
		function selectImg() {
			   $('#onlyFile').click();
		    }
			function changeFile(_this) {
				$.shade.show();
				$("#fileForm").ajaxSubmit({
					type : 'post',
					headers : {
						'type' : $(_this).attr("rel"),
						'isPublic' : 'true'
					},
					url : URL.fileupload,
					success : function(data) {
						$.shade.hide();
						 $.get("${ctxPath}/weixin/sysConfig/addCarouselImg.do?imgId="+data.data[0], function(result){
						 	console.info(result);
						 	if(result == true) { 
						 		$("#carouselTable").reloadGrid();
						 	    toastr.success("图片上传成功");
						 	} else {
						 		toastr.success("图片上传失败");
						 		return;
						 	}
						 });
						
					},
					error : function(XmlHttpRequest, textStatus, errorThrown) {
						$.shade.hide();
						toastr.success("图片上传失败");
					}
				}
			  );}
	   $().ready(
		     function(){
				$("#carouselTable")
					.grid(
							{
								url : _ctxPath
										+ "/weixin/sysConfig/queryCarouselImg.do",
								shrinkToFit : false,
								colNames : [ "操作","轮播图片"],
								colModel : [
										{
											name : "_id",
											index : "_id",
											align : "center",
											formatter : function(cellvalue,options,rowObject) {
											var v = "<a href='${ctxPath}/weixin/sysConfig/deleteCarouselImg.do?carouselId="
											  + cellvalue + "'>删除</a>";
											   return v;
											 }
										},
										{
											name : "img",
											index : "img",
											align : "center",
											formatter : function(cellvalue,options,rowObject) {
									              var v = "<image style='height:150px;width:375px;' src='${ossImageHost}/"
												  + cellvalue + "@!banner'></image>";
												   return v;
									         },
									         width:"400px"
										}
								     ],
								gridComplete: function(){
			             	$(".ui-jqgrid-sortable").css("text-align", "center");  
			         	}
				   });
				}
		);
	</script>
</body>
</html>
