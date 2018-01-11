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
				 		<form id="kaoqinleibie" method="post" action="${ctxPath}/kaoqin/upsertKaoqinleibie.do" class="form-horizontal">
				 			<input type="hidden" id="_id" name="kaoqinleibieId"/>
				 			<input type="hidden" id="code" name="code" value="0"/>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">考勤类别名称</label>
                                <div class="col-sm-8">
                                    <input id="names" name="names"  placeholder="考勤类别名称" type="text"
										class="form-control required">
                                </div>
                                <div class="col-sm-2">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">基数1(分钟)</label>
                                <div class="col-sm-8">
                                    <input id="base1" name="base1" placeholder="基数1(分钟)" type="number"
										class="form-control digits">
                                </div>
                                <div class="col-sm-2">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">扣罚(元)</label>
                                <div class="col-sm-8">
                                    <input id="money1" name="money1"  placeholder="扣罚(元)" type="number"
										class="form-control number">
                                </div>
                                <div class="col-sm-2">
                                </div>
                            </div>
				 			<div class="form-group">
                                <label class="col-sm-2 control-label">基数2(分钟)</label>
                                <div class="col-sm-8">
                                    <input id="base2" name="base2" placeholder="基数2(分钟)" type="number"
										class="form-control digits">
                                </div>
                                <div class="col-sm-2">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">扣罚(元)</label>
                                <div class="col-sm-8">
                                    <input id="money2" name="money2"   placeholder="扣罚(元)" type="number"
										class="form-control number">
                                </div>
                                <div class="col-sm-2">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">基数3(分钟)</label>
                                <div class="col-sm-8">
                                    <input id="base3" name="base3"   placeholder="基数3(分钟)" type="number"
										class="form-control digits">
                                </div>
                                <div class="col-sm-2">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">一次性扣罚(元)</label>
                                <div class="col-sm-8">
                                   <input id="money3" name="money3"   placeholder="一次性扣罚(元)" type="number"
										class="form-control number">
                                </div>
                                <div class="col-sm-2">
                                </div>
                            </div>
                             <div class="form-group">
                                <label class="col-sm-2 control-label">一次罚款</label>
                                <label class="col-sm-2 control-label" style="text-align: left;"><input id="guding_flag" name="guding_flag"  type="checkbox" >&nbsp;&nbsp;是否一次性罚款</label>
                                <div class="col-sm-8">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">备注</label>
                                <div class="col-sm-8">
                                   <input id="note" name="note" type="text"   placeholder="备注"
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
				 		<div>
				 		</div>
				 	</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$("#cancel").click(function() {
			window.location.href=_ctxPath+"/kaoqin/toKaoqinleibie.do";
		});
		
		$("#save").click(function() {
			$("#kaoqinleibie").validate();
		});
	</script>
</body>
</html>
