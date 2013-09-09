package com.xinguo.dop.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.xinguo.dop.dao.BaseJdbcDao;
import com.xinguo.dop.dao.SystemDao;
import com.xinguo.dop.entity.AccessCode;
import com.xinguo.dop.entity.CallType;
import com.xinguo.dop.entity.CallTypeIdentification;
import com.xinguo.dop.entity.Operator;
import com.xinguo.util.common.BeanToMapUtil;

@Repository("systemDao")
public class SystemDaoImpl extends BaseJdbcDao implements SystemDao {

	private static final String Select_All_AccessCode = "select * from access_code";

	private static final String Select_AccessCode = "select * from access_code where code=?";

	private static final String Check_AccessCode = "select count(1) from access_code where code=?";

	private static final String Select_All_CallType = "select * from Call_Type";

	private static final String Select_CallType = "select * from Call_Type where name=?";

	private static final String Check_CallType = "select count(1) from Call_Type where name=?";

	private static final String Select_CallTypeIdentification_List = "SELECT cti.access_code, ct.name calltypename from call_type_identification cti left join call_type ct on cti.call_type=ct.id GROUP BY cti.access_code";
	
	private static final String Select_CallTypeIdentification = "SELECT cti.access_code, ct.name calltypename from call_type_identification cti left join call_type ct on cti.call_type=ct.id where access_code=? limit 1";
	
	private static final String Check_CallTypeIdentificationBycalltype = "select count(1) from call_type_identification where call_type=?";
	
	private static final String Check_CallTypeIdentification = "select count(1) from call_type_identification where access_code=?";

	private static final String Count_CallTypeInCallTypeIdentification = "select count(1) from(SELECT call_type from call_type_identification where call_type=?) cou";

	private static final String GET_MAX_CallType = "SELECT MAX(ID) FROM CALL_TYPE";

	private static final String Insert_AccessCode = "insert into access_code(code,name,description)VALUES(:code,:name,:description)";

	private static final String Insert_CallTypeIdentification = "insert into Call_Type_Identification(access_code,calling_class,call_type)VALUES(:accessCode,:callingClass,:callType)";

	private static final String Insert_CallType = "insert into Call_Type(id,name,description)VALUES(:id,:name,:description)";

	private static final String update_AccessCode = "update access_code set name=:name,description=:description where code=:code";

	private static final String update_CallTypeIdentification = "update Call_Type_Identification set calling_class=:callingClass where access_code=:accessCode and call_type=:callType ";

	private static final String update_CallType = "update Call_Type set name=:name,description=:description where id=:id";

	private static final String Del_AccessCode = "delete from access_code where code=?";

	private static final String Del_CallType = "delete from Call_Type where id=?";

	private static final String Del_All_CallTypeIdentification = "delete from Call_Type_Identification where access_code=?";

	private static final String Del_All_CallTypeIdentificationByCallType = "delete from Call_Type_Identification where call_type=?";

	private static final String Del_CallTypeIdentification = "delete from Call_Type_Identification where access_code=? and calling_class=?";

	private org.springframework.jdbc.core.simple.SimpleJdbcTemplate simpleJdbcTemplate;

	public org.springframework.jdbc.core.simple.SimpleJdbcTemplate getSimpleJdbcTemplate() {
		return simpleJdbcTemplate;
	}

	@Resource
	public void setSimpleJdbcTemplate(
			org.springframework.jdbc.core.simple.SimpleJdbcTemplate simpleJdbcTemplate) {
		this.simpleJdbcTemplate = simpleJdbcTemplate;
	}

	public List<AccessCode> getAccessCodeList() {
		return getSimpleJdbcTemplate().query(Select_All_AccessCode,
				new BeanPropertyRowMapper<AccessCode>(AccessCode.class));
	}

	public void addAccessCode(AccessCode accessCode) {
		Map<String, Object> userMap = BeanToMapUtil.beanToMap(accessCode);
		getSimpleJdbcTemplate().update(Insert_AccessCode, userMap);
	}

	public void addCallTypeIdentificationList(
			CallTypeIdentification callTypeIdentification) {
		Map<String, Object> userMap = BeanToMapUtil
				.beanToMap(callTypeIdentification);
		getSimpleJdbcTemplate().update(Insert_CallTypeIdentification, userMap);

	}

