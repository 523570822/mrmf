<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/moduleweb/resources/common.jsp"%>
<html>
<head>
<title></title>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content  animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content">
					    <form class="form-horizontal"  style="border:1px solid #1ab394">
							<div class="row" style="margin-top:10px">
								<div class="col-sm-12">
									<div class="form-group">
										<label class="col-sm-2 control-label">头像：</label>
										<div class="col-sm-3">
											<img style="height:80px;width:80px" src="${ossImageHost}${staff.logo}">
										</div>
									</div>
								</div>
						     </div>
						     <div class="row">
								<div class="col-sm-12">
									<div class="form-group">
										<label class="col-sm-2 control-label">姓名：</label>
										<div class="col-sm-3">
											<input value="${ staff.name }" name="name" readonly="readonly" type= "text" class="form-control">
										</div>
										<label class="col-sm-2 control-label">注册手机号：</label>
										<div class="col-sm-3">
											<input   value="${ staff.phone }"  name="phone" readonly="readonly" type= "text" class="form-control">
										</div>
									</div>
								</div>
							</div>
							 <div class="row">
								<div class="col-sm-12">
									<div class="form-group">
										<label class="col-sm-2 control-label">昵称：</label>
										<div class="col-sm-3">
											<input  value="${ staff.nick }" name="nick" readonly="readonly" type= "text" class="form-control">
										</div>
										<label class="col-sm-2 control-label">性别：</label>
										<div class="col-sm-3">
											<input  value="${ staff.sex }"   name="sex" readonly="readonly" type= "text" class="form-control">
										</div>
									</div>
								</div>
							</div>
							 <div class="row">
								<div class="col-sm-12">
									<div class="form-group">
										<label class="col-sm-2 control-label">资格证号：</label>
										<div class="col-sm-3">
											<input value="${ staff.certNumber }"  name="certNumber" readonly="readonly" type= "text" class="form-control">
										</div>
										<label class="col-sm-2 control-label">身份证号：</label>
										<div class="col-sm-3">
											<input  value="${ staff.idcard }" name="idcard" readonly="readonly" type= "text" class="form-control">
										</div>
									</div>
								</div>
							</div>
							 <div class="row">
								<div class="col-sm-12">
									<div class="form-group">
										<label class="col-sm-2 control-label">技师特长：</label>
										<div class="col-sm-3">
											<input  value="${ staff.jishiTechang }" name="jishiTechang" readonly="readonly" type= "text" class="form-control">
										</div>
										<label class="col-sm-2 control-label">联系地址：</label>
										<div class="col-sm-3">
											<input value="${ staff.home }"  name="home" readonly="readonly" type= "text" class="form-control">
										</div>
									</div>
								</div>
							</div>
						</form>
						 <div class="form-horizontal" style="border:1px solid #1ab394;margin-top:10px">
						  	<div class="row" style="margin-top:10px">
								<div class="col-sm-12">
									<div class="form-group">
										<label  class="col-sm-2 control-label">审核结果:</label>
										<div  class="col-sm-3">
											<select style="heihgt:100%;width:100%;" id="checkResult" name="checkResult" class="form-control"> 
		                                        <option value="1">通过</option>
		                                        <option value="-1">未通过</option>
		                                         <!--1:通过，-1:不通过  class="form-control m-b" -->
                                           </select>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-12">
									<div class="form-group">
										<label class="col-sm-2 control-label">审核意见:</label>
										<div class="col-sm-3">
                                            <textarea class="form-control" style="height:100px;width:100%;" id="memo" name="memo"></textarea>
										</div>
									</div>
								</div>
							</div>
					     </div>
   
					     <div class="form-horizontal">
					     	<div class="row" style="margin-top:10px">
								<div class="col-sm-12">
									<div class="form-group">
										<div class="col-sm-3"></div>
										<div class="col-sm-3">
											 <button id="save" class="btn btn-primary">保存</button>
										</div>
										<div class="col-sm-1"></div>
										<div class="col-sm-3">
										     <button id ="cancel" class="btn btn-primary">返回</button>
										</div>
									</div>
								</div>
							</div>
					     </div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<input type="hidden" value="${organId}"  id="organId" >
	<input type="hidden" value="${ staff._id }"  id="staffId" >
	<script type="text/javascript">
			$("#cancel").click(function() {
				window.location.href = _ctxPath+"/weixin/organ/user/queryStaffCheck.do";
			});
			 $("#save").click(function() {
				var organId = $.trim($("#organId").val());
				var staffId = $.trim($("#staffId").val());
				var checkResult = $.trim($("#checkResult").val());
				var memo = $.trim($("#memo").val());
				$.post(_ctxPath + "/weixin/organ/user/commitStaffCheck.do",{"organId":organId,"staffId":staffId,"checkResult":checkResult,"memo":memo},
				  function(data){ 
				     if(data == "保存成功") {
				     	window.location.href = _ctxPath+"/weixin/organ/user/queryStaffCheck.do";
				     } else {
				        layer.alert(data);
				     }
				});
			});
	</script>
</body>
</html>
