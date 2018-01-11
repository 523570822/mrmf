<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/moduleweb/resources/common.jsp"%>
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
        <link href="${ctxPath}/module/resources/css/my_style.css" rel="stylesheet">
    </head>
<body class="bg_gray">
    <div class="list_nav">
        <div>
            <div class="list_left">
                <span>列表</span>
            </div>
            <div class="map_right">
                <span>地图</span>
            </div>
        </div>
        <i>
        </i>
    </div>
    <input type="hidden" value="${cityId }" id="cityId"/>
    <input type="hidden" value="${city }" id="city"/>
    <input type="hidden" value="" id="followCount"/>
	<input type="hidden" value="" id="organ_type"/>
	<input type="hidden" value="" id="distance"/>
    <ul class="sort_fun">
        <li>店铺类型&nbsp;&nbsp;<img src="${ctxPath}/module/resources/images/home/icon_aorrw_down.png"></li>
        <li>所在区域&nbsp;&nbsp;<img src="${ctxPath}/module/resources/images/home/icon_aorrw_down.png"></li>
        <li id="followCount">热度排序</li>
        <li>距离排序</li>
    </ul>
    <ul class="store_list" id="store_list"></ul>


    <!--modal select list  -->
    <div class="com_back_bg"></div>
    <div class="select_nav">
        <div class="list_nav">
            <div>
                <div class="list_left">
                    <span>列表</span>
                </div>
                <div class="map_right">
                    <span>地图</span>
                </div>
            </div>
            <i>
            </i>
        </div>
        <ul class="sort_fun_m">
            <li>店铺类型&nbsp;&nbsp;<img id="img1" src="${ctxPath}/module/resources/images/home/icon_aorrw_down.png"></li>
            <li>所在区域&nbsp;&nbsp;<img src="${ctxPath}/module/resources/images/home/icon_aorrw_down.png"></li>
            <li>热度排序</li>
            <li>距离排序</li>
        </ul>
        <ul class="store_type">
            <li class="add_selected">所有类型<i></i></li>
            <c:forEach items="${codeList}" var="code" varStatus="status">
            <li>${code.name }<i></i></li>
            </c:forEach>
        </ul>
    </div>

    <div class="col-10 common_func_menu">
        <ul>
            <li id="common_func_menu_first">
                <div>
                    <img src="${ctxPath}/module/resources/images/icon_home_nor.png" />
                    <p class="font-red">
                        首页
                    </p>
                </div>
            </li>
            <li>
                <div>
                    <img src="${ctxPath}/module/resources/images/icon_store_pre.png" />
                    <p style="color:#f4370b">店铺</p>
                </div>
            </li>
            <li>
                <div>
                    <img src="${ctxPath}/module/resources/images/icon_ask_nor.png" />
                    <p>询价</p>
                </div>
            </li>
            <li>
                <div>
                    <img src="${ctxPath}/module/resources/images/icon_my_nor.png" />
                    <p>我的</p>
                </div>
            </li>
        </ul>
    </div>
<script src="${ctxPath}/module/resources/js/userStore.js"></script>
<script src="${ctxPath}/module/user/userstore/js/store.js"></script>
    
</body>
</html>