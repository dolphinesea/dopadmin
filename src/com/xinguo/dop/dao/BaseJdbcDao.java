package com.xinguo.dop.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.xinguo.util.page.PageResult;

@Repository
public class BaseJdbcDao {
	protected Logger log = Logger.getLogger(getClass());

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				dataSource);
	}

	/**
	 * 通用分页存储
	 * 
	 * */
	@SuppressWarnings("unchecked")
	public PageResult findByPage(JdbcPageInfo pageInfo) {
		//pageInfo.setPageSize(0);// 取出全部不做分页
		int recordsNumber = countRecordsNumberByExample(pageInfo);
		if (pageInfo.getPageSize() <= 0) {// PageSize==0相当于取全部
			List all = findByMysql(pageInfo);
			return new PageResult(all, recordsNumber, 1);// 取全部，所以页数为1
		}
		int pageAmount = (recordsNumber % pageInfo.getPageSize() > 0) ? (recordsNumber
				/ pageInfo.getPageSize() + 1)
				: (recordsNumber / pageInfo.getPageSize());
		int pageNo = pageInfo.getPageNo() > 0 ? pageInfo.getPageNo() : 1;

		if (pageNo > pageAmount) {
			pageNo = pageAmount;
		}

		if (pageInfo.getEnd() != null && pageInfo.getEnd().booleanValue()) {// end=true:首页
			pageNo = 1;
		}
		if (pageInfo.getEnd() != null && !pageInfo.getEnd().booleanValue()) {// end=false:尾页
			pageNo = pageAmount;
		}

		int firstResult = (pageNo - 1) * pageInfo.getPageSize();
		List pageData = findByMysql(pageInfo, firstResult, pageInfo
				.getPageSize());
		if (pageData != null) {
			if (pageData.size() == 0) {
				pageData = null;
			}
		}
		return new PageResult(pageData, recordsNumber, pageAmount);
	}

	@SuppressWarnings("unchecked")
	private List findByExample(JdbcPageInfo pageInfo,
			int... firstResultAndMaxResults) {
		String sql = pageInfo.getSql();
		if (pageInfo.getOrderByList() != null
				&& pageInfo.getOrderByList().size() > 0) {
			sql = sql + " order by";
			for (String exp : pageInfo.getOrderByList()) {
				sql = sql + " " + exp + " ,";
			}
			if (sql.endsWith(",")) {
				sql = sql.substring(0, sql.length() - 1);
			}
		}
		log.debug("=======callrecord=======SQL:" + sql);
		if (firstResultAndMaxResults != null
				&& firstResultAndMaxResults.length == 2) {
			return (List) namedParameterJdbcTemplate.query(sql,
					new BeanPropertySqlParameterSource(pageInfo
							.getExampleModel()),
					new SplitPageResultSetExtractor(pageInfo.getRowMapper(),
							firstResultAndMaxResults[0],
							firstResultAndMaxResults[1]));
		} else {
			return namedParameterJdbcTemplate.query(sql,
					new BeanPropertySqlParameterSource(pageInfo
							.getExampleModel()), pageInfo.getRowMapper());
		}
	}

	@SuppressWarnings("unchecked")
	private List findByMysql(JdbcPageInfo pageInfo,
			int... firstResultAndMaxResults) {
		List list_ = new ArrayList();
		String sql = pageInfo.getSql();
		if (pageInfo.getOrderByList() != null
				&& pageInfo.getOrderByList().size() > 0) {
			sql = sql + " order by";
			for (String exp : pageInfo.getOrderByList()) {
				sql = sql + " " + exp + " ,";
			}
			if (sql.endsWith(",")) {
				sql = sql.substring(0, sql.length() - 1);
			}
		}

		if (firstResultAndMaxResults != null
				&& firstResultAndMaxResults.length == 2) {
			if (firstResultAndMaxResults[0] >= 0) {
				sql = sql + " LIMIT " + firstResultAndMaxResults[0] + ", "
						+ firstResultAndMaxResults[1];
			}
			log.debug("===============>SQL:" + sql);
		    list_ = (List) namedParameterJdbcTemplate.query(sql,
					new BeanPropertySqlParameterSource(pageInfo
							.getExampleModel()), pageInfo.getRowMapper());
		    log.debug("list of count"+list_.size());
			 return list_;
		} else {
			log.debug("===============>SQL:" + sql);
			list_ = namedParameterJdbcTemplate.query(sql,
					new BeanPropertySqlParameterSource(pageInfo
							.getExampleModel()), pageInfo.getRowMapper());
			log.debug("list of count"+list_.size());
			return list_;
		}
	}

	/**
	 * 采用滚动结果集分页算法， 暂未实现拼SQL的物理分页，因为SQL语法与数据库相关 物理分页可参照BaseIbatisDao及其配置
	 */
	class SplitPageResultSetExtractor implements ResultSetExtractor {
		private final int start;// 起始行号
		private final int len;// 结果集合的长度
		private final RowMapper rowMapper;// 行包装器

		public SplitPageResultSetExtractor(RowMapper rowMapper, int start,
				int len) {
			Assert.notNull(rowMapper, "RowMapper is required");
			this.rowMapper = rowMapper;
			this.start = start;
			this.len = len;
		}

		/**
		 * 处理结果集合,被接口自动调用，该类外边不应该调用
		 */
		@SuppressWarnings("unchecked")
		public Object extractData(ResultSet rs) throws SQLException,
				DataAccessException {
			List result = new ArrayList();
			int rowNum = 0;
			int end = start + len;
			while (rs.next()) {
				rowNum++;
				if (rowNum <= start) {
					continue;
				} else if (rowNum > end) {
					break;
				} else {
					result.add(this.rowMapper.mapRow(rs, rowNum));
				}
			}
			return result;
		}
	}

	private int countRecordsNumberByExample(JdbcPageInfo pageInfo) {
		String sql = "select count(0) from (" + pageInfo.getCountsql() + ") c";
		log.debug("===========count SQL:" + sql);
		int count_ = namedParameterJdbcTemplate.queryForInt(sql,
				new BeanPropertySqlParameterSource(pageInfo.getExampleModel()));
		log.debug( "callrecord of count:" + count_);
		return count_;
	}
}
