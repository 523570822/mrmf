<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/moduleweb/resources/common.jsp"%>
<html>
<head>
<title></title>
<style>
.form-control2 {
	width: 60%;
	display: inline;
}

.input-group .form-control2 {
	width: 60%;
	display: inline;
}

.center {
	text-align: center;
}

.leftPadding {
	padding-left: 40px;
}

.input-group {
	display: inline;
}
</style>
</head>
<body class="gray-bg">
	<div class="ibox float-e-margins">
		<div class="ibox-content">
			<div class="row">
				<div class="col-sm-12">
					<form id="incard" method="post" class="form-horizontal">
						<input type="hidden" id="organId" name="organId"
							value="${param.organId}"> <input type="hidden"
							id="userId" name="userId"><input type="hidden" id="flag2"
							name="flag2" value="false"> <input type="hidden"
							id="delete_flag" name="delete_flag" value="false"> <input
							type="hidden" id="guazhang_flag" name="guazhang_flag"
							value="true"> <input type="hidden" id="bigsort"
							name="bigsort"> <input type="hidden" id="cardId"
							name="cardId"><input type="hidden" id="id_2" name="id_2">
						<input type="hidden" id="money1" name="money1"><input
							type="hidden" id="money_xiaofei" name="money_xiaofei"> <input
							type="hidden" id="userpartId" name="userpartId">
						<div class="form-group">
							<label class="col-sm-1 control-label">卡表面号:</label>
							<div class="col-sm-2">
								<input id="cardno" name="cardno" type="text"
									class="form-control" minlength="2" maxlength="50" disabled>
							</div>
							<label class="col-sm-2 control-label">会员姓名:</label>
							<div class="col-sm-2">
								<input id="name" name="name" type="text" class="form-control"
									minlength="2" maxlength="50" disabled>
							</div>
							<label class="col-sm-2 control-label">会员类型:</label>
							<div class="col-sm-2">
								<select class="form-control" id="membersort" name="membersort"
									disabled>
									<option value="">请选择</option>
									<c:forEach items="${ffusersorts}" var="usersort">
										<option value="${usersort._id}">${usersort.name1}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-1 control-label">折扣:</label>
							<div class="col-sm-2">
								<input id="money6" name="money6" type="number" min="0"
									class="form-control" value="100" required>
							</div>
							<label class="col-sm-2 control-label zhekour">余 额:</label>
							<div class="col-sm-2 zhekour">
								<input id="money4" name="money4" type="number" min="0"
									class="form-control" disabled >
							</div>
							<label class="col-sm-2 control-label cishur">单次款额:</label>
							<div class="col-sm-2 cishur">
								<input id="danci_money" name="danci_money" type="number" min="0"
									class="form-control" disabled>
							</div>
							<label class="col-sm-1 control-label cishur">总 次 数:</label>
							<div class="col-sm-1 cishur">
								<input id="allcishu" name="allcishu" type="digits" min="0"
									class="form-control" value="0" disabled>
							</div>
							<label class="col-sm-1 control-label cishur">剩余次数:</label>
							<div class="col-sm-1 cishur">
								<input id="shengcishu" name="shengcishu" type="digits" min="0"
									class="form-control" value="0" disabled>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-1 control-label cishur">消费次数:</label>
							<div class="col-sm-2 cishur">
								<input id="cishu" name="cishu" type="digits" min="1"
									class="form-control" value="1">
							</div>
							<label class="col-sm-1 control-label">选择项目:</label>
							<div class="col-sm-2">
								<input type="text" class="form-control suggest" id="smallsort"
									name="smallsort"
									suggest="{data :fillmaps.smallsorts,style3:true}">
							</div>
							<label class="col-sm-2 control-label zhekour">折后价格:</label>
							<div class="col-sm-2 zhekour">
								<input id="price" name="price" type="number" min="0"
									class="form-control" disabled>
							</div>
							<label class="col-sm-2 control-label zhekour">抹零:</label>
							<div class="col-sm-2 zhekour">
								<input id="money5" name="money5" type="number" min="0"
									class="form-control">
							</div>
						</div>
						<div class="hr-line-dashed"></div>
						<span id="tichengRegion">
							<div class="form-group">
								<label class="col-sm-1 control-label">员工1:</label>
								<div class="col-sm-4">
									<input type="text" class="form-control form-control2 suggest"
										id="staffId1" name="staffId1"
										suggest="{data :fillmaps.staffs,style2:true}"> <span
										class="feihuiyuan"> <span class="feihuiyuan">&nbsp;&nbsp;<label
											class="control-label"><input id="dian1" name="dian1"
												type="checkbox">&nbsp;点</label>&nbsp;&nbsp;<label
											class="control-label"><input id="quan1" name="quan1"
												type="checkbox">&nbsp;劝</label></span>
								</div>
								<c:if test="${organSetting.displayTicheng}">
									<label class="col-sm-1 control-label">提成1:</label>
									<div class="col-sm-2">
										<input id="somemoney1" name="somemoney1" type="number" min="0"
											class="form-control">
									</div>
								</c:if>
								<c:if test="${!organSetting.displayTicheng}">
									<input id="somemoney1" name="somemoney1" type="hidden">
								</c:if>
								<label class="col-sm-1 control-label">业绩1:</label>
								<div class="col-sm-2">
									<input id="yeji1" name="yeji1" type="number" min="0"
										class="form-control">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-1 control-label">员工2:</label>
								<div class="col-sm-4">
									<input type="text" class="form-control form-control2 suggest"
										id="staffId2" name="staffId2"
										suggest="{data :fillmaps.staffs,style2:true}"> <span
										class="feihuiyuan"><span class="feihuiyuan">
											&nbsp;&nbsp;<label class="control-label"><input
												id="dian2" name="dian2" type="checkbox">&nbsp;点</label>&nbsp;&nbsp;<label
											class="control-label"><input id="quan2" name="quan2"
												type="checkbox">&nbsp;劝</label>
									</span>
								</div>
								<c:if test="${organSetting.displayTicheng}">
									<label class="col-sm-1 control-label">提成2:</label>
									<div class="col-sm-2">
										<input id="somemoney2" name="somemoney2" type="number" min="0"
											class="form-control">
									</div>
								</c:if>
								<c:if test="${!organSetting.displayTicheng}">
									<input id="somemoney2" name="somemoney2" type="hidden">
								</c:if>
								<label class="col-sm-1 control-label">业绩2:</label>
								<div class="col-sm-2">
									<input id="yeji2" name="yeji2" type="number" min="0"
										class="form-control">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-1 control-label">员工3:</label>
								<div class="col-sm-4">
									<input type="text" class="form-control form-control2 suggest"
										id="staffId3" name="staffId3"
										suggest="{data :fillmaps.staffs,style2:true}"> <span
										class="feihuiyuan"><span class="feihuiyuan">
											&nbsp;&nbsp;<label class="control-label"><input
												id="dian3" name="dian3" type="checkbox">&nbsp;点</label>&nbsp;&nbsp;<label
											class="control-label"><input id="quan3" name="quan3"
												type="checkbox">&nbsp;劝</label>
									</span>
								</div>
								<c:if test="${organSetting.displayTicheng}">
									<label class="col-sm-1 control-label">提成3:</label>
									<div class="col-sm-2">
										<input id="somemoney3" name="somemoney3" type="number" min="0"
											class="form-control">
									</div>
								</c:if>
								<c:if test="${!organSetting.displayTicheng}">
									<input id="somemoney3" name="somemoney3" type="hidden">
								</c:if>
								<label class="col-sm-1 control-label">业绩3:</label>
								<div class="col-sm-2">
									<input id="yeji3" name="yeji3" type="number" min="0"
										class="form-control">
								</div>
							</div>
						</span>
						<div class="hr-line-dashed"></div>
						<div class="form-group">
							<div class="col-sm-3 center">
								<label class="control-label"><input id="miandan"
									name="miandan" type="checkbox">&nbsp;免单</label>
							</div>
							<div class="col-sm-2 center">
								<label class="control-label"><input id="laibin_flag"
									name="laibin_flag" type="checkbox">&nbsp;来宾</label>
							</div>
							<label class="col-sm-1 control-label">性别:</label>
							<div class="col-sm-2">
								<label class="control-label"><input id="sex1" name="sex"
									type="radio" value="男">&nbsp;男</label>&nbsp;&nbsp;<label
									class="control-label"><input id="sex2" name="sex"
									type="radio" value="女">&nbsp;女</label>
							</div>
							<div class="col-sm-4">
								<button id="takeChanpin" class="btn btn-info" type="button">选择产品</button>

							</div>
						</div>


						<div class="hr-line-dashed"></div>
						<div class="form-group">
							<div class="col-sm-12 leftPadding">
								<button id="btnNew" class="btn btn-success" type="button">重置</button>
								<button id="btnSave" class="btn btn-primary" type="submit">保存</button>
								<button id="btnEdit" class="btn btn-info" type="button">修改</button>
								<button id="btnWaimai" class="btn btn-warning" type="button">外卖</button>
								<button id="btnJiezhang" class="btn btn-danger" type="button"
									style="width: 100px; margin-left: 15px; margin-right: 15px;">结账</button>
								<button id="btnPrint" class="btn btn-outline btn-info"
									type="button">打印小票</button>
								<button id="btnBack" class="btn btn-outline btn-danger"
									type="button">返回</button>
							</div>
						</div>
					</form>
					<div class="jqGrid_wrapper">
						<table id="myTable"></table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		var manageType, selectedUserpartRowId, waimai;
		$()
				.ready(
						function() {
							var uss = fillmaps.usersorts, ms = fillmaps.incard.membersort, fd = false, us;
							for (var i = 0; i < uss.length; i++) {
								if (ms === uss[i]._id) {
									fd = true;
									us = uss[i];
									break;
								}
							}
							if (!fd) { // 不存在会员类别，为跨店消费
								$
										.post(
												"${ctxPath}/user/usersort/queryById.do",
												{
													usersort : ms
												},
												function(data, status) {
													us = data;
													var mo = $("<option value=\""+ms+"\">"
															+ data.name1
															+ "</option>");
													$("#incard #membersort")
															.append(mo);
													mo[0].selected = true;
												});
							}
							function clearUserpart() {
								$("#cishu").val(1);
								if (fillmaps.incard.flag1 != "1003") { // 非次数卡，计算消费金额
									$("#smallsort").val("");
								}
								$("#price").val(0); // 显示折后金额
								$("#money_xiaofei").val(0); // 折后金额
								$("#money5").val(0); // 抹零
								$("#money1").val(0); // 应交款额
								$("#userpartId").val("");
								$("#miandan")[0].checked = false;
								$("#tichengRegion").clearform({
									clearHidden : true
								});
							}
							$("#btnNew").click(function() { // 重置
								clearUserpart();
							});
							function gouCishu() {
								var shengcishu = $("#shengcishu").val(), c = parseInt($(
										"#cishu").val());
								var ids = $("#myTable").jqGrid('getDataIDs');
								var cs = 0;
								for (var i = 0; i < ids.length; i++) {
									var obj = $("#" + ids[i], $("#myTable"))
											.data("rawData");
									cs += obj.cishu;
								}

								if (cs + c > shengcishu) {
									toastr.warning("超出可用次数:"
											+ (shengcishu - cs));
									$("#cishu").focus();
									return false;
								}
								return true;
							}
							$("#money6").change(function() { // 折扣变化
								jisuan();
							});
							$("#money5").change(function() { // 抹零变化
								jisuan();
							});
							$("#smallsort").change(function() { // 服务项目变化
								jisuan();
							});
							$("#staffId1").change(function() {
								jisuan();
							});
							$("#staffId2").change(function() {
								jisuan();
							});
							$("#staffId3").change(function() {
								jisuan();
							});
							$("#miandan").change(function() {
								jisuan();
							});
							$("#cishu").change(function() {
								jisuan();
							});
							function jisuan() {
								var ss = fillmaps.smallsorts[$("#smallsort")
										.attr("selectedIndex")];
								if (ss) {
									if (fillmaps.incard.flag1 != "1003") { // 非次数卡，计算消费金额
										var m = $("#money6").val();
										if (m) {
											var am = ss.price * m / 100;
											if (fillmaps.incard.flag1 == "1001") { // 单纯打折卡
												$("#money1").val(am);
											} else { // 存钱打折卡
												$("#money_xiaofei").val(am);
											}
											$("#price").val(am);
										}
									}
								}

								// 计算提成、业绩
								tichengYeji("staffId1", "somemoney1", "yeji1");
								tichengYeji("staffId2", "somemoney2", "yeji2");
								tichengYeji("staffId3", "somemoney3", "yeji3");
							}
//							$("#yeji1")
//									.change(
//											function() {
//												var y1 = $("#yeji1").val();
//												if (y1 != "") {
//													tichengYeji("staffId1",
//															"somemoney1",
//															"yeji1", true);
//												}
//											});
//							$("#yeji2")
//									.change(
//											function() {
//												var y1 = $("#yeji2").val();
//												if (y1 != "") {
//													tichengYeji("staffId2",
//															"somemoney2",
//															"yeji2", true);
//												}
//											});
//							$("#yeji3")
//									.change(
//											function() {
//												var y1 = $("#yeji3").val();
//												if (y1 != "") {
//													tichengYeji("staffId3",
//															"somemoney3",
//															"yeji3", true);
//												}
//											});
							function tichengYeji(staffId, somemoney, yeji, flag) {
								var s1 = $("#" + staffId).val(), m1;
								//$("#" + somemoney).val(0);
//								if (flag) {
//									m1 = $("#" + yeji).val();
//								} else {
//									$("#" + yeji).val(0);
//								}
								var ss = fillmaps.smallsorts[$("#smallsort")
										.attr("selectedIndex")];
								if (s1 && !$("#miandan").is(":checked")) {
									if (!flag) {
										if (fillmaps.incard.flag1 == "1003") { // 次数卡
											m1 = $("#danci_money").val()
													* $("#cishu").val();
										} else { // 折扣卡
											m1 = $("#price").val();
										}
									}

									var m5 = $("#money5").val();
									if (m5 == "")
										m5 = "0";
									$("#" + yeji).val(m1 - parseFloat(m5)); // 业绩 = m1 - 抹零

									// 计算提成
									var s = fillmaps.staffs[$("#" + staffId)
											.attr("selectedIndex")];
									for (var i = 0; i < fillmaps.staffTichengs.length; i++) {
										var st = fillmaps.staffTichengs[i];
										var yj = st.yeji;
										if (s && ss && (s.dutyId == st.staffpost)
												&& (ss._id == st.smallsort)) {
											var mm1 = m1;
											if (!flag && yj && yj > 0) {
												if (fillmaps.incard.flag1 == "1003") { // 次数卡
													mm1 = yj
															* $("#cishu").val();
												} else {
													mm1 = yj;
												}
												//$("#" + yeji).val(mm1); // 业绩
											}
											<c:if test="${organSetting.tichengDeductCost}">
											mm1 -= ss.price_chengben;
                                            if(ss.price_chengben==0){//成本为0 提成根据折后计算
                                                mm1=m1;
                                            }
											if (mm1 < 0)
												mm1 = 0;
											</c:if>
                                            <c:if test="${organSetting.tichengDeductCost==false}">
                                            	mm1=m1;
                                            </c:if>
											var tc;
                                            mm1 -= parseFloat(m5);//去除抹零计算
											if (st.removeAmount) { // 去除固定
												tc = (mm1 - st.tichengCard)
														* st.tichengCardPercent
														/ 100;
											} else {
												tc = mm1
														* st.tichengCardPercent
														/ 100;
											}
											tc += st.tichengCard;
											$("#" + somemoney).val(
													tc.toFixed(2));
											break;
										}
									}
								}
							}

							$("#incard")
									.validate(
											{
												submitHandler : function(form) { // 保存
													if (fillmaps.incard.flag1 == "1003") { // 次数卡
														var shengcishu = parseInt($(
																"#shengcishu")
																.val());
														var cishu = parseInt($(
																"#cishu").val());
														if (cishu < 1) {
															toastr
																	.warning("请输入消费次数");
															$("#cishu").focus();
															return;
														}
														if (!gouCishu())
															return;
													} else { // 折扣卡
														var s = $(
																"#incard #smallsort")
																.val();
														if (!s) {
															toastr
																	.warning("请选择服务项目");
															$(
																	"#incard #sg_smallsort")
																	.focus();
															return;
														}
													}

													var s = $(
															"#incard #staffId1")
															.val();
													if (!s) {
														toastr
																.warning("请选择做活员工");
														$("#incard #staffId1")
																.focus();
														return;
													}

													saveUserpart();
												}
											});
							function saveUserpart(obj) {
								var partObj = $("#incard").formobj();
								partObj.guazhang_flag = true; // 是否挂账
								partObj.flag2 = false; // 是否交款
								partObj.delete_flag = false; // 是否删除
								partObj.type = 1; // 会员卡消费类别
								partObj.incardId = fillmaps.incard._id;
								partObj.cardId = fillmaps.incard.usercardId;
								parent.$.shade.show();
								$.post("${ctxPath}/user/userpart/upsert.do",
										partObj, function(data, status) {
											if (data.success) {
												reloadIncard();
												clearUserpart();
												queryUserpartList();
											} else {
												layer.alert(data.message);
											}
											parent.$.shade.hide();
										});
							}
							$("#btnEdit").click(
									function() { // 修改
										if (!selectedUserpartRowId) {
											toastr.warning("请选择要修改的消费信息");
											return;
										}
										var kd = $("#" + selectedUserpartRowId,
												$("#myTable")).data("rawData");
										if (kd.flag2 || !guazhang_flag) {
											layer.alert("已结账信息不能修改！");
										} else {
											clearUserpart();
											$("#incard").fillform(kd);
										}
									});
							// ----------------结账开始-----------------
							function jiezhangJisuan() {
								var ids = $("#myTable").jqGrid('getDataIDs');//得到jqgrid的行数(几笔订单)
								if (ids.length < 1
										&& (!waimai || waimai.length < 1)) {
									return false;
								}
								var xiaocishu = 0, xiaofei = 0;//初始化消费的钱  和消费次数
								for (var i = 0; i < ids.length; i++) {//便利列表   循环添加次数，添加钱
									var up = $("#" + ids[i], $("#myTable"))
											.data("rawData");//得到行对象
									if (!up.flag2 && up.guazhang_flag
											&& !up.miandan) { // 未交款并挂账的计入总金额，并且非免单的
										xiaocishu += up.cishu;
										xiaofei += up.money_xiaofei + up.money1
												- up.money5;
									}
								}

								var xj = 0;
								if (waimai) { // 外卖结账金额
									for (var i = 0; i < waimai.length; i++) {
										if (!waimai[i].miandan) { // 非免单计费
											xj += waimai[i].money1
													- waimai[i].money_qian;
										}
									}
								}
								xiaofei += xj;

								$("#xianjin").val(xiaofei.toFixed(2));
								if (fillmaps.incard.flag1 == "1003") { // 次数卡
									$("#xiaocishu").val(xiaocishu);
								} else if (fillmaps.incard.flag1 == "1001") { //单纯打折卡
									//$("#xianjin").val(xiaofei);
								} else if (fillmaps.incard.flag1 == "1002") { //存钱打折卡
									var m4 = parseFloat($("#money4").val())
											+ xiaofei; // 卡余额
									if (xiaofei > m4) {
										$("#huaka").val(m4);
										$("#xianjin").val(
												(xiaofei - m4).toFixed(2));
									} else {
										$("#huaka").val(xiaofei.toFixed(2));
										$("#xianjin").val(0);
									}
								}

								var xianjin = parseFloat($("#xianjin").val()), money_cash = parseFloat($(
										"#money_cash").val()), money_yinhang_money = parseFloat($(
										"#money_yinhang_money").val());
								var m3 = money_cash
										+ (money_yinhang_money ? money_yinhang_money
												: 0) - xianjin;
								$("#jiezhangDiv #money3").val(m3);

								return true;
							}
							$("#money_cash").change(function() {
								jiezhangJisuan();
							});
							$("#money_yinhang_money").change(function() {
								jiezhangJisuan();
							});
							function jiezhangPasswd() {
								layer.closeAll();
								$("#passwdDiv").show();
								$("#passwd").val("");
								layer.open({
									type : 1,
									title : "消卡密码",
									area : [ '300px', '200px' ],
									btn : [ '确定', '取消' ],
									yes : function(index, layero) {
										doJiezhangPasswd();
									},
									cancel : function(index) {
										layer.closeAll();
									},
									content : $("#passwdDiv")
								});
							}
							$("#passwdForm").validate({
								submitHandler : function(form) { // 结账提交
									doJiezhangPasswd();
								}
							});
							function doJiezhangPasswd() {
								var passwd = $("#passwd").val();
								if (!passwd) {
									toastr.warning("请输入卡密码");
									$("#passwd").focus();
									return;
								}
								if (passwd.length < 6) {
									toastr.warning("卡密码长度不能小于6");
									$("#passwd").focus();
									return;
								}
								var obj = $("#jiezhangDiv").formobj();
								obj.passwd = passwd;
								var ids = $("#myTable").jqGrid('getDataIDs');
								printds = [];
								for (var i = 0; i < ids.length; i++) {
									var up = $("#" + ids[i], $("#myTable"))
											.data("rawData");
									printds.push(up._id);
								}
								parent.$.shade.show();
								$
										.post(
												"${ctxPath}/user/userIncard/jiezhang.do",
												obj,
												function(data, status) {
													if (data.success) {
														layer.closeAll();
														$("#btnPrint").click();
														$("#myTable")
																.jqGrid(
																		'clearGridData');
														reloadIncard();
													} else {
														layer
																.alert(data.message);
													}
													parent.$.shade.hide();
												});
							}
							function jiezhang(form) {
								var money3 = parseFloat($(
										"#jiezhangDiv #money3").val());
								if (fillmaps.incard.flag1 == "1002") { //存钱打折卡判断余额是否足够
									if (money3 < 0) {
										layer.confirm('卡余额不足，确定要欠款消费?',
												function(index) {
													layer.close(index);
													jiezhangPasswd();
												});
										return;
									}
								}
								if (money3 < 0) {
									layer.alert("金额不足!");
									return;
								}

								jiezhangPasswd();
							}
							$("#jiezhangForm").validate({
								submitHandler : function(form) { // 结账提交
									jiezhang(form);
								}
							});
							$("#btnJiezhang").click(function() { // 结账
								$("#jiezhangDiv").clearform();//重置表单
								$("#xianjin").val(0);
								$("#huaka").val(0);
								$("#money_cash").val(0);
								$("#money_yinhang_money").val(0);
								$("#money3").val(0);

								// 查询外卖消费信息
								$.post("${ctxPath}/waimai/query.do", {
									kaidanId : '${ffincard._id}'
								}, function(data) {
									waimai = data;
									popupJiezhang();
								});
							});
							function popupJiezhang() {
								if (!jiezhangJisuan()) {
									toastr.warning("没有需要结账的消费记录");
									return;
								}
								$("#jiezhangDiv").show();
								layer.open({
									type : 1,
									title : "结账收银",
									area : [ '300px', '400px' ],
									btn : [ '结账', '取消' ],
									yes : function(index, layero) {
										$("#jiezhangForm").submit();
									},
									cancel : function(index) {
										layer.closeAll();
									},
									content : $("#jiezhangDiv")
								});
							}

							$("#jiezhangDiv").hide();
							$("#passwdDiv").hide();
							// ----------------结账结束-----------------

							// ---------------选择产品按钮---------------
							$("#takeChanpin")
									.click(
											function() {
												var userpartId = $(
														"#userpartId").val();
												var takeChanpinLayerId = layer
														.open({
															type : 2,
															area : [ '400px',
																	'330px' ],
															fix : false, //不固定
															title : "选择产品",
															maxmin : false,
															content : '${ctxPath}/usewupin/toQuery.do?userpartId='
																	+ userpartId
														});
												layer.full(takeChanpinLayerId);
												//parent.takeChanpinLayerId = takeChanpinLayerId;
											});

							$("#btnWaimai")
									.click(
											function() { // 外卖
												var idx = layer
														.open({
															type : 2,
															area : [ '400px',
																	'330px' ],
															fix : false, //不固定
															title : "外卖品销售管理",
															maxmin : false,
															content : '${ctxPath}/waimai/toQuery.do?kaidanId=${ffincard._id}&zhekou='
																	+ us.waimaizhekou
														});
												layer.full(idx);
											});
							$("#btnPrint")
									.click(
											function() { // 打印小票
												var ids = $("#myTable").jqGrid(
														'getDataIDs');
												var ds = [];
												for (var i = 0; i < ids.length; i++) {
													var up = $("#" + ids[i],
															$("#myTable"))
															.data("rawData");
													ds.push(up._id);
												}
												if (ds.length == 0 && printds) {
													ds = printds;
												}

												var wms = [];// 外卖
												if (waimai) {
													for (var i = 0; i < waimai.length; i++) {
														wms.push(waimai[i]._id);
													}
												}

												if ((ds && ds.length > 0)
														|| (wms && wms.length > 0)) {
													$("#prt")[0].src = '${ctxPath }/user/userpart/print.do?type=1&ids='
															+ ds.join(',')
															+ '&wmids='
															+ wms.join(',');
												} else {
													toastr
															.warning("没有可打印的消费记录");
												}
											});
							$("#btnBack").click(function() { // 返回
								parent.layer.closeAll();

							});
							if (fillmaps.incard.flag1 == "1003") { // 次数卡
								$(".cishur").show();
								$(".zhekour").hide();
							} else { // 折扣卡
								$(".cishur").hide();
								$(".zhekour").show();
								$("#money6").val(fillmaps.incard.zhekou);
							}

							$("#myTable")
									.grid(
											{
												datatype : "local",
												colNames : [ "操作", "挂账",
														"会员姓名", "会员类型", "服务项目",
														"光临时间", "员工1", "员工1提成",
														"消卡金额", "余额", "业绩1",
														"业绩2", "业绩3", "卡号",
														"消费次数" ],
												shrinkToFit : false,
												onSelectRow : function(id) {
													selectedUserpartRowId = id;
												},
												ondblClickRow : function(id) {
													var up = $("#" + id,
															$("#myTable"))
															.data("rawData");
													if (up.incardId) { // 双击进入消卡消费界面

													}
												},
												pager : null,
												colModel : [
														{
															name : "idx",
															index : "idx",
															width : 60,
															sortable : false,
															formatter : function(
																	cellvalue,
																	options,
																	rowObject) {
																var v = "<a href='javascript:void(0);' onClick='delRow(\""
																		+ options.rowId
																		+ "\")'>删除</a>&nbsp;&nbsp;";
																return v;
															}
														},
														{
															name : "guazhang_flag",
															index : "guazhang_flag",
															sortable : false,
															width : 60,
															formatter : function(
																	cellvalue,
																	options,
																	rowObject) {
																if (cellvalue)
																	return "是";
																else
																	return "否";
															}
														},
														{
															name : "name",
															index : "name",
															sortable : false,
															width : 100
														},
														{
															name : "usersortName",
															index : "usersortName",
															sortable : false,
															width : 100
														},
														/*{
															name : "membersort",
															sortable : false,
															index : "membersort",
															formatter : function(
																	cellvalue) {
																var ms = fillmaps.usersorts;
																for (var i = 0; i < ms.length; i++) {
																	if (ms[i]._id == cellvalue) {
																		return ms[i].name1;
																	}
																}
																return "无";
															}
														},*/
														{
															name : "smallsortName",
															sortable : false,
															index : "smallsortName",
															width : 120
														},
														/*{
															name : "smallsort",
															sortable : false,
															index : "smallsort",
															formatter : function(
																	cellvalue) {
																var ms = fillmaps.smallsorts;
																for (var i = 0; i < ms.length; i++) {
																	if (ms[i]._id == cellvalue) {
																		return ms[i].name;
																	}
																}
																return "未知";
															},
															width : 120
														},*/
														{
															name : "createTime",
															sortable : false,
															index : "createTime"
														},
														{
															name : "staffId1",
															sortable : false,
															index : "staffId1",
															formatter : function(
																	cellvalue) {
																var ms = fillmaps.staffs;
																for (var i = 0; i < ms.length; i++) {
																	if (ms[i]._id == cellvalue) {
																		return ms[i].name;
																	}
																}
																return "未知";
															}
														},
														{
															name : "somemoney1",
															sortable : false,
															index : "somemoney1"
														},
														{
															name : "money_xiaofei",
															sortable : false,
															index : "money_xiaofei",
															width : 80,
															formatter : function(rowValue, aaa, callValue) {
																return accSub(rowValue ,callValue.money5);
															}
														}, {
															name : "money4",
															sortable : false,
															index : "money4",
															width : 80
														}, {
															name : "yeji1",
															sortable : false,
															index : "yeji1"
														}, {
															name : "yeji2",
															sortable : false,
															index : "yeji2"
														}, {
															name : "yeji3",
															sortable : false,
															index : "yeji3"
														}, {
															name : "id_2",
															sortable : false,
															index : "id_2"
														}, {
															name : "cishu",
															sortable : false,
															index : "cishu"
														}

												]
											});

							queryUserpartList();

							$("#incard").fillform(fillmaps.incard);
						});

		function queryUserpartList() {
			$.post("${ctxPath}/user/userpart/queryByIncard.do", {
				incardId : fillmaps.incard._id,
				type : 1,
				all : false
			}, function(data, status) {
				selectedUserpartRowId = null;
				$("#myTable").jqGrid('clearGridData');
				for (var i = 0; i <= data.length; i++) {
					$("#myTable").jqGrid('addRowData', i + 1, data[i]);
				}
			});
		}
        function accSub(arg1, arg2) {
            var r1, r2, m, n;
            try {
                r1 = arg1.toString().split(".")[1].length;
            }
            catch (e) {
                r1 = 0;
            }
            try {
                r2 = arg2.toString().split(".")[1].length;
            }
            catch (e) {
                r2 = 0;
            }
            m = Math.pow(10, Math.max(r1, r2)); //last modify by deeka //动态控制精度长度
            n = (r1 >= r2) ? r1 : r2;
            return ((arg1 * m - arg2 * m) / m).toFixed(n);
        }

		function delRow(idx) {
			var kd = $("#" + idx, $("#myTable")).data("rawData");
			layer.confirm('确定删除消卡记录?', function(index) {
				layer.close(index);
				$.post("${ctxPath}/user/userpart/remove.do", {
					id : kd._id
				}, function(data, status) {
					if (data.success) {
						reloadIncard();
						queryUserpartList();
					} else {
						layer.alert(data.message);
					}
				});
			});
		}
		function reloadIncard() {
			parent.$.shade.show();
			$.post("${ctxPath}/user/userIncard/queryById.do", {
				id : fillmaps.incard._id
			}, function(data, status) {
				$("#incard").fillform(data);
				parent.$.shade.hide();
			});
		}
		function helloDate(d) {
			//alert(d);
		}
		function closeTakeChanpinLayer() {
			parent.layer.close(takeChanpinLayerId);
		}
	</script>

	<!-- 结账div -->
	<div id="jiezhangDiv" class="wrapper wrapper-content"
		style="width: 95%; height: 95%">
		<div class="row">
			<form id="jiezhangForm" method="post" class="form-horizontal"
				onSubmit="return false;">
				<input id="incardId" name="incardId" value="${ffincard._id}"
					type="hidden">
				<c:if test="${ffincard.flag1 == '1003'}">
					<div class="form-group">
						<label class="col-sm-4 control-label">消费次数</label>
						<div class="col-sm-7">
							<input id="xiaocishu" name="xiaocishu" type="text"
								class="form-control" disabled>
						</div>
					</div>
				</c:if>
				<div class="form-group">
					<label class="col-sm-4 control-label">现金款额</label>
					<div class="col-sm-7">
						<input id="xianjin" name="xianjin" type="text"
							class="form-control" value="0" disabled>
					</div>
				</div>
				<c:if test="${ffincard.flag1 == '1002'}">
					<div class="form-group">
						<label class="col-sm-4 control-label">划卡款额</label>
						<div class="col-sm-7">
							<input id="huaka" name="huaka" type="text" class="form-control"
								disabled>
						</div>
					</div>
				</c:if>
				<div class="hr-line-dashed"></div>
				<div class="form-group">
					<label class="col-sm-4 control-label">现 金</label>
					<div class="col-sm-7">
						<input id="money_cash" name="money_cash" type="number" min="0"
							class="form-control">
					</div>
				</div>
				<c:if test="${ffincard.flag1 == '1001'}">
					<div class="form-group">
						<label class="col-sm-4 control-label">银 行 卡</label>
						<div class="col-sm-7">
							<input id="money_yinhang_money" name="money_yinhang_money"
								type="number" min="0" class="form-control">
						</div>
					</div>
				</c:if>
				<div class="form-group">
					<label class="col-sm-4 control-label">找 零</label>
					<div class="col-sm-7">
						<input id="money3" name="money3" type="text" class="form-control"
							disabled>
					</div>
				</div>
				<input type="submit" value="提交" style="display: none">
			</form>
		</div>
	</div>

	<!-- 卡密码div -->
	<div id="passwdDiv" class="wrapper wrapper-content"
		style="width: 95%; height: 95%">
		<div class="row">
			<form id="passwdForm" method="post" class="form-horizontal"
				onSubmit="return false;">
				<div class="form-group">
					<label class="col-sm-4 control-label">卡密码</label>
					<div class="col-sm-7">
						<input id="passwd" name="passwd" type="password"
							class="form-control">
					</div>
				</div>
				<input type="submit" value="提交" style="display: none">
			</form>
		</div>
	</div>
	<iframe id="prt" src="" width="180" height="400" style="display: none"></iframe>
</body>
</html>
