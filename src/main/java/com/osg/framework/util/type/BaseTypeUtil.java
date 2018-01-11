package com.osg.framework.util.type;

/**
 * 基本类型工具
 * 
 * @author xiangf
 * @since JDK5.0
 */
public class BaseTypeUtil {

	/**
	 * 判断是否为空字符串
	 * 
	 * @param arg
	 *            字符串
	 * @return 判断结果
	 */
	public static boolean isEmptyString(String arg) {
		return (arg == null || "".equals(arg.trim()));
	}

	/**
	 * 检测是否是null字符串, 允许空格
	 * 
	 * <pre>
     * Strings.isEmpty("null")    = true
     * Strings.isEmpty(null)      = true
     * Strings.isEmpty("")        = true
     * Strings.isEmpty(" ")       = false
     * Strings.isEmpty("bob")     = false
     * Strings.isEmpty("  bob  ") = false
	 * </pre>
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		return ((str == null) || (str.length() == 0) || "null".equalsIgnoreCase(str));
	}
	/**
	 * 检测是否是空字符串, 不允许空格
	 * 
     * <pre>
     * Strings.isBlank("null")    = true
     * Strings.isBlank(null)      = true
     * Strings.isBlank("")        = true
     * Strings.isBlank(" ")       = true
     * Strings.isBlank("bob")     = false
     * Strings.isBlank("  bob  ") = false
     * </pre>
     * 
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str){
		if(isEmpty(str))
			return true;
		for (int i = 0; i < str.length(); ++i) {
			if (!(Character.isWhitespace(str.charAt(i)))) {
				return false;
			}
		}
		return true;
	}
	/**
	 * 判断是否为空字符串，为空则替换为默认值
	 * 
	 * @param arg
	 *            源字符串
	 * @param defaultString
	 *            默认值
	 * @return 结果
	 */
	public static String replaceEmptyString(String arg, String defaultString) {
		return (arg == null || "".equals(arg.trim())) ? defaultString : arg;
	}

	/**
	 * 判断Integer是否为空，为空则替换为默认值
	 * 
	 * @param arg
	 *            源值
	 * @param defaultInteger
	 *            默认值
	 * @return 结果
	 */
	public static Integer replaceEmptyInteger(Integer arg,
			Integer defaultInteger) {
		return (arg == null) ? defaultInteger : arg;
	}	
	
	/**
	 * 
	 * @param text
	 * @param isEscapeSpace 是否转换空格
	 * @return
	 */
	public static String toHTML(String text, boolean isEscapeSpace) {
		return toHTML(text, isEscapeSpace, false);
	}
	/**
	 * 
	 * @param text
	 * @param isEscapeSpace 是否转换空格
	 * @param isEnter 是否转换回车
	 * @return
	 */
	public static String toHTML(String text, boolean isEscapeSpace,boolean isEnter) {
		if (isEmpty(text)) {
			return text;
		}

		char[] content = new char[text.length()];
		text.getChars(0, text.length(), content, 0);
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < content.length; i++) {
			switch (content[i]) {
			case '\n':
				if(isEnter){
					result.append("<br/>");
				}else{
					result.append(content[i]);
				}
				break;
			case '\r':
//				result.append("");
				break;
			case '<':
				result.append("&lt;");
				break;
			case '>':
				result.append("&gt;");
				break;
			case '&':
				result.append("&amp;");
				break;
			case '\'':
				result.append("&#039;");
				break;
			case '"':
				result.append("&quot;");
				break;
			case ' ':
				if(isEscapeSpace){
					result.append("&nbsp;");
				}
				else{
					result.append(content[i]);
				}
				break;
			default:
				result.append(content[i]);
			}
		}
		return result.toString();
	}
	/**
	 * 反转html的转移符
	 * @param text
	 * @return
	 */
	public static String oppHTML(String text) {
		if (isEmpty(text)) {
			return text;
		}
		return text.replaceAll("&lt;", "<").replaceAll("&gt;", ">")
			.replaceAll("<br/>", "\\\\n").replaceAll("&amp;", "&")
			.replaceAll("&#039;", "'").replaceAll("&quot;", "\\\\\"")
			.replaceAll("&nbsp;", " ");
	}
	/**
	 * 屏蔽html的控件
	 * @param text
	 * @return
	 */
	public static String oppHTMLDom(String text) {
		if (isEmpty(text)) {
			return text;
		}
		return text.replaceAll("\\<(.*?)\\>", "");
	}
	
}
