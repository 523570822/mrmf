<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/moduleweb/resources/common.jsp"%>
<c:set var="parentId"
       value="${param.parentId == null?sessionScope.organId:param.parentId}" />
<html>
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
                             工位管理
                        </h5>
                    </div>
                </c:if>
                <div class="ibox-content" style="height: 100% !important;">
                    <form id="searchForm" method="get" class="form-horizontal">
                        <input type="hidden" id="parentId" name="parentId"
                               value="${parentId}">
                        <div class="form-group">
                            <%--<label class="col-sm-1 control-label">城市</label>
                            <div class="col-sm-2">
                                <select class="form-control" id="city" name="city">
                                    <option value="">请选择</option>
                                    <c:forEach items="${ffcitys}" var="city">
                                        <option value="${city._id }">${city.name }</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <label class="col-sm-1 control-label">区域</label>
                            <div class="col-sm-2">
                                <select class="form-control" id="district" name="district">
                                    <option value="">请选择</option>
                                </select>
                            </div>
                            <label class="col-sm-1 control-label">商圈</label>
                            <div class="col-sm-2">
                                <select class="form-control" id="region" name="region">
                                    <option value="">请选择</option>
                                </select>
                            </div>--%>
                            <label class="col-sm-1 control-label">名称</label>
                            <div class="col-sm-2">
                                <input id="regex:name" name="regex:name" type="text"
                                       class="form-control">
                            </div>
                            <label class="col-sm-1 control-label">手机号</label>
                            <div class="col-sm-2">
                                <input id="regex:phone" name="regex:phone" type="text"
                                       class="form-control">
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
                                <button id="search" class="btn btn-primary" type="submit">查询</button>
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
<script type="text/javascript">
    $()
        .ready(
            function() {
                $("#searchForm").submit(function() {
                    $("#organTable").reloadGrid({data : $("#searchForm").formobj()});
                    return false;
                });
                $("#organTable")
                    .grid(
                        {
                            url : _ctxPath
                            + "/organ/query.do",
                            height: '94%',
                            colNames : [ "工位操作","名称", "工位状态", "简称",
                                "联系人", "手机号", "负责人","加入时间"],
                            postData : $("#searchForm").formobj(),
                            autowidth: true,
                            shrinkToFit : false,
                            colModel : [
                                {
                                    name : "_id",
                                    index : "_id",
                                    align : "center",
                                    width : 100,
                                    formatter : function(
                                        cellvalue,
                                        options,
                                        rowObject) {
                                        var v ="";
                                        <c:if test="${!sessionScope.isOrganAdmin}">
                                           if(rowObject.organPositionState=="0"){
                                               v += "<a href='javascript:void(0);' onclick='disable(\""
                                                   + cellvalue
                                                   + "\")'>关闭  </a>";
                                               v += "<a href='javascript:void(0);' onclick='addPosition(\""
                                                   + cellvalue
                                                   + "\")'>修改</a>";
                                           }else{
                                               v += "<a href='javascript:void(0);' onclick='enable(\""
                                                   + cellvalue
                                                   + "\")'>开启</a>";
                                           }
                                        </c:if>
                                        return v;
                                    }
                                },
                                {
                                    name : "name",
                                    index : "name",
                                    width:180
                                },
                                {
                                    name : "organPositionState",
                                    index : "organPositionState",
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
                                    name : "abname",
                                    index : "abname",
                                    align : "center",
                                    width : 140
                                },
                                {
                                    name : "contactMan",
                                    index : "contactMan",
                                    align : "center",
                                    width : 80
                                },
                                {
                                    name : "phone",
                                    index : "phone",
                                    align : "center",
                                    width : 100
                                },
                                {
                                    name : "master",
                                    index : "master",
                                    align : "center",
                                    width : 80
                                },
                                {
                                    name : "createTime",
                                    index : "createTime",
                                    align : "center",
                                    width : 100,
                                    formatter:function(cellvalue,options,rowObject) {
                                        if (cellvalue) {
                                            return cellvalue
                                                .substring(
                                                    0,
                                                    10);
                                        } else {
                                            return "";
                                        }
                                    }
                                }
                                 ]
                        });

            });
    function addPosition(organId) {//跳转配置工位页面
        window.location.href="${ctxPath}/OrganPosition/toAdd.do?organId="+organId;
    }
    function enable(organId) {
        doGet(_ctxPath + "/OrganPosition/enable/"+organId +".do");
    }
    function disable(organId) {
        doGet(_ctxPath + "/OrganPosition/disable/"+organId +".do");
    }
    function doGet(url) {
        $.get(url, {}, function(data) {
            if (data) {
                if (data.success) {
                    toastr.success("操作成功");
                } else {
                    toastr.error(data.message);
                }
                $("#organTable").trigger("reloadGrid");
            } else {
                toastr.error("操作失败");
            }
        });
    }
</script>
</body>
</html>
