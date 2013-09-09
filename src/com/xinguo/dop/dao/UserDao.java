package com.xinguo.dop.dao;

import java.util.List;

import com.xinguo.dop.entity.CallPriority;
import com.xinguo.dop.entity.Operator;
import com.xinguo.dop.entity.Subscriber;
import com.xinguo.dop.entity.query.SubscriberJdbcQuery;
import com.xinguo.util.page.PageResult;

/**
 * @Title:UserDAO
 * @Description:UserDAO数据访问接口
 * 
 * @updatetime:2011-09-20
 * @version: 1.0
 * @author <a href="mailto:yangaoming@xinguo.sh-fortune.com.cn">Yang AoMing</a>
 */
public interface UserDao {

	/*操作员*/
	public int login(Operator operator);

	public int checkOperatorExist(String account);

	public int addOperator(Operator operator);

	public List<Operator> getAllOperator();
	
	public List<Operator> getNoAdminOperator();

	public void editOperator(Operator operator);

	public void delOperator(Operator operator);

	public Operator getOperatorById(Operator operator);

	public PageResult operatorPageList(JdbcPageInfo pageInfo);

	
	/*电话用户*/
	
	public int checkSubscriberExist(String number);

	public void addSubscriber(Subscriber subscriber);
	
	public void delAllSubscriber();

	public List<Subscriber> getAllSubscriber();

	public void editSubscriber(Subscriber subscriber);

	public void delSubscriber(Subscriber subscriber);

	public void updatecoverSubscriberbatch( List<Object[]> list );
	
	public Subscriber getSubscriberByNumber(Subscriber subscriber);

	public PageResult subscriberPageList(JdbcPageInfo pageInfo);

	public List<CallPriority> getCallPriorityList();
	
	public List<Subscriber> subscriberPageList(SubscriberJdbcQuery subscriberJdbcQuery);
}
