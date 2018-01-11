<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/moduleweb/resources/common.jsp"%>
<c:set var="parentId"
       value="${param.parentId == null?sessionScope.organId:param.parentId}" />
<html>
<script src="${ctxPath}/moduleweb/resources/js/echarts.min.js"></script>
<head>
    <title></title>
    <style>
        .ui-jqgrid{height: 96% !important;}
        .ui-jqgrid-view{height: 92% !important;}
        .table>tbody>tr>td, .table>tbody>tr>th, .table>tfoot>tr>td, .table>tfoot>tr>th, .table>thead>tr>td, .table>thead>tr>th{border-top: 0 !important;}
        .ui-jqgrid .ui-jqgrid-pager, .ui-jqgrid .ui-jqgrid-toppager{padding: 10px 0 10px !important;}
        .ibox-content{padding-bottom: 0 !important;}
        .ui-jqgrid .ui-jqgrid-view{overflow-y: hidden;}
    </style>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight" style="height: 99% !important;">
    <div class="row" style="height: 100% !important;">
        <div class="col-sm-12" style="height: 100% !important;">
            <div class="ibox " style="height: 100% !important;">
                <c:if
                        test="${param.parentId != \"0\" && !sessionScope.isOrganAdmin}">
                    <div class="ibox-title">
                        <h5>
                            优惠券统计
                        </h5>
                    </div>
                </c:if>
                <div class="ibox-content" style="height: 100% !important;">
                    <form id="searchForm" method="get" class="form-horizontal">
                        <div class="form-group">
                            <label class="col-sm-1 control-label">周期</label>
                            <div class="col-sm-2">
                                <select class="form-control" id="period" name="period">
                                    <option value="0">日</option>
                                    <option value="1">月</option>
                                    <option value="2">年</option>
                                </select>
                            </div>
                            <label class="col-sm-2 control-label">统计类型</label>
                            <div class="col-sm-2">
                                <select class="form-control" id="isUsed" name="isUsed">
                                    <option value="0">发放统计</option>
                                    <option value="1">使用统计</option>
                                </select>
                            </div>
                            <div class="col-sm-2">
                                <input id="gte:createTime|date" name="gte:createTime|date"
                                       class="laydate-icon form-control layer-date"
                                       placeholder="起始日期" data-mask="9999-99-99"
                                       laydate="{format : 'YYYY-MM-DD',min : '1938-06-16 23:59:59'}">
                            </div>
                            <div class="col-sm-2">
                                <input id="lte:createTime|date+1" name="lte:createTime|date+1"
                                       class="laydate-icon form-control layer-date"
                                       placeholder="结束日期" data-mask="9999-99-99"
                                       laydate="{format : 'YYYY-MM-DD',min : '1938-06-16 23:59:59'}">
                            </div>
                            <div class="col-sm-1">
                                <button id="search" class="btn btn-primary" type="button">查询</button>
                            </div>
                        </div>
                    </form>
                    <!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
                    <div id="show" style="width: 600px;height:400px;"></div>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $()
        .ready(
            function() {
                var option="";
                // 基于准备好的dom，初始化echarts实例
                tj();
             $("#search").click(function () {
                tj();
             });
             $("#period").change(function () {
                 tj();
             })
                function tj() {
                    var obj = $("#searchForm").formobj();
                    $.post(_ctxPath + "/coupon/couponRecord/statistics.do",obj,function (e) {
                        add(e);
                    })
                }
             function add(e) {
                 var legendArr = [];
                 var xAxisArr = [];
                 var seriesDate = [];
                 for (var i=0;i<e.legenDate.length;i++){
                     legendArr.push(e.legenDate[i]);
                     var item = {name:e.seriesDate[i].name,type:'bar',barWidth : 30,data:e.seriesDate[i].data}
                     seriesDate.push(item);
                 }
                 for (var i=0;i<e.xAxisData.length;i++){
                     xAxisArr.push(e.xAxisData[i])
                 }
                 show(legendArr,xAxisArr,seriesDate);
             }
                //选择按钮事件  seriesDate
                function show(legendDate,xAxisData,seriesDate) {
                    var myChart = echarts.init(document.getElementById('show'));
                    myChart.clear();//清空上次数据
                    // 使用刚指定的配置项和数据显示图表。
                    option = {
                        tooltip : {
                            trigger: 'axis',
                            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                            }
                        },
                        legend: {
                            data:legendDate
                        },
                        grid: {
                            left: '3%',
                            right: '4%',
                            bottom: '3%',
                            containLabel: true
                        },
                        xAxis : [
                            {
                                barCategoryGap : 20,
                                type : 'category',
//                                axisLabel:{
//                                    rotate:45 //刻度旋转45度角
//                                },
                                data : xAxisData
                            }
                        ],
                        yAxis : [//y轴数据
                            {
                                type : 'value'
                            }
                        ],
                        series :seriesDate
                    };
                    myChart.setOption(option);
                }
            });
</script>
</body>
</html>
