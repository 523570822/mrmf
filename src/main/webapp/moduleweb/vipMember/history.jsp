<%--
  Created by IntelliJ IDEA.
  User: 蔺哲
  Date: 2017/6/27
  Time: 13:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/moduleweb/resources/common.jsp"%>
<c:set var="organId"
       value="${param.organId == null?sessionScope.organId:param.organId}" />
<html>
<head>
    <title></title>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <form id="searchForm" method="get" class="form-horizontal">
                        <input type="hidden" id="organId" name="organId" value="${organId}">
                        <input type="hidden" id="sortField" name="sortField"
                               value="createTime">
                        <input type="hidden" id="sortOrder" name="sortOrder"
                               value="desc">
                        <%--<div class="col-sm-2">--%>
                            <%--<input id="name" name="name" type="text"--%>
                                   <%--class="form-control" placeholder="会员姓名">--%>
                        <%--</div>--%>
                        <%--<div class="col-sm-2">--%>
                            <%--<input id="phone" name="phone" type="text"--%>
                                   <%--class="form-control" placeholder="会员手机号">--%>
                        <%--</div>--%>
                        <%--<div class="form-group">--%>
                            <%--<div class="col-sm-5">--%>
                                <%--<button id="search" class="btn btn-primary" type="submit">查询</button>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    </form>
                    <div class="jqGrid_wrapper">
                        <table id="myTable"></table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var manageType;
    var grantId;
    $(function() {
        //点击查询触发事件
        $("#searchForm").submit(function() {
            $("#myTable").reloadGrid({
                postData : $("#searchForm").formobj()
            });
            return false;
        });
        //主表
        $("#myTable")
            .grid(
                {
                    url : _ctxPath
                    + "/shopVip/queryHistory.do",
                    postData : $("#searchForm").formobj(),
                    //pager:null,
                    //rownumbers:true,
                    autowidth: true,
                    shrinkToFit: false,
                    //datatype : "local",

                    onSelectRow : function(id) {
                        var kd = $("#" + id, $("#myTable")).data("rawData");
                        //var rowData = $('#gridTable').jqGrid('getRowData',id);
                        loadChanpin(kd._id);

                    },
                    colNames : [ "会员名","会员手机号","商品名称","商品价钱", "审核状态","描述","审核日期"],
                    colModel : [
                        {
                            name : "userName",
                            index : "userName",
                            sortable : false,
                            width : 90
                        },
                        {
                            name : "phone",
                            index : "sn",
                            width : 130,
                            align:"center"
                        },
                        {
                            name : "goodsName",
                            index : "goodsName",
                            width : 110,
                            align:"center"
                        },
                        {
                            name : "price",
                            index : "price",
                            width : 70,
                            align:"center"
                        },
                        {
                            name : "state",
                            index : "state",
                            width : 80,
                            align:"center"
                        },
                        {
                            name : "msg",
                            index : "msg",
                            width : 150,
                            align:"center"
                        },
                        {
                            name : "createTime",
                            index : "createTime",
                            width : 170,
                            align:"left"
                        }
                    ]
                });
    });
</script>
</body>
</html>
