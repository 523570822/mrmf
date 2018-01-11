(function() {
	shade = {};
	
	shade.settings = {
		msg:"操作中...请耐心等待"
	}
	
	shade.show = function(options){
		var settings, options = options || {};
		options && typeof options ==='object' && $.extend(shade.settings, options);
		typeof options === 'string' && (shade.settings.msg = options);
		settings = shade.settings;
		var m = '<div class="spiner-example"><div class="sk-spinner sk-spinner-chasing-dots"><div class="sk-dot1"></div><div class="sk-dot2"></div></div></div>';
		$('.swfMask').length === 0 ? 
		($('body').append('<div class="swfMask"><p><span class="fs2">'+m+'</span></p></div>'),$('.swfMask').width($(document).width()).height($(document).height())) : 
			($('.swfMask').find("p span").text(settings.msg),$('.swfMask').show());
		$('.swfMask p span').unbind('click').click(function() {
			$('.swfMask').hide();
		});
	}
	
	shade.hide = function (){
		$('.swfMask').length !== 0 ? $('.swfMask').hide() : null;
	}
	
	$.shade = shade;
})();
