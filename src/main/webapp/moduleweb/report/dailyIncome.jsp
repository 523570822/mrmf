<%@ page language="java" pageEncoding="utf-8" %>
<%@ include file="/moduleweb/resources/common.jsp" %>
<c:set var="organId"
	   value="${param.organId == null?sessionScope.organId:param.organId}"/>
<html>
<head>
	<title></title>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
	<div class="row">
		<div class="col-sm-12">
			<div class="ibox ">
				<div class="ibox-content">
					<form id="searchForm" method="get" class="form-horizontal">
						<input type="hidden" value="0" id="queryType" name="queryType">
						<div class="form-group">
							<div class="col-sm-2">
								<input id="startTime" name="startTime"
									   class="laydate-icon form-control layer-date"
									   placeholder="起始日期" data-mask="9999-99-99"
									   laydate="{format : 'YYYY-MM-DD',min : '1938-06-16 23:59:59'}">
							</div>
							<div class="col-sm-2">
								<input id="endTime" name="endTime"
									   class="laydate-icon form-control layer-date"
									   placeholder="结束日期" data-mask="9999-99-99"
									   laydate="{format : 'YYYY-MM-DD',min : '1938-06-16 23:59:59'}">
							</div>
							<div class="col-sm-1">
								<button id="search" class="btn btn-primary" type="submit">查询</button>
							</div>
						</div>
					</form>
					<div class="row">
						<div class="col-sm-6">普通会员记录：</div>
						<div class="col-sm-6">会员详细消费记录：</div>
					</div>
					<div class="row">
						<div class="col-sm-6">
							<div class="jqGrid_wrapper">
								<table id="upTable"></table>
							</div>
						</div>
						<div class="col-sm-6">
							<div class="jqGrid_wrapper">
								<table id="upCardTable"></table>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-2">
							挂账的总计:<span id="userGuazhangTotal">0</span>元
						</div>
						<div class="col-sm-4">
							现金:<span id="userXianjin">0</span>元&nbsp;&nbsp;代金券:<span
								id="userDaijinquan">0</span>元&nbsp;&nbsp;银行卡:<span
								id="userYinhangka">0</span>元
						</div>

						<div class="col-sm-6">
							挂账的总计:<span id="userCardGuazhangTotal">0</span>元
						</div>
					</div>
					<div class="row">
						<div class="col-sm-2">
							大活总计:<span id="userDahuoTotal">0</span>元
						</div>
						<div class="col-sm-3">
							小活总计:<span id="userXiaohuoTotal">0</span>元
						</div>
						<div class="col-sm-1">
							<button id="export1" class="btn btn-primary export" type="button" t="1">导出</button>
						</div>
						<div class="col-sm-3">
							大活总计:<span id="userCardDahuoTotal">0</span>元
						</div>
						<div class="col-sm-2">
							小活总计:<span id="userCardXiaohuoTotal">0</span>元
						</div>
						<div class="col-sm-1">
							<button id="export2" class="btn btn-primary export" type="button" t="2">导出</button>
						</div>
					</div>
					<div class="hr-line-dashed"></div>
					<div class="row">
						<div class="col-sm-6">会员办卡记录：</div>
						<div class="col-sm-6">折扣卡消费记录：</div>
					</div>
					<div class="row">
						<div class="col-sm-6">
							<div class="jqGrid_wrapper">
								<table id="upCardNewTable"></table>
							</div>
						</div>
						<div class="col-sm-6" style="padding: 0px">
							<div class="col-sm-6" style="padding-right: 5px">
								<div class="jqGrid_wrapper">
									<table id="upCardZhekouTable"></table>
								</div>
							</div>
							<div class="col-sm-6" style="padding-left: 5px">
								<div class="jqGrid_wrapper">
									<table id="zengsongTable"></table>
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-3">
							办卡总计:<span id="bankaTotal">0</span>元&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;挂账:<span
								id="bankaGuazhangTotal">0</span>
						</div>
						<div class="col-sm-2">
							办卡总数:<span id="bankaCount">0</span>张
						</div>
						<div class="col-sm-1" style="padding-top: 15px">
							<button id="export3" class="btn btn-primary export" type="button" t="3">导出</button>
						</div>
						<div class="col-sm-3">
							现金:<span id="zhekouXianjinTotal">0</span>元
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							免单:<span id="zhekouMiandanTotal">0</span>元
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							挂账:<span id="zhekouGuazhangTotal">0</span>元
						</div>
						<div class="col-sm-2">
							赠送:<span id="zengsongTotal">0</span>元
						</div>
						<div class="col-sm-1" style="padding-top: 15px">
							<button id="export4" class="btn btn-primary export" type="button" t="4">导出</button>
						</div>
					</div>
					<div class="hr-line-dashed"></div>
					<div class="row">
						<div class="col-sm-6">会员续费记录：</div>
						<div class="col-sm-6">外卖记录：</div>
					</div>
					<div class="row">
						<div class="col-sm-6">
							<div class="jqGrid_wrapper">
								<table id="upCardXufeiTable"></table>
							</div>
						</div>
						<div class="col-sm-6">
							<div class="jqGrid_wrapper">
								<table id="upWaimaiTable"></table>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-2">
							共:<span id="xufeiTotal">0</span>元
						</div>
						<div class="col-sm-3">
							银行卡:<span id="xufeiYinhang">0</span>元 &nbsp;&nbsp;现金:<span
								id="xufeiXianjin">0</span>元
						</div>
						<div class="col-sm-1" style="padding-top: 15px">
							<button id="export5" class="btn btn-primary export" type="button" t="5">导出</button>
						</div>
						<div class="col-sm-2">
							共:<span id="waimaiTotal">0</span>元&nbsp;&nbsp;卡消费:<span
								id="waimaiCardTotal">0</span>元
						</div>
						<div class="col-sm-3">
							挂账:<span id="waimaiGuazhang">0</span>元&nbsp;&nbsp;卡挂账:<span
								id="waimaiCardGuazhang">0</span>元
						</div>
						<div class="col-sm-1" style="padding-top: 15px">
							<button id="export6" class="btn btn-primary export" type="button" t="6">导出</button>
						</div>
					</div>
					<div class="hr-line-dashed"></div>
					<div class="row">
						<div class="col-sm-2">
							收入:<span id="totalShouru">0</span>元
						</div>
						<div class="col-sm-2">
							挂账:<span id="totalGuazhang">0</span>元
						</div>
						<div class="col-sm-2">
							免单总数:<span id="totalMiandanCount">0</span>次
						</div>
						<div class="col-sm-2">
							免单总计:<span id="totalMiandan">0</span>元
						</div>
						<div class="col-sm-2">
							会员消费:<span id="totalHuiyuan">0</span>元
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<form id="export" method="post"  action="${ctxPath}/dailyIncome/exportUser.do" class="form-horizontal">
		<input type="hidden" id="content" name="content">
	</form>
