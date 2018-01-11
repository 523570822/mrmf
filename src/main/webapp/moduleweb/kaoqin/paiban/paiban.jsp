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
						<div class="row">
							<div class="col-sm-3">
								<h5>排班管理</h5>
							</div>
						</div>
					</div>
					<div class="ibox-content">
						<div class="row">
							<div class="col-sm-5">
								<div class="jqGrid_wrapper">
									<table id="staffTable"></table>
								</div>
							</div>
							<div class="col-sm-2" style="height:450px; border:1px solid #ddd" >
								<label class="col-sm-12 control-label"><h4>排班班次选择</h4></label>
								<div class="col-sm-12">
									<c:forEach items="${ kBancidingyis }" var="bancidingyi" varStatus="status">
										<c:choose>
											<c:when test="${ status.count == 1}">
												<label class="control-label col-sm-12"><input id="banci" name="banci" type="radio" checked="checked" value="${bancidingyi._id}">${bancidingyi.names}</label>
											</c:when>
											<c:otherwise>
												<label class="control-label col-sm-12"><input id="banci" name="banci" type="radio" value="${bancidingyi._id}">${bancidingyi.names}</label>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</div>
							</div>
							<div class="col-sm-5">
							    <div class="col-sm-3">
								</div>
								<div class="col-sm-9">
									<h4 style="color:red" id="paibanTitle">目前没有可以排班的人员!!</h4>
								</div>
								<div id='calendar'></div>
								<div style="margin-top:25px;margin-left:20px">
									<button id="save" class="btn btn-primary" type="button"><strong>保存员工排班</strong></button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<form id="form" >
	    <input id="organId" name="organId" type="hidden" value="${ organId }" >
		<input id="staffIds" name="staffIds" type="hidden" >
		<input id="staffNames" name="staffNames" type="hidden" >
		<input  id="yearmonth" name="yearmonth" type="hidden" >
		<input  id="day01" name="day01" type="hidden" >
		<input  id="day02" name="day02" type="hidden" >
		<input  id="day03" name="day03" type="hidden" >
		<input  id="day04" name="day04" type="hidden" >
		<input  id="day05" name="day05" type="hidden" >
		<input  id="day06" name="day06" type="hidden" >
		<input  id="day07" name="day07" type="hidden" >
		<input  id="day08" name="day08" type="hidden" >
		<input  id="day09" name="day09" type="hidden" >
		<input  id="day10" name="day10" type="hidden" >
		<input  id="day11" name="day11" type="hidden" >
		<input  id="day12"  name="day12" type="hidden" >
		<input  id="day13"   name="day13" type="hidden" >
		<input  id="day14"   name="day14" type="hidden" >
		<input id="day15"  name="day15" type="hidden" >
		<input id="day16" name="day16" type="hidden" >
		<input id="day17" name="day17" type="hidden"  >
		<input id="day18"  name="day18" type="hidden" >
		<input id="day19"  name="day19" type="hidden" >
		<input id="day20" name="day20" type="hidden" >
		<input id="day21" name="day21" type="hidden" >
		<input id="day22" name="day22" type="hidden" >
		<input id="day23" name="day23" type="hidden" >
		<input id="day24" name="day24" type="hidden"  >
		<input id="day25" name="day25" type="hidden" >
		<input id="day26" name="day26" type="hidden" >
		<input id="day27" name="day27" type="hidden" >
		<input id="day28" name="day28" type="hidden" >
		<input id="day29" name="day29" type="hidden" >
		<input id="day30" name="day30" type="hidden" >
		<input id="day31" name="day31" type="hidden" >
	</form>
	<script type="text/javascript">
		$(document).ready(
				function() {
					$('#calendar').fullCalendar(
							{   
								dayClick : function(date, jsEvent, view) {
									if($(this).hasClass('fc-past')) {
										 layer.alert('对不起,您选择的排班时间已经过去了,请重新选择');
										 return;
									}
									if($(this).hasClass('fc-other-month')) {
										 layer.alert('对不起,只能选择当月的时间,请重新选择');
										 return;
									}
									$("#yearmonth").val(date.format('yyyyMM'));
								 	var staffName = $("#paibanTitle input").val();
									if(typeof(staffName) == "undefined") {
									    layer.alert("请您选择要排班的员工,然后点击开始排班,才能进行排班");
									    return;
									} 
									var $t = $(this);
									if ($t.data("sel")) {
										$t.css('background-color', '');
										$t.data("sel", false);
										$(".fc-day-content div", $t).css(
												"text-align", "center")
												.text("");
									} else {
										$t.css('background-color', '#f4307b');
										$t.css('color', '#000000');
										$t.css('font-size', '14px');
										$t.data("sel", true);
										$(".fc-day-content div", $t).css(
												"text-align", "center").html(
											$("input:radio:checked").parent().text() + "<input type='hidden' value="+ $("input:radio:checked").val()+" >");
									}
								}
				        });
						$(".fc-future").css("background-color","#c7edcd");
				        var selectedKaidanRowId;
				        var staffs=new Array();
				        $("#staffTable")
									.grid(
											{
												url : _ctxPath
														+ "/kaoqin/queryStaff.do",
												shrinkToFit : false,
												multiselect :true,
												onSelectRow : function(id) {
													 if (!selectedKaidanRowId
														|| selectedKaidanRowId != id) {
														selectedKaidanRowId = id;
													  } 
													  var kd = $("#" + selectedKaidanRowId, $("#staffTable")).data(
												     "rawData");
													  var flag = false;
													  $.each(staffs,function(index,value) {
														  if(value == kd){
															  staffs.splice(index,1);
															  flag = true;
														  }
													  });
													  if(flag == false) {
														  staffs[staffs.length] = kd;
													  }
													  var names = new Array();
													  var staffIds = new Array();
													  $.each(staffs,function(index,staff) {
														  names[index] = staff.name;
														  staffIds[index] = staff._id;
													  });
												      $("#paibanTitle").html("你好，你正在为<span id='staffName1' style='color:blue'>"+names.join(',') +"</span>排班" + "<input type='hidden'  value="+staffIds.join(',') +" >");
													  selectedKaidanRowId = null;
												}, 
												colNames : [ "员工姓名","性别", "岗位"],
												colModel : [{
															name : "name",
															index : "name",
															align : "center",
														},
														{
															name : "sex",
															index : "sex",
															align : "center"
														},
														{
															name : "dutyName",
															index : "dutyName",
															align : "center"
														}],
											 gridComplete: function(){
							             				$(".ui-jqgrid-sortable").css("text-align", "center");},
								});
				       	      $("#cb_staffTable").css("display","none");
				        	 /*
				        	 	保存排班
				        	 */
				        	 $("#save").click(function() {
				              	 var staffId = $("#paibanTitle input").val();
				              	 if(typeof(staffId) == "undefined") {
									    layer.alert("请您选择要排班的员工,才能进行排班");
									    return;
								 }
				              	 if($(".fc-day-content div").find("input").size()<1) {
				              	 	layer.alert("对不起，你还没有排班,再去保存排班。");
				              	 	return;
				              	 }
				              	 
				             	for(var i =1; i<=31;i++) {
									if(i<10) {
										$("#day0"+ i).val("");
									} else {
										$("#day"+ i).val("");
									}
								}
				              	 $("#staffNames").val($("#staffName1").text());
				              	 $("#staffIds").val($("#paibanTitle input").val());
				              	 $(".fc-day-content div input").each(function(){
				              	 	var day = $.trim($($($($(this).parent()).parent()).prev()).text());
				              	 	if(day.length == 2) {
				              	 		$("#day"+ day).val($(this).val());
				              	 	} else {
				              	 		$("#day0"+ day).val($(this).val());
				              	 	}
								 });
								 $("#paibanTitle").text("目前没有可以排班的人员!!");
								 $("#paibanTitle input").val("");
								 $.post(_ctxPath + "/kaoqin/upsertPaiban.do",$("#form").formobj(),function(data) {
								 	if(data ==  true) {
								 		layer.alert("保存排班成功！",function(index){
  											 layer.close(index);
  											 window.location.reload();
										});
								 	} else {
								 		 layer.alert("保存排班失败！",function(index){
  											 layer.close(index);
  											 window.location.reload();
									     });
								 	}
								 }); 
				           });
					});
	</script>
</body>
</html>
