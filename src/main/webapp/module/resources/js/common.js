/**
 * Created by seven on 2016/3/8.
 */

$(document).ready(function(){
   //手机号校验
    $('#finish').click(function(){
        var phone = $("#telNumber").val();
        if(phone && /^1[3|4|5|8]\d{9}$/.test(phone)==false){
            $('.error_tel').fadeIn();
        }
    });
    $('.modal_bg').click(function(){
        $('.error_tel').fadeOut();
    });
    //性别切换
    $('.sex_select li').click(function(){
       $(this).addClass('active').siblings().removeClass('active');
    });

    //美丽之星筛选
    $('#followCount').click(function(){
        $(this).addClass('active');
        $('#priceOrder').removeClass('active');
        $('#distance').removeClass('active');
        findList(1);
    });
    $('#priceOrder').click(function(){
        $(this).addClass('active');
        $('#followCount').removeClass('active');
        $('#distance').removeClass('active');
        $('#clickCount').val(parseInt($('#clickCount').val()) + 1);
        findList(1);
    });
    $('#distance').click(function(){
        $(this).addClass('active');
        $('#followCount').removeClass('active');
        $('#priceOrder').removeClass('active');
        findList(1);
    });
    $('.m_screen').click(function(){
        $('.m-1').animate({
            right:0
        },500);
        $('.modal_bg').fadeIn('fast');
    });

    $('.modal_nav .cancel').click(function(){
        $('.m-1').animate({
            right:"-100%"
        },500);
        $('.modal_bg').fadeOut('fast');
        $('.m-2').animate({
            right:'-100%'
        },500);
        $('.m-3').animate({
            right:'-100%'
        },500);
    });
    $('.modal_bg').click(function(){
        $('.m-1').animate({
            right:"-100%"
        },500);
        $('.modal_bg').fadeOut('fast');
        $('.m-2').animate({
            right:'-100%'
        },500);
        $('.m-3').animate({
            right:'-100%'
        },500);
    });

    $('.sex_scr').click(function(){
        $('.m-2').animate({
            right:0
        },500);
    });


    $('.m-2 li').click(function(){
        $(this).addClass('active').siblings().removeClass('active');
        $($('.sex_scr').find('div').get(1)).text($($(this).find('div').get(0)).text());
        $('.m-2').animate({
            right:'-100%'
        },500);
    });
    $('.band').click(function() {
        $('.m-3').animate({
            right:0
        },500);
    });
    $('.band_listing li').click(function(){
        $(this).addClass('active').siblings().removeClass('active');
        $('#band_id').text($($(this).find('div').get(0)).text());
        $('.m-3').animate({
            right:'-100%'
        },500);
    });
    $('.modal_nav .sure').click(function() {
    	$('.m-1').animate({
            right:"-100%"
        },500);
        $('.modal_bg').fadeOut('fast');
    	findList(1);
    });
    /*  my start   */
    $('.message_selector li').click(function() {
        $(this).addClass('active').siblings().removeClass('active');
    });
    /* 预约详情   */
    $('.common_btn button').click(function() {
    	$('.common_btn button').attr('disabled',"true");
    	var staffId = $.trim($('#staffId').val());
    	var organId = $.trim($('#organId').val());
    	var appointTime = $.trim($('#appointTime').text());
    	var caseId = $.trim($('#caseId').val());
    	var orderPrice = $.trim($('#orderPrice').text());
    	var storeAddress = $.trim($('#storeAddress').text());
    	if(appointTime == "请选择预约时间") {
    		alert("请选择预约时间和地点");
    		return;
    	}
        $.post(_ctxPath + "/w/home/commitApoint",{'staffId':staffId,'organId':organId,'appointTime':appointTime,'caseId':caseId,'orderPrice':orderPrice},
				  function(data){
    		if(data!='nophone' && data!= "failure")
    	    {
    			$("#orderId").val(data);
	    		$('.model_bg_appoint').fadeIn('fast');
	            $('.modal_appoint').fadeIn('fast');
    		} else if(data=='nophone'){
    			window.location.href = _ctxPath + "/w/home/toBindPhone";
    		} else {
    			layer.alert('预约失败了');
    		}
    		$('.common_btn button').removeAttr("disabled");
		});
    });
    $('.ret_home').click(function() {
    	window.location.href = _ctxPath + "/w/home/toHomePage";
    });
    
    $('.order_info').click(function() {
    	var orderId = $.trim($("#orderId").val());
    	window.location.href=_ctxPath + "/w/userMy/orderDetail?orderId="+orderId+"&type=2";
    	//在这里添加去订单详情的链接
    });
   
    /* 预约详情   */
    /* get red pack */
    $('.red_items>li').click(function() {
        $('.red_pack_modal_bg').fadeIn('fast');
        $('.red_m').fadeIn('fast');
    });
    $('.red_pack_modal_bg').click(function() {
        $('.red_m').fadeOut('fast');
        $(this).fadeOut('fast');
    });
    $('.red_cl').click(function() {
        $('.red_m').fadeOut('fast');
        $('.red_pack_modal_bg').fadeOut('fast');
    });

    /* get red pack end */
    $('.sort_enq li').click(function() {
        $(this).css('color','#f4370b').siblings().css('color','#22242a');
    });
    
    /*  密码框  */
    $('.btn_next button').click(function() {
  	  var realboxLength = Number($('.realbox').val().length);
  	  if(realboxLength != 6) {
  		  alert("你的输入不合法");
  		  return;
  	  } 
  	  $('#form').submit();  
    });
    $('.virbox').click(function(){
        $('.realbox').focus();
        $('.virbox span').css('background','#FFFECF');
    });
    $('.realbox').blur(function(){
        $('.virbox span').css('background','#ffffff');
    });
    $('.realbox').bind('input propertychange', function() {
        $('.virbox span').html('');
        var realboxLength = Number($('.realbox').val().length);
        if(realboxLength === 0){
        }
        for(var i=0;i<realboxLength;i++){ 
            var words = $('.realbox').val();
            $('.virbox span').eq(i).html('.');
        }
    });
    /*  密码框 end*/

    // 预约时间
   $('#scroller li').click(function(){
        $(this).addClass('active').siblings().removeClass('active');
    });

    $('.dp_header li').click(function(){
        $(this).addClass('active').siblings().removeClass('active');
    });
    $('.dp_content .title li').click(function(){
        $(this).addClass('active').siblings().removeClass('active');
        var v=$(this).attr('id');
        if(v=='v1'){
            $('.db_modal_1').fadeIn();
            $('.db_modal_content').slideDown('fast');
            $('.db_modal_content li').click(function(){
                $(this).addClass('active').siblings().removeClass('active');
            });
            $('body').addClass('hide');
            $('.modal_bg').click(function(){
                $('.db_modal_content').slideUp();
                $('.db_modal').fadeOut();
                $('#v1').removeClass('active');
                $('body').removeClass('hide');
            });
        }
    });
});


