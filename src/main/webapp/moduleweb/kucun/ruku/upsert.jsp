<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/moduleweb/resources/common.jsp"%>
<html>
<head>
<style>
.maxHeight {
	height: 100%;
	border: 1px solid #ddd;
	overflow: auto;
	padding-bottom: 15px;
}

.maxWidth {
	width: 100%;
}

.form-control2 {
	width: 60%;
	display: inline;
}

.input-group .form-control2 {
	width: 60%;
	display: inline;
}

.input-group {
	display: inline;
}

.center {
	text-align: center;
}

.leftPadding {
	padding-left: 40px;
}

.form-control,.single-line {
	padding: 4px 6px;
}

.form-group {
	margin-bottom: 4px;
}

.hr-line-dashed {
	margin: 6px 0px;
}

.wrapper-content {
	padding: 0px;
}

.ibox-content {
	padding-top: 6px;
}

.ibox-title {
	padding-top: 6px;
	padding-bottom: 6px;
}
</style>
<title></title>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>
							<a href="${ctxPath}/kucun/ruku/toQuery.do" class="btn-link">
								<i class="fa fa-angle-double-left"></i>返回 </a>
							<c:if test="${ffruku._id == null}">
								新建入库信息
							</c:if>
							<c:if test="${ffruku._id != null}">修改入库信息
							</c:if>
						</h5>
					</div>
					<div class="ibox-content">
						<c:if test="${returnStatus.status == 0}">
							<div class="alert alert-danger">
								<i class="fa fa-exclamation-triangle"></i>${returnStatus.message}
							</div>
						</c:if>
						<form id="ruku" method="post"
							action="${ctxPath}/kucun/ruku/upsert.do" class="form-horizontal">
							<input type="hidden" id="_id" name="_id"> <input
								type="hidden" id="organId" name="organId">
							<div class="form-group">
								<label class="col-sm-2 control-label">物品类别</label>
								<div class="col-sm-4">
									<input type="text" class="form-control suggest" id="wupinId"
										name="wupinId"
										suggest="{data :fillmaps.leibies,searchFields : [ '_id' ],keyField : 'mingcheng'}">
								</div>
								<label class="col-sm-2 control-label">产品编码</label>
								<div class="col-sm-4">
									<input id="code" name="code" type="text" class="form-control"
										minlength="2" maxlength="50" required disabled="disabled">
								</div>

							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">产品名称</label>
								<div class="col-sm-4">
									<input id="mingcheng" name="mingcheng" type="text"
										class="form-control" minlength="2" maxlength="50" required
										disabled="disabled">
								</div>
								<label class="col-sm-2 control-label">品牌</label>
								<div class="col-sm-4">
									<input id="pinpaiName" name="pinpaiName" type="text"
										class="form-control" minlength="2" maxlength="50" required
										disabled="disabled"> <input type="hidden" id="pinpai"
										name="pinpai">
								</div>

							</div>
							<div class="form-group">
								<!-- 								<label class="col-sm-2 control-label">进货价格</label> -->
								<!-- 								<div class="col-sm-4"> -->
								<!-- 									<input id="price" name="price" type="number" class="form-control" -->
								<!-- 										 min="0" maxlength="50" required> -->
								<!-- 								</div> -->
								<input type="hidden" id="price" name="price"> <label
									class="col-sm-2 control-label">进货数量</label>
								<div class="col-sm-4">
									<input id="num" name="num" type="text" class="form-control"
										min="0" maxlength="50" required>
								</div>
								<!-- 								<label class="col-sm-2 control-label">总价</label> -->
								<!-- 								<div class="col-sm-4"> -->
								<!-- 									<input id="price_all" name="price_all" type="number" class="form-control" -->
								<!-- 										min="0" maxlength="50" > -->
								<!-- 								</div> -->
								<label class="col-sm-2 control-label">净含量</label>
								<div class="col-sm-4">
									<input id="weight" name="weight" type="number"
										class="form-control" min="0" maxlength="50">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">总量</label>
								<div class="col-sm-4">
									<input id="weight_all" name="weight_all" type="number"
										class="form-control" min="0" maxlength="50">
								</div>
								<label class="col-sm-2 control-label">产品产地</label>
								<div class="col-sm-4">
									<input id="place1" name="place1" type="text"
										class="form-control" minlength="2" maxlength="50">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">终端销售价格</label>
								<div class="col-sm-4">
									<input id="price_xs" name="price_xs" type="number"
										class="form-control" min="0" maxlength="50">
								</div>
								<label class="col-sm-2 control-label">终端销售总价</label>
								<div class="col-sm-4">
									<input id="price_all_xs" name="price_all_xs" type="number"
										class="form-control" min="0" maxlength="50">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">联系人</label>
								<div class="col-sm-4">
									<input id="lianxiren" name="lianxiren" type="text"
										class="form-control" minlength="2" maxlength="50">
								</div>
								<label class="col-sm-2 control-label">产品公司地址</label>
								<div class="col-sm-4">
									<input id="place2" name="place2" type="text"
										class="form-control" minlength="2" maxlength="50">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">电话</label>
								<div class="col-sm-4">
									<input id="phone" name="phone" type="text" class="form-control"
										minlength="2" maxlength="50" mobile="true">
								</div>
								<label class="col-sm-2 control-label">传真</label>
								<div class="col-sm-4">
									<input id="chuanzhen" name="chuanzhen" type="text"
										class="form-control" minlength="2" maxlength="50">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">邮箱</label>
								<div class="col-sm-4">
									<input id="email" name="email" type="email"
										class="form-control" maxlength="50">
								</div>
								<label class="col-sm-2 control-label">进货人</label>
								<div class="col-sm-4">
									<input id="staff" name="staff" type="text" class="form-control"
										minlength="2" maxlength="50">
								</div>
							</div>

							<div class="form-group"></div>
							<div class="form-group">
								<label class="col-sm-2 control-label">部门</label>
								<div class="col-sm-4">
									<select class="form-control" id="bumen" name="bumen">
										<option value="">请选择</option>
										<c:forEach items="${bumens}" var="bumen">
											<option value="${bumen._id}">${bumen.name}</option>
										</c:forEach>
									</select><span class="help-block m-b-none"></span>
								</div>
								<label class="col-sm-2 control-label">有效期</label>
								<div class="col-sm-4">
									<input id="useful_life" name="useful_life"
										class="laydate-icon form-control layer-date" placeholder=""
										data-mask="9999-99-99"
										laydate="{format : 'YYYY-MM-DD',min : '1938-06-16 23:59:59',choose: helloDate}">
								</div>
							</div>
							<div class="form-group">
								<!-- 								<label class="col-sm-2 control-label">进货时间</label> -->
								<!-- 								<div class="col-sm-4"> -->
								<!-- 											<input id="come_time" name="come_time" -->
								<!-- 												class="laydate-icon form-control layer-date" placeholder="" -->
								<!-- 												data-mask="9999-99-99" -->
								<!-- 												laydate="{format : 'YYYY-MM-DD',min : '1938-06-16 23:59:59',choose: helloDate}" required> -->
								<!-- 								</div> -->

							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">单位</label>
								<div class="col-sm-4">
									<select class="form-control" id="danwei" name="danwei">
										<option value="">请选择</option>
										<c:forEach items="${codes}" var="code">
											<option value="${code._id}">${code.name}</option>
										</c:forEach>
									</select>
								</div>
								<label class="col-sm-2 control-label">进货单位名称</label>
								<div class="col-sm-4">
									<input id="danweiname" name="danweiname" type="text"
										class="form-control" maxlength="50">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">组盘号</label>
								<div class="col-sm-4">
									<input id="zupanhao" name="zupanhao" type="text"
										class="form-control" min="0" maxlength="50">
								</div>
								<label class="col-sm-2 control-label">规格</label>
								<div class="col-sm-4">
									<input id="guige" name="guige" type="text" class="form-control"
										maxlength="50">
								</div>
							</div>


							<div class="form-group">
								<label class="col-sm-2 control-label">备注</label>
								<div class="col-sm-4">
									<input id="note" name="note" type="text" class="form-control"
										minlength="2" maxlength="50">
								</div>
								<label class="col-sm-2 control-label">单号</label>
								<div class="col-sm-4">
									<input id="danhao" name="danhao" type="text"
										disabled="disabled" class="form-control" value=""
										maxlength="50">
								</div>
							</div>

							<div class="hr-line-dashed"></div>
							<div class="form-group">
								<div class="col-sm-4 col-sm-offset-2">
									<button class="btn btn-primary" type="submit">提交</button>
									<button id="cancelBtn" class="btn btn-white" type="button">取消</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		function shijian() {

			var come_time = $("#come_time").val();
			var useful_life = $("#useful_life").val();
			if (come_time != "" && useful_life != "") {
				if (come_time < useful_life) {
					layer.alert("进货日期不能小于有效期");
					return;
				}
			}
		}
		function helloDate(d) {
			//alert(d);
		}
		$().ready(function() {
			shijian();
			$("#ruku").validate({
				submitHandler : function(form) {
					var wupinId = $("#wupinId").val();
					if ("" == wupinId) {
						toastr.error("物品类别不能为空！");
						return;
					}
					var num = $("#num").val();
					var reg = regNum(num);
					if ("" == reg) {
						$.shade.show();
						$("#ruku").enable();
						form.submit();
					} else {
						toastr.error(reg);
					}
				}
			});
			$("#cancelBtn").click(function() {
				document.location.href = _ctxPath + "/kucun/ruku/toQuery.do";
			});
			$("#price").change(function() {
				//jisuan();
			});
			$("#num").change(function() {
				jisuan();
			});
			$("#come_time").change(function() {
				//shijian();
			});
			$("#price_xs").change(function() {
				jisuan();
			});
			$("#weight").change(function() {
				jisuan();
			});
			$("#wupinId").change(function() {
				leibie();
			});
		});
		

		function leibie(f) {
			var lb = fillmaps.leibies[$("#wupinId").attr("selectedIndex")];
			if (!f) {
				if (lb) {
					$("#code").val(lb.code);
					$("#mingcheng").val(lb.mingcheng);
					$("#pinpaiName").val(lb.pinpaiName);
					$("#price_xs").val(lb.price_xs);
					$("#price").val(lb.price);
					$("#pinpai").val(lb.pinpai);

				}
			}
		}
		function regNum(num) {
			var regex = /^([1-9]\d{0,15}|0)(\.\d{1,4})?$/;
			if (!regex.test(num)) {
				return "进货数量格式不正确";
			} else {
				if (num <= 0) {
					return "进货数量不能为零";
				}
				return "";
			}
		}
		function jisuan() {
			var weight = $("#weight").val();
			var num = $("#num").val();
			if(regNum(num)==""){
				$("#price_all_xs").val($("#price_xs").val() * num);
			}
			if(regNum(weight)==""){
				$("#weight_all").val(weight * num);
			}
			

		}
	</script>
</body>
</html>
