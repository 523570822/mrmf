<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/module/resources/wecommon.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html >
<html>
<head>
<base href="<%=basePath%>">
     <meta charset="utf-8">
        <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport">
        <meta content="yes" name="apple-mobile-web-app-capable">
        <meta content="black" name="apple-mobile-web-app-status-bar-style">
        <meta content="telephone=no" name="format-detection">
        <meta content="email=no" name="format-detection">
        <meta name="description" content="This is  a ..">
<title>发送询价</title>
<link href="${ctxPath}/module/user/css/style.css" rel="stylesheet">
</head>
<body style="background:#f8f8f8" class="bg_gray">
	 <input type="hidden" id="type" name="type" value="enquiry">
    <div class="enquiryprice_nav">
        <h4 class="font-34">询价</h4>
    </div>
    <div class="col-10"style="height: 40rem;">
        <form action="" method="post" id="enquiry_form">
        <input type="hidden" value="enquiry" name="type" id="type">
            <div class="col-10 enquiryprice_form_item" onClick="toChangeType()">
                <div class="col-2 enquiryprice_form_item_text_name">询价类型</div>
                <div class="col-7" >
                    <label class = "enquiryprice_form_item_text_value">
       	             <c:if test="${empty code }"> 请选择询价类型</c:if>
       	             <c:if test="${!empty code }">${code.name }</c:if>
       	             <input type="hidden" value="${code._id }"  name="codeId" id="service_type">
                   </label>
                </div>
                <div class="col-1 next"  >
                </div>
            </div>

            <div class="col-10 form_item_summarize" >
                <div class="form_item_summarize_name">需求概述</div>
                <div class="form_area">
                  <textarea class="form_area" placeholder="简要描述您的询价描述" name="desc" id="desc">${desc}</textarea>
                </div>
            </div>
            <div class="col-10 form_item_pic">
                <div class="form_item_summarize_name">添加照片</div>
                <div class="btn_add">
                    <ul id="imgs">
                        <li class="">
                            <input type="hidden" id="logo0" name="logo0">
							<img name="file" src="${ctxPath }/module/staff/images/img/btn_add_picture.png"  class="file" id="logoimg0" >
							<div id="loading0" class="none"><div class="img_mask"><div class="loading"></div></div></div>
                        </li>
                        <li class="">
                            <input type="hidden" id="logo1" name="logo1">
							<img name="file" src="${ctxPath }/module/staff/images/img/btn_add_picture.png" class="none"  class="file" id="logoimg1" >
							<div id="loading1" class="none"><div class="img_mask"><div class="loading"></div></div></div>
                        </li>
                        <li class="">
                            <input type="hidden" id="logo2" name="logo2">
							<img name="file" src="${ctxPath }/module/staff/images/img/btn_add_picture.png" class="none"  class="file" id="logoimg2" >
							<div id="loading2" class="none"><div class="img_mask"><div class="loading"></div></div></div>
                        </li>
                    </ul>
                </div>
            </div>
			<input type="hidden" value="" id="img_position">
            <div class="col-10 btn_send" style="bottom: 5rem;position: fixed;">
                <button id="send_enquiry">
                	    发送
                </button>
            </div>
            <div class="col-10 common_func_menu">
                <ul>
                    <li id="function_item_first">
                        <div id="user_home">
                            <img src="${ctxPath}/module/resources/images/icon_home_nor.png" />
                            <p>
                                                                                                     首页
                            </p>
                        </div>
                    </li>
                    <li>
                        <div id="user_organ">
                             <img src="${ctxPath}/module/resources/images/icon_store_nor.png" />
                            <p>店铺</p>
                        </div>
                    </li>
                    <li>
                        <div>
                            <img src="${ctxPath}/module/resources/images/icon_ask_pre.png" />
                            <p style="color: #f4370b;">询价</p>
                        </div>
                    </li>
                    <li>
                        <div id="user_my">
                             <img src="${ctxPath}/module/resources/images/icon_my_nor.png" />
                            <p>我的</p>
                        </div>
                    </li>
                </ul>
            </div>
        </form>
    </div>
<script src="${ctxPath}/module/user/js/media_contrl.js"></script>
<script src="${ctxPath}/module/user/js/enquiry.js"></script>
<script src="${ctxPath}/module/staff/js/jquery.form.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript">
$(function(){
	$("#imgs>li").click(function(){
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
//	alert("上传失败");
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
                    	$.get(_ctxPath+"/w/user/uploadFile",{"serverId":serverId},function(data) {
                    		console.info($("#img_position").val());
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
                       //alert('上传失败!');
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