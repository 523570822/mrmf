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
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="this is my page">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport"/>
    <meta content="yes" name="apple-mobile-web-app-capable"/>
    <meta content="black" name="apple-mobile-web-app-status-bar-style"/>
    <meta content="telephone=no" name="format-detection"/>
    <meta content="email=no" name="format-detection"/>
    <meta name="description" content="This is  a .."/>
    <link rel="stylesheet" type="text/css" href="${ctxPath}/module/staff/css/style_myRecommendation.css"/>
	<title>自我推荐</title>
</head>
 <body style="background: #f8f8f8;">
 <input type="hidden" value="myRecommendation" id="status">
 <form action="" id="my_recommendation" method="post">
 
<input type="hidden" id="staff_id" name="staffId">
    <div class="nav">
	    <i class="back" id="back"></i>
	    <h4 class="font-34">自我推荐</h4>
	    <i class="i0 confirm" onclick="editSave()">保存</i>
	</div>
	<div class="mine_content">
		<ul>
			<li style="padding: 0.7rem 0;">
				<div class="col-6 font-30" style="text-align: left;">个人简介</div>
				<div class="col-2">
					<img src="${ctxPath }/module/staff/images/img/icon_edit.png" class="editPic" />
				</div>
				<div class="col-2 font-28 edit red" >编辑简介</div>
			</li>
			<li class="li-1">
				<div style="text-align: left;" class="font-28">
					<textarea id="text_area" name="textArea" >${staff.desc }</textarea>
				</div>
			</li>
		</ul>
	</div>
	<div class="mine_content">
		<ul>
			<li class="li-2">
				<div class="col-6 font-30" style="text-align: left;">照片风采</div>
				<div class="col-2">
					<img src="${ctxPath }/module/staff/images/img/icon_addpicture.png" alt="header" style="width: 1.4rem;height: 1.4rem;float: right;"/>
				</div>
				<div class="col-2 font-28" style="text-align: right;color: #f4370b;">添加照片</div>
			</li>
			<li class="li-3">
				<ul style="width: 100%;float: left;" id="imgs">
					<li class="li4">
						<input type="hidden" id="logo0" name="logo0" value="${staff.descImages[0] }">
						<c:if test="${empty staff.descImages[0]}">
							<img src="${ctxPath }/module/staff/images/img/btn_add_picture.png"  name="file" id="logoimg0"> 
						</c:if>
						<c:if test="${!empty staff.descImages[0]}">
							<img src="${ossImageHost}${staff.descImages[0]}@!style400" name="file" id="logoimg0"> 
						</c:if>
						<div id="loading0" class="none"><div class="img_mask" style="left:0"><div class="loading"></div></div></div> 
					</li>
					<li class="li4" style="margin-left: 1.6rem;">
						<input type="hidden" id="logo1" name="logo1" value="${staff.descImages[1] }">
						<c:if test="${empty staff.descImages[1]}">
							<img src="${ctxPath }/module/staff/images/img/btn_add_picture.png" class="none" style="width: 4.3rem;height: 4.3rem;" name="file" id="logoimg1"> 
						</c:if>
						<c:if test="${!empty staff.descImages[1]}">
							<img src="${ossImageHost}${staff.descImages[1]}@!style400" name="file" id="logoimg1" onclick="doc.click()"> 
						</c:if>
						<div id="loading1" class="none"><div class="img_mask"><div class="loading"></div></div></div> 
					</li>
					<li class="li4" style="float: right;">
						<input type="hidden" id="logo2" name="logo2" value="${staff.descImages[2] }">
						<c:if test="${empty staff.descImages[2]}">
							<img src="${ctxPath }/module/staff/images/img/btn_add_picture.png" class="none" style="width: 4.3rem;height: 4.3rem;" name="file" id="logoimg2" > 
						</c:if>
						<c:if test="${!empty staff.descImages[2]}">
							<img src="${ossImageHost}${staff.descImages[2]}@!style400" name="file" id="logoimg2"> 
						</c:if>
						<div id="loading2" class="none"><div class="img_mask"><div class="loading"></div></div></div> 
					</li>
				</ul>
				<input type="hidden" value="" id="img_position">
			</li>
		</ul>
	</div>
	</form>
<script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
<script src="${ctxPath}/module/staff/js/staff.js"></script>
<script src="${ctxPath}/module/staff/js/jquery.form.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript">
$(function(){
	$("#imgs li").click(function(){
		$("#img_position").val($(this).index()+1);
	});
	if ($("#logo0").val()!="" && $("#logo1").val()=="") {
		$("#logoimg1").removeClass("none");
	}else if ($("#logo1").val()!="" && $("#logo2").val()=="") {
		$("#logoimg2").removeClass("none");
	}
});
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
	wx.error(function(){
		if ($("#img_position").val()=="0") {
			$("#loading0").toggleClass('block');
			$("#loading0").toggleClass('none');
		}else if ($("#img_position").val()=="1") {
			$("#loading1").toggleClass('block');
			$("#loading1").toggleClass('none');
		}else if ($("#img_position").val()=="2") {
			$("#loading2").toggleClass('block');
			$("#loading2").toggleClass('none');
		}
		alert("上传失败");		
	}); 
	wx.ready(function () {
		var serverId;
		var localId;
		$("#logoimg0,#logoimg1,#logoimg2").click(function() {
			wx.chooseImage({
			    count: 1, 
			    sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
			    sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
			    success: function (res) {
			        var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
			        res.localIds = null;
			        localId = localIds[0];
			        wx.uploadImage({  
                        localId: localId,  
                        success: function(res) {  
                        	serverId = res.serverId;
                        	$.get(_ctxPath+"/w/staff/uploadFile",{"serverId":serverId},function(data) {
                        		var imgpath = _ossImageHost + data+"@!style400";
            					if ($("#img_position").val()=="1") {
            						$("#logoimg0").attr("src", imgpath);
            						$("#logo0").val(data);
            						$("#logoimg1").removeClass("none");
            					}else if ($("#img_position").val()=="2") {
            						$("#logoimg1").attr("src", imgpath);
            						$("#logo1").val(data);
            						$("#logoimg2").removeClass("none");
            					}
                        	});
                        	serverId = null;
                        	res.serverId = null
                        },  
                        fail: function(res) {  
                           alert('上传失败!');
                        }  
                    });
			       localId = null;
			    }
			});
		});
	   });
</script>
  </body>
</html>