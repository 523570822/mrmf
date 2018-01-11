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
                        <div class="col-sm-2">
                            <input id="name" name="name" type="text"
                                   class="form-control" placeholder="会员姓名">
                        </div>
                        <div class="col-sm-2">
                            <input id="phone" name="phone" type="text"
                                   class="form-control" placeholder="会员手机号">
                        </div>
                        <div class="form-group">
                            <div class="col-sm-1">
                                <button id="search" class="btn btn-primary" type="submit">查询</button>
                            </div>
                            <div class="col-sm-2">
                                <button id="his" class="btn btn-primary">审核记录</button>
                            </div>
                        </div>
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
        $("#his").click(function () {
            window.location.href = _ctxPath + "/shopVip/toCheckVipGoodsHistory.do";
        })
        //主表
        $("#myTable")
            .grid(
                {
                    url : _ctxPath
                    + "/shopVip/shenhe.do",
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
                    colNames : [ "操作","商品名称","会员名", "会员手机号","订单总价","创建日期"],
                    colModel : [
                        {
                            name : "id",
                            index : "id",
                            sortable : false,
                            width : 90,
                            formatter : function(
                                cellvalue,
                                options,
                                rowObject) {
                                var v = "";
                                    v += "<a href='javascript:void(0);' class='shenhe' data='"+rowObject.price+"' rowId='"+cellvalue+"'>审核</a>";

                                    v += "&nbsp;&nbsp;<a href='javascript:void(0);' onclick='enable(\""
                                        + cellvalue
                                        + "\")'>不通过</a>";
//                                v += "&nbsp;&nbsp;<a href='javascript:void(0);' onclick='del(\""
//                                    + cellvalue
//                                    + "\")'>删除</a>";
                                return v;
                            }
                        },
                        {
                            name : "goodsName",
                            index : "goodsName",
                            width : 120,
                            align:"center"
                        },
                        {
                            name : "memberName",
                            index : "memberName",
                            width : 70,
                            align:"center"
                        },
                        {
                            name : "phone",
                            index : "phone",
                            width : 120,
                            align:"center"
                        },
                        {
                            name : "goodsPrice",
                            index : "goodsPrice",
                            width : 70,
                            align:"center"
                        },
                        {
                            name : "createDate",
                            index : "createDate",
                            width : 170,
                            align:"left"
                        }
                    ]
                });
    });
    //点击审核 未通过 删除
    function enable(vipMemberId) {
        doGet(_ctxPath + "/shopVip/doShenhe.do?orderId="+vipMemberId+"&state=0");
    }
    $("body").on("click", ".shenhe", function() {
        var price = $(this).attr("data");
        var rowId = $(this).attr("rowId");
        doGet(_ctxPath + "/shopVip/doShenhe.do?orderId="+rowId+"&state=1&price="+price);
    });
    function del(vipMemberId) {
        doGet(_ctxPath + "/shopVip/doShenhe.do?orderId="+vipMemberId+"&state=3");
    }
    function doGet(url) {
        var organId = $("#organId").val();
        $.get(url, {"organId":organId}, function(data) {
            if (data) {
                if (data.success) {
                    toastr.success("操作成功");
                    //重新加载表单
                } else {
                    toastr.error(data.message);
                }
                $("#myTable").reloadGrid({
                    postData : $("#searchForm").formobj()
                });
            } else {
                toastr.error("操作失败");
            }
        });
    }
    //新增配置事件
    $("#add").click(function(){
        var organId = $("#organId").val();
        window.location.href="${ctxPath}/shopVip/toUpsert.do?organId="+organId;
    })
</script>
</body>
</html>
