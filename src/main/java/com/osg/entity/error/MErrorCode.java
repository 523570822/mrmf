package com.osg.entity.error;

public enum MErrorCode {
	//1:oauth
	e1001(1001,"Access token缺失"),
	e1002(1002,"Debug模式缺少uid参数"),
	e1003(1003,"无效的token"),
	e1010(1010,"登陆失败"),
	e1011(1011,"无此权限"),
	//2:
	e2001(2001,"非法的请求参数"),
	e2002(2002,"不被支持的请求方法"),
	e2003(2003,"没有匹配的请求处理器"),
	e2004(2004,"不被支持的MediaType"),
	e2005(2005,"非法的请求"),
	e2006(2006,"下载文件不存在"),
	e2007(2007,"群组禁止转发"),
	e2008(2008,"消息已被处理"),
	e3000(3000,"网络异常，请稍候再试"),
	e3001(3001,"调用远程服务失败"),
	e3002(3002,"POJO转换失败"),
	e3003(3003,"获取i18n message失败"),
	e3004(3004,"Spring mvc 标准异常"),
	e3005(3005,"调用远程OAUTH服务失败"),
	e3006(3006,"调用远程Dubbo服务失败"),
	e8000(8000,"请检查你的网络"),
	e8001(8001,"HTTP请求错误"),
	e8002(8002,"读取HTTP响应错误"),
	e8003(8003,"解析json数据异常"),
	e8010(8010,"存储卡缺失"),
	e8011(8011,"数据库缺失"),
	e8012(8012,"数据库初始化错误"),
	e8015(8015,"数据库脚本执行错误"),
	e8016(8016,"数据库数据读取错误"),
	e8017(8017,"数据库数据保存错误"),
	e8018(8018,"数据库数据删除错误"),
	e8019(8019,"数据库数据修改错误"),
	e8020(8020,"文件缺失"),
	e8021(8021,"文件读取异常"),
	e9000(9000,"用户名或密码错误"),
	e9001(9001,"用户已锁定无法登陆请与管理员联系"),
	e9002(9002,"数据错误"),
	e9003(9003,"原始密码错误"),
	e9004(9004,"账号不存在"),
	e9005(9005,"用户名已存在"),
	e9006(9006,"账号已被引用"),
	e10001(10001,"无此文件类型")
	;

	private int code;
	private String desc;
	private MErrorCode(int code, String desc) {
	    this.code = code;
	    this.desc = desc;
	}
	
	public int code() {
		return code;
	}

	public String desc() {
		return desc;
	}

	public static String desc(int code){
		for(MErrorCode error : MErrorCode.values()){
			if(error.code == code)
				return error.desc();
		}
		return "未知错误";
	}
}
