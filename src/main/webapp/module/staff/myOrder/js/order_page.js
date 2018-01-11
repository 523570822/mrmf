$(function(){
	$.ajaxSetup({
		  async: false
	});
	orderList();
	$(window).scroll(function(){
	    if($(window).scrollTop() == $(document).height() - $(window).height()){
	    	orderList();
	    	
	    }
	});
	$(".messTitle div").click(function(){
		$(this).siblings().removeClass("sel_message");
		$(this).addClass("sel_message");
		var index=$(this).index();
		if(index==0){//全部
			$("#type").val("1");
			$("#pages").val("");
			$("#page").val("");
			$("#order_list").html("");
			orderList();
		}else if(index==1){//待支付
			$("#type").val("2");
			$("#pages").val("");
			$("#page").val("");
			$("#order_list").html("");
			orderList();
		}else if(index==2){//待确认
			$("#type").val("3");
			$("#pages").val("");
			$("#page").val("");
			$("#order_list").html("");
			orderList();
		}else if(index==3){//待评价
			$("#type").val("4");
			$("#pages").val("");
			$("#page").val("");
			$("#order_list").html("");
			orderList();
		}
	});
});
function orderList(){
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
		$.post(_ctxPath + "/w/staffMy/myOrderList",{'staffId':staffId,'page':page,'type':type},
				function(data){
			var obj=eval(data);
			var html='';
			$("#pages").val(data.pages);
			$("#page").val(data.page);
			if (obj.data.length==0) {
				html+='<li style="background:#eee;border:0;"><div class="notOrder"></div><div class="notOrderTitle">暂无相关订单</div></li>';
				$("#order_list").html($("#order_list").html()+html);
			}else {
				for(var i=0;i<obj.data.length;i++){
					html+='<li onclick="orderDetail(this)"><input type="hidden" value="'+obj.data[i]._id+'"><div class="name"><div class="col-1"><img src="';
					if (obj.data[i].serverType=="1") {
						html+=_ctxPath+'/module/staff/images/img/icon_haircut_bill.png';
					}else if (obj.data[i].serverType=="2") {
						html+=_ctxPath+'/module/staff/images/img/icon_cosmetology.png';
					}else if (obj.data[i].serverType=="3") {
						html+=_ctxPath+'/module/staff/images/img/icon_nail.png';
					}else if (obj.data[i].serverType=="4") {
						html+=_ctxPath+'/module/staff/images/img/icon_foot.png';
					}
					html+='"></div><div class="col-7"><span>'+obj.data[i].title+'</span></div><div class="col-2"><span class="colorRed">';
					if (obj.data[i].state=="10") {
						html+='已完成';
					}else if (obj.data[i].state=="1") {
						html+='待确认';
					}else if (obj.data[i].state=="2") {
						html+='待支付';
					}else if (obj.data[i].state=="3") {
						html+='待评价';
					}
					html+='</span></div></div><div class="detail"><div class="address"><div class="col-2"><img src="';
					if (obj.data[i].organLogo !=null && !obj.data[i].organLogo=="" ) {
						html+=_ossImageHost+obj.data[i].organLogo+'@!style400';
					}else {
						html+=_ctxPath+'/module/staff/images/img/nopicture.jpg';
					}
					html+='"></div><div class="shopName">'+obj.data[i].organName+'</div></div><div class="bespeakTime"><span class="colorGray">预约时间</span><span>'+obj.data[i].orderTime+'</span></div><div class="bespeakTime"><span class="colorGray">店铺地址</span><span class="appAddress">'
					+obj.data[i].organAddress+'</span></div></div></li>';
				}
				$("#order_list").html($("#order_list").html()+html);
			}
		},
		"json");//这里返回的类型有：json,html,xml,text
}
function orderDetail(str) {
	var staffId=$("#staff_id").val();
	 var orderId=$(str).children('input:eq(0)').val();
	 window.location.href = _ctxPath + "/w/staffMy/orderDetail.do?orderId="+orderId+"&staffId="+staffId;
}