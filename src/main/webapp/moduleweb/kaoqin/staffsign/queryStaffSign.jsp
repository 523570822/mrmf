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
				 		<form id="searchForm" method="get" class="form-horizontal">
							<input type="hidden" id="organId" name="organId"
								value="${organId}">
							<div class="form-group">
								<label class="col-sm-1 control-label">员工姓名</label>
								<div class="col-sm-2">
									<input id="regex:staffName" name="regex:staffName"
										placeholder="员工姓名" type="text" class="form-control">
								</div>
								
								<label class="col-sm-1 control-label">开始年月</label>
								<div class="col-sm-2">
									<input id="gte:yearmonth|integer" placeholder="格式:yyyyMM"
										name="gte:yearmonth|integer" type="text" class="form-control">
								</div>
								<label class="col-sm-1 control-label">结束年月</label>
								<div class="col-sm-2">
									<input id="lte:yearmonth|integer" placeholder="格式:yyyyMM"
										name="lte:yearmonth|integer" type="text" class="form-control">
								</div>
								<div class="col-sm-1">
									<button id="search" class="btn btn-primary" type="submit">查询</button>
								</div>
								<div class="col-sm-2"></div>
							</div>
						</form>
				 		<div class="jqGrid_wrapper">
							<table id="staffSignTable"></table>
						</div>
				 	</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	$().ready(   function() {
        var start = {
            elem : '#gte:yearmonth|integer',
            format : 'YYYYMM',
            max : '2099-06-16 23:59:59', //最大日期
            istime : true,
            istoday : false,
//            choose : function(datas) {
//                end.min = datas; //开始日选好后，重置结束日的最小日期
//                end.start = datas; //将结束日的初始值设定为开始日
//            }
        };
        var end = {
            elem : '#lte:yearmonth|integer',
            format : 'YYYYMM',
            max : '2099-06-16 23:59:59',
            istime : true,
            istoday : false,
//            choose : function(datas) {
//                start.max = datas; //结束日选好后，重置开始日的最大日期
//            }
        };
        laydate(start);
        laydate(end);
							$("#searchForm").submit(function() {
								$("#staffSignTable").reloadGrid({
									postData : $("#searchForm").formobj()
								});
								return false;
							});
							$("#staffSignTable")
									.grid(
											{
												url : _ctxPath
														+ "/kaoqin/queryStaffSign.do",
												shrinkToFit : false,
												colNames : [ "员工姓名", "签到年月",
												          "01","02", "03","04","05",
												          "06","07", "08","09","10",
												          "11","12", "13","14","15",
												          "16","17", "18","19","20",
												          "21","22", "23","24","25",
												          "26","27", "28","29","30",
												          "31"
														],
												colModel : [
												        {
															name : "staffName",
															index : "staffName",
															 width:"100px",
															align : "center"
														},
														 {
															name : "yearmonth",
															index : "yearmonth",
															width:"100px",
															align : "center"
														},
														{   name : "day01State",index : "day01State",align:"center",width:"40px",
															formatter : function(cellvalue,options, rowObject) {
																return cellvalue+"次";
															}
														},
														{   name : "day02State",index : "day02State",align:"center",width:"40px",
															formatter : function(cellvalue,options, rowObject) {
																return cellvalue+"次";}
														},
														{   name : "day03State",index : "day03State",align:"center",width:"40px",
															formatter : function(cellvalue,options, rowObject) {
																return cellvalue+"次";}
														},
														{   name : "day04State",index : "day04State",align:"center",width:"40px",
															formatter : function(cellvalue,options, rowObject) {
																return cellvalue+"次";}
														},
														{   name : "day05State",index : "day05State",align:"center",width:"40px",
															formatter : function(cellvalue,options, rowObject) {
																return cellvalue+"次";}
														},
														{   name : "day06State",index : "day06State",align:"center",width:"40px",
															formatter : function(cellvalue,options, rowObject) {
																return cellvalue+"次";}
														},
														{   name : "day07State",index : "day07State",align:"center",width:"40px",
															formatter : function(cellvalue,options, rowObject) {
																return cellvalue+"次";}
														},
														{   name : "day08State",index : "day08State",align:"center",width:"40px",
															formatter : function(cellvalue,options, rowObject) {
																return cellvalue+"次";}
														},
														{   name : "day09State",index : "day09State",align:"center",width:"40px",
															formatter : function(cellvalue,options, rowObject) {
																return cellvalue+"次";}
														},
														{   name : "day10State",index : "day10State",align:"center",width:"40px",
															formatter : function(cellvalue,options, rowObject) {
																return cellvalue+"次";}
														},
														{   name : "day11State",index : "day11State",align:"center",width:"40px",
															formatter : function(cellvalue,options, rowObject) {
																return cellvalue+"次";}
														},
														{   name : "day12State",index : "day12State",align:"center",width:"40px",
															formatter : function(cellvalue,options, rowObject) {
																return cellvalue+"次";}
														},
														{   name : "day13State",index : "day13State",align:"center",width:"40px",
															formatter : function(cellvalue,options, rowObject) {
																return cellvalue+"次";}
														},
														{   name : "day14State",index : "day14State",align:"center",width:"40px",
															formatter : function(cellvalue,options, rowObject) {
																return cellvalue+"次";}
														},
														{   name : "day15State",index : "day15State",align:"center",width:"40px",
															formatter : function(cellvalue,options, rowObject) {
																return cellvalue+"次";}
														},
														{   name : "day16State",index : "day16State",align:"center",width:"40px",
															formatter : function(cellvalue,options, rowObject) {
																return cellvalue+"次";}
														},
														{   name : "day17State",index : "day17State",align:"center",width:"40px",
															formatter : function(cellvalue,options, rowObject) {
																return cellvalue+"次";}
														},
														{   name : "day18State",index : "day18State",align:"center",width:"40px",
															formatter : function(cellvalue,options, rowObject) {
																return cellvalue+"次";}
														},
														{   name : "day19State",index : "day19State",align:"center",width:"40px",
															formatter : function(cellvalue,options, rowObject) {
																return cellvalue+"次";}
														},
														{   name : "day20State",index : "day20State",align:"center",width:"40px",
															formatter : function(cellvalue,options, rowObject) {
																return cellvalue+"次";}
														},
														{   name : "day21State",index : "day21State",align:"center",width:"40px",
															formatter : function(cellvalue,options, rowObject) {
																return cellvalue+"次";}
														},
														{   name : "day22State",index : "day22State",align:"center",width:"40px",
															formatter : function(cellvalue,options, rowObject) {
																return cellvalue+"次";}
														},
														{   name : "day23State",index : "day23State",align:"center",width:"40px",
															formatter : function(cellvalue,options, rowObject) {
																return cellvalue+"次";}
														},
														{   name : "day24State",index : "day24State",align:"center",width:"40px",
															formatter : function(cellvalue,options, rowObject) {
																return cellvalue+"次";}
														},
														{   name : "day25State",index : "day25State",align:"center",width:"40px",
															formatter : function(cellvalue,options, rowObject) {
																return cellvalue+"次";}
														},
														{   name : "day26State",index : "day26State",align:"center",width:"40px",
															formatter : function(cellvalue,options, rowObject) {
																return cellvalue+"次";}
														},
														{   name : "day27State",index : "day27State",align:"center",width:"40px",
															formatter : function(cellvalue,options, rowObject) {
																return cellvalue+"次";}
														},
														{   name : "day28State",index : "day28State",align:"center",width:"40px",
															formatter : function(cellvalue,options, rowObject) {
																return cellvalue+"次";}
														},
														{   name : "day29State",index : "day29State",align:"center",width:"40px",
															formatter : function(cellvalue,options, rowObject) {
																return cellvalue+"次";}
														},
														{   name : "day30State",index : "day30State",align:"center",width:"40px",
															formatter : function(cellvalue,options, rowObject) {
																return cellvalue+"次";}
														},
														{   name : "day31State",index : "day31State",align:"center",width:"40px",
															formatter : function(cellvalue,options, rowObject) {
																return cellvalue+"次";}
														}
														],
												 		gridComplete: function(){
							             				$(".ui-jqgrid-sortable").css("text-align", "center");  
							         		    },
								});
								
						});
	</script>
</body>
</html>
