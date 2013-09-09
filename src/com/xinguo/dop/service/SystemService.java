package com.xinguo.dop.service;

import java.util.List;

import com.xinguo.dop.entity.AccessCode;
import com.xinguo.dop.entity.CallType;
import com.xinguo.dop.entity.CallTypeIdentification;

public interface SystemService {

	public List<AccessCode> getAccessCodeList();

	public void addAccessCode(AccessCode accessCode);

	public void updateAccessCode(AccessCode accessCode);

	public void delAccessCode(AccessCode accessCode);

	public AccessCode getAccessCode(AccessCode accessCode);

	public List<CallTypeIdentification> getCallTypeIdentificationList(
			CallTypeIdentification callTypeIdentification);

	public void addCallTypeIdentificationList(
			CallTypeIdentification callTypeIdentification);

	public void updateCallTypeIdentificationList(
			CallTypeIdentification callTypeIdentification);

	public void delCallTypeIdentificationList(
			CallTypeIdentification callTypeIdentification);

	public CallTypeIdentification getCallTypeIdentification(
			CallTypeIdentification callTypeIdentification);

	public List<CallType> getCallTypeList();

	public void addCallType(CallType callType);

	public void updateCallType(CallType callType);

	public void delCallType(CallTypeIdentification callTypeIdentification);

	public CallType getCallType(CallType callType);

	public int checkAccessCode(AccessCode accessCode);

	public int checkCallType(CallType callType);
	
	public int checkCallTypeIdentificationbycalltype(CallTypeIdentification callTypeIdentification);
	
	public int checkCallTypeIdentification(CallTypeIdentification callTypeIdentification);
	
	public int getCallTypeNumberInIdentification(CallTypeIdentification callTypeIdentification);
	
}
