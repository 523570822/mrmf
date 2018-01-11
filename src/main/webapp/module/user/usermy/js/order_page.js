$(function(){
	orderList();
	$(window).scroll(function(){
	    if($(window).scrollTop() == $(document).height() - $(window).height()){
	    	orderList();
	    }
	});
});
function orderList(){
	var page=$("#page").val();
	var pages=$("#pages").val();
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
		$.post(_ctxPath + "/w/userMy/myFinishedOrderList",{'page':page },
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
					html+='<li onclick="selectOrder(this)"><input type="hidden" value="'+obj.data[i]._id+'"><div class="name"><div class="col-1"><img src="';
					/*if (obj.data[i].serviveName.indexOf("发")>0) {
						html+=_ctxPath+'/module/staff/images/img/icon_haircut_bill.png';
					}else if (obj.data[i].serviveName.indexOf("容")>0) {
						html+=_ctxPath+'/module/staff/images/img/icon_cosmetology.png';
					}else if (obj.data[i].serviveName.indexOf("足")>0) {
						html+=_ctxPath+'/module/staff/images/img/icon_foot.png';
					}else {
						html+=_ctxPath+'/module/staff/images/img/icon_haircut_bill.png';
					}*/
					if(obj.data[i].serverType == "hairType") {
						html+=_ctxPath+'/module/staff/images/img/icon_haircut_bill.png';
					} else if(obj.data[i].serverType == "meiRongType") {
						html+=_ctxPath+'/module/staff/images/img/icon_cosmetology.png';
					} else if(obj.data[i].serverType == "zuLiaoType"){
						html+=_ctxPath+'/module/staff/images/img/icon_foot.png';
					} else if(obj.data[i].serverType == "meiJiaType") {
						html+=_ctxPath+'/module/staff/images/img/icon_haircut_bill.png';
					}
					html+='"></div><div class="col-7"><span>'+obj.data[i].title+'</span></div><div class="col-2"><span class="colorRed">';
					/*if (obj.data[i].state=="10") {
						html+='已完成';
					}*//*else if (obj.data[i].state=="1") {
						html+='预约中';
					}else if (obj.data[i].state=="2") {
						html+='待支付';
					}else if (obj.data[i].state=="3") {
						html+='待评价';
					}*/
					html+='</span></div></div><div class="detail"><div class="address"><div class="col-2"><img src="'+(obj.data[i].organLogo==null || obj.data[i].organLogo==''?_ctxPath+'/module/staff/images/img/nopicture.jpg':_ossImageHost+obj.data[i].organLogo+'@!style400')+'"></div><div class="shopName">'
					+obj.data[i].organName+'</div></div><div class="bespeakTime"><span class="colorGray">预约时间</span><span>'+obj.data[i].orderTime+'</span></div><div class="bespeakTime"><span class="colorGray">店铺地址</span><span>'
					+obj.data[i].organAddress+'</span></div></div></li>';
				}
				$("#order_list").html($("#order_list").html()+html);
			}
		},
		"json");//这里返回的类型有：json,html,xml,text
}
function selectOrder(str) {
	var userId=$("#user_id").val();
	var orderId=$(str).children('input:eq(0)').val();
	$.post(_ctxPath + "/w/userMy/searchCompensate",{'orderId':orderId},
			function(data){
		if(data=='false'){
			alert('同一项目只能选择一次');
			return false;
		}else {
			window.location.href = _ctxPath + "/w/userMy/returnCompensate.do?orderId="+orderId+"&userId="+userId;
		}
	},
	"text");
}