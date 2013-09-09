package com.xinguo.dop.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.xinguo.dop.dao.BaseJdbcDao;
import com.xinguo.dop.dao.JdbcPageInfo;
import com.xinguo.dop.dao.RecordDao;
import com.xinguo.dop.entity.Callrecord;
import com.xinguo.dop.entity.query.RecordJdbcQuery;
import com.xinguo.util.common.BeanToMapUtil;
import com.xinguo.util.page.PageResult;

@Repository("recordDao")
public class RecordDaoImpl extends BaseJdbcDao implements RecordDao {

	private static final String Select_All = "select * from callrecord";

	private static final String Get_All_ByTicketId = "select * from callrecord where ticketid=? and  recordlength > 0 and recordcorrect = 0  order by recordstarttime";

	private static final String Get_Muti_Record = "SELECT cr.* FROM callrecord cr left join review r on cr.ticketid=r.ticketid left join calltickets ct on cr.ticketid=ct.sequenceNumber where 1=1 and cr.recordid IN (?)";

	private static final String Get_All_ByRecordId = "select * from callrecord where recordid=? order by recordstarttime";

	private static final String Get_All_ByTicketid = "select savefilename from callrecord where ticketid=? order by recordstarttime";

	private static final String Get_All_InTicketid = "SELECT ticketid,group_concat(savefilename separator ',') savefilename from callrecord where ticketid in (?) group BY ticketid";

	private static final String UPDATE = "update callrecord set type=:type,answer=:answer,numbertype=:numbertype,checknumber=:checknumber,checknumbertime=:checknumbertime,dealproblem=:dealproblem,serviceterms=:serviceterms,isbad=:isbad,checktime=:checktime,operatorname=:operatorname where recordid=:recordid";

	private static final String DEL = "delete from callrecord where recordid=?";

	// private static final String DELMutil =
	// "DELETE from callrecord where recordid in (SELECT recordid from (SELECT recordid FROM callrecord cr left join review r on cr.ticketid=r.ticketid left join calltickets ct on cr.ticketid=ct.sequenceNumber where 1=1 and cr.recordid IN (?)) de )";

	private static final String DELMutil = "update callrecord set recordcorrect = 1 where recordid in (SELECT recordid from (SELECT recordid FROM callrecord cr left join review r on cr.ticketid=r.ticketid left join calltickets ct on cr.ticketid=ct.sequenceNumber where 1=1 and cr.recordid IN (?)) de )";

	// private org.springframework.jdbc.core.simple.SimpleJdbcTemplate
	// simpleJdbcTemplate;
	//
	// public org.springframework.jdbc.core.simple.SimpleJdbcTemplate
	// getSimpleJdbcTemplate() {
	// return simpleJdbcTemplate;
	// }
	//
	// @Resource
	// public void setSimpleJdbcTemplate(
	// org.springframework.jdbc.core.simple.SimpleJdbcTemplate
	// simpleJdbcTemplate) {
	// this.simpleJdbcTemplate = simpleJdbcTemplate;
	// }

	private org.springframework.jdbc.core.JdbcTemplate jdbcTemplate;

