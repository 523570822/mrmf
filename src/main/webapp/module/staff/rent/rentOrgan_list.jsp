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

    <meta charset="utf-8">
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <meta content="email=no" name="format-detection">
    <meta name="description" content="This is  a ..">
    <title>任性猫</title>
    <link href="${ctxPath}/module/resources/css/style_user_index.css" rel="stylesheet"/>
    <link href="${ctxPath}/module/resources/css/my_style.css" rel="stylesheet">
    <link href="${ctxPath}/module/organ/store/css/nodata.css" rel="stylesheet">

</head>
<body style="background-color: white">
    <div class="zulin">
        <i class="back" id="back" onclick="window.history.go(-1)"></i>
        <h4 class="font-34">工位租赁</h4>
        <span id="lixi">租赁详情</span>
    </div>
<form action="" method="post" id="stroe_list_form">
    <input type="hidden" value="${cityId }" name="cityId" id="cityId"/>
    <input type="hidden" value="${city }" name="city" id="city"/>
    <input type="hidden" value="${staff._id }" name="staffId" id="staffId"/>
    <input type="hidden" value="${longitude}" name="longitude" id="longitude"/>
    <input type="hidden" value="${latitude }" name="latitude" id="latitude"/>
    <input type="hidden" value="" id="pages"/>
    <input type="hidden" value="" id="page"/>
    <input type="hidden" value="" id="followCount"/>
    <input type="hidden" value="" id="organ_type"/>
    <input type="hidden" value="distance" id="distance"/>
    <input type="hidden" value="" id="districtId"/>
    <input type="hidden" value="" id="district"/>
    <input type="hidden" value="" id="region"/>
</form>
<ul class="rent_head">
    <li><span>合作模式</span>&nbsp;&nbsp;<img src="${ctxPath}/module/resources/images/home/icon_aorrw_down.png"></li>
    <li id="distance_type" class="distance_sort"><span>距离排序</span>&nbsp;&nbsp;<img src="${ctxPath}/module/resources/images/home/icon_aorrw_down.png"></li>
    <li>所在区域&nbsp;&nbsp;<img src="${ctxPath}/module/resources/images/home/icon_aorrw_down.png"></li>
</ul>
<ul class="rent_list" id="rent_list">

    <%--<li >--%>
        <%--<div class="dianpu"><img src="../images/img/header.jpg"></div>--%>
        <%--<div class="zhong clear">--%>
            <%--<h3>速度快放假啊会计师的</h3>--%>
            <%--<div class="gongwei">20 个橘黄色的工位可租赁</div>--%>
            <%--<div class="dizhi">昌平 回龙观上去</div>--%>
            <%--<div class="moshi"></div>--%>
            <%--<div class="juli clear">距离1.8km</div>--%>
        <%--</div>--%>
        <%--<div><span>￥</span><p>300</p><span>/天</span></div>--%>
    <%--</li>--%>
    <%--<li >--%>
        <%--<div class="dianpu"><img src="../images/img/header.jpg"></div>--%>
        <%--<div>--%>
            <%--<h3>速度快放假啊会计师的</h3>--%>
            <%--<div class="gongwei">20 个橘黄色的工位可租赁</div>--%>
            <%--<div class="dizhi">昌平 回龙观上去</div>--%>
            <%--<div class="moshi"></div>--%>
            <%--<div class="juli"><span>距离1.8km</span><p>￥300<span>/天</span></p></div>--%>
        <%--</div>--%>
    <%--</li>--%>
</ul>
<!--modal select list  -->
<div class="com_back_bg"></div>
<div class="select_nav" style="top: 6.34rem;">
    <div class="area_m">
        <div id="wrapper_area">
            <div id="scroller1">
                <ul class="area_state">
                    <li class="active">所有区域<input type="hidden" value="all" /><input type="hidden" value="所有区域" /></li>
                    <c:forEach items="${map}" var="item">
                        <c:if test="${item.key=='district' }">
                            <c:forEach items="${item.value}" var="d">
                                <li>${d.name }
                                    <input type="hidden" value="${d._id }" />
                                    <input type="hidden" value="${d.name }"/>
                                </li>
                            </c:forEach>
                        </c:if>
                    </c:forEach>
                </ul>
            </div>
        </div>
        <div id="wrapper2_area">
            <div id="scroller2" class="test">
                <ul id="region_sel" >
                    <c:forEach items="${map}" var="item">
                        <c:if test="${item.key=='region' }">
                            <c:forEach items="${item.value}" var="d">
                                <li>${d.name }
                                    <input type="hidden" value="${d._id }" />
                                    <input type="hidden" value="${d.name }" />
                                    <i></i>
                                </li>
                            </c:forEach>
                        </c:if>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </div>
</div>
<div class="coll" style="display: none;">
        <ul>
            <li class="active" id="suoyou">所有模式</li>
            <li id="fen">分账模式</li>
            <li id="zu">租金模式</li>
        </ul>
    </div>
<div class="sort_rent" style="display: none;">
    <ul>
        <li id="up_sort">升序</li>
        <li id="down_sort">降序</li>
    </ul>
</div>


