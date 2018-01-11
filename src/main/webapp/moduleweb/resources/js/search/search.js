var search=[
	{id:"id_2",name:"会员号",op:[{eq:"等于"},{ne:"不等于"},{regex:"包含"},{preregex:"前匹配"}],val:[],valtype:""},
	{id:"sex",name:"性别",op:[{eq:"等于"},{ne:"不等于"}],val:[{id:"男",name:"男"},{id:"女",name:"女"}],valtype:""},
	{id:"money_xiaofei",name:"消费金额",op:[{eq:"等于"},{ne:"不等于"},{gt:"大于"},{lt:"小于"},{gte:"大于等于"},{lte:"小于等于"}],val:[],valtype:"double"},
	{id:"money1",name:"应交款额",op:[{eq:"等于"},{ne:"不等于"},{gt:"大于"},{lt:"小于"},{gte:"大于等于"},{lte:"小于等于"}],val:[],valtype:"double"},
	//差一个服务明细动态获取--
	//员工123动态获取--
	//会员类型--
	//服务小类--
	//userinincard 类型membersort
	{id:"name",name:"会员姓名",op:[{eq:"等于"},{ne:"不等于"},{regex:"包含"},{preregex:"前匹配"}],val:[],valtype:""},
	{id:"phone",name:"电话",op:[{eq:"等于"},{ne:"不等于"},{regex:"包含"},{preregex:"前匹配"}],val:[],valtype:""},
	{id:"createTime",name:"光临日期",op:[{eq:"等于"},{ne:"不等于"},{gt:"大于"},{lt:"小于"},{gte:"大于等于"},{lte:"小于等于"}],val:[],valtype:"date"},
	{id:"birthday",name:"生日",op:[{eq:"等于"},{ne:"不等于"},{gt:"大于"},{lt:"小于"},{gte:"大于等于"},{lte:"小于等于"}],val:[],valtype:"date"},
	{id:"havemoney1",name:"员工1提成",op:[{eq:"等于"},{ne:"不等于"}],val:[],valtype:""},
	{id:"havemoney2",name:"员工2提成",op:[{eq:"等于"},{ne:"不等于"}],val:[],valtype:""},
	{id:"yeji1",name:"员工1业绩",op:[{eq:"等于"},{ne:"不等于"}],val:[],valtype:"double"},
	{id:"yeji2",name:"员工2业绩",op:[{eq:"等于"},{ne:"不等于"}],val:[],valtype:"double"},
	{id:"yeji3",name:"员工3业绩",op:[{eq:"等于"},{ne:"不等于"}],val:[],valtype:"double"},
	{id:"delete_flag",name:"是否删除",op:[{eq:"是"},{ne:"否"}],val:[{id:"true",name:"删除"}],valtype:"boolean"},
	//{id:"jingshou",name:"操作员",op:[{eq:"等于"},{ne:"不等于"},{regex:"包含"},{preregex:"前匹配"}],val:[],valtype:""},
	{id:"guazhang_flag",name:"挂账",op:[{eq:"是"},{ne:"否"}],val:[{id:"true",name:"挂账"}],valtype:"boolean"},
	{id:"havemoney3",name:"员工3提成",op:[{eq:"等于"},{ne:"不等于"}],val:[],valtype:""},
	{id:"money_wupin",name:"使用产品价钱",op:[{eq:"等于"},{ne:"不等于"},{gt:"大于"},{lt:"小于"},{gte:"大于等于"},{lte:"小于等于"}],val:[],valtype:"double"},
	{id:"miandan",name:"免单",op:[{eq:"是"},{ne:"否"}],val:[{id:"true",name:"免单"}],valtype:"boolean"},
	{id:"money_qian",name:"欠费",op:[{eq:"等于"},{ne:"不等于"},{gt:"大于"},{lt:"小于"},{gte:"大于等于"},{lte:"小于等于"}],val:[],valtype:"double"},
	{id:"cardno",name:"卡表面号",op:[{eq:"等于"},{ne:"不等于"},{regex:"包含"},{preregex:"前匹配"}],val:[],valtype:""},
	{id:"money4",name:"余额",op:[{eq:"等于"},{ne:"不等于"},{gt:"大于"},{lt:"小于"},{gte:"大于等于"},{lte:"小于等于"}],val:[],valtype:"double"},
	{id:"money2",name:"续费额",op:[{eq:"等于"},{ne:"不等于"},{gt:"大于"},{lt:"小于"},{gte:"大于等于"},{lte:"小于等于"}],val:[],valtype:"double"},
	{id:"createTime",name:"续费日期",op:[{eq:"等于"},{ne:"不等于"},{gt:"大于"},{lt:"小于"},{gte:"大于等于"},{lte:"小于等于"}],val:[],valtype:"date"},
	{id:"allcishu",name:"总次数",op:[{eq:"等于"},{ne:"不等于"},{gt:"大于"},{lt:"小于"},{gte:"大于等于"},{lte:"小于等于"}],val:[],valtype:"integer"},
	{id:"shengcishu",name:"剩余次数",op:[{eq:"等于"},{ne:"不等于"},{gt:"大于"},{lt:"小于"},{gte:"大于等于"},{lte:"小于等于"}],val:[],valtype:"integer"},
	{id:"danci_money",name:"单次款额",op:[{eq:"等于"},{ne:"不等于"},{gt:"大于"},{lt:"小于"},{gte:"大于等于"},{lte:"小于等于"}],val:[],valtype:"double"},
	{id:"createTime",name:"建卡日期",op:[{eq:"等于"},{ne:"不等于"},{gt:"大于"},{lt:"小于"},{gte:"大于等于"},{lte:"小于等于"}],val:[],valtype:"date"},
	{id:"money2",name:"折后价",op:[{eq:"等于"},{ne:"不等于"},{gt:"大于"},{lt:"小于"},{gte:"大于等于"},{lte:"小于等于"}],val:[],valtype:"double"},
];
function selectHandle(handeldiv){
	var div= $(handeldiv);
	var id=div.children('input').val();
	var val = div.children('label').html();
	$("#handle").val(val);
	$("#handelhidden").val(id);
}
function selectMatch(handeldiv){
	var div= $(handeldiv);
	var val = div.children('label').html();
	var id = div.children('input').val();
	$("#matchhiddenID").val(id);
	$("#matchContent").val(val);
}
function selectSearch(searchdiv){
	$(searchdiv).siblings().removeClass("selectSearch");
	$(searchdiv).toggleClass("selectSearch");
}
function clear(){
	$("#searchTable tr td:eq(1) div").html("");
	$("#searchTable tr td:eq(2) #matchdiv").html("");
	$("#handle").val("");
	$("#handelhidden").val("");
	$("#matchContent").val("");
	$("#matchhidden").val("");
	$("#searchContent").val("");
	$("#searchHidden").val("");
	$("#matchhiddenID").val("");
	
}
$(function(){
	//初始化服务明细
	var smallsort = fillmaps.smallsort;
	if(typeof(smallsort)!='undefined'&&smallsort.length>0){
		var oo={};
		oo["id"]="smallsort";
		oo["name"]="服务明细";
		oo["op"]=[{eq:"等于"},{ne:"不等于"}];
		oo["val"]=[];
		for(var i=0;i<smallsort.length;i++){
			var val={};
			val["id"]=smallsort[i]._id;
			val["name"]=smallsort[i].name;
			oo["val"].push(val);
		}
		search.push(oo);
	}
	//初始化员工
	var organstaff = fillmaps.organstaff;
	if(typeof(organstaff)!='undefined'&&organstaff.length>0){
		//员工1
		var oo1={};
		oo1["id"]="staffId1";
		oo1["name"]="员工1";
		oo1["op"]=[{eq:"等于"},{ne:"不等于"}];
		oo1["val"]=[];
		//员工2
		var oo2={};
		oo2["id"]="staffId2";
		oo2["name"]="员工2";
		oo2["op"]=[{eq:"等于"},{ne:"不等于"}];
		oo2["val"]=[];
		//员工3
		var oo3={};
		oo3["id"]="staffId3";
		oo3["name"]="员工3";
		oo3["op"]=[{eq:"等于"},{ne:"不等于"}];
		oo3["val"]=[];
		for(var i=0;i<organstaff.length;i++){
			var val={};
			val["id"]=organstaff[i]._id;
			val["name"]=organstaff[i].name;
			oo1["val"].push(val);
			oo2["val"].push(val);
			oo3["val"].push(val);
		}
		search.push(oo1);
		search.push(oo2);
		search.push(oo3);
	}
	//初始化会员类型
	var membersort = fillmaps.membersort;
	if(typeof(membersort)!='undefined'&&membersort.length>0){
		var oo={};
		oo["id"]="membersort";
		oo["name"]="会员类型";
		oo["op"]=[{eq:"等于"},{ne:"不等于"}];
		oo["val"]=[];
		for(var i=0;i<membersort.length;i++){
			var val={};
			val["id"]=membersort[i]._id;
			val["name"]=membersort[i].name1;
			oo["val"].push(val);
		}
		search.push(oo);
	}
	//初始化服务大类
	var bigsort = fillmaps.bigsort;
	if(typeof(bigsort)!='undefined'&&bigsort.length>0){
		var oo={};
		oo["id"]="bigsort";
		oo["name"]="服务大类";
		oo["op"]=[{eq:"等于"},{ne:"不等于"}];
		oo["val"]=[];
		for(var i=0;i<bigsort.length;i++){
			var val={};
			val["id"]=bigsort[i]._id;
			val["name"]=bigsort[i].name;
			oo["val"].push(val);
		}
		search.push(oo);
	}
	$("#searchTable tr td:eq(0) div div").click(function(){
		clear();
		var searchId="";
		var div= $(this);
		var id=div.children('input').val();
		var val = div.children('label').html();
	    $("#searchContent").val(val);
	    $("#searchHidden").val(id);
	    $.each(search,function(){
	    	if(this.id==id){
	    		if(searchId.indexOf(id)==-1){
	    			searchId+=","+id;
		    		$.each(this.op,function(index,item){
		    			for(name in item){
		    				var handle=$("#searchTable tr td:eq(1)");
		    				var handeldiv = '<div onclick="selectHandle(this)" class=" divwidth">'
											+'<label class=" control-label">'+item[name]+'</label>'
											+'<input type="hidden" value="'+name+'"></div>';
		    				//handle.children("div").html(handle.children("div").html()+handeldiv);
		    				handle.children("div").append(handeldiv);
		    			}
		    		});
	    		
	    		if(this.val.length!=0){
	    			$("#matchContent").attr("disabled",true);
			    	$.each(this.val,function(index,item){
			    			var match=$("#searchTable tr td:eq(2)");
			    			var matchdiv='<div onclick="selectMatch(this)" class=" divwidth">'
			    						+'<label class=" control-label">'+item.name+'</label>'
			    						+'<input type="hidden" value="'+item.id+'"></div>';
			    			//match.children("div").html(match.children("div").html()+matchdiv);
			    			match.children("div").append(matchdiv);
			    			
			    	});
	    		}else{
	    			$("#matchContent").attr("disabled",false);
	    		}
		    	$("#matchhidden").val(this.valtype);
	    	}
	    }
	    });
	});
	$("#searchAdd").click(function(){
		var handle=$("#handle").val();
		var match = $("#matchContent").val();
		var matchhiddenID = $("#matchhiddenID").val();
		if(matchhiddenID==""){
			matchhiddenID = match;
		}
		var connectorHid = $('input[name="connector"]:checked ').val();
		var connector="";
		if(connectorHid=="and"){
			connector="并且";
		}else if(connectorHid=="or"){
			connector="或者";
		}
		if(handle==""){
			toastr.error("操作符不能为空");
			return;
		}
		if(match==""){
			toastr.error("匹配内容不能为空");
			return;
		}
		var regmatch = $("#matchhidden").val();
		if(regmatch=="double"){
			var regex = /^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/;
			if(!regex.test(match)){
				toastr.error("匹配内容格式不正确");
				return;
			}
		}else if(regmatch=="integer"){
			 var regex =/^[1-9][0-9]*$/;
			 if(!regex.test(match)){
					toastr.error("匹配内容格式不正确");
					return;
				}
		}
		 var seasrchDivHid = $("#searchDivHid").val();
		 var fieldhid = $("#searchHidden").val();
		 if(seasrchDivHid.indexOf(fieldhid)>=0){
			 toastr.error("已经添加了此条件");
			 return;
		 }
		 var file = $("#searchContent").val();	
		 var handle = $("#handle").val();
		 var handlehid = $("#handelhidden").val();
		 var match = $("#matchContent").val();
		 var matchhid = $("#matchhidden").val();
		 var search = '<div onclick="selectSearch(this)" class="" style="height:40px;">'
				      +'<label class="control-label">'+file+'&nbsp;'+handle+'&nbsp;'+match+'&nbsp;'+connector+'</label>'
				     +'<input type="hidden" value="'+handlehid+':'+fieldhid+'|'+matchhid+'#'+connectorHid+'">'
				     +'<input type="hidden" value="'+matchhiddenID+'"/></div>';
		$("#searchTable tr td:eq(4) div").html($("#searchTable tr td:eq(4) div").html()+search);
		$("#searchDivHid").val($("#searchDivHid").val()+","+fieldhid);
		clear();
	});
	$("#searchDel").click(function(){
		var fildInfo = $(".selectSearch").children("input:eq(0)").val();
		if(typeof(fildInfo)!='undefined'){
			var filed = fildInfo.split("|")[0].split(":")[1];
			var serchDivHid = $("#searchDivHid").val();
			$("#searchDivHid").val(serchDivHid.replace(filed,""));
			$("div").remove(".selectSearch");
		}
	});
	$("#searchUp").click(function(){
		var seldiv = $("div .selectSearch");
		if(seldiv.length!=0){
			var prediv = seldiv.prev();
			if(prediv.length!=0){
				var seldivhtml = seldiv.html();
				var predivhtml = prediv.html();
				seldiv.html(predivhtml);
				prediv.html(seldivhtml);
				seldiv.toggleClass("selectSearch");
				prediv.toggleClass("selectSearch");
			}
		}
	});
	$("#searchDown").click(function(){
		var seldiv = $("div .selectSearch");
		if(seldiv.length!=0){
			var nextdiv = seldiv.next();
			if(nextdiv.length!=0){
				var seldivhtml = seldiv.html();
				var nextdivhtml = nextdiv.html();
				seldiv.html(nextdivhtml);
				nextdiv.html(seldivhtml);
				seldiv.toggleClass("selectSearch");
				nextdiv.toggleClass("selectSearch");
			}
		}
	});
	$("#searchClear").click(function(){
		$("#searchDiv>div").remove();
		$("#searchDivHid").val("");
	});
	$(".closethis").click(function(){
		$("#searchTable").hide();
		clear();
		$("#searchDiv>div").remove();
		$("#searchDivHid").val("");
	});
	//查询方法
	$("#searchbutton").click(function(){
							var searchdiv = $("#searchDiv div");
							getHandle(parentSearchForm);
							$.each(searchdiv,function(index,item){
								var key = $(item).children('input:eq(0)').val();
								var value = $(item).children('input:eq(1)').val();
								searchCriteria[key] = value;
							});
							toMultipleSearch();
							$("#"+parentTable).reloadGrid({
													postData : searchCriteria
													});
							if(parentClearTable!=""&&parentClearTable!='null'&&typeof(parentClearTable)!='undefined'){
								var tables = parentClearTable.split(",");
								for(var i=0;i<tables.length;i++){
									$("#"+tables[i]).jqGrid('clearGridData');
								}
							}
							//$("#myTable2").jqGrid('clearGridData');
						});
});
function toMultipleSearch(){
	$("#searchTable").hide();
	clear();
	$("#searchDiv>div").remove();
	$("#searchDivHid").val("");
}
//初始化查询条件
var searchCriteria={};
var parentSearchForm="";//父页面查询form
var parentTable="";
var parentClearTable="";
function getHandle(parentSearchForm){
		var o = $("#"+parentSearchForm).formobj();
		searchCriteria={};
		for(var key in o){
			searchCriteria[key]=o[key];
		}
	}
	
	//打开综合查询页面
	function showMultipleSearch(searchForm,searchTable,clearTable,displayCondition){
		parentSearchForm=searchForm;
		parentTable = searchTable;
		parentClearTable = clearTable;
		//加载显示条件
		DisplayCondition(displayCondition);
		$("#searchTable").show();
	}
	function selectDisplay(display){
		clear();
		var searchId="";
		var div= $(display);
		var id=div.children('input').val();
		var val = div.children('label').html();
	    $("#searchContent").val(val);
	    $("#searchHidden").val(id);
	    $.each(search,function(){
	    	if(this.id==id){
	    		if(searchId.indexOf(id)==-1){
	    			searchId+=","+id;
		    		$.each(this.op,function(index,item){
		    			for(name in item){
		    				var handle=$("#searchTable tr td:eq(1)");
		    				var handeldiv = '<div onclick="selectHandle(this)" class=" divwidth">'
											+'<label class=" control-label">'+item[name]+'</label>'
											+'<input type="hidden" value="'+name+'"></div>';
		    				//handle.children("div").html(handle.children("div").html()+handeldiv);
		    				handle.children("div").append(handeldiv);
		    			}
		    		});
		    	$("#matchContent").remove();
		    	var matchContent = '<input id="matchContent" name="matchContent" type="text"'
									+'class="form-control">';
		    	$("#matchContentdiv").append(matchContent);
	    		if(this.val.length!=0){
	    			$("#matchContent").attr("disabled",true);
			    	$.each(this.val,function(index,item){
			    			var match=$("#searchTable tr td:eq(2)");
			    			var matchdiv='<div onclick="selectMatch(this)" class=" divwidth">'
			    						+'<label class=" control-label">'+item.name+'</label>'
			    						+'<input type="hidden" value="'+item.id+'"></div>';
			    			//match.children("div").html(match.children("div").html()+matchdiv);
			    			match.children("#matchdiv").append(matchdiv);
			    			
			    	});
	    		}else{
	    			if(this.valtype=="date"){
	    				$("#matchContent").remove();
	    				var date='<input id="matchContent" name="matchContent" onclick="selectDate(this)"' 
								 +'class="laydate-icon form-control" placeholder="日期">';
	    				$("#matchContentdiv").append(date);
	    				/*var dateTime = {
								elem : '#matchContent',
								format : 'YYYY-MM-DD hh:mm:ss',
								min : laydate.now(),
								max : '2099-06-16 23:59:59',
								istime : true,
								istoday : false,
								choose : function(datas) {
									start.max = datas; //结束日选好后，重置开始日的最大日期
								}
							};
							laydate(dateTime);*/
	    				//alert("date");
	    			}else{
	    				$("#matchContent").attr("disabled",false);
	    			}
	    		}
		    	$("#matchhidden").val(this.valtype);
	    	}
	    }
	    });
	}
	function DisplayCondition(displayCondition){
		$("#displayCondition").html("");
		var display = displayCondition.split(",");
		$.each(display,function(index,item){
			//console.log(item);
			 $.each(search,function(index,searchItem){
				 if(item==searchItem.name){
					var div = '<div onclick="selectDisplay(this)" class="divwidth">'
						    +'<label class="control-label">'+searchItem.name+'</label>'
						    +'<input type="hidden" value="'+searchItem.id+'"></div>';
					$("#displayCondition").append(div);
				 }
			 });
		});

	}
	function selectDate(date){
		$t = $(date);
		var layercfg = {
			event : "focus",
			format : 'YYYY-MM-DD',
			min : "1938-06-16 23:59:59",
			max : "2099-06-16 23:59:59",
			istime : false,
			istoday : true
		};
		var layerdate = $t.attr("laydate");
		if (layerdate) {
			var cfg = $.parseJSON(layerdate);
			layercfg = $.extend(layercfg, cfg);
		}
		layercfg.elem = "#" + $t.attr("id");
		laydate(layercfg);
	}