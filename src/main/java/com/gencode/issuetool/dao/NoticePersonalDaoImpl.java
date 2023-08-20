package com.gencode.issuetool.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.NotSupportedException;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.gencode.issuetool.etc.Constant;
import com.gencode.issuetool.etc.ObjMapper;
import com.gencode.issuetool.etc.Utils;
import com.gencode.issuetool.io.PageRequest;
import com.gencode.issuetool.io.PageResultObj;
import com.gencode.issuetool.io.SearchMapObj;
import com.gencode.issuetool.obj.NoticeBoard;
import com.gencode.issuetool.obj.NoticePersonal;
import com.gencode.issuetool.obj.NoticePersonalEx;


@Component
public class NoticePersonalDaoImpl extends AbstractDaoImpl implements NoticePersonalDao {
	
	public NoticePersonalDaoImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		super(jdbcTemplate, namedParameterJdbcTemplate);
	}

	@Override
	public long register(NoticePersonal t) {
		return 0;
	}

	@Override
	public Optional<NoticePersonal> load(long id) {
		//throw new NotSupportedException();
		return null;
	}
	
	@Override
	public long delete(long id) {
		return -1;
	}

	@Override
	public long deleteByRefId(long refId, String noticeType) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("refId", refId)
		.addValue("noticeType", noticeType);
		return namedParameterJdbcTemplate.update("DELETE FROM notice_personal where ref_id = :refId and notice_type=:noticeType",
				namedParameters);
	}
	
	@Override
	public long deleteByUserId(long userId, String noticeType) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("userId", userId)
		.addValue("noticeType", noticeType);
		return namedParameterJdbcTemplate.update("DELETE FROM notice_personal where user_id = :userId and notice_type=:noticeType",
				namedParameters);
	}
	
	@Override
	public long forceDelete(NoticePersonal t) {
		return namedParameterJdbcTemplate.update("DELETE FROM notice_personal where ref_id = :refId and user_id = :userId and notice_type=:noticeType",
				new BeanPropertySqlParameterSource(t));
	}
	
	@Override
	public long update(NoticePersonal t) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Optional<List<NoticePersonal>> loadAll() {
		throw new UnsupportedOperationException();
	}
	@Override
	public Optional<List<NoticePersonal>> search(Map<String, String> map) {
		throw new UnsupportedOperationException();
	}
	@Override
	public Optional<List<NoticePersonalEx>> searchEx(Map<String, String> map) {
		String queryStr = "select b.ref_id, b.user_id, b.notice_type, a.content message, a.title from notice_personal b, proto_notice_board a " + 
				"where b.ref_id = a.id" + 
				"  and b.notice_type = 'N' " + 
				"  and b.user_id = :userId " + 
				"union all " + 
				"select b.ref_id, b.user_id, b.notice_type, a.last_message message, a.last_message title from notice_personal b, proto_chat_info a " + 
				"where b.ref_id = a.id " + 
				"  and b.notice_type = 'C' " + 
				"  and b.user_id = :userId";
		String userId = map.get("userId");
		List<NoticePersonalEx> t = namedParameterJdbcTemplate.query(
				queryStr, 
				new MapSqlParameterSource("userId", userId),
				new BeanPropertyRowMapper<NoticePersonalEx>(NoticePersonalEx.class));
		return Optional.of(t);
	}
	
	private NoticePersonalEx toNoticePersonalEx(ResultSet resultSet) throws SQLException {
		NoticePersonalEx obj = new NoticePersonalEx();
		obj.setRefId(resultSet.getLong("REF_ID"));
		obj.setUserId(resultSet.getLong("USER_ID"));
		obj.setNoticeType(resultSet.getString("NOTICE_TYPE"));
		obj.setTitle(resultSet.getString("TITLE"));
		obj.setMessage(resultSet.getString("MESSAGE"));
		return obj;
	}

	@Override
	public Optional<PageResultObj<List<NoticePersonal>>> search(PageRequest req) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long insertAll(long refId, String noticeType) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("refId", refId)
		.addValue("noticeType", noticeType);
		return namedParameterJdbcTemplate.update("INSERT INTO notice_personal (ref_id, user_id, notice_type) " + 
				"select :refId, id, :noticeType from user_info where user_status <> 'D'",
				namedParameters);
	}
}
