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
    <link rel="stylesheet" href="${ctxPath}/module/resources/css/style_user_index.css"/>
</head>
<body style="background: #f7f7f7">
	<input type="hidden" id="longitude" value="${longitude}"/>
	<input type="hidden" id="latitude" value="${latitude}"/>
    <input type="hidden" id="userId" value="${userId}"/>
    <div class="register_nav">
         <i class="back" onclick="window.location.href='${ctxPath}/w/home/toHomePage'"></i>
        <h4 class="font-34">
        	<c:choose>
        		<c:when test="${sort==2}">明星技师
                </c:when>
        		<c:otherwise>美丽之星
                </c:otherwise>
        	</c:choose>
            <i class="search"></i>
        </h4>
    </div>
    <ul class="menu">
        <li <c:if test="${ sort==2}">class="active"</c:if> id="followCount">热度排序</li>
        <li  id="priceOrder">价格排序</li>

        <li <c:if test="${ sort==1}">class="active"</c:if> id="distance">距离排序</li>
        <li class="m_screen"><i></i>筛选</li>
    </ul>
    <input type="hidden" value="0" id="clickCount" />
    <input type="hidden" value="" id="pages"/>
    <input type="hidden" value="" id="page"/>
<ul class="list" id="list">
</ul>

<!--modal 筛选 start-->
<div class="modal m-1">
    <div class="modal_nav">
        <div class="col-3 font-28 cancel">取消</div>
        <div class="col-4 font-34">筛选</div>
        <div class="col-3 font-28 sure">确定</div>
    </div>
    <ul class="modal_list">
        <li class="sex_scr">
            <div class="col-2 sex">性别</div>
            <div class="col-7 txt">不限</div>
            <div class="col-1 r_arrow"><i id="sex_t"></i></div>
        </li>
        <li class="band">
            <div class="col-2 sex">级别</div>
            <div id="band_id" class="col-7 txt">不限</div>
            <div class="col-1 r_arrow"><i id="band_f"></i></div>
        </li>
    </ul>
</div>

<!--性别-->
    <div class="modal m-2">
        <div class="register_nav sex_list" style="border-bottom: 1px solid #ddd;">
            <i class="back"></i>
            <h4 class="font-34">性别</h4>
        </div>
        <ul class="modal_list sex_listing">
            <li class="active">
                <div class="col-2 sex">不限</div>
                <div class="col-1-r"><i></i></div>
            </li>
            <li>
                <div class="col-2 sex">男</div>
                <div class="col-1-r"><i></i></div>
            </li>
            <li>
                <div class="col-2 sex">女</div>
                <div class="col-1-r"><i></i></div>
            </li>
        </ul>
    </div>
    <!--级别-->
    <div class="modal m-3">
        <div class="register_nav sex_list" style="border-bottom: 1px solid #ddd;">
            <i class="back"></i>
            <h4 class="font-34">级别</h4>
        </div>
        <ul class="modal_list band_listing sex_listing">
            <li class="active">
                <div class="col-2 sex">不限</div>
                <div class="col-1-r"><i></i></div>
            </li>
            <li>
                <div class="col-2 sex">一级</div>
                <div class="col-1-r"><i></i></div>
            </li>
            <li>
                <div class="col-2 sex">二级</div>
                <div class="col-1-r"><i></i></div>
            </li>
            <li>
                <div class="col-2 sex">三级</div>
                <div class="col-1-r"><i></i></div>
            </li>
            <li>
                <div class="col-2 sex">四级</div>
                <div class="col-1-r"><i></i></div>
            </li>
            <li>
                <div class="col-2 sex">五级</div>
                <div class="col-1-r"><i></i></div>
            </li>
        </ul>
    </div>
	<div class="modal_bg" style="display: none;"></div>
    <!--modal 筛选 end-->
    <script src="${ctxPath}/module/resources/js/jquery.min.js"></script>
    <script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
    <script src="${ctxPath}/module/resources/js/common.js"></script>
    <script src="${ctxPath}/module/user/userhome/js/star.js"></script>
</body>
</html>