;(function() {
	cookie = {};
	cookie.set = function(key, value, expires) {
		var date = new Date(),
			s = location.protocol === 'https:' ? "; secure" : '',
			day = /^[1-9]([0-9]+)?d$/.test(expires),
			hour = /^[1-9]([0-9]+)?h$/.test(expires),
			second = /^[1-9]([0-9]+)?s$/.test(expires);
		if (!expires || !(day || hour || second)) {
			expires = ''
		} else {
			expires = parseInt(expires.substr(0, expires.length - 1), 10);
			if (day) {
				date.setDate(date.getDate() + expires);
			} else if (hour) {
				date.setHours(date.getHours() + expires);
			} else if (second) {
				date.setSeconds(date.getSeconds() + expires);
			}
			expires = "; expires=" + date.toGMTString();
		}
		return (document.cookie = [key, "=", !value ? "" : encodeURIComponent(value.toString()), expires, "; path=/", "; domain=." + document.domain, s].join(""))
	}
	cookie.get = function(key) {
		var value;
		return (value = new RegExp("(?:^|; )" + key + "=([^;]*)").exec(document.cookie)) ? value[1] : null
	}
	cookie.deleteCookie = function(key) {
		if (!key) {
			return false;
		};
		var val = cookie.get(key);
		if (val != null) cookie.set(key, val, -1);
	}
	eh.utils.cookie = cookie;
})();