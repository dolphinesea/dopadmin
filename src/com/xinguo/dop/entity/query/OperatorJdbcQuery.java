package com.xinguo.dop.entity.query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.xinguo.dop.dao.JdbcPageInfo;
import com.xinguo.dop.entity.CallTicket;
import com.xinguo.dop.entity.Callrecord;
import com.xinguo.dop.entity.Operator;
import com.xinguo.util.page.PageInfo;
import com.xinguo.util.page.PageQuery;

@SuppressWarnings("unchecked")
public class OperatorJdbcQuery extends Operator implements PageQuery<Map, String> {

	public PageInfo<Map, String> generatePageInfo() {
		JdbcPageInfo pi = new JdbcPageInfo();
		OperatorJdbcQuery exampleModel = new OperatorJdbcQuery();
		String sql = "select * from operator where 1=1 ";
		if (super.getAccount() != null
				&& super.getAccount().trim().length() > 0) {
			sql += " and lower(account) like :account";
			exampleModel.setAccount("%" + getAccount().toLowerCase() + "%");
		}
		if (super.getName() != null && super.getName().trim().length() > 0) {
			sql += " and lower(name) like :name";
			exampleModel.setName("%" + getName().toLowerCase() + "%");
		}
		pi.putSql(sql);
		pi.putCountsql(sql.replace("*", "id")); // 减少查询数据量
		pi.putExampleModel(exampleModel);
		pi.putRowMapper(new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				Operator u = new Operator();
				u.setId(rs.getInt("id"));
				u.setAccount(rs.getString("account"));
				u.setName(rs.getString("name"));
				u.setPassword(rs.getString("password"));
				u.setLevel(rs.getInt("level"));
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
