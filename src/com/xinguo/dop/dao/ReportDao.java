package com.xinguo.dop.dao;

import java.util.List;

import com.xinguo.dop.entity.query.ReportJdbcQuery;

public interface ReportDao {

	public List getOperatorRecodeDetail(ReportJdbcQuery reportJdbcQuery);

	public List getOperatorRecodeStatistics(ReportJdbcQuery reportJdbcQuery);
}
