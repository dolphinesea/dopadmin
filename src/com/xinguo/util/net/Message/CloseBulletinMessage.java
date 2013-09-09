package com.xinguo.util.net.Message;

import java.io.UnsupportedEncodingException;

import com.xinguo.util.common.BytesUtil;
import com.xinguo.util.net.Message.BbsMessage.MessageType;

public class CloseBulletinMessage extends BbsMessage {

	public final static int MAX_USERNAME_LENGTH = 20;

	public CloseBulletinMessage() {
		super(MessageType.CLOSE_BULLETIN);
	}

	public CloseBulletinMessage(int bulletinId, String deletedBy)
			throws UnsupportedEncodingException {
		super(MessageType.CLOSE_BULLETIN);
		this.bulletinId = bulletinId;
		this.deletedBy = deletedBy;
		
		byte[] deletedByByte = new byte[MAX_USERNAME_LENGTH];
		System.arraycopy(deletedBy.getBytes("gb2312"), 0, deletedByByte, 0,
				deletedBy.getBytes("gb2312").length);
		msgByte = BytesUtil.addBytes(msgByte, deletedByByte);
		
		byte[] bulletinIdByte = new byte[4];
		BytesUtil.putReverseBytesInt(bulletinIdByte, getBulletinId(), 0);
		msgByte = BytesUtil.addBytes(msgByte, bulletinIdByte);

		
	}

	public String getName() {
		return "BbsMessage";
	}

	int bulletinId;

	String deletedBy;

	public int getBulletinId() {
		return bulletinId;
	}

	public void setBulletinId(int bulletinId) {
		this.bulletinId = bulletinId;
	}

	public String getDeletedBy() {
		return deletedBy;
	}

	public void setDeletedBy(String deletedBy) {
		this.deletedBy = deletedBy;
	}

}
