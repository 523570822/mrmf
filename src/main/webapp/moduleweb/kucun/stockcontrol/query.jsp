<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/moduleweb/resources/common.jsp"%>
<html>
<head>
<title></title>
<style>
.maxHeight {
	height: 825px;
	border: 1px solid #ddd;
	overflow: auto;
}

.minHeight {
	
}
</style>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content  animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content">
						<div class="row">
							<div class="col-sm-12">
								<form id="addForm" method="get" class="form-horizontal">
									<label class="col-sm-1 control-label">物品类别</label>
									<div class="col-sm-2">
										<input type="text" class="form-control suggest" id="leibie"
											name="leibie"
											suggest="{data :fillmaps.leibies,style2:true,searchFields : [ '_id' ],keyField : 'mingcheng'}">
									</div>
									<!-- 									<label class="col-sm-2 control-label">物品条码号</label> -->
									<!-- 									<div class="col-sm-2"> -->
									<!-- 										<input id="code" name="code" type="text" class="form-control"> -->
									<!-- 									</div> -->
									<!-- 							<label class="col-sm-1 control-label">助记符</label> -->
									<!-- 							<div class="col-sm-2"> -->
									<!-- 									<input id="zjfCode" name="zjfCode" type="text" -->
									<!-- 														class="form-control"> -->
									<!-- 							</div> -->
									<label class="col-sm-1 control-label">退货量</label>
									<div class="col-sm-2">
										<input id="num" name="num" type="number" class="form-control"
											min="0">
									</div>
									<input id="kucunId" name="kucunId" type="hidden"
										class="form-control">
									<c:if test="${filiale==true }">
										<label class="col-sm-1 control-label">退货地址</label>
										<div class="col-sm-2">
											<input type="hidden" value="${filiale }" id="filiale" /> <select
												class="form-control" id="reAddr" name="reAddr">
												<option value="">请选择</option>
												<option value="1">总公司</option>
												<option value="2">厂家</option>
											</select>
										</div>
									</c:if>
									<c:if test="${filiale!=true }">
										<input type="hidden" value="2" id="reAddr" name="reAddr" />
									</c:if>
									<div class="col-sm-2">
										<button id="tuihuo" class="btn btn-outline btn-danger"
											type="button">退货</button>
									</div>
								</form>
							</div>
							<form id="searchForm" method="get" class="form-horizontal">
								<div class="form-group">
									<input id="wupinId" name="wupinId" type="hidden"
										class="form-control"> <input id="shenhe|boolean"
										name="shenhe|boolean" type="hidden" value="true"
										class="form-control">
								</div>
							</form>
							<form id="kucunsearchForm" method="get" class="form-horizontal">
								<div class="form-group">
									<input id="shenhe|boolean" name="shenhe|boolean" type="hidden"
										value="true" class="form-control">
								</div>
							</form>
						</div>

						<div class="ibox-content">
							<div class="row">
								<div class="col-sm-6 maxHeight">
									<div class="jqGrid_wrapper">
										<table id="kucunTable"></table>
									</div>
								</div>
								<div class="col-sm-6">
									<div class="jqGrid_wrapper minHeight">
										<table id="rukuTable"></table>
									</div>
									<div class="row" id="ru" style="display: none;">
										<div class="col-sm-6">
											总共入库量为：<span id="inSum">0</span>
										</div>
										<div class="col-sm-6">
											总价为：<span id="inPriceSum">0</span>
										</div>
									</div>
									<div class="jqGrid_wrapper minHeight">
										<table id="chukuTable"></table>
									</div>
									<div class="row" id="chu" style="display: none;">
										<div class="col-sm-6">
											总共出库量为：<span id="outSum">0</span>
										</div>
										<div class="col-sm-6">
											总价为：<span id="outPriceSum">0</span>
										</div>
									</div>
									<div class="jqGrid_wrapper minHeight">
										<table id="tuihuoTable"></table>
									</div>
									<div class="row" id="tui" style="display: none;">
										<div class="col-sm-6">
											总共退货量为：<span id="tuiHuoSum">0</span>
										</div>
										<div class="col-sm-6">
											总价为：<span id="tuiHuoPriceSum">0</span>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript">
			var kucunId = "";
			var wupinId = "";
			$()
					.ready(
							function() {
								$("#tuihuo")
										.click(
												function() {
													var url = _ctxPath
															+ "/kucun/tuihuo/upsert.do";
													var code = $("#leibie").val();
													//var zjfCode=$("#zjfCode").val();
													
													if (code == "") {
														toastr
																.error("物品类别不能为空");
														return;
													}
													var num = $("#num").val();
													var reginfo = regNum(num);
													if (reginfo != "") {
														toastr.error(reginfo);
														return;
													}
													var filiale=$("#filiale").val();
													var reAddr=$("#reAddr").val();
													if(filiale=="true"){
														if(""==reAddr){
															toastr.error("退货的地址不能为空");
															return;
														}
														
													}
													/*var kucunIds = $("#kucunId")
															.val();
													if (kucunIds == "") {
														toastr.error("请选择退货商品");
														return;
													}*/
													layer.confirm('确认退货吗?',function(index){
															$.post(url,{'code' : code,'num' : num,'reAddr':reAddr},function(data) {
															if (data) {
																if (data.success) {
																	toastr.success("操作成功");
																	$("#kucunTable").reloadGrid(
																				{postData : $("#kucunsearchForm").formobj()});
																				/*$("#rukuTable").reloadGrid(
																								{postData : $("#searchForm").formobj()
																								});
																				$("#chukuTable").reloadGrid({
																				postData : $("#searchForm").formobj()
																								});
																				$("#tuihuoTable").reloadGrid(
																								{postData : $("#searchForm").formobj()
																								});*/
																				if(wupinId!=""){getinfo();
																					}else{getDetailInfo();
																					}
																				getSum();
																			} else {
																				toastr.error(data.message);
																			}

																		} else {
																			toastr
																					.error("操作失败");
																		}
																	});
															layer.close(index);
										            		});
													
												});

								$("#searchForm").submit(function() {
									$("#kucunTable").reloadGrid({
										postData : $("#searchForm").formobj()
									});
									return false;
								});
								$("#kucunTable").grid(
										{
											url : _ctxPath
													+ "/kucun/kucun/query.do",
											shrinkToFit : false,
											postData : $("#searchForm")
													.formobj(),
											onSelectRow : function(id) {
												getInfo(id);
											},
											colNames : [ "物品类别", "品牌", "产品名称",
													"单价", "数量", "总价", "备注",
													 "净含量", "总量", "部门",
													"助记符", "报警数量", "有效期",
													"报警日期", "规格" ],
											colModel : [ {
												name : "wupinName",
												index : "wupinId"
											}, {
												name : "pinpaiName",
												index : "pinpai"
											}, {
												name : "mingcheng",
												index : "mingcheng"
											}, {
												name : "price",
												index : "price"
											}, {
												name : "num",
												index : "num"
											}, {
												name : "price_all",
												index : "price_all"
											}, {
												name : "note",
												index : "note"
											},  {
												name : "weight",
												index : "weight"
											}, {
												name : "weight_all",
												index : "weight_all"
											}, {
												name : "bumenName",
												index : "bumen"
											}, {
												name : "zjfCode",
												index : "zjfCode"
											}, {
												name : "jingjie",
												index : "jingjie"
											}, {
												name : "useful_life",
												index : "useful_life"
											}, {
												name : "jingjiedate",
												index : "jingjiedate"
											}, {
												name : "guige",
												index : "guige"
											} ]
										});
							$("#leibie").change(function() {
								leibie();
							});
							});
			function leibie(f) {
			var lb = fillmaps.leibies[$("#leibie").attr("selectedIndex")];
			if (!f) {
				if (lb) {
// 					$("#code").val(lb.code);
// 					$("#mingcheng").val(lb.mingcheng);
// 					$("#pinpaiName").val(lb.pinpaiName);
// 					$("#price_xs").val(lb.price_xs);
// 					$("#price").val(lb.price);
// 					$("#pinpai").val(lb._id);
					$("#wupinId").val(lb._id);
					/*$("#kucunTable").reloadGrid({
							postData : $("#searchForm").formobj()
					});
					if(wupinId!=""){
					$("#rukuTable").reloadGrid({
						postData : $("#searchForm").formobj()
					});
					$("#chukuTable").reloadGrid({
						postData : $("#searchForm").formobj()
					});
					$("#tuihuoTable").reloadGrid({
						postData : $("#searchForm").formobj()
					});
					}else{
						getDetailInfo();
					}*/
				}
			}
		}
			function removeLeibie(leibieId) {
				var url = _ctxPath + "/kucun/leibie/remove/" + leibieId + ".do";
				$.get(url, {}, function(data) {
					if (data) {
						if (data.success) {
							toastr.success("操作成功");
						} else {
							toastr.error(data.message);
						}
						$("#leibieTable").trigger("reloadGrid");
					} else {
						toastr.error("操作失败");
					}
				});
			}
			function getSum() {
				var o = $("#searchForm").formobj();
				$.post("${ctxPath}/kucun/stockcontrolSum/total.do", o,
						function(data, status) {
							//$("#totalRegion").fillform(data);
							//console.log(data);
							$("#inSum").html(data.inSum);
							$("#inPriceSum").html(data.inPriceSum);
							$("#outSum").html(data.outSum);
							$("#outPriceSum").html(data.outPriceSum);
							$("#tuiHuoSum").html(data.tuiHuoSum);
							$("#tuiHuoPriceSum").html(data.tuiHuoPriceSum);
						});

			}
			function getInfo(id) {
				if (id != "") {
						var kc = $("#" + id, $("#kucunTable")).data("rawData");
						$("#wupinId").val(kc.wupinId);
						//console.log(kc);
						$("#kucunId").val(kc._id);
					} else {
						$("#wupinId").val(wupinId);
						//console.log(kc);
						$("#kucunId").val(kucunId);
					}
				
				if ("" == wupinId) {
					kucunId = kc._id;
					wupinId = kc.wupinId;
					getDetailInfo();
				} else {
					$("#rukuTable").reloadGrid({
						postData : $("#searchForm").formobj()
					});
					$("#chukuTable").reloadGrid({
						postData : $("#searchForm").formobj()
					});
					$("#tuihuoTable").reloadGrid({
						postData : $("#searchForm").formobj()
					});
				}
				getSum();
				return false;
			}
			function regNum(num) {
				var regex = /^([1-9]\d{0,15}|0)(\.\d{1,4})?$/;
				if (!regex.test(num)) {
					return "退货量格式不正确";
				} else {
					if (num <= 0) {
						return "退货量不能为零";
					}
					return "";
				}
			}
			
			function getDetailInfo(){
				$("#rukuTable").grid(
							{
								url : _ctxPath + "/kucun/ruku/query.do",
								shrinkToFit : false,
								rowNum : 5,
								rowList : [ 3, 4,5 ],
								postData : $("#searchForm").formobj(),
								colNames : [ "单号", "物品类别", "产品编码", "产品名称",
										<c:if test='${filiale!=true }'>"进货价格",</c:if> "进货数量", "总价", "产品产地", "联系人",
										"产品公司地址", "电话", "传真", "邮箱", "进货人",
										"备注", "产品状态", "净含量", "总量", "部门", "助记符",
										"进货时间", "有效期", "单位", "进货单位名称", "组盘号",
										"规格", "入库审核", "销售价格", "销售总价", "品牌",
										"分公司入库审核", "日期" ],
								colModel : [ {
									name : "danhao",
									index : "danhao",
									align : "center"
								}, {
									name : "wupinName",
									index : "wupinId",
									align : "center"
								}, {
									name : "code",
									index : "code",
									align : "center"
								}, {
									name : "mingcheng",
									index : "mingcheng",
									align : "center"
								}, <c:if test='${filiale!=true }'>{
									name : "price",
									index : "price",
									align : "center"
								},</c:if> {
									name : "num",
									index : "num",
									align : "center"
								}, {
									name : "price_all",
									index : "price_all",
									align : "center"
								}, {
									name : "place1",
									index : "place1",
									align : "center"
								}, {
									name : "lianxiren",
									index : "lianxiren",
									align : "center"
								}, {
									name : "place2",
									index : "place2",
									align : "center"
								}, {
									name : "phone",
									index : "phone",
									align : "center"
								}, {
									name : "chuanzhen",
									index : "chuanzhen",
									align : "center"
								}, {
									name : "email",
									index : "email",
									align : "center"
								}, {
									name : "staff",
									index : "staff",
									align : "center"
								}, {
									name : "note",
									index : "note",
									align : "center"
								}, {
									name : "flag",
									index : "flag",
									align : "center"
								}, {
									name : "weight",
									index : "weight",
									align : "center"
								}, {
									name : "weight_all",
									index : "weight_all",
									align : "center"
								}, {
									name : "bumenName",
									index : "bumen",
									align : "center"
								}, {
									name : "zjfCode",
									index : "zjfCode",
									align : "center"
								}, {
									name : "come_time",
									index : "come_time",
									align : "center"
								}, {
									name : "useful_life",
									index : "useful_life",
									align : "center"
								}, {
									name : "danweiName1",
									index : "danwei",
									align : "center"
								}, {
									name : "danweiname",
									index : "danweiname",
									align : "center"
								}, {
									name : "zupanhao",
									index : "zupanhao",
									align : "center"
								}, {
									name : "guige",
									index : "guige",
									align : "center"
								}, {
									name : "shenheName",
									index : "shenhe",
									align : "center"
								}, {
									name : "price_xs",
									index : "price_xs",
									align : "center"
								}, {
									name : "price_all_xs",
									index : "price_all_xs",
									align : "center"
								}, {
									name : "pinpaiName",
									index : "pinpai",
									align : "center"
								}, {
									name : "shenhefenName",
									index : "shenhe_fen",
									align : "center"
								}, {
									name : "createTime",
									index : "createTime",
									align : "center"
								} ],
								gridComplete : function() {
									$(".ui-jqgrid-sortable").css("text-align",
											"center");
								}
							});

					$("#chukuTable").grid(
							{
								url : _ctxPath + "/kucun/chuku/query.do",
								shrinkToFit : false,
								rowNum : 5,
								rowList : [ 3, 4,5 ],
								postData : $("#searchForm").formobj(),
								colNames : [ "单号", "物品类别", "产品编码", "产品名称",
										<c:if test='${filiale!=true }'>"进货价格",</c:if>"数量", "总价", "产品产地", "员工", "电话",
										"产品公司地址", "领用人", "备注", "外卖出库", "净含量",
										"总量", "部门", "助记符", "出库时间", "是否挂账",
										"出货到公司", "组盘号", "规格", "出库审核", "销售价格",
										"销售总价", "品牌", "分公入库审核", "日期" ],
								colModel : [ {
									name : "danhao",
									index : "danhao",
									align : "center"
								}, {
									name : "wupinName",
									index : "wupinId",
									align : "center"
								}, {
									name : "code",
									index : "code",
									align : "center"
								}, {
									name : "mingcheng",
									index : "mingcheng",
									align : "center"
								},<c:if test='${filiale!=true}'>
								 {
									name : "price",
									index : "price",
									align : "center"
								},</c:if> 
								{
									name : "num",
									index : "num",
									align : "center"
								}, {
									name : "price_all",
									index : "price_all",
									align : "center"
								}, {
									name : "place1",
									index : "place1",
									align : "center"
								}, {
									name : "staff",
									index : "staff",
									align : "center"
								}, {
									name : "phone",
									index : "phone",
									align : "center"
								}, {
									name : "place2",
									index : "place2",
									align : "center"
								}, {
									name : "lingyong",
									index : "lingyong",
									align : "center"
								}, {
									name : "note",
									index : "note",
									align : "center"
								}, {
									name : "flagName",
									index : "flag",
									align : "center"
								}, {
									name : "weight",
									index : "weight",
									align : "center"
								}, {
									name : "weight_all",
									index : "weight_all",
									align : "center"
								}, {
									name : "bumenName",
									index : "bumen",
									align : "center"
								}, {
									name : "zjfCode",
									index : "zjfCode",
									align : "center"
								}, {
									name : "come_time",
									index : "come_time",
									align : "center"
								}, {
									name : "guizhangName",
									index : "guazhang_flag",
									align : "center"
								}, {
									name : "danweiname",
									index : "danweiname",
									align : "center"
								}, {
									name : "zupanhao",
									index : "zupanhao",
									align : "center"
								}, {
									name : "guige",
									index : "guige",
									align : "center"
								}, {
									name : "shenheName",
									index : "shenhe",
									align : "center"
								}, {
									name : "price_xs",
									index : "price_xs",
									align : "center"
								}, {
									name : "price_all_xs",
									index : "price_all_xs",
									align : "center"
								}, {
									name : "pinpaiName",
									index : "pinpai",
									align : "center"
								}, {
									name : "shenhefenName",
									index : "shenhe_fen",
									align : "center"
								}, {
									name : "createTime",
									index : "createTime",
									align : "center"
								} ],
								gridComplete : function() {
									$(".ui-jqgrid-sortable").css("text-align",
											"center");
								}
							});
					$("#tuihuoTable").grid(
							{
								url : _ctxPath + "/kucun/tuihuo/query.do",
								shrinkToFit : false,
								rowNum : 5,
								rowList : [ 3,4, 5 ],
								postData : $("#searchForm").formobj(),
								colNames : [ "产品编码", "产品名称", "物品类别", "单价",
										"数量", "总价", "退货人", "外卖出库", "净含量", "总量",
										"部门", "备注", "退货时间" ],
								colModel : [ {
									name : "code",
									index : "code",
									align : "center"
								}, {
									name : "mingcheng",
									index : "mingcheng",
									align : "center"
								}, {
									name : "wupinName",
									index : "wupinId",
									align : "center"
								}, {
									name : "price",
									index : "price",
									align : "center"
								}, {
									name : "num",
									index : "num",
									align : "center"
								}, {
									name : "price_all",
									index : "price_all",
									align : "center"
								}, {
									name : "staff",
									index : "staff",
									align : "center"
								}, {
									name : "flagName",
									index : "flag",
									align : "center"
								}, {
									name : "weight",
									index : "weight",
									align : "center"
								}, {
									name : "weight_all",
									index : "weight_all",
									align : "center"
								}, {
									name : "bumenName",
									index : "bumen",
									align : "center"
								}, {
									name : "note",
									index : "note",
									align : "center"
								}, {
									name : "createTime",
									index : "createTime",
									align : "center"
								} ],
								gridComplete : function() {
									$(".ui-jqgrid-sortable").css("text-align",
											"center");
								}
							});
					//$(".minHeight .ui-jqgrid-bdiv").css("height", "60px");
					$(".minHeight .ui-jqgrid-bdiv").css("height", "170px");
					$("#ru").css("display","block");
					$("#chu").css("display","block");
					$("#tui").css("display","block");
			}
		</script>
</body>
</html>
