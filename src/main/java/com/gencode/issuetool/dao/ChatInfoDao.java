package com.gencode.issuetool.dao;

import java.util.List;
import java.util.Optional;

import com.gencode.issuetool.obj.ChatInfo;
import com.gencode.issuetool.dao.Dao;

public interface ChatInfoDao extends Dao<ChatInfo> {
	void resetUnreadCnt(long chatId);
}
