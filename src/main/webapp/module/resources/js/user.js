$(function() {
	$(window).scroll(function(){
	    if($(window).scrollTop() == $(document).height() - $(window).height()){
	    	queryList();
	    }
	});
	/* 点击搜索图标 */
	$('.search_icon').click(function() {
		queryList(1);
	});

	/* 点击跳转到上一个界面；
	 */
	$('.search_cancel').click(function() {
		window.history.go(-1);
	});
    $('.modal_bg,#type_m').click(function() {
        $('.modal_bg').fadeOut('fast');
        $('.modal_content').fadeOut('fast');
    });
    /*    申请提现的模态框   */
    $('#com_cash').click(function() {
        console.info('申请提现的模态框');
        $('.com_back_bg').fadeIn('fast');
        $('.modal_dialog').fadeIn('fast');
    });

    $('.com_back_bg').click(function() {
        $(this).fadeOut('fast');
        $('.modal_dialog').fadeOut('fast');
    });

    /*  密码框 */
    $('.virbox_m').click(function(){
        $('.realbox_m').focus();
        $('.virbox_m span').css('background','#FFFECF');
    });
    $('.realbox_m').blur(function(){
        $('.virbox_m span').css('background','#ffffff');
    });
    $('.realbox_m').keyup(function(){
        $('.virbox_m span').html('');
        var realboxLength = Number($('.realbox_m').val().length);
        if(realboxLength === 0){
        }
        for(var i=0;i<realboxLength;i++){
            var words = $('.realbox_m').val();
            $('.virbox_m span').eq(i).html('.');
        }
        if(realboxLength == 6 ) {
            var pwd = $('.realbox_m').val();
            console.info(pwd);
        }
    });
    $('.modal_t>i').click(function () {
        $('.com_back_bg').fadeOut('fast');
        $('.modal_dialog').fadeOut('fast');
    });
    /*    申请提现的模态框  end  */
    /* donation start  */
    $('#com_dona').click(function() {
        $('.mb1').fadeIn('fast');
        $('.modal_dialog1').fadeIn('fast');

        var realboxLength = Number($('.realbox_m1').val().length);
        if(realboxLength === 0){
        }
        for(var i=0;i<realboxLength;i++){
            var words = $('.realbox_m1').val();
            $('.virbox_m1 span').eq(i).html('');
        }
        $('.realbox_m1').val('');
    });

    $('.mb1').click(function() {
        $(this).fadeOut('fast');
        $('.modal_dialog1').fadeOut('fast');
    });
    $('.modal_t1>i').click(function () {
        $('.mb1').fadeOut('fast');
        $('.modal_dialog1').fadeOut('fast');
    });


    /*  密码框 */
    $('.virbox_m1').click(function(){
        $('.realbox_m1').focus();
        $('.virbox_m1 span').css('background','#FFFECF');
    });
    $('.realbox_m1').blur(function(){
        $('.virbox_m1 span').css('background','#ffffff');
    });
    $('.realbox_m1').keyup(function(){
        $('.virbox_m1 span').html('');
        var realboxLength = Number($('.realbox_m1').val().length);
        if(realboxLength === 0){
        }
        for(var i=0;i<realboxLength;i++){
            var words = $('.realbox_m1').val();
            $('.virbox_m1 span').eq(i).html('.');
        }
        if(realboxLength == 6 ) {
            var pwd = $('.realbox_m1').val();
           //在这里可以判断转赠是否成功！ 我这里只是做了一个简单的判断实例
            if(pwd=='111111') {
                 $('.modal_dialog1').fadeOut('fast');
                 $('.modal_dialog_s').fadeIn('fast');
            } else {
                var realboxLength = Number($('.realbox_m_f').val().length);
                if(realboxLength === 0){
                }
                for(var i=0;i<realboxLength;i++){
                    var words = $('.realbox_m_f').val();
                    $('.virbox_m_f span').eq(i).html('');
                }
                $('.realbox_m_f').val('');
                $('.modal_dialog1').fadeOut('fast');
                $('.modal_dialog_f').fadeIn('fast');
            }
        }
    });
    /*  密码框end  */
    /* 转赠成功对话框  */
    $('.dialog_s_down').click(function() {
        $('.modal_dialog_s').fadeOut('fast');
        $('.mb1').fadeOut('fast');
    });
    /* 转赠成功对话框end */

    $('.modal_t_f i').click(function() {
        $('.modal_dialog_f').fadeOut('fast');
        $('.mb1').fadeOut('fast');
    });
    /*  密码框 */
    $('.virbox_m_f').click(function(){
        $('.realbox_m_f').focus();
        $('.virbox_m_f span').css('background','#FFFECF');
    });
    $('.realbox_m_f').blur(function(){
        $('.virbox_m_f span').css('background','#ffffff');
    });
    $('.realbox_m_f').keyup(function(){
        $('.virbox_m_f span').html('');
        var realboxLength = Number($('.realbox_m_f').val().length);
        if(realboxLength === 0){
        }
        for(var i=0;i<realboxLength;i++){
            var words = $('.realbox_m_f').val();
            $('.virbox_m_f span').eq(i).html('.');
        }
        if(realboxLength == 6 ) {
            var pwd = $('.realbox_m_f').val();
            //在这里可以判断转赠是否成功！ 我这里只是做了一个简单的判断实例
            if(pwd=='111111') {
                $('.modal_dialog_f').fadeOut('fast');
                $('.modal_dialog_s').fadeIn('fast');
            } else {
                $('.modal_dialog1').fadeOut('fast');
                $('.modal_dialog_f').fadeIn('fast');
                var realboxLength = Number($('.realbox_m_f').val().length);
                if(realboxLength === 0){
                }
                for(var i=0;i<realboxLength;i++){
                    var words = $('.realbox_m_f').val();
                    $('.virbox_m_f span').eq(i).html('');
                }
                $('.realbox_m_f').val('');
                $('.modal_dialog1').fadeOut('fast');
                $('.modal_dialog_f').fadeIn('fast');
            }
        }
    });
    /*  密码框end  */
    /* donation end  */
});
function queryList(page) {
    var homeType =$("#homeType").val();
    if(page==1){
        $("#hair_list2").html("");
    }else{
        var nextPage=parseInt($("#page1").val())+1;
        page=nextPage;
        var pages=parseInt($("#pages1").val());
        if(nextPage>pages){
            return;
        }
    }
    var title = $.trim($('.search_input').find('input').val());
    if(title == "") {
        alert("请输入案例名称");
        return;
    }
    $.post(_ctxPath + "/w/home/queryCase",{'page':page,'title':title,"homeType":homeType},
        function(data){
            var obj=eval(data);
            $("#pages1").val(data.pages);
            $("#page1").val(data.page);
            if(obj.data.length == 0) {
                $(".hair_list").css("backgroundColor","#f8f8f8");
                var html ='<div style="float: none;border: none;"><div class="notOrder"></div><div ' +
                    'class="notOrderTitle">暂无相关数据</div></div>';
                $("#hair_list2").html($("#hair_list2").html()+ html);
                return;
            }else {
                for(var i=0;i<obj.data.length;i++){
                    var html ='<div onclick="goCaseDes(this)"><input type="hidden" value="'+obj.data[i]._id+'">'
                        +'<div class="hair_pic"><img src="'+_ossImageHost + obj.data[i].logo[0]+'@!avatar'+'"/></div><div><strong>'+
                        obj.data[i].title+'</strong></div><div class="margin_btm"><div class="hair_price_div">'+
                        '<span>&yen;</span><span>'+obj.data[i].price+'</span></div><div class="attention_font">'
                        +'<span>关注</span><span>' +obj.data[i].followCount +'</span></div></div></div>';
                    $("#hair_list2").html($("#hair_list2").html()+ html);}
            }

        },"json");//这里返回的类型有：json,html,xml,text
}
/* 去案例详情 */
function goCaseDes(thisli){
	var caseId = $(thisli).find('input').val();
	var homeType = $.trim($('#homeType').val());
	window.location.href = _ctxPath+'/w/home/toCaseDes?caseId='+caseId + '&homeType=' + encodeURIComponent(encodeURIComponent(homeType));
}