<script src="${ctxPath}/module/resources/js/iscroll.js"></script>
<script src="${ctxPath}/module/staff/rent/js/store.js"></script>
<%--<script src="${ctxPath}/module/resources/js/userStore.js"></script>--%>
<script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
<script>
    var myScroll;
    var myScroll2;
    function loaded() {
        myScroll = new IScroll('#wrapper_area', { checkDOMChanges: true,vScrollbar:false,hScrollbar:false });
        myScroll2 = new IScroll('#wrapper2_area', { checkDOMChanges: true,vScrollbar:false,hScrollbar:false  });

    }
    loaded();
    $(document).ready(function(){
        var myScroll;
        var myScroll2;
        myScroll = new IScroll('#wrapper_area', { click:true, checkDOMChanges: true,vScrollbar:false,hScrollbar:false });
        myScroll2 = new IScroll('#wrapper2_area', { click:true,checkDOMChanges: true,vScrollbar:false,hScrollbar:false  });
        $('#region_sel li').click(function() {
                $(this).addClass('se_active').siblings().removeClass('se_active');
                var id=$(this).children("input:eq(0)").val() ;
                var name=$(this).children("input:eq(1)").val();
                $('.rent_head li').eq(2).html(name + '&nbsp;&nbsp;<img src='+_ctxPath+'/module/resources/images/home/icon_aorrw_down.png>');
                $("#region").val(name);
                $('.select_nav').fadeOut('fast');
                $('.com_back_bg ').fadeOut('fast');
                organList(1);

        });
        
        $(".com_back_bg").click(function () {
            $('.com_back_bg ').fadeOut('fast');
            $('.select_nav').slideUp('fast');
            $(".coll").slideUp();
            $(".sort_rent").slideUp();
        });
        
        $(".sort_rent ul li").click(function () {
            $(this).siblings().removeClass('active');
            $(this).addClass('active');
            $('.com_back_bg ').fadeOut('fast');
            $(".sort_rent").slideUp();
            $("#distance_type>span:first-child").html($(this).html()).addClass('jiantou');
        });

        var flagc=0;
        $('.rent_head li').eq(2).click(function () {
            if(flagc==0){
                $('#img1').attr("src",_ctxPath+'/module/resources/images/home/icon_aorrw_down.png');
                $('#img2').attr("src",_ctxPath+'/module/resources/images/home/icon_aorrw_down_pre.png');
                $('.com_back_bg ').fadeIn('fast');
                $('.select_nav').fadeIn('fast');
                $('.area_m').fadeIn('fast');
                $(".coll").css("display","none");
                $(".sort_rent").css("display","none");
                myScroll.refresh();
                myScroll.enable();
                myScroll2.refresh();
                myScroll2.enable();
                flagc=1;
            }else {
                $('.com_back_bg ').fadeOut('fast');
                $('.area_m').fadeOut('fast');
                flagc=0;
            }


            //flagec
        });
        $('.area_state li').click(function(){
            $(this).addClass('active').siblings().removeClass('active');
            var id=$(this).children("input:eq(0)").val() ;
            var name=$(this).children("input:eq(1)").val();
            if(id!="all"){
                $("#district").val(name);
                var cityId=$("#cityId").val();
                $.post(_ctxPath + "/w/organ/districtList",{'cityId':cityId,'districtId':id},
                    function(data){
                        var obj=eval(data);
                        $("#region_sel").html("");
                        for(var i=0;i<obj.region.length;i++){
                            var html='<li>'+obj.region[i].name+'<input type="hidden" value="'+obj.region[i]._id+'" />'+
                                '<input type="hidden" value="'+obj.region[i].name+'" /><i></i></li>';
                            $("#region_sel").html($("#region_sel").html()+html);
                        }
                        $('#region_sel li').click(function() {
                                $(this).addClass('se_active').siblings().removeClass('se_active');
                                var id=$(this).children("input:eq(0)").val() ;
                                var name=$(this).children("input:eq(1)").val();
                                $('.rent_head li').eq(2).html(name + '&nbsp;&nbsp;<img src='+_ctxPath+'/module/resources/images/home/icon_aorrw_down.png>');
                                $("#region").val(name);
                                $('.select_nav').fadeOut('fast');
                                $('.com_back_bg ').fadeOut('fast');
                                $('.area_m').fadeOut('fast');
                                organList(1);
                        });

                    },"json");//这里返回的类型有：json,html,xml,text
            }else{
                $("#district").val("");
                $("#region").val("");
                $('.rent_head li').eq(2).html(name + '&nbsp;&nbsp;<img src='+_ctxPath+'/module/resources/images/home/icon_aorrw_down.png>');
                $('.select_nav').fadeOut('fast');
                $('.com_back_bg ').fadeOut('fast');
                organList(1);
            }
            //select_nav

        });

        var flaga=0;
        $(".rent_head>li:first-child").click(function () {
            if(flaga==0){
                $(".coll").slideDown();
                $(".com_back_bg").fadeIn();
                $(".select_nav").css("display","none");
                $(".sort_rent").css("display","none");
                flaga=1;
            }else {
                $('.com_back_bg ').fadeOut('fast');
                $(".coll").slideUp();
                flaga=0;

            }

        });

        var flagb=0;
        $(".rent_head>li:nth-child(2)").click(function () {
            if(flagb==0){
                $(".sort_rent").slideDown();
                $(".com_back_bg").fadeIn();
                $(".select_nav").css("display","none");
                $(".coll").css("display","none");
                flagb=1;
            }else {
                $('.com_back_bg ').fadeOut('fast');
                $(".sort_rent").slideUp();
                flagb=0;
            }

        });
        $(".coll ul li").click(function () {
            //$(".coll ul li").removeClass('active');
            $(this).siblings().removeClass("active");
            $(this).addClass('active');
            $('.com_back_bg ').fadeOut('fast');
            $(".coll").slideUp();
            $(".rent_head>li:first-child>span:first-child").html($(this).html());
        });

    });

</script>

</body>

</html>