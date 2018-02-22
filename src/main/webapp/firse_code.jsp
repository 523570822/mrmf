<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%--<%@ include file="/module/resources/wecommon.jsp"%>--%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    String basePathS =request.getServerName()+":"+request.getServerPort()+path+"/";
    String scheme=request.getScheme();
    pageContext.setAttribute("basePath",basePath);
    pageContext.setAttribute("basePathS",basePathS);
    pageContext.setAttribute("scheme",scheme);

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<base href="<%=scheme%>">

  <head>
    <base href="<%=basePathS%>">
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <meta content="email=no" name="format-detection">
    <meta name="description" content="This is  a ..">
    <title>任性猫</title>
      <script src="moduleweb/resources/js/jquery.min.js?v=2.1.4"></script>
</head>
  <script type="text/javascript">
      $(function(){
      $(".get_code").click(function(){
       //   var phone_reg=/^0?(17[0-9]|13[0-9]|15[0-9]|18[0-9]|14[57])[0-9]{8}$/;
       //   var phone=$("#phone").val();
      //    if(!phone_reg.test(phone)){
      //        alert("输入正确的手机号码！");
       //   }else{

var devicedId=GetQueryString("devicedId");
          var  organId=$("#organId").val();

          var  name=$("#name").val();
          var person = {
              "organId": organId,
              "name": name,
              "devicedId":devicedId
          }






       var json= JSON.stringify(person);
console.info(json);
     $.ajax(
              {
                  url:"/w/stageApp/firstInstall",
                  type: "POST",
                  data: person,
                  success: function(data){
                      console.info(data);
                      $("#dataTime").html("code:"+data.code+"</br> message"+data.message+"</br> data:"+data.data)
                  },


              } );

        //  }
      });
      });
  </script>



<body class="bg_gray">
    <div class="common_nav">
         		 <h4 class="font-34">首次绑定设备</h4>

   </div>
    <div class="pay_pwd_div">
        <div>
            <div class="pay_pwd_email">
                <span>镜台名称</span>
            </div>
            <div >
                <input  style="width: 1000px" type="text" placeholder="输入镜台名称" id="name" value=""/>
            </div>
        </div>
    </div>

    <div class="code_div">
        <div>
            <div class="phone_code_name">
                <span>店铺编码</span>
            </div>
            <div>
              <input style="width: 1000px" type="text" id="organId" value="5282086565131207777"  >
            </div>
        </div>
    </div>
    <br>
    <br>
    <br>
    <div class="get_code">
        <button>
         	  确定
        </button>
    </div>
<div id="dataTime">


</div>


</body>


  <script type="text/javascript">

      function GetQueryString(name)
      {
          var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
          var r = window.location.search.substr(1).match(reg);
          if(r!=null)return unescape(r[2]);
          return null;
      }

  </script>
</html>