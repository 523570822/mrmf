<%--
  Created by IntelliJ IDEA.
  User: 蔺哲
  Date: 2017/6/27
  Time: 13:47
  To change this template use File | Settings | File Templates.
--%>
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
                    <h5><a href="javascript:history.go(-1)">《返回</a>
                        <c:if test="${vipMember==null}">新增vip会员</c:if>
                        <c:if test="${vipMember!=null}">编辑vip会员</c:if>
                    </h5>
                </div>
                <div class="ibox-content">
                    <c:if test="${returnStatus.status == 0}">
                        <div class="alert alert-danger">
                            <i class="fa fa-exclamation-triangle"></i>${returnStatus.message}</div>
                    </c:if>

                    <form id="sub" method="post"
                          action="${ctxPath}/shopVip/upsert.do"
                          class="form-horizontal">
                        <c:if test="${vipMember._id!=null}">
                            <input type="hidden" value="${vipMember._id}" name="_id" id="_id">
                        </c:if>

                        <input type="hidden" name="organId" value="${organId}">
                        <div class="form-group">
                            <label class="col-sm-4 control-label"><span>*</span>会员手机号</label>
                            <div class="col-sm-6">
                                <input id="vipPhone" name="phone" type="number" class="form-control"
                                       min="0" required value="${vipMember.phone}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label"><span>*</span>会员姓名</label>
                            <div class="col-sm-6">
                                <input id="vipName" name="name" type="text" class="form-control"
                                       min="0"  value="${vipMember.name}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">是否生效</label>
                            <div class="col-sm-6">
                                <input id="state" name="state" type="checkbox" <c:if test="${vipMember.state==true}">checked</c:if>
                                       class="switcher"/>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <div class="col-sm-4 col-sm-offset-2">
                                <button class="btn btn-primary" type="submit" id="subFrom">提交</button>
                            </div>
                            <div class="col-sm-4 col-sm-offset-2">
                                <button class="btn btn-primary" type="button" id="back">返回</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    $(document).ready(function () {
//        var time = {
//            elem : '#endTime',
//            format : 'YYYY-MM-DD',
//            max : '2099-06-16', //最大日期
//            istime : true,
//            istoday : false,
//            isclear: true, //是否显示清空
//            choose : function(datas) {
//                end.min = datas; //开始日选好后，重置结束日的最小日期
//                end.start = datas; //将结束日的初始值设定为开始日
//            }
//        };
//        laydate(time);
//        $inputForm.validate({
//            rules:{
//                "vidPhone":"required",
//                "vipName":{
//                    required:true
//                }
//            },
//            submitHandler:function (from) {
//                from.submit();
//            }
//        })
        $("#sub").submit(function () {
//            var myreg = /^(((13[0-9]{1})|(14[0-9]{1})|(17[0]{1})|(15[0-3]{1})|(15[5-9]{1})|(18[0-9]{1}))+\d{8})$/;
            var myreg = /^0?(13[0-9]|15[012356789]|17[013678]|18[0-9]|14[57])[0-9]{8}$/;
            var phone = $("#vipPhone").val();
            if(phone==""){
                toastr.warning("请输入手机号！");
                return false;
            }
            if(!myreg.test(phone)){
                toastr.warning("请输入正确手机号！");
                return false;
            }
            if($("#vipName").val()==""){
                toastr.warning("请输入会员姓名！");
                return false;
            }
        })
        $("#back").click(function () {
            window
                .location.href="${ctxPath}/shopVip/toQuery.do"
        })
    });
</script>
</body>
</html>
