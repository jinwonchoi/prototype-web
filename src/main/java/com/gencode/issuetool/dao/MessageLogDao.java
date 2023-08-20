package com.gencode.issuetool.dao;

import com.gencode.issuetool.obj.MessageLog;

public interface MessageLogDao extends Dao<MessageLog> {
	long updateByChatId(long chatId, String status);
}
