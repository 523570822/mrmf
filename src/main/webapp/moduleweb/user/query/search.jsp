<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/moduleweb/resources/common.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style>
	.divwidth{
		height:25px;
		line-height:25px;
	}
	#matchdiv {
		max-width:100px;
	}
	#matchdiv label {
		white-space:nowrap;
	}
	.selectSearch{
		background-color:#eee;
	}
	</style>
	<script src="${ctxPath}/moduleweb/resources/js/search/search.js"></script>
  </head>
  
  <body>
   <div id="searchTable" class="wrapper wrapper-content animated fadeInRight" style="height:590px;display:none;width:60%;z-index:9999;background-color:#ccc;position:absolute;top:20%;left:20%;">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
				<div class="ibox-title">
						<div class="row">
							<div class="col-sm-3">
								<h5>综合查询</h5>
							</div>
							<div class="closethis" style="cursor:pointer;width:24px;;height:24px;float:right;margin-right:2%;background: url(${ctxPath}/moduleweb/resources/images/closesmall.png) no-repeat">
							</div>
						</div>
					</div>
					<div class="ibox-content">
					<div class="row">
						<table id="searchTable " class="table table-bordered">
							<thead class="table-bordered">
								<tr>
								<th>查询内容</th>
								<th>操作符</th>
								<th>匹配内容</th>
								<th></th>
								<th>已形成的条件</th>
								</tr>
							</thead>
							<tbody class="table-bordered">
								<tr >
									<td style="width:20%;height:300px;vertical-align:top;">
										<input  id="searchContent" name="searchContent" type="text"
										class="form-control" disabled="disabled">
										<input id="searchHidden" value="" type="hidden"/>
										<div id="displayCondition" class="form-group pre-scrollable" style="height:300px;">
										</div>
									</td>
									<td style="width:20%;vertical-align:top;" >
										<input id="handle" name="handle" type="text"
										class="form-control" disabled="disabled"/>
										<input id="handelhidden" value="" type="hidden"/>
										<div id="handlediv" class="form-group pre-scrollable" style="height:300px;">
										</div>
									</td>
									<td style="width:20%;vertical-align:top;" >
										<div id="matchContentdiv">
											<input id="matchContent" name="matchContent" type="text"
											class="form-control">
											<input id="matchhidden" value="" type="hidden"/>
											<input id="matchhiddenID" value="" type="hidden"/>
										</div>
										<div id="matchdiv" class="form-group pre-scrollable" style="height:300px;">
										</div>
									</td>
									<td style="width:20%;vertical-align:top;text-align:center;" >
									<div class="control-label ">
										<label class="control-label"><input  name="connector" type="radio" checked="checked" value="and">并且</label>
										</div>
										<div class="control-label">
										<label class="control-label"><input  name="connector" type="radio"  value="or">或者</label>
										</div>
										<div class="control-label ">
											<button id="searchAdd" class="btn btn-primary" type="button">添加>></button>
										</div>
										<div class="control-label ">
											<button id="searchDel" class="btn btn-primary" type="button"><<删除</button>
										</div>
										<div class="control-label ">
											<button id="searchUp" class="btn btn-primary" type="button">&nbsp;&nbsp;上移&nbsp;&nbsp;</button>
										</div>
										<div class="control-label">
											<button id="searchDown" class="btn btn-primary" type="button">&nbsp;&nbsp;下移&nbsp;&nbsp;</button>
										</div>
										<div class="control-label">
											<button id="searchClear" class="btn btn-primary" type="button">&nbsp;&nbsp;清空&nbsp;&nbsp;</button>
										</div>
									</td>
									<td style="width:20%;vertical-align:top;" >
										<input id="searchDivHid" type="hidden" value=""/>
										<div id="searchDiv" class="control-label ">
										</div>
									</td>
								</tr>
							</tbody>
						</table>
						<div class="form-group">
								<div class="col-sm-3 col-sm-offset-9">
									<button id="searchbutton" class="btn btn-primary" type="button">查询</button>
									<button id="cancelBtn" class="btn btn-white closethis" type="button">取消</button>
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
