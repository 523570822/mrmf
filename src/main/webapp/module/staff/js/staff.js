function mystore(str) {
	window.location.href = _ctxPath + "/w/staff/myStore.do?staffId="
			+ $("#staff_id").val();
}
function tariff(str) {// 价格管理
	window.location.href = _ctxPath + "/w/staff/tariff.do";
}
function myDetail(str) {
	window.location.href = _ctxPath + "/w/staff/myDetail";
}
$(function() {
	$("#main_page").click(
			function() {// 跳转首页
				var staffId = $("#staff_id").val();
				window.location.href = _ctxPath
						+ "/w/staff/toMainPage.do?staffId=" + staffId;
			});
	$("#back").click(
			function() {

				var staffId = $("#staff_id").val();
				var status = $("#status").val();
				var cityId = $("#city_id").val();
				window.location.href = _ctxPath
						+ "/w/staff/tolastPage.do?staffId=" + staffId
						+ "&status=" + status + "&cityId=" + cityId;
			});
	$("#back2").click(
			function() {

				var staffId = $("#staff_id").val();
				var status = $("#status").val();
				window.location.href = _ctxPath
						+ "/w/staff/tolastPage.do?status=" + status;
			});
	$("#change_nick").click(
			function() {// 昵称
				var status = "nick";
				var staffId = $("#staff_id").val();
				window.location.href = _ctxPath
						+ "/w/staff/changeMessLoad.do?status=" + status;
			});
	$("#change_name").click(
			function() {// 姓名
				var status = "name";
				var staffId = $("#staff_id").val();
				window.location.href = _ctxPath
						+ "/w/staff/changeMessLoad.do?status=" + status;
			});
	$("#change_phone").click(
			function() {// 手机号
				var status = "phone";
				var staffId = $("#staff_id").val();
				window.location.href = _ctxPath
						+ "/w/staff/changeMessLoad.do?status=" + status;
			});
	$("#change_idcard").click(
			function() {// 身份证号码
				var status = "idcard";
				var staffId = $("#staff_id").val();
				window.location.href = _ctxPath
						+ "/w/staff/changeMessLoad.do?status=" + status;
			});
	$("#change_certNumber").click(
			function() {// 资格证号
				var status = "certNumber";
				var staffId = $("#staff_id").val();
				window.location.href = _ctxPath
						+ "/w/staff/changeMessLoad.do?status=" + status;
			});
	$("#change_home").click(
			function() {// 地址
				var status = "home";
				var staffId = $("#staff_id").val();
				window.location.href = _ctxPath
						+ "/w/staff/changeMessLoad.do?status=" + status;
			});
	$("#change_sex").click(
			function() {// 性别
				var status = "sex";
				var staffId = $("#staff_id").val();
				window.location.href = _ctxPath
						+ "/w/staff/changeMessLoad.do?status=" + status;
			});
	$("#change_jishiTechang").click(
			function() {// 技师特长
				var status = "jishiTechang";
				var staffId = $("#staff_id").val();
				window.location.href = _ctxPath
						+ "/w/staff/changeMessLoad.do?status=" + status;
			});
	$("#change_workYears").click(
			function() {// 工作年限
				var status = "workYears";
				var staffId = $("#staff_id").val();
				window.location.href = _ctxPath
						+ "/w/staff/changeMessLoad.do?status=" + status;
			});
	$("#change_sex li").click(function() {
		$(this).addClass('active').siblings().removeClass('active');
		$("#change_sex").val($(this).index());
	});
	$("#change_sex li").each(function(index) {
		var sex = 0;
		var state = $("#sex").val();
		if (state == "男") {
			sex = 0;
		} else {
			sex = 1;
		}
		if (sex == index) {
			$(this).addClass('active').siblings().removeClass('active');
		}
	});
	$("#change_jishiTechang li").click(function() {
		$(this).addClass('active').siblings().removeClass('active');
		$("#change_jishiTechang").val($(this).index());
	});
	$("#change_jishiTechang li").each(function(index) {
		var jishiTechang = 0;
		var state = $("#jishiTechang").val();
		if (state == "美发") {
			jishiTechang = 0;
		} else if (state == "美容") {
			jishiTechang = 1;
		} else if (state == "美甲") {
			jishiTechang = 2;
		} else {
			jishiTechang = 3;
		}
		if (jishiTechang == index) {
			$(this).addClass('active').siblings().removeClass('active');
		}
	});
	/*
	 * $(".sex_select li").click(function(){
	 * $(this).addClass('active').siblings().removeClass('active');
	 * $("#new_workYears").val($(this).index()); });
	 */
	/*
	 * $(".sex_select li").each(function(index){ var
	 * state=$("#workYears").val(); if(state==index){
	 * $(this).addClass('active').siblings().removeClass('active'); } });
	 */
	$("#work_years li").click(function() {
		$(this).addClass('active').siblings().removeClass('active');
		$("#new_workYears").val($(this).index());
	});
	$("#work_years li").each(function(index) {
		var state = $("#workYears").val();
		if (state == index) {
			$(this).addClass('active').siblings().removeClass('active');
		}
	});
	$("#submit")
			.click(
					function() {
						var status = $("#status").val();
						var val = $("#value").val();
						if (status == "idcard") {
							var flag = validateIdCard(val);
							if (flag) {
								$("#form").attr("action",
										_ctxPath + "/w/staff/changeMessSave")
										.submit();
							} else {
								layer.msg("请输入正确的身份证号");
								return false;
							}
						} else if (status == "phone") {
							var flag = isPhoneNum(val);
							if (flag) {
								$
										.post(
												_ctxPath
														+ "/w/staff/isHaveStaffPhone",
												{
													'phone' : val
												},
												function(data) {
													if (data == true) {
														alert('此手机已经有技师使用,请核对！');
													} else {
														$("#form")
																.attr(
																		"action",
																		_ctxPath
																				+ "/w/staff/changeMessSave")
																.submit();
													}
												});

							} else {
								alert("请输入正确的手机号");
								return false;
							}
						} else if (status == "certNumber") {
							if (val != "") {
								var flag = isUnsigned(val);
								if (flag) {
									$("#form")
											.attr(
													"action",
													_ctxPath
															+ "/w/staff/changeMessSave")
											.submit();
								} else {
									layer.msg("请输入正确的资格证号");
									return false;
								}
							} else {
								$("#form").attr("action",
										_ctxPath + "/w/staff/changeMessSave")
										.submit();
							}
						} else if (status == "name" || status == "nick") {
							if (val.length > 10) {
								layer
										.msg("<div style='text-align:center;'>您输入的内容过长</div>");
								return false;
							}
							$("#form").attr("action",
									_ctxPath + "/w/staff/changeMessSave")
									.submit();
						} else {
							$("#form").attr("action",
									_ctxPath + "/w/staff/changeMessSave")
									.submit();
						}
					});
	$(".time_list li").click(
			function() {// 日程
				var flag = !$(this).hasClass('active');
				var index = $(this).index();
				var organId = $("#organId").val();
				if (organId == "") {
					layer.msg("请选择店铺");
					return false;
				} else {
					$.post(_ctxPath + "/w/staff/scheduleSave", {
						'staffId' : $("#staff_id").val(),
						'organId' : organId,
						'day' : $("#day").val(),
						'index' : index,
						'selected' : flag
					}, function(data) {
						if (data == 'true') {
							$(".time_list li:nth-child(" + (index + 1) + ")")
									.toggleClass('active');
						}
					}, "text");
				}
			});
	$(".weeklist li").click(
			function() {
				var day = $(this).children("input").val();
				var staffId = $("#staff_id").val();
				window.location.href = _ctxPath
						+ "/w/staff/schedule.do?staffId=" + staffId + "&day="
						+ day;
			});
	$("#select_organ").click(
			function() {// 选择店铺
				var staffId = $("#staff_id").val();
				var day = $("#day").val();
				window.location.href = _ctxPath
						+ "/w/staff/selectOrgan.do?staffId=" + staffId
						+ "&day=" + day;
			});
	$("#select_my_organ li").click(function() {// 日程选择店铺
		$("#organ_id").val($(this).children("input").val());
		$(this).addClass("active").siblings().removeClass("active");
	});
	$("#terminate").click(
			function() {// 解约
				$.ajax({
					url : _ctxPath + "/w/staff/isJoin",
					type : "post",
					data : $('#terminate_form').serialize(),
					success : function(msg) {
						if (msg.status == "1") {
							window.location.href = _ctxPath
									+ "/w/staff/myStore.do";
						} else {
							layer.msg(msg.message);
							setTimeout(function() {
								window.location.href = _ctxPath
										+ "/w/staff/myStore.do";
							}, 3000);
						}
					}
				}, "json");
			});
	$("#join").click(function() {// 申请加入
		$.ajax({
			url : _ctxPath + "/w/staff/isJoin",
			type : "post",
			data : $('#join_form').serialize(),
			success : function(msg) {
				if (msg.status == "1") {
					window.location.href = _ctxPath + "/w/staff/myStore.do";
				} else {
					alert(msg.message);
					window.location.href = _ctxPath + "/w/staff/myStore.do";
				}
			}
		}, "json");
	});
	$("#example").click(
			function() {// 典型案例
			// $(this).addClass("mainpage_active");
				$("#staff_mainPage").attr("action",
						_ctxPath + "/w/staff/exampleList").submit();
			});
	$("#add_example").click(
			function() {// 添加案例
				var staffId = $("#staff_id").val();
				window.location.href = _ctxPath
						+ "/w/staff/addExample.do?staffId=" + staffId;
			});
	$(".canEdit").click(function() {// 案例设置内容可编辑
		$(this).attr("contenteditable", "true");
		$(this).focus();
	});
	$("#save_example")
			.click(
					function() {// 保存案例
						var price = $("#price").val();
						var consumeTime = $("#consume_time").val();
						var title = $("#title").val();
						var desc = $("#desc").val();
						if (title == "") {
							layer.msg("请输入标题");
							return false;
						} else if (title != "" && title.length > 10) {
							layer.msg("标题长度不大于10个字");
							return false;
						}
						if (desc == "") {
							layer.msg("请输入介绍");
							return false;
						} else if (desc != "" && desc.length > 60) {
							layer
									.msg("<div style='text-align:center;'>您输入的内容过长</div>");
							return false;
						}
						var flag_p = isUnsigned(price);
						var flag_c = isUnsigned(consumeTime);
						if (flag_c && flag_p) {
							$.ajax({
								url : _ctxPath + "/w/staff/editAndSaveExam",
								type : "post",
								data : {
									'staffCaseId' : $("#staffCase_id").val(),
									'title' : title,
									'desc' : desc,
									'price' : price,
									'consumeTime' : consumeTime
								},
								success : function(msg) {
									if (msg.status == "1") {
										window.location.href = _ctxPath
												+ "/w/staff/exampleList.do";
									} else {
										layer.msg(msg.message);
									}
								}
							}, "json");
						} else {
							layer.msg("金钱和时间只能为整数，请从新输入！");
							return false;
						}
					});
	$("#delete").click(
			function() {// 删除案例
				var staffId = $("#staffId").val();
				$.ajax({
					url : _ctxPath + "/w/staff/deleteExample",
					type : "post",
					data : $('#delete_form').serialize(),
					success : function(msg) {
						if (msg.status == "1") {
							window.location.href = _ctxPath
									+ "/w/staff/exampleList.do?staffId="
									+ staffId;
						} else {
							layer.msg(msg.message);
							return false;
						}
					}
				}, "json");
			});
	$("#publish").click(function() {// 发布案例
		var price = $("#price").val();
		var consumeTime = $("#consume_time").val();
		var type = $("#type").val();
		var title = $("#title").val();
		var desc = $("#desc").val();
		var realType = $("#real_type").val();
		if (type == "" || type == null) {
			alert("请输入类型");
			return;
		}
		if (title == "") {
			alert("请输入标题");
			return;
		} 
		if (title != "" && title.length > 10) {
			alert("标题长度不大于10个字");
			return;
		}
		if (desc == "") {
			alert("请输入介绍");
			return;
		} else if (desc != "" && desc.length > 60) {
			alert("您输入的内容过长");
			return;
		}
		if($('.uploaded').length<=0) {
			alert("请上传图片");
			return;
		}
		var tempArr = new Array();
		$('.uploaded').each(function(index,element){
			var imgsrc = $(element).find('input').val();
			tempArr.push(imgsrc);
		});
		var images = tempArr.join(',');
		var flag_p = isUnsigned(price);
		var flag_c = isUnsigned(consumeTime);
		if (flag_c && flag_p) {
			price = window.parseInt(price);
			consumeTime = window.parseInt(consumeTime);
			var param = {'real_type':realType,'price':price,'consumeTime':consumeTime,'type':type,'title':title,'desc':desc,'images':images};
			$.ajax({
				url : _ctxPath + "/w/staff/addExampleSave",
				type : "post",
				data : param,
				success : function(msg) {
					if (msg == "true") {
						$("#showDiv").css({'display':'block'});
						$("#content").css({'display':'block'});
					}
				}
			}, "json");
			return;
		} else {
			alert("金钱和时间只能为整数，请从新输入！");
			return;
		}
	});
	$("#my_example").click(
			function() {// 跳转我的案例
				var staffId = $("#staff_id").val();
				window.location.href = _ctxPath
						+ "/w/staff/exampleList.do?staffId=" + staffId;
			});
	$("#ask_price").click(
			function() {// 跳转询价列表
				$("#staff_mainPage").attr("action",
						_ctxPath + "/w/staff/askPrice").submit();
			});
	$("#sign_in").click(
			function() {// 跳转签到
				$("#staff_mainPage").attr("action",
						_ctxPath + "/w/staff/signIn").submit();
			});
	$("#sign_save").click(function() {// 签到保存
		var organId = $("#organ_id").val();
		if (organId == "") {
			alert("请选择签到店铺");
			return false;
		} else {
			$("#sign_save").attr("disabled", true);
			$.ajax({
				url : _ctxPath + "/w/staff/signInSave",
				type : "post",
				data : {
					'staffId' : $("#staff_id").val(),
					'organId' : $("#organ_id").val(),
					'longitude' : $("#longitude").val(),
					'latitude' : $("#latitude").val()
				},
				success : function(msg) {
					if (msg.status == "1") {
						$("#sign_save").attr("disabled", false);
						$("#sign_success").css({
							'display' : 'block'
						});
						alert(msg.message);
					} else {
						alert(msg.message);
					}
				}
			}, "json");
		}
	});

	$("#statistics").click(
			function() {// 跳转签到统计
				var staffId = $("#staff_id").val();
				window.location.href = _ctxPath
						+ "/w/staff/signStatistics.do?staffId=" + staffId;
			});

	$("#askprice_save").click(
			function() {// 保存技师报价
				var myPrice = $("#my_price").val();
				var desc = $("#price_desc").val();
				if (desc == "") {
					alert('请输入您的报价说明(所用产品、时间及相关说明)');
					return false;
				} else if (desc != "" && desc.length > 60) {
					alert('您输入的内容过长');
					return false;
				}
				var flag = isRightMoney(myPrice);
				if (flag) {
					$.ajax({
						url : _ctxPath + "/w/staff/askPriceSave",
						type : "post",
						data : $('#askprice_form').serialize(),
						success : function(msg) {
							if (msg.status == "1") {
								window.location.href = _ctxPath
										+ "/w/staff/askPrice.do";
							} else {
								alert(msg.message);
								return;
							}
						}
					}, "json");
				} else {
					alert('金额无效，请输入有效金额');
					return false;
				}
			});
	$("#return_price").click(
			function(str) {
				window.location.href = _ctxPath
						+ "/w/staff/addExample.do?staffId=" + staffId;
			});
	$("#busytime_set").click(
			function() {// 跳转繁忙时间
				var staffId = $("#staff_id").val();
				window.location.href = _ctxPath
						+ "/w/staff/setBusyTime.do?staffId=" + staffId;
			});
	$("#save_busytime").click(
			function(str) {// 设置繁忙时间
				var staffId = $("#staff_id").val();
				var busyTimeStart = $("#busyTimeStart").html();
				var busyTimeEnd = $("#busyTimeEnd").html();
				if (busyTimeStart == "") {
					layer.msg("请选择开始时间");
					return;
				}
				if (busyTimeEnd == "") {
					layer.msg("请选择结束时间");
					return;
				}
				if (busyTimeStart >= busyTimeEnd) {
					layer.msg("起始时间必须小于结束时间");
					return false;
				}
				window.location.href = _ctxPath
						+ "/w/staff/saveBusyTime.do?busyTimeStart="
						+ busyTimeStart + "&busyTimeEnd=" + busyTimeEnd;
			});

	$("#search").click(function() {// 店铺名搜索
		// $("#search_form").attr("action",_ctxPath+"/w/staff/adjustLocation").submit();
	});
	$("#mine").click(
			function() {// 跳转我的页面
				$("#staff_mainPage").attr("action", _ctxPath + "/w/staff/mine")
						.submit();
			});
	$("#edit").click(function() {// 编辑简介
		$.ajax({
			url : _ctxPath + "/w/staff/saveRecommendation",
			type : "post",
			data : {
				'staffId' : $("#staff_id").val(),
				'textArea' : $("#text_area").val()
			},
			success : function(msg) {
				if (msg == "true") {
					alert("保存成功");
				}
			}
		}, "json");
	});
	$("#searchOrgan").click(
			function() {// 选择店铺
				var organId = $("#organ_id").val();
				var staffId = $("#staff_id").val();
				if (organId != "") {
					window.location.href = _ctxPath
							+ "/w/staff/signIn.do?staffId=" + staffId
							+ "&organId=" + organId;
				} else {
					alert("请选择店铺");
					return false;
				}

			});
	$('#goOrganMap').click(
			function() {// 跳转地图查看店铺
				var organId = $.trim($("#organ_id").val());
				if (organId == "") {
					return false;
				}
				window.location.href = _ctxPath + "/w/home/goOrganMap?organId="
						+ organId;
			});

});
function addStore() {
	$("#mystore_form").attr("action", _ctxPath + "/w/staff/stores").submit();
}
function isjoin(str) {
	if (str == "1") {
		var showDiv = document.getElementById("showDiv");
		var content = document.getElementById("content");
		showDiv.style.display = 'block';
		content.style.display = 'block';
	} else {
		var showDiv = document.getElementById("showDiv");
		var contentJoin = document.getElementById("content_join");
		showDiv.style.display = 'block';
		contentJoin.style.display = 'block';
	}
}
function schedule(str) {
	// $(str).addClass("mainpage_active");
	$("#staff_mainPage").attr("action", _ctxPath + "/w/staff/schedule")
			.submit();
}
function rent(str) {
    // $(str).addClass("mainpage_active");
    var staffId=$("#staff_id" ).val();
    var city=$("#city" ).val();
    var cityId=$("#cityId" ).val();
    window.location.href= _ctxPath + "/w/staff/toRent.do?staffId="+staffId+"&city="+city+"&cityId="+cityId;
		// "${ctxPath/w/staff/toRent.do?staffId="+staffId;
}
function openInquiry(str) {// 开启询价
	var longitude = $("#longitude").val();
	var latitude = $("#latitude").val();
	window.location.href = _ctxPath + "/w/staff/openInquiry";
}
function closeInquiry(str) {// 关闭询价
	/*
	 * var longitude=$("#longitude").val(); var latitude=$("#latitude").val();
	 */
	window.location.href = _ctxPath + "/w/staff/closeInquiry";
}
function return_price(str) {// 返回询价首页
	window.location.href = _ctxPath + "/w/staff/askPrice.do?staffId=" + staffId;
}
function selectOrgan() {// 日程管理选择店铺
	var organId = $("#organ_id").val();
	var day = $("#day").val();
	if (organId == "") {
		layer.msg("<div style='text-align:center;'>请选择店铺</div>");
		return false;
	}
	window.location.href = _ctxPath + "/w/staff/schedule.do?organId=" + organId
			+ "&day=" + day;
}
function adjustLocation() {// 地点微调

	window.location.href = _ctxPath + "/w/staff/adjustLocation.do";
}
function getOrganId(str) {// 地点微调结束
	var staffId = $("#staff_id").val();
	$(this).addClass('active2').siblings().removeClass('active2');
	$("#organ_id").val(str);
	// window.location.href = _ctxPath +
	// "/w/staff/signIn.do?staffId="+staffId+"&organId="+str;
}
function organInfo(str) {// 跳转店铺
	var staffId = $("#staff_id").val();
	window.location.href = _ctxPath + "/w/staff/store.do?staffId=" + staffId
			+ "&organId=" + str;
}
function myRecommendation(str) {// 跳转自我推荐
// $(str).addClass("mainpage_active");
	var staffId = $("#staff_id").val();
	window.location.href = _ctxPath + "/w/staff/myRecommendation.do?staffId="
			+ staffId;
}
function editSave() {// 保存自我推荐
	var desc = $("#text_area").val();
	if (desc.length > 90) {
		layer.msg("您输入的内容过长");
		return false;
	}
	$.ajax({
		url : _ctxPath + "/w/staff/saveRecommendation",
		type : "post",
		data : {
			'logo0' : $("#logo0").val(),
			'logo1' : $("#logo1").val(),
			'logo2' : $("#logo2").val(),
			'textArea' : $("#text_area").val()
		},
		success : function(msg) {
			if (msg == "true") {
				layer.msg("保存成功");
				setTimeout(function() {
					window.location.href = _ctxPath
							+ "/w/staff/tolastPage.do?status="
							+ $("#status").val();
				}, 3000);
			}
		}
	}, "json");
}
function comment() {// 保存自我推荐
	var organId = $("#organ_id");
	window.location.href = _ctxPath + "/w/organ/toRatedList.do?organId="
			+ organId;
}
function lostFocus(str) {// 失去焦点
	$(str).attr("contenteditable", "false");
	if ($.trim($(str).text()) == "") {
		if ($(str).attr("id") == "editTime") {
			$(str).text($("#consume_time").val());
		} else if ($(str).attr("id") == "editPrice") {
			$(str).text($("#price").val());
		} else if ($(str).attr("id") == "editTitle") {
			$(str).text($("#title").val());
		}
	} else {
		if ($(str).attr("id") == "editTime") {
			$("#consume_time").val($.trim($(str).text()));
		} else if ($(str).attr("id") == "editPrice") {
			$("#price").val($.trim($(str).text()));
		} else if ($(str).attr("id") == "editTitle") {
			$("#title").val($.trim($(str).text()));
		}
	}
}
function mess() {// 查看信息补全情况
	var status = $("#status").val();
	var message = $("#message").val();
	if (status != "" && status != "0") {
		alert(message);
	}
}
function isUnsignedInteger(a) {// 验证正数
	var reg = /^[1-9][0-9]*/;
	return reg.test(a);
}
function isUnsigned(a) {// 验证正整数
	var reg = /^[0-9]*[1-9][0-9]*$/;
	return reg.test(a);
}
function isRightMoney(a) {// 验证金额
	var reg = /^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/;
	return reg.test(a);
}

