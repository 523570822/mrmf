$(function(){
	customerList();
	$(window).scroll(function(){
	    if($(window).scrollTop() == $(document).height() - $(window).height()){
	    	customerList();
	    	
	    }
	});
	$(".messTitle div").click(function(){
		$(this).siblings().removeClass("sel_message");
		$(this).addClass("sel_message");
		var index=$(this).index();
		//$("#not_click").addClass("not_click");
		if(index==0){
			$("#type").val("1");
			$("#pages").val("");
			$("#page").val("");
			$("#my_customer").html("");
			customerList();
		}else if(index==1){
			$("#type").val("2");
			$("#pages").val("");
			$("#page").val("");
			$("#my_customer").html("");
			customerList();
		}else if(index==2){
			$("#type").val("3");
			$("#pages").val("");
			$("#page").val("");
			$("#my_customer").html("");
			customerList();
		}
	});
	$("#back").click(function() {//跳回我的页面
		window.location.href = _ctxPath + "/w/staff/mine";
	});
});
function customerList(){
	$.ajaxSetup({
		  async: false
	});
	var staffId=$("#staff_id").val();
	var page=$("#page").val();
	var pages=$("#pages").val();
	var type=$("#type").val();
	if(page!=""){
		page=parseInt(page)+1;
	}else{
		page=1;
	}
	if(pages!=""){
		pages=parseInt(pages);
		if(page>pages){
			return;
		}
	}
	/**

	 	2015/09/09 12:00		
	 			<c:if test="${customer.isFirst eq 1 }">
	 				<div class="col-1"><span class="up">首单</span></div>
	 			</c:if>
	 			<c:if test="${customer.isMember eq 1 }">
<div class="col-1"><span class="up">会员</span></div>

	 			</c:if>	 			
	 		</li>
<div class="notOrder"></div><i class="notOrderTitle">暂无相关数据</i>
	 */
	if (type=="1") {//预约
		$.post(_ctxPath + "/w/staffMy/myCustomer_appoint",{'staffId':staffId,'page':page,'type':type},
				function(data){
			var obj=eval(data);
			$("#pages").val(data.pages);
			$("#page").val(data.page);
			if (obj.data.length==0) {
				var html='<div class="notOrder"></div><i class="notOrderTitle">暂无相关数据</i>';
				$("#my_customer").html($("#my_customer").html()+html);
			}else {
				for(var i=0;i<obj.data.length;i++){
					var html='<li><div class="col-2"><img src="'+_ossImageHost+obj.data[i].avatar+'@!avatar'+'"></div><div class="col-7"><h4>'+obj.data[i].nick+'</h4><a class="custom_tel" href="tel:'+obj.data[i].phone+'"><h5><span>电话&#12288;</span><span>'+obj.data[i].phone+'<img src="'+_ctxPath+'/module/resources/images/store/icon_telephone.png"></span></h5></a><h5><span class="color666">'
					+obj.data[i].orderTime+'</span></h5></div>';
					if (obj.data[i].orderNum==1 && obj.data[i].isMember==false) {
						html+='<div class="col-1"><span class="up">首单</span></div>';
					}else if (obj.data[i].orderNum>1 && obj.data[i].isMember==true) {
						html+='<div class="col-1"><span class="up">会员</span></div>';
						//html+='<div class="col-1"><span class="up">会员</span><div class="down next" onclick="member('+"'"+obj.data[i].userId+"'"+')"></div></div>';
					}else if (obj.data[i].orderNum==1 && obj.data[i].isMember==true) {
						html+='<div class="col-1"><span class="up">会员</span></div>';
						//html+='<div class="col-1"><span class="up">会员</span><div class="down next" onclick="member('+"'"+obj.data[i].userId+"'"+')"></div></div>';
					}
					$("#my_customer").html($("#my_customer").html()+html);
				}
			}
		},
		"json");//这里返回的类型有：json,html,xml,text
	}else if (type=="2") {//会员
		$.post(_ctxPath + "/w/staffMy/myCustomer_member",{'staffId':staffId,'page':page,'type':type},
				function(data){
			var obj=eval(data);
			$("#pages").val(data.pages);
			$("#page").val(data.page);
			if (obj.data.length==0) {
				var html='<div class="notOrder"></div><i class="notOrderTitle">暂无相关数据</i>';
				$("#my_customer").html($("#my_customer").html()+html);
			}else {
				for(var i=0;i<obj.data.length;i++){
					var html='<li><div class="col-2"><img src="'+_ossImageHost+obj.data[i].avatar+'@!avatar'+'"></div><div class="col-7"><h4>'+obj.data[i].nick+'</h4><a class="custom_tel" href="tel:'+obj.data[i].phone+'"><h5><span>电话&#12288;</span><span>'+obj.data[i].phone+'<img src="'+_ctxPath+'/module/resources/images/store/icon_telephone.png"></span></h5></a><h5><span class="color666">'
					+obj.data[i].orderTime+'</span></h5></div>';
					if (obj.data[i].isMember==true) {
						html+='<div class="col-1"><span class="up">会员</span></div>';
						//html+='<div class="col-1"><span class="up">会员</span><div class="down next" onclick="member('+"'"+obj.data[i].userId+"'"+')"></div></div></div>';
					}
					$("#my_customer").html($("#my_customer").html()+html);
				}
			}
		},
		"json");//这里返回的类型有：json,html,xml,text
	}else if (type=="3") {//关注
		$.post(_ctxPath + "/w/staffMy/myCustomer_follow",{'staffId':staffId,'page':page,'type':type},
				function(data){
			var obj=eval(data);
			$("#pages").val(data.pages);
			$("#page").val(data.page);
			if (obj.data.length==0) {
				var html='<div class="notOrder"></div><i class="notOrderTitle">暂无相关数据</i>';
				$("#my_customer").html($("#my_customer").html()+html);
			}else {
				for(var i=0;i<obj.data.length;i++){
					var html='<li><div class="col-2"><img src="'+_ossImageHost+obj.data[i].avatar+'@!avatar'+'"></div><div class="col-7"><h4>'+obj.data[i].nick+'</h4><a class="custom_tel" href="tel:'+obj.data[i].phone+'"><h5><span>电话&#12288;</span><span>'+obj.data[i].phone+'<img src="'+_ctxPath+'/module/resources/images/store/icon_telephone.png"></span></h5></a></div>';
					$("#my_customer").html($("#my_customer").html()+html);
				}
			}
		},
		"json");//这里返回的类型有：json,html,xml,text	
	}else {
		
	}
}
function member(str) {
	var staffId=$("#staff_id").val();
	var type="staff";
	window.location.href = _ctxPath + "/w/staffMy/customerDetail.do?userId="+str+"&staffId="+staffId+"&type="+type;
}