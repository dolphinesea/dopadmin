package com.xinguo.dop.entity.query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.xinguo.dop.dao.JdbcPageInfo;
import com.xinguo.dop.entity.Callrecord;
import com.xinguo.util.common.SystemConfig;
import com.xinguo.util.page.PageInfo;
import com.xinguo.util.page.PageQuery;

@SuppressWarnings("unchecked")
public class RecordJdbcQuery extends Callrecord implements
		PageQuery<Map, String> {
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
		//String limit=" limit 0,"+SystemConfig.callTicketsQueryLimit;
		JdbcPageInfo pi = new JdbcPageInfo();
		RecordJdbcQuery exampleModel = new RecordJdbcQuery();
		String dateRange="";
		String sql = "SELECT cr.*,r.type,r.answer,r.numbertype,r.checknumber,r.checknumbertime,r.checktime,ct.operatorName FROM (SELECT * FROM callrecord {dateRange} LIMIT 0,"+SystemConfig.callTicketsQueryLimit+") cr left join review r on cr.ticketid=r.ticketid left join calltickets ct on cr.ticketid=ct.sequenceNumber where 1=1 and recordcorrect = 0";
		if (super.getOperatorName() != null
				&& super.getOperatorName().trim().length() > 0) {
			sql += " and lower(ct.operatorName) like :operatorName";
			exampleModel.setOperatorName("%" + getOperatorName().toLowerCase()
					+ "%");
		}
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:ss");
		String fdate = dateFormat.format(now);
		if (getStartstartTime()	== null&&getEndstartTime()	== null)
		{
			this.setStartstartTime(fdate);
			this.setEndstartTime(fdate);
		}
		if (getStartstartTime() != null
				&& getStartstartTime().trim().length() > 0) {
			dateRange += " and recordstarttime >=:StartstartTime";
			exampleModel.setStartstartTime(getStartstartTime()+"");

		}
		if (getEndstartTime() != null && getEndstartTime().trim().length() > 0) {
			dateRange += " and recordstarttime <= :EndstartTime";
			exampleModel.setEndstartTime(getEndstartTime()+"");
		}
		if(!dateRange.equals("")){
			dateRange=" where 1=1 "+dateRange;
		}
		sql=sql.replace("{dateRange}", dateRange);
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
		//sql+=limit;
		pi.putSql(sql);
		pi.putCountsql(sql); // 减少查询数据量
		pi.putExampleModel(exampleModel);
		pi.putRowMapper(new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				Callrecord u = new Callrecord();
				u.setRecordid(rs.getInt("Recordid"));
				u.setTicketid(rs.getInt("Ticketid"));
				u.setRecordcorrect(rs.getInt("Recordcorrect"));
				u.setRecordstarttime(rs.getTimestamp("Recordstarttime"));
				u.setRecordstoptime(rs.getTimestamp("Recordstoptime"));
				
				
				Time Recordlength = new Time(0,0,rs.getInt("Recordlength"));
				u.setRecordlength(Recordlength);
				// u.setChecktime(rs.getTimestamp("Checktime"));
				// u.setOperatorname(rs.getString("OperatorName"));
				u.setSavepath(rs.getString("Savepath"));
				u.setSavefilename(rs.getString("Savefilename"));
				u.setType(rs.getInt("type"));
				u.setOperatorName(rs.getString("operatorName"));
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
