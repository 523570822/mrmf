<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/moduleweb/resources/common.jsp"%>
<html>
<head>
<title></title>
<style type="text/css">
	.font_right {
		text-align: right;
	}
</style>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>
							<a href="${ctxPath}/weixin/sys/user/staffToQuery.do" class="btn-link"> <i
								class="fa fa-angle-double-left"></i>返回
							</a>
						</h5>
					</div>
					<div class="ibox-content">
						<div class="row">
							<div class="panel panel-primary">
							  <div class="panel-heading">
							    <h3 class="panel-title">基本信息</h3>
							  </div>
							  <div class="panel-body">
							    <div class="row" style="margin:10px;">
									<div class="col-sm-3 font_right">头像：</div>
									<div class="col-sm-3"><img width="50px;" height="50px;" <c:if test="${staff.logo != null && staff.logo != ''}">src="${ossImageHost}${staff.logo}"</c:if>></div>
									<div class="col-sm-3 font_right">注册手机号：</div>
									<div class="col-sm-3">${staff.phone }</div>
								</div>
							    <div class="row" style="margin:10px;">
									<div class="col-sm-3 font_right">姓名：</div>
									<div class="col-sm-3">${staff.name }</div>
									<div class="col-sm-3 font_right">性别：</div>
									<div class="col-sm-3">${staff.sex }</div>
								</div>
							    <div class="row" style="margin:10px;">
									<div class="col-sm-3 font_right">昵称：</div>
									<div class="col-sm-3">${staff.nick }</div>
									<div class="col-sm-3 font_right">身份证号：</div>
									<div class="col-sm-3">${staff.idcard }</div>
								</div>
							    <div class="row" style="margin:10px;">
									<div class="col-sm-3 font_right">资格证号：</div>
									<div class="col-sm-3">${staff.certNumber }</div>
									<div class="col-sm-3 font_right">联系地址：</div>
									<div class="col-sm-3">${staff.home }</div>
								</div>
							    <div class="row" style="margin:10px;">
									<div class="col-sm-3 font_right">技师特长：</div>
									<div class="col-sm-3">${staff.jishiTechang }</div>
									<div class="col-sm-3 font_right">工作年限：</div>
									<div class="col-sm-3">${staff.workYears }</div>
								</div>
							  </div>
							</div>
						</div>
						<div class="row">
							<div class="panel panel-primary">
							  <div class="panel-heading">
							    <h3 class="panel-title">店铺信息</h3>
							  </div>
							  <div class="panel-body">
							  	<table class="table table-striped table-hover table-condensed">
							  		<thead>
							  			<th>序号</th>
							  			<th>归属店铺</th>
							  			<th>城市</th>
							  			<th>区</th>
							  			<th>商圈</th>
							  			<th>地址</th>
							  		</thead>
							  		<tbody>
							  			<c:forEach items="${organL }" var="organ" varStatus="i">
							  				<tr>
							  					<td>${i.index+1 }</td>
							  					<td>${organ.name }</td>
							  					<td>${organ.city }</td>
							  					<td>${organ.district }</td>
							  					<td>${organ.region }</td>
							  					<td>${organ.address }</td>
							  				</tr>
							  			</c:forEach>
							  		</tbody>
							  	</table>
							  </div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
