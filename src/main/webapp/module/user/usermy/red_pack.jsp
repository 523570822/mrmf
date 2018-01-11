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
    <link rel="stylesheet" href="${ctxPath}/module/resources/css/my_style.css"/>
</head>
<body>
    <div class="common_nav" style="position: absolute; left: 0; top: 0;z-index: 20;">
	    <i onclick="window.history.go(-1);"></i>
	    <h4 class="font-34">抢红包</h4>
	</div>
	<div class="red_pack">
		<div class="red_pack_img">
			<img src="${ctxPath}/module/resources/images/my/icon_red_bag.jpg">
		</div>
		<div class="red_bag" style="display: none;"></div>
		<%-- <div class="red_pack_container">
			<img src="${ctxPath}/module/resources/images/my/red_pack.png" />
			<img src="${ctxPath}/module/resources/images/my/red_pack.png" />
			<img src="${ctxPath}/module/resources/images/my/red_pack.png" />
			<img src="${ctxPath}/module/resources/images/my/red_pack.png" />
			<img src="${ctxPath}/module/resources/images/my/red_pack.png" />
			<img src="${ctxPath}/module/resources/images/my/red_pack.png" />
			<img src="${ctxPath}/module/resources/images/my/red_pack.png" />
			<img src="${ctxPath}/module/resources/images/my/red_pack.png" />
			<img src="${ctxPath}/module/resources/images/my/red_pack.png" />
			<img src="${ctxPath}/module/resources/images/my/red_pack.png" /></div> --%>
		<div class="mask_click">
		 	<div><button id="getRedPack" style="letter-spacing:0.7rem;border-radius:1rem;font-size:1.4rem;width:50%;height:3rem;color:#fff;background-color: #f00;font-weight:bold;">抢红包</button></div>
		</div>
	</div>

    <div class="mask_red_pack" id="get_redpack">
        <div class="red_pack_details">
            <div class="get_money">
                <p><span>0</span>元</p>
            </div>
            <a  class="check">查看红包详情</a>
            <input type="hidden" id="redPackId">
        </div>
    </div>
    <div class="mask_red_pack"  id="no_redpack">
        <div class="red_pack_details">
           <div class="no_money">
               <a class="redpack_close"></a>
               <span>领完啦!</span>  
           </div>
        </div>
    </div>
	<script src="${ctxPath}/module/resources/js/jquery.min.js"></script> 
    <script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
    <script type="text/javascript">
	    $(function(){
	    	$("#getRedPack").click(function() {
	    		//$('#getRedPack').attr('disabled',"true");
	    		$.post(_ctxPath + "/w/userMy/getRedPack", function(res){
	    			var result=eval(res);
	  				if(result.status == 1 ) { //表示红包成功领取
	  					$(".get_money span").text(result.data.amount);
	  					$("#redPackId").val(result.data._id);
	  					$("#get_redpack").fadeIn(500);
	  				}  else {  
	  					alert(result.message);
	  				}
	  			  //  $('#getRedPack').removeAttr("disabled");
	            },"json");
	    	});
	    	//function findRedPack(){
	    		//$(".mask_click").unbind("click");
	    		/* $(".red_bag").slideDown(1500);
	    		$(".red_pack_container img").eq(0).addClass("img01");
	    		$(".red_pack_container img").eq(1).addClass("img02");
	    		$(".red_pack_container img").eq(2).addClass("img03");
	    		$(".red_pack_container img").eq(3).addClass("img04");
	    		$(".red_pack_container img").eq(4).addClass("img05");
	    		$(".red_pack_container img").eq(5).addClass("img06");
	    		$(".red_pack_container img").eq(6).addClass("img07");
	    		$(".red_pack_container img").eq(7).addClass("img08");
	    		$(".red_pack_container img").eq(8).addClass("img09"); */
	       /*  $(".redpack_close").click(function(){
	            $("#no_redpack").fadeOut(500);
	        }); */
	        $(".check").click(function() {
	        	var redPackId = $.trim($("#redPackId").val());
	        	window.location.href = _ctxPath +"/w/userMy/redDetail?redPackId=" + redPackId; 
	        });
	    });
    </script>
</body>
</html>