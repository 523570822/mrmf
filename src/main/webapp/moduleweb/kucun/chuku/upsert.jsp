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

.form-control, .single-line {
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
							<a href="${ctxPath}/kucun/chuku/toQuery.do" class="btn-link"> <i
								class="fa fa-angle-double-left"></i>返回
							</a>
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
								<i class="fa fa-exclamation-triangle"></i>${returnStatus.message}</div>
						</c:if>
						<form id="chuku" method="post" action="${ctxPath}/kucun/chuku/upsert.do"
							class="form-horizontal">
							<input type="hidden" id="_id" name="_id">
							<input type="hidden" id="organId" name="organId">
							<div class="form-group">
							<label class="col-sm-2 control-label">物品类别</label>
								<div class="col-sm-4">
										<input type="text" class="form-control suggest"
												id="wupinId" name="wupinId"
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
									<input id="mingcheng" name="mingcheng" type="text" class="form-control"
										minlength="2" maxlength="50" required disabled="disabled">
								</div>
								<label class="col-sm-2 control-label">品牌</label>
								<div class="col-sm-4">
									<input id="pinpaiName" name="pinpaiName" type="text" class="form-control"
										minlength="2" maxlength="50" required disabled="disabled">
										<input type="hidden" id="pinpai" name="pinpai">
								</div>
								
							</div>
							<div class="form-group">
<!-- 								<label class="col-sm-2 control-label">进货价格</label> -->
<!-- 								<div class="col-sm-4"> -->
<!-- 									<input id="price" name="price" type="number" class="form-control" -->
<!-- 										 min="0" maxlength="50" required> -->
<!-- 								</div> -->
								<input type="hidden" id="price" name="price">
								<label class="col-sm-2 control-label">出货数量</label>
								<div class="col-sm-4">
									<input id="num" name="num" type="number" class="form-control"
										min="0" maxlength="50" >
								</div>
								<label class="col-sm-2 control-label">净含量</label>
								<div class="col-sm-4">
									<input id="weight" name="weight" type="number" class="form-control"
										min="0" maxlength="50" >
								</div>
							</div>
							<div class="form-group">
								
								<label class="col-sm-2 control-label">总量</label>
								<div class="col-sm-4">
									<input id="weight_all" name="weight_all" type="number" class="form-control"
										min="0" maxlength="50" required>
								</div>
								<label class="col-sm-2 control-label">出货价</label>
								<div class="col-sm-4">
									<input id="price_xs" name="price_xs" type="number" class="form-control"
										min="0" maxlength="50">
								</div>
							</div>
							<div class="form-group">
								
								<label class="col-sm-2 control-label">出货总价</label>
								<div class="col-sm-4">
									<input id="price_all_xs" name="price_all_xs" type="number" class="form-control"
										min="0" maxlength="50" >
								</div>
								<label class="col-sm-2 control-label">产品产地</label>
								<div class="col-sm-4">
									<input id="place1" name="place1" type="text" class="form-control"
										minlength="2" maxlength="50" >
								</div>
							</div>
							<div class="form-group">
<!-- 								<label class="col-sm-2 control-label">总价</label> -->
<!-- 								<div class="col-sm-4"> -->
<!-- 									<input id="price_all" name="price_all" type="number" class="form-control" -->
<!-- 										min="0" maxlength="50" required> -->
<!-- 								</div> -->
								
								<label class="col-sm-2 control-label">产品公司地址</label>
								<div class="col-sm-4">
									<input id="place2" name="place2" type="text" class="form-control"
										minlength="2" maxlength="50"  >
								</div>
								<label class="col-sm-2 control-label">员工</label>
								<div class="col-sm-4">
									<input id="staff" name="staff" type="text" class="form-control"
										minlength="2" maxlength="50" >
								</div>
							</div>
							<div class="form-group">
								
								<label class="col-sm-2 control-label">电话</label>
								<div class="col-sm-4">
									<input id="phone" name="phone" type="text" class="form-control"
										minlength="2" maxlength="50" mobile="true" >
								</div>
								<label class="col-sm-2 control-label">领用人</label>
								<div class="col-sm-4">
									<input id="lingyong" name="lingyong" type="text" class="form-control"
										minlength="2" maxlength="50" >
								</div>
							
							</div>
							
							
							<div class="form-group">
<!-- 								<label class="col-sm-2 control-label">助记符</label> -->
<!-- 								<div class="col-sm-4"> -->
<!-- 									<input id="zjfCode" name="zjfCode" type="text" class="form-control" -->
<!-- 										minlength="2" maxlength="50" > -->
<!-- 								</div> -->
								<label class="col-sm-2 control-label">部门</label>
								<div class="col-sm-4">
									<select class="form-control" id="bumen" name="bumen">
										<option value="">请选择</option>
										<c:forEach items="${bumens}" var="bumen">
											<option value="${bumen._id}">${bumen.name}</option>
										</c:forEach>
									</select><span class="help-block m-b-none"></span>
								</div>
								<label class="col-sm-2 control-label">是否挂账</label>
								<div class="col-sm-4">
											<select class="form-control" id="guazhang_flag" name="guazhang_flag">
													<option value="">请选择</option>
													<option value="true">是</option>
													<option value="false">否</option>
										</select><span class="help-block m-b-none"></span>
									</div>
							</div>
							<div class="form-group">
<!-- 								<label class="col-sm-2 control-label">出库时间</label> -->
<!-- 								<div class="col-sm-4"> -->
<!-- 											<input id="come_time" name="come_time" -->
<!-- 												class="laydate-icon form-control layer-date" placeholder="" -->
<!-- 												data-mask="9999-99-99" -->
<!-- 												laydate="{format : 'YYYY-MM-DD',min : '1938-06-16 23:59:59',choose: helloDate}" required> -->
<!-- 								</div> -->
								
							</div>
							
							<div class="form-group">
								<label class="col-sm-2 control-label">组盘号</label>
								<div class="col-sm-4">
									<input id="zupanhao" name="zupanhao" type="text" class="form-control"
										 min="0" maxlength="50"  >
								</div>
								<label class="col-sm-2 control-label">规格</label>
								<div class="col-sm-4">
									<input id="guige" name="guige" type="text" class="form-control"
										 maxlength="50" >
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-2 control-label">备注</label>
								<div class="col-sm-4">
									<input id="note" name="note" type="text" class="form-control"
										 maxlength="50"  >
								</div>
								<c:if test="${organType=='HQ'}">
									<label class="col-sm-2 control-label" id="company_label" >出货到公司</label>
									<div class="col-sm-4" id="company_div">
										<select class="form-control" id="danwei" name="danwei">
											<option value="">请选择</option>
											<c:forEach items="${branch}" var="b">
												<option value="${b._id}">${b.name}</option>
											</c:forEach>
										</select><span class="help-block m-b-none"></span>
									</div>
									</c:if>
<!-- 								<label class="col-sm-2 control-label">外卖出库</label> -->
<!-- 								<div class="col-sm-4"> -->
<!-- 									<select class="form-control" id="flag" name="flag"> -->
<!--         									<option value="false">否</option> -->
        									<input type="hidden" value="false" id="flag" name="flag" />
<!-- 										</select><span class="help-block m-b-none"></span> -->
<!-- 								</div> -->
							</div>
							
								<div class="form-group" id="company_group">
								    <label class="col-sm-2 control-label">单号</label>
								<div class="col-sm-4">
									<input id="danhao" name="danhao" type="text" disabled="disabled" class="form-control"
										value="" maxlength="50">
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
	function shijian(){
		
		 var come_time=$("#come_time").val();
		 var useful_life=$("#useful_life").val();
		 if(come_time!=""&&useful_life!=""){
		 	if(come_time<useful_life){
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
			$("#chuku").validate({
				submitHandler : function(form) {
				var wupinId=$("#wupinId").val();
				if(""==wupinId){
				toastr.error("物品类别不能为空！");
				return;
				}
				var num=$("#num").val();
				var reg=regNum(num);
					if(""==reg){
						$.shade.show();
						$("#chuku").enable();
						form.submit();
					}else{
					toastr.error(reg);
					}
					
				}
			});
			$("#cancelBtn").click(function() {
				document.location.href = _ctxPath + "/kucun/chuku/toQuery.do";
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
			$("#useful_life").change(function() {
								//shijian();
			});
			$("#weight").change(function() {
								jisuan();
			});
			$("#flag").change(function() {
								//branch(this);
			});
			$("#wupinId").change(function() {
								leibie();
							});
			$("#price_xs").change(function(){
				jisuan();
			});
			
		});
		/*function branch(t){
			var flag=$(t).val();
			if(flag=="false"){
				$("#company_label").css("display","block");
				$("#company_div").css("display","block");
			}else{
				$("#company_label").css("display","none");
				$("#company_div").css("display","none");
			}
		}*/
		
		function leibie(f){
		var lb = fillmaps.leibies[$("#wupinId")
										.attr("selectedIndex")];
			if (!f) {
					if (lb) {
						$("#code").val(lb.code);
						$("#mingcheng").val(lb.mingcheng);
						$("#pinpaiName").val(lb.pinpaiName);
						$("#price_xs").val(lb.price_ch);
						$("#price").val(lb.price);
						$("#pinpai").val(lb._id);
						
					}
			}
		}
		function regNum(num){
		var regex = /^([1-9]\d{0,15}|0)(\.\d{1,4})?$/;
			if(!regex.test(num)){
				return "出货数量格式不正确";
			}else{
				if(num<=0){
				 return "出货数量不能为零";
				}
			    return "";
			}
		}
		function jisuan(){
			var price=$("#price_xs").val();
			var num=$("#num").val();
			var weight=$("#weight").val();
			//alert(price*num);
			if(regNum(num)==""){
			$("#price_all_xs").val(price*num);
			}	
			if(regNum(weight)==""&&regNum(num)==""){
				$("#weight_all").val(weight*num);
			}
			
			
		}
	</script>
</body>
</html>
