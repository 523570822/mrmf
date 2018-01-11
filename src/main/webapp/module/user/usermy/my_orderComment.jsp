<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/module/resources/wecommon.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
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
    <title>任性猫</title>
    <link rel="stylesheet" href="${ctxPath}/module/user/css/my_style.css"/>
    <script src="${ctxPath}/module/user/js/media_contrl.js"></script>
    <script type="text/javascript">
    	function setZanData(obj){
    		var docid = obj.id;
    		var docname = docid.substr(0,3);
    		var docnum = docid.substr(3,4);
    		for(var i = 1;i<=docnum;i++){
    			var element = document.getElementById(docname+i);
    			if(docname == "zan"){
    				element.src = "${ctxPath}/module/user/images/flower.png";
    				document.getElementById("zanCount").value=docnum;
    			}else if(docname == "qiu"){
    				element.src = "${ctxPath}/module/user/images/icon_egg_90_pre.png";
    				document.getElementById("qiuCount").value=docnum;
    			}else if(docname == "yan"){
    				element.src = "${ctxPath}/module/user/images/icon_yanzhi_nor.png";
    				document.getElementById("faceScore").value=docnum;
    			} 				
    		}
    		for(var i = 5;i>docnum;i--){
    			var element = document.getElementById(docname+i);
    			if(docname == "zan"){
    				element.src = "${ctxPath}/module/user/images/icon_flower_90_nor.png";
    			}else if(docname == "qiu"){
    				element.src = "${ctxPath}/module/user/images/icon_egg_90_nor.png";
    			}else if(docname == "yan"){
    				element.src = "${ctxPath}/module/user/images/icon_yanzhi_pre.png";
    			} 				
    		}
    		
    	}
    	function setSelect(obj){
    		var docid = obj.id;
    		var docname = docid.substr(0,3);
    		var docnum = docid.substr(3,4);
    		var type = $(obj).parent().parent().attr("id");
    		if(type=="zan"){
				var selFlag = $(obj).hasClass("selClass");
				for(var i=1;i<=5;i++){
						var element = document.getElementById(docname+i);
						element.src = "${ctxPath}/module/user/images/icon_flower_90_nor.png";
						if($(element).hasClass("selClass")){
							$(element).removeClass("selClass");
						}
				}
				var num=0;
				if(selFlag){
					num = docnum-1;
					
				}else{
					num = docnum;
				}
				
				for(var i=1;i<=num;i++){
						var element = document.getElementById(docname+i);
						element.src = "${ctxPath}/module/user/images/flower.png";
						if(!$(element).hasClass("selClass")){
							$(element).addClass("selClass");
						}
				}
				document.getElementById("zanCount").value=num;
				
    		}else if(type=="qiu"){
    			var selFlag = $(obj).hasClass("selClass");
    			for(var i=1;i<=5;i++){
						var element = document.getElementById(docname+i);
						element.src = "${ctxPath}/module/user/images/icon_egg_90_nor.png";
						if($(element).hasClass("selClass")){
							$(element).removeClass("selClass");
						}
				}
    			var num = 0;
				if(selFlag){
				   num = docnum-1;
				}else{
					num = docnum;
				}
				for(var i=1;i<=num;i++){
						var element = document.getElementById(docname+i);
						element.src = "${ctxPath}/module/user/images/icon_egg_90_pre.png";
						if(!$(element).hasClass("selClass")){
							$(element).addClass("selClass");
						}
				}
					
				document.getElementById("qiuCount").value=num;
    		}else if(type=="yan"){
    		
    			var selFlag = $(obj).hasClass("selClass");
    			for(var i=1;i<=5;i++){
						var element = document.getElementById(docname+i);
						element.src = "${ctxPath}/module/user/images/icon_yanzhi_pre.png";
						if($(element).hasClass("selClass")){
							$(element).removeClass("selClass");
						}
			}
    			var num = 0;
				if(selFlag){
					num = docnum-1;
				}else{
					num = docnum;
				}
				for(var i=1;i<=num;i++){
						var element = document.getElementById(docname+i);
						element.src = "${ctxPath}/module/user/images/icon_yanzhi_nor.png";
						if(!$(element).hasClass("selClass")){
							$(element).addClass("selClass");
						}
					}
					
					document.getElementById("faceScore").value=num;
    		}
    	}
    </script>
</head>

