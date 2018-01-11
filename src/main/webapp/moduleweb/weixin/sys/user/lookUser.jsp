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
					<div class="ibox-title">
						<h5>
							<a href="${ctxPath}/weixin/sys/user/userToQuery.do" class="btn-link"> <i
								class="fa fa-angle-double-left"></i>返回
							</a>
						</h5>
					</div>
					<div class="ibox-content">
		
						<div class="row" style="margin-top:20px;">  
							<div class="col-sm-12">                      
				                <ul class="nav nav-tabs">
								   <li class="active"><a href="#">基本信息</a></li>
								   <li><a href="${ctxPath}/weixin/sys/user/lookUserOrder.do?userId=${user._id }">预约信息</a></li>
								</ul>
							</div> 
				        </div>
						<div class="row" style="margin-top: 20px;">
							<div class="panel panel-primary">
							  <div class="panel-heading">
							    <h3 class="panel-title">基本信息</h3>
							  </div>
							  <div class="panel-body">
							    <div class="row" style="margin:10px;">
									<div class="col-sm-3">头像：<img width="100px;" height="100px;" <c:if test="${user.avatar != null && user.avatar != ''}">src="${ossImageHost}${user.avatar}"</c:if>></div>
									<div class="col-sm-3">注册手机号：${user.phone }</div>
									<div class="col-sm-3">昵称：${user.nick }</div>
								</div>
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
