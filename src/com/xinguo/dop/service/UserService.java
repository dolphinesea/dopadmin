package com.xinguo.dop.service;

import java.util.List;

import com.xinguo.dop.dao.JdbcPageInfo;
import com.xinguo.dop.entity.CallPriority;
import com.xinguo.dop.entity.Operator;
import com.xinguo.dop.entity.Subscriber;
import com.xinguo.dop.entity.query.ReportJdbcQuery;
import com.xinguo.dop.entity.query.SubscriberJdbcQuery;
import com.xinguo.util.page.PageResult;

/**
 * @Title:UserService
 * @Description: UserService业务逻辑接口
 * 
 * @updatetime:2011-09-20
 * @version: 1.0
 * @author <a href="mailto:yangaoming@xinguo.sh-fortune.com.cn">Yang AoMing</a>
 */
public interface UserService {

	public int login(Operator user);
	
	public int checkOperatorExist(String account);

	public int addOperator(Operator user);

	public List<Operator> getAllOperator();
	
	public List<Operator> getNoAdminOperator();

	public void editOperator(Operator user);

	public void delOperator(Operator user);

	public Operator getOperatorById(Operator user);

	public PageResult operatorPageList(JdbcPageInfo pageInfo);
	
	
	
	/*电话用户*/
	public int checkSubscriberExist(String number);

	public void addSubscriber(Subscriber subscriber);
	public void delAllSubscriber();
	public List<Subscriber> getAllSubscriber();

	public void editSubscriber(Subscriber subscriber);

	public void delSubscriber(Subscriber subscriber);

	public void updatecoverSubscriberBatch(List <Object[]> list);
	
	public Subscriber getSubscriberByNumber(Subscriber subscriber);

	public PageResult subscriberPageList(JdbcPageInfo pageInfo);
	
	public List<CallPriority> getCallPriorityList();
	
	public List<Subscriber> subscriberPageList(SubscriberJdbcQuery subscriberJdbcQuery);
}
