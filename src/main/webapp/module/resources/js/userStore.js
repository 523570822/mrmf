/**
 * Created by dd on 2016/4/15.
 * version 1.1
 * author yangshaodong
 */

$(function () {
	$("#organ_list_back").click(function(){
		window.location.href = _ctxPath + "/w/home/toHomePage.do";
	});
	$("#organ_my_back").click(function(){
		var userId=$("#userId").val();
		window.location.href = _ctxPath + "/w/userMy/toUserMyHome.do?userId="+userId;
	});
    $('.sort_fun li').eq(0).click(function (){
        $('#img1')[0].src=_ctxPath+'/module/resources/images/home/icon_aorrw_down_pre.png';
        $('.sort_fun_m li').eq(0).css('color','#f4370b');
        $('.sort_fun_m li').eq(1).css('color','#22242a');
        $('.area_m').fadeOut('fast');
        $('.com_back_bg').fadeIn('fast');
        $('.select_nav').fadeIn('fast');
        $('.store_type').fadeIn('fast');
        
        
        
    });

    $('.sort_fun li').eq(2).click(function (){
//        $('.com_back_bg ').fadeIn('fast');
//        $('.select_nav').fadeIn('fast');
//        $('#img1')[0].src=_ctxPath+'/module/resources/images/home/icon_aorrw_down_pre.png';
//        $('.sort_fun_m li').eq(0).css('color','#f4370b');
    });



    $('.com_back_bg ').click(function() {
        $('.select_nav').fadeOut('fast');
        $(this).fadeOut('fast');
    });

   $('.store_type li').click(function () {
       $(this).addClass('add_selected').siblings().removeClass('add_selected');
       $('.sort_fun li').eq(0).html($(this).text() + '&nbsp;&nbsp;<img src='+_ctxPath+'/module/resources/images/home/icon_aorrw_down.png>');
       $('.select_nav').fadeOut('fast');
       $('.com_back_bg ').fadeOut('fast');
       $("#organ_type").val($(this).text());
       organList(1);
   });
  
   $('.sort_fun_m li').eq(0).click(function () {
       $('.area_m').fadeOut('fast');
       $('.store_type').fadeIn('fast');
   });
   

});


function getCity(){
	var cityId=$("#cityId").val();
	var districtId=$("#districtId").val();
	$.post(_ctxPath + "/w/organ/districtList",{'cityId':cityId,'districtId':districtId},
			  function(data){
				var obj=eval(data);
				console.log(data);
	},
			  "json");//这里返回的类型有：json,html,xml,text	
}
