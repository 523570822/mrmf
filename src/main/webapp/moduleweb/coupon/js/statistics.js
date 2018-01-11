require.config({
    paths: {
        echarts: '../resource/js/dist'
    }
}); //require.config配置完成就可以动态加载echarts
require(['echarts', 'echarts/chart/line'], callBack //回调函数
);

var myChart;
var option;
function callBack(ec) {
    myChart = ec.init(document.getElementById('main')); //初始化接口，返回EChart实例  (macarons /infographic/helianthus .. 为主题参数)
    window.onresize = myChart.resize;
    myChart.showLoading();
    option = {
        tooltip: {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type: 'shadow'       // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        legend: { //图例
            show: true,
            data: [],
            //图例内容数组，数组项通常为{string}，每一项代表一个系列的name
            x: 'center',
            y: '40'
        },
        toolbox: { //工具栏
            show: true,
            feature: {
                mark: {
                    show: true
                },
                dataView: {
                    show: true,
                    readOnly: false
                },
                dataZoom: {
                    show: true
                },
                restore: {
                    show: true
                },
                saveAsImage: {
                    show: true
                }
            }
        },
        xAxis: [{
            type: 'category',
            //横轴类型 有'category'类目型，'value'数值型，'time'时间型
            data: [],
            axisLabel: {
                formatter: '{value}'
            }
        }],
        yAxis: [{
            type: 'value'
        }],
        dataZoom: { //数据区域缩放，仅对直角坐标系图表有效
            show: true,
            //是否显示，当show为true时则接管使用指定类目轴的全部系列数据，如不指定则接管全部直角坐标系数据
            realtime: true,
            //缩放变化是否实时显示。
            start: 0,
            //选择起始比例，默认为0%；从第一个数据开始，
            end: 100
            //选择结束比例，默认为100%；到最后一个数据，
        },
        series: []
    };
    var chk_value = [];
    $('input[name="eq_ck"]:checked').each(function() { //遍历每一个复选框，其中选中的执行函数
        chk_value.push($(this).val()); //将选中的值添加到数组chk_value中
    });
    var url = "equipmentHistorysAjax.action?theDay=" + $("#theDay").val() + "&endDay=" + $("#endDay").val() + "&eqs=" + chk_value + "&def=" + $("#def").val() + "&rate=" + $("#rate").val();
    var sArr = [];
    var legendArr = [];
    var timeArr = [];
    $.post(url,"",function (result) {
        for (i = 0; i < result.length; i++) {
            if (null != result[i] && typeof(result[i].code) != 'undefined') {
                legendArr.push(result[i].code);
                var valueArr = [];
            }
            if (null != result[i] && typeof(result[i].list) != 'undefined' && null != result[i].list) {
                timeArr.length = 0;
                for (j = 0; j < result[i].list.length; j++) {
                    if (result[i].list[j] != null) {
                        timeArr.push(format(result[i].list[j].time, "yyyy-MM-dd HH:mm:ss"));
                        valueArr.push(result[i].list[j].value);
                    }
                }
            }
            if (null != result[i] && typeof(result[i].code) != 'undefined' && typeof(result[i].list) != 'undefined') {
                sArr.push(valueArr);
            }
        }
    });
    option.legend.data = legendArr;
    option.xAxis[0].data = timeArr;
    for (i = 0; i < sArr.length; i++) {
        var item = {
            name: legendArr[i],
            type: 'line',
            data: sArr[i],
            smooth: true,
            //平滑曲线显示
            legendHoverLink: true,
            //是否启用图例（legend）hover时的联动响应（高亮显示
            markPoint: { //最大最小值标记
                data: [{
                    type: 'max',
                    name: '最大值'
                },
                    {
                        type: 'min',
                        name: '最小值'
                    }]
            },
            markLine: { //平均线
                data: [{
                    type: 'average',
                    name: '平均值'
                }]
            }
        }
        option.series.push(item);
    };
    clearTimeout(loadingTicket);
    var loadingTicket = setTimeout(function() {
            myChart.hideLoading();
            myChart.setOption(option);
        },
        1000);
}