var URL = {
	json: '//cdnjs.cloudflare.com/ajax/libs/json3/3.3.2/json3.min.js',
	login: _ctxPath+'/login',
	logout: _ctxPath+'/logout',
	fileupload: _ctxPath+'/back/super/file/upload.do',
	filepage: _ctxPath+'/back/super/file/filepage.do'
},
dialogText = {
	title: '温馨提示',
	content: '操作成功！',
	confirmeBtnText: '确定',
	cancelBtnText: '取消',
	width: 400,
	larger: 500,
	quit: '您长时间未进行操作，3秒后自动跳转到登录页面',
	noaccess:'您无此登陆权限'
},
consolelogText = {
	nosuchdom:'没有这个dom对象'
},
cookieRelative = {
	basic: 'Basic',
	authorization: 'Authorization',
	token: 'X-Auth-Token',
	token_encode: 'X-Auth-Token-Encode',
	userid: 'X-Userid',
	username: 'X-Username',
	userpwd: 'X-Password',
	userrole: 'role',
	tokenTime: '_tokenTime',
	internal: 'message',
	referrer: 'referrer',
	quitsecond: 3000,
	tokenExpires: 1200000,
	pending_confirm: 15,
	pending_kypay_confirmed:23
},
pageText = {
	dataNull: '暂无信息',
	pageHeader:'Range',
	pageEntity:'Entity'
};