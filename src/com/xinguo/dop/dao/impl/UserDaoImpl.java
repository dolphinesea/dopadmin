package com.xinguo.dop.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.mysql.jdbc.Connection;
import com.xinguo.dop.dao.BaseJdbcDao;
import com.xinguo.dop.dao.JdbcPageInfo;
import com.xinguo.dop.dao.UserDao;
import com.xinguo.dop.entity.CallPriority;
import com.xinguo.dop.entity.Operator;
import com.xinguo.dop.entity.Subscriber;
import com.xinguo.dop.entity.query.SubscriberJdbcQuery;
import com.xinguo.util.common.BeanToMapUtil;
import com.xinguo.util.page.PageResult;

/**
 * @Title:UserDAOImpl
 * @Description:UserDAOImpl数据访问接口实现
 * 
 * @updatetime:2011-09-20
 * @version: 1.0
 * @author <a href="mailto:yangaoming@xinguo.sh-fortune.com.cn">Yang AoMing</a>
 */
@Repository("userDao")
public class UserDaoImpl extends BaseJdbcDao implements UserDao {

	private static final String Insert_Operator = "insert into operator(id,account,password,name,level)VALUES(:id,:account,:password,:name,:level)";
	private static final String Update_Operator = "update operator set account=:account,password=:password,name=:name,level=:level where id=:id";
	private static final String Get_Operator = "select * from operator where id=?";
	private static final String LOGIN = "select id from operator where account=? and password=?";
	private static final String Check_Operator = "select count(1) from operator where account=?";
	private static final String Select_All_Operator = "select * from operator";
	private static final String Select_No_Admin_Operator = "SELECT * FROM operator WHERE level <> 3";
	private static final String Del_Operator = "delete from operator where id=?";
	private static final String Del_Subscriber_All = "delete from subscriber";
	private static final String Insert_Subscriber = "insert into Subscriber(number,call_priority,description,name,dnd,linetype)VALUES(:number,:call_priority,:description,:name,:dnd,:linetype)";
	private static final String Update_Subscriber = "update Subscriber set call_priority=:call_priority,description=:description,name=:name,dnd=:dnd,linetype=:linetype where number=:number";
	private static final String Get_Subscriber = "select * from Subscriber where number=?";

	private static final String Check_Subscriber = "select count(1) from Subscriber where number=?";
	private static final String Select_All_Subscriber = "select s.*,cp.name as call_priority_name from Subscriber s,call_priority cp where s.call_priority=cp.priority";
	private static final String Del_Subscriber = "delete from Subscriber where number=?";
	private static final String Update_Cover_Subscriber_Batch = "replace into Subscriber(number,name,call_priority,dnd,description,linetype)VALUES(?,?,?,?,?,?)";

	
	private static final String Select_All_CallPriority = "select * from Call_Priority";
	// private static final String COUNT =
	// "select op.* from operator op where 1=1 ";

	private org.springframework.jdbc.core.simple.SimpleJdbcTemplate simpleJdbcTemplate;
	
	private org.springframework.jdbc.core.JdbcTemplate jdbctemplate;
	

	public org.springframework.jdbc.core.simple.SimpleJdbcTemplate getSimpleJdbcTemplate() {
		return simpleJdbcTemplate;
	}

	@Resource
	public void setSimpleJdbcTemplate(
			org.springframework.jdbc.core.simple.SimpleJdbcTemplate simpleJdbcTemplate) {
		this.simpleJdbcTemplate = simpleJdbcTemplate;
	}

	public int addOperator(final Operator operator) {
		Map<String, Object> userMap = BeanToMapUtil.beanToMap(operator);
		getSimpleJdbcTemplate().update(Insert_Operator, userMap);
		
		return getSimpleJdbcTemplate().queryForInt("SELECT LAST_INSERT_ID()");
	}

	public int login(Operator operator) {
		return getSimpleJdbcTemplate().queryForInt(LOGIN,
				operator.getAccount(), operator.getPassword());
	}

	public int checkOperatorExist(String account) {
		return getSimpleJdbcTemplate().queryForInt(Check_Operator, account);
	}

	public void delOperator(Operator operator) {
		getSimpleJdbcTemplate().update(Del_Operator, operator.getId());
	}

	public void editOperator(final Operator operator) {
		Map<String, Object> userMap = BeanToMapUtil.beanToMap(operator);
		getSimpleJdbcTemplate().update(Update_Operator, userMap);
	}

	public List<Operator> getAllOperator() {
		return getSimpleJdbcTemplate().query(Select_All_Operator,
				new BeanPropertyRowMapper<Operator>(Operator.class));
	}
	
	public List<Operator> getNoAdminOperator() {
		return getSimpleJdbcTemplate().query(Select_No_Admin_Operator,
				new BeanPropertyRowMapper<Operator>(Operator.class));
	}

	public Operator getOperatorById(Operator operator) {
		return getSimpleJdbcTemplate().queryForObject(Get_Operator,
				new BeanPropertyRowMapper<Operator>(Operator.class),
				operator.getId());
	}

