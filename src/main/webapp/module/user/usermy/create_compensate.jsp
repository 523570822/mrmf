<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/module/resources/wecommon.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html >
<html>
<head>
<base href="<%=basePath%>">
    <meta charset="utf-8">
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <meta content="email=no" name="format-detection">
    <meta name="description" content="This is  a ..">
<title>我要赔付</title>
<link rel="stylesheet" href="${ctxPath}/module/user/css/my_style.css"/>
</head>
<body class="bg_gray">
<form action="" method="post" id="compensate_form">
<input type="hidden" value="${userId }" id="user_id" name="userId"/>
<input type="hidden" value="${order._id }" id="order_id" name="orderId"/>
<input type="hidden" value="${code._id }" id="code_id" name="codeId"/>
<input type="hidden" value="${target }" id="target" name="target"/>
<input type="hidden" value="createCompensate" id="status"/>
    <div class="common_nav">
        <i id="back"></i>
        <h4 class="font-34">我要理赔</h4>
            <span class="commit_r" id="saveCompensate">
                                              提交
            </span>
    </div>

    <div class="my_com_item">
        <div id="selectOrder">
            <div class="col-2 my_com_title">
                <span>
                    理赔项目
                </span>
            </div>
            <div class="col-8 my_com_div">
                <div>
                    <span>${order.title }</span>
                    <i></i>
                </div>
            </div>
        </div>
    </div>
    <div class="my_com_item">
        <div id="selectProvider">
            <div class="col-2 my_com_title">
                <span>
                                                  店铺/技师
                </span>
            </div>
            <div class="col-8 my_com_div">
                <div>
                    <span>
                    	<c:if test="${target=='1' }">${order.organName }</c:if>
                    	<c:if test="${target=='2' }">${order.staffName }</c:if>
                    </span>
                    <!-- <i></i> -->
                </div>
            </div>
        </div>
    </div>
    <div class="my_com_item">
        <div id="selectType">
            <div class="col-2 my_com_title">
                <span>
                   理赔类型
                </span>
            </div>
            <div class="col-8 my_com_div">
                <div>
                    <span>${code.name }</span>
                    <i></i>
                </div>
            </div>
        </div>
    </div>
    <div class="com_div">
        <div class="com_des_title">
            <span>
                理赔描述
            </span>
        </div>
        <div class="com_des_content">
          <textarea placeholder="请描述您的理赔详情..." name="desc" id="desc"></textarea>
        </div>
    </div>
    <div class="com_pic_div">
        <div class="com_pic_title">
            <span>
                                         上传照片
            </span>
        </div>
        <div class="com_pic">
            <ul>
                <li id="pic_add">
                    <input type="hidden" id="logo" name="logo">
                    <img id="logoimg" src="${ctxPath }/module/user/images/btn_add_picture.png" name="file" onclick="doc.click()">
                    <div id="loading" class="none"><div class="img_mask"><div class="loading"></div></div></div>
                </li>
                <li id="pic_add">
                    <input type="hidden" id="logo1" name="logo1">
                    <img id="logoimg1" src="${ctxPath }/module/user/images/btn_add_picture.png" name="file" onclick="doc1.click()">
                    <div id="loading1" class="none"><div class="img_mask"><div class="loading"></div></div></div>
                </li>
                 <li id="pic_add">
                    <input type="hidden" id="logo2" name="logo2">
                    <img id="logoimg2" src="${ctxPath }/module/user/images/btn_add_picture.png" name="file" onclick="doc2.click()">
                    <div id="loading2" class="none"><div class="img_mask"><div class="loading"></div></div></div>
                </li>
                 <li id="pic_add">
                    <input type="hidden" id="logo3" name="logo3">
                    <img id="logoimg3" src="${ctxPath }/module/user/images/btn_add_picture.png" name="file" onclick="doc3.click()">
                    <div id="loading3" class="none"><div class="img_mask"><div class="loading"></div></div></div>
                </li>
            </ul>
        </div>
    </div>
