;(function() {
	function isType(type) {
		return function(obj) {
			return {}.toString.call(obj) == "[object " + type + "]"
		}
	}
	function objIsNull(obj) {
		for(var i in obj){
			return false
		} 
		return true; 
	}
	function buildPageParameter(pageNum,pageSize){
		return "&page="+pageNum+"&size="+pageSize;
	}
	// 截取URL参数
	function getQueryString(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
		var r = window.location.search.substr(1).match(reg);
		if (r != null)
			return unescape(r[2]);
		return null;
	}
	//去掉前后空格
	function trim (name){
		return name.replace(/(^\s*)|(\s*$)/g,'');
	}
	
	eh.utils.isObject = isType("Object");
	eh.utils.isString = isType("String");
	eh.utils.isArray = Array.isArray || isType("Array");
	eh.utils.isFunction = isType("Function");
	eh.utils.isNumber = isType("Number");
	eh.utils.objIsNull = objIsNull;
	eh.utils.buildPageParameter = buildPageParameter;
	eh.utils.getQueryString = getQueryString;
	eh.utils.trim = trim;
})();