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
    <style type="text/css">
        a{
            text-decoration: none;
            color:#22242a;
        }
    </style>
</head>
<body class="bg_gray">
    <div class="city_nav">
        <i class="back"></i>
        <h4 class="font-34">选择城市</h4>
    </div>
    <div class="position_city">
        <span>当前城市</span>
    </div>
    <div class="locate">
        <div class="col-8">
            <img src="${ctxPath}/module/resources/images/enquiryprice/icon_didian_shouye.png" />
            <span>
               ${city}
            </span>
         </div>
         <div class="confirm_img">
            <img src="${ctxPath}/module/resources/images/icon_choiced.png" />
         </div>
    </div>
    <c:forEach items="${cityList}" var="c" varStatus="status">
    	<div class="multiple_cities">
        <div class="multiple_show_city">
            <div class="col-8">
                <span>
                   ${c.name }
                </span>
            </div>
        </div>
        <div class="solid_line"></div>
    </div>
    </c:forEach>
   <!--  <div class="letter_div">
        <span>B</span>
    </div>
    <div class="show_city">
        <div class="col-2">
            <span>
                                     北京
            </span>
        </div>
    </div>
    <div class="letter_div">
        <span>S</span>
    </div>
    <div class="multiple_cities">
        <div class="multiple_show_city">
            <div class="col-2">
                <span>
                                                   苏州
                </span>
            </div>
        </div>
        <div class="solid_line"></div>
        <div class="multiple_show_city">
            <div class="col-2">
       	         <span>
                  	  上海
                </span>
            </div>
        </div>
    </div>
    <div class="letter_div">
        <span>T</span>
    </div>
    <div class="show_city">
        <div class="col-2">
            <span>
              		 天津
            </span>
        </div>
    </div> -->
   <!--  <div class="alphabet_list">
        <ul>
            <li><a href="#">A</a></li>
            <li><a href="#">B</a></li>
            <li><a href="#">C</a></li>
            <li><a href="#">D</a></li>
            <li><a href="#">E</a></li>
            <li><a href="#">F</a></li>
            <li><a href="#">G</a></li>
            <li><a href="#">L</a></li>
            <li><a href="#">J</a></li>
            <li><a href="#">K</a></li>
            <li><a href="#">A</a></li>
            <li><a href="#">E</a></li>
            <li><a href="#">A</a></li>
            <li><a href="#">B</a></li>
            <li><a href="#">C</a></li>
            <li><a href="#">D</a></li>
            <li><a href="#">E</a></li>
            <li><a href="#">F</a></li>
            <li><a href="#">G</a></li>
            <li><a href="#">L</a></li>
            <li><a href="#">J</a></li>
            <li><a href="#">K</a></li>
            <li><a href="#">A</a></li>
            <li><a href="#">E</a></li>
        </ul>
    </div> -->
    <script src="${ctxPath}/module/resources/js/jquery.min.js"></script>
    <script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
    <script type="text/javascript">
    	$(function(){
    	
    		$(".multiple_show_city").click(function() {
    			$(".confirm_img").appendTo($(this));
    			$(".locate span").text($(this).text());
    		});
    		
    		$(".show_city").click(function() {
    			$(".confirm_img").appendTo($(this));
    			$(".locate span").text($(this).text());
    		});
    	});
    	
    	$(".back").click(function() {
    		var city = $.trim($(".locate span").text());
    		window.location.href=_ctxPath+"/w/home/toHomePageByCity?city="+encodeURIComponent(encodeURIComponent(city));
    	});
    </script>
</body>
</html>