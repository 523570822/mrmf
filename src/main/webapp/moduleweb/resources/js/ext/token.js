(function() {
	var c = eh.utils.cookie,
	token = {};
	token.quit = function() {
		c.deleteCookie(cookieRelative.token);
		c.deleteCookie(cookieRelative.token_encode);
		c.deleteCookie(cookieRelative.userid);
		c.deleteCookie(cookieRelative.username);
		c.deleteCookie(cookieRelative.userpwd);
		c.deleteCookie(cookieRelative.userrole);
	}
	
	token.update = function() {
	}
	eh.utils.token = token;
})();