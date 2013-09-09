package com.xinguo.dop.entity.query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.xinguo.dop.dao.JdbcPageInfo;
import com.xinguo.dop.entity.CallTicket;
import com.xinguo.dop.entity.Callrecord;
import com.xinguo.dop.entity.Operator;
import com.xinguo.dop.entity.Subscriber;
import com.xinguo.util.page.PageInfo;
import com.xinguo.util.page.PageQuery;

@SuppressWarnings("unchecked")
public class SubscriberJdbcQuery extends Subscriber implements
		PageQuery<Map, String> {

	 String p_orderBy="";
	 String p_orderDir="";
	 
	 
	
	public String getP_orderBy() {
		return p_orderBy;
	}



	public void setP_orderBy(String pOrderBy) {
		p_orderBy = pOrderBy;
	}



	public String getP_orderDir() {
		return p_orderDir;
	}



	public void setP_orderDir(String pOrderDir) {
		p_orderDir = pOrderDir;
	}



	public PageInfo<Map, String> generatePageInfo() {
		JdbcPageInfo pi = new JdbcPageInfo();
		SubscriberJdbcQuery exampleModel = new SubscriberJdbcQuery();
		String sql = "select s.*,cp.name as call_priority_name from Subscriber s,call_priority cp where s.call_priority=cp.priority";
		if (super.getNumber() != null && super.getNumber().trim().length() > 0) {
			sql += " and lower(number) like :number";
			exampleModel.setNumber("%" + getNumber().toLowerCase() + "%");
		}

		if (super.getCall_priority() != null && super.getCall_priority() > 0) {
			sql += " and lower(call_priority) = :call_priority";
			exampleModel.setCall_priority(getCall_priority());
		}

		if (super.getDescription() != null
				&& super.getDescription().trim().length() > 0) {
			sql += " and lower(s.description) like :description";
			exampleModel.setDescription("%" + getDescription().toLowerCase()
					+ "%");
		}
		if (super.getName() != null && super.getName().trim().length() > 0) {
			String[] nameArr = super.getName().split(" ");
			if (nameArr.length <= 1) {
				nameArr = super.getName().split(",");
			}
			if (nameArr.length <= 1) {
				sql += " and lower(s.name) like :name";
				exampleModel.setName("%" + getName().toLowerCase().trim() + "%");
			} else {
				String operatorNameArrString="";
				for (int i = 0; i < nameArr.length; i++) {
					if (!nameArr[i].equals("")) {
						operatorNameArrString+="'"+nameArr[i]+"'";
						if (i!=nameArr.length-1) {
							operatorNameArrString+=",";
						}
					}
					
				}
				sql += " and lower(s.name)  in("+operatorNameArrString+")";
			}
			// if (super.getName() != null && super.getName().trim().length() >
			// 0) {
			// sql += " and lower(s.name) like :name";
			// exampleModel.setName("%" + getName().toLowerCase() + "%");
		}

		if (super.getDnd() != null) {
			sql += " and s.dnd = " + getDnd();

		}

		pi.putSql(sql);
		pi.putCountsql(sql.replace("*", "number")); // 减少查询数据量
		pi.putExampleModel(exampleModel);
		pi.putRowMapper(new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				Subscriber u = new Subscriber();
				u.setNumber(rs.getString("number"));
				u.setName(rs.getString("name"));
				u.setDnd(rs.getBoolean("dnd"));
				u.setCall_priority(rs.getInt("call_priority"));
				u.setDescription(rs.getString("description"));
				u.setCall_priority_name(rs.getString("call_priority_name"));
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
