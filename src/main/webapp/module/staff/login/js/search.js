$(function(){
	 $(document).bind('click',function(){
		 $(".search_suggest").fadeOut();	 
		 });
	$("#search_organ").click(function(){
		var search_input=$(".search_input").val().trim();
		if(search_input==""){
			alert("请输入搜索内容!");
			return;
		}
		$.post(_ctxPath + "/w/staff/searchOrgan",{'keyWord':search_input},
				  function(data){
					var obj=eval(data);
					if(obj.data.length==0){
						var html='<li>没有搜索到相应店铺</li>';
						$(".search_suggest").html(html);
						$(".search_suggest").fadeIn().addClass("no_suggest");
					}else{
						var html="";
						$(".search_suggest").html("");
						for(var i=0;i<obj.data.length;i++){
							html+='<li onclick="sel_organ(this)">'+obj.data[i].name+'<input type="hidden" value="'+obj.data[i]._id+'" />'+
								'<input type="hidden" value="'+obj.data[i].name+'"/></li>';
						}
						$(".search_suggest").html(html);
						$(".search_suggest").fadeIn().removeClass("no_suggest");
					}
				  },
				  "json");//这里返回的类型有：json,html,xml,text
	});
	
	//获取验证码
	$(".get_code").click(function(){
		var phone_reg=/^0?(17[0-9]|13[0-9]|15[0-9]|18[0-9]|14[57])[0-9]{8}$/;
		var phone=$("#phone").val();
		if(!phone_reg.test(phone)){
			alert("输入正确的手机号码！");
		}else{
			$.post(_ctxPath + "/w/staff/getPhoneCode",{'phone':phone,'type':'staff'},
					  function(data){
							alert(data);
					  },
					  "text");//这里返回的类型有：json,html,xml,text
		}
	});
	$(".btn_next").click(function(){
		var phone=$("#phone").val();
		var code=$("#code").val().trim();
		var organId=$("#organId").val();
		var organName=$("#organName").val();
		if(code=="输入验证码"||code==""){
			alert("输入验证码");
			return;
		}
		if(organName==""){
			alert("请选择店铺");
			return;
		}
		$.post(_ctxPath + "/w/staff/wxAddOrgan",{'phone':phone,'code':code,'organId':organId,'organName':organName},
				  function(data){
			var obj=eval(data);
			if("绑定成功"==obj.msg){
				window.location.href = _ctxPath + "/w/staff/toAddorganinfo.do?staffId="+obj.staffId+"&state=addorgantrue";
			}else{
				alert(obj.msg);
			}
			
				  },
				  "json");//这里返回的类型有：json,html,xml,text
	});
});
function sel_organ(t){
	var organId=$(t).children('input:eq(0)').val();
	var organName=$(t).children('input:eq(1)').val();
	$("#organId").val(organId);
	$("#organName").val(organName);
	$(".search_input").val(organName);
	$(".search_suggest").fadeOut();
}
