$(function(){
	var openInquiry=$("#openInquiry").val();
	if (openInquiry=="true") {
		priceList();
		$(window).scroll(function(){
			if($(window).scrollTop() == $(document).height() - $(window).height()){
				if ($("#data").val() !="-1") {
					priceList();
				}
			}
		});
	}
	
});
function priceList(){
	var page=$("#page").val();
	var pages=$("#pages").val();
	if(page!=""){
		page=parseInt(page)+1;
	}else{
		page=1;
	}
	var staffId=$("#staff_id").val();
	var longitude=$("#longitude").val();
	var latitude=$("#latitude").val();
	if(longitude == "" || longitude =="0"||latitude == "" || latitude =="0") {
		alert("没有获取到地理位置，请从新进去");
		return;
	}
	$.post(_ctxPath + "/w/staff/askPriceList",{'staffId':staffId,'longitude':longitude,'latitude':latitude,'page':page},
			  function(data){
		console.log(data);
		var obj=eval(data);
		$("#pages").val(data.pages);
		$("#page").val(data.page);
		var html='';
		if (obj.data.length==0) {
			if ($("#has_date").val()!="yes") {
				html+='<li style="background:#f9f9f9;border:0;"><div class="notOrder" style="left:9rem;"></div><div class="notOrderTitle" style="left:8.8rem;">暂无相关数据</div></li>';
				$("#askPrice_list").html($("#askPrice_list").html()+html);
			}
			$("#data").val(-1);
		}else {
			for(var i=0;i<obj.data.length;i++){
				html+='<li onclick="weinquiry(this)"><input type="hidden" value="'+obj.data[i]._id+'" name="inquiryId"><div class="p-title"><div class="col-8 font-32"><h4>'+obj.data[i].type+'</h4> </div><div class="col-1"><h4>'+obj.data[i].createTimeFormat+'</h4> </div></div><div class="p-pic">';
				for ( var j = 0; j < obj.data[i].images.length; j++) {
					html+='<img src="'+_ossImageHost+obj.data[i].images[j]+'@!style400'+'">';
				}
				html+='</div><div class="p-detail"><h4>'+obj.data[i].desc+'</h4></div></li>';
			}
			$("#askPrice_list").html($("#askPrice_list").html()+html);
			$("#has_date").val("yes");
		}
	},"json");	
}
/* 
 * 跳转到报价详情
 */
function weinquiry(str) {
	 var inquiryId=$(str).children('input:eq(0)').val();
	 window.location.href = _ctxPath + "/w/staff/askPriceDetail.do?inquiryId="+inquiryId;
}