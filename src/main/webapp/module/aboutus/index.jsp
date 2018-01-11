<%@ page language="java" contentType="text/html; charset=UTF-8"
           pageEncoding="UTF-8"%>
<%@ include file="/module/resources/wecommon.jsp"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <meta charset="UTF-8">
	<meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <meta content="email=no" name="format-detection">
    <meta name="description" content="This is  a ..">
    <title>任性猫</title>
     <link rel="stylesheet" href="${ctxPath}/module/resources/css/my_style.css"/>
    <link rel="stylesheet" href="${ctxPath}/module/aboutus/aboutus.css"/>
</head>

<body>
	<div class="common_nav">
        <h4 class="font-34 h4_line">关于我们</h4>
    </div>
    
    <div class="txt_wrap">
		<h4><i></i></h4>
		<p class="text p_text">任性猫（北京）科技有限责任公司 是一家集应用app微营销系统平台开发及运营、企业方案咨询服务为主体的产业化运作的专业性的高新技术企业。公司立足于为客户提供专业化、全方位的管理信息化建设服务；同时面向团体市场、专业市场和消费市场，提供专业化、标准化、规范化、产业化的app微营销系统平台开发及运营、企业咨询等综合服务</p>
        <p class="text p_text">我们以“发展智能管理软件，追求管理创新”为宗旨，为客户提供美容美发O2O服务平台、专业、快捷、全面地帮助客户形成核心竞争能力，为智能管理的美业传播，永不停歇。</p>
        <p class="text p_text">我们高素质的人才队伍对于企业的发展起着决定性的作用。硕士、博士和特、高级技术人员等构成了公司的主要骨干力量。为我们的技术开发、售前售后服务奠定了坚实的基础。为推动公司国际化、信息化进程的发展，拓宽合作领域，公司先后与众多国际知名行业组织达成合作，不断完善系统设计的内核架构，为我们的客户提供高质量的解决方案。</p>
        <h2><i></i></h2>
        <p class="text p_text">我们将一如既往的为中国美业信息化建设添砖加瓦。</p>
        <img src="${showqrcode}" style="margin-left: 25%;margin-top: 0%;" width="50%" height="50%"/>
        <p class="text p_text" style="margin-left: 10%;margin-top: 0%;">扫码关注任性猫美业互联网</p>
        <p class="text p_text company_profile">客服电话:&nbsp;<span>400-088-2224</span></p>
        <p class="company_profile text p_text">公司地址:&nbsp;<span>北京市西城区黄寺大街23号北广大厦15F</span></p>

    </div>
    <script src="${ctxPath}/module/resources/js/media_contrl.js"></script>
</body>