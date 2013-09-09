package com.xinguo.dop.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xinguo.dop.dao.SystemDao;
import com.xinguo.dop.entity.AccessCode;
import com.xinguo.dop.entity.CallType;
import com.xinguo.dop.entity.CallTypeIdentification;
import com.xinguo.dop.service.SystemService;

@Service("systemService")
public class SystemServiceImpl implements SystemService {

	private SystemDao systemDao;

	public SystemDao getSystemDao() {
		return systemDao;
	}

	@Resource(name = "systemDao")
	public void setSystemDao(SystemDao systemDao) {
		this.systemDao = systemDao;
	}

	public List<AccessCode> getAccessCodeList() {
		return systemDao.getAccessCodeList();
	}

	public void addAccessCode(AccessCode accessCode) {
		systemDao.addAccessCode(accessCode);
	}

	public void addCallTypeIdentificationList(
			CallTypeIdentification callTypeIdentification) {
		systemDao.addCallTypeIdentificationList(callTypeIdentification);

	}

	public void delAccessCode(AccessCode accessCode) {
		systemDao.delAccessCode(accessCode);

	}

	public void delCallTypeIdentificationList(
			CallTypeIdentification callTypeIdentification) {
		systemDao.delCallTypeIdentificationList(callTypeIdentification);

	}

	public List<CallTypeIdentification> getCallTypeIdentificationList(
			CallTypeIdentification callTypeIdentification) {
		return systemDao.getCallTypeIdentificationList(callTypeIdentification);
	}

	public void updateAccessCode(AccessCode accessCode) {
		systemDao.updateAccessCode(accessCode);

	}

	public void updateCallTypeIdentificationList(
			CallTypeIdentification callTypeIdentification) {
		systemDao.updateCallTypeIdentificationList(callTypeIdentification);

	}

	public AccessCode getAccessCode(AccessCode accessCode) {
		return systemDao.getAccessCode(accessCode);
	}

	public CallTypeIdentification getCallTypeIdentification(
			CallTypeIdentification callTypeIdentification) {
		return systemDao.getCallTypeIdentification(callTypeIdentification);
	}

	public List<CallType> getCallTypeList() {
		return systemDao.getCallTypeList();
	}

	public void addCallType(CallType callType) {
		systemDao.addCallType(callType);

	}

	public void delCallType(CallTypeIdentification callTypeIdentification) {
		systemDao.delCallType(callTypeIdentification);

	}

	public CallType getCallType(CallType callType) {
		return systemDao.getCallType(callType);
	}

	public void updateCallType(CallType callType) {
		systemDao.updateCallType(callType);

	}

	public int checkAccessCode(AccessCode accessCode) {
		return systemDao.checkAccessCode(accessCode);
	}

	public int checkCallType(CallType callType) {
		return systemDao.checkCallType(callType);
	}
	
	public int checkCallTypeIdentificationbycalltype(CallTypeIdentification callTypeIdentification){
		return systemDao.checkCallTypeIdentificationbycalltype(callTypeIdentification);
	}

	public int checkCallTypeIdentification(
			CallTypeIdentification callTypeIdentification) {
		return systemDao.checkCallTypeIdentification(callTypeIdentification);
	}

	public int getCallTypeNumberInIdentification(
			CallTypeIdentification callTypeIdentification) {
		return systemDao.getCallTypeNumberInIdentification(callTypeIdentification);
	}

}
