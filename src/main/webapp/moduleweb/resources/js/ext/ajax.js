/**
 * contains the ehJS ajax handling module
 * @return {object} self
 */
;(function() {
	if (!window.JSON) $('head').append('<script src="'+ URL.json +'"></script>');
	ajax = {};
	
	ajax.settings = {
		url: '',
		type: 'GET',
		dataType: 'json',
		data: {},
		async: true,
		success: function() {},
		error: function() {},
		complete: function() {},
		ajaxSetHeader: function() {}
	};

	ajax.send = function(options) {
		var settings, options = options || {}, contentType, data;
		$.extend(ajax.settings, options);
		settings = ajax.settings;
		if (eh.utils.objIsNull(settings.data)) {
			contentType = 'application/x-www-form-urlencoded; charset=UTF-8';
			data = null;
		} else {
			contentType = 'application/json; charset=UTF-8';
			if(typeof settings.data === "string") {
				data = settings.data;
			}else {
				data = JSON.stringify(settings.data);
			}
		}
		$.ajax({
			url: settings.url,
			type: settings.type,
			dataType: settings.dataType,
			contentType: contentType,
			data: data,
			async: settings.async,
			beforeSend: function(request) {
		        var t = eh.utils.cookie.get(cookieRelative.token);
		        t && request.setRequestHeader(cookieRelative.token, t);
		        settings.ajaxSetHeader.call(null, request);
		    },
			success: function(data) {
				data = $.extend({status:1,data:{},message:"",entity:{},errors:[]},data);
				settings.data=null;
				settings.success.call(null, data);
			},
			error: function(data) {
				if (data.status === 404) {
					alert('/error/404.html');
				};
				if (data.status === 500) {
					if(data.responseJSON.length>0){
						eh.ui.dialogTip.popup({
		    	            title: dialogText.title,
		    	            content: data.responseJSON[0].message,
		    	            callback: function() {
		    	            	location.reload();
		    	            }
		    	        })
					}else{
						location.href='/error/500.html';
					}
				};
				if (data.status === 401) {
					alert('/error/401.html');
				};
				settings.data=null;
				settings.error.call(null, data);
			},
            complete: function(response) {
            	settings.complete.call(null, response);
            }
		})
	};

	ajax.refreshToken = function() {
		alert("URL.freshToken");
		/*$.ajax({
			url: URL.freshToken,
			type: 'GET',
			dataType: 'json',
			success: function(data) {
				if (refresh.success) {
					eh.utils.token.set();
					eh.utils.cookie.set(cookieRelative.token, data.token);
					ajax.send();
				};
			}
		})*/
	}

	eh.utils.ajax = ajax;

})();