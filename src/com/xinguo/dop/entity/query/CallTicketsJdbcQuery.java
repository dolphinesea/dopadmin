package com.xinguo.dop.entity.query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.xinguo.dop.dao.JdbcPageInfo;
import com.xinguo.dop.entity.CallTicket;
import com.xinguo.util.page.PageInfo;
import com.xinguo.util.page.PageQuery;

@SuppressWarnings("unchecked")
public class CallTicketsJdbcQuery extends CallTicket implements
		PageQuery<Map, String> {
	
	private String desk;
	
	private String saveFileNameArr;
	
	
	
	public String getSaveFileNameArr() {
		return saveFileNameArr;
	}

	public void setSaveFileNameArr(String saveFileNameArr) {
		this.saveFileNameArr = saveFileNameArr;
	}

	boolean isFirst;
	
	
	
	public boolean isFirst() {
		return isFirst;
	}

	public void setFirst(boolean isFirst) {
		this.isFirst = isFirst;
	}

	public String getDesk() {
		return desk;
	}

	public void setDesk(String desk) {
		this.desk = desk;
	}

	private String startstartTime;

	public String getStartstartTime() {
		return startstartTime;
	}

	public void setStartstartTime(String startstartTime) {
		this.startstartTime = startstartTime;
	}

	private String endstartTime;

	public String getEndstartTime() {
		return endstartTime;
	}

	public void setEndstartTime(String endstartTime) {
		this.endstartTime = endstartTime;
	}

	private boolean istypeone;

	private boolean istypetwo;

	private boolean ischecked;

	public boolean isIstypeone() {
		return istypeone;
	}

	public void setIstypeone(boolean istypeone) {
		this.istypeone = istypeone;
	}

	public boolean isIstypetwo() {
		return istypetwo;
	}

	public void setIstypetwo(boolean istypetwo) {
		this.istypetwo = istypetwo;
	}

	public boolean isIschecked() {
		return ischecked;
	}

	public void setIschecked(boolean ischecked) {
		this.ischecked = ischecked;
	}

	public PageInfo<Map, String> generatePageInfo() {
		JdbcPageInfo pi = new JdbcPageInfo();
		CallTicketsJdbcQuery exampleModel = new CallTicketsJdbcQuery();
		//String sql = "SELECT * ,(SELECT COUNT(cr.recordid) from callrecord cr where ticketid =ct.sequenceNumber) as recordCount FROM calltickets ct left join review r on ct.sequenceNumber=r.ticketid where 1=1";
		String sql = "SELECT * ,(0) as recordCount FROM calltickets ct left join review r on ct.sequenceNumber=r.ticketid where 1=1";
		
		if (istypeone && istypetwo) {
			sql += " and (type  = 1 or type  = 2)";
		}
		if (istypetwo && !istypeone) {
			sql += " and type  = 2";
		}
		if (!istypetwo && istypeone) {
			sql += " and type  = 1";
		}
		if (ischecked) {
			sql += " and checktime!='0000-00-00' ";
		}
		if (super.getOperatorName() != null
				&& super.getOperatorName().trim().length() > 0) {
			sql += " and lower(OperatorName) like :OperatorName";
			exampleModel.setOperatorName("%" + getOperatorName().toLowerCase()
					+ "%");
		}
		if (super.getCallingNumber() != null
				&& super.getCallingNumber().trim().length() > 0) {
			sql += " and lower(CallingNumber) like :CallingNumber";
			exampleModel.setCallingNumber("%"
					+ getCallingNumber().toLowerCase() + "%");
		}
		if (super.getCalledNumber() != null
				&& super.getCalledNumber().trim().length() > 0) {
			sql += " and lower(CalledNumber) like :CalledNumber";
			exampleModel.setCalledNumber("%" + getCalledNumber().toLowerCase()
					+ "%");
		}
		if (getStartstartTime() != null) {
			if (!"".equals(getStartstartTime())) {
				sql += " and StartTime > :StartstartTime";
				exampleModel.setStartstartTime(getStartstartTime());
			}

		}
		if (getEndstartTime() != null) {
			if (!"".equals(getEndstartTime())) {
				sql += " and StartTime < :EndstartTime";
				exampleModel.setEndstartTime(getEndstartTime());
			}
		}

		pi.putSql(sql);
		pi.putCountsql(sql.replace("*", "sequenceNumber")); // 减少查询数据量
		pi.putExampleModel(exampleModel);
		pi.putRowMapper(new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				CallTicket u = new CallTicket();
				u.setSequenceNumber(rs.getLong("SequenceNumber"));
				u.setOperatorName(rs.getString("OperatorName"));
				u.setCalledNumber(rs.getString("calledNumber"));
				u.setCallingNumber(rs.getString("callingNumber"));
				u.setStartTime(rs.getTimestamp("StartTime"));
				u.setStopTime(rs.getTimestamp("StopTime"));
				u.setAnswerTime(rs.getTimestamp("AnswerTime"));
				u.setEstablishTime(rs.getTimestamp("EstablishTime"));
				u.setRecordCount(rs.getInt("recordCount"));
				u.setChecktime(rs.getTimestamp("checktime"));
				u.setType(rs.getInt("type"));
				// u.setOperatorDesk(rs.getInt("OperatorDesk"));
				// u.setOriginator(rs.getInt("originator"));
				// u.setCallPriority(rs.getInt("Priority"));
				// u.setCallType(rs.getString("Type"));
				return u;
			}
		});

		return pi;
	}

}
