$(function() {
	findList(1);
	/*  判断是否滚动到屏幕 */
	$(window).scroll(function(){
	    if($(window).scrollTop() == $(document).height() - $(window).height()){
	    	findList();
	    }
	});

	$('#backIndex').click(function() { //返回按钮
		window.location.href=_ctxPath+ '/w/organ/backIndex';
	});
	
	$("#my_expense").click(function() {//我的消费记录
		var id = $('#id').val();
		window.location.href=_ctxPath+'/w/organ/toExpenseList';
	});

	$('.donate_div').click(function() {//我的提现记录界面
        window.location.href=_ctxPath+'/w/organ/toInputMoney';
        /*
		window.location.href=_ctxPath+'/w/organ/myDonation';
		*/
	});
	/*
	$('#donationRecord').click(function() {//我的转赠记录界面
		window.location.href=_ctxPath+'/w/organ/donationRecord';
	});
	*/
	/*
	$('.cash_div').click(function() {//我的提现记录界面
		window.location.href=_ctxPath+'/w/organ/toInputMoney';
	});
	*/
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
		$.post(_ctxPath + "/w/organ/toExpense",{"page":page,"id":id},
				  function(data){
			var obj=eval(data);
			$("#pages").val(data.pages);
			$("#page").val(data.page);
			for(var i=0;i<obj.data.length;i++){
				var html='<div class="expense_item"><div class="col-7 expense_name"><span>' + obj.data[i].desc + '</span><p><span>'+ obj.data[i].createTime+ '</span></p></div><div class="col-3 expense_money"><span> '
					+ obj.data[i].amount+ '</span></div></div>';
				$("#expense_list").html($("#expense_list").html()+ html);}
		},"json");
}