	public PageResult operatorPageList(JdbcPageInfo pageInfo) {
		return findByPage(pageInfo);
	}

	public void addSubscriber(Subscriber subscriber) {
		Map<String, Object> userMap = BeanToMapUtil.beanToMap(subscriber);
		log.debug("insert subscriber ..... name:"+subscriber.getName());
		getSimpleJdbcTemplate().update(Insert_Subscriber, userMap);

	}
	/**
	 * add batch subscribers.
	 */
	@Override
    public void delAllSubscriber() {
		getSimpleJdbcTemplate().update(Del_Subscriber_All);
    }

	public int checkSubscriberExist(String number) {
		return getSimpleJdbcTemplate().queryForInt(Check_Subscriber, number);
	}

	public void delSubscriber(Subscriber subscriber) {
		getSimpleJdbcTemplate().update(Del_Subscriber, subscriber.getNumber());

	}
	/**
	 *batch delete subscriberbatch.
	 */
	public void updatecoverSubscriberbatch( List<Object[]> list ){
		getSimpleJdbcTemplate().batchUpdate(Update_Cover_Subscriber_Batch, list);
	}
	public void editSubscriber(Subscriber subscriber) {
		Map<String, Object> userMap = BeanToMapUtil.beanToMap(subscriber);
		getSimpleJdbcTemplate().update(Update_Subscriber, userMap);

	}

	public List<Subscriber> getAllSubscriber() {
		return getSimpleJdbcTemplate().query(Select_All_Subscriber,
				new BeanPropertyRowMapper<Subscriber>(Subscriber.class));
	}

	public Subscriber getSubscriberByNumber(Subscriber subscriber) {
		return getSimpleJdbcTemplate().queryForObject(Get_Subscriber,
				new BeanPropertyRowMapper<Subscriber>(Subscriber.class),
				subscriber.getNumber());
	}

	public PageResult subscriberPageList(JdbcPageInfo pageInfo) {
		log.debug("current page of size"+pageInfo.getPageSize());
		return findByPage(pageInfo);
	}

	public List<CallPriority> getCallPriorityList() {
		return getSimpleJdbcTemplate().query(Select_All_CallPriority,
				new BeanPropertyRowMapper<CallPriority>(CallPriority.class));
	}

	@Override
	public List<Subscriber> subscriberPageList(SubscriberJdbcQuery subscriberJdbcQuery) {
		String sql = "select s.*,cp.name as call_priority_name from Subscriber s,call_priority cp where s.call_priority=cp.priority ";

		String orderby = "";
		if (null != subscriberJdbcQuery.getP_orderBy() && !"".equals(subscriberJdbcQuery.getP_orderBy())
				&& !"".equals(subscriberJdbcQuery.getP_orderDir())) {
			orderby = " order by " + subscriberJdbcQuery.getP_orderBy() + " "
					+ subscriberJdbcQuery.getP_orderDir();
		}
		if (subscriberJdbcQuery.getNumber() != null
				&& subscriberJdbcQuery.getNumber().trim().length() > 0) {
			sql += " and lower(number) like '%"
					+ subscriberJdbcQuery.getNumber() + "%'";
		}

		if (subscriberJdbcQuery.getCall_priority() != null
				&& subscriberJdbcQuery.getCall_priority() > 0) {
			sql += " and lower(call_priority) = "
					+ subscriberJdbcQuery.getCall_priority();
		}

		if (subscriberJdbcQuery.getDescription() != null
				&& subscriberJdbcQuery.getDescription().trim().length() > 0) {
			sql += " and lower(s.description) like '%"
					+ subscriberJdbcQuery.getDescription() + "%'";
		}
		if (subscriberJdbcQuery.getName() != null
				&& subscriberJdbcQuery.getName().trim().length() > 0) {

			String[] nameArr = subscriberJdbcQuery.getName().split(",");
			String operatorNameArrString = "";
			if (nameArr.length <= 1) {
				sql += " and lower(s.name) like '%"
						+ subscriberJdbcQuery.getName() + "%'";
			} else {
				sql += " and (";
				
				for(int i = 0; i < nameArr.length; i++){
					if(i == nameArr.length - 1){
						operatorNameArrString += "lower(s.name) like '%"+ nameArr[i] +"%'";
					}
					else{
						operatorNameArrString += "lower(s.name) like '%"+ nameArr[i] +"%' or ";
					}	
				}

				sql += operatorNameArrString + ")";
			}
		}

		if (subscriberJdbcQuery.getDnd() != null) {
			sql += " and s.dnd = " + subscriberJdbcQuery.getDnd();

		}
		
		if(subscriberJdbcQuery.getLinetype() != null && subscriberJdbcQuery.getLinetype() >= 0){
			sql += " and s.linetype = " + subscriberJdbcQuery.getLinetype();
		}
		
		sql = sql + orderby;
		log.debug("export subscriber of sql :"+sql);
		return getSimpleJdbcTemplate().query(sql,
				new BeanPropertyRowMapper<Subscriber>(Subscriber.class));
	}
}