	public void delAccessCode(AccessCode accessCode) {
		getSimpleJdbcTemplate().update(Del_AccessCode, accessCode.getCode());
		getSimpleJdbcTemplate().update(Del_All_CallTypeIdentification,accessCode.getCode());
	}

	public void delCallTypeIdentificationList(
			CallTypeIdentification callTypeIdentification) {
		getSimpleJdbcTemplate().update(Del_CallTypeIdentification,
				callTypeIdentification.getAccessCode(),
				callTypeIdentification.getCallingClass());

	}

	public List<CallTypeIdentification> getCallTypeIdentificationList(
			CallTypeIdentification callTypeIdentification) {
		String sql = Select_CallTypeIdentification_List;
		if (callTypeIdentification != null) {
			sql = sql + "and cti.access_code=?";
			return getSimpleJdbcTemplate().query(
					sql,
					new BeanPropertyRowMapper<CallTypeIdentification>(
							CallTypeIdentification.class),
					callTypeIdentification.getAccessCode());
		}
		return getSimpleJdbcTemplate().query(
				sql,
				new BeanPropertyRowMapper<CallTypeIdentification>(
						CallTypeIdentification.class));

	}

	public void updateAccessCode(AccessCode accessCode) {
		Map<String, Object> userMap = BeanToMapUtil.beanToMap(accessCode);
		getSimpleJdbcTemplate().update(update_AccessCode, userMap);

	}

	public void updateCallTypeIdentificationList(
			CallTypeIdentification callTypeIdentification) {
		try {
			Map<String, Object> userMap = BeanToMapUtil
			.beanToMap(callTypeIdentification);
	getSimpleJdbcTemplate().update(update_CallTypeIdentification, userMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}

	public AccessCode getAccessCode(AccessCode accessCode) {
		return getSimpleJdbcTemplate().queryForObject(Select_AccessCode,
				new BeanPropertyRowMapper<AccessCode>(AccessCode.class),
				accessCode.getCode());
	}

	public CallTypeIdentification getCallTypeIdentification(
			CallTypeIdentification callTypeIdentification) {
		return getSimpleJdbcTemplate().queryForObject(
				Select_CallTypeIdentification,
				new BeanPropertyRowMapper<CallTypeIdentification>(
						CallTypeIdentification.class),
				callTypeIdentification.getAccessCode());
	}

	public List<CallType> getCallTypeList() {
		return getSimpleJdbcTemplate().query(Select_All_CallType,
				new BeanPropertyRowMapper<CallType>(CallType.class));
	}

	public void addCallType(CallType callType) {

		int max = getSimpleJdbcTemplate().queryForInt(GET_MAX_CallType) + 1;

		callType.setId(max);
		Map<String, Object> userMap = BeanToMapUtil.beanToMap(callType);
		getSimpleJdbcTemplate().update(Insert_CallType, userMap);

	}

	public void delCallType(CallTypeIdentification callTypeIdentification) {
		getSimpleJdbcTemplate().update(Del_CallType, callTypeIdentification.getId());
	}

	public CallType getCallType(CallType callType) {
		return getSimpleJdbcTemplate().queryForObject(Select_CallType,
				new BeanPropertyRowMapper<CallType>(CallType.class),
				callType.getName());
	}

	public void updateCallType(CallType callType) {
		Map<String, Object> userMap = BeanToMapUtil.beanToMap(callType);
		getSimpleJdbcTemplate().update(update_CallType, userMap);
	}

	public int checkAccessCode(AccessCode accessCode) {
		return getSimpleJdbcTemplate().queryForInt(Check_AccessCode,
				accessCode.getCode());
	}

	public int checkCallType(CallType callType) {
		return getSimpleJdbcTemplate().queryForInt(Check_CallType,
				callType.getName());
	}
	
	public int checkCallTypeIdentificationbycalltype(CallTypeIdentification callTypeIdentification){
		return getSimpleJdbcTemplate().queryForInt(Check_CallTypeIdentificationBycalltype, 
				callTypeIdentification.getId());
	}

	public int checkCallTypeIdentification(
			CallTypeIdentification callTypeIdentification) {
		return getSimpleJdbcTemplate().queryForInt(
				Check_CallTypeIdentification,
				callTypeIdentification.getAccessCode());
	}

	public int getCallTypeNumberInIdentification(
			CallTypeIdentification callTypeIdentification) {
		return getSimpleJdbcTemplate().queryForInt(
				Count_CallTypeInCallTypeIdentification,
				callTypeIdentification.getCallType());
	}
}
