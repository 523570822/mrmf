<%@ page language="java" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	function prt() {
		var myDoc = {
			documents : document,
			settings : {
				<c:if test="${organSetting.printerName != \"\"}">
				printer:'${organSetting.printerName}',
				</c:if>
				topMargin : 10,
				leftMargin : 0,
				bottomMargin : 0,
				rightMargin : 0
			},
			copyrights : '杰创软件拥有版权  www.jatools.com'
		};

		<c:if test="${organSetting.printPreview}">
			document.getElementById("jatoolsPrinter").printPreview(myDoc); // 打印预览
		</c:if>
		<c:if test="${!organSetting.printPreview}">
			for(var i=0;i<${organSetting.printCount};i++) {
				document.getElementById("jatoolsPrinter").print(myDoc, false); // 不弹出对话框打印
			}
		</c:if>
			//document.getElementById("jatoolsPrinter").print(myDoc, true); // 打印前弹出打印设置对话框
	}
</script>
</head>
<body style="margin: 0; padding: 0;">
	<div id='page1' style='margin: 0 auto;'>
		<div style="font-size: 14px; text-align: center;">${organ.name }</div>
		<div style="font-size: 10px; text-align: center; margin-top: 6px;">${date }</div>
		<div
			style="font-size: 10px; text-align: right; margin-top: 6px; width: 160px;">
			${xiaopiaoCode}</div>
		<div style="font-size: 10px; text-align: left; margin-top: 6px;">单号:${kaidan.kaidan_id }</div>
		<c:set var="total" value="0" />
		<c:set var="moling" value="0" />
		<c:set var="shijiao" value="0" />
		<c:set var="cash" value="0" />
		<c:set var="li" value="0" />
		<c:set var="zhaoling" value="0" />
		<c:forEach items="${userparts}" var="userpart" varStatus="status">
			<c:set var="flag1" value="" />
			<c:forEach items="${usersorts}" var="usersort">
				<c:if test="${userpart.membersort == usersort._id }">
					<div style="font-size: 10px; text-align: left; margin-top: 6px;">${usersort.name1}</div>
					<c:set var="flag1" value="${usersort.flag1}" />
				</c:if>
			</c:forEach>
			<c:if test="${flag1 != null && flag1 == '1000'}">
				<%-- 非会员消费 --%>
				<c:forEach items="${smallsorts}" var="smallsort">
					<c:if test="${userpart.smallsort == smallsort._id }">
						<div style="font-size: 10px; text-align: right; margin-top: 6px;">${smallsort.name}</div>
					</c:if>
				</c:forEach>
			</c:if>
			<c:if test="${flag1 != null && flag1 != '1000'}">
				<%-- 会员办卡 --%>
				<div style="font-size: 10px; text-align: right; margin-top: 6px;">办卡</div>
			</c:if>
			<c:forEach items="${staffs}" var="staff">
				<c:if test="${userpart.staffId1 == staff._id }">
					<div
						style="font-size: 10px; text-align: left; margin-top: 6px; margin-left: 10px">${staff.name}</div>
				</c:if>
			</c:forEach>
			<c:set var="total" value="${total +  userpart.money1}" />
			<c:set var="moling" value="${moling +  userpart.money5}" />
			<c:set var="shijiao" value="${shijiao +  userpart.money2}" />
			<c:set var="cash" value="${userpart.money_cash}" />
			<c:set var="li" value="${ userpart.money_li_money}" />
			<c:set var="zhaoling" value="${userpart.money3}" />
			<div
				style="font-size: 10px; text-align: center; margin-top: 6px; width: 30px; float: left;">折扣</div>
			<div
				style="font-size: 10px; text-align: center; margin-top: 6px; width: 70px; float: left;">抹零</div>
			<div
				style="font-size: 10px; text-align: right; margin-top: 6px; width: 60px; float: left;">应收款额</div>
			<div
				style="font-size: 10px; text-align: center; margin-top: 6px; width: 30px; float: left;">${userpart.money6 }</div>
			<div
				style="font-size: 10px; text-align: center; margin-top: 6px; width: 70px; float: left;">${userpart.money5 }</div>
			<div
				style="font-size: 10px; text-align: right; margin-top: 6px; width: 60px; float: left;">${userpart.money1 }</div>

			<c:if test="${!status.last}">
				<div
					style="font-size: 2px; float: left; width: 100%; margin-top: 3px; border-top: 1px dashed #000000;"></div>
			</c:if>
		</c:forEach>
		<c:forEach items="${waimais}" var="waimai" varStatus="status">

			<div
				style="font-size: 10px; text-align: left; margin-top: 6px; margin-left: 10px">${waimai.wupinName}</div>

			<c:set var="total" value="${total +  waimai.money1}" />

			<div
				style="font-size: 10px; text-align: center; margin-top: 6px; width: 30px; float: left;">单价</div>
			<div
				style="font-size: 10px; text-align: center; margin-top: 6px; width: 70px; float: left;">数量</div>
			<div
				style="font-size: 10px; text-align: right; margin-top: 6px; width: 60px; float: left;">应交款额</div>
			<div
				style="font-size: 10px; text-align: center; margin-top: 6px; width: 30px; float: left;">${waimai.money2 }</div>
			<div
				style="font-size: 10px; text-align: center; margin-top: 6px; width: 70px; float: left;">${waimai.num }</div>
			<div
				style="font-size: 10px; text-align: right; margin-top: 6px; width: 60px; float: left;">${waimai.money1 }</div>

			<c:if test="${!status.last}">
				<div
					style="font-size: 2px; float: left; width: 100%; margin-top: 3px; border-top: 1px dashed #000000;"></div>
			</c:if>
		</c:forEach>
		<div
			style="font-size: 2px; float: left; width: 100%; margin-top: 10px; border-top: 1px solid #000000;"></div>

		<div
			style="font-size: 10px; text-align: center; margin-top: 6px; width: 50px; float: left;">总计</div>
		<div
			style="font-size: 10px; text-align: center; margin-top: 6px; width: 75px; float: left;">实交现金</div>
		<div
			style="font-size: 10px; text-align: right; margin-top: 6px; width: 35px; float: left;">抹零</div>
		<div
			style="font-size: 10px; text-align: center; margin-top: 6px; width: 50px; float: left;">${total }</div>
		<div
			style="font-size: 10px; text-align: center; margin-top: 6px; width: 75px; float: left;">${shijiao }</div>
		<div
			style="font-size: 10px; text-align: right; margin-top: 6px; width: 35px; float: left;">${moling }</div>
		<div
			style="font-size: 10px; text-align: center; margin-top: 6px; width: 50px; float: left;">实收现金</div>
		<div
			style="font-size: 10px; text-align: center; margin-top: 6px; width: 75px; float: left;">实交代金券</div>
		<div
			style="font-size: 10px; text-align: right; margin-top: 6px; width: 35px; float: left;">找零</div>
		<div
			style="font-size: 10px; text-align: center; margin-top: 6px; width: 50px; float: left;">${cash }</div>
		<div
			style="font-size: 10px; text-align: center; margin-top: 6px; width: 75px; float: left;">${li }</div>
		<div
			style="font-size: 10px; text-align: right; margin-top: 6px; width: 35px; float: left;">${zhaoling }</div>
		<c:if
			test="${organSetting.printNote != null && organSetting.printNote != ''}">
			<div
				style="font-size: 10px; text-align: left; margin-top: 16px; width: 100%; float: left; line-height: 16px;">
				${organSetting.printNote}</div>
		</c:if>
	</div>
	<OBJECT ID="jatoolsPrinter"
		CLASSID="CLSID:B43D3361-D075-4BE2-87FE-057188254255"
		codebase="jatoolsPrinter.cab#version=8,6,0,0"></OBJECT>
	<script type="text/javascript">
		prt();
	</script>
</body>
</html>
