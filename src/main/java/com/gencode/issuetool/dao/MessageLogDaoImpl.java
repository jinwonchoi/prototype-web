package com.gencode.issuetool.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.gencode.issuetool.etc.ObjMapper;
import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.io.SearchMapObj;
import com.gencode.issuetool.obj.MessageLog;

@Component
public class MessageLogDaoImpl extends AbstractDaoImpl implements MessageLogDao {
	final String fieldList =  "id, chat_id, sender_id, receiver_id, status, message, updated_dtm, created_dtm"; 

	public MessageLogDaoImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		super(jdbcTemplate, namedParameterJdbcTemplate);
	}

	@Override
	public long register(MessageLog t) {
			namedParameterJdbcTemplate.update("INSERT INTO message_log (id, chat_id, sender_id, receiver_id, status, message, updated_dtm, created_dtm)" + 
					"VALUES(:chatId, :senderId, :receiverId, :status, :message, NOW(3), NOW(3) )"
					,new BeanPropertySqlParameterSource(t));
			return -1;
	}

	@Override
	public Optional<MessageLog> load(long id) {
		throw new UnsupportedOperationException();
	}

	public Optional<MessageLog> load(String id) {
		MessageLog t = DataAccessUtils.singleResult(namedParameterJdbcTemplate.query("select "+fieldList+" from message_log where id = :id",
				new MapSqlParameterSource("id",id ), (resultSet,i)->{
					return ObjMapper.toMessageLog(resultSet);
				}));
		return Optional.of(t);
	}

	@Override
	public long delete(long id) {
		throw new UnsupportedOperationException();
	}

	public long delete(String id) {
		return namedParameterJdbcTemplate.update("DELETE FROM message_log where id = :id",
				new MapSqlParameterSource("id", id));
	}

	@Override
	public long update(MessageLog t) {
		return namedParameterJdbcTemplate.update("UPDATE message_log SET " + 
				"chat_id=:chatId,"+
				"sender_id=:senderId,"+
				"receiver_id=:receiverId,"+
				"status=:status,"+
				"message=:message,"+ 
				"updated_dtm=now(3)"+
				"WHERE id = :id"
				,new BeanPropertySqlParameterSource(t));		 
	}

	@Override
	public Optional<List<MessageLog>> loadAll() {
		StringBuffer sb = new StringBuffer();
		sb.append("select "+fieldList+" from message_log");
		List<MessageLog> list = jdbcTemplate.query(sb.toString(), (resultSet, i) -> {
            return ObjMapper.toMessageLog(resultSet);
        });
		return Optional.of(list);
	}
	
	@Override
	public Optional<PageResultObj<List<MessageLog>>> search(PageRequest req) {
		StringBuffer sb = new StringBuffer();
		sb.append("select "+fieldList+" from message_log");
		sb.append(" where 1=1");
		return internalSearch(sb.toString(), req, MessageLog.class);
	}

	@Override
	public Optional<List<MessageLog>> search(Map<String, String> map) {
		StringBuffer sb = new StringBuffer();
		sb.append("select "+fieldList+" from message_log");
		sb.append(" where 1=1");
		SearchMapObj searchMapObj = new SearchMapObj(map);
		List<MessageLog> t = namedParameterJdbcTemplate.query
				(sb.toString()
						+ searchMapObj.andQuery()
						, searchMapObj.params()
						, new BeanPropertyRowMapper<MessageLog>(MessageLog.class));
		return Optional.of(t);
	}

	@Override
	public long updateByChatId(long chatId, String status) {
		return namedParameterJdbcTemplate.update("UPDATE message_log SET " + 
				"chat_id=:chatId,"+
				"sender_id=:senderId,"+
				"receiver_id=:receiverId,"+
				"status=:status,"+
				"message=:message,"+ 
				"updated_dtm=now(3)"+
				"WHERE chat_id = :chatId"+
				" and status= :status",
				new MapSqlParameterSource(
						new HashMap<String, String>(){{
							put("chatId",Long.toString(chatId));
							put("status",status);					
						}}
					)
				);		 
	}

}
