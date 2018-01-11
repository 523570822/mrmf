<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/module/resources/wecommon.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <meta content="email=no" name="format-detection">
    <meta name="description" content="This is  a ..">
    <title>任性猫</title>
    <link rel="stylesheet" href="${ctxPath}/module/resources/css/my_style.css"/>
</head>

<body class="bg_gray">
    <div class="common_nav">
        <i onclick="window.history.go(-1);"></i>
        <h4 class="font-34">设置头像</h4>
        <span id="finished_header" class="finished">完成</span>
    </div>
    <input type="hidden" id="id" value="${ user._id }" />
    <div class="change_img">
            <div>
                <img id ="headerImg" src="${ossImageHost}${user.avatar}@!avatar">
                <input id = "imgSrc" type="hidden" value="" />
            </div>
           <button onclick="selectImg()">上传头像</button>
    </div>
 
      <div style="display:none" >
		<form action="" id="fileForm">
			<input type="file" rel="msgImage" autocomplete="off" name="onlyFile"
				id="onlyFile" onchange="changeFile(this)" placeholder="File here" />
		</form>
	</div>
    <script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
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
				url : _ctxPath+'/w/file/upload.do',
				success : function(data) {
					$.shade.hide();
					var imgpath = _ossImageHost + data.data[0]+"@!avatar";
					$("#headerImg").attr("src", imgpath);
					$("#imgSrc").val(data.data[0]);
					toastr.success("图片上传成功");
				},
				error : function(XmlHttpRequest, textStatus, errorThrown) {
				    $.shade.hide();
					toastr.success("图片上传失败");
				}
			});
		}
	    $("#finished_header").click(function() {
	    	var id = $("#id").val();
	    	var imgSrc = $("#imgSrc").val();
	        window.location.href=_ctxPath+"/w/userMy/toSetHomeByHeader?id=" + id+"&imgSrc="+imgSrc;
	    });
     </script>
</body>
</html>