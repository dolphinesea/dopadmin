package com.xinguo.dop.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.xinguo.dop.dao.ReportDao;
import com.xinguo.dop.entity.Callrecord;
import com.xinguo.dop.entity.RecordDetail;
import com.xinguo.dop.entity.RecordStatistics;
import com.xinguo.dop.entity.query.ReportJdbcQuery;
import com.xinguo.util.common.SystemConfig;
import com.xinguo.util.page.PageInfo;
import com.xinguo.util.page.PageModel;

@Repository("reportDao")
public class ReportDaoImpl implements ReportDao {

	private static final String sqlgetOperatorRecodeDetail = "SELECT ct.operatorName,ct.operatorDesk,starttime as startdate,starttime as starttime,type,answer,numbertype,checknumbertime,checknumber,dealproblem,serviceterms,isbad from calltickets ct,review r where r.ticketid=ct.sequenceNumber ";

	private static final String sqlgetOperatorRecodeStatistics = "SELECT operatorName, count(case when answer=1 then 1 end) as answerTimeout,COUNT(case when checknumber=1 and numbertype=1 then 1 end) as largeTimeoutBase, COUNT(case when checknumber=2 and numbertype=1 then 1 end) as smallTimeoutBase,COUNT(case when numbertype=1 then 1 end) as timeoutBaseCount,COUNT(case when checknumber=1 and numbertype=2 then 1 end) as largeTimeoutNoBase,COUNT(case when checknumber=2 and numbertype=2 then 1 end) as smallTimeoutNoBase,COUNT(case when numbertype=2 then 1 end) as timeoutNoBaseCount,COUNT(case when dealproblem=1 then 1 end) as dealGood,COUNT(case when dealproblem=2 then 1 end) as dealBad,COUNT(case when serviceterms=2  then 1 end) as badWords,COUNT(case when isbad=1 then 1 end) as mistakes,COUNT(operatorName) as dealCount,COUNT(operatorName) as ticketNum ";

	private org.springframework.jdbc.core.simple.SimpleJdbcTemplate simpleJdbcTemplate;

	public org.springframework.jdbc.core.simple.SimpleJdbcTemplate getSimpleJdbcTemplate() {
		return simpleJdbcTemplate;
	}

	@Resource
	public void setSimpleJdbcTemplate(
			org.springframework.jdbc.core.simple.SimpleJdbcTemplate simpleJdbcTemplate) {
		this.simpleJdbcTemplate = simpleJdbcTemplate;
	}

	public List getOperatorRecodeDetail(ReportJdbcQuery reportJdbcQuery) {

		if (reportJdbcQuery.getOperatorName() == null) {
			return null;
		} else if ("".equals(reportJdbcQuery.getOperatorName().trim())) {
			return null;
		} else {
			Date now = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String fdate = dateFormat.format(now);
			if(reportJdbcQuery.getStartTime() == null&&reportJdbcQuery.getEndTime() == null){
				reportJdbcQuery.setStartTime(fdate);
				reportJdbcQuery.setEndTime(fdate);
				
			}
			if(reportJdbcQuery.getStartTime().equals("")&&reportJdbcQuery.getEndTime().equals("")){
				reportJdbcQuery.setStartTime(fdate);
				reportJdbcQuery.setEndTime(fdate);
				
			}
			List rdList = getCallDetailList(reportJdbcQuery,sqlgetOperatorRecodeDetail);
 		return rdList;

		}

	}

	private List getCallDetailList(ReportJdbcQuery reportJdbcQuery, String sql) {
		StringBuilder sbsql = new StringBuilder(sql);
		sbsql.append(" and ct.operatorName in ( ");
		String operatorName = "'"+reportJdbcQuery.getOperatorName().replace(",", "','")+"'"; 
		logger.debug("operatorname :"+operatorName);
		sbsql.append( operatorName + " )");

		if (reportJdbcQuery.getStartTime() != null) {
			if (!"".equals(reportJdbcQuery.getStartTime().trim())) {
				sbsql.append(" and ct.starttime>= ");
				sbsql.append("'" + reportJdbcQuery.getStartTime() + " 00:00:00'");
			}
		}

		if (reportJdbcQuery.getEndTime() != null) {
			if (!"".equals(reportJdbcQuery.getEndTime().trim())) {
				sbsql.append(" and ct.starttime<= ");
				sbsql.append("'" + reportJdbcQuery.getEndTime() + " 23:59:59'");
			}
		}
	
		sbsql.append(" order by ct.starttime,ct.operatorName desc limit 0,"+SystemConfig.maxItme);
		logger.debug(sbsql.toString());
		List rdList = getSimpleJdbcTemplate().query("select *from ("+sbsql.toString()+") T order by T.operatorName ",
				new BeanPropertyRowMapper<RecordDetail>(RecordDetail.class));
		return rdList;
	}

	public List getOperatorRecodeStatistics(ReportJdbcQuery reportJdbcQuery) {
		String groupby = " GROUP BY operatorName";
		StringBuilder sbsql = new StringBuilder(sqlgetOperatorRecodeStatistics);

		// from (SELECT * from callrecord where operatorName='dop' and
		// recordtime> '2006-10-25' and recordtime< '2011-10-25') as cr GROUP BY
		// operatorName;

		sbsql.append(" from (SELECT ct.*,r.* from calltickets ct,review r where r.ticketid=ct.sequenceNumber and ");
		if (reportJdbcQuery.getOperatorName() == null) {
			return null;
		} else if ("".equals(reportJdbcQuery.getOperatorName().trim())) {
			return null;
		} else {
			
			Date now = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String fdate = dateFormat.format(now);
			if(reportJdbcQuery.getStartTime() == null&&reportJdbcQuery.getEndTime() == null){
				reportJdbcQuery.setStartTime(fdate);
				reportJdbcQuery.setEndTime(fdate);
				
			}
			
			if(reportJdbcQuery.getStartTime().equals("")&&reportJdbcQuery.getEndTime().equals("")){
				reportJdbcQuery.setStartTime(fdate);
				reportJdbcQuery.setEndTime(fdate);
				
			}
			String[] operatorNameArr = reportJdbcQuery.getOperatorName().split(
					",");

			if (operatorNameArr.length <= 1) {
				sbsql.append(" operatorName=");
				sbsql.append("'" + reportJdbcQuery.getOperatorName() + "'");
			} else {
				sbsql.append(" operatorName in(");
				String operatorNameArrString = "'"
						+ reportJdbcQuery.getOperatorName().replace(",", "','")
						+ "'";

				sbsql.append(operatorNameArrString + ")");
			}

			if (reportJdbcQuery.getStartTime() != null) {
				if (!"".equals(reportJdbcQuery.getStartTime().trim())) {
					sbsql.append(" and starttime>= ");
					sbsql.append("'" + reportJdbcQuery.getStartTime() + " 00:00:00'");
				}
			}
			if (reportJdbcQuery.getEndTime() != null) {
				if (!"".equals(reportJdbcQuery.getEndTime().trim())) {
					sbsql.append(" and starttime<= ");
					sbsql.append("'" + reportJdbcQuery.getEndTime() + " 23:59:59'");
				}
			}
			sbsql.append(" ) cr ");
			sbsql.append(groupby);
			logger.debug(sbsql.toString());
			return getSimpleJdbcTemplate().query(
					sbsql.toString(),
					new BeanPropertyRowMapper<RecordStatistics>(
							RecordStatistics.class));
		}
	}
	private Logger logger = Logger.getLogger(ReportDaoImpl.class);
}
