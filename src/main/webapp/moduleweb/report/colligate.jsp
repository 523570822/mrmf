<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/moduleweb/resources/common.jsp"%>
<c:set var="organId"
	   value="${param.organId == null?sessionScope.organId:param.organId}" />
<html>
<head>
	<title></title>
	<style type="text/css">
		td {
			padding: 4px 30px 4px 4px;
			word-break: keep-all;
		}

		.tdr {
			text-align: right;
			padding-left: 30px;
			padding-right: 0px;
		}
	</style>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
	<div class="row">
		<div class="col-sm-12">
			<div class="ibox ">
				<div class="ibox-content">
					<form id="searchForm" method="get" class="form-horizontal">
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
							<div class="col-sm-4">
								<button id="search" class="btn btn-primary" type="submit">查询</button>
								<!-- 									<button id="download" class="btn btn-outline btn-danger" type="button">导出</button> -->
							</div>
						</div>
					</form>
					<div id='page1' attaches="pdf#a.pdf" class="row"
						 style="overflow: auto;">
						<table border="1">
							<tr bgcolor="#cdffff">
								<td class="tdr">现金业绩：</td>
								<td id="xjyj">0</td>
								<td class="tdr" rowspan="2">总金额：</td>
								<td id="zje" rowspan="2">0</td>
								<td class="tdr" rowspan="2">总人数：</td>
								<td id="zrs" rowspan="2">0</td>
								<td rowspan="2" style="padding: 0px;"><table border="1">
									<tr>
										<td class="tdr" width="120">指定男客：</td>
										<td id="zdnank">0</td>
									</tr>
									<tr>
										<td class="tdr">指定女客：</td>
										<td id="zdnvk">0</td>
									</tr>
									<tr>
										<td class="tdr">非指定男客：</td>
										<td id="fzdnank">0</td>
									</tr>
									<tr>
										<td class="tdr">非指定女客：</td>
										<td id="fzdnvk">0</td>
									</tr>
								</table></td>
								<td rowspan="2" style="padding: 0px;"><table border="1">
									<tr>
										<td class="tdr" width="120">劝男客：</td>
										<td id="qnank">0</td>
									</tr>
									<tr>
										<td class="tdr">劝女客：</td>
										<td id="qnvk">0</td>
									</tr>
									<tr>
										<td class="tdr">非劝男客：</td>
										<td id="fqnank">0</td>
									</tr>
									<tr>
										<td class="tdr">非劝女客：</td>
										<td id="fqnvk">0</td>
									</tr>
								</table></td>
								<td class="tdr" rowspan="2">总划卡业绩：</td>
								<td id="zhkyj" rowspan="2">0</td>
								<td class="tdr">劳动业绩：</td>
								<td id="ldyj">0</td>
                                <td class="tdr">免单业绩：</td>
                                <td id="mdyj">0</td>
							</tr>
							<tr bgcolor="#cdffff">
								<td class="tdr">划卡业绩：</td>
								<td id="skyj">0</td>
								<td class="tdr">劳动业绩累计：</td>
								<td id="ldyjlj">0</td>
							</tr>
							<tr bgcolor="#fdfd38">
								<td class="tdr">现金累计：</td>
								<td id="xjlj">0</td>
								<td class="tdr" rowspan="2">累计总金额：</td>
								<td id="ljzje" rowspan="2">0</td>
								<td class="tdr" rowspan="2">累计总人数：</td>
								<td id="ljzrs" rowspan="2">0</td>
								<td rowspan="2" style="padding: 0px;"><table border="1">
									<tr>
										<td class="tdr" width="120">女客累计：</td>
										<td id="nvklj"></td>
									</tr>
									<tr>
										<td class="tdr">男客累计：</td>
										<td id="nanklj"></td>
									</tr>
								</table></td>
								<td class="tdr" rowspan="2">累计划卡：</td>
								<td id="ljhk" rowspan="2">0</td>
								<td></td>
								<td></td>
							</tr>
							<tr bgcolor="#fdfd38">
								<td class="tdr">划卡累计：</td>
								<td id="sklj">0</td>
								<td></td>
								<td></td>
							</tr>

							<tr id="smname" bgcolor="#fccc9f">
								<td colspan="2" style="text-align: center;">外卖</td>
								<td colspan="6" style="text-align: center;">会员卡</td>
							</tr>
							<tr id="smyeji" bgcolor="#cdffff">
								<td class="tdr">外卖客数：</td>
								<td id="wmks">0</td>
								<td class="tdr">新开卡金额：</td>
								<td id="xkkje">0</td>
								<td class="tdr">续卡金额：</td>
								<td id="xkje">0</td>
								<td class="tdr" rowspan="2">总卡金：</td>
								<td id="zkj" rowspan="2">0</td>
							</tr>
							<tr id="smcount" bgcolor="#cdffff">
								<td class="tdr">外卖业绩：</td>
								<td id="wmyj">0</td>
								<td class="tdr">新开卡客数：</td>
								<td id="xkkks">0</td>
								<td class="tdr">续卡客数：</td>
								<td id="xkks">0</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
    $()
        .ready(
            function() {
                $.ajaxSetup({
                    async : false
                });
                $("#download")
                    .click(
                        function() {
                            var startTime = $("#startTime")
                                .val().parseDate();
                            var endTime = $("#endTime")
                                .val().parseDate();
                            if (!startTime) {
                                toastr.warning("开始日期不能为空");
                                $("#startTime").focus();
                                return;
                            }
                            if (!endTime) {
                                endTime = new Date()
                                    .format()
                                    .parseDate();
                            }
                            if (startTime - endTime === 0) {
                            } else if (startTime > endTime) {
                                toastr
                                    .warning("开始日期需早于结束日期");
                                $("#endTime").focus();
                                return;
                            } else {
                                var ok = false, maxDays = 400;
                                for (var i = 1; i <= maxDays; i++) { // 400天之内
                                    var sd = new Date(
                                        startTime
                                            .getTime())
                                        .addDate(i);
                                    if (sd - endTime === 0) {
                                        ok = true;
                                        break;
                                    }
                                }
                                if (!ok) {
                                    toastr
                                        .warning("开始日期与结束日期间隔不能超过"
                                            + maxDays
                                            + "天");
                                    $("#endTime").focus();
                                    return;
                                }
                            }

                            window.location.href = _ctxPath
                                + "/colligateReport/download.do?startTime="
                                + $("#startTime").val()
                                + "&endTime="
                                + $("#endTime").val();

                        });

                $("#startTime").val(new Date().format());

                function loadAll() {
                    //外卖业绩  免单外卖业绩  外卖人数  外卖划卡业绩
                    var wmyj =0;
                    var mdwmyj=0;
                    var wmrs=0;
                    var wmhkyj=0;
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

                    $.shade.show();

                    // -------------------------加载外卖记录------------------------
                    $
                        .post(
                            "${ctxPath}/colligateReport/queryWaimai.do",
                            $("#searchForm").formobj(),
                            function(data) {
                                var ks = [], waimaiYejiTotal = 0;
                                if (data) {
                                    for (var i = 0; i < data.length; i++) {
                                        var d = data[i];
                                        if(d.miandan){
                                            mdwmyj += d.money1;
                                        }
                                        if(d.isCard){
                                            wmhkyj += d.money1;
										}
                                        waimaiYejiTotal += d.money1;
                                        if (!ks
                                                .contains(d.kaidanId)) {
                                            ks
                                                .push(d.kaidanId);
                                           wmrs+=1;
                                        }
                                    }
                                }
                                wmyj = waimaiYejiTotal;
                                $("#wmks").text(ks.length);
                                $("#wmyj").text(waimaiYejiTotal);
                            });

                    // -------------------------加载全部消费记录------------------------
                    $
                        .post(
                            "${ctxPath}/colligateReport/query.do",
                            $("#searchForm").formobj(),
                            function(data) {
                                $.shade.hide();
                                var xjyj = skyj = zje = zrs = zdnank = zdnvk = fzdnank = fzdnvk = qnank = qnvk = fqnank = fqnvk = zhkyj = ldyj = ldyjlj = xjlj = sklj = ljzje = ljzrs = nanklj = nvklj = ljhk = xkkje = xkkks = xkje = xkks = zkj =mdyj= 0
                                var smallsorts = {}, smallsortsCount = {};
                                var ms = fillmaps.smallsorts;
                                var bs = fillmaps.bigsorts;
                                if (data) {
                                    for (var i = 0; i < data.length; i++) {
                                        var d = data[i];

                                        if(d.miandan){
                                            mdyj += d.yeji1;
                                            mdyj += d.yeji2;
                                            mdyj += d.yeji3;
                                        }


                                        if(!d.miandan){
                                            xjyj += d.money2;
                                            if(d.usersortType==1003){
                                                if(d.type==0){
                                                    zje += d.money2;
												}else {
                                                    skyj += d.danci_money;
                                                    zhkyj += d.danci_money;
												}
                                            }else if(d.usersortType==1002){
                                                zhkyj += d.money_xiaofei-d.money5;
                                                skyj += d.money_xiaofei-d.money5;
                                                if(d.type==0){
                                                    zje += d.money2
                                                        + d.money_xiaofei-d.money5;
												}
                                            }else if(d.usersortType==1001){
                                                zhkyj += d.money_xiaofei-d.money5;
                                                skyj += d.money_xiaofei-d.money5;
                                               if(d.type==0){
                                                   zje += d.money1 -d.money5;
											   }
											}else {//非会员消费   续费，补欠费计算
                                                if(d.type==0||d.type==4||d.type==3){
                                                    zje += d.money1 -d.money5;

                                                }
											}
                                            zrs++;
                                            if (d.dian1
                                                || d.dian2
                                                || d.dian3) { // 点活
                                                if (d.sex == "男")
                                                    zdnank++;
                                                else
                                                    zdnvk++;
                                            } else { // 非点
                                                if (d.sex == "男")
                                                    fzdnank++;
                                                else
                                                    fzdnvk++;
                                            }
                                            if (d.quan1
                                                || d.quan2
                                                || d.quan3) { // 点活
                                                if (d.sex == "男")
                                                    qnank++;
                                                else
                                                    qnvk++;
                                            } else { // 非点
                                                if (d.sex == "男")
                                                    fqnank++;
                                                else
                                                    fqnvk++;
                                            }
                                        }
                                        if (d.smallsort) {
                                            if (!smallsorts[d.smallsort]) {
                                                smallsorts[d.smallsort] = 0;
                                            }
                                            if (!smallsortsCount[d.smallsort]) {
                                                smallsortsCount[d.smallsort] = 0;
                                            }
                                            if(!d.miandan){
                                                if (d.type == 0
                                                    && !d.incardId) { // 非会员消费
                                                    smallsorts[d.smallsort] += d.money2;
                                                    smallsortsCount[d.smallsort]++;
                                                } else if (d.type == 1) { // 会员卡消费
                                                    if(d.usersortType == "1003"){
                                                        smallsorts[d.smallsort] += d.danci_money;
                                                    }else {
                                                        smallsorts[d.smallsort] += d.money_xiaofei-d.money5;
                                                    }
                                                    smallsortsCount[d.smallsort]++;
                                                }
                                            }
                                        }

                                        if(!d.miandan){
                                            if (d.sex == "男")
                                                nanklj++;
                                            else
                                                nvklj++;

                                            ldyj += d.yeji1;
                                            ldyj += d.yeji2;
                                            ldyj += d.yeji3;
                                        }



                                        if (d.type == 0
                                            && d.incardId) { // 办卡
                                            xkkje += d.money2;
                                            xkkks++;
                                        }
                                        if ((d.type == 3)||(d.type==4)) { // 会员卡续费
                                            xkje += d.money2;
                                            xkks++;
                                        }
                                    }
                                }
                                //增加外卖人数和业绩 非会员外卖 加入到现金,业绩中
                                zrs = zrs+wmrs;
                                nvklj = nvklj+wmrs;
                                fqnvk = fqnvk+wmrs;
                                fzdnvk = fzdnvk+wmrs;
                                zhkyj += wmhkyj;
                                ldyj +=wmyj;
								skyj =skyj + wmhkyj;
                                mdyj = mdyj + mdwmyj;
                                xjyj =xjyj+wmyj-wmhkyj;
                                zje =zje+wmyj-wmhkyj;
                                xjyj = xjyj.toFixed(2);
                                skyj = skyj.toFixed(2);
                                zje = zje.toFixed(2);
                                ldyj = ldyj.toFixed(2);
                                zrs = zrs.toFixed(2);

                                mdyj = mdyj.toFixed(2);
                                xjlj = xjyj;
                                sklj = skyj;
                                ljzje = zje;
                                ljzrs = zrs;
                                //zhkyj = skyj;
                                ljhk = zhkyj.toFixed(2);
                                $("#mdyj").text(mdyj);
                                $("#xjyj").text(xjyj);
                                $("#skyj").text(skyj);
                                $("#zje").text(zje);
                                $("#zrs").text(zrs);
                                $("#zdnank").text(zdnank);
                                $("#zdnvk").text(zdnvk);
                                $("#fzdnank").text(fzdnank);
                                $("#fzdnvk").text(fzdnvk);
                                $("#qnank").text(qnank);
                                $("#qnvk").text(qnvk);
                                $("#fqnank").text(fqnank);
                                $("#fqnvk").text(fqnvk);
                                $("#zhkyj").text(zhkyj.toFixed(2));
                                $("#ldyj").text(ldyj);
                                $("#ldyjlj").text(ldyj);
                                $("#xjlj").text(xjlj);
                                $("#sklj").text(sklj);
                                $("#ljzje").text(ljzje);
                                $("#ljzrs").text(ljzrs);
                                $("#nvklj").text(nvklj);
                                $("#nanklj").text(nanklj);
                                $("#ljhk").text(ljhk);
//													$("#wmks").text(wmks);
//													$("#wmyj").text(wmyj);
                                $("#xkkje").text(xkkje);
                                $("#xkkks").text(xkkks);
                                $("#xkje").text(xkje);
                                $("#xkks").text(xkks);
                                $("#zkj").text(xkkje+xkje);

                                var bigcodeObj = {}, bigcodeCountObj = {};

                                for (var j = 0; j < ms.length; j++) {
                                    var ss = ms[j], je = smallsorts[ss._id], count = smallsortsCount[ss._id];
                                    //console.log(ss);
                                    if (!je)
                                        je = 0;
                                    if (!count)
                                        count = 0;

                                    if (!bigcodeObj[ss.bigcode]) {
                                        bigcodeObj[ss.bigcode] = 0;
                                    }
                                    if (!bigcodeCountObj[ss.bigcode]) {
                                        bigcodeCountObj[ss.bigcode] = 0;
                                    }

                                    bigcodeObj[ss.bigcode] += je;
                                    bigcodeCountObj[ss.bigcode] += count;
//														console
//																.log("bigcodeCountObj[ss.bigcode]"
//																		+ bigcodeCountObj[ss.bigcode]);
                                }

                                for (var j = 0; j < bs.length; j++) {
                                    var ss = bs[j], je = bigcodeObj[ss._id], count = bigcodeCountObj[ss._id];
                                    //console.log(ss);
                                    if (!je)
                                        je = 0;
                                    if (!count)
                                        count = 0;

                                    var jeEle = $("#yj_"
                                        + ss._id), countEle = $("#count_"
                                        + ss._id);
                                    je = je.toFixed(2);
                                    if (jeEle.length > 0) {
                                        jeEle.text(je);
                                        countEle
                                            .text(count);
                                    } else {
                                        $("#smname")
                                            .prepend(
                                                "<td colspan=\"2\" style=\"text-align: center;\">"
                                                + ss.name
                                                + "</td>");
                                        $("#smyeji")
                                            .prepend(
                                                "<td class=\"tdr\">"
                                                + ss.name
                                                + "业绩：</td><td id=\"yj_"+ss._id+"\">"
                                                + je
                                                + "</td>");
                                        $("#smcount")
                                            .prepend(
                                                "<td class=\"tdr\">"
                                                + ss.name
                                                + "客数：</td><td id=\"count_"+ss._id+"\">"
                                                + count
                                                + "</td>");
                                    }
                                }

                            });
                }

                $("#searchForm").submit(function() {
                    loadAll();
                    return false;
                });

                $("#searchForm").submit();
            });
</script>
</body>
</html>
