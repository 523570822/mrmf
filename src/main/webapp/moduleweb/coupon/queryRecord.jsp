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
                            <%--<label class="col-sm-1 control-label">统计类型</label>--%>
                            <%--<div class="col-sm-2">--%>
                                <%--<select class="form-control" id="type" name="type">--%>
                                    <%--<option value="">请选择</option>--%>
                                    <%--<option value="0">发放统计</option>--%>
                                    <%--<option value="1">使用统计</option>--%>
                                <%--</select>--%>
                            <%--</div>--%>
                            <label class="col-sm-1 control-label">使用状态</label>
                            <div class="col-sm-2">
                                <select class="form-control" id="isUsed" name="isUsed">
                                    <option value="">请选择</option>
                                    <option value="1">已使用</option>
                                    <option value="0">未使用</option>
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
                            <div class="col-sm-1">
                                <button class="btn btn-danger" id="export" name="export" type="button">
                                    <strong>导出</strong>
                                </button>
                            </div>
                            <div class="col-sm-1">
                                <button class="btn btn-primary" id="tongji" name="tongji" type="button">统计图表</button>
                            </div>
                        </div>
                    </form>
                    <div class="jqGrid_wrapper" style="height: 90% !important;">
                        <table id="organTable"></table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
<div id="show" style="width: 600px;height:400px;"></div>
<script type="text/javascript">
    $()
        .ready(
            function() {
                $('#export').click(function() {
                    $("#searchForm").attr("action", _ctxPath + "/coupon/couponRecord/exportConponRecord.do").submit();
                });
                $("#search").click(function() {
                    $("#organTable").reloadGrid({data : $("#searchForm").formobj()});
                    return false;
                });
                var layerId;
                $("#tongji").click(function () {
                    layerId= layer.open({
                        title:"",
                        type: 2,
                        skin: 'layui-layer-rim', //加上边框
                        area: ['990px', '520px'], //宽高
                        //content: $("#show"),
                        content: _ctxPath + "/coupon/couponRecord/toStatistics.do",
                        shadeClose:true,
                    });
                    $("#show").show();
                });
                //选择按钮事件
                $("#organTable")
                    .grid(
                        {
                            url : _ctxPath
                            + "/coupon/couponRecord/query.do",
                            height: '94%',
                            colNames : [ "用户名称","作用范围", "优惠券类型", "金额",
                                "代金券描述", "发放时间", "截止时间","是否使用"],
                            postData : $("#searchForm").formobj(),
                            autowidth: true,
                            shrinkToFit : false,
                            colModel : [
                                {
                                    name : "userName",
                                    index : "userName",
                                    align : "center",
                                    width : 100
                                },
                                {
                                    name : "couponType",
                                    index : "couponType",
                                    width:100
                                },
                                {
                                    name : "moneyType",
                                    index : "moneyType",
                                    align : "center",
                                    width : 90,
                                    formatter : function(cellvalue, options, rowObject) {
                                        var v = "";
                                        if(cellvalue==0){
                                            v = "代金券";
                                        }else {
                                            v = "折扣券";
                                        }
                                        return v;
                                    }
                                },
                                {
                                    name : "moneyOrRatio",
                                    index : "moneyOrRatio",
                                    align : "center",
                                    width : 140
                                },
                                {
                                    name : "description",
                                    index : "description",
                                    align : "center",
                                    width : 80
                                },
                                {
                                    name : "createTime",
                                    index : "createTime",
                                    align : "center",
                                    width : 150
                                },
                                {
                                    name : "endTime",
                                    index : "endTime",
                                    align : "center",
                                    width : 150
                                },
                                {
                                    name : "isUsed",
                                    index : "isUsed",
                                    align : "center",
                                    width : 100,
                                    formatter : function(cellvalue, options, rowObject) {
                                        var v = "";
                                        if(cellvalue==0){
                                            v = "未使用";
                                        }else {
                                            v = "已使用";
                                        }
                                        return v;
                                    }
                                }
                            ]
                        });

            });
</script>
</body>
</html>
