<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/module/resources/wecommon.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <meta content="email=no" name="format-detection">
    <meta name="description" content="This is  a ..">
<title>个人信息</title>
</head>
<link href="${ctxPath}/module/staff/css/style_technician.css" rel="stylesheet">
<body style="background: #f8f8f8">
<div class="nav">
    <i class="back" id="back"></i>
    <h4 class="font-34">个人信息</h4>
</div>
<p class="font-30 prompt">完善信息，入职店铺</p>
<input type="hidden" value="${staff._id }" id="staff_id">
<input type="hidden" value="myDetail" id="status">
<div class="mine_content">
    <ul>
        <li class="header_show" onclick="selectImg()">
            <div class="col-2">头像</div>
            <div class="col-7 header">
            <input type="hidden" id="logo" >
            <input type="hidden" id="headImg" value="${staff.logo}">
            <%--<img src="" name="file" id="logoimg"/>--%>
            <img src="" id="logoimg"/>

            <div id="loading" class="none"><div class="img_mask" style="width: 3.6rem;height: 3.6rem;"><div class="loading" style="height: 2.5rem;width: 2.5rem;"></div></div></div>
           <%--  <c:if test="${empty staff.logo}">
                <img src="module/staff/images/img/icon_headportrait .png" alt="header" name="file"/>
            </c:if> --%>
            </div>
            <div class="col-1 next"></div>
        </li>
        <li id="change_nick">
            <div class="col-2">昵称</div>
            <div class="col-7" id="staff_nick">${staff.nick } </div>
            <div class="col-1 next" ></div>
            
        </li>
        <li id="change_name">
            <div class="col-2">姓名</div>
            <div class="col-7" >${staff.name }</div>
            <div class="col-1 next" ></div>
        </li>
        <li id="change_phone">
            <div class="col-2">手机号</div>
            <div class="col-7" >${staff.phone }</div>
            <div class="col-1 next" ></div>
        </li>
        <li id="change_idcard">
            <div class="col-2">身份证号</div>
            <div class="col-7" >${staff.idcard }</div>
            <div class="col-1 next" ></div>
        </li>
        <li id="change_sex">
            <div class="col-2">性别</div>
            <div class="col-7" >${staff.sex }</div>
            <div class="col-1 next" ></div>
        </li>
        <li id="change_certNumber">
            <div class="col-2">资格证号</div>
            <div class="col-7" >${staff.certNumber }</div>
            <div class="col-1 next" ></div>
        </li>
        <li id="change_home">
            <div class="col-2">联系地址</div>
            <div class="col-7" >${staff.home }</div>
            <div class="col-1 next" ></div>
        </li>
        <li id="change_jishiTechang">
            <div class="col-2">技师特长</div>
            <div class="col-7" >${staff.jishiTechang }</div>
            <div class="col-1 next" ></div>
        </li>
        <li id="change_workYears">
            <div class="col-2">工作年限</div>
            <c:choose>
            	<c:when test="${staff.workYears == 6 }">
            		  <div class="col-7" >10年及以上</div>
            	</c:when>
            	<c:otherwise>
            		  <div class="col-7" >${staff.workYears }年</div>
            	</c:otherwise>
            </c:choose>
            <div class="col-1 next" ></div>
        </li>
    </ul>
</div>

<form action="" class="form-horizontal">
    <div class="form-group" style="display: none;">
        <input type="file" id="file" onchange="changeFiles()" autocomplete="off" class="form-control"/><br>
        <input type="text" class="form-control" id="object-key-file" placeholder="填写视频名称" />
        <input type="hidden" name="videoName" id="videoName">
    </div>
</form>
<div class="progress" style="display: none">
    <div id="progress-bar"
         class="progress-bar"
         role="progressbar"
         aria-valuenow="0"
         aria-valuemin="0"
         aria-valuemax="100" style="min-width: 2em;">
        0%
    </div>
</div>
<form id="fileForm" enctype="multipart/form-data"  method="post">
 	<input type="file" rel="msgImage" autocomplete="off" id="onlyFile"
	onchange="changeFile(this)" style="display: none;" name="onlyFile">
</form>
<%--<input type="file" id="file" autocomplete="off"--%>
<%--onchange="changeFiles()" style="display: none;" name="file">--%>
<%--<input type="file" id="file" style="display: none;" onchange="changeFiles(this)"/>--%>

<script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
<script src="${ctxPath}/module/staff/js/staff.js"></script>
<script src="${ctxPath}/module/staff/js/jquery.form.js"></script>
<script src="https://www.promisejs.org/polyfills/promise-6.1.0.js"></script>
<script type="text/javascript" src="http://gosspublic.alicdn.com/aliyun-oss-sdk.min.js"></script>
<script src="${ctxPath}/module/staff/js/uploadFile.js"></script>
<script type="text/javascript">
    $(function () {
        var headImg = $("#headImg").val();
        if(headImg.indexOf("http")==0) {
            $("#logoimg").attr("src", headImg);
        }
//        if (headImg != '' && headImg != undefined && headImg != 'undefined' && headImg.indexOf("http")>0){
//
//        }
        if(headImg.indexOf("http") == -1){
            $("#logoimg").attr("src", "${ossImageHost}${staff.logo}@!avatar");
        }
    });

    function selectImg(){
      //  $("#file").click();
     $("#onlyFile").click();
    }
    function changeFile(_this) {
        //$.shade.show();
        $("#loading").toggleClass('block');
        $("#loading").toggleClass('none');
        $("#fileForm").ajaxSubmit({
            type: 'post',
            headers: {
                'type': $(_this).attr("rel"),
                'isPublic': 'true'
            },
            url: _ctxPath + '/w/file/upload.do',
            success: function (data) {
                /* $.shade.hide(); */
                var imgpath = _ossImageHost + data.data[0];
                $("#logoimg").attr("src", imgpath);
                $("#logo").val(data.data[0]);
                $("#loading").toggleClass('block');
                $("#loading").toggleClass('none');
                //保存图片
                $.post(_ctxPath + "/w/staff/savePhoto", {'staffId': $("#staff_id").val(), 'logo': $("#logo").val()},
                        function (data) {
                        },
                        "text");
            },
            error: function (XmlHttpRequest, textStatus, errorThrown) {
//                /* $.shade.hide(); */
//                alert(JSON.stringify(XmlHttpRequest));
//                alert(JSON.stringify(textStatus));
//                alert(JSON.stringify(errorThrown));
//                alert("阿里失败");
            }
        });

    }
</script>
</body>
</html>