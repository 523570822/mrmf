(function() {
	function Login(options) {
		var m = this;
		m.options = options || {};
		$.extend(m, m.options);
		m.loginSubmit = m.loginSubmit || 'form input[type=button]';
		m.accountName = m.accountName || '#accountName';
		m.accountPwd = m.accountPwd || '#accountPwd';
	}
	Login.prototype.init = function() {
		var m = this;
		m.regEvent();
	}
	Login.prototype.regEvent = function() {
		var m = this;
		/*
		 * $(m.loginSubmit).click(function(){
		 * 
		 * });
		 */
	}

	var login = new Login();
	login.init();

})()

function submitForm() {
	var accountName = $("#accountName").val();
	var accountPwd = $("#accountPwd").val();
	var userdetail = cookieRelative.basic + " "
			+ eh.utils.Base64.encode(accountName + ":" + accountPwd);
	eh.utils.ajax.send({
		type : 'POST',
		url : URL.login,
		data : {
			verifyCode : $("#verifyCode").val()
		},
		ajaxSetHeader : function(request) {
			request.setRequestHeader(cookieRelative.authorization, userdetail);
		},
		success : function(data) {

			if (!data.success || data.status == 0) {
				layer.alert(data.message);
				$.shade.hide();
			} else {
				var entity = data.entity;
				eh.utils.cookie.set(cookieRelative.userid, entity._id, 1);
				eh.utils.cookie.set(cookieRelative.username,
						entity.accountName, cookieRelative.tokenExpires);
				eh.utils.cookie.set(cookieRelative.userpwd, entity.accountPwd,
						cookieRelative.tokenExpires);
			}
		},
		error : function(data) {
			$.shade.hide();
			layer.alert("error:" + $.toJSON(data));
		},
		complete : function(response) {

			var token = response.getResponseHeader(cookieRelative.token);
			if (token != null && token != "null") {
				eh.utils.cookie.set(cookieRelative.token, token,
						cookieRelative.tokenExpires);
			window.location.href = _ctxPath + "/login/main.html";
			}
		}
	});
	return false;
}
