$(function(){
	var staffState=$("#staffState").val();
	if(staffState=="isAdd"){
		$(".tip p").addClass("failure").html("您已经申请了加入店铺请耐心等待");
	}else if(staffState=="exist"){
		$(".tip p").addClass("failure").html("您已经加入店铺请退出公众号重新进入任性猫");
	}else if(staffState=="addorgantrue"){
		$(".tip p").addClass("through").html("申请已发送请耐心等待");
	}else if(staffState=="noAdd"){
		$(".tip p").addClass("failure").html("您申请加入的店铺已经跟您解约请重新加入店铺");
	}
});