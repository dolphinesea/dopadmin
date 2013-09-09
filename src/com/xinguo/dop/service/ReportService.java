package com.xinguo.dop.service;

import java.util.List;

import com.xinguo.dop.entity.query.ReportJdbcQuery;

public interface ReportService {

	public List getOperatorRecodeDetail(ReportJdbcQuery reportJdbcQuery);

	public List getOperatorRecodeStatistics(ReportJdbcQuery reportJdbcQuery);

}