</form>
<form action="" id="fileForm">
	<input type="file" rel="msgImage" autocomplete="off" id="doc" 
	onchange="changeFile(this)" style="display: none;" name="onlyFile">
</form>
<form action="" id="fileForm1">
	<input type="file" rel="msgImage" autocomplete="off" id="doc1" 
	onchange="changeFile1(this)" style="display: none;" name="onlyFile">
</form>
<form action="" id="fileForm2">
	<input type="file" rel="msgImage" autocomplete="off" id="doc2" 
	onchange="changeFile2(this)" style="display: none;" name="onlyFile">
</form>
<form action="" id="fileForm3">
	<input type="file" rel="msgImage" autocomplete="off" id="doc3" 
	onchange="changeFile3(this)" style="display: none;" name="onlyFile">
</form>

<script src="${ctxPath}/module/user/js/media_contrl.js"></script>
<script src="${ctxPath}/module/user/usermy/js/compensate.js"></script>
<script src="${ctxPath}/module/staff/js/jquery.form.js"></script>
<script type="text/javascript">
		function changeFile(_this) {
			$("#loading").toggleClass('block');
			$("#loading").toggleClass('none');
			$("#fileForm").ajaxSubmit({
				type : 'post',
				headers : {
					'type' : $(_this).attr("rel"),
					'isPublic' : 'true'
				},
				url : _ctxPath+'/w/file/upload.do',
				success : function(data) {
					var imgpath = _ossImageHost + data.data[0]+"@!style400";
					$("#logoimg").attr("src", imgpath);
					$("#logo").val(data.data[0]);
					$("#loading").toggleClass('block');
					$("#loading").toggleClass('none');
				},
				error : function(XmlHttpRequest, textStatus, errorThrown) {
					/* $.shade.hide(); */
				}
			});
		}
		function changeFile1(_this) {
			$("#loading1").toggleClass('block');
			$("#loading1").toggleClass('none');
			$("#fileForm1").ajaxSubmit({
				type : 'post',
				headers : {
					'type' : $(_this).attr("rel"),
					'isPublic' : 'true'
				},
				url : _ctxPath+'/w/file/upload.do',
				success : function(data) {
					var imgpath = _ossImageHost + data.data[0]+"@!style400";
					$("#logoimg1").attr("src", imgpath);
					$("#logo1").val(data.data[0]);
					$("#loading1").toggleClass('block');
					$("#loading1").toggleClass('none');
				},
				error : function(XmlHttpRequest, textStatus, errorThrown) {
					/* $.shade.hide(); */
				}
			});
		}
		
	   function changeFile2(_this) {
			$("#loading2").toggleClass('block');
			$("#loading2").toggleClass('none');
			$("#fileForm2").ajaxSubmit({
				type : 'post',
				headers : {
					'type' : $(_this).attr("rel"),
					'isPublic' : 'true'
				},
				url : _ctxPath+'/w/file/upload.do',
				success : function(data) {
					var imgpath = _ossImageHost + data.data[0]+"@!style400";
					$("#logoimg2").attr("src", imgpath);
					$("#logo2").val(data.data[0]);
					$("#loading2").toggleClass('block');
					$("#loading2").toggleClass('none');
				},
				error : function(XmlHttpRequest, textStatus, errorThrown) {
					/* $.shade.hide(); */
				}
			});
		}
		
		function changeFile3(_this) {
			$("#loading3").toggleClass('block');
			$("#loading3").toggleClass('none');
			$("#fileForm3").ajaxSubmit({
				type : 'post',
				headers : {
					'type' : $(_this).attr("rel"),
					'isPublic' : 'true'
				},
				url : _ctxPath+'/w/file/upload.do',
				success : function(data) {
					var imgpath = _ossImageHost + data.data[0]+"@!style400";
					$("#logoimg3").attr("src", imgpath);
					$("#logo3").val(data.data[0]);
					$("#loading3").toggleClass('block');
					$("#loading3").toggleClass('none');
				},
				error : function(XmlHttpRequest, textStatus, errorThrown) {
					/* $.shade.hide(); */
				}
			});
		}
</script>
</body>
</html>