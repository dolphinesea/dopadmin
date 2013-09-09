package com.xinguo.dop.service;

import java.util.List;

import com.xinguo.dop.dao.JdbcPageInfo;
import com.xinguo.dop.entity.CallTicket;
import com.xinguo.dop.entity.query.CallTicketsJdbcQuery;
import com.xinguo.util.page.PageResult;

/**
 * @Title:CallTicketService
 * @Description: CallTicketService业务逻辑接口
 * 
 * @updatetime:2011-10-08
 * @version: 1.0
 * @author <a href="mailto:yangjunjian@xinguo.sh-fortune.com.cn">Yang JunJian</a>
 */
public interface CallTicketService {
	public List<CallTicket> getAllCallTicket();
	
	public List<CallTicket> getCallTicket(CallTicketsJdbcQuery callTicketsJdbcQuery);
	public List<CallTicket> getCallTicket(String sequenceNumber);
	
	public List<CallTicket> getCallTicketAjax(CallTicketsJdbcQuery callTicketsJdbcQuery);
	
	public CallTicket getCallTicketBySeqNum(CallTicket callTicket);
	
	public PageResult pageList(JdbcPageInfo pageInfo);
	
	public CallTicket getReviewByTicketid(CallTicket callTicket);

	public void addReview(CallTicket callTicket);

	public void updateReview(CallTicket callTicket);
	
	public void deleteReview(CallTicket callTicket);
	
	public void updateCallTicket(CallTicket callTicket);
	
	public int getCallTicketTotal(CallTicketsJdbcQuery callTicketsJdbcQuery);
}
