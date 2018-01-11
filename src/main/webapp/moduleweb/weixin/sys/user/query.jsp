<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/moduleweb/resources/common.jsp"%>
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
                        <div class="row">
                            <div class="col-sm-12">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">用户手机号：</label>
                                    <div class="col-sm-3">
                                        <input id="regex:phone" name="regex:phone" type="text"
                                               class="form-control">
                                    </div>
                                    <label class="col-sm-2 control-label"></label>
                                    <div class="col-sm-3">
                                        <select  class="btn" style="font-size: 15px;border:1px solid" name="internalStaff"><option value="">全部</option><option value="0">内部员工</option><option value="1">非内部员工</option></select>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-12">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">注册时间：</label>
                                    <div class="col-sm-3">
                                        <input id="gte:createTime|date" name="gte:createTime|date"
                                               type="text" class="form-control laydate-icon">
                                    </div>
                                    <label class="col-sm-2 control-label">至：</label>
                                    <div class="col-sm-3">
                                        <input id="lte:createTime|date" name="lte:createTime|date"
                                               type="text" class="form-control laydate-icon">
                                    </div>

                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-12">
                                <div class="form-group">
                                    <div class="col-sm-2"></div>
                                    <div class="col-sm-2">
                                        <button id="search" class="btn btn-primary" type="submit">查询</button>
                                    </div>
                                    <div class="col-sm-1">
                                        <button class="btn btn-danger" id="export" name="export"
                                                type="button">
                                            <strong>导出</strong>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                    <div class="jqGrid_wrapper">
                        <table id="userTable"></table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var manageType;
    $()
        .ready(
            function() {
                $('#export')
                    .click(
                        function() {
                            var param = $("#searchForm")
                                .formobj();
                            var o = {};
                            o.phone = param["regex:phone"];
                            o.startTime = param["gte:createTime|date"];
                            o.endTime = param["lte:createTime|date"];
                            var parStr = jQuery.param(o);
                            //console.log(param);
                            window.location.href = _ctxPath
                                + "/weixin/sys/user/exportUserManagement.do?"
                                + parStr;
                            //$("#searchForm").attr("action", _ctxPath + "/weixin/sys/user/exportUser.do").submit();
                        });
                $("#searchForm").submit(function() {
                    $("#userTable").reloadGrid({
                        postData : $("#searchForm").formobj()
                    });
                    return false;
                });

                $("#userTable")
                    .grid(
                        {
                            url : _ctxPath
                            + "/weixin/sys/user/queryUser.do",
                            postData : $("#searchForm")
                                .formobj(),
                            colNames : [ "操作", "是否是内部员工","昵称", "手机号",
                                "注册时间" ],
                            colModel : [
                                {
                                    name : '_id',
                                    index : '_id',
                                    align : "left",
                                    width:100,
                                    align : "center",
                                    formatter : function(
                                        cellvalue,
                                        options,
                                        rowObject) {
                                        var v = "";
                                        if(rowObject.internalStaff==0){
                                            v+="<a href='javascript:void(0);' onclick='disable(\""
                                                + cellvalue
                                                + "\")'>关闭</a>"
                                        }else{
                                            v+="<a href='javascript:void(0);' onclick='enable(\""
                                                + cellvalue
                                                + "\")'>开启</a>"
                                        }
                                        return v;
                                    }
                                },
                                {
                                    name : "internalStaff",
                                    index : "internalStaff",
                                    align : "center",
                                    width : 90,
                                    formatter : function(
                                        cellvalue,
                                        options,
                                        rowObject) {
                                        return cellvalue == "0" ? "是"
                                            : "<font color=\"red\">否</font>";
                                    }
                                },
                                {
                                    name : "nick",
                                    index : "nick",
                                    align : "center"
                                },
                                {
                                    name : "phone",
                                    index : "phone",
                                    align : "center"
                                },
                                {
                                    name : "createTime",
                                    index : "createTime",
                                    align : "center"
                                },
                            ],
                            gridComplete : function() {
                                $(".ui-jqgrid-sortable")
                                    .css("text-align",
                                        "center");
                            }
                        });

            });

    function enable(userId) {//设定为内部员工
        doGet(_ctxPath + "/weixin/sys/user/enable/"+userId +".do");
    }
    function disable(userId) {
        doGet(_ctxPath + "/weixin/sys/user/disable/"+userId +".do");
    }
    function doGet(url) {
        $.get(url, {}, function(data) {
            if (data) {
                if (data.success) {
                    toastr.success("操作成功");
                } else {
                    toastr.error(data.message);
                }
                $("#userTable").trigger("reloadGrid");
            } else {
                toastr.error("操作失败");
            }
        });
    }
    var sCreateTime = {
        elem : '#gte:createTime|date',
        format : 'YYYY-MM-DD',
        max : '2099-06-16 23:59:59', //最大日期
        istime : false,
        istoday : false,
        choose : function(datas) {
            eCreateTime.min = datas; //开始日选好后，重置结束日的最小日期
            eCreateTime.start = datas; //将结束日的初始值设定为开始日
        }
    };
    var eCreateTime = {
        elem : '#lte:createTime|date',
        format : 'YYYY-MM-DD',
        max : '2099-06-16 23:59:59',
        istime : false,
        istoday : false,
        choose : function(datas) {
            sCreateTime.max = datas; //结束日选好后，重置开始日的最大日期
        }
    };
    laydate(sCreateTime);
    laydate(eCreateTime);
</script>
</body>
</html>
