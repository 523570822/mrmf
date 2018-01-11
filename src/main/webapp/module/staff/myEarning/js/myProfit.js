$(function(){
    $.ajaxSetup({
        async: false
    });
    earnList();
    $(window).scroll(function(){//屏幕滚动事件
        if($(window).scrollTop() == $(document).height() - $(window).height()){
            earnList();
        }
    });
});
$("#goMyEarning").click(function () {
    var page=$("#page").val("");
    var pages=$("#pages").val("");
    window.location.href=_ctxPath+"/w/staffMy/myEarn?flag="+1;
})
function earnList(){
    var staffId=$("#staff_id").val();
    var page=$("#page").val();
    var pages=$("#pages").val();
    var type=$("#type").val();
    var name=$("#name").val();
    var appDate = $("#appDate").val();
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

        $.post(_ctxPath + "/w/staffMy/queryMyProfit",{'staffId':staffId,"page":page,"pages":pages,"name":name,"appDate":appDate},
            function(data){
                var thisTime = $("#earn_list ul:last-child").attr("thisTime");
                var obj=eval(data);
                var dataList = obj.data;
                $("#pages").val(data.pages);
                $("#page").val(data.page);
                if (obj.data.length==0) {
                    var html='<div class="notOrder_wallet"></div><i class="notOrderTitle_wallet">暂无相关数据</i>';
                    $("#earn_list").html($("#earn_list").html()+html);
                }else {
                    for(var i=0;i<dataList.length;i++){
                        var mapList = dataList[i].mapList;
                        var mapTime = dataList[i].time;
                        if(thisTime==mapTime){
                            var html="";
                            for(var j=0;j<mapList.length;j++){
                                var profit = mapList[j].profit;
                                var time = mapList[j].time
                                html+= '<li class="profit-li"> <div class="col-5-left"> <p>'+mapList[j].title+'</p> <p><span>'+mapList[j].staffName +' </span><span style="color: #989597;margin-left: 12px">'+time.substring(11)+'</span></p> </div><div class="col-5-right">+'+toDecimal2(profit)+'</div> </li>'
                            }
                            $("#earn_list ul:last-child").append(html);
                            continue;
                        }
                        var a=dataList[i].totalMoney;
                        var html = '<ul class="profit" thisTime="'+mapTime+'"> <li><div class="col-5-left font-color">'+dataList[i].time+'</div> <div class="col-5-right">当日收益<span class="font-color"> '+toDecimal2(a)+'</span>元</div> </li> '
                        if(mapList.length>0){
                            for(var j = 0;j<mapList.length;j++){
                                var profit = mapList[j].profit;
                                var time = mapList[j].time;
                                html+= '<li class="profit-li"> <div class="col-5-left"> <p>'+mapList[j].title+'</p> <p><span>'+mapList[j].staffName +' </span><span style="color: #989597;margin-left: 12px">'+time.substring(11)+'</span></p> </div><div class="col-5-right">+'+toDecimal2(profit)+'</div> </li>'
                            }
                        }
                        html+= ' </ul>'
                        $("#earn_list").html($("#earn_list").html()+html);
                    }
                }
            },
            "json");
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
        while (s.length <= rs + 2) {
            s += '0';
        }
        return s;
    }
}
