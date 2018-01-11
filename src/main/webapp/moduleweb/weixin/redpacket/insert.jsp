<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/moduleweb/resources/common.jsp"%>
<html>
<head>
<title></title>
<style type="text/css">
.title{
	color:#337ab7;
	font-size:25px
}
.title span{
	color:#f4307b;
	font-size:25px
}
</style>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content  animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content">
					
						<form method="post"  id="form" action="${ctxPath}/weixin/redPacket/insertWeRed.do" class="form-horizontal">
                           	<div class="form-group">
                           	    <div class="col-sm-3">
                           	    </div>
                           	    <div class="col-sm-9">
                           	    	<h4 class="title">友情提示:<span>红包的金额必须保证每一个人0.01元</span></h4>
                           	    </div>
                            </div>
                           	<input type="hidden" id="type" name="type" value="1" >
                           	<input type="hidden" id="scope" name="scope" value="3" >
                           	<input type="hidden" id="senderId" name="senderId" value="0" >
                           	<input type="hidden" id="range" name="range" value="-1" >
                           	<input type="hidden" id="state" name="state" value="1" >
                            <div class="form-group">
                                <label class="col-sm-2 control-label">红包金额:</label>
                                <div class="col-sm-8">
                                    <input type="number" id="amount" name="amount" class="form-control  required">
                                </div>
                                <div class="col-sm-2">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">红包份数:</label>
                                <div class="col-sm-8">
                                    <input type="number" id="count" name="count" class="form-control digits required">
                                </div>
                                <div class="col-sm-2">
                                </div>
                            </div>
                             <div class="form-group">
                                <label class="col-sm-2 control-label">红包描述:</label>
                                <div class="col-sm-8">
                                    <input type="text" id="desc" name="desc" class="form-control required">
                                </div>
                                <div class="col-sm-2">
                                </div>
                            </div>
                             <div class="form-group">
                                <div class="col-sm-4">
                                </div>
                               <div class="col-sm-2">
                               		<button class="btn btn-primary" id="sendRedPacket" type="button"><strong>发送</strong></button>
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
	 			window.location.href = _ctxPath + '/weixin/redPacket/toRedPacket.do';
	 		});
	 		$('#sendRedPacket').click(function(){
	 			$('#form').validate();
	 			var amount = parseFloat($('#amount').val());
	 			//这里缺少一个正则验证
	 			var count = parseFloat($('#count').val());
	 			var tmp = amount /count;
	 			if(tmp >= 0.01) {
	 			   $('#form').submit();
	 			} else {
	 				alert('你输入的金额太少了，不能确保每个人0.01元');
	 			}
	 		});
	 	});
	</script>
</body>
</html>
