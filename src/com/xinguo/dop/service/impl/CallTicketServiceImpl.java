package com.xinguo.dop.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xinguo.dop.dao.CallTicketDao;
import com.xinguo.dop.dao.JdbcPageInfo;
import com.xinguo.dop.entity.CallTicket;
import com.xinguo.dop.entity.query.CallTicketsJdbcQuery;
import com.xinguo.dop.service.CallTicketService;
import com.xinguo.util.page.PageResult;

/**
 * @Title:CallTicketServiceImpl
 * @Description: 对CallTicketServiceImpl业务逻辑接口接口实现
 * 
 * @updatetime:2011-10-08
 * @version: 1.0
 * @author <a href="mailto:yangjunjian@xinguo.sh-fortune.com.cn">Yang
 *         JunJian</a>
 */
@Service("callTicketService")
public class CallTicketServiceImpl implements CallTicketService {

	private CallTicketDao callTicketDao;

	public CallTicketDao getCallTicketDAO() {
		return callTicketDao;
	}

	@Resource(name = "callTicketDao")
	public void setCallTicketDAO(CallTicketDao callTicketDao) {
		this.callTicketDao = callTicketDao;
	}

	public List<CallTicket> getAllCallTicket() {
		return (List<CallTicket>) this.callTicketDao.getAllCallTicket();
	}

	public PageResult pageList(JdbcPageInfo pageInfo) {
		return this.callTicketDao.pageList(pageInfo);
	}

	public CallTicket getCallTicketBySeqNum(CallTicket callTicket) {
		return this.callTicketDao.getCallTicketBySeqNum(callTicket);
	}

	public void addReview(CallTicket callTicket) {
		callTicketDao.addReview(callTicket);

	}

	public CallTicket getReviewByTicketid(CallTicket callTicket) {
		return callTicketDao.getReviewByTicketid(callTicket);
	}

	public void updateReview(CallTicket callTicket) {
		callTicketDao.updateReview(callTicket);

	}

	public void deleteReview(CallTicket callTicket) {
		callTicketDao.deleteReview(callTicket);
	}

	@Override
	public List<CallTicket> getCallTicket(
			CallTicketsJdbcQuery callTicketsJdbcQuery) {
		return callTicketDao.getCallTicket(callTicketsJdbcQuery);
	}

	@Override
	public void updateCallTicket(CallTicket callTicket) {
		callTicketDao.updateCallTicket(callTicket);

	}

	@Override
	public List<CallTicket> getCallTicketAjax(
			CallTicketsJdbcQuery callTicketsJdbcQuery) {
		return callTicketDao.getCallTicketAjax(callTicketsJdbcQuery);
	}

	@Override
    public List<CallTicket> getCallTicket(String sequenceNumber) {

		return callTicketDao.getCallTicket(sequenceNumber);
    }
	
	@Override
	public int getCallTicketTotal(CallTicketsJdbcQuery callTicketsJdbcQuery){
		return callTicketDao.getCallTicketTotal(callTicketsJdbcQuery);
	}
}
