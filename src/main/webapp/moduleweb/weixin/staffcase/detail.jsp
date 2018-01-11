<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/moduleweb/resources/common.jsp"%>
<html>
<head>
<title></title>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>
							<a href="${ctxPath}/weixin/verify/staffcase/toQuery.do" class="btn-link"> <i
								class="fa fa-angle-double-left"></i>返回
							</a>
								删除案例
						</h5>
					</div>
					<div class="ibox-content">
						<c:if test="${returnStatus.status == 0}">
							<div class="alert alert-danger">
								<i class="fa fa-exclamation-triangle"></i>${returnStatus.message}</div>
						</c:if>
						<form id="case" method="post" action=""
							class="form-horizontal">
							<input type="hidden" id="_id" name="_id">
							<div class="form-group">
								<label class="col-sm-2 control-label">技师名称</label>
								<div class="col-sm-4">
									<input id="staffName" name="staffName" type="text" class="form-control"
										minlength="2" maxlength="50" disabled>
								</div>
								<label class="col-sm-2 control-label">案例类型</label>
								<div class="col-sm-4">
									<input id="type" name="type" type="text" class="form-control"
										minlength="2" maxlength="50" disabled>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">案例标题</label>
								<div class="col-sm-4">
									<input id="title" name="title" type="text" class="form-control"
										minlength="2" maxlength="50" disabled>
								</div>
								<label class="col-sm-2 control-label">案例价格</label>
								<div class="col-sm-4">
									<input id="price" name="price" type="text" class="form-control"
										minlength="2" maxlength="50" disabled>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">服务耗时</label>
								<div class="col-sm-4">
									<input id="consumeTime" name="consumeTime" type="text" class="form-control"
										minlength="2" maxlength="50" disabled>
								</div>
								<label class="col-sm-2 control-label">案例介绍</label>
								<div class="col-sm-4">
									<input id="desc" name="desc" type="text" class="form-control"
										minlength="2" maxlength="50" disabled>
								</div>
							</div>
							<c:forEach items="${ffcase.logo }" var="img" varStatus="status">
									<c:if test="${status.index%3==0}">
										<div class="form-group" id="caseImgs">
										<label class="col-sm-2 control-label"></label>
										<div class="col-sm-3">
										    <input type="hidden" value="${img }" >
										  	<div style="height:36px"></div>
											<img style="max-width:100%" src="${ossImageHost}${img }@!style400">
										</div>
										<c:if test="${status.last }">
											</div>
										</c:if>
									</c:if>
									<c:if test="${status.index%3!=0}">
										<c:if test="${status.index%3==2}">
											<div class="col-sm-3">
												<input type="hidden" value="${img }" >
												<button class="btn btn-primary" type="button" onClick="setCaseTopImg(this)">置顶</button>&nbsp;&nbsp;&nbsp;
												<button class="btn btn-danger" type="button" onClick="deleteCase(this)">删除单张</button>
												<img style="max-width:100%" src="${ossImageHost}${img }@!style400">
											</div>
											</div>
										</c:if>
										<c:if test="${status.index%3!=2}">
											<div class="col-sm-3">
											    <input type="hidden" value="${img }" >
												<button class="btn btn-primary" type="button" onClick="setCaseTopImg(this)">置顶</button>&nbsp;&nbsp;&nbsp;
												<button class="btn btn-danger" type="button" onClick="deleteCase(this)">删除单张</button>
												<img style="max-width:100%" src="${ossImageHost}${img }@!style400">
											</div>
											<c:if test="${status.last }">
											</div>
										</c:if>
										</c:if>
									</c:if>
							</c:forEach>
							<div class="hr-line-dashed"></div>
							<div class="form-group">
								<div class="col-sm-4 col-sm-offset-2">
									<button class="btn btn-primary" type="submit">删除</button>
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
		function deleteCase(img) {
		      var img = $($(img).parent()).find('input').val();
		      var _id = $('#_id').val();
		      console.info(_id);
		      $('#caseImgs').html('');
		      var html = '';
		      $.post(_ctxPath+'/weixin/verify/staffcase/delcaseImg.do',{'_id':_id,'img':img},function(res) {
		    	  for(var i = 0; i < res.data.length; i++) {
		    		  if(i==0) {
		    			   html +='<label class="col-sm-2 control-label"></label>';
		    		   }
		    		  html +='<div class="col-sm-3"><input type="hidden" value="'+res.data[i]+'">';
					    if(i!=0) {
					    	  html +='<button class="btn btn-primary" type="button" onClick="setCaseTopImg(this)">置顶</button>&nbsp;&nbsp;&nbsp;<button class="btn btn-danger" type="button" onClick="deleteCase(this)" >删除单张</button>';
					    } else {
					    	html +=	'<div style="height:36px"></div>';
					    }
					    html +='<img style="max-width:100%" src="${ossImageHost}' +res.data[i] + '@!style400"></div>';
		    	  }
		    	  $('#caseImgs').html(html);
		      }); 
		}
		
		function setCaseTopImg(img) {
			 var img = $($(img).parent()).find('input').val();
		      var _id = $('#_id').val();
		      console.info(_id);
		      $('#caseImgs').html('');
		      var html = '';
		      $.post(_ctxPath+'/weixin/verify/staffcase/setCaseTopImg.do',{'_id':_id,'img':img},function(res) {
		    	  for(var i = 0; i < res.data.length; i++) {
		    		   if(i==0) {
		    			   html +='<label class="col-sm-2 control-label"></label>';
		    		   }
		    		    html +='<div class="col-sm-3"><input type="hidden" value="'+res.data[i]+'">';
					    if(i!=0) {
					    	  html +='<button class="btn btn-primary" type="button" onClick="setCaseTopImg(this)">置顶</button>&nbsp;&nbsp;&nbsp;<button class="btn btn-danger" type="button" onClick="deleteCase(this)" >删除单张</button>';
					    } else {
					    	html +=	'<div style="height:36px"></div>';
					    }
					    html +='<img style="max-width:100%" src="${ossImageHost}' +res.data[i] + '@!style400"></div>';
		    	  }
		    	  $('#caseImgs').html(html);
		      }); 
		}
		$().ready(function() {
			$("#case").validate({
				submitHandler : function(form) {
					$.shade.show();
					$("#case").enable();
					var caseId = $("#_id").val();
					$("#case").attr("action",_ctxPath+"/weixin/verify/staffcase/detailDel.do?caseId="+caseId);
					form.submit();
				}
			});

			$("#cancelBtn").click(function() {
			    document.location.href = _ctxPath + "/weixin/verify/staffcase/toQuery.do";
			});
		});
	</script>
</body>
</html>
