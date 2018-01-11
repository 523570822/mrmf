<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/module/resources/wecommon.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html >
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <meta content="email=no" name="format-detection">
    <meta name="description" content="This is  a ..">
<title>添加案例</title>
<link href="${ctxPath}/module/staff/css/style_myExample.css" rel="stylesheet">
</head>

<body style="background: #f8f8f8;">
<input type="hidden" id="select_type" value="addExample">
<form action="" method="post" id="addExample_form">
	<input type="hidden" value="${staffId }" name="staffId" id="staff_id">
	<div class="nav" style="margin-bottom: 1.1rem;">
	    <i class="back" id="back"></i>
	    <input type="hidden" value="addExample" name="status" id="status">
	    <h4 class="font-34">添加案例</h4>
	</div>
	<div class="addexample_content">
		<ul>
			<li onclick="toChangeType()">
				<div class="col-2 font-30">选择类型</div>
				<div class="col-7 font-32" name="typeName" id="type_name">
					<c:if test="${empty code }"> 请选择类型</c:if>
       	            <c:if test="${!empty code }">${code.name }</c:if>
       	            <input type="hidden" value="${code._id }"  name="codeId" id="codeId" >
       	            <input type="hidden" value="${code.name }"  name="type" id="type">
	       	        <input type="hidden" value="${code.type }"  name="realType" id="real_type">
				</div>
				<div class="col-1 next"  ></div>
			</li>
			<li>
				<input type="text" placeholder="输入标题" name="title" id="title" value="${title }">
			</li>
			<li  class="li">
				<textarea class="textarea" style="height:6rem;resize:none;padding:0 1rem;" placeholder="输入案例介绍..." name="desc" id="desc">${desc}</textarea>
			</li>
			<li class="imgList" id="imgList" style="padding:1rem 0 0 0">
			    <div class="loadImg " id="addImgs"> 
			        <input type="hidden" name="fileImgs" id="fileImgs" >
					<img src="${ctxPath }/module/staff/images/img/btn_add_picture.png"  class="file">
			    </div>
			</li>
			<li class="li">
				<div class="col-2 font-30">设置价格</div>
				<div class="col-7 font-32" style="padding: 0">
					<input type="text" placeholder="设置案例价格(元)" value="${ price }" style="padding: 0" name="price" id="price">
				</div>
			</li>
			<li>
				<div class="col-2 font-30">预计时间</div>
				<div class="col-7 font-32" style="padding: 0">
					<input type="text" placeholder="预计服务时间(分钟)" value="${ consumeTime }" style="padding: 0" name="consumeTime" id="consume_time">
				</div>
			</li>
		</ul>
	</div>
	<div class="bottom">
		<button class="button font-34" type="button" id="publish">发布案例</button>
	</div>
	<div id="showDiv" class="hide"></div>
		<div id="content" class="showpublish" >
			<div class="title">发布成功</div>
			<div class="botton-left" id="main_page">返回首页</div>
			<div class="botton-right" id="my_example">确定</div>
		</div>
</form>
<script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
<script src="${ctxPath}/module/staff/js/staff.js"></script>
<script src="${ctxPath}/module/staff/js/changejs.js"></script>
<script src="${ctxPath}/module/staff/js/jquery.form.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript">
	 wx.config({    
		debug: false,    
		appId: '${sign.appid}', // 必填，公众号的唯一标识
		timestamp: '${sign.timestamp}', // 必填，生成签名的时间戳
		nonceStr: '${sign.nonceStr}', // 必填，生成签名的随机串
		signature: '${sign.signature}',// 必填，签名，见附录1    
		jsApiList: [    
		'checkJsApi',    
		'chooseImage',
		'uploadImage'
		]    
	});  
	wx.error(function(res){
		alert('上传图片失败，请反馈给任性猫平台:'+res);		
	}); 
	wx.ready(function () {
		$("#addImgs").click(function() {
			var localIds=[];
			var imgCount = $('.uploaded').length;
			if(imgCount>=5) {
				alert('亲，案例最多上传五张图片哦!');
				return;
			}
			var selectCount=5-window.parseInt(imgCount);
			wx.chooseImage({
				count:selectCount,
			    sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
			    sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
			    success: function (res) {
			    	localIds = res.localIds; // 返回选定照片的本地ID列表
			        res.localIds = null;
			    	var i=0,length = localIds.length;
			    	function upload() {
			    		var imgItem = '';
			    		var imgBlock = '';
			    		if($('.uploaded').length<4) {
			    			imgBlock = '<div class="loadImg uploaded">'+
							'<div id="loading2" class="block"><div class="img_mask"><div class="loading"></div></div></div></div>';
							$('#addImgs').before(imgBlock);	
			    		} else {
			    			$('#addImgs').addClass('uploaded');
			    			$('#addImgs').empty().append('<div id="loading2" class="block"><div class="img_mask"><div class="loading"></div></div></div>');
			    		}
			    	    wx.uploadImage({  
			    	    	isShowProgressTips: 0, // 默认为1，显示进度提示
	                        localId: localIds[i], 
	                        success: function(res) {  
	                        	var serverId = res.serverId;
	                        	$.get(_ctxPath+"/w/staff/uploadFile",{"serverId":serverId},function(data) {
	                        		var imgpath = _ossImageHost + data+"@!style400";
	                        		imgItem ='<input type="hidden" name="fileImgs" id="fileImgs" value="'+data+'"><img  src="'+ imgpath+'" class="file">';
	                        		if($('.uploaded').length>4){
	                        			$('#addImgs').empty().append(imgItem);
	                        		} else {
	                        			$('#addImgs').prev().empty().append(imgItem);
	                        		}
	                        		imgpath = null
	                        		imgItem = null;
	                        		serverId = null;
		                        	res.serverId = null;
		                        	++i;
	                        		if(i<length) {
	                        			upload();
	                        		}
	                        	});
	                        },  
	                        fail: function(res) {  
	                           alert('上传失败!'+ res);
	                        }  
	                    }); 
			    	}
			    	upload();
			    }
			});
		});
	});
</script>
</body>
</html>