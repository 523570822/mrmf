package com.osg.entity;


/**
 * 微信文本类消息类
 * @author yangshaodong
 */
public class TextMessage extends BaseMessage {
	private String Content;  //文本消息内容
	private String MsgId;    //为本消息的id
	
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public String getMsgId() {
		return MsgId;
	}
	public void setMsgId(String msgId) {
		MsgId = msgId;
	}
}
