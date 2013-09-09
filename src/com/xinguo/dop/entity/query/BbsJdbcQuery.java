package com.xinguo.dop.entity.query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.xinguo.dop.dao.JdbcPageInfo;
import com.xinguo.dop.entity.Bbs;
import com.xinguo.util.page.PageInfo;
import com.xinguo.util.page.PageQuery;

@SuppressWarnings("unchecked")
public class BbsJdbcQuery extends Bbs implements PageQuery<Map, String> {

	SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
	
	
	public PageInfo<Map, String> generatePageInfo() {
		JdbcPageInfo pi = new JdbcPageInfo();
		BbsJdbcQuery exampleModel = new BbsJdbcQuery();
		String sql = "select * from bbs where 1=1 ";
		if (super.getDeletedFlag() != null) {
			sql += " and deletedflag=" + super.getDeletedFlag();
		}
		 
		pi.putSql(sql);
		pi.putCountsql(sql.replace("*", "id")); // 减少查询数据量
		pi.putExampleModel(exampleModel);
		pi.putRowMapper(new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				Bbs u = new Bbs();
				u.setId(rs.getInt("id"));
				u.setType(rs.getInt("type"));
				u.setWriter(rs.getString("writer"));
				u.setCreatedDate(rs.getInt("createdDate"));
				u.setDeletedFlag(rs.getInt("deletedFlag"));
				u.setBody(rs.getString("body"));
				u.setcDate(dateTimeFormat.format(new Timestamp((long) rs.getInt("createdDate") * 1000)));
				return u;
			}
		});
		return pi;
	}
}
