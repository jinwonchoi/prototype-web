package com.gencode.issuetool.obj;

import java.util.List;

import com.gencode.issuetool.io.PageResultObj;

public class NoticePersonalEx extends NoticePersonal {
	protected String title;
	protected String message;
	
	public NoticePersonalEx(long refId, long userId, String noticeType,String title, String message) {
		super();
		this.refId = refId;
		this.userId = userId;
		this.noticeType = noticeType;
		this.title = title;
		this.message = message;
	}
	
	public NoticePersonalEx() {
		super();	
	}
		
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
