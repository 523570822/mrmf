<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/module/resources/common.jsp"%>
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
</head>
<body class="bg_gray">
    <div class="common_nav">
        <i></i>
        <h4 class="font-34">我的收藏</h4>
    </div>
        <input type="hidden" value="${userId }" id="userId"/>
<input type="hidden" value="" id="page"/>
<input type="hidden" value="" id="pages"/>
<input type="hidden" value="${type }" id="type"/>
    <div class="my_fun_div">
        <ul>
            <li class="active" onclick="changePage(1)">精美发型</li>
            <li onclick="changePage(2)">收藏技师</li>
            <li onclick="changePage(3)">收藏店铺</li>
        </ul>
    </div>
    <ul class="list"></ul>
    <ul class="store_list">
    </ul>
    <ul class="hair_list"></ul>
    <!-- 
    <div class="hair_list">
    <c:forEach var="collect" items="${fpi.data }" varStatus="status">
    	<c:if test="${status.count%2==1}">
    		 <div class="hair_list">
		        <div>
		            <div class="hair_pic">
		                <img src="${collect.picture }" />
		            </div>
		            <div>
		                <strong>
		                    ${collect.title }
		                </strong>
		            </div>
		            <div class="margin_btm">
		                <div class="hair_price_div">
		                    <span>&yen;</span> <span>${collect.price }</span>
		                </div>
		                <div class="attention_font" >
		                    <span>关注</span>  <span>${collect.followCount }</span>
		                </div>
		            </div>
		        </div>
    	</c:if>
    	<c:if test="${status.count%2==0}">
    			<div>
		            <div class="hair_pic">
		                <img src="${collect.picture }" />
		            </div>
		            <div>
		                <strong>
		                    ${collect.title }
		                </strong>
		            </div>
		            <div class="margin_btm">
		                <div class="hair_price_div">
		                    <span>&yen;</span> <span>${collect.price }</span>
		                </div>
		                <div class="attention_font" >
		                    <span>关注</span>  <span>${collect.followCount }</span>
		                </div>
		            </div>
		        </div>
		    </div>
    	</c:if>
    </c:forEach>
     -->
    <script src="${ctxPath}/module/resources/js/jquery.min.js"></script>
    <script src="${ctxPath}/module/user/js/media_contrl.js"></script>
    <script src="${ctxPath}/module/user/js/store_collect.js"></script>
</body>
</html>