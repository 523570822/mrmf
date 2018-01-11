$(function(){
	$.ajaxSetup({
		  async: false
	});
	var longitude = $.trim($("#longitude").val());
	var latitude = $.trim($("#latitude").val());
	if(longitude == '0' || latitude == "0") {
		alert("定位不准确！请重新登录");
		return;
	}
	organSearch();
	$(window).scroll(function(){
	    if($(window).scrollTop() == $(document).height() - $(window).height()){
	    	organSearch();
	    }
	});
	$("#search").click(function(){
		$("#organ_list").html("");
		$("#pages").val(200);
		organSearch();
	});
});
function organSearch(){
	var page=$("#page").val();
	var pages=$("#pages").val();
	var organName=$("#organName").val();
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
	$.post(_ctxPath + "/w/staff/organList",{'organName':organName,'longitude':$("#longitude").val(),'latitude':$("#latitude").val()},
			  function(data){
		var obj=eval(data);
		$("#page").val(data.page);
		if (obj.data.length==0) {
			$("#pages").val(0);
		}
		for(var i=0;i<obj.data.length;i++){
			var html='<li onclick="selectOrgan(this)"><input type="hidden" value="'+obj.data[i]._id+'"><div class="organName font-32">'
				+obj.data[i].name+'</div><div class="organAddress font-28">'+obj.data[i].address+'</div></li>';
				$("#organ_list").html($("#organ_list").html()+html);
			}
		},
	"json");
}
function selectOrgan(str) {
	$(str).addClass('active2').siblings().removeClass('active2');
    $("#organ_id").val($(str).children('input:eq(0)').val());
}