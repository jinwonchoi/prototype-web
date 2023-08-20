package com.gencode.issuetool.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.gencode.issuetool.dao.UserInfoDao;
import com.gencode.issuetool.dao.MessageLogDao;
import com.gencode.issuetool.dao.ChatInfoDao;
import com.gencode.issuetool.etc.Constant;
import com.gencode.issuetool.etc.ReturnCode;
import com.gencode.issuetool.etc.Utils;
import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.io.StompObj;
import com.gencode.issuetool.obj.ChatInfo;
import com.gencode.issuetool.obj.MessageLog;
import com.gencode.issuetool.service.PushService;
import com.gencode.issuetool.util.JsonUtils;
import com.gencode.issuetool.websocket.obj.StompMessage;

@Service
public class ChatService {
	private static final Logger log = LoggerFactory.getLogger(ChatService.class);
    @Autowired
    private SimpMessagingTemplate template;
    @Autowired
    MessageLogDao messageLogDao;
    @Autowired
    ChatInfoDao chatInfoDao;
    @Autowired
    UserInfoDao UserInfoDao;
    
	@Autowired
	private PushService pushService;

	@Transactional 
	public Optional<ChatInfo> openChat(ChatInfo chatInfo) {
		chatInfo.setUnreadCnt(0);
		chatInfo.setStatus("N");
		long chatId = chatInfoDao.register(chatInfo);
		Optional<ChatInfo> newChatInfo = chatInfoDao.load(chatId);
		pushService.sendMsgAll(Constant.PUSH_TAG_PROTOTYPE_CHAT_ADD.get(), newChatInfo.get());
		return newChatInfo;
	}
	@Transactional
    public boolean sendMsgToChat(MessageLog msg) {
    	//boolean result = false;
		// 전달자
    	long chatId = 0;
    	if (msg.getChatId() <= 0) {
    		ChatInfo chatInfo = new ChatInfo(msg.getSenderId(), msg.getReceiverId(), -1, "", 0, "N");//
    		chatId = chatInfoDao.register(chatInfo);
    		msg.setChatId(chatId);
    		pushService.sendMsgAll(Constant.PUSH_TAG_PROTOTYPE_CHAT_ADD.get(), chatInfo);
    	} else {
    		chatId = msg.getChatId();
    	}
        long msgId = messageLogDao.register(msg);
        MessageLog messageLog = messageLogDao.load(msgId).get();
		pushService.sendMsgAll(Constant.PUSH_TAG_PROTOTYPE_CHATMSG_ADD.get(), messageLog);
		
		ChatInfo chatInfoA = chatInfoDao.load(chatId).get();
		chatInfoA.setLastMessage(messageLog.getMessage());
		chatInfoA.setLastMessageId(messageLog.getSenderId());
		chatInfoA.setUnreadCnt(chatInfoA.getUnreadCnt()+1);
        chatInfoDao.update(chatInfoA);
		pushService.sendMsgAll(Constant.PUSH_TAG_PROTOTYPE_CHAT_UPDATE.get(), chatInfoDao.load(chatId).get());
        return true;
    }

    /** 
     * 채팅 목록
     */
	public Optional<PageResultObj<List<ChatInfo>>> searchChatList(PageRequest req) {
    	return chatInfoDao.search(req);
    }
    
    
    /**
     * 메시지목록
     */
    public Optional<PageResultObj<List<MessageLog>>> searchMessageList(PageRequest req) {
    	return messageLogDao.search(req);
    }
    
    public Optional<List<MessageLog>> getAllMessageList() {
    	return messageLogDao.loadAll();
    }
    
    /**
     * 메시지목록/페이지
     */
    
    /**
     * 일기완료표시
     */
    public void resetUnReadCnt(long chatId) {
    	chatInfoDao.resetUnreadCnt(chatId);
    	pushService.sendMsgAll(Constant.PUSH_TAG_PROTOTYPE_CHAT_UPDATE.get(), chatInfoDao.load(chatId).get());
    	messageLogDao.updateByChatId(chatId, "R");
    	pushService.sendMsgAll(Constant.PUSH_TAG_PROTOTYPE_CHATMSG_RESET.get(), Long.toString(chatId));
    }
    
    /**
     * 채팅종료
     */
    public void closeChat(long chatId) {
    	ChatInfo chatInfo = chatInfoDao.load(chatId).get();
    	chatInfo.setStatus("C");
    	chatInfo.setUnreadCnt(0);
    	chatInfoDao.update(chatInfo);
    	pushService.sendMsgAll(Constant.PUSH_TAG_PROTOTYPE_CHAT_UPDATE.get(), chatInfoDao.load(chatId).get());
    }
    
}
