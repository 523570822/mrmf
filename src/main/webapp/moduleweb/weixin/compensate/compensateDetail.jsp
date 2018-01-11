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
				 		<form id="weUserCompensate" method="post" action="${ctxPath}/weixin/sysConfig/upsertCompensate.do" class="form-horizontal">
				 			<input type="hidden" id="_id" name="_id"/>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">服务项目:</label>
                                <div class="col-sm-10">
                                    <input id="service" name="service"  type="text" readonly="readonly"
										class="form-control">
                                </div>
                            </div>
                             <div class="form-group">
                                <label class="col-sm-2 control-label">技师/店铺名称:</label>
                                <div class="col-sm-10">
                                    <input id="providerName" name="providerName"  type="text" readonly="readonly"
										class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">赔付类型:</label>
                                <div class="col-sm-10">
                                    <input id="type" name="type"  type="text" readonly="readonly"
										class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">用户赔付描述:</label>
                                <div class="col-sm-10">
                                    <input id="desc" name="desc"  type="text" readonly="readonly"
										class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">申请者手机号:</label>
                                <div class="col-sm-10">
                                    <input id="phone" name="phone"  type="text" readonly="readonly"
										class="form-control" >
                                </div>
                            </div>
                             <div class="form-group">
                                <label class="col-sm-2 control-label">赔付申请时间:</label>
                                <div class="col-sm-10">
                                    <input id="createTime" name="createTime"  type="text" readonly="readonly"
										class="form-control">
                                </div>
                            </div>
                            <!-- 这里可以回填吗？ -->
                             <div class="form-group">
                                <label class="col-sm-2 control-label">理赔图片:</label>
                                <div class="col-sm-10">
                                     <ul>
                                    	 <c:forEach  items="${ffweUserCompensate.images}" var="img">
                                    	    <li style="width:200px;height:200px; margin-left:6px;float:left; list-style:none;">
                                     	       <img src="${ossImageHost}${img}@!avatar" style="width:200px;height:200px;">
                                     	    </li>
                                     	 </c:forEach>
                                     </ul>
                                </div>
                            </div>
                            <c:choose>
		                        <c:when test="${state == 0}">
		                        	 <div class="form-group">
                                       <label class="col-sm-2 control-label">理赔结果:</label>
                                       <div class="col-sm-10">
                                       <input id="result" name="result"  type="text" 
										     class="form-control required">
                                       </div>	
                                    </div>
                                    <div class="form-group">
                                       <label class="col-sm-2 control-label">理赔描述:</label>
                                       <div class="col-sm-10">
                                         <input id="resultDesc" name="resultDesc"  type="text" 
										     class="form-control required">
                                       </div>	
                                    </div> 
                                    <div class="form-group">
		                                  <div class="col-sm-4">
		                                  </div>
			                              <div class="col-sm-2">
			                               		<button class="btn btn-primary" id="save" type="button"><strong>提交</strong></button>
			                              </div>
			                              <div class="col-sm-2">
			                               		<button class="btn btn-danger" id="cancel" type="button"><strong>返回</strong></button>
			                              </div>
			                              <div class="col-sm-4">
			                              </div>
	                                 </div>
		                         </c:when>
		                         <c:otherwise>
		                             <div class="form-group">
                                       <label class="col-sm-2 control-label">理赔结果:</label>
                                       <div class="col-sm-10">
                                       <input id="result" name="result"  type="text" readonly="readonly"
										     class="form-control required">
                                       </div>	
                                    </div>
                                    <div class="form-group">
                                       <label class="col-sm-2 control-label">理赔描述:</label>
                                       <div class="col-sm-10">
                                         <input id="resultDesc" name="resultDesc"  type="text" readonly="readonly"
										     class="form-control required">
                                       </div>	
                                    </div>
		                         	<div class="form-group">
		                                  <div class="col-sm-5">
		                                  </div>
			                              <div class="col-sm-2">
			                               		<button class="btn btn-danger" id="cancel" type="button"><strong>返回</strong></button>
			                              </div>
			                              <div class="col-sm-5">
			                              </div>
	                                 </div>
		                         </c:otherwise>
                            </c:choose>
                       </form>
				 	</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	$().ready(function() {
	    $("#cancel").click(function() {
	    	window.location.href= _ctxPath + '/weixin/sysConfig/toCompensate.do';
	    });
	    $("#save").click(function() {
	    	$("#weUserCompensate").validate();
	    	$("#weUserCompensate").submit();
	    });
    });
	</script>
</body>
</html>
