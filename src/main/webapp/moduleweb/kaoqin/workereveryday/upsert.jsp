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
				<div class="ibox">
				   <div class="ibox-content">
				 		<form id="qingjiadengji" method="post" action="${ctxPath}/kaoqin/upsertQingjiadengji.do" class="form-horizontal">
				 			<input type="hidden" value="${qingjiadengji._id}"  id="_id" name="qingjiadengjiId"/>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">请假人姓名:</label>
                                <div class="col-sm-3">
                                    <select class="form-control form-control2 required" name="names">
										<option value="">请选择</option>
										<c:forEach var="staff" items="${staffs}">
											<c:choose>
												<c:when test="${staff._id == ffqingjiadengji.names }">
													<option selected="selected" value="${staff._id}">${staff.name}</option>
												</c:when>
												<c:otherwise>
													<option value="${staff._id}">${staff.name}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
                                </div>
                                <div class="col-sm-2">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">请假开始日期:</label>
                                <div class="col-sm-3">
                                    <input id="date1" name="date1" class="laydate-icon form-control required" placeholder="请假开始日期">
                                </div>
                                <label class="col-sm-2 control-label">请假结束日期:</label>
                                <div class="col-sm-3">
                                    <input id="date3" name="date3" class="laydate-icon form-control required" placeholder="请假结束日期">
                                </div>
                                <div class="col-sm-2">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">请假类型</label>
                                <div class="col-sm-8">
                                   <select class="form-control form-control2 required" name="type1">
										<option value="">请选择</option>
										<c:forEach var="kaoqinleibie" items="${kaoqinleibies}">
											<c:choose>
												<c:when test="${kaoqinleibie._id == ffqingjiadengji.type1 }">
													<option selected="selected" value="${kaoqinleibie._id}">${kaoqinleibie.names}</option>
												</c:when>
												<c:otherwise>
													<option value="${kaoqinleibie._id}">${kaoqinleibie.names}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
                                </div>
                                <div class="col-sm-2">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">扣罚金额:</label>
                                <div class="col-sm-8">
                                    <input id="money_koufa" name="money_koufa"  placeholder="扣罚金额" type="number"
										class="form-control number">
                                </div>
                                <div class="col-sm-2">
                                </div>
                            </div>
                             <div class="form-group">
                                <label class="col-sm-2 control-label">是否早班:</label>
                                <label class="col-sm-2 control-label" style="text-align:left"><input id="zao_flag" name="zao_flag" type="checkbox">&nbsp;是否早班</label>
                                <label class="col-sm-2 control-label">是否晚班:</label>
                                <label class="col-sm-2 control-label" style="text-align:left"><input id="wan_flag" name="wan_flag" type="checkbox">&nbsp;是否晚班</label>
                                <div class="col-sm-2">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">请假理由:</label>
                                <div class="col-sm-8">
                                    <input id="reason" name="reason" placeholder="请假理由" type="text"
										class="form-control">
                                </div>
                                <div class="col-sm-2">
                                </div>
                            </div>
                            <div class="form-group">
                               <div class="col-sm-4">
                                </div>
                               <div class="col-sm-2">
                               		<button class="btn btn-primary" id="save" type="submit"><strong>保存</strong></button>
                                </div>
                                <div class="col-sm-2">
                               		<button class="btn btn-danger" id="cancel" type="button"><strong>取消</strong></button>
                                </div>
                                <div class="col-sm-4">
                                </div>
                            </div>
				 		</form>
				 	</div>
				</div>
			</div>
		</div>
	</div>
	
	<script type="text/javascript">
		$("#cancel").click(function() {
			window.location.href=_ctxPath+"/kaoqin/toWorkereveryday.do";
		});
		var start = {
		    elem: '#date1',
		    format: 'YYYY-MM-DD hh:mm:ss',
		    min: laydate.now(), //设定最小日期为当前日期
		    max: '2099-06-16 23:59:59', //最大日期
		    istime: true,
		    istoday: false,
		    choose: function(datas){
		         end.min = datas; //开始日选好后，重置结束日的最小日期
		         end.start = datas; //将结束日的初始值设定为开始日
		    }
		};
		var end = {
		    elem: '#date3',
		    format: 'YYYY-MM-DD hh:mm:ss',
		    min: laydate.now(),
		    max: '2099-06-16 23:59:59',
		    istime: true,
		    istoday: false,
		    choose: function(datas){
		        start.max = datas; //结束日选好后，重置开始日的最大日期
		    }
		};
		laydate(start);
		laydate(end);
		$('#qingjiadengji').validate();
	</script>
</body>
</html>
