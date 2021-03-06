<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/moduleweb/resources/common.jsp"%>
<%--<%@ include file="/moduleweb/user/query/search.jsp"%>--%>
<%--<c:set var="organId"--%>
<%--value="${param.organId == null?sessionScope.organId:param.organId}" />--%>
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
						<input type="hidden" id="sortField" name="sortField"
							   value="createTime"> <input type="hidden" id="sortOrder"
														  name="sortOrder" value="DESC">
						<input type="hidden" id="rxm" name="rxm" value="1111">
						<input type="hidden"
							   id="in:type|array-integer" name="in:type|array-integer"
							   value="0">
						<label class="col-sm-2 control-label">店铺名称：</label>
						<div class="col-sm-3">
							<input id="organName" name="organName" type="text"
								   class="form-control">
						</div>
						<div class="col-sm-2">
							<input id="gte:createTime|date" name="gte:createTime|date"
								   class="laydate-icon form-control layer-date" placeholder="起始日期"
								   data-mask="9999-99-99"
								   laydate="{format : 'YYYY-MM-DD',min : '1938-06-16 23:59:59'}">
						</div>
						<div class="col-sm-2">
							<input id="lte:createTime|date+1" name="lte:createTime|date+1"
								   class="laydate-icon form-control layer-date" placeholder="结束日期"
								   data-mask="9999-99-99"
								   laydate="{format : 'YYYY-MM-DD',min : '1938-06-16 23:59:59'}">
						</div>
						<%--<c:if test="${!empty organList }">
                            <label class="col-sm-1 control-label" id="company_label">子公司</label>
                            <div class="col-sm-2" id="company_div">
                                <select class="form-control" id="organId" name="organId">
                                    <option value="">请选择</option>
                                    <c:forEach items="${organList}" var="organ">
                                        <option value="${organ._id}">${organ.name}</option>
                                    </c:forEach>
                                </select><span class="help-block m-b-none"></span>
                            </div>
                        </c:if>--%>
						<div class="form-group">
							<div class="col-sm-3">
								<button id="search" class="btn btn-primary" type="submit">查询</button>
								<%--<button id="multipleSearch" class="btn btn-primary"
                                    type="button">综合查询</button>
                                <button id="download" class="btn btn-danger btn-outline"
                                    type="button">导出</button>--%>
							</div>
						</div>
					</form>
					<div class="jqGrid_wrapper">
						<table id="myTable"></table>
					</div>
					<div class="jqGrid_wrapper" style="margin-top: 10px">
						<table id="myTable2"></table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
    //		var manageType;
    $(document).ready(
        function () {
//            alert("hjsdfffffffffffffffffffffffffffffffffff")
            $("#searchForm").submit(function() {

                var o = $("#searchForm").formobj();
//                searchCriteria = {};
//                for ( var key in o) {
//                    searchCriteria[key] = o[key];
//                }
                $("#myTable").jqGrid('clearGridData');
                $("#myTable").reloadGrid({
                    postData : $("#searchForm").formobj()
                });
                $("#myTable2").jqGrid('clearGridData');
                return false;
            });

            $("#myTable")
                .grid(
                    {
                        url : _ctxPath
                        + "/weixin/sys/user/toMemberByFpi.do",
                        postData : $("#searchForm")
                            .formobj(),
                        postData : $("#searchForm")
                            .formobj(),
                        onSelectRow : function(id) {
                            var kd = $("#" + id,
                                $("#myTable"))
                                .data("rawData");
                            loadChanpin(kd._id);
                        },
                        shrinkToFit : false,
                        colNames : [ "店铺名称","流水号", "卡表面号",
                            "免单", "挂账", "应交款额",
                            "会员姓名", "电话", "备注",
                            "员工1", "员工2", "会员类型",
                            "欠费", "员工3", "性别",
                            "生日", "光临日期", "员工1提成",
                            "员工2提成", "员工3提成",
                            "是否删除", "使用产品价钱",
                            "会员号", "余额" ],
                        colModel : [

                            {
                                name : "organName",
                                index : "organName",
                                width : 200
                            },
                            {
                                name : "xiaopiao",
                                index : "xiaopiao",
                                width : 80
                            },
                            {
                                name : "cardno",
                                index : "cardno",
                                width : 80
                            },
                            {
                                name : "miandan",
                                index : "miandan",
                                width : 60,
                                formatter : function(
                                    cellvalue,
                                    options,
                                    rowObject) {
                                    if (cellvalue)
                                        return "是";
                                    else
                                        return "否";
                                }
                            },
                            {
                                name : "guazhang_flag",
                                index : "guazhang_flag",
                                width : 60,
                                formatter : function(
                                    cellvalue,
                                    options,
                                    rowObject) {
                                    if (cellvalue)
                                        return "是";
                                    else
                                        return "否";
                                }
                            },
                            {
                                name : "money1",
                                index : "money1",
                                width : 80
                            },
                            {
                                name : "name",
                                index : "name",
                                width : 100
                            },
                            {
                                name : "phone",
                                index : "phone",
                                width : 100
                            },
                            {
                                name : "love",
                                index : "love"
                            },
                            {
                                name : "staffId1",
                                sortable : false,
                                index : "staffId1",
                                formatter : function(
                                    cellvalue,options,rowObject) {
									return rowObject.staff1Name;

                                },
                                width : 80
                            },
                            {
                                name : "staffId2",
                                sortable : false,
                                index : "staffId2",
                                formatter : function(
                                    cellvalue,options,rowObject) {
									return rowObject.staff2Name;

                                },
                                width : 80
                            },
                            {
                                name : "usersortName",
                                index : "usersortName"
                            },
                            {
                                name : "money_qian",
                                index : "money_qian",
                                width : 80
                            },
                            {
                                name : "staffId3",
                                sortable : false,
                                index : "staffId3",
                                formatter : function(
                                    cellvalue,options,rowObject) {
                                        return rowObject.staff3Name;
                                },
                                width : 80
                            },
                            {
                                name : "sex",
                                index : "sex",
                                width : 60
                            },
                            {
                                name : "birthday",
                                index : "birthday",
                                sortable : false,
                                width : 100,
                                formatter : function(
                                    cellvalue,
                                    options,
                                    rowObject) {
                                    if (cellvalue) {
                                        return cellvalue
                                            .substring(
                                                0,
                                                10);
                                    } else {
                                        return "";
                                    }
                                }
                            },
                            {
                                name : "createTime",
                                index : "createTime"
                            },
                            {
                                name : "somemoney1",
                                index : "somemoney1",
                                width : 80
                            },
                            {
                                name : "somemoney2",
                                index : "somemoney2",
                                width : 80
                            },
                            {
                                name : "somemoney3",
                                index : "somemoney3",
                                width : 80
                            },
                            {
                                name : "delete_flag",
                                index : "delete_flag",
                                width : 70,
                                formatter : function(
                                    cellvalue,
                                    options,
                                    rowObject) {
                                    if (cellvalue)
                                        return "是";
                                    else
                                        return "否";
                                }
                            },
                            {
                                name : "money_wupin",
                                index : "money_wupin",
                                width : 100
                            }, {
                                name : "id_2",
                                index : "id_2"
                            }, {
                                name : "nowMoney4",
                                index : "nowMoney4"
                            } ]
                    });
            $("#myTable2").grid({
                pager : null,
                shrinkToFit : false,
                datatype : "local",
                colNames : [ "光临日期", "产品名称", "用量", "价格" ],
                colModel : [ {
                    name : "createTime",
                    index : "createTime"
                }, {
                    name : "wupinName",
                    index : "wupinName"
                }, {
                    name : "yongliang",
                    index : "yongliang"
                }, {
                    name : "money1",
                    index : "money1"
                } ]
            });
        }
    )
    function loadChanpin(userpartId) {
        $.get("${ctxPath}/usewupin/query.do", {
            userpartId : userpartId
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
</script>
</body>
</html>
