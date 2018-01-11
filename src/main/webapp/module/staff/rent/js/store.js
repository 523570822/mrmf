var paxu="up";
var zulimoshi="所有模式";
$(function(){
    $.ajaxSetup({
        async: false
    });
    organList(1);
    $(".hot_sort").click(function(){
        $('.select_nav').fadeOut('fast');
        $('.com_back_bg ').fadeOut('fast');
        $('.area_m').fadeOut('fast');

        organList(1);
    });
    $(".distance_sort").click(function(){
        $('.select_nav').fadeOut('fast');
        $('.com_back_bg ').fadeOut('fast');
        $('.area_m').fadeOut('fast');
        organList(1);
    });
    $(window).scroll(function(){
        //alert($(window).scrollTop()+"::"+($(document).height() - $(window).height()));
        //alert($(window).scrollTop());
        //alert("整个页面的高度"+$(document).height());
        //alert("当前窗口高度"+$(window).height());
        //alert($(document).height() - $(window).height());
        //alert($(window).scrollTop() == $(document).height() - $(window).height());
        if($(window).scrollTop() == $(document).height() - $(window).height()){//滚动条距离
            //alert(1111);
            organList();
        }
    });
    $("#search").click(function(){
        $("#stroe_list_form").attr("action",_ctxPath+"/w/staff/toSearchOrgan").submit();
    });
    $("#fen").click(function () {
        zulimoshi="分账模式";
        organList(1);

    });
    $("#zu").click(function () {
        zulimoshi="租金模式";
        organList(1);
    });
    $("#suoyou").click(function () {
        zulimoshi="所有模式";
        organList(1);
    });
    $("#up_sort").click(function () {
        paxu="up";
        organList(1);
    });
    $("#down_sort").click(function () {
        paxu="down";
        organList(1);
    });
});

