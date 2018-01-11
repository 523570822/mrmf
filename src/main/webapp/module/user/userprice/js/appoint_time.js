$(function() {
	var myScroll = new IScroll('#wrapper', { mouseWheel:true, click:true });
	var myScroll2 = new IScroll('#wrapper2', { mouseWheel:true,click:true} );
	myScroll.refresh();
	myScroll.enable();
	myScroll2.refresh();
	myScroll2.enable();
	$('#week').find('li').click(function() {
		    $('#time').html('');
			var day = $(this).find('input').eq(0).val();
			var staffId = $(this).find('input').eq(1).val();
			var organId = $(this).find('input').eq(2).val();
			queryTime(day,staffId,organId);
	});
	$('#week').find('li').click(function() {
		var day = $(this).find('input').eq(0).val();
		var staffId = $(this).find('input').eq(1).val();
		var organId = $(this).find('input').eq(2).val();
		var organName = $(this).find('input').eq(3).val();
		var replyId = $(this).find('input').eq(4).val();
		$('#dayF').val($.trim(day));
		$('#staffIdF').val($.trim(staffId));
		$('#organIdF').val($.trim(organId));
		$('#organNameF').val($.trim(organName));
		$('#quoteIdF').val($.trim(replyId));
	});
	if($('#week').find('li').hasClass('active')) {
		var day = $($('#week').find('li')).find('input').eq(0).val();
		var staffId = $($('#week').find('li')).find('input').eq(1).val();
		var organId = $($('#week').find('li')).find('input').eq(2).val();
		var organName = $($('#week').find('li')).find('input').eq(3).val();
		var quoteId = $($('#week').find('li')).find('input').eq(4).val();
		$('#dayF').val($.trim(day));
		$('#staffIdF').val($.trim(staffId));
		$('#organIdF').val($.trim(organId));
		$('#organNameF').val($.trim(organName));
		$('#quoteIdF').val($.trim(quoteId));
		queryTime(day,staffId,organId);
	}
	$('.rightCom').click(function(){
		if($('#week').find('li').hasClass('active')) {
			if($('#time1F').val()=="") {
				layer.msg("请选择时间");
				return false;
			}
			$('#formField').submit();
		}
	});
	
	function queryTime(day,staffId,organId) {
		$.post(_ctxPath + "/w/home/queyTime",{'day':day,'staffId':staffId,'organId':organId },
				  function(data){
			var obj=eval(data);
			var html = '';
			 if(obj.time8 == true) {
			    	html +='<li class="ok">08:00</li>';
			    } else {
			    	html +='<li>08:00</li>';
			    }
			    if(obj.time9 == true) {
			    	html +='<li class="ok">09:00</li>';
			    } else {
			    	html +='<li>09:00</li>';
			    }
			    if(obj.time10 == true) {
			    	html +='<li class="ok">10:00</li>';
			    } else {
			    	html +='<li>10:00</li>';
			    }
			    if(obj.time11 == true) {
			    	html +='<li class="ok">11:00</li>';
			    } else {
			    	html +='<li>11:00</li>';
			    }
			    if(obj.time12 == true) {
			    	html +='<li class="ok">12:00</li>';
			    } else {
			    	html +='<li>12:00</li>';
			    }
			    if(obj.time13 == true) {
			    	html +='<li class="ok">13:00</li>';
			    } else {
			    	html +='<li>13:00</li>';
			    }
			    if(obj.time14 == true) {
			    	html +='<li class="ok">14:00</li>';
			    } else {
			    	html +='<li>14:00</li>';
			    }
			    if(obj.time15 == true) {
			    	html +='<li class="ok">15:00</li>';
			    } else {
			    	html +='<li>15:00</li>';
			    }
			    if(obj.time16 == true) {
			    	html +='<li class="ok">16:00</li>';
			    } else {
			    	html +='<li>16:00</li>';
			    }
			    if(obj.time17 == true) {
			    	html +='<li class="ok">17:00</li>';
			    } else {
			    	html +='<li>17:00</li>';
			    }
			    if(obj.time18 == true) {
			    	html +='<li class="ok">18:00</li>';
			    } else {
			    	html +='<li>18:00</li>';
			    }
			    if(obj.time19 == true) {
			    	html +='<li class="ok">19:00</li>';
			    } else {
			    	html +='<li>19:00</li>';
			    }
			    if(obj.time20 == true) {
			    	html +='<li class="ok">20:00</li>';
			    } else {
			    	html +='<li>20:00</li>';
			    }
			    if(obj.time21 == true) {
			    	html +='<li class="ok">21:00</li>';
			    } else {
			    	html +='<li>21:00</li>';
			    }
			    if(obj.time22 == true) {
			    	html +='<li class="ok">22:00</li>';
			    } else {
			    	html +='<li>22:00</li>';
			    }
			    if(obj.time23 == true) {
			    	html +='<li class="ok">23:00</li>';
			    } else {
			    	html +='<li>23:00</li>';
			    }
			    $('#time').html(html);
			    $("#time li").each(function(index){
					var intTime=$("#int_time").val(); 
					var intDay=$("#int_day").val(); 
					var dayF=$("#dayF").val(); 
					var start=$("#start").val(); 
					var end=$("#end").val(); 
					if(Number(intTime)>=index+8 && intDay==dayF){
						$(this).addClass("oldTime");
						if ($(this).hasClass('ok')) {
							$(this).removeClass('ok');
						}
					}
					if (start !="" && end !="") {
						if (Number(start)<=index+8 && Number(end)>=index+8) {
							$(this).addClass("busy");
						}
					}
				});
			    $('#time li').click(function(){
			    	if ($(this).hasClass("oldTime")) {
			    	}else if($(this).hasClass('ok')) {
			    		var that = this;
			    		if ($(this).hasClass('busy')) {
			    			layer.confirm('你选择的时间在技师繁忙时间段,确定要选择吗?', function(index){
			    				$('#time1F').val($(that).text());
								$('#timeNum').val($(that).index()+8);
			    				$(that).addClass('active').siblings().removeClass('active');
			    				layer.close(index);
			    			});
						}else {
							$(this).addClass('active').siblings().removeClass('active');
							$('#time1F').val($(that).text());
							$('#timeNum').val($(that).index()+8);
						}
			    	}
			    });
		},"json");
	}
});
