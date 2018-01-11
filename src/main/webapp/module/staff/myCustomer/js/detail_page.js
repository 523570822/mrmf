$(function(){
	detailList();
	$(window).scroll(function(){
	    if($(window).scrollTop() == $(document).height() - $(window).height()){
	    	detailList();
	    }
	});
	$("#customDetail_back").click(function() {
		var type=$("#type").val();
		if (type=="staff") {
			var staffId=$("#staff_id").val();
			window.location.href = _ctxPath + "/w/staffMy/toMyCustomer.do?staffId="+staffId;
		}else if (type=="store") {
			var organId=$("#organ_id").val();
			window.location.href = _ctxPath + "/w/organ/toCustomList.do?organId="+organId;
			
		}
	});
});
function detailList(){
	var userId=$("#user_id").val();
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
	
	$.post(_ctxPath + "/w/staffMy/customList",{'userId':userId,'page':page},
			function(data){
		var obj=eval(data);
		$("#pages").val(data.pages);
		$("#page").val(data.page);
		for(var i=0;i<obj.data.length;i++){
			var html='<li><div class="time">'+obj.data[i].orderTime+'</div><div class="cpicture"><div class="lineUp"></div><div class="img"><i class="riqilei"></i></div><div class="lineDown"></div></div><div class="cmess"><h4 class="">'
			+obj.data[i].title+'</h4><h3 class=" colorRed price" >¥'+obj.data[i].orderPrice+'</h3><h5>'+obj.data[i].organName+'</h5></div></li>';
			$("#customer_detail").html($("#customer_detail").html()+html);
		}
	},
	"json");
}