function isCardNo(card) {// 验证身份证
	var pattern = /^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{4}$/;
	return pattern.test(card);
}
function isPhoneNum(phone) {// 验证手机号
	var pattern = /^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])\d{8}$/;
	return pattern.test(phone);
}
function validateIdCard(idCard) {// 验证身份证
	// 15位和18位身份证号码的正则表达式
	var regIdCard = /^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/;

	// 如果通过该验证，说明身份证格式正确，但准确性还需计算
	if (regIdCard.test(idCard)) {
		if (idCard.length == 18) {
			var idCardWi = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10,
					5, 8, 4, 2); // 将前17位加权因子保存在数组里
			var idCardY = new Array(1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2); // 这是除以11后，可能产生的11位余数、验证码，也保存成数组
			var idCardWiSum = 0; // 用来保存前17位各自乖以加权因子后的总和
			for (var i = 0; i < 17; i++) {
				idCardWiSum += idCard.substring(i, i + 1) * idCardWi[i];
			}

			var idCardMod = idCardWiSum % 11;// 计算出校验码所在数组的位置
			var idCardLast = idCard.substring(17);// 得到最后一位身份证号码

			// 如果等于2，则说明校验码是10，身份证号码最后一位应该是X
			if (idCardMod == 2) {
				if (idCardLast == "X" || idCardLast == "x") {
					return true;
				} else {
					return false;
				}
			} else {
				// 用计算出的验证码与最后一位身份证号码匹配，如果一致，说明通过，否则是无效的身份证号码
				if (idCardLast == idCardY[idCardMod]) {
					return true;
				} else {
					return false;
				}
			}
		}
	} else {
		return false;
	}
}