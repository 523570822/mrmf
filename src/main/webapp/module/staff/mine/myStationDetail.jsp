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
    <link href="${ctxPath}/module/staff/css/style_technician.css" rel="stylesheet">
    <title>任性猫</title>
</head>

<body style="background: #f8f8f8">
<div class="sta">
    <i class="back" id="back" onclick="window.history.go(-1)"></i>
    <h4 class="font-34">租赁详情</h4>
    <c:if test="${stationDetail.getTimeState()==0}">
        <span id="lixi">联系店主</span>
    </c:if>
    <c:if test="${stationDetail.getTimeState()==1}">
        <span id="lixi">联系店主</span>
    </c:if>

</div>
<input type="hidden" value="${staffId}" id="staff_id">
<input type="hidden" value="${organId }" id="organ_id">
<input type="hidden" value="${positionOrderId}" id="positionOrder_id">
<input name="distance" type="hidden" value="${stationDetail.getDistrict()}" />
<input name="city" type="hidden" value="${stationDetail.getCity()}" />
<div class="station_box">
    <div><img src="${ossImageHost}${stationDetail.getLogo()}" ></div>
    <div class="sta2 clear_both">
        <h3>${stationDetail.getOrganName()}</h3>
        <c:if test="${stationDetail.getLeaseType()==0}">
            <div class="tup0">
                <span class="tup"></span>
                <c:if test="${stationDetail.getTimeState()==0}">
                    <span class="tup3"></span>
                </c:if>
                <c:if test="${stationDetail.getTimeState()==1}">
                    <span class="tup1"></span>
                </c:if>
                <c:if test="${stationDetail.getTimeState()==2}">
                    <span class="tup4"></span>
                </c:if>

            </div>
            <div class="zuji">
                <span class="zuji_1">租金总额</span><span >￥${stationDetail.getTotalMoney()} </span>
                <span class="zuji_2">(${stationDetail.getTotalDay()}天，￥ ${stationDetail.getLeaseMoney()}/天)</span>
            </div>
        </c:if>
        <c:if test="${stationDetail.getLeaseType()==1}">
            <div class="tup0">
                <span class="tup2"></span>
                <c:if test="${stationDetail.getState()==0}">
                    <span class="tupD"></span>
                </c:if>
                <c:if test="${stationDetail.getState()==2}">
                    <span class="tupN"></span>
                </c:if>
                <c:if test="${stationDetail.getState()==1}">
                   <c:if test="${stationDetail.getTimeState()==0}">
                       <span class="tup3"></span>
                   </c:if>
                   <c:if test="${stationDetail.getTimeState()==1}">
                       <span class="tup1"></span>
                   </c:if>
                   <c:if test="${stationDetail.getTimeState()==2}">
                       <span class="tup4"></span>
                   </c:if>
               </c:if>
            </div>
            <div class="zuji"><span class="zuji_1"></span></div>
        </c:if>
    </div>
    <div class="address_box">
        <span class="tup1"></span>
        <span>${stationDetail.getAddress()}</span>
        <span class="tup2" onclick="toStoreDetail()"></span>
    </div>
</div>
<div class="station_list_box">
    <div>
        <span>时间(共${stationDetail.getTotalDay()}天)</span>
        <span>工位号</span>
    </div>
    <ul id="station_List">
    </ul>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        var organId=$("#organ_id").val();
        var staff=$("#staff_id").val();
        var positionOrderId=$("#positionOrder_id").val();
        $.post(_ctxPath + "/w/staffMy/toStationList",{"positionOrderId":positionOrderId},
            function(map){
//                var obj=eval(data);
                var html='';
//                $("#pages").val(data.pages);
//                $("#page").val(data.page);
                var i=0;
                for(var k in map){
                    i++;
                }
                    if (i==0) {
                        html+='';
                        $("#station_List").html($("#station_List").html()+html);
                    }else {
                    var j=1;
                        for(var k in map){  //通过定义一个局部变量k遍历获取到了map中所有的key值
                            j++;
                            var docList=map[k]; //获取到了key所对应的value的值！
                            if((j+1)%2){
                                html+=' <li class="station_cell"><span>'+k+'</span><span>'+map[k]+'</span></li>';
                            }else{
                                html+=' <li class="station_cell2"><span>'+k+'</span><span>'+map[k]+'</span></li>';
                            }

                        }
                        $("#station_List").html($("#station_List").html() + html);
                    }

            },
            "json");//这里返回的类型有：json,html,xml,text
    });
    function toStoreDetail(){
        var staffId=$("#staff_id").val();
        var distance=$("#distance").val();
         var organId=$("#organ_id").val();
        var city=$("#city").val();
//	alert(city+"888"+distance);
        window.location.href = _ctxPath + "/w/staff/toOrganDetail.do?organId="+organId+"&staffId="+staffId+"&distance="+distance+"&city="+encodeURIComponent(encodeURIComponent(city));
    }
</script>
</body>
</html>