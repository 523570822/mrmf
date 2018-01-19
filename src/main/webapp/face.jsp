<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/module/resources/wecommon.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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

          var  face=$("#face").val();
        var data1=$("#data").val();
        console.info(data1);
          var data = JSON.parse(data1);
          $.post(_ctxPath +face,data,
                  function(data){
                  console.info(data);
                   $("#dataTime").html("code:"+data.code+"</br> message"+data.message+"</br> data:"+data.data)
                  });
        //  }
      });
      });
  </script>



<body class="bg_gray">
    <div class="common_nav">
         		 <h4 class="font-34">接口测试</h4>

   </div>
    <div class="pay_pwd_div">
        <div>
            <div class="pay_pwd_email">
                <span>接口地址1236</span>
            </div>
            <div >
                <input  style="width: 1000px" type="text" placeholder="输入接口地址1" id="face" value="/w/app/"/>
            </div>
        </div>
    </div>

    <div class="code_div">
        <div>
            <div class="phone_code_name">
                <span>参数</span>
            </div>
            <div>
              <input style="width: 1000px" type="text" id="data">
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

    Welcome<br/><input id="text" type="text"/>
    <button onclick="send()">发送消息</button>
    <hr/>
    <button onclick="closeWebSocket()">关闭WebSocket连接</button>
    <hr/>
    <div id="message"></div>
</body>


  <script type="text/javascript">
      var websocket = null;
      //判断当前浏览器是否支持WebSocket
      if ('WebSocket' in window) {
          websocket = new WebSocket("ws://localhost:80/websocket");
      }
      else {
          alert('当前浏览器 Not support websocket')
      }

      //连接发生错误的回调方法
      websocket.onerror = function () {
          setMessageInnerHTML("WebSocket连接发生错误");
      };

      //连接成功建立的回调方法
      websocket.onopen = function () {
          setMessageInnerHTML("WebSocket连接成功");
      }

      //接收到消息的回调方法
      websocket.onmessage = function (event) {
          setMessageInnerHTML(event.data);
      }

      //连接关闭的回调方法
      websocket.onclose = function () {
          setMessageInnerHTML("WebSocket连接关闭");
      }

      //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
      window.onbeforeunload = function () {
          closeWebSocket();
      }

      //将消息显示在网页上
      function setMessageInnerHTML(innerHTML) {
          document.getElementById('message').innerHTML += innerHTML + '<br/>';
      }

      //关闭WebSocket连接
      function closeWebSocket() {
          websocket.close();
      }

      //发送消息
      function send() {
          var message = document.getElementById('text').value;
          websocket.send(message);
      }
  </script>
</html>