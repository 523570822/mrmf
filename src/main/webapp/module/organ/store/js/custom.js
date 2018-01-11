$(function(){
	$.ajaxSetup({
		  async: false
	});
	$("#custom_back").click(function(){
		var organId=$("#organId").val();
		window.location.href = _ctxPath + "/w/organ/toIndex.do?organId="+organId;
	});
	$(".custom_tab li").click(function(){
		$(this).parent().children("li").removeClass("active"); 
		$(this).addClass("active");
		var index=$(this).index();
		$("#type").val(index);
		$("#page").val("");
		$("#pages").val("");
		$("#custom_list").html("");
		custom();
	});
	$(window).scroll(function(){
	    if($(window).scrollTop() == $(document).height() - $(window).height()){
	    	custom();
	    	
	    }
	});
	custom();
});
function custom(){
	var organId=$("#organId").val();
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
	$.post(_ctxPath + "/w/organ/organCustomList",{'organId':organId,'page':page,'type':type},
			  function(data){
		 	  	var obj=eval(data);
		 	  	$("#pages").val(data.pages);
				$("#page").val(data.page);
				if(obj.data.length==0){
					$("#custom_list").addClass("nodateul");
					var html='<li style="background:#eee;border:0;" class="nodatali"><div class="notDate"></div><div class="notDateTitle">暂无相关数据</div></li>';
					$("#custom_list").html($("#custom_list").html()+html);
				}else{
					$("#custom_list").removeClass("nodateul");
			 	  	for(var i=0;i<obj.data.length;i++){
			 	  		var html='<li class="cutorm_arrow"><div class="col-3" style="text-align: center;margin-top:1.2rem;">';
			 	  		if(obj.data[i].avatar==""||obj.data[i].avatar=="null"){
			 	  			html+='<img src="'+_ctxPath+'/module/resources/images/nopicture.jpg">';
			 	  		}else{
			 	  			html+='<img src="'+_ossImageHost+obj.data[i].avatar+'@!avatar'+'" alt="头像"/>';
			 	  		}
			 	  		
			 	  		html+= '</div><div class="col-5"><h4 class="font-32">'+obj.data[i].nick+'</h4>';
			 	  			    if(typeof(obj.data[i].phone)=="undefined"){
			 	  			    	html+='<p class="font-30"></p>';
			 	  			    }else{
			 	  			    	html+='<a class="custom_tel" href="tel:'+obj.data[i].phone+'"><img src="'+_ctxPath+'/module/resources/images/store/icon_telephone.png"><p class="font-30">电话 '+obj.data[i].phone+'</p></a>';
			 	  			    }
			 	  			    if(typeof(obj.data[i].orderTime)=="undefined"){
			 	  			    	html+='<span></span></div>';
			 	  			    }else{
			 	  			    	html+='<span>'+obj.data[i].orderTime+'</span></div>';
			 	  			    }
			 	  			    
			 	  			    
			 	  			     
			 	  		if(obj.data[i].userType!=""&&typeof(obj.data[i].userType)!="undefined"){
			 	  			//html+='<div class="col-2" onclick="customDetail('+"'"+obj.data[i]._id+"'"+')"><i>会员</i>';
			 	  			html+='<div class="col-2"><i>会员</i>';
			 	  		}else if(obj.data[i].orderNum==1){
			 	  			html+='<div><i>首单</i>';
			 	  		}
			 	  		html+='</div></li>';
			 	  		$("#custom_list").html($("#custom_list").html()+html);
			 	  	}
				}
			  },
			  "json");//这里返回的类型有：json,html,xml,text	
}
function customDetail(str) {
	var type="store";
	var organId=$("#organId").val();
	window.location.href = _ctxPath + "/w/staffMy/customerDetail.do?userId="+str+"&type="+type+"&organId="+organId;
}