function organList(page){
    //alert("翻頁");
    if(page==1){
        $("#rent_list").html("");
    }else{
        var nextPage=parseInt($("#page").val())+1;
        page=nextPage;
        var pages=parseInt($("#pages").val());
        if(nextPage>pages){
            return;
        }
    }
    var type=$("#organ_type").val();
    var city=$("#city").val();
//	type 店铺类型
//	city 城市
//	district 区域
//	region 商圈
//	longitude 经度
//	latitude 纬度
//	maxDistance 搜索半径
//	followCount 关注（排序条件如果不为空就按关注的倒叙排序）
    var district=$("#district").val();
    var region=$("#region").val();
    //var longitude="116.391786289962";
    //var latitude="39.9077741797592";
    var longitude=$("#longitude").val();
    var latitude=$("#latitude").val();
    //alert(longitude);
    var maxDistance="-1";
    var followCount=$("#followCount").val();
    var distance=$("#distance").val();
    //alert("到这里了");
    var pagesize=20;
    $.post(_ctxPath + "/w/staff/organList1",{'paixu':paxu,'type':type,'city':city,'district':district,'region':region,'longitude':longitude,'latitude':latitude,'distance':distance,'maxDistance':maxDistance,'followCount':followCount,'page':page,'pagesize':pagesize},
        function(data){
            var obj=eval(data);
            var html='';
            $("#pages").val(data.pages);
            $("#page").val(data.page);
            if (obj.data.length==0) {
                $("#rent_list").addClass("nodateul");
                html+='<li style="background:#eee;border:0;" class="nodatali"><div class="notDate"></div><div class="notDateTitle">暂无相关数据</div></li>';
                $("#rent_list").html($("#rent_list").html()+html);
            }else {
                $("#rent_list").removeClass("nodateul");

                    for (var i = 0; i < obj.data.length; i++) {
                        if (zulimoshi == "所有模式") {
                            if (obj.data[i].num1 != null) {
                                if (obj.data[i].logo == "" || obj.data[i].logo == "null") {
                                    html += '<li class="clear" onclick="toStoreDetail(this)">' +
                                        '<input type="hidden" name="organid" value="' + obj.data[i]._id + '" /><input name="distance" type="hidden" value="' + obj.data[i].distance + obj.data[i].unit + '" />' +
                                        '<div class="dianpu1"><img src="'+_ctxPath+'/module/resources/images/nopicture.jpg"></div>';
                                } else {
                                    html += '<li class="clear" onclick="toStoreDetail(this)">' +
                                        '<input type="hidden" name="organid" value="' + obj.data[i]._id + '" /><input name="distance" type="hidden" value="' + obj.data[i].distance + obj.data[i].unit + '" />' +
                                        '<div class="dianpu"><img src="' + _ossImageHost + obj.data[i].logo + '"></div>';
                                }
                                html += '<div class="zhong clear"><h3>' + obj.data[i].name + '</h3>';
                                html += ' <div class="gongwei">' + obj.data[i].num1 + ' 个工位可租赁</div>';
                                html += '<div class="dizhi">' + obj.data[i].district + ' ' + obj.data[i].district + '商区</div>';


                                if (obj.data[i].leaseType == 0) {
                                    html += '<div class="moshi"></div>';
                                    html += ' <div class="juli clear">距离' + obj.data[i].distance + '' + obj.data[i].unit + '</div></div> ';
                                    html+='<div><span> ￥</span><p>' + obj.data[i].leaseMoney + '</p><span>/天</span></div></li>';
                                } else if (obj.data[i].leaseType == 1) {
                                    html += '<div class="moshi1"></div>';
                                    html += ' <div class="juli clear">距离' + obj.data[i].distance + '' + obj.data[i].unit + '</div></div> ';
                                    html+='<div><span></span><p>分账模式</p><span></span></div></li>';
                                }

                            }
                        } else if (zulimoshi == "租金模式" && obj.data[i].leaseType == 0) {
                            if (obj.data[i].num1 != null) {
                                if (obj.data[i].logo == "" || obj.data[i].logo == "null") {
                                    html += '<li class="clear" onclick="toStoreDetail(this)">' +
                                        '<input type="hidden" name="organid" value="' + obj.data[i]._id + '" /><input name="distance" type="hidden" value="' + obj.data[i].distance + obj.data[i].unit + '" />' +
                                        '<div class="dianpu1"><img src=" '+_ctxPath+'/module/resources/images/nopicture.jpg"></div>';
                                } else {
                                    html += '<li class="clear" onclick="toStoreDetail(this)">' +
                                        '<input type="hidden" name="organid" value="' + obj.data[i]._id + '" /><input name="distance" type="hidden" value="' + obj.data[i].distance + obj.data[i].unit + '" />' +
                                        '<div class="dianpu"><img src="' + _ossImageHost + obj.data[i].logo + '"></div>';
                                }
                                html += '<div class="zhong clear"><h3>' + obj.data[i].name + '</h3>';
                                html += ' <div class="gongwei">' + obj.data[i].num1 + ' 个工位可租赁</div>';
                                html += '<div class="dizhi">' + obj.data[i].district + ' ' + obj.data[i].district + '商区</div>';
                                html += '<div class="moshi"></div>';
                                html += ' <div class="juli clear">距离' + obj.data[i].distance + '' + obj.data[i].unit + '</div></div> ';
                                html+='<div><span>￥</span><p> ' + obj.data[i].leaseMoney + '</p><span>/天</span></div></li>';

                            }

                        } else if (zulimoshi == "分账模式" && obj.data[i].leaseType == 1) {
                            if (obj.data[i].num1 != null) {
                                if (obj.data[i].logo == "" || obj.data[i].logo == "null") {
                                    html += '<li class="clear" onclick="toStoreDetail(this)">' +
                                        '<input type="hidden" name="organid" value="' + obj.data[i]._id + '" /><input name="distance" type="hidden" value="' + obj.data[i].distance + obj.data[i].unit + '" />' +
                                        '<div class="dianpu1"><img src='+_ctxPath+'"module/resources/images/nopicture.jpg"></div>';
                                } else {
                                    html += '<li class="clear" onclick="toStoreDetail(this)">' +
                                        '<input type="hidden" name="organid" value="' + obj.data[i]._id + '" /><input name="distance" type="hidden" value="' + obj.data[i].distance + obj.data[i].unit + '" />' +
                                        '<div class="dianpu"><img src="' + _ossImageHost + obj.data[i].logo + '"></div>';
                                }
                                html += '<div class="zhong clear"><h3>' + obj.data[i].name + '</h3>';
                                html += ' <div class="gongwei">' + obj.data[i].num1 + ' 个工位可租赁</div>';
                                html += '<div class="dizhi">' + obj.data[i].district + ' ' + obj.data[i].district + '商区</div>';
                                html += '<div class="moshi1"></div>';
                                html += ' <div class="juli clear">距离' + obj.data[i].distance + '' + obj.data[i].unit + '</div></div> ';
                                html+='<div><span></span><p>分账模式</p><span></span></div></li>';


                            }
                        }

                }
                $("#rent_list").html($("#rent_list").html()+html);
            }
        },
        "json");//这里返回的类型有：json,html,xml,text
}
function toStoreDetail(thisli){
    var organId=$(thisli).children('input:eq(0)').val();
    var distance=$(thisli).children('input:eq(1)').val();
    var staffId=$("#staffId").val();
    // var organId=$("#organId").val();
    var cityId=$("#cityId").val();
    var city=$("#city").val();
//	alert(city+"::"+cityId);
    window.location.href = _ctxPath + "/w/staff/toOrganDetail.do?organId="+organId+"&staffId="+staffId+"&distance="+distance+"&cityId="+cityId+"&city="+encodeURIComponent(encodeURIComponent(city));
}
