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

                        <div class="col-sm-1">
                            <select name="business" class="btn" style="font-size: 15px;border:1px solid">
                                <option value="">请选择</option>
                                <option value="注册">注册</option>
                                <option value="消费">消费</option>
                                <option value="关注">关注</option>
                                <option value="分享">分享</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-5">
                                <button id="search" class="btn btn-primary" type="submit">查询</button>
                                <button id="add" class="btn btn-primary" type="button">新增发放配置</button>
                            </div>
                        </div>
                    </form>
                    <div class="jqGrid_wrapper">
                        <table id="myTable"></table>
                    </div>
                    <div class="jqGrid_wrapper" style="margin-top: 10px;" id="coupon">
                        <input type="hidden" value="" id="couponGrandId"/>
                        <button id="addCoupon" class="btn btn-primary" type="button" disabled="true">新增优惠券</button>
                        <table id="myTable2"></table>
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
        queryStaffRank();
        //点击查询触发事件
        $("#searchForm").submit(function() {
            var o = $("#searchForm").formobj();
            searchCriteria = {};
            for ( var key in o) {
                searchCriteria[key] = o[key];
            }
            $("#myTable").jqGrid('clearGridData');
            $("#myTable").reloadGrid({
                postData : $("#searchForm").formobj()
            });
            //$("thead tr").style("")
            $("#myTable2").jqGrid('clearGridData');
            queryStaffRank();
            return false;
        });
        //主表
        $("#myTable")
            .grid(
                {
                    //url : _ctxPath
                    //+ "/coupon/colligateCoupon/toQuery.do",
                    pager:null,
                    //rownumbers:true,
                    autowidth: true,
                    shrinkToFit: true,
                    height:180,
                    datatype : "local",
                    postData : $("#searchForm").formobj(),
                    onSelectRow : function(id) {
                        var kd = $("#" + id, $("#myTable")).data("rawData");
                        //var rowData = $('#gridTable').jqGrid('getRowData',id);
                        loadChanpin(kd._id);

                    },
                    shrinkToFit : false,
                    colNames : [ "操作","业务类型", "代金券类型","店面名称",
                        "有效期", "消费起始值",
                        "消费终止值", "单人领取次数", /*"总领取次数",*/
                        "是否有效"],
                    colModel : [
                        {
                            name : "_id",
                            index : "_id",
                            sortable : false,
                            width : 100,
                            formatter : function(
                                cellvalue,
                                options,
                                rowObject) {
                                var v = "";
                                if (1==1) {
                                    v += "<a href='${ctxPath}/coupon/OperationController/ToDoGrant.do?GrantId="
                                        + cellvalue
                                        + "'>编辑</a>&nbsp;&nbsp;";
                                }
                                if (rowObject.state=="是") {
                                    v += "<a href='javascript:void(0);' onclick='disable(\""
                                        + cellvalue
                                        + "\")'>禁用</a>";
                                }else if(rowObject.state=="否"){
                                    v += "<a href='javascript:void(0);' onclick='enable(\""
                                        + cellvalue
                                        + "\")'>启用</a>";
                                }

                                return v;
                            }
                        },
                        {
                            name : "business",
                            index : "business",
                            width : 70,
                            align:"center"
                        },
                        {
                            name : "type",
                            index : "type",
                            width : 80,
                            align:"center"
                        },
                        {
                            name : "record",
                            index : "record",
                            width : 170,
                            align:"left"
                        },
                        {
                            name : "endTime",
                            index : "endTime",
                            width : 80,
                            align:"center",
                            formatter:'date',
                            formatoptions: {newformat:'Y-m-d'}
                        },
                        {
                            name : "minCondition",
                            index : "minCondition",
                            width : 100,
                            align: "right"
                        },
                        {
                            name : "maxCondition",
                            index : "maxCondition",
                            width : 100,
                            align: "right"
                        },
                        {
                            name : "singleReceive",
                            index : "singleReceive",
                            align: "right",
                            width : 120
                        },
                        /*{
                            name : "totalReceive",
                            index : "totalReceive",
                            align: "right",
                            width : 100
                        },*/
                        {
                            name : "state",
                            index : "state",
                            width : 100,
                            align:"center"
                        },
                    ]
                });
        //从表grid
        $("#myTable2").grid({
            pager : null,
            autowidth: true,
            shrinkToFit: true,
            height:200,
            datatype : "local",
            colNames : [ "操作", "作用范围","店面名称","店铺类型","服务大类","有效期", "发放类型","起始值","终止值","最低消费金额","是否允许转赠","是否有效" ],
            colModel : [
                {
                    name : "_id",
                    index : "_id",
                    sortable : false,
                    width : 100,
                    formatter : function(
                        cellvalue,
                        options,
                        rowObject) {
                        var v = "";
                        grantId = rowObject.grantId;
                        //优惠券刷新有bug
                        if (1==1) {
                            v += "<a href='${ctxPath}/coupon/OperationController/DoUpdate.do?couponId="
                                + cellvalue
                                + "'>编辑</a>&nbsp;&nbsp;";
                        }
                        if (rowObject.state=="是") {
                            v += "<a href='javascript:void(0);' onclick='disable2(\""
                                + cellvalue
                                + "\")'>禁用</a>";
                        }else if(rowObject.state=="否"){
                            v += "<a href='javascript:void(0);' onclick='enable2(\""
                                + cellvalue
                                + "\")'>启用</a>";
                        }

                        return v;
                    }
                },
                {
                    name : "couponType",
                    index : "couponType",
                    width : 80,
                    align:"center"
                },
                {
                    name : "shopName",
                    index : "shopName",
                    width : 170,
                    align:"left"
                },{
                    name : "typeName",
                    index : "typeName",
                    align: "center",
                    width : 80
                }, {
                    name : "bigSortName",
                    index : "bigSortName",
                    align: "center",
                    width : 80
                },{
                    name : "endTime",
                    index : "endTime",
                    width : 80,
                    align:"center",
                    formatter:'date',
                    formatoptions: {newformat:'Y-m-d'}
                },{
                    name : "favourableType",
                    index : "favourableType",
                    align: "center",
                    width : 80,
                    formatter : function(
                        cellvalue,
                        options,
                        rowObject) {
                        var v = "";
                        if (cellvalue==0) {
                            v += "金额";
                        }else if(cellvalue==1){
                            v += "比例";
                        }
                        return v;
                    }
                },
                {
                    name : "minValue",
                    index : "minValue",
                    align: "right",
                    width : 65
                }, {
                    name : "maxValue",
                    index : "maxValue",
                    align: "right",
                    width : 65
                }, {
                    name : "minConsume",
                    index : "minConsume",
                    align: "right",
                    width : 100
                }, {
                    name : "donate",
                    index : "donate",
                    align: "center",
                    width : 100,
                    formatter : function(
                        cellvalue,
                        options,
                        rowObject) {
                        var v = "";
                        if (cellvalue) {
                            v += "是";
                        }else{
                            v += "否";
                        }
                        return v;
                    }
                },{
                    name : "state",
                    index : "state",
                    width : 80,
                    align:"center"
                } ]
        });
    });
    //主从表,点击出从表事件
    function loadChanpin(grantId) {
        $("#addCoupon").attr('disabled',false);
        $("#couponGrandId").val(grantId)
        $.get("${ctxPath}/coupon/colligateCoupon/queryCoupon.do", {
            grantId : grantId
        }, function(data) {
            if (data) {
                $("#myTable2").jqGrid('clearGridData');
                for (var i = 0; i <= data.length; i++) {
                    $("#myTable2").jqGrid('addRowData', i + 1, data[i]);
                }
            } else {
                toastr.error("操作失败");
            }
        });
    }
    //点击启用或停用触发的事件
    function enable(couponGrantId) {
        doGet(_ctxPath + "/coupon/colligateCoupon/enable/" + couponGrantId + ".do");
    }
    function disable(couponGrantId) {
        doGet(_ctxPath + "/coupon/colligateCoupon/disable/" + couponGrantId+ ".do");
    }
    function doGet(url) {
        $.get(url, {}, function(data) {
            if (data) {
                if (data.success) {
                    toastr.success("操作成功");
                } else {
                    toastr.error(data.message);
                }
                queryStaffRank();
                //$("#search").submit;
                //$("#myTable").trigger("reloadGrid");
            } else {
                toastr.error("操作失败");
            }
        });
    }
    function enable2(couponId) {
        doGet2(_ctxPath + "/coupon/colligateCoupon/enable2/" + couponId + ".do");
    }
    function disable2(couponId) {
        doGet2(_ctxPath + "/coupon/colligateCoupon/disable2/" + couponId+ ".do");
    }
    function doGet2(url) {
        $.get(url, {}, function(data) {
            if (data) {
                if (data.success) {
                    toastr.success("操作成功");
                } else {
                    toastr.error(data.message);
                }
                loadChanpin(grantId);
                //$("#myTable2").trigger("reloadGrid");
            } else {
                toastr.error("操作失败");
            }
        });
    }
    $("#addCoupon").click(function(){
        var couponGrandId = $("#couponGrandId").val();
        window.location.href="${ctxPath}/coupon/OperationController/toAdd.do?couponGrandId="+couponGrandId;
    });
    //新增配置事件
    $("#add").click(function(){
        window.location.href="${ctxPath}/coupon/OperationController/ToDoGrant.do";
    })
    function queryStaffRank(){
        var obj = $("#searchForm").formobj();
        $.post("${ctxPath}/coupon/colligateCoupon/toQuery.do", obj, function(data,
                                                                             status) {
            $("#myTable").jqGrid('clearGridData');//清除当前加载数据
            for (var i = 0; i <= data.length; i++) {
                $("#myTable").jqGrid('addRowData', i + 1, data[i]);//循环添加
            }

        });
    }
</script>
</body>
</html>
