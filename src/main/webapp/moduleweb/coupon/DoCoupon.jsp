<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/moduleweb/resources/common.jsp"%>
<html>
<head>
    <title></title>
    <style type="text/css">
        #config>div>label>span{
            color: red;
        }
    </style>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5><a href="javascript:history.go(-1)">《返回</a> 优惠券</h5>
                </div>
                <div class="ibox-content">
                    <c:if test="${returnStatus.status == 0}">
                        <div class="alert alert-danger">
                            <i class="fa fa-exclamation-triangle"></i>${returnStatus.message}</div>
                    </c:if>
                    <form id="config" method="post"
                          action="${ctxPath}/coupon/OperationController/AddCoupon.do"
                          class="form-horizontal">
                        <input type="hidden" value="${coupon.donate}" id="couponDonate"/>
                        <input type="hidden" name="state" value="${coupon.state}">
                        <input type="hidden" name="startTime" value="<fmt:formatDate value="${coupon.startTime}"/>">
                        <c:if test="${coupon.grantId!=null}"><input type="hidden" value="${coupon.grantId}" name="grantId" id="grantId"></c:if>
                        <input type="hidden" value="${coupon.bigSortName}" name="bigSortName" id="bigSortName">
                        <c:if test="${coupon._id!=null}"><input type="hidden" value="${coupon._id}" name="_id" id="_id"></c:if>
                        <div class="form-group">
                            <label class="col-sm-4 control-label"><span>*</span>作用范围</label>
                            <div class="col-sm-6">
                                <select id="type" name="couponType" class="form-control" min="0" max="100" required>
                                    <option value="">请选择</option>
                                    <option value="平台"<c:if test="${coupon.couponType=='平台' }">selected = selected</c:if>>平台</option>
                                    <option value="店面"<c:if test="${coupon.couponType=='店面' }">selected = selected</c:if>>店面</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">选择店铺</label>
                            <div class="col-sm-6">
                                <input style="width:89%;display:inline" class="form-control" min="0" max="100" required value="${coupon.shopName}" id="shopName" name="shopName" readonly="readonly"/>
                                <input class="form-control" min="0" max="100" required value="${coupon.shopId}" id="shopId" name="shopId" style="display: none"/>
                                <button style="float: right" class="btn btn-primary" type="button" id="choice" disabled="true">选择</button>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">店铺类型</label>
                            <div class="col-sm-6">
                                <select name="typeName" id="shopType" class="form-control" min="0" max="100" required>
                                    <option value="通用类型">通用类型</option>
                                    <c:forEach items="${typeList}" var="list">
                                        <option value="${list}"<c:if test="${list == coupon.typeName}">selected = selected</c:if> >${list}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">服务大类</label>
                            <div class="col-sm-6">
                                <select name="bigSort" id="big" class="form-control" min="0" max="100" required>
                                    <c:forEach items="${big}" var="big">
                                        <option value="${big._id}"<c:if test="${big._id == coupon.bigSort}">selected = selected</c:if> >${big.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label"><span>*</span>有效期</label>
                            <div class="col-sm-6">
                                <input type="text" name="endTime" id="endTime" placeholder="请选择" class="laydate-icon form-control" value="<fmt:formatDate value="${coupon.endTime}"></fmt:formatDate>"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label"><span>*</span>发放类型</label>
                            <div class="col-sm-6 " style="font-size: 14px;">
                                金额
                                <input name="favourableType" checked="checked" <c:if test="${coupon.favourableType==0}">checked</c:if> type="radio" min="0" required value="0">
                                比例
                                <input name="favourableType" <c:if test="${coupon.favourableType==1}">checked</c:if> type="radio" min="0" required value="1">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label" id="xuan"><span>*</span>金额</label>
                            <div class="col-sm-6" style="font-size: 14px">
                                <input style="width:47%;display:inline" id="minValue" name="minValue" type="number" class="form-control"
                                       min="0" required value="${coupon.minValue}">
                                至
                                <input style="width:48%;display:inline" id="maxValue" name="maxValue" type="number" class="form-control"
                                       min="0" required value="${coupon.maxValue}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label"><span>*</span>最低消费</label>
                            <div class="col-sm-6">
                                <input id="minConsume" name="minConsume" type="digits" class="form-control"
                                       min="0" required value="${coupon.minConsume}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">是否允许转增</label>
                            <div class="col-sm-6">
                                <input id="donate" name="donate" type="checkbox" <c:if test="${coupon.donate}">checked</c:if>
                                       class="switcher"/>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <div class="col-sm-4 col-sm-offset-2">
                                <button class="btn btn-primary" type="button" id="sub">提交</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="form-group ibox-content" style="display: none" id="show">
    <form id="searchForm">
        <label class="col-sm-4 control-label">店名<input class="form-control" style="width:85%;display:inline" type="text" name="regex:name" id="regex:name"/></label>
        <label class="col-sm-4 control-label">手机号<input class="form-control" style="width:82%;display:inline" type="text" id="regex:phone" name="regex:phone"/></label>
        <button class="btn btn-primary" type="button" id="select">查询</button>
    </form>
    <div class="col-sm-8 ">
        <table id="myTable"></table>
    </div>
 </div>
<script>
    var flag="0";
    var typeFlag="0";
    var bigFlag="0";
    $().ready(

        $("#type").change(function(){
            var typeVal = $("#type").val();
            if(typeVal=="店面"){
                $("#choice").attr('disabled',false);
                $("#big").empty();
                $("#big").append( "<option value="+"通用大类"+">"+"通用大类"+"</option>" );
                if(typeFlag==1){
                    $("#bigSortName").val("通用大类");
                    $("#shopType").empty();
                    $("#shopType").append( "<option value="+"通用类型"+">"+"通用类型"+"</option>" );
                }
            }else if (typeVal=="平台"){
                $("#choice").attr('disabled',true);
                $("#show").hide();
                $("#shopName").val("");
                $("#shopId").val("");
                $("#big").empty();
                $("#big").append( "<option value="+"通用大类"+">"+"通用大类"+"</option>" );
                if (typeFlag==1){
                    $("#bigSortName").val("通用大类");
                    $("#shopType").empty();
                    $("#shopType").append( "<option value="+"通用类型"+">"+"通用类型"+"</option>" );
                    $("#shopType").append( "<option value="+"美发"+">"+"美发"+"</option>" );
                    $("#shopType").append( "<option value="+"美容"+">"+"美容"+"</option>" );
                    $("#shopType").append( "<option value="+"养生"+">"+"养生"+"</option>" );
                    $("#shopType").append( "<option value="+"美甲"+">"+"美甲"+"</option>" );
                }
            }else{
                $("#choice").attr('disabled',true);
                $("#show").hide();
                $("#shopType").empty();
                $("#shopType").append( "<option value="+"通用类型"+">"+"通用类型"+"</option>" );
                $("#big").empty();
                $("#bigSortName").val("通用大类");
                $("#big").append( "<option value="+"通用大类"+">"+"通用大类"+"</option>" );
                $("#shopName").val("");
                $("#shopId").val("");
            }
        })
    )
    $("#myTable")
        .grid(
            {
                url : _ctxPath
                + "/coupon/OperationController/queryOrgan.do",
                styleUI: 'Bootstrap',
                //rownumbers:true,
                //pager:null,
                //datatype : "local",
                postData : $("#searchForm").formobj(),
                onSelectRow : function(id) {
                    var kd = $("#" + id,
                        $("#myTable"))
                        .data("rawData");
                    loadChanpin(kd._id,kd.name);
                },
                autowidth: true,
                shrinkToFit: true,
                height:350,
                width:850,
                colNames : [ "名称","地址", "联系人",
                    "手机号"],
                colModel : [
                    {
                        name : "name",
                        index : "name",
                        width : 200
                    },
                    {
                        name : "address",
                        index : "address",
                        align : "left",
                        width : 370
                    },
                    {
                        name : "contactMan",
                        index : "contactMan",
                        align : "center",
                        width : 100
                    },
                    {
                        name : "phone",
                        index : "phone",
                        align : "right",
                        width : 120
                    }
                ]
            })
    var layerId;
    //选择按钮事件
    $("#choice").click(function () {
        layerId= layer.open({
            title:"店铺列表",
            type: 1,
            skin: 'layui-layer-rim', //加上边框
            area: ['870px', '540px'], //宽高
           content: $("#show"),
            shadeClose:true,
      });
       $("#show").show();
    })
    //查询按钮事件
    $("#select").click(function () {
        $("#myTable").reloadGrid({data : $("#searchForm").formobj()});
    })
    //选择列表一家店触发的事件
    function  loadChanpin(shopId,name) {
        layer.close(layerId);
        $("#shopName").val(name);
        $("#shopId").val(shopId);
        $("#show").hide();
        $("#shopType").empty();
        $("#big").empty();
        $("#bigSortName").val("通用大类");
        $("#big").append( "<option value="+"通用大类"+">"+"通用大类"+"</option>" );
        $("#shopType").append( "<option value="+"通用类型"+">"+"通用类型"+"</option>" );
        $.post("${ctxPath}/coupon/OperationController/queryShopType.do",{"organId":shopId},function (data) {
            for (var i = 0; i < data.length; i++) {
                $("#shopType").append( "<option value="+data[i]+">"+data[i]+"</option>" );
            }
        })
    }
    //店铺类型选中事件
    $("#shopType").change(function () {
        var shId= $("#shopId").val();
        var typeName= $("#shopType").val();
        var type = $("#type").val();
        $("#big").empty();
        $("#big").append( "<option value="+"通用大类"+">"+"通用大类"+"</option>" );
       if(typeName!="通用类型"){
           if(type=="平台"){
               $.post("${ctxPath}/coupon/OperationController/queryCode.do",{"typeName":typeName},function (data) {
                   for (var i = 0; i < data.length; i++) {
                       $("#big").append( "<option value="+data[i]._id+" >"+data[i].name+"</option>" );
                       var da = data[i].name;
                       var bigSort = $("#bigSortName").val();
                       var daId = data[i]._id;
                       if(da==bigSort&&flag==0){
                           $("#big option[value="+daId+"]").attr("selected",true);
                           flag=1;
                       }
                   }
               })
           }else if(type=="店面"){
               $.post("${ctxPath}/coupon/OperationController/queryBigsort.do",{"organId":shId,"typeName":typeName},function (data) {
                   for (var i = 0; i < data.length; i++) {
                       $("#big").append( "<option value="+data[i]._id+" >"+data[i].name+"</option>" );
                       var da = data[i].name;
                       var bigSort = $("#bigSortName").val();
                       var daId = data[i]._id;
                       if(da==bigSort&&flag==0){
                           $("#big option[value="+daId+"]").attr("selected",true);
                           flag=1;
                       }
                   }
               })
           }
       }
       if(typeFlag==1){
           $("#bigSortName").val("通用大类");
       }
        typeFlag=1;
    })
    //大类选中事件
    $("#big").change(function(e){
       if(bigFlag==1){
           var big = $("#big").val();
           var bigName = $("#big option:selected").text()
           $("#bigSortName").val(bigName);
       }
        bigFlag=1;
    })
    //提交表单的方法
    $("#sub").click(function () {
        var ff = 0;
        var endTime = $("#endTime").val().trim();
        var minValue = $("#minValue").val().trim();
        var maxValue = $("#maxValue").val().trim();
        var minConsume = $("#minConsume").val().trim();
        if(!endTime){
            layer.alert("有效期不能为空");
            return;
        }
        if(!minValue){
            layer.alert("金额不可为空");
            return;
        }
        if(!maxValue){
            layer.alert("金额不可为空");
            return;
        }
        if(!minConsume){
            layer.alert("最低消费不可为空");
            return;
        }
            var obj = $("#config").serialize();
            $.post("${ctxPath}/coupon/OperationController/AddCoupon.do",obj,function (data) {
                if(data==1){
                    layer.alert("操作成功");
                    window.location.href="${ctxPath}/coupon/colligateCoupon/toCoupon.do";
                }else{
                    layer.alert("操作失败");
                }
            })
    });
    $("input:radio[name=favourableType]").change(function () {
        var val=$('input:radio[name="favourableType"]:checked').val();
        if(val==0){
            $("#xuan").html("金额")
        }else if(val==1){
            $("#xuan").html("比例")
        }
    });
    $(document).ready(function () {


        /*if(dd){

            $("#donate").show();
        }*/
        var time = {
            elem : '#endTime',
            format : 'YYYY-MM-DD',
            max : '2099-06-16 23:59:59', //最大日期
            istime : true,
            istoday : false,
            isclear: true, //是否显示清空
            choose : function(datas) {
                end.min = datas; //开始日选好后，重置结束日的最小日期
                end.start = datas; //将结束日的初始值设定为开始日
            }
        };
        laydate(time);
    });
</script>
<script src="${ctxPath}/moduleweb/resources/plugins/laydate/laydate.js"></script>
</body>
</html>
