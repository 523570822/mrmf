<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/moduleweb/resources/common.jsp"%>
<html>
<head>
    <title></title>
    <style type="text/css">
        /*span{
            color: #00b83f;
        }*/
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
                    <h5><a href="javascript:history.go(-1)">《返回</a> 代金券方法配置设置</h5>
                </div>
                <div class="ibox-content">
                    <c:if test="${returnStatus.status == 0}">
                        <div class="alert alert-danger">
                            <i class="fa fa-exclamation-triangle"></i>${returnStatus.message}</div>
                    </c:if>

                    <form id="config" method="post"
                          action="${ctxPath}/coupon/colligateCoupon/AddCoupon.do"
                          class="form-horizontal">
                        <c:if test="${grant._id!=null}">
                            <input type="hidden" value="${grant._id}" name="_id" id="_id">
                            <input type="hidden" name="state" value="${grant.state}">
                        </c:if>

                        <div class="form-group">
                            <label class="col-sm-4 control-label"><span>*</span>业务类型</label>
                            <div class="col-sm-6">
                                <select name="business" id="business" class="form-control" min="0" max="100" required>
                                    <option value="注册"<c:if test="${grant.business=='注册' }">selected = selected</c:if>>注册</option>
                                    <option value="消费"<c:if test="${grant.business=='消费' }">selected = selected</c:if>>消费</option>
                                    <option value="分享"<c:if test="${grant.business=='分享' }">selected = selected</c:if>>分享</option>
                                    <option value="关注"<c:if test="${grant.business=='关注' }">selected = selected</c:if>>关注</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label"><span>*</span>代金券类型</label>
                            <div class="col-sm-6">
                                <select id="type" name="type" class="form-control" min="0" max="100" required autocomplete="off">
                                    <option value="平台"<c:if test="${grant.type=='平台' }">selected = selected</c:if>>平台</option>
                                    <option value="店面"<c:if test="${grant.type=='店面' }">selected = selected</c:if>>店面</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">选择店铺</label>
                            <div class="col-sm-6">
                                <input style="width:88%;display:inline" class="form-control" min="0" max="100" required value="${grant.record}" id="shopName" name="record" readonly="readonly"/>
                                <input class="form-control" min="0" max="100" required value="${grant.recordId}" id="shopId" name="recordId" style="display: none"/>
                                <button style="float: right" class="btn btn-primary" type="button" id="choice" disabled="true">选择</button>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label"><span>*</span>有效期</label>
                            <div class="col-sm-6">
                                <input type="text" name="endTime" id="endTime" placeholder="请选择" class="laydate-icon form-control" value="<fmt:formatDate value="${grant.endTime}"></fmt:formatDate>"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">消费金额</label>
                            <div class="col-sm-6" style="font-size: 14px">
                                <input style="width:47%;display:inline" id="minCondition" name="minCondition" type="number" class="form-control"
                                       min="0" required value="${grant.minCondition}">
                                至
                                <input style="width:48%;display:inline" id="maxCondition" name="maxCondition" type="number" class="form-control"
                                       min="0" required value="${grant.maxCondition}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label"><span>*</span>单人领取次数</label>
                            <div class="col-sm-6">
                                <input id="singleReceive" name="singleReceive" type="number" class="form-control"
                                       min="0" required value="${grant.singleReceive}">
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
    <div class="col-sm-8">
        <table id="myTable"></table>
    </div>
</div>
<script>
    var flag="0";
    $().ready(
        $("#type").change(function(){
            if($("#type").val()=="店面"){
                //var url = _ctxPath + "/coupon/OperationController/queryOrgan.do";
                $("#choice").attr('disabled',false);
            }else if ($("#type").val()!="店面"){
                $("#choice").attr('disabled',true);
                $("#show").hide();
                $("#shopName").val("");
                $("#shopId").val("");
            }
        })
    )
    $("#business").change(function () {
        var business = $("#business").val();
        if(business!="消费"){
            $("#minCondition").attr('disabled',true);
            $("#maxCondition").attr('disabled',true);
            $("#type option[value=平台]").prop("selected",true);
            $("#type").attr("disabled",true);
            $("#minCondition").val("");
            $("#maxCondition").val("");
            $("#shopName").val("");
            $("#shopId").val("");
            $("#choice").attr('disabled',true);
        }else {
            $("#minCondition").attr('disabled',false);
            $("#maxCondition").attr('disabled',false);
            $("#type").attr("disabled",false);
        }
    })
    //提交表单的方法
    $("#sub").click(function () {
        var endTime = $("#endTime").val().trim();
        var minCondition = $("#minCondition").val().trim();
        var maxCondition = $("#maxCondition").val().trim();
        var singleReceive = $("#singleReceive").val().trim();
        if(!endTime){
            layer.alert("有效期不可为空");
            return;
        }
        var business = $("#business").val();
       /*if(business=="消费"){
           if(!minCondition||!maxCondition){
               layer.alert("消费金额不可为空");
               return;
           }
       }*/
        if(!singleReceive){
            layer.alert("领取次数不可为空");
            return;
        }
        $("#type").attr("disabled",false);
        var obj = $("#config").serialize();
        $.get("${ctxPath}/coupon/colligateCoupon/addCoupon.do",obj,function (date) {
            if(date==1){
                window.location.href="${ctxPath}/coupon/colligateCoupon/toCoupon.do";
            }else{
                alert("操作失败")
            }
        })
    })

    var la;
    $("#choice").click(function () {
        //wt_open_layer_html("model", ['50%', '40%'], '商品标签');
        la= layer.open({
            title:"店铺列表",
            type: 1,
            skin: 'layui-layer-rim', //加上边框
            area: ['870px', '540px'], //宽高
            content: $("#show"),
            shadeClose:true,
        });
        $("#show").show();
        //queryStaffRank();
    })
    //查询按钮事件
    $("#select").click(function () {
        $("#myTable").reloadGrid({data : $("#searchForm").formobj()});
    })

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
                colNames : [ "名称","是否有效", "联系人",
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
    //选择列表一家店触发的事件
    function  loadChanpin(sid,name) {
        //layer.closeAll();
        layer.close(la);
        $("#shopName").val(name);
        $("#shopId").val(sid);
        $("#show").hide();
        $("#big").empty();
        $("#big").append( "<option value="+""+">"+"所有大类"+"</option>" );
        $.post("${ctxPath}/coupon/OperationController/queryBigsort.do",{"organId":sid},function (data) {//查店面服务大类
            for (var i = 0; i <= data.length; i++) {
                $("#big").append( "<option value="+data[i]._id+">"+data[i].name+"</option>" );
            }
        })
    }
    $(document).ready(function () {
        var time = {
            elem : '#endTime',
            format : 'YYYY-MM-DD',
            max : '2099-06-16', //最大日期
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
</body>
</html>
