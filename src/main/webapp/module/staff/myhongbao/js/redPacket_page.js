$(function(){
	redPacketList();
	$(window).scroll(function(){
	    if($(window).scrollTop() == $(document).height() - $(window).height()){
	    	redPacketList();
	    }
	});
});
function redPacketList(){
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
	/**

	 	
	 */
		$.post(_ctxPath + "/w/staffMy/redPacketList",{'page':page},
				function(data){
			var obj=eval(data);
			$("#pages").val(data.pages);
			$("#page").val(data.page);
			var html="";
			if (obj.data.length==0) {
				html+='<div class="notOrder"></div><i class="notOrderTitle">暂无相关数据</i>';
				$("#redPacket_list").html($("#redPacket_list").html()+html);
			}else {
				for(var i=0;i<obj.data.length;i++){
					html+='<li onclick="redPacketDetail(this)"><input type="hidden" value="'+obj.data[i]._id+'"><div class="honbaoList"><div class="col-2"></div><div class="honbao-mess"><div class="up"><h4>'
					+obj.data[i].desc+'</h4></div><div class="down"><h4>'+obj.data[i].count+'</h4><h4>&nbsp;个&nbsp;</h4><h4>';
					if (obj.data[i].scope==1) {
						html+='所有服务过的会员';
					}else if (obj.data[i].scope==2) {
						html+='所有关注客户';
					}/*else if (obj.data[i].scope==3) {
						html+='所有预约客户';
					}*/
					html+='</h4><h4 style="float: right;margin-right:0.5rem;">￥'+obj.data[i].amount+'</h4></div></div><div class="down-time"><div class="col-2"><img src="'+_ctxPath+'/module/staff/images/img/icon_clock_gray.png"></div><div class="col-7"><h4>'
					+obj.data[i].createTime+'</h4></div></div></div></li>';
				}
				$("#redPacket_list").html($("#redPacket_list").html()+html);
			}
		},
		"json");//这里返回的类型有：json,html,xml,text
}
function redPacketDetail(str) {
	 var redId=$(str).children('input:eq(0)').val();
	 window.location.href = _ctxPath + "/w/staffMy/redPacketDetail.do?redId="+redId;
}