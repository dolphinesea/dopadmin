package com.xinguo.dop.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xinguo.dop.dao.JdbcPageInfo;
import com.xinguo.dop.dao.UserDao;
import com.xinguo.dop.entity.CallPriority;
import com.xinguo.dop.entity.Operator;
import com.xinguo.dop.entity.Subscriber;
import com.xinguo.dop.entity.query.SubscriberJdbcQuery;
import com.xinguo.dop.service.UserService;
import com.xinguo.util.page.PageResult;

/**
 * @Title:UserServiceImpl
 * @Description: 对UserService业务逻辑接口接口实现
 * 
 * @updatetime:2011-09-20
 * @version: 1.0
 * @author <a href="mailto:yangaoming@xinguo.sh-fortune.com.cn">Yang AoMing</a>
 */
@Service("userService")
public class UserServiceImpl implements UserService {

	private UserDao userDao;

	public UserDao getUserDao() {
		return userDao;
	}

	@Resource(name = "userDao")
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public int addOperator(Operator operator) {
		return this.userDao.addOperator(operator);
	}

	public int login(Operator operator) {
		return this.userDao.login(operator);
	}

	public int checkOperatorExist(String account) {
		return this.userDao.checkOperatorExist(account);
	}

	public void delOperator(Operator operator) {
		this.userDao.delOperator(operator);
	}

	public Operator getOperatorById(Operator operator) {
		return (Operator) this.userDao.getOperatorById(operator);
	}

	public List<Operator> getOperatorList() {
		return (List<Operator>) this.userDao.getAllOperator();
	}

	public void editOperator(Operator operator) {
		this.userDao.editOperator(operator);
	}

	public List<Operator> getAllOperator() {
		return this.userDao.getAllOperator();
	}
	
	public List<Operator> getNoAdminOperator() {
		return this.userDao.getNoAdminOperator();
	}

	public PageResult operatorPageList(JdbcPageInfo pageInfo) {
		return this.userDao.operatorPageList(pageInfo);
	}

	public void addSubscriber(Subscriber subscriber) {
		this.userDao.addSubscriber(subscriber);

	}

	public int checkSubscriberExist(String number) {
		return this.userDao.checkSubscriberExist(number);
	}

	public void delSubscriber(Subscriber subscriber) {
		this.userDao.delSubscriber(subscriber);

	}
	/**
	 * batch delete subscriber.
	 */
	@Override
	public void updatecoverSubscriberBatch(List <Object[]> list){
		userDao.updatecoverSubscriberbatch(list);
	
	}
	public void editSubscriber(Subscriber subscriber) {
		this.userDao.editSubscriber(subscriber);

	}

	public List<Subscriber> getAllSubscriber() {
		return this.userDao.getAllSubscriber();
	}

	public Subscriber getSubscriberByNumber(Subscriber subscriber) {
		return this.userDao.getSubscriberByNumber(subscriber);
	}

	public PageResult subscriberPageList(JdbcPageInfo pageInfo) {
		return this.userDao.subscriberPageList(pageInfo);
	}

	public List<CallPriority> getCallPriorityList() {
		return this.userDao.getCallPriorityList();
	}

	public List<Subscriber> subscriberPageList(SubscriberJdbcQuery subscriberJdbcQuery)
	{
		return this.userDao.subscriberPageList(subscriberJdbcQuery);

	}

	@Override
    public void delAllSubscriber() {
		this.userDao.delAllSubscriber();
	    
    }
	
}
