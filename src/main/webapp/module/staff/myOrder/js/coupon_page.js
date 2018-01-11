$(function(){
    $.ajaxSetup({
        async: false
    });
    couponList();
    $(window).scroll(function(){
        if($(window).scrollTop() == $(document).height() - $(window).height()){
            couponList();

        }
    });

    /*
    * 选项卡切换效果
    */

    $(".control-tab ul li").click(function () {
        $(".control-tab ul li").removeClass('active');
        $(this).addClass('active');
        var index=$(this).index();
        if(index==0){//全部
            $("#type").val("1");
            $("#pages").val("");
            $("#page").val("");
            $("#coupon_list").html("");
            couponList();
        }else if(index==1){//未使用
            $("#type").val("2");
            $("#pages").val("");
            $("#page").val("");
            $("#coupon_list").html("");
            couponList();
        }else if(index==2){//已使用
            $("#type").val("3");
            $("#pages").val("");
            $("#page").val("");
            $("#coupon_list").html("");
            couponList();
        }else if(index==3){//已过期
            $("#type").val("4");
            $("#pages").val("");
            $("#page").val("");
            $("#coupon_list").html("");
            couponList();
        }
    });


   /* $(".messTitle div").click(function(){
        $(this).siblings().removeClass("sel_message");
        $(this).addClass("sel_message");
        var index=$(this).index();
        if(index==0){//全部
            $("#type").val("1");
            $("#pages").val("");
            $("#page").val("");
            $("#coupon_list").html("");
            couponList();
        }else if(index==1){//未使用
            $("#type").val("2");
            $("#pages").val("");
            $("#page").val("");
            $("#coupon_list").html("");
            couponList();
        }else if(index==2){//已使用
            $("#type").val("3");
            $("#pages").val("");
            $("#page").val("");
            $("#coupon_list").html("");
            couponList();
        }else if(index==3){//已过期
            $("#type").val("4");
            $("#pages").val("");
            $("#page").val("");
            $("#coupon_list").html("");
            couponList();
        }
    });*/
});
function couponList(){
    var userId=$("#user_id").val();
    var page=$("#page").val();
    var pages=$("#pages").val();
    var type=$("#type").val();
    if(page!=""){
        page=parseInt(page)+1;
    }else{
        page=1;
    }
    if(pages!=""){
        pages=parseInt(pages);
        if(page>pages){
            return;
        }
    }
    $.post(_ctxPath + "/w/userMy/myCouponList",{'userId':userId,'page':page,'type':type},
        function(data){
            var obj=eval(data);
            var html='';
            $("#pages").val(data.pages);
            $("#page").val(data.page);
            if (obj.data.length==0) {
                html+='<li><div class="notOrder"></div><div class="notOrderTitle">暂无优惠券</div></li>';
                $("#coupon_list").html($("#coupon_list").html()+html);
            }else {
                for(var i=0;i<obj.data.length;i++){
                    if(type!=1){//全部
                        if(type==2){//未使用
                            html += '<li class="coupon-use clearfix">' ;
                        }else if(type==3){
                            html += '<li class="coupon-used clearfix">' ;
                        }else if(type==4){
                            html += '<li class="coupon-dued clearfix">' ;
                        }
                    }else {
                        if(obj.data[i].endTime<new Date().format("yyyy-MM-dd")&&obj.data[i].isUsed==0){
                            html += '<li class="coupon-dued clearfix">' ;
                        }else {
                            if(obj.data[i].isUsed==0){
                                html += '<li class="coupon-use clearfix">' ;
                            }else{
                                html += '<li class="coupon-used clearfix">' ;
                            }
                        }
                    }
                    var mm = obj.data[i].moneyOrRatio;
                    if(obj.data[i].minConsume!=undefined&&obj.data[i].minConsume!=0) {
                        if(obj.data[i].moneyType==1){//折扣或者代金券
                            html+='<div><p>折 <span>' + toDecimal2(mm*10) + '</span></p><span>满'+obj.data[i].minConsume+'元可用</span></div>';
                        }else{
                            html += '<div><p>￥ <span>' + toDecimal2(mm) + '</span></p><span>满'+obj.data[i].minConsume+'元可用</span></div>';
                        }
                    }else {
                        if(obj.data[i].moneyType==1){//折扣或者代金券
                            html += '<div><p style="height: 6.8rem; line-height: 6.8rem;">折 <span>' + toDecimal2(mm*10) + '</span></p></div>';
                        }else{
                            html += '<div><p style="height: 6.8rem; line-height: 6.8rem;">￥ <span>' + toDecimal2(mm) + '</span></p></div>';
                        }
                    }
                    if(obj.data[i].couponType=="店面"){//店面或平台通用
                        if(obj.data[i].typeName!="通用类型"){//美容美足美发通用类型
                            if(obj.data[i].bigSortName!=null){

                                html += '<div><div><h4>'+obj.data[i].shopName+'专用('+obj.data[i].typeName+' '+obj.data[i].bigSortName+')</h4></div>';
                            }else {
                                html += '<div><div><h4>'+obj.data[i].shopName+'专用('+obj.data[i].typeName+')</h4></div>';
                            }
                        }else{
                            html += '<div><div><h4>'+obj.data[i].shopName+'专用</h4></div>';
                        }
                    }else{
                        if(obj.data[i].typeName!="通用类型"){//美容美足美发通用类型
                            if(obj.data[i].bigSortName!=null) {
                                html += '<div><div><h4>平台通用(' + obj.data[i].typeName + ' ' + obj.data[i].bigSortName + ')</h4></div>';
                            }else {
                                html += '<div><div><h4>平台通用(' + obj.data[i].typeName + ')</h4></div>';
                            }
                        }else{
                            html += '<div><div><h4>平台通用</h4></div>';
                        }
                    }
                    if(obj.data[i].endTime==null){
                        html += '<div></div></div></li>';
                    }else{
                        html += '<div><time>有效期至 ' + obj.data[i].endTime + '</time></div></div></li>';
                    }
                }
                $("#coupon_list").html($("#coupon_list").html()+html);
            }
        },
        "json");//这里返回的类型有：json,html,xml,text
    function toDecimal2(x) {
        var f = parseFloat(x);
        if (isNaN(f)) {
            return false;
        }
        var f = Math.round(x*100)/100;
        var s = f.toString();
        var rs = s.indexOf('.');
        if (rs < 0) {
            rs = s.length;
            s += '.';
        }
        while (s.length <= rs + 1) {
            s += '0';
        }
        return s;
    }
}
