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
						<form method="post"  id="form" action="${ctxPath}/kaoqin/addBanci.do" class="form-horizontal">
							<input type="hidden" name="banciId"  value="${ bancidingyi._id }">
                            <div class="form-group">
                                <label class="col-sm-2 control-label">班次名称:</label>
                                <div class="col-sm-8">
                                    <input type="text" name="names" value="${ bancidingyi.names }" class="form-control required">
                                </div>
                                <div class="col-sm-2">
                                </div>
                            </div>
                             <div class="form-group">
                                <label class="col-sm-2 control-label">上班时间:</label>
                                <div class="col-sm-3">
                                    <input type="number"  id="time_a1_h" name="time_a1_h" value="${ time_a1_h }" class="form-control required">
                                </div>
                               <label class="col-sm-1 control-label" style="text-align: left;">小时</label>
                                <div class="col-sm-3">
                                    <input type="number" id="time_a1_m" name="time_a1_m" value="${ time_a1_m }" class="form-control required">
                                </div>
                                <label class="col-sm-1 control-label" style="text-align: left;" >分钟</label>
                                <div class="col-sm-2">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">是否考勤:</label>
                               <label class="col-sm-2 control-label" style="text-align: left;"><input name="kaoqin_a1" <c:if test="${bancidingyi.kaoqin_a1}">checked = "checked"</c:if> type="checkbox">&nbsp;&nbsp;考勤</label>
                                <div class="col-sm-8">
                                </div>
                            </div>
                             <div class="form-group">
                                <label class="col-sm-2 control-label">下班时间:</label>
                                <div class="col-sm-3">
                                    <input type="number" id="time_a2_h" name="time_a2_h" value="${ time_a2_h }" class="form-control required">
                                </div>
                               <label class="col-sm-1 control-label" style="text-align: left;">小时</label>
                                <div class="col-sm-3">
                                    <input type="number" id="time_a2_m" name="time_a2_m" value="${time_a2_m }"  class="form-control required">
                                </div>
                                <label class="col-sm-1 control-label" style="text-align: left;" >分钟</label>
                                <div class="col-sm-2">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">是否考勤:</label>
                                <label class="col-sm-2 control-label" style="text-align: left;"><input name="kaoqin_a2" <c:if test="${bancidingyi.kaoqin_a2}">checked = "checked"</c:if> type="checkbox" >&nbsp;&nbsp;考勤</label>
                                <div class="col-sm-8">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">是否跨天:</label>
                                <label class="col-sm-2 control-label" style="text-align: left;"><input name="kuatian" <c:if test="${bancidingyi.kuatian}">checked = "checked"</c:if> type="checkbox">&nbsp;&nbsp;跨天</label>
                                <div class="col-sm-8">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-4">
                                </div>
                               <div class="col-sm-2">
                               		<button class="btn btn-primary" id="addBanci" type="button"><strong>保存</strong></button>
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
	 	$(function() {
	 		$('#cancel').click(function(){
	 			window.location.href = _ctxPath + '/kaoqin/toBancidingyi.do';
	 		});
	 	});
	 	$('#addBanci').click(function() {
	 		$("#form").validate();
	 		var time_a1_h = $("#time_a1_h").val();
	 		var time_a1_m  = $("#time_a1_m").val();
	 		var time_a2_h = $("#time_a2_h").val();
	 		var time_a2_m  = $("#time_a2_m").val();
	 		var regH = /^(([0-1]\d)|(2[0-4]))$/;
	 		var regM = /^[0-5]\d$/;
	 		/* if(regH.test(time_a1_h)) {
	 		    alert("你输入的小时不合法！");
	 		    return;
	 		}
	 		if(regM.test(time_a1_m)) {
	 		    alert("你输入的分不合法！");
	 		    return;
	 		}
	 		if(regH.test(time_a2_h)) {
	 		    alert("你输入的小时不合法！");
	 		    return;
	 		}
	 		if(regM.test(time_a2_m)) {
	 		    alert("你输入的分不合法！");
	 		    return;
	 		} */
	 		$("#form").submit();
	 	});
	 	
	</script>
</body>
</html>
