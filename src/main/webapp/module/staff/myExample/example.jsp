<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/module/resources/wecommon.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html >
<html>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <meta content="email=no" name="format-detection">
    <meta name="description" content="This is  a ..">
     <style>
        .swiper-container {
            width: 100%;
            height: 26.79rem;
        }
        .swiper-slide {
            text-align: center;
            font-size: 3rem;
            background: #fff;
            /* Center slide text vertically */
            display: -webkit-box;
            display: -ms-flexbox;
            display: -webkit-flex;
            display: flex;
            -webkit-box-pack: center;
            -ms-flex-pack: center;
            -webkit-justify-content: center;
            justify-content: center;
            -webkit-box-align: center;
            -ms-flex-align: center;
            -webkit-align-items: center;
            align-items: center;
        }
        .swiper-slide>img {
            height:auto;
            width:100%;
        }
        .swiper-pagination-bullet {
            width: 0.43rem;
            height:  0.43rem;
            text-align: center;
            line-height: 20px;
            /*font-size: 12px;*/
            color:#000;
            opacity: 1;
            background: rgba(0,0,0,0);
            border: 1px solid #22242a;
        }
        .swiper-pagination-bullet-active {
            background: #22242a;
        }
    </style>
<title>案例</title>
<link href="${ctxPath}/module/staff/css/style_myExample.css" rel="stylesheet">
<link rel="stylesheet" href="${ctxPath}/module/resources/css/swiper.min.css"/>
</head>
<body>
<div class="nav">
	    <i class="back2" onclick="window.history.go(-1)"><img style="width:1rem;height:1rem;" src="${ctxPath }/module/staff/images/img/icon_back.png"></i>
	    <h4 class="font-34-1" style="width: 60%;float: left;" >${staffCase.type }</h4>
	    <i class="i0" style="right:4rem;" id="save_example">保存</i>
	    <i class="i0" id="delete">删除</i>
	</div>
	<form action="" method="post" id="delete_form">
		<input type="hidden" value="${staffCase._id }" name="staffCaseId" id="staffCase_id">
		<input type="hidden" value="${staffId }" name="staffId" id="staffId">
		<input type="hidden" value="${staffCase.consumeTime }" name="consumeTime" id="consume_time">
		<input type="hidden" value="${staffCase.price }" name="price" id="price">
		<input type="hidden" value="${staffCase.title }" name="title" id="title">
	</form>
	<div class="example_content_2">
		<div class="swiper-container">
        <div class="swiper-wrapper">
            <c:forEach items="${staffCase.logo}" var="logo">
           		<div class="swiper-slide"><img src="${ossImageHost}${logo}@!style1000"></div>
            </c:forEach>
        </div>
        <!-- Add Pagination -->
        <div class="swiper-pagination"></div>
    </div>
		<div class="detail">
			<ul>
				<li style="height: 5rem;">
					<h4 class="font-32 canEdit" id="editTitle" onblur="lostFocus(this)">${staffCase.title }</h4>
					<div class="col-2 font-30"><i class="i3" style="border-right: 0">耗时</i><i class="i3" ><i class="canEdit" id="editTime" style="width: 5%;" onblur="lostFocus(this)">${staffCase.consumeTime }</i><i>分钟</i></i></div>
					<div class="col-7" style="color: #f4370b;">￥<i class="i1 canEdit" id="editPrice" onblur="lostFocus(this)">${staffCase.price }</i><%-- .<i class="i2">${decimal }</i> --%></div>
				</li>
				<li>
					<h4 class="font-28" style="color:#666666;">发型说明</h4>
					<h4 class="font-28" style="color:#666666;">
						<textarea class="exampleTextArea" id="desc">${staffCase.desc }</textarea>
					</h4>
				</li>
			</ul>
		</div>
	</div>
<script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
<script src="${ctxPath}/module/staff/js/staff.js"></script>
<script src="${ctxPath}/module/resources/js/swiper.min.js"></script>
<script type="text/javascript">
	  var mySwiper = new Swiper('.swiper-container', {
            autoplay: 2000,//可选选项，自动滑动
            pagination : '.swiper-pagination',
            paginationClickable: true,
            autoplayDisableOnInteraction: false,
            loop : true,
        });
</script>
</body>
</html>