<body class="bg_gray">
<div class="bg_gray">
    <!-- top -->
    <div class="common_nav">
        <h4 class="font-34">评价技师</h4>
    </div>
    
    <div class="commentheader_div" >
        <div class="clearfix">
            <div class="commentheader_left fl">赞</div>
            <div class="commentheader_right fl">
                <ul class="clearfix" id="zan">
                    <li>
                        <img src="${ctxPath}/module/user/images/icon_flower_90_nor.png" id="zan1" onclick="setSelect(this)"/>
                    </li>
                    <li>
                        <img src="${ctxPath}/module/user/images/icon_flower_90_nor.png" id="zan2" onclick="setSelect(this)"/>
                    </li>
                    <li>
                        <img src="${ctxPath}/module/user/images/icon_flower_90_nor.png" id="zan3" onclick="setSelect(this)"/>
                    </li>
                    <li>
                        <img src="${ctxPath}/module/user/images/icon_flower_90_nor.png" id="zan4" onclick="setSelect(this)"/>
                    </li>
                    <li>
                        <img src="${ctxPath}/module/user/images/icon_flower_90_nor.png" id="zan5" onclick="setSelect(this)"/>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    <div class="commentheader_div" style="border:none; margin-top:0;">
        <div class="clearfix">
            <div class="commentheader_left fl">糗</div>
            <div class="commentheader_right fl">
                <ul class="clearfix" id="qiu">
                    <li>
                        <img src="${ctxPath}/module/user/images/icon_egg_90_nor.png" id="qiu1" onclick="setSelect(this)"/>
                    </li>
                    <li>
                        <img src="${ctxPath}/module/user/images/icon_egg_90_nor.png" id="qiu2" onclick="setSelect(this)"/>
                    </li>
                    <li>
                        <img src="${ctxPath}/module/user/images/icon_egg_90_nor.png" id="qiu3" onclick="setSelect(this)"/>
                    </li>
                    <li>
                        <img src="${ctxPath}/module/user/images/icon_egg_90_nor.png" id="qiu4" onclick="setSelect(this)"/>
                    </li>
                    <li>
                        <img src="${ctxPath}/module/user/images/icon_egg_90_nor.png" id="qiu5" onclick="setSelect(this)"/>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    <div class="commentheader_div" style="border:none;">
        <div class="clearfix">
            <div class="commentheader_left fl">技师颜值</div>
            <div class="commentheader_right fl">
                <ul class="clearfix" id="yan">
                    <li>
                        <img src="${ctxPath}/module/user/images/icon_yanzhi_pre.png" id="yan1" onclick="setSelect(this)"/>
                    </li>
                    <li>
                        <img src="${ctxPath}/module/user/images/icon_yanzhi_pre.png" id="yan2" onclick="setSelect(this)"/>
                    </li>
                    <li>
                        <img src="${ctxPath}/module/user/images/icon_yanzhi_pre.png" id="yan3" onclick="setSelect(this)"/>
                    </li>
                    <li>
                        <img src="${ctxPath}/module/user/images/icon_yanzhi_pre.png" id="yan4" onclick="setSelect(this)"/>
                    </li>
                    <li>
                        <img src="${ctxPath}/module/user/images/icon_yanzhi_pre.png" id="yan5" onclick="setSelect(this)"/>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    <!-- end -->
    <!-- comment start -->
    <form class="comment_form" id="organ_index_form" action=""  method="post">
        <textarea placeholder="请输入您的评论..." id="comment" name="comment"></textarea>
    	<input type="hidden" value="${orderId }" id="order_id" name="orderId" />
    	<input type="hidden" value="${userId }" id="user_id" name="userId"/>
    	<input type="hidden" value="0" id="zanCount" name="zanCount"/>
    	<input type="hidden" value="0" id="qiuCount" name="qiuCount"/>
    	<input type="hidden" value="0" id="faceScore" name="faceScore"/>
        <div class="commentheader_div footer_div">
        <input type="button" class="button_footer_1" value="返回" onclick="returnOrder()">
        <input type="button" class="button_footer_2" value="完成" id="comment_submit">
        <input type="hidden" value="commentStaff" id="comment_type">
    </div>
    </form>
     <input type="hidden" value="${type }" id="type">
</div>
<script src="${ctxPath}/module/user/usermy/myOrder/js/my_order.js"></script>
</body>
</html>












