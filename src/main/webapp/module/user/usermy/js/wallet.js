$(function() {
	findList(1);
	/*  判断是否滚动到屏幕 */
	$(window).scroll(function(){
	    if($(window).scrollTop() == $(document).height() - $(window).height()){
	    	findList();
	    }
	});
	
	$("#my_expense").click(function() {//我的消费记录
		var id = $('#id').val();
		window.location.href=_ctxPath+'/w/userMy/toExpenseList';
	});
	$('.donate_div').click(function() {
		window.location.href=_ctxPath+'/w/userMy/myDonation';
	});
	$('#donationRecord').click(function() {
		window.location.href=_ctxPath+'/w/userMy/donationRecord';
	});
	$('.cash_div').click(function() {
		window.location.href=_ctxPath+'/w/userMy/toInputMoney';
	});
});	
function findList(page){
		if(page==1){
			$("#expense_list").html("");
		}else{
			 var nextPage=parseInt($("#page").val())+1;
			 page=nextPage;
			 var pages=parseInt($("#pages").val());
			 if(nextPage>pages){
				 return;
			 }
		}
		
		var id = $.trim($('#id').val());
		$.post(_ctxPath + "/w/userMy/toExpense",{'page':page,"id":id},
				  function(data){
			var obj=eval(data);
			$("#pages").val(data.pages);
			$("#page").val(data.page);
			for(var i=0;i<obj.data.length;i++){
				var html ='<div class="expense_item"><div><div class="expense_div">'+
                '<div class="col-5 expense_name" style="width:80%;"><span>'+ obj.data[i].desc +'</span></div>'+ 
                 '<div class="col-5 expense_money "style="width:18%;"><span>' + obj.data[i].amount+'</span></div></div><div class="expense_time">'+
                  '<div><span>' + obj.data[i].createTime+'</span></div></div></div></div>';
				$("#expense_list").html($("#expense_list").html()+ html);}
		},"json");
}









