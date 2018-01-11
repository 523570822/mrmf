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
        <h4 class="font-34">我的收藏</h4>
    </div>
    <input type="hidden" value="${userId }" id="userId"/>
<input type="hidden" value="" id="page"/>
<input type="hidden" value="" id="pages"/>
<input type="hidden" value="${type }" id="type"/>
    <div class="my_fun_div">
        <ul>
            <li onclick="changePage(1)">精美发型</li>
            <li class="active" onclick="changePage(2)">收藏技师</li>
            <li onclick="changePage(3)">收藏店铺</li>
        </ul>
    </div>
    <ul class="list">
    <c:forEach var="collect" items="${fpi.data }">
    	<li>
             <div class="col-2 tx">
                <img src="${collect.picture }" alt="header"/>
            </div>
            <div class="col-6 txt">
                <h4>${collect.title }</h4>
                <ul class="flowers">
                	<c:forEach items="${collect.zanCount }" var="zanCnt">
                   		<li></li>
                   	</c:forEach>
                </ul>
                <p><span>${collect.followCount }</span> 人关注</p>
            </div>
            <div class="col-2 location">
                <div><i></i></div>
                <div class="price">&yen; <span>${collect.price }</span> 起</div>
            </div>
        </li>
    </c:forEach>
    </ul>

    <script src="${ctxPath}/module/resources/js/jquery.min.js"></script>
    <script src="${ctxPath}/module/user/js/media_contrl.js"></script>
</body>
</html>