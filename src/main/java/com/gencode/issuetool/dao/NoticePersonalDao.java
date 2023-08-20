package com.gencode.issuetool.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.gencode.issuetool.obj.NoticePersonal;
import com.gencode.issuetool.obj.NoticePersonalEx;

public interface NoticePersonalDao extends Dao<NoticePersonal> {
	long deleteByRefId(long refId, String noticeType);
	long deleteByUserId(long userId, String noticeType);
	long forceDelete(NoticePersonal noticePersonal);
	long insertAll(long refId, String noticeType);
	Optional<List<NoticePersonalEx>> searchEx(Map<String, String> map);
}
