package com.xinguo.dop.dao.impl;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.xinguo.dop.dao.BaseJdbcDao;
import com.xinguo.dop.dao.CallTicketDao;
import com.xinguo.dop.entity.CallTicket;
import com.xinguo.dop.entity.query.CallTicketsJdbcQuery;
import com.xinguo.util.common.BeanToMapUtil;
import com.xinguo.util.common.SystemConfig;

/**
 * @Title:CallTicketDAOImpl
 * @Description:CallTicketDAOImpl数据访问接口实现
 * 
 * @updatetime:2011-10-08
 * @version: 1.0
 * @author <a href="mailto:yangjunjian@xinguo.sh-fortune.com.cn">Yang
 *         JunJian</a>
 */
@Repository("callTicketDao")
public class CallTicketDaoImpl extends BaseJdbcDao implements CallTicketDao {

	private static final String GET = "select * from calltickets where sequenceNumber=?";

	private static String getAll = "SELECT * ,(SELECT COUNT(cr.recordid) from callrecord cr where ticketid =ct.sequenceNumber and recordlength > 0 and recordcorrect = 0) as recordCount FROM calltickets ct left join review r on ct.sequenceNumber=r.ticketid where 1=1";

	private static String getCount = "select sequenceNumber from calltickets where 1=1";

	private static final String SELECTREVIEW = "select * from review where ticketid=?";

	private static final String UPDATEREVIEW = "update REVIEW set type=:type,answer=:answer,numbertype=:numbertype,checknumber=:checknumber,checknumbertime=:checknumbertime,dealproblem=:dealproblem,serviceterms=:serviceterms,isbad=:isbad,checktime=:checktime,reviewer=:reviewer where ticketid=:ticketid";

	private static final String INSERTREVIEW = "INSERT INTO review VALUES (:ticketid,:type,:answer,:numbertype,:checknumber,:checknumbertime,:dealproblem,:isbad,:serviceterms,:checktime,:reviewer);";
	
	private static final String DELETEREVIEW = "DELETE from review where ticketid=:ticketid";

	private static final String getTickets = "SELECT *,CONCAT(operatorName,',',answer,',',checknumbertime,',',checknumber,',',numbertype,',',dealproblem,',',serviceterms,',',isbad) as result from (SELECT ct.*,(TIMEDIFF(answerTime,startTime)) as answerLength,group_concat(cr.savefilename separator ',') savefilename from calltickets ct,callrecord cr where cr.ticketid=ct.sequenceNumber {where1} GROUP BY ct.sequenceNumber order by ct.starttime  ) as a left join review r on a.sequenceNumber=r.ticketid  where 1=1 {where2}  LIMIT 0,{max}";
	
	private static final String getTicketsAjax = "SELECT *,CONCAT(operatorName,',',type,',',answer,',',checknumbertime,',',checknumber,',',numbertype,',',dealproblem,',',serviceterms,',',isbad) as result, (TIMEDIFF(answerTime,startTime)) as answerLength from calltickets ct left join review r on r.ticketid=ct.sequenceNumber where 1=1 {where1} ORDER BY startTime LIMIT 0,{max}";
	
	private static final String getTicketsCount = "SELECT COUNT(*) from calltickets ct left join review r on r.ticketid=ct.sequenceNumber where 1=1 {where1}";
	
	private static final String UPDATETickets ="update calltickets set operatorName=:operatorName where sequenceNumber=:sequenceNumber";
	//private static final String getTickets = "SELECT * from (SELECT ct.*,group_concat(cr.savefilename separator ',') savefilename from calltickets ct,callrecord cr where cr.ticketid=ct.sequenceNumber {where1} GROUP BY ct.sequenceNumber LIMIT 0,100 order by ct.starttime desc) as a left join review r on a.sequenceNumber=r.ticketid ";
	private org.springframework.jdbc.core.simple.SimpleJdbcTemplate simpleJdbcTemplate;

