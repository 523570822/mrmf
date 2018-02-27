<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/module/resources/wecommon.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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


  <head>

    <base href="<%=basePath%>">
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <meta content="email=no" name="format-detection">
    <meta name="description" content="This is  a ..">
    <title>任性猫</title>

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

     $.ajax(
         {
                  url:"w/stageApp/firstInstall",
                  type: "POST",
                  data: person,
                  success: function(data){
                      console.info(data);
                      $("#dataTime").html("code:"+data.code+"</br> message"+data.message+"</br> data:"+data.data)
                  },
                  error:function(data){
                      console.info(data);
                  },
              } );

        //  }
      });
      });


       function pay() {
           var devicedId=GetQueryString("devicedId");
           var person = {
               "money": "12",
               "type": "staff",
               "phone": "15620512598",
               "devicedId":devicedId
           }
           $.ajax(
               {
                   url:"w/app/appPay",
                   type: "POST",
                   data: person,
                   success: function(data){
                       console.info(data);
                       $("#dataTime").html("接口：w/app/appPay <br>code:"+data.code+"</br> message"+data.message+"</br> data:"+data.data)
                   },
                   error:function(data){
                       console.info(data);
                   },
               } );

       }

      function close() {
          var devicedId=GetQueryString("devicedId");
          var person = {

              "devicedId":devicedId
          }
          $.ajax(
              {
                  url:"w/app/closeStage",
                  type: "POST",
                  data: person,
                  success: function(data){
                      console.info(data);
                      $("#dataTime").html("接口：w/app/closeStage <br>code:"+data.code+"</br> message"+data.message+"</br> data:"+data.data)
                  },
                  error:function(data){
                      console.info(data);
                  },
              } );


      }
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
    <div class="form-group">
        <label class="col-sm-2 control-label"> 店铺名称</label>
        <div class="col-sm-10">
            <select id="organId" name="organId" width="10px">
                <option value="">---请选择---</option>
                <c:forEach items="${organlist}" var="organ" varStatus="row">

                       <%-- <c:when test="${file.fid eq obj.service.docUrl}">
                            <option value="${file._id}" selected="selected">${file.name }</option>
                        </c:when>
                        <c:otherwise>
                            <option value="${file._id}">${file.name}</option>
                        </c:otherwise>--%>
                        <option value="${organ._id}">${organ.name}</option>


                </c:forEach>
            </select>
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
<div >
  <input id="pay" onclick="pay()" value="支付">

</div>

    <div >
        <input id="close" onclick="close()" value="关闭镜台">

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