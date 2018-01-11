$(function() {
	$('#selectType').click(function() {
		$('#appointTime').val($.trim($('#ap_time').text()));
		$('#organName').val($.trim($('#ap_store').text()));
		$('#addressAndTime').submit();
	});
	
	$('.type_list li').click(function() {
		$('#choiced').appendTo($(this));
	});
	
	$('#back').click(function() {
		window.history.go(-1);
	});
	$("#comType").click(function() {
		$('#type').val($.trim($('#choiced').parent().text()));
		$('#typeForm').submit();
	});
	$('#apTime').click(function() {
		$('#type').val($.trim($('#selectedType').text()));
		$('#typeForm').submit();
	}); 
	$('#goOrganMap').click(function() {
	    var organId =$.trim($("#organId").val());
	    if(organId=="") {
	    	return;
	    }
		window.location.href=_ctxPath+"/w/home/goOrganMap?organId="+organId;
	});
	
	 /* 预约详情   */
    $('.common_btn button').click(function() {
    	var staffId = $.trim($('#staffId').val());
    	var organId = $.trim($('#organId').val());
    	var appointTime = $.trim($('#ap_time').text());
    	var orderPrice = $.trim($('#orderPrice').val());  //这里是订单最低价格
    	var type = $.trim($('#selectedType').text());
    	if(type == "请选择服务类型") {
    		layer.msg("请选择服务类型");
    		return false;
    	}
    	if(appointTime == "请选择预约时间") {
    		layer.msg("请选择预约时间和地点");
    		return false;
    	}
    	$('.common_btn button').attr('disabled',"true");
        $.post(_ctxPath + "/w/home/commitApStaff",{'staffId':staffId,'organId':organId,'appointTime':appointTime,'orderPrice':orderPrice,'type':type},
				  function(data){
    		if(data!='nophone' && data!= "failure" && data!='repeat'){
    			$("#orderId").val(data);
    			$('.model_bg_appoint').fadeIn('fast');
    	        $('.modal_appoint').fadeIn('fast');
    		} else if(data=='nophone'){
    			window.location.href = _ctxPath + "/w/home/toBindPhone";
    		}else if (data=='repeat') {
				layer.msg('您已提交过相同订单');
			} else {
    			layer.alert('预约失败了');
    		}
    		$('.common_btn button').removeAttr("disabled");
		});  
    });
    $('.ret_home').click(function() {
    	window.location.href=_ctxPath + "/w/home/toHomePage";
    });
    /**
     * 去订单详情页面
     */
    $('.order_info').click(function() {
    	var orderId = $.trim($("#orderId").val());
    	window.location.href=_ctxPath + "/w/userMy/orderDetail?orderId="+orderId+"&type=2";
    });
});



