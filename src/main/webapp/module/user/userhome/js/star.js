$(function () {
    findList(1);
    $(window).scroll(function () {
        if ($(window).scrollTop() == $(document).height() - $(window).height()) {
            findList();
        }
    });
    /* 点击图标跳转到搜索技师界面 */
    $(".search").click(function () {
        var userId = $("#userId").val();
        window.location.href = _ctxPath + "/w/home/toQueryStaff.do?userId="+userId;
       
    });

    /* 点击搜索图标 */
    $('.search_icon').click(function () {
        queryStaff(1);
    });

});


function queryStaff(page) {
    if (page == 1) {
        $("#staff_list").html("");
    } else {
        var nextPage = parseInt($("#page").val()) + 1;
        page = nextPage;
        var pages = parseInt($("#pages").val());
        if (nextPage > pages) {
            return;
        }
    }

    var name = $.trim($('.search_input').find('input').val());
    if(name == "") {
        alert("请输入您要搜索的技师");
        return;
    }
    var longitude = $.trim($('#longitude').val());
    var latitude = $.trim($('#latitude').val());
    if (longitude == '' || longitude == '0') {
        alert('没有获取到位置，请从进入！');
        return;
    }
    if (latitude == '' || latitude == '0') {
        alert('没有获取到位置，请从进入！');
        return;
    }
    var maxDistance = "-1";
    var sex = $.trim($('.sex_scr .txt').text());

    var band = $.trim($('.band .txt').text());

    /*var time=$("#time_d").val();*/
    var distance = "";
    var priceOrder = "";
    var followCount = "";
    var clickCount = "0";
    if ($('#distance').hasClass('active')) {
        distance = "distance";
    }
    if ($('#priceOrder').hasClass('active')) {
        priceOrder = "priceOrder";
        clickCount = $('#clickCount').val();
    }

    if ($('#followCount').hasClass('active')) {
        followCount = "followCount";
    }
    $.trim($('.sex_scr .txt').text());
    $.trim($('.band .txt').text());
    $.ajaxSetup({
        async: false
    });
    $.post(_ctxPath + "/w/home/queryStaff", {
            'longitude': longitude,
            'latitude': latitude,
            'maxDistance': maxDistance,
            'sex': sex,
            'band': band, /*'time':time,*/
            'distance': distance,
            'startPrice': priceOrder,
            'clickCount': clickCount,
            'followCount': followCount,
            'page': page,
            'name': name

        },
        function (data) {
            var obj = eval(data);
            $("#pages").val(data.pages);
            $("#page").val(data.page);
            //var html = "";
            if (obj.data.length == 0) {
                var html1 = '<div style="list-style:none;background:#eee;border:0;"><div class="notOrder"></div><div ' +
                    'class="notOrderTitle">暂无相关数据</div></div>';
                $("#staff_list").html($("#staff_list").html() + html1);
            }
            for (var i = 0; i < obj.data.length; i++) {
                var html = '<li onclick="goDetail(this)";><input type="hidden" value=' + obj.data[i]._id +
                    ' /><div class="col-2 tx"> <img src="' + _ossImageHost + obj.data[i].logo + '@!avatar' + '"/></div> <div class="col-5 txt">' +
                    '<h4>' + obj.data[i].name + '</h4><ul class="flowers">';
                if (obj.data[i].evaluateCount == null || obj.data[i].evaluateCount == "") {
                    obj.data[i].evaluateCount = 1;
                }
                var count = Math.ceil(obj.data[i].zanCount / obj.data[i].evaluateCount);
                for (var j = 0; j < count; j++) {
                    html += '<li></li>';
                }
                html += '</ul><p><span>' + obj.data[i].followCount + '</span>人关注</p></div><div class="col-3 location">';
                if (!isNaN(obj.data[i].distance)) {
                    html += '<div><i></i>' + obj.data[i].distance + obj.data[i].unit + '</div>';
                }
                html += '<div class="price">&yen; <span>' + obj.data[i].startPrice + '</span> 起</div></div></li>';
                $("#staff_list").html($("#staff_list").html() + html);
            }
        }, "json");//这里返回的类型有:json

}

function findList(page) {
    if (page == 1) {
        $("#list").html("");

    } else {
        var nextPage = parseInt($("#page").val()) + 1;
        page = nextPage;
        var pages = parseInt($("#pages").val());
        if (nextPage > pages) {
            return;
        }
    }
    var longitude = $.trim($('#longitude').val());
    var latitude = $.trim($('#latitude').val());
    if (longitude == '' || longitude == '0') {
        alert('没有获取到位置，请从进入！');
        return;
    }
    if (latitude == '' || latitude == '0') {
        alert('没有获取到位置，请从进入！');
        return;
    }
    var maxDistance = "-1";
    var sex = $.trim($('.sex_scr .txt').text());
    ;
    var band = $.trim($('.band .txt').text());
    ;
    /*var time=$("#time_d").val();*/
    var distance = "";
    var priceOrder = "";
    var followCount = "";
    var clickCount = "0";
    if ($('#distance').hasClass('active')) {
        distance = "distance";
    }
    if ($('#priceOrder').hasClass('active')) {
        priceOrder = "priceOrder";
        clickCount = $('#clickCount').val();
    }

    if ($('#followCount').hasClass('active')) {
        followCount = "followCount";
    }
    $.trim($('.sex_scr .txt').text());
    $.trim($('.band .txt').text());
    $.ajaxSetup({
        async: false
    });
    $.post(_ctxPath + "/w/home/staffList", {
            'longitude': longitude,
            'latitude': latitude,
            'maxDistance': maxDistance,
            'sex': sex,
            'band': band, /*'time':time,*/
            'distance': distance,
            'startPrice': priceOrder,
            'clickCount': clickCount,
            'followCount': followCount,
            'page': page
        },
        function (data) {
            var obj = eval(data);
            $("#pages").val(data.pages);
            $("#page").val(data.page);
            //var html = "";
            if (obj.data.length == 0) {
                var html1 = '<div style="list-style:none;background:#eee;border:0;"><div class="notOrder"></div><div ' +
                    'class="notOrderTitle">暂无相关数据</div></div>';
                $("#list").html($("#list").html() + html1);
            }
            for (var i = 0; i < obj.data.length; i++) {
                var html = '<li onclick="goDetail(this)";><input type="hidden" value=' + obj.data[i]._id +
                    ' /><div class="col-2 tx"> <img src="' + _ossImageHost + obj.data[i].logo + '@!avatar' + '"/></div> <div class="col-5 txt">' +
                    '<h4>' + obj.data[i].name + '</h4><ul class="flowers">';
                if (obj.data[i].evaluateCount == null || obj.data[i].evaluateCount == "") {
                    obj.data[i].evaluateCount = 1;
                }
                var count = Math.ceil(obj.data[i].zanCount / obj.data[i].evaluateCount);
                for (var j = 0; j < count; j++) {
                    html += '<li></li>';
                }
                html += '</ul><p><span>' + obj.data[i].followCount + '</span>人关注</p></div><div class="col-3 location">';
                if (!isNaN(obj.data[i].distance)) {
                    html += '<div><i></i>' + obj.data[i].distance + obj.data[i].unit + '</div>';
                }
                html += '<div class="price">&yen; <span>' + obj.data[i].startPrice + '</span> 起</div></div></li>';
                $("#list").html($("#list").html() + html);
            }
        }, "json");//这里返回的类型有:json
}
function goDetail(Item) {
    var sId = $.trim($(Item).find('input').val());
    window.location.href = _ctxPath + "/w/home/staffDetail?staffId=" + sId;
}