	public org.springframework.jdbc.core.simple.SimpleJdbcTemplate getSimpleJdbcTemplate() {
		return simpleJdbcTemplate;
	}

	@Resource
	public void setSimpleJdbcTemplate(
			org.springframework.jdbc.core.simple.SimpleJdbcTemplate simpleJdbcTemplate) {
		this.simpleJdbcTemplate = simpleJdbcTemplate;
	}

	public List<CallTicket> getAllCallTicket() {
		return getSimpleJdbcTemplate().query(getAll,
				new BeanPropertyRowMapper<CallTicket>(CallTicket.class));
	}

	public CallTicket getCallTicketBySeqNum(CallTicket callTicket) {
		try {
			return getSimpleJdbcTemplate().queryForObject(GET,
					new BeanPropertyRowMapper<CallTicket>(CallTicket.class),
					callTicket.getSequenceNumber());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public com.xinguo.util.page.PageResult pageList(
			com.xinguo.dop.dao.JdbcPageInfo pageInfo) {
		return findByPage(pageInfo);
	}

	public void addReview(CallTicket callTicket) {
		Map<String, Object> map = BeanToMapUtil.beanToMap(callTicket);
		
		getSimpleJdbcTemplate().update(INSERTREVIEW, map);
	}

	public CallTicket getReviewByTicketid(CallTicket callTicket) {
		try {
			return getSimpleJdbcTemplate().queryForObject(SELECTREVIEW,
					new BeanPropertyRowMapper<CallTicket>(CallTicket.class),
					callTicket.getSequenceNumber());
		} catch (Exception e) {
			return null;
		}

	}

	public void updateReview(CallTicket callTicket) {
		Map<String, Object> map = BeanToMapUtil.beanToMap(callTicket);
		getSimpleJdbcTemplate().update(UPDATEREVIEW, map);

	}
	
	public void deleteReview(CallTicket callTicket){
		Map<String, Object> map = BeanToMapUtil.beanToMap(callTicket);
		getSimpleJdbcTemplate().update(DELETEREVIEW, map);

	}
	

	@Override
	public List<CallTicket> getCallTicket(
			CallTicketsJdbcQuery callTicketsJdbcQuery) {

		
		String sql=getTickets;
		String where1="";
		String where2="";
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:ss");
		String fdate = dateFormat.format(now);
		if(callTicketsJdbcQuery.getStartstartTime() == null&&callTicketsJdbcQuery.getEndstartTime() == null){
			callTicketsJdbcQuery.setStartstartTime(fdate);
			callTicketsJdbcQuery.setEndstartTime(fdate);
			return null;
		}
		if ("".equals(callTicketsJdbcQuery.getEndstartTime())&&"".equals(callTicketsJdbcQuery.getStartstartTime())) {
			
			callTicketsJdbcQuery.setStartstartTime(fdate);
			callTicketsJdbcQuery.setEndstartTime(fdate);

		}

		
		if (callTicketsJdbcQuery.isIstypeone() && callTicketsJdbcQuery.isIstypetwo()) {
			
			
			where2 += " and (type  = 1 or type  = 2)";
		}
		if (callTicketsJdbcQuery.isIstypetwo() && !callTicketsJdbcQuery.isIstypeone()) {
			where2 += " and type  = 2";
		}
		if (!callTicketsJdbcQuery.isIstypetwo() && callTicketsJdbcQuery.isIstypeone()) {
			where2 += " and type  = 1";
		}
		if (callTicketsJdbcQuery.isIschecked()) {
			where2 += " and checktime!='0000-00-00' ";
		}
		if (callTicketsJdbcQuery.getOperatorName() != null
				&& callTicketsJdbcQuery.getOperatorName().trim().length() > 0) {
			where1 += " and lower(OperatorName) like  '%"+callTicketsJdbcQuery.getOperatorName()+"%'";
			
		}
		if (callTicketsJdbcQuery.getDesk()!= null
				&& callTicketsJdbcQuery.getDesk().trim().length() > 0) {
			where1 += " and lower(OperatorDesk) =  "+callTicketsJdbcQuery.getDesk();
			
		}
		if (callTicketsJdbcQuery.getCallingNumber() != null
				&& callTicketsJdbcQuery.getCallingNumber().trim().length() > 0) {
			where1 += " and lower(CallingNumber) like '%"+callTicketsJdbcQuery.getCallingNumber()+"%'";
			
		}
		if (callTicketsJdbcQuery.getCalledNumber() != null
				&& callTicketsJdbcQuery.getCalledNumber().trim().length() > 0) {
			where1 += " and lower(CalledNumber) like '%"+callTicketsJdbcQuery.getCalledNumber()+"%'";
			
		}
		if (callTicketsJdbcQuery.getStartstartTime() != null) {
			if (!"".equals(callTicketsJdbcQuery.getStartstartTime())) {
				where1 += " and StartTime >= '"+callTicketsJdbcQuery.getStartstartTime()+"'";
				
			}

		}
		if (callTicketsJdbcQuery.getEndstartTime() != null) {
			if (!"".equals(callTicketsJdbcQuery.getEndstartTime())) {
				where1 += " and StartTime <= '"+callTicketsJdbcQuery.getEndstartTime()+"'";
				
			}
		}
		sql=sql.replace("{where1}", where1);
		sql=sql.replace("{where2}", where2);
		sql=sql.replace("{max}", SystemConfig.callTicketsQueryLimit.toString());
		System.out.println(sql);
		return getSimpleJdbcTemplate().query(sql,
				new BeanPropertyRowMapper<CallTicket>(CallTicket.class));
	}

	@Override
	public void updateCallTicket(CallTicket callTicket) {
		Map<String, Object> map = BeanToMapUtil.beanToMap(callTicket);
		getSimpleJdbcTemplate().update(UPDATETickets, map);
		
	}

	@Override
	public List<CallTicket> getCallTicketAjax(
			CallTicketsJdbcQuery callTicketsJdbcQuery) {
		
		
//		List<CallTicket> dsCallTickets=getSimpleJdbcTemplate().query("select * from calltickets",
//				new BeanPropertyRowMapper<CallTicket>(CallTicket.class));
//		
//		
//		for (int i = 0; i < dsCallTickets.size(); i++) {
//			String aaString=String.valueOf(dsCallTickets.get(i).getSequenceNumber()) ;
//			dsCallTickets.get(i).setTicketid(Integer.valueOf(aaString));
//			dsCallTickets.get(i).setAnswer(1);
//			dsCallTickets.get(i).setType(1);
//			dsCallTickets.get(i).setAnswerLength("10");
//			dsCallTickets.get(i).setChecktime(new Timestamp(12321));
//			dsCallTickets.get(i).setChecknumber(1);
//			dsCallTickets.get(i).setChecknumbertime(new Time(11));
//			dsCallTickets.get(i).setNumbertype(11);
//			dsCallTickets.get(i).setDealproblem(11);
//			dsCallTickets.get(i).setIsbad(111);
//			dsCallTickets.get(i).setServiceterms(12);
//			dsCallTickets.get(i).setReviewer("dsads");
//			//:ticketid,:type,:answer,:numbertype,:checknumber,:checknumbertime,:dealproblem,:isbad,:serviceterms,:checktime,:reviewer
//			try {
//				addReview(dsCallTickets.get(i));
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			
//		}
		
		
		String sql=getTicketsAjax;
		String where1="";
		Date yesterday = new Date(System.currentTimeMillis() - 24*60*60*1000);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:ss");
		SimpleDateFormat yesterdayformat = new SimpleDateFormat("yyyy-MM-dd 00:00");
		String fdate = dateFormat.format(new Date());
		if(callTicketsJdbcQuery.getStartstartTime() == null&&callTicketsJdbcQuery.getEndstartTime() == null){
			callTicketsJdbcQuery.setStartstartTime(yesterdayformat.format(yesterday));
			callTicketsJdbcQuery.setEndstartTime(fdate);
			return null;
		}
		if ("".equals(callTicketsJdbcQuery.getEndstartTime())&&"".equals(callTicketsJdbcQuery.getStartstartTime())) {
			
			callTicketsJdbcQuery.setStartstartTime(fdate);
			callTicketsJdbcQuery.setEndstartTime(fdate);

		}

		
		if (callTicketsJdbcQuery.isIstypeone() && callTicketsJdbcQuery.isIstypetwo()) {
			
			
			where1 += " and (type  = 1 or type  = 2)";
		}
		if (callTicketsJdbcQuery.isIstypetwo() && !callTicketsJdbcQuery.isIstypeone()) {
			where1 += " and type  = 2";
		}
		if (!callTicketsJdbcQuery.isIstypetwo() && callTicketsJdbcQuery.isIstypeone()) {
			where1 += " and type  = 1";
		}
		if (callTicketsJdbcQuery.isIschecked()) {
			where1 += " and checktime!='0000-00-00' ";
		}
		if (callTicketsJdbcQuery.getOperatorName() != null
				&& callTicketsJdbcQuery.getOperatorName().trim().length() > 0) {
			where1 += " and lower(OperatorName) like  '%"+callTicketsJdbcQuery.getOperatorName()+"%'";
			
		}
		if (callTicketsJdbcQuery.getDesk()!= null
				&& callTicketsJdbcQuery.getDesk().trim().length() > 0) {
			where1 += " and lower(OperatorDesk) =  "+callTicketsJdbcQuery.getDesk();
			
		}
		if (callTicketsJdbcQuery.getCallingNumber() != null
				&& callTicketsJdbcQuery.getCallingNumber().trim().length() > 0) {
			where1 += " and lower(CallingNumber) like '%"+callTicketsJdbcQuery.getCallingNumber()+"%'";
			
		}
		if (callTicketsJdbcQuery.getCalledNumber() != null
				&& callTicketsJdbcQuery.getCalledNumber().trim().length() > 0) {
			where1 += " and lower(CalledNumber) like '%"+callTicketsJdbcQuery.getCalledNumber()+"%'";
			
		}
		if (callTicketsJdbcQuery.getStartstartTime() != null) {
			if (!"".equals(callTicketsJdbcQuery.getStartstartTime())) {
				where1 += " and StartTime >= '"+callTicketsJdbcQuery.getStartstartTime()+"'";
				
			}

		}
		if (callTicketsJdbcQuery.getEndstartTime() != null) {
			if (!"".equals(callTicketsJdbcQuery.getEndstartTime())) {
				where1 += " and StartTime <= '"+callTicketsJdbcQuery.getEndstartTime()+"'";
				
			}
		}
		sql=sql.replace("{where1}", where1);
		sql=sql.replace("{max}", SystemConfig.callTicketsQueryLimit.toString());
		logger.debug("getCallTicketAjax of sql :'"+sql+"'");
		return getSimpleJdbcTemplate().query(sql,
				new BeanPropertyRowMapper<CallTicket>(CallTicket.class));
	}
	private Logger logger = Logger.getLogger(CallTicketDaoImpl.class);

	@Override
    public List<CallTicket> getCallTicket(String sequenceNumber) {
		
		String sql=" select calltickets.*, callrecord.*, CONCAT(operatorName,',',type,',',answer,',',checknumbertime,',',checknumber,',',numbertype,',',dealproblem,',',serviceterms,',',isbad) as result from calltickets left join review on calltickets.sequenceNumber = review.ticketid right join callrecord on calltickets.sequenceNumber = callrecord.ticketid where calltickets.sequenceNumber in ("+sequenceNumber+") order  by calltickets.sequenceNumber, startTime asc" ;
		logger.debug(sql);
		return getSimpleJdbcTemplate().query(sql,
				new BeanPropertyRowMapper<CallTicket>(CallTicket.class));
    }
	
	@Override
	public int getCallTicketTotal(CallTicketsJdbcQuery callTicketsJdbcQuery){
		String sql = getTicketsCount;
		String where1="";
		Date yesterday = new Date(System.currentTimeMillis() - 24*60*60*1000);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:ss");
		SimpleDateFormat yesterdayformat = new SimpleDateFormat("yyyy-MM-dd 00:00");
		String fdate = dateFormat.format(new Date());
		
		if(callTicketsJdbcQuery.getStartstartTime() == null&&callTicketsJdbcQuery.getEndstartTime() == null){
			callTicketsJdbcQuery.setStartstartTime(yesterdayformat.format(yesterday));
			callTicketsJdbcQuery.setEndstartTime(fdate);
		}
		if ("".equals(callTicketsJdbcQuery.getEndstartTime())&&"".equals(callTicketsJdbcQuery.getStartstartTime())) {
			
			callTicketsJdbcQuery.setStartstartTime(fdate);
			callTicketsJdbcQuery.setEndstartTime(fdate);

		}
		
		if (callTicketsJdbcQuery.isIstypeone() && callTicketsJdbcQuery.isIstypetwo()) {		
			where1 += " and (type  = 1 or type  = 2)";
		}
		if (callTicketsJdbcQuery.isIstypetwo() && !callTicketsJdbcQuery.isIstypeone()) {
			where1 += " and type  = 2";
		}
		if (!callTicketsJdbcQuery.isIstypetwo() && callTicketsJdbcQuery.isIstypeone()) {
			where1 += " and type  = 1";
		}
		if (callTicketsJdbcQuery.isIschecked()) {
			where1 += " and checktime!='0000-00-00' ";
		}
		if (callTicketsJdbcQuery.getOperatorName() != null
				&& callTicketsJdbcQuery.getOperatorName().trim().length() > 0) {
			where1 += " and lower(OperatorName) like  '%"+callTicketsJdbcQuery.getOperatorName()+"%'";
			
		}
		if (callTicketsJdbcQuery.getDesk()!= null
				&& callTicketsJdbcQuery.getDesk().trim().length() > 0) {
			where1 += " and lower(OperatorDesk) =  "+callTicketsJdbcQuery.getDesk();
			
		}
		if (callTicketsJdbcQuery.getCallingNumber() != null
				&& callTicketsJdbcQuery.getCallingNumber().trim().length() > 0) {
			where1 += " and lower(CallingNumber) like '%"+callTicketsJdbcQuery.getCallingNumber()+"%'";
			
		}
		if (callTicketsJdbcQuery.getCalledNumber() != null
				&& callTicketsJdbcQuery.getCalledNumber().trim().length() > 0) {
			where1 += " and lower(CalledNumber) like '%"+callTicketsJdbcQuery.getCalledNumber()+"%'";
			
		}
		if (callTicketsJdbcQuery.getStartstartTime() != null) {
			if (!"".equals(callTicketsJdbcQuery.getStartstartTime())) {
				where1 += " and StartTime >= '"+callTicketsJdbcQuery.getStartstartTime()+"'";
				
			}

		}
		if (callTicketsJdbcQuery.getEndstartTime() != null) {
			if (!"".equals(callTicketsJdbcQuery.getEndstartTime())) {
				where1 += " and StartTime <= '"+callTicketsJdbcQuery.getEndstartTime()+"'";
				
			}
		}
		
		sql=sql.replace("{where1}", where1);
		logger.debug("getCallTicketTotal of sql :'"+sql+"'");
		return getSimpleJdbcTemplate().queryForInt(sql);
	}
}
