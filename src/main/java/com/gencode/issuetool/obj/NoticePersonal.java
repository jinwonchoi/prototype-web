package com.gencode.issuetool.obj;

public class NoticePersonal extends Pojo {
	protected long refId;
	protected long userId;
	protected String noticeType;

	public NoticePersonal() {
		super();
	}

	public NoticePersonal(long refId, long userId, String noticeType) {
		super();
		this.refId = refId;
		this.userId = userId;
		this.noticeType = noticeType;
	}

	public long getRefId() {
		return refId;
	}

	public void setRefId(long refId) {
		this.refId = refId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}


}
