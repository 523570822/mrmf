$(document).ready(function () {

    var onOff = false;
    var beforeIndex = null;
    var afterIndex = null;
    var beforeTime = null;
    var afterTime = null;
    var beforeDate = null;
    var afterDate = null;
    var dateYear = null;
    var dateMonth = null;
    var number = 0;
    var num = 0;
    var flag = 10;   // 0 向前选  1 向后选 2同元素第二次点击
    $("#next").click(function () {
        var fristDate = $(".active-left").length;
        var centerDate = $(".active-center").length;
        var fristTime = $(".active-left").attr("date");
        var lastTime = $(".active-right").attr("date");
        var oneDay = $(".active-circle").length;
        $("#fom input:eq(1)").nextAll().remove();
        var fom = $("#fom");
        if(oneDay==1){//只提交一天
            var oneTime = $(".active-circle").attr("date");
            var times="<input name='timeList[0]' type='hidden' value='"+oneTime+"'/>";
            fom.append(times);
            fom.attr("action",_ctxPath+"/w/staffMy/toRentPay").submit();

        }
        if(fristDate==1){//提交多天
            var ff = 1;
            var times="<input name='timeList[0]' type='hidden' value='"+fristTime+"'/>";

            $(".active-center").each(function () {
                var thisTime = $(this).attr("date");
                times+="<input name='timeList["+ff+"]' type='hidden' value='"+thisTime+"'/>";
                ff+=1;
            });
            times+="<input name='timeList["+ff+"]' type='hidden' value='"+lastTime+"'/>";
            fom.append(times);
            fom.attr("action",_ctxPath+"/w/staffMy/toRentPay").submit();
        }
    });

    $(".calendar-nav .clean").click(function () {

        onOff = false;
        beforeIndex = null;
        afterIndex = null;
        beforeTime = null;
        afterTime = null;
        beforeDate = null;
        afterDate = null;
        dateYear = null;
        dateMonth = null;
        num = 0;
        number = 0;

        $(".calendar ul li").removeClass();

    });


    $(".calendar .day li").click(function () {

        if($(this).attr("check") != undefined){
            layer.open({
                content: '当日不可预约'
                ,skin: 'msg'
                ,time: 2
            })
        }else {
            $("#day-day").css("display","block");
            $("#next").css("background","#f43f15");
            if (onOff == false){//第一次点击
                $(".calendar .day li").removeClass();
                $(this).addClass("active-circle");
                onOff = true;
                beforeIndex = $(this).index();
                beforeTime = $(this).attr('date');
                beforeDate = new Date(beforeTime.replace(/-/g,"/"));
                //console.log(beforeIndex);

                //console.log(beforeTime);
                number = $(this).parent().parent().parent().find("h4").attr("month");

            }else if(onOff == true){
                afterIndex = $(this).index();
                afterTime = $(this).attr('date');
                afterDate = new Date(afterTime.replace(/-/g,"/"));
                //console.log(afterIndex);

                //console.log(afterTime);
                //同月
                if(beforeDate.getFullYear() == afterDate.getFullYear() && beforeDate.getMonth() == afterDate.getMonth()){
                    if(afterIndex < beforeIndex){
                        $(this).addClass("active-circle");
                        $(this).siblings().removeClass("active-circle");
                        beforeIndex = $(this).index();
                        number = $(this).parent().parent().parent().find("h4").attr("month");
                    }else if(afterIndex == beforeIndex){
                        $(this).removeClass("active-circle");
                        onOff = false;
                    }else if(afterIndex > beforeIndex){
                        $(this).addClass("active-right");
                        $(this).parent().parent().find('li').eq(beforeIndex).removeClass("active-circle").addClass("active-left");
                        $(this).parent().parent().find('li').each(function () {
                            //console.log($(this).index());
                            if (Number(beforeIndex) < Number($(this).index()) && Number($(this).index()) < Number(afterIndex)){
                                if($(this).attr('check') == undefined){
                                    $(this).addClass("active-center");
                                }
                            }
                        });


                        onOff = false;
                        beforeIndex = null;
                        afterIndex = null;
                        beforeTime = null;
                        afterTime = null;
                        beforeDate = null;
                        afterDate = null;
                        dateYear = null;
                        dateMonth = null;
                        num = 0;
                        number = 0;
                    }
                }else {
                    //afterDate 之后(第二次)   beforeDate 之前(第一次)
                    //不是同月 判断是前选还是后选
                    if(afterDate < beforeDate){

                        $(".calendar .day li").removeClass("active-circle");
                        $(this).addClass("active-circle");
                        beforeIndex = $(this).index();
                        beforeTime = $(this).attr('date');
                        beforeDate = new Date(beforeTime.replace(/-/g,"/"));
                        number = $(this).parent().parent().parent().find("h4").attr("month");

                    }else {//后选逻辑 判断是否跨年
                        if(afterDate.getFullYear() - beforeDate.getFullYear() == 0){
                            //console.log("同一年");
                            dateMonth = afterDate.getMonth() - beforeDate.getMonth();
                            //console.log(dateMonth);
                           flag = 1;
                            if(afterDate.getMonth() - beforeDate.getMonth() == 1){
                                beforeIndex = $(".calendar .day .active-circle").index();
                                afterIndex = $(this).index();
                                $(".calendar .day .active-circle").addClass("active-left").removeClass("active-circle");
                                $(this).addClass("active-right");
                                $(".calendar .day .active-left").parent().parent().find('.day').find("li").each(function () {
                                    if (Number(beforeIndex) < Number($(this).index())){
                                        if($(this).attr('check') == undefined){
                                            $(this).addClass("active-center");
                                        }
                                    }
                                });

                                $(this).parent().parent().find('.day').find("li").each(function () {
                                    if (Number(afterIndex) > Number($(this).index())){
                                        if($(this).attr('check') == undefined){
                                            $(this).addClass("active-center");
                                        }
                                    }
                                });


                                onOff = false;
                                beforeIndex = null;
                                afterIndex = null;
                                beforeTime = null;
                                afterTime = null;
                                beforeDate = null;
                                afterDate = null;
                                dateYear = null;
                                dateMonth = null;
                                num = 0;
                                number = 0;

                            }else if(afterDate.getMonth() - beforeDate.getMonth() > 1){

                                dateMonth = afterDate.getMonth() - beforeDate.getMonth();

                                beforeIndex = $(".calendar .day .active-circle").index();
                                afterIndex = $(this).index();

                                $(".calendar .day .active-circle").addClass("active-left").removeClass("active-circle");
                                $(this).addClass("active-right");
                                $(".calendar .day .active-left").parent().parent().find('.day').find("li").each(function () {
                                    if (Number(beforeIndex) < Number($(this).index())){
                                        if($(this).attr('check') == undefined){
                                            $(this).addClass("active-center");
                                        }
                                    }
                                });

                                $(this).parent().parent().find('.day').find("li").each(function () {
                                    if (Number(afterIndex) > Number($(this).index())){
                                        if($(this).attr('check') == undefined){
                                            $(this).addClass("active-center");
                                        }
                                    }
                                });
                                //console.log('跨' + dateMonth + '个月');
                                for(var i = 1; i < dateMonth; i ++){
                                    num = number;
                                    //console.log('第一个月是' + num + '月');
                                    num = Number(num) + i;
                                    //console.log('需要改变的是' + num + '月');
                                    $(".calendar").each(function () {
                                        if($(this).find("h4").attr("month") == num){

                                            $(this).find('.day').find("li").each(function () {
                                                if($(this).attr('check') == undefined){
                                                    $(this).addClass('active-center');
                                                }
                                            });

                                        }
                                    });
                                }
                                onOff = false;
                                beforeIndex = null;
                                afterIndex = null;
                                beforeTime = null;
                                afterTime = null;
                                beforeDate = null;
                                afterDate = null;
                                dateYear = null;
                                dateMonth = null;
                                num = 0;
                                number = 0;
                            }

                        }else {//跨年计算天数
                            //console.log("不是同一年");
                            dateMonth = 12 - beforeDate.getMonth() + afterDate.getMonth();
                            //console.log(dateMonth);


                            beforeIndex = $(".calendar .day .active-circle").index();
                            afterIndex = $(this).index();

                            $(".calendar .day .active-circle").addClass("active-left").removeClass("active-circle");
                            $(this).addClass("active-right");
                            $(".calendar .day .active-left").parent().parent().find('.day').find("li").each(function () {
                                if (Number(beforeIndex) < Number($(this).index())){
                                    if($(this).attr('check') == undefined){
                                        $(this).addClass("active-center");
                                    }
                                }
                            });

                            $(this).parent().parent().find('.day').find("li").each(function () {
                                if (Number(afterIndex) > Number($(this).index())){
                                    if($(this).attr('check') == undefined){
                                        $(this).addClass("active-center");
                                    }
                                }
                            });

                            //console.log('跨' + dateMonth + '个月');
                            for(var i = 1; i < dateMonth; i ++){
                                num = number;
                                //console.log('第一个月是' + num + '月');
                                num = Number(num) + i;
                                if(num>12){
                                    num = num - 12;
                                }
                                //console.log('需要改变的是' + num + '月');
                                $(".calendar").each(function () {
                                    if($(this).find("h4").attr("month") == num){

                                        $(this).find('.day').find("li").each(function () {
                                            if($(this).attr('check') == undefined){
                                                $(this).addClass('active-center');
                                            }
                                        });

                                    }
                                });
                            }
                            onOff = false;
                            beforeIndex = null;
                            afterIndex = null;
                            beforeTime = null;
                            afterTime = null;
                            beforeDate = null;
                            afterDate = null;
                            dateYear = null;
                            dateMonth = null;
                            num = 0;
                            number = 0;

                        }

                    }
                }
            }
        }

        //点击事件最后  判断选择单天还是多天  还是没选
        var oneDateNum = $("#day-div .active-circle").length;
        var fristDateNum = $("#day-div .active-left").length;
        var lastDateNum = $("#day-div .active-right").length;
        var centerDateNum = $("#day-div .active-center").length;

        //得到单选时间  多选开始时间  结束时间
        var oneDate = $(".active-circle").attr("date");
        var fristDate = $(".active-left").attr("date");
        var lastDate = $(".active-right").attr("date");
        if(oneDateNum==1){
            $("#money1").html(getMonthDay(oneDate));
            $("#money2").html(getMonthDay(oneDate));
            $("#weekDay1").html(getWeekday(oneDate));
            $("#weekDay2").html(getWeekday(oneDate));
            $("#days").html(oneDateNum);
        }
        if(fristDateNum==1){
            $("#money1").html(getMonthDay(fristDate));
            $("#money2").html(getMonthDay(lastDate));
            $("#weekDay1").html(getWeekday(fristDate));
            $("#weekDay2").html(getWeekday(lastDate));
            $("#days").html(centerDateNum+2);
        }
        if(oneDateNum==0&&fristDateNum==0){
            $("#money1").html("");
            $("#money2").html("");
            $("#days").html(0);
            $("#next").css("background","#bbbbbb");
            $("#day-day").css("display","none");
        }
    });
});
function getWeekday(sDate){
    var dt = new Date(sDate.replace(/-/g, '/'));
    var a = ['星期日', '星期一','星期二','星期三','星期四','星期五','星期六'];
    return a[dt.getDay()];
}
function getMonthDay(time) {
    var date = new Date(time);
    var year = date.getFullYear();
    var month = date.getMonth()+1;
    var day = date.getDate();
    var hour = date.getHours();
    var minute = date.getMinutes();
    var second = date.getSeconds();
    return month+'月'+day+'日 ';
}