	public org.springframework.jdbc.core.JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	@Resource
	public void setJdbcTemplate(
			org.springframework.jdbc.core.JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public Callrecord getCallRecordByCallRecordId(Callrecord callrecord) {
		return getJdbcTemplate().queryForObject(Get_All_ByRecordId,
				new BeanPropertyRowMapper<Callrecord>(Callrecord.class),
				callrecord.getRecordid());
	}

	public void updateRecord(Callrecord callrecord) {
		Map<String, Object> callrecordMap = BeanToMapUtil.beanToMap(callrecord);
		getJdbcTemplate().update(UPDATE, callrecordMap);
	}

	public List<Callrecord> getAllCallRecords() {
		return getJdbcTemplate().query(Select_All,
				new BeanPropertyRowMapper<Callrecord>(Callrecord.class));
	}

	public PageResult pageList(JdbcPageInfo pageInfo) {
		return findByPage(pageInfo);
	}

	public void delRecord(RecordJdbcQuery recordJdbcQuery) {
		String sql = "";
		if (recordJdbcQuery.getMutilid() != null) {
			sql = DELMutil;
			sql = sql.replace("?", recordJdbcQuery.getMutilid());
		} else {
			String query = "";
			if (recordJdbcQuery.getOperatorName() != null
					&& recordJdbcQuery.getOperatorName().trim().length() > 0) {
				query += " and lower(ct.operatorName) like '%"+recordJdbcQuery.getOperatorName()+"%'";

			}
			if (recordJdbcQuery.getStartstartTime() != null
					&& recordJdbcQuery.getStartstartTime().trim().length() > 0) {
				query += " and StartTime > '"+recordJdbcQuery.getStartstartTime()+"'";

			}
			if (recordJdbcQuery.getEndstartTime() != null
					&& recordJdbcQuery.getEndstartTime().trim().length() > 0) {
				query += " and StartTime < '"+recordJdbcQuery.getEndstartTime()+"'";

			}
			if (recordJdbcQuery.isIstypeone() && recordJdbcQuery.isIstypetwo()) {
				query += " and (type  = 1 or type  = 2)";
			}
			if (recordJdbcQuery.isIstypetwo() && !recordJdbcQuery.isIstypeone()) {
				query += " and type  = 2";
			}
			if (!recordJdbcQuery.isIstypetwo() && recordJdbcQuery.isIstypeone()) {
				query += " and type  = 1";
			}
			if (recordJdbcQuery.isIschecked()) {
				query += " and checktime!='0000-00-00' ";
			}
			// sql =
			// "DELETE from callrecord where recordid in (SELECT recordid from (SELECT recordid FROM callrecord cr left join review r on cr.ticketid=r.ticketid left join calltickets ct on cr.ticketid=ct.sequenceNumber where 1=1 ?) de )";
			sql = "update callrecord set recordcorrect = 1 where recordid in (SELECT recordid from (SELECT recordid FROM callrecord cr left join review r on cr.ticketid=r.ticketid left join calltickets ct on cr.ticketid=ct.sequenceNumber where 1=1 ?) de )";
			sql = sql.replace("?", query);
		}
		log.debug("delete callrecord of sql statmenet:["+sql+"]");
		getJdbcTemplate().update(sql);
	}

	RowMapper rowMapper = new RowMapper() {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

			Callrecord u = new Callrecord();
			u.setRecordid(rs.getInt("Recordid"));
			u.setTicketid(rs.getInt("Ticketid"));
			u.setRecordcorrect(rs.getInt("Recordcorrect"));
			u.setRecordstarttime(rs.getTimestamp("Recordstarttime"));
			u.setRecordstoptime(rs.getTimestamp("Recordstoptime"));

			Time Recordlength = new Time(0, 0, rs.getInt("Recordlength"));
			u.setRecordlength(Recordlength);
			// u.setChecktime(rs.getTimestamp("Checktime"));
			// u.setOperatorname(rs.getString("OperatorName"));
			u.setSavepath(rs.getString("Savepath"));
			u.setSavefilename(rs.getString("Savefilename"));
			// u.setType(rs.getInt("type"));
			// u.setOperatorName(rs.getString("operatorName"));
			// u.setOperatorDesk(rs.getInt("OperatorDesk"));
			// u.setOriginator(rs.getInt("originator"));
			// u.setCallPriority(rs.getInt("Priority"));
			// u.setCallType(rs.getString("Type"));
			return u;
		}
	};

	public List<Callrecord> getList(Callrecord callrecord) {
		try {

			List list = getJdbcTemplate().query(Get_All_ByTicketId, rowMapper,
					callrecord.getTicketid());
			if (list.size() > 0) {
				return list;
			} else {
				return null;
			}
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	@Override
	public List<Callrecord> getMutiCallRecords(final RecordJdbcQuery recordJdbcQuery) {
		try {
			List<Callrecord> list = null;
			String sql = "";

			if (recordJdbcQuery.getMutilid() != null) {
				sql = Get_Muti_Record;
				sql = sql.replace("?", recordJdbcQuery.getMutilid());
				list = getJdbcTemplate().query(sql, rowMapper);
			} else {
				sql = "SELECT cr.* FROM callrecord cr left join review r on cr.ticketid=r.ticketid left join calltickets ct on cr.ticketid=ct.sequenceNumber where 1=1 ";
				if (recordJdbcQuery.getOperatorName() != null
						&& recordJdbcQuery.getOperatorName().trim().length() > 0) {
					sql += " and lower(ct.operatorName) like '%"+recordJdbcQuery.getOperatorName()+"%'";

				}
				if (recordJdbcQuery.getStartstartTime() != null
						&& recordJdbcQuery.getStartstartTime().trim().length() > 0) {
					sql += " and StartTime > '"+recordJdbcQuery.getStartstartTime().trim()+"'";

				}
				if (recordJdbcQuery.getEndstartTime() != null
						&& recordJdbcQuery.getEndstartTime().trim().length() > 0) {
					sql += " and StartTime < '"+recordJdbcQuery.getEndstartTime().trim()+"'";

				}
				if (recordJdbcQuery.isIstypeone()
						&& recordJdbcQuery.isIstypetwo()) {
					sql += " and (type  = 1 or type  = 2)";
				}
				if (recordJdbcQuery.isIstypeone()
						&& !recordJdbcQuery.isIstypeone()) {
					sql += " and type  = 2";
				}
				if (!recordJdbcQuery.isIstypeone()
						&& recordJdbcQuery.isIstypeone()) {
					sql += " and type  = 1";
				}
				if (recordJdbcQuery.isIschecked()) {
					sql += " and checktime!='0000-00-00' ";
				}
				log.debug("look for record of sql :["+sql+"]");
				list = getJdbcTemplate().query(sql, rowMapper);
			}

			if (list.size() > 0) {
				return list;
			} else {
				return null;
			}
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	@Override
	public List<Callrecord> getCallRecordByCallTicketId(Callrecord callrecord) {
		if (callrecord.getMutilid() != null) {

			String sql = Get_All_InTicketid;
			sql = sql.replace("?", callrecord.getMutilid());
			return getJdbcTemplate().query(sql,
					new BeanPropertyRowMapper<Callrecord>(Callrecord.class));
		} else {
			return getJdbcTemplate().query(Get_All_ByTicketid,
					new BeanPropertyRowMapper<Callrecord>(Callrecord.class),
					callrecord.getTicketid());
		}

	}
}