</div>
<script type="text/javascript">
    $()
        .ready(
            function () {
                $("#startTime").val(new Date().format());

                var totalShouru = totalGuazhang = totalMiandanCount = totalMiandan = totalHuiyuan = 0;
                var userGuazhangTotal = userXianjin = userDaijinquan = userYinhangka = userDahuoTotal = userXiaohuoTotal = 0;
                var userCardGuazhangTotal = userCardDahuoTotal = userCardXiaohuoTotal = 0;
                var zhekouXianjinTotal, zhekouGuazhangTotal = 0;
                var zengsongTotal = 0;
                var bankaTotal = bankaGuazhangTotal = bankaCount = 0;
                var xufeiTotal = xufeiYinhang = xufeiXianjin = 0;
                var waimaiTotal = waimaiGuazhang = waimaiNotCardTotal = waimaiNotCardGuazhang = 0;

                function showTotal() {
                    totalShouru = userDahuoTotal + userXiaohuoTotal
                        - userGuazhangTotal
							/* + userCardDahuoTotal
							 + userCardXiaohuoTotal
							 - userCardGuazhangTotal */
                        + zhekouXianjinTotal
                        - zhekouGuazhangTotal + bankaTotal
                        - bankaGuazhangTotal + xufeiTotal
                        + waimaiNotCardTotal
                        - waimaiNotCardGuazhang
					-totalMiandan;
                    totalGuazhang = userGuazhangTotal
                        + userCardGuazhangTotal
                        + zhekouGuazhangTotal
                        + bankaGuazhangTotal + waimaiGuazhang;
                    totalHuiyuan = userCardDahuoTotal
                        + userCardXiaohuoTotal
                        - userCardGuazhangTotal
                        + waimaiTotal
                        - waimaiNotCardTotal
                        - (waimaiGuazhang - waimaiNotCardGuazhang);
                    $("#totalShouru").text(totalShouru.toFixed(2));
                    $("#totalGuazhang").text(
                        totalGuazhang.toFixed(2));
                    $("#totalMiandanCount").text(totalMiandanCount);
                    //18-2-8发现问题 次数卡免单会算进消费里，而不算入免单金额，由于totalMiandan 会加入现金计算，所以新加次数卡免单金额 来把次数卡免单记录下来
                    $("#totalMiandan")
                        .text((totalMiandan+userCardMiandan+waimaiIsCard+cishuKaMiandan).toFixed(2));
                    $("#totalHuiyuan")
                        .text((totalHuiyuan-cishuKaMiandan).toFixed(2));
                }

                function loadAll() {
                    var startTime = $("#startTime").val()
                        .parseDate();
                    var endTime = $("#endTime").val().parseDate();
                    if (!startTime) {
                        toastr.warning("开始日期不能为空");
                        $("#startTime").focus();
                        return;
                    }
                    if (!endTime) {
                        endTime = new Date().format().parseDate();
                    }
                    if (startTime - endTime === 0) {
                    } else if (startTime > endTime) {
                        toastr.warning("开始日期需早于结束日期");
                        $("#endTime").focus();
                        return;
                    } else {
                        var ok = false, maxDays = 400;
                        for (var i = 1; i <= maxDays; i++) { // 400天之内
                            var sd = new Date(startTime.getTime())
                                .addDate(i);
                            if (sd - endTime === 0) {
                                ok = true;
                                break;
                            }
                        }
                        if (!ok) {
                            toastr.warning("开始日期与结束日期间隔不能超过"
                                + maxDays + "天");
                            $("#endTime").focus();
                            return;
                        }
                    }

                    totalShouru = totalGuazhang = totalMiandanCount = totalMiandan = totalHuiyuan = 0;
                    // -------------------------加载普通会员记录------------------------
                    $
                        .post(
                            "${ctxPath}/dailyIncome/queryUser.do",
                            $("#searchForm").formobj(),
                            function (data) {
                                $("#upTable").jqGrid(
                                    'clearGridData');
                                var ms = fillmaps.smallsorts;
                                userGuazhangTotal = userXianjin = userDaijinquan = userYinhangka = userDahuoTotal = userXiaohuoTotal = 0;
                                if (data) {
                                    for (var i = 0; i < data.length; i++) {
                                        var d = data[i];
                                        userXianjin += d.money_cash;
                                        userDaijinquan += d.money_li_money;
                                        userYinhangka += d.money_yinhang_money;
                                        if (d.guazhang_flag) {
                                            var money1 =d.money1-d.money5;
                                            userGuazhangTotal += money1;
                                        }
                                        if (d.miandan) {
                                            totalMiandanCount++;
                                            totalMiandan += d.miandanMoney;
                                        }
                                        for (var j = 0; j < ms.length; j++) {
                                            if (ms[j]._id == d.smallsort) {
                                                //成交金额减去 抹零金额
                                                var money1 =d.money1-d.money5;
                                                if (ms[j].charge_flag)
                                                    userXiaohuoTotal += money1;
                                                else
                                                    userDahuoTotal += money1;
                                                break;
                                            }
                                        }
                                        $("#upTable")
                                            .jqGrid(
                                                'addRowData',
                                                i,
                                                d);
                                    }
                                }
                                $("#userGuazhangTotal")
                                    .text(
                                        userGuazhangTotal
                                            .toFixed(2));
                                $("#userXianjin")
                                    .text(
                                        userXianjin
                                            .toFixed(2));
                                $("#userDaijinquan")
                                    .text(
                                        userDaijinquan
                                            .toFixed(2));
                                $("#userYinhangka")
                                    .text(
                                        userYinhangka
                                            .toFixed(2));
                                $("#userDahuoTotal")
                                    .text(
                                        userDahuoTotal
                                            .toFixed(2));
                                $("#userXiaohuoTotal")
                                    .text(
                                        userXiaohuoTotal
                                            .toFixed(2));
                                showTotal();
                            });

                    // -------------------------加载会员详细消费记录------------------------
                    $
                        .post(
                            "${ctxPath}/dailyIncome/queryUserCard.do",
                            $("#searchForm").formobj(),
                            function (data) {
                                $("#upCardTable").jqGrid(
                                    'clearGridData');
                                $("#upCardZhekouTable")
                                    .jqGrid(
                                        'clearGridData');
                                var ms = fillmaps.smallsorts;
                                var us = fillmaps.usersorts;
                                userCardGuazhangTotal = userCardDahuoTotal = userCardXiaohuoTotal = userCardMiandan = zhekouXianjinTotal=cishuKaMiandan=zhekouMiandanTotal= 0;
                                var m = 0;
                                if (data) {
                                    for (var i = 0; i < data.length; i++) {
                                        var d = data[i];
                                        if (d.miandan) {
                                            totalMiandanCount++;
                                            userCardMiandan += d.miandanMoney;
                                        }
										/*totalHuiyuan += d.money_xiaofei
										 + d.money2;*/

                                        if (d.usersortType == '1001') { // 单纯打折卡
                                            $(
                                                "#upCardZhekouTable")
                                                .jqGrid(
                                                    'addRowData',
                                                    m,
                                                    d);
                                            zhekouXianjinTotal += d.money1-d.money5;
                                            if(!d.miandan){
                                                totalShouru = d.money2;
                                            }else {
                                                totalMiandan+= d.money1-d.money5;
                                                zhekouMiandanTotal += d.money1-d.money5;
											}
                                            if (d.guazhang_flag) {
                                                zhekouGuazhangTotal += d.money2;
                                            }
                                            m++;
                                        } else {
                                            var xf = d.money_xiaofei;
                                            if (d.usersortType == "1003"){
                                                xf = d.danci_money
                                                    * d.cishu;
                                                //次数卡免单 不计入会员消费，计入免单
												if(d.miandan){
                                                    cishuKaMiandan =d.danci_money
                                                        * d.cishu;
												}
											}
                                            if (d.guazhang_flag) {
                                                userCardGuazhangTotal += xf;
                                            }


                                            // 会员消费大活、小活统计
                                            for (var j = 0; j < ms.length; j++) {
                                                if (ms[j]._id == d.smallsort) {
                                                    if (ms[j].charge_flag)
                                                        userCardXiaohuoTotal += xf;
                                                    else
                                                        userCardDahuoTotal += xf;

                                                    break;
                                                }
                                            }
                                            $(
                                                "#upCardTable")
                                                .jqGrid(
                                                    'addRowData',
                                                    i,
                                                    d);
                                        }
                                    }


                                }
                                $("#userCardGuazhangTotal")
                                    .text(
                                        userCardGuazhangTotal
                                            .toFixed(2));
                                $("#userCardDahuoTotal")
                                    .text(
                                        userCardDahuoTotal
                                            .toFixed(2));
                                $("#userCardXiaohuoTotal")
                                    .text(
                                        userCardXiaohuoTotal
                                            .toFixed(2));
                                $("#zhekouXianjinTotal")
                                    .text(
										(zhekouXianjinTotal-zhekouMiandanTotal)
                                            .toFixed(2));
                                $("#zhekouMiandanTotal")
                                    .text(
                                        zhekouMiandanTotal
                                            .toFixed(2));
                                $("#zhekouGuazhangTotal")
                                    .text(
                                        zhekouGuazhangTotal
                                            .toFixed(2));
                                showTotal();
                            });

                    // -------------------------加载赠送记录------------------------
                    $
                        .post(
                            "${ctxPath}/dailyIncome/queryZengsong.do",
                            $("#searchForm").formobj(),
                            function (data) {
                                $("#zengsongTable").jqGrid(
                                    'clearGridData');
                                var ms = fillmaps.smallsorts;
                                zengsongTotal = 0;
                                if (data) {
                                    for (var i = 0; i < data.length; i++) {
                                        var d = data[i];
                                        zengsongTotal += d.song_money;
                                        $("#zengsongTable")
                                            .jqGrid(
                                                'addRowData',
                                                i,
                                                d);
                                    }
                                }
                                $("#zengsongTotal")
                                    .text(
                                        zengsongTotal
                                            .toFixed(2));
                            });

                    // -------------------------加载会员办卡记录------------------------
                    $
                        .post(
                            "${ctxPath}/dailyIncome/queryUserCardNew.do",
                            $("#searchForm").formobj(),
                            function (data) {
                                $("#upCardNewTable")
                                    .jqGrid(
                                        'clearGridData');
                                var ms = fillmaps.smallsorts;
                                bankaTotal = bankaCount = 0;
                                if (data) {
                                    bankaCount = data.length;
                                    for (var i = 0; i < data.length; i++) {
                                        var d = data[i];
                                        if (d.guazhang_flag) {
                                            bankaGuazhangTotal += d.money1;
                                        }
                                        if (d.miandan) {
                                            totalMiandanCount++;
                                            totalMiandan += d.miandanMoney;
                                        }
                                        bankaTotal += d.money2;
                                        $("#upCardNewTable")
                                            .jqGrid(
                                                'addRowData',
                                                i,
                                                d);
                                    }
                                }
                                $("#bankaTotal")
                                    .text(
                                        bankaTotal
                                            .toFixed(2));
                                $("#bankaGuazhangTotal")
                                    .text(
                                        bankaGuazhangTotal
                                            .toFixed(2));
                                $("#bankaCount").text(
                                    bankaCount);
                                showTotal();
                            });

                    // -------------------------加载会员续费记录------------------------
                    $
                        .post(
                            "${ctxPath}/dailyIncome/queryUserCardXufei.do",
                            $("#searchForm").formobj(),
                            function (data) {
                                $("#upCardXufeiTable")
                                    .jqGrid(
                                        'clearGridData');
                                var ms = fillmaps.smallsorts;
                                xufeiTotal = xufeiYinhang = xufeiXianjin = 0;
                                if (data) {
                                    for (var i = 0; i < data.length; i++) {
                                        var d = data[i];
                                        xufeiTotal += d.money2;
                                        xufeiXianjin += d.money_cash;
                                        xufeiYinhang += d.money_yinhang_money;
                                        $(
                                            "#upCardXufeiTable")
                                            .jqGrid(
                                                'addRowData',
                                                i,
                                                d);
                                    }
                                }
                                $("#xufeiTotal")
                                    .text(
                                        xufeiTotal
                                            .toFixed(2));
                                $("#xufeiYinhang")
                                    .text(
                                        xufeiYinhang
                                            .toFixed(2));
                                $("#xufeiXianjin")
                                    .text(
                                        xufeiXianjin
                                            .toFixed(2));
                                showTotal();
                            });

                    // -------------------------加载外卖记录------------------------
                    $
                        .post(
                            "${ctxPath}/dailyIncome/queryWaimai.do",
                            $("#searchForm").formobj(),
                            function (data) {
                                $("#upWaimaiTable").jqGrid(
                                    'clearGridData');
                                waimaiTotal = 0;
                                waimaiGuazhang = 0;
                                waimaiNotCardTotal = 0;
                                waimaiNotCardGuazhang = 0;
                                waimaiIsCard = 0;
                                if (data) {
                                    for (var i = 0; i < data.length; i++) {
                                        var d = data[i];
                                        waimaiTotal += d.money1;
                                        if (!d.isCard)
                                            waimaiNotCardTotal += d.money1;
                                        if (d.guazhang_flag) {
                                            waimaiGuazhang += d.money1;
                                            if (!d.isCard)
                                                waimaiNotCardGuazhang += d.money1;
                                        }
                                        if(d.miandan){
                                            totalMiandanCount++;
                                            if(d.isCard){
                                                 waimaiIsCard +=d.money1;
											}else {
                                                totalMiandan +=d.money1;
											}
										}
                                        $("#upWaimaiTable")
                                            .jqGrid(
                                                'addRowData',
                                                i,
                                                d);
                                    }
                                }
                                $("#waimaiTotal")
                                    .text(
                                        waimaiTotal
                                            .toFixed(2));
                                $("#waimaiGuazhang")
                                    .text(
                                        waimaiGuazhang
                                            .toFixed(2));
                                $("#waimaiCardTotal")
                                    .text(
                                        (waimaiTotal - waimaiNotCardTotal)
                                            .toFixed(2));
                                $("#waimaiCardGuazhang")
                                    .text(
                                        (waimaiGuazhang - waimaiNotCardGuazhang)
                                            .toFixed(2));
                                showTotal();
                            });
                }

                $("#searchForm").submit(function () {
                    loadAll();
                    return false;
                });
                $("#upTable")
                    .grid(
                        {
                            colNames: ["应交款额", "实交款额","抹零金额",
                                "挂账", "现金", "代金券",
                                "银行卡", "服务明细", "服务人员1",
                                "服务人员1提成", "服务人员2",
                                "服务人员2提成", "会员类型",
                                "会员姓名", "光临日期",
                                "服务人员3", "服务人员3提成",
                                "免单"],
                            shrinkToFit: false,
                            datatype: "local",
                            pager: null,
                            height: 200,
                            colModel: [
                                {
                                    name: "money1",
                                    index: "money1",
                                    width: 70
                                },
                                {
                                    name: "money2",
                                    index: "money2",
                                    width: 70
                                },
                                {
                                    name: "money5",
                                    index: "money5",
                                    width: 70
                                },
                                {
                                    name: "guazhang_flag",
                                    index: "guazhang_flag",
                                    align: "center",
                                    width: 60,
                                    formatter: function (cellvalue,
                                                         options,
                                                         rowObject) {
                                        if (cellvalue)
                                            return "<font color='red'>是</font>";
                                        else
                                            return "否";
                                    }
                                },
                                {
                                    name: "money_cash",
                                    index: "money_cash",
                                    align: "center",
                                    width: 60
                                },
                                {
                                    name: "money_li_money",
                                    index: "money_li_money",
                                    align: "center",
                                    width: 60
                                },
                                {
                                    name: "money_yinhang_money",
                                    index: "money_yinhang_money",
                                    align: "center",
                                    width: 60
                                },
                                {
                                    name: "smallsort",
                                    sortable: false,
                                    index: "smallsort",
                                    formatter: function (cellvalue) {
                                        var ms = fillmaps.smallsorts;
                                        for (var i = 0; i < ms.length; i++) {
                                            if (ms[i]._id == cellvalue) {
                                                return ms[i].name;
                                            }
                                        }
                                        return "无";
                                    },
                                    width: 120
                                },
                                {
                                    name: "staffId1",
                                    sortable: false,
                                    index: "staffId1",
                                    formatter: function (cellvalue) {
                                        var ms = fillmaps.staffs;
                                        for (var i = 0; i < ms.length; i++) {
                                            if (ms[i]._id == cellvalue) {
                                                return ms[i].name;
                                            }
                                        }
                                        return "无";
                                    },
                                    width: 80
                                },
                                {
                                    name: "somemoney1",
                                    sortable: false,
                                    index: "somemoney1",
                                    width: 100
                                },
                                {
                                    name: "staffId2",
                                    sortable: false,
                                    index: "staffId2",
                                    formatter: function (cellvalue) {
                                        var ms = fillmaps.staffs;
                                        for (var i = 0; i < ms.length; i++) {
                                            if (ms[i]._id == cellvalue) {
                                                return ms[i].name;
                                            }
                                        }
                                        return "无";
                                    },
                                    width: 80
                                },
                                {
                                    name: "somemoney2",
                                    sortable: false,
                                    index: "somemoney2",
                                    width: 100
                                },
                                {
                                    name: "membersort",
                                    sortable: false,
                                    index: "membersort",
                                    formatter: function (cellvalue) {
                                        var ms = fillmaps.usersorts;
                                        for (var i = 0; i < ms.length; i++) {
                                            if (ms[i]._id == cellvalue) {
                                                return ms[i].name1;
                                            }
                                        }
                                        return "无";
                                    },
                                    width: 120
                                },
                                {
                                    name: "name",
                                    index: "name",
                                    align: "center",
                                    width: 70
                                },
                                {
                                    name: "createTime",
                                    sortable: false,
                                    index: "createTime"
                                },
                                {
                                    name: "staffId3",
                                    sortable: false,
                                    index: "staffId3",
                                    formatter: function (cellvalue) {
                                        var ms = fillmaps.staffs;
                                        for (var i = 0; i < ms.length; i++) {
                                            if (ms[i]._id == cellvalue) {
                                                return ms[i].name;
                                            }
                                        }
                                        return "无";
                                    },
                                    width: 80
                                },
                                {
                                    name: "somemoney3",
                                    sortable: false,
                                    index: "somemoney3",
                                    width: 100
                                },
                                {
                                    name: "miandan",
                                    index: "miandan",
                                    align: "center",
                                    width: 60,
                                    formatter: function (cellvalue,
                                                         options,
                                                         rowObject) {
                                        if (cellvalue)
                                            return "是";
                                        else
                                            return "否";
                                    }
                                }]
                        });

                $("#upCardTable")
                    .grid(
                        {
                            colNames: ["消费额", "消费次数",
                                "挂账", "是否子卡", "服务明细",
                                "服务人员1", "服务人员1提成",
                                "服务人员2", "服务人员2提成",
                                "会员类型", "卡表面号", "会员姓名",
                                "光临日期", "服务人员3",
                                "服务人员3提成", "免单", "其他付款"],
                            shrinkToFit: false,
                            datatype: "local",
                            pager: null,
                            height: 200,
                            colModel: [
                                {
                                    name: "money_xiaofei",
                                    index: "money_xiaofei",
                                    width: 70,
                                    formatter: function (cellvalue,
                                                         options,
                                                         rowObject) {
                                        if (rowObject.money5 != 0)
                                            return cellvalue;
                                        else if (rowObject.usersortType == "1003")
                                            return rowObject.danci_money
                                                * rowObject.cishu;
                                        else
                                            return cellvalue;
                                    }
                                },
                                {
                                    name: "cishu",
                                    index: "cishu",
                                    width: 70,
                                    formatter: function (cellvalue,
                                                         options,
                                                         rowObject) {
                                        if (rowObject.usersortType != "1003")
                                            return "";
                                        else
                                            return cellvalue;
                                    }
                                },
                                {
                                    name: "guazhang_flag",
                                    index: "guazhang_flag",
                                    align: "center",
                                    width: 60,
                                    formatter: function (cellvalue,
                                                         options,
                                                         rowObject) {
                                        if (cellvalue)
                                            return "<font color='red'>是</font>";
                                        else
                                            return "否";
                                    }
                                },
                                {
                                    name: "type",
                                    index: "type",
                                    width: 80,
                                    formatter: function (cellvalue,
                                                         options,
                                                         rowObject) {
                                        if (cellvalue == 11)
                                            return "是";
                                        else
                                            return "否";
                                    }
                                },
                                {
                                    name: "smallsort",
                                    sortable: false,
                                    index: "smallsort",
                                    formatter: function (cellvalue) {
                                        var ms = fillmaps.smallsorts;
                                        for (var i = 0; i < ms.length; i++) {
                                            if (ms[i]._id == cellvalue) {
                                                return ms[i].name;
                                            }
                                        }
                                        return "无";
                                    },
                                    width: 120
                                },
                                {
                                    name: "staffId1",
                                    sortable: false,
                                    index: "staffId1",
                                    formatter: function (cellvalue) {
                                        var ms = fillmaps.staffs;
                                        for (var i = 0; i < ms.length; i++) {
                                            if (ms[i]._id == cellvalue) {
                                                return ms[i].name;
                                            }
                                        }
                                        return "无";
                                    },
                                    width: 80
                                },
                                {
                                    name: "somemoney1",
                                    sortable: false,
                                    index: "somemoney1",
                                    width: 100
                                },
                                {
                                    name: "staffId2",
                                    sortable: false,
                                    index: "staffId2",
                                    formatter: function (cellvalue) {
                                        var ms = fillmaps.staffs;
                                        for (var i = 0; i < ms.length; i++) {
                                            if (ms[i]._id == cellvalue) {
                                                return ms[i].name;
                                            }
                                        }
                                        return "无";
                                    },
                                    width: 80
                                },
                                {
                                    name: "somemoney2",
                                    sortable: false,
                                    index: "somemoney2",
                                    width: 100
                                },
                                {
                                    name: "membersort",
                                    sortable: false,
                                    index: "membersort",
                                    formatter: function (cellvalue) {
                                        var ms = fillmaps.usersorts;
                                        for (var i = 0; i < ms.length; i++) {
                                            if (ms[i]._id == cellvalue) {
                                                return ms[i].name1;
                                            }
                                        }
                                        return "无";
                                    },
                                    width: 120
                                },
                                {
                                    name: "cardno",
                                    index: "cardno",
                                    width: 100
                                },
                                {
                                    name: "name",
                                    index: "name",
                                    align: "center",
                                    width: 70
                                },
                                {
                                    name: "createTime",
                                    sortable: false,
                                    index: "createTime"
                                },
                                {
                                    name: "staffId3",
                                    sortable: false,
                                    index: "staffId3",
                                    formatter: function (cellvalue) {
                                        var ms = fillmaps.staffs;
                                        for (var i = 0; i < ms.length; i++) {
                                            if (ms[i]._id == cellvalue) {
                                                return ms[i].name;
                                            }
                                        }
                                        return "无";
                                    },
                                    width: 80
                                },
                                {
                                    name: "somemoney3",
                                    sortable: false,
                                    index: "somemoney3",
                                    width: 100
                                },
                                {
                                    name: "miandan",
                                    index: "miandan",
                                    align: "center",
                                    width: 60,
                                    formatter: function (cellvalue,
                                                         options,
                                                         rowObject) {
                                        if (cellvalue)
                                            return "是";
                                        else
                                            return "否";
                                    }
                                },
                                {
                                    name: "money_other_money",
                                    index: "money_other_money",
                                    width: 70
                                }]
                        });

                $("#upCardNewTable")
                    .grid(
                        {
                            colNames: ["应交款额", "实交款额",
                                "赠送金额", "挂账", "现金",
                                "代金券", "银行卡", "服务明细",
                                "服务人员1", "服务人员1提成",
                                "服务人员2", "服务人员2提成",
                                "会员类型", "会员姓名", "光临日期",
                                "服务人员3", "服务人员3提成",
                                "免单"],
                            shrinkToFit: false,
                            datatype: "local",
                            pager: null,
                            height: 200,
                            colModel: [
                                {
                                    name: "money1",
                                    index: "money1",
                                    width: 70
                                },
                                {
                                    name: "money2",
                                    index: "money2",
                                    width: 70
                                },
                                {
                                    name: "nowSong_money",
                                    index: "nowSong_money",
                                    width: 70
                                },
                                {
                                    name: "guazhang_flag",
                                    index: "guazhang_flag",
                                    align: "center",
                                    width: 60,
                                    formatter: function (cellvalue,
                                                         options,
                                                         rowObject) {
                                        if (cellvalue)
                                            return "<font color='red'>是</font>";
                                        else
                                            return "否";
                                    }
                                },
                                {
                                    name: "money_cash",
                                    index: "money_cash",
                                    align: "center",
                                    width: 60
                                },
                                {
                                    name: "money_li_money",
                                    index: "money_li_money",
                                    align: "center",
                                    width: 60
                                },
                                {
                                    name: "money_yinhang_money",
                                    index: "money_yinhang_money",
                                    align: "center",
                                    width: 60
                                },
                                {
                                    name: "smallsort",
                                    sortable: false,
                                    index: "smallsort",
                                    formatter: function (cellvalue) {
                                        var ms = fillmaps.smallsorts;
                                        for (var i = 0; i < ms.length; i++) {
                                            if (ms[i]._id == cellvalue) {
                                                return ms[i].name;
                                            }
                                        }
                                        return "无";
                                    },
                                    width: 120
                                },
                                {
                                    name: "staffId1",
                                    sortable: false,
                                    index: "staffId1",
                                    formatter: function (cellvalue) {
                                        var ms = fillmaps.staffs;
                                        for (var i = 0; i < ms.length; i++) {
                                            if (ms[i]._id == cellvalue) {
                                                return ms[i].name;
                                            }
                                        }
                                        return "无";
                                    },
                                    width: 80
                                },
                                {
                                    name: "somemoney1",
                                    sortable: false,
                                    index: "somemoney1",
                                    width: 100
                                },
                                {
                                    name: "staffId2",
                                    sortable: false,
                                    index: "staffId2",
                                    formatter: function (cellvalue) {
                                        var ms = fillmaps.staffs;
                                        for (var i = 0; i < ms.length; i++) {
                                            if (ms[i]._id == cellvalue) {
                                                return ms[i].name;
                                            }
                                        }
                                        return "无";
                                    },
                                    width: 80
                                },
                                {
                                    name: "somemoney2",
                                    sortable: false,
                                    index: "somemoney2",
                                    width: 100
                                },
                                {
                                    name: "membersort",
                                    sortable: false,
                                    index: "membersort",
                                    formatter: function (cellvalue) {
                                        var ms = fillmaps.usersorts;
                                        for (var i = 0; i < ms.length; i++) {
                                            if (ms[i]._id == cellvalue) {
                                                return ms[i].name1;
                                            }
                                        }
                                        return "无";
                                    },
                                    width: 120
                                },
                                {
                                    name: "name",
                                    index: "name",
                                    align: "center",
                                    width: 70
                                },
                                {
                                    name: "createTime",
                                    sortable: false,
                                    index: "createTime"
                                },
                                {
                                    name: "staffId3",
                                    sortable: false,
                                    index: "staffId3",
                                    formatter: function (cellvalue) {
                                        var ms = fillmaps.staffs;
                                        for (var i = 0; i < ms.length; i++) {
                                            if (ms[i]._id == cellvalue) {
                                                return ms[i].name;
                                            }
                                        }
                                        return "无";
                                    },
                                    width: 80
                                },
                                {
                                    name: "somemoney3",
                                    sortable: false,
                                    index: "somemoney3",
                                    width: 100
                                },
                                {
                                    name: "miandan",
                                    index: "miandan",
                                    align: "center",
                                    width: 60,
                                    formatter: function (cellvalue,
                                                         options,
                                                         rowObject) {
                                        if (cellvalue)
                                            return "是";
                                        else
                                            return "否";
                                    }
                                }]
                        });

                $("#upCardZhekouTable")
                    .grid(
                        {
                            colNames: ["卡号", "会员姓名",
                                "会员类型", "服务明细",
                                "单价", "折后价格", "光临日期",
                                "服务人员1", "挂账"],
                            shrinkToFit: false,
                            datatype: "local",
                            pager: null,
                            height: 200,
                            colModel: [
                                {
                                    name: "id_2",
                                    index: "id_2",
                                    width: 100
                                },
                                {
                                    name: "name",
                                    index: "name",
                                    align: "center",
                                    width: 70
                                },
//														{
//															name : "sex",
//															index : "sex",
//															align : "center",
//															formatter : function(
//																	cellvalue) {
//																if (!cellvalue)
//																	return "未知";
//																else
//																	return cellvalue;
//															},
//															width : 60
//														},
                                {
                                    name: "membersort",
                                    sortable: false,
                                    index: "membersort",
                                    formatter: function (cellvalue) {
                                        var ms = fillmaps.usersorts;
                                        for (var i = 0; i < ms.length; i++) {
                                            if (ms[i]._id == cellvalue) {
                                                return ms[i].name1;
                                            }
                                        }
                                        return "无";
                                    },
                                    width: 120
                                },
                                {
                                    name: "smallsort",
                                    sortable: false,
                                    index: "smallsort",
                                    formatter: function (cellvalue) {
                                        var ms = fillmaps.smallsorts;
                                        for (var i = 0; i < ms.length; i++) {
                                            if (ms[i]._id == cellvalue) {
                                                return ms[i].name;
                                            }
                                        }
                                        return "无";
                                    },
                                    width: 120
                                },
                                {
                                    name: "smallsort",
                                    index: "smallsort",
                                    align: "center",
                                    formatter: function (cellvalue) {
                                        var ms = fillmaps.smallsorts;
                                        for (var i = 0; i < ms.length; i++) {
                                            if (ms[i]._id == cellvalue) {
                                                return ms[i].price;
                                            }
                                        }
                                        return "0";
                                    },
                                    width: 60
                                },
                                {
                                    name: "money2",
                                    index: "money2",
                                    align: "center",
                                    formatter: function (cellvalue, options, rowObject) {
                                        return (rowObject.money1 - rowObject.money5);
                                    },
                                    width: 70
                                },
                                {
                                    name: "createTime",
                                    sortable: false,
                                    index: "createTime"
                                },
                                {
                                    name: "staffId1",
                                    sortable: false,
                                    index: "staffId1",
                                    formatter: function (cellvalue) {
                                        var ms = fillmaps.staffs;
                                        for (var i = 0; i < ms.length; i++) {
                                            if (ms[i]._id == cellvalue) {
                                                return ms[i].name;
                                            }
                                        }
                                        return "无";
                                    },
                                    width: 80
                                },
                                {
                                    name: "guazhang_flag",
                                    index: "guazhang_flag",
                                    align: "center",
                                    width: 60,
                                    formatter: function (cellvalue,
                                                         options,
                                                         rowObject) {
                                        if (cellvalue)
                                            return "<font color='red'>是</font>";
                                        else
                                            return "否";
                                    }
                                }]
                        });

                $("#zengsongTable").grid(
                    {
                        colNames: ["赠送金额", "日期", "会员姓名",
                            "会员卡号", "实缴金额"],
                        shrinkToFit: false,
                        datatype: "local",
                        pager: null,
                        height: 200,
                        colModel: [{
                            name: "song_money",
                            index: "song_money",
                            width: 100
                        }, {
                            name: "createTime",
                            sortable: false,
                            index: "createTime",
                            width: 80
                        }, {
                            name: "name",
                            index: "name",
                            align: "center",
                            width: 70
                        }, {
                            name: "id_2",
                            index: "id_2",
                            width: 100
                        }, {
                            name: "money2",
                            index: "money2",
                            width: 80
                        }]
                    });

                $("#upCardXufeiTable")
                    .grid(
                        {
                            colNames: ["续费额", "银行卡",
                                "现金", "会员姓名", "会员类型",
                                "续费日期", "提成"],
                            shrinkToFit: false,
                            datatype: "local",
                            pager: null,
                            height: 200,
                            colModel: [
                                {
                                    name: "money2",
                                    index: "money2",
                                    width: 70
                                },
                                {
                                    name: "money_yinhang_money",
                                    index: "money_yinhang_money",
                                    align: "center",
                                    width: 60
                                },
                                {
                                    name: "money_cash",
                                    index: "money_cash",
                                    align: "center",
                                    width: 60
                                },
                                {
                                    name: "name",
                                    index: "name",
                                    align: "center",
                                    width: 70
                                },
                                {
                                    name: "membersort",
                                    sortable: false,
                                    index: "membersort",
                                    formatter: function (cellvalue) {
                                        var ms = fillmaps.usersorts;
                                        for (var i = 0; i < ms.length; i++) {
                                            if (ms[i]._id == cellvalue) {
                                                return ms[i].name1;
                                            }
                                        }
                                        return "无";
                                    },
                                    width: 120
                                },
                                {
                                    name: "createTime",
                                    sortable: false,
                                    index: "createTime"
                                },
                                {
                                    name: "somemoney1",
                                    sortable: false,
                                    index: "somemoney1",
                                    width: 60
                                }]
                        });

                $("#upWaimaiTable")
                    .grid(
                        {
                            colNames: ["物品名称", "规格",
                                "卡消费", "单价", "数量",
                                "总价", "售出日期", "售出人员",
                                "购买人", "售出人员2", "业绩1",
                                "业绩2", "免单", "是否挂账"],
                            shrinkToFit: false,
                            datatype: "local",
                            pager: null,
                            height: 200,
                            colModel: [
                                {
                                    name: "wupinName",
                                    index: "wupinName"
                                },
                                {
                                    name: "guige",
                                    width: 80,
                                    index: "guige"
                                },
                                {
                                    name: "isCard",
                                    index: "isCard",
                                    width: 60,
                                    formatter: function (cellvalue,
                                                         options,
                                                         rowObject) {
                                        if (cellvalue)
                                            return "是";
                                        else
                                            return "否";
                                    }
                                },
                                {
                                    name: "money2",
                                    width: 80,
                                    index: "money2"
                                },
                                {
                                    name: "num",
                                    width: 80,
                                    index: "num"
                                },
                                {
                                    name: "money1",
                                    width: 80,
                                    index: "money1"
                                },
                                {
                                    name: "createTime",
                                    index: "createTime"
                                },
                                {
                                    name: "staffId1",
                                    sortable: false,
                                    width: 100,
                                    index: "staffId1",
                                    formatter: function (cellvalue) {
                                        var ms = fillmaps.staffs;
                                        for (var i = 0; i < ms.length; i++) {
                                            if (ms[i]._id == cellvalue) {
                                                return ms[i].name;
                                            }
                                        }
                                        return "无";
                                    }
                                },
                                {
                                    name: "buyname",
                                    width: 100,
                                    index: "buyname"
                                },
                                {
                                    name: "staffId2",
                                    sortable: false,
                                    index: "staffId2",
                                    width: 100,
                                    formatter: function (cellvalue) {
                                        var ms = fillmaps.staffs;
                                        for (var i = 0; i < ms.length; i++) {
                                            if (ms[i]._id == cellvalue) {
                                                return ms[i].name;
                                            }
                                        }
                                        return "无";
                                    }
                                },
                                {
                                    name: "yeji1",
                                    width: 60,
                                    index: "yeji1"
                                },
                                {
                                    name: "yeji2",
                                    width: 60,
                                    index: "yeji2"
                                },
                                {
                                    name: "miandan",
                                    index: "miandan",
                                    width: 60,
                                    formatter: function (cellvalue,
                                                         options,
                                                         rowObject) {
                                        if (cellvalue)
                                            return "是";
                                        else
                                            return "否";
                                    }
                                },
                                {
                                    name: "guazhang_flag",
                                    index: "guazhang_flag",
                                    width: 70,
                                    formatter: function (cellvalue,
                                                         options,
                                                         rowObject) {
                                        if (cellvalue)
                                            return "<font color='red'>是</font>";
                                        else
                                            return "否";
                                    }
                                }]
                        });
                $("#searchForm").submit();
            });
    $(".export")
        .click(
            function() {
                var t = $(this).attr("t");
                $("#queryType").val(t);
                var o = $("#searchForm")
                    .formobj();
                searchCriteria = {};
                for ( var key in o) {
                    searchCriteria[key] = o[key];
                }
                var downloadstr = "";
                $("#export").html("");
//                var input1 = $("<input>");
//                input1.attr("type", "hidden");
//                input1
//                    .attr("name",
//                        "ex");
//                input1.attr("value", t);
//                $("#export").append(input1);
                //$("body").append(form);//将表单放置在web中

                for ( var key in searchCriteria) {
                    var input1 = $("<input>");
                    input1.attr("type",
                        "hidden");
                    input1.attr("name", key);
                    input1
                        .attr(
                            "value",
                            searchCriteria[key]);
                    $("#export").append(input1);
                }
                $("#export").submit();
            });

</script>
</body>
</html>
