<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/moduleweb/resources/common.jsp"%>
<html>
<head>
    <title></title>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>
                       <c:if test="${type==1}">
                           <a href="${ctxPath}/prestore/toQuery.do" class="btn-link">
                               <i class="fa fa-angle-double-left"></i>返回
                           </a>编辑商城管理员
                       </c:if>
                    </h5>
                </div>
                <div class="ibox-content">
                    <c:if test="${returnStatus.status == 0}">
                        <div class="alert alert-danger">
                            <i class="fa fa-exclamation-triangle"></i>${returnStatus.message}</div>
                    </c:if>
                    <form id="usercard" method="post"
                          action="${ctxPath}/shopVip/upsertShopMember.do"
                          class="form-horizontal">
                        <input type="hidden" id="_id" name="_id" value="${organ._id}" >
                        <input type="hidden" id="type" name="type" value="${type}" >
                        <div class="form-group">
                            <label class="col-sm-2 control-label">公司名称</label>
                            <div class="col-sm-10">
                                <input id="name" name="name" value="${organ.name}" type="text"
                                       class="form-control" disabled>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">手机号</label>
                            <div class="col-sm-10">
                                <input id="phone" name="phone" type="text"
                                       class="form-control" required value="${organ.shopPhone}"> <span
                                    class="help-block m-b-none">商城相关操作，请先指定管理员手机号。</span>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <div class="col-sm-4 col-sm-offset-2">
                                <button class="btn btn-primary" type="submit">提交</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var layerId;
    $().ready(
        function() {
            $("#usercard").validate({
                submitHandler : function(form) {
                    $.shade.show();
                    form.submit();
                }
            });

            $("#cancelBtn").click(
                function() {
                    document.location.href = _ctxPath
                        + "/prestore/toQuery.do";
                });

        });
</script>

</body>
</html>
