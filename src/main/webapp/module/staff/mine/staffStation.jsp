<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/module/resources/wecommon.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <meta content="email=no" name="format-detection">
    <meta name="description" content="This is  a ..">
    <title>任性猫</title>
    <link rel="stylesheet" href="${ctxPath}/module/resources/css/my_style.css"/>
    <link rel="stylesheet" type="text/css" href="${ctxPath}/module/staff/css/style_mainPage.css"/>
    <link href="${ctxPath}/module/resources/css/my_style.css" rel="stylesheet">
</head>
<body id="station">
<input type="hidden" value="${staffId}" id="staff_id" name="staffId">
<input type="hidden" value="" id="page"/>
<input type="hidden" value="" id="pages"/>
<input type="hidden" value="1" id="type"/>
<input type="hidden" value="myStation" id="status">
<div class="common_nav">
    <i onclick="window.history.go(-1)"></i>
    <h4 class="font-34">我的工位租赁</h4>
</div>
<div class="txt_wrap">
    <div class="station-list-box">
        <ul id="station_list" class="clear">
            <%--<li>--%>
            <%--<div class="clear"><h3>爱啥啥某某某的沙沙啥店</h3><span>进行中</span><p>详情></p></div>--%>
            <%--<div >--%>
            <%--<div class="clear"><p>服务日期：</p><span>2017-2-3</span></div>--%>
            <%--<div class="clear"><p>支付租金</p><span>￥3000</span></div>--%>
            <%--</div>--%>
            <%--</li>--%>
            <%--<li>--%>
            <%--<div class="clear"><h3>爱啥啥某某某的沙沙啥店</h3><span>进行中</span><p>详情></p></div>--%>
            <%--<div >--%>
            <%--<div class="clear"><p>服务日期：</p><span>2017-2-3</span></div>--%>
            <%--<div class="clear"><p>支付租金</p><span>￥3000</span></div>--%>
            <%--</div>--%>
            <%--</li>--%>
        </ul>
    </div>
</div>
<script>
    $(document).ready(function () {
        var staffId=$("#staff_id").val();
        $.post(_ctxPath + "/w/staffMy/myStationList",{"staffId":staffId},
            function(data){
                var obj=eval(data);

                var html='';
                $("#pages").val(data.pages);
                $("#page").val(data.page);

                if (obj.data.length==0) {
                    html+='<li><div class="notOrder"></div><div class="notOrderTitle">暂无租赁信息</div></li>';
                    $("#station_list").html($("#station_list").html()+html);
                }else {
                    for(var i=0;i<obj.data.length;i++) {
                        html +='<li><div class="clear"><h3>' + obj.data[i].organName + '</h3>' ;
                        if(obj.data[i].state == 1){
                            if(obj.data[i].timeState==0){
                                html +=  '<span id="station_img1"></span>' ;
                            }else if(obj.data[i].timeState==1){
                                html +=  '<span id="station_img"></span>' ;
                            }else if(obj.data[i].timeState==2){
                                html +=  '<span id="station_img2"></span>' ;
                            }
                        }else if(obj.data[i].state == 0){
                            html +=  '<span id="station_img3"></span>' ;
                        }else if(obj.data[i].state == 2){
                            html +=  '<span id="station_img4"></span>' ;
                        }
                        html +=  '<p><a href="JavaScript:void(0);" onclick="station(\''+obj.data[i]._id+'\')">详情</a></p></div>'+
                            '<div ><div class="clear"><p>服务日期</p><span>' + obj.data[i].timeString + '（共' + obj.data[i].totalDay +'天）</span></div>';
                        if(obj.data[i].leaseType==0){
                            html+='<div class="clear"><p>支付租金</p><span>￥'+ obj.data[i].leaseMoney +'</span></div></div></li>';
                        }else {
                            html+='<div class="clear"><p>分账模式</p></div></div></li>';
                        }
                    }
                    $("#station_list").html($("#station_list").html() + html);
                }
            },
            "json");//这里返回的类型有：json,html,xml,text
    })
    function station(positionOrderId) {
        var staffId=$("#staff_id").val();
        window.location.href = _ctxPath + "/w/staffMy/myStationDetail.do?positionOrderId="+positionOrderId+"&staffId="+staffId;
    }
</script>
</body>
</html>