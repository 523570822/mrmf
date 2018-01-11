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
                            <input id="regex:name" name="regex:name" type="text"
                                   class="form-control" placeholder="会员姓名">
                        </div>
                        <div class="col-sm-2">
                            <input id="regex:phone" name="regex:phone" type="text"
                                   class="form-control" placeholder="会员手机号">
                        </div>
                        <div class="form-group">
                            <div class="col-sm-5">
                                <button id="search" class="btn btn-primary" type="submit">查询</button>
                                <button id="add" class="btn btn-primary" type="button">新增</button>
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
        //主表
        $("#myTable")
            .grid(
                {
                    url : _ctxPath
                    + "/shopVip/query.do",
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
                    colNames : [ "操作","会员名", "会员手机号","创建日期","生否生效"],
                    colModel : [
                        {
                            name : "_id",
                            index : "_id",
                            sortable : false,
                            width : 120,
                            formatter : function(
                                cellvalue,
                                options,
                                rowObject) {
                                var v = "";
                                if (1==1) {
                                    v += "<a href='${ctxPath}/shopVip/toUpsert.do?vipMemberId="
                                        + cellvalue
                                        + "&organId="+rowObject.organId+"'>编辑</a>&nbsp;&nbsp;";
                                }
                                if (rowObject.state==1) {
                                    v += "<a href='javascript:void(0);' onclick='disable(\""
                                        + cellvalue
                                        + "\")'>禁用</a>";
                                }else if(rowObject.state==0){
                                    v += "<a href='javascript:void(0);' onclick='enable(\""
                                        + cellvalue
                                        + "\")'>启用</a>";
                                }
                                v += "&nbsp;&nbsp;<a href='javascript:void(0);' onclick='del(\""
                                    + cellvalue
                                    + "\")'>删除</a>";
                                return v;
                            }
                        },
                        {
                            name : "name",
                            index : "name",
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
                            name : "createTime",
                            index : "createTime",
                            width : 170,
                            align:"left"
                        },
                        {
                            name:"state",
                            index:"state",
                            width:80,
                            align:"center",
                            formatter:function (cellvalue) {
                                var v = "";
                                if(cellvalue==0){
                                    v="否";
                                }else {
                                    v="是";
                                }
                                return v;
                            }
                        }
                    ]
                });
    });
    //点击启用或停用触发的事件
    function enable(vipMemberId) {
        doGet(_ctxPath + "/shopVip/updateState.do?vipMemberId="+vipMemberId+"&state=1");
    }
    function disable(vipMemberId) {
        doGet(_ctxPath + "/shopVip/updateState.do?vipMemberId="+vipMemberId+"&state=0");
    }
    function del(vipMemberId) {
        doGet(_ctxPath + "/shopVip/updateState.do?vipMemberId="+vipMemberId+"&state=3");
    }
    function doGet(url) {
        $.get(url, {}, function(data) {
            if (data) {
                if (data.success) {
                    toastr.success("操作成功");
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
