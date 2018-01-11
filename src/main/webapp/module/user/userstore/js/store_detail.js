$(function(){
	$.ajaxSetup({
		  async: false
	});
	$("#organ_detail_back").click(function(){
		var type=$("#type").val();
		if (type !='' && type=="collect") {
			window.location.href= _ctxPath + "/w/staffMy/myCollection";
		}else if (type !='' && type=="collectstore") {
			window.location.href= _ctxPath + "/w/staffMy/myCollection";
		} else {
			var search=$("#search").val();
			if(search!=""){
				$("#organ_detail_form").attr("action",_ctxPath + "/w/organ/toSearchOrgan").submit();
			}else{
				$("#organ_detail_form").attr("action",_ctxPath + "/w/organ/toOrganList.do").submit();
			}
			
		}
	});
	$(".store_fun li").click(function(){
		$(this).parent().children("li").children("label").removeClass("organ_info_active");
		var label=$(this).children("label");
		label.toggleClass("organ_info_active");
		var index=$(this).index();
		if(index==0){
			if($("#desc").val()==""){
				$("#context ul").addClass("nodateulsrote");
				var html='<li style="background:#eee;border:0;" class="nodatali"><div class="notDate"></div><div class="notDateTitle">暂无相关数据</div></li>';
				$("#context ul").html(html);
			}else{
				$("#context ul").removeClass("nodateulsrote");
				$("#context").html("<ul class='list'><p>"+$("#desc").val()+"</p></ul>");
			}
			$("#organ_datail_type").val("");
			$("#page").val("");
			$("#pages").val("");
		}else if(index==1){
			if($("#discountInfo").val()==""){
				$("#context ul").addClass("nodateulsrote");
				var html='<li style="background:#eee;border:0;" class="nodatali"><div class="notDate"></div><div class="notDateTitle">暂无相关数据</div></li>';
				$("#context ul").html(html);
			}else{
				$("#context ul").removeClass("nodateulsrote");
				$("#context").html("<ul class='list'><p>"+$("#discountInfo").val()+"</p></ul>");
			}
			$("#organ_datail_type").val("");
			$("#page").val("");
			$("#pages").val("");
		}else if(index==2){
			$("#organ_datail_type").val("organ_staff_list");
			$("#context ul").html("");
			$("#pages").val("");
			$("#page").val("");
			staffList();
			
		}
		
		
	});
	$(window).scroll(function(){
	    if($(window).scrollTop() == $(document).height() - $(window).height()){
	    	var type=$("#organ_datail_type").val();
	    	if(type!=""){
	    		staffList();
	    	}
	    	
	    }
	});
	$(".hair_appoint1").click(function(){
		$("#organ_detail_form").attr("action",_ctxPath + "/w/organ/toAppointInfo.do").submit();
	});
	$(".hair_collect1").click(function(){
		var state=$.trim($(".hair_collect1 span").text());
		var organId=$("#organId").val();
		if("收藏"==state){
			$.post(_ctxPath + "/w/organ/favorTheOrganId",{"organId":organId,"state":"1"},
					  function(data){
					  $(".hair_collect1 span").text('取消收藏');
					  var followCount=parseInt($("#followCount").val());
					  $(".hair_collect1 i").text('('+(followCount+1)+')');
					  $("#followCount").val(followCount+1);
	        	});
		}else if("取消收藏"==state){
			$.post(_ctxPath + "/w/organ/favorTheOrganId",{"organId":organId,"state":"2"},
					  function(data){
					  $(".hair_collect1 span").text('收藏');
					  var followCount=parseInt($("#followCount").val());
					  $(".hair_collect1 i").text('('+(followCount-1)+')');
					  $("#followCount").val(followCount-1);
	        	});
		}
	});
	/*$("#organ_addr").click(function(){
		$("#organ_detail_form").attr("action",_ctxPath + "/w/organ/toOrganDetailMap").submit();
	});*/
	$("#smallsort").click(function(){
		$("#organ_detail_form").attr("action",_ctxPath + "/w/organ/tariff").submit();
	});
	$("#store_QRCode").click(function(){
		$("#organ_detail_form").attr("action",_ctxPath + "/w/organ/code").submit();
	});
    $("#pay").click(function(){
    	var userAppID=$("#userAppID").val();
    	var encode =$("#encode").val();
    	var organId=$("#organId").val();
         window.location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid="+userAppID+"&redirect_uri="+encode+"%2Fmrmf%2Fw%2Fpay%2FwxSaoMaToPay.do&response_type=code&scope=snsapi_userinfo&state="+organId+"#wechat_redirect";
    });
});
function staffList(){
	var organId=$("#organId").val();
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
	$.post(_ctxPath + "/w/organ/organStaffList",{'organId':organId,'page':page},
			  function(data){
		 	  	var obj=eval(data);
				$("#pages").val(data.pages);
				$("#page").val(data.page);
				//console.log(data);
				if(obj.data.length==0){
					$("#context ul").addClass("nodateulsrote");
					var html='<li style="background:#eee;border:0;" class="nodatali"><div class="notDate"></div><div class="notDateTitle">暂无相关数据</div></li>';
					$("#context ul").html(html);
				}else{
					$("#context ul").removeClass("nodateulsrote");
					for(var i=0;i<obj.data.length;i++){
						var html='<li onclick="staffDetail(this)"><input type="hidden" value="'+obj.data[i]._id+'"><div class="col-2 tx">';
						if(obj.data[i].logo==""||obj.data[i].logo=="null"){
							html+='<img src="'+_ctxPath+'/module/resources/images/nopicture.jpg" alt="header"/>';
						}else{
							html+='<img src="'+_ossImageHost+obj.data[i].logo+'@!style400'+'" alt="header"/>';
						}
						html+='</div><div class="col-6 txt"><h4>'+obj.data[i].name+'</h4><ul class="flowers">';
							for(var j=0;j<obj.data[i].zanCount;j++){
								html+='<li></li>';
							}
							html+='</ul><p><span>'+obj.data[i].followCount+'</span> 人关注</p></div><div class="col-2 location">'+
							'<div><i></i></div><div class="price">&yen; <span>'+obj.data[i].startPrice+'</span> 起</div></div></li>';
						$("#context ul").html($("#context ul").html()+html);
					}
				}	
			  },
			  "json");//这里返回的类型有：json,html,xml,text
	
}
function organRated(){
	$("#organ_detail_form").attr("action",_ctxPath+"/w/organ/toUserRatedList").submit();
}
/* 去技师详情 */
function staffDetail(str){
	var staffId=$(str).children('input:eq(0)').val();
	var type=$("#type").val();
	var cityId=$("#cityId").val();
	var city=$("#city").val();
	if (type=="collect") {
		type+="store";
	}
	var organId=$("#organId").val();
	var distance=$("#distance").val();
	window.location.href = _ctxPath+'/w/home/staffDetail?staffId='+staffId+'&type='+type+'&organId='+organId+'&cityId='+cityId+'&come=organDetail&distance='+distance+'&city='+encodeURIComponent(encodeURIComponent(city));
}
