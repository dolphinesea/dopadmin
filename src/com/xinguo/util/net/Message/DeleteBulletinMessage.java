package com.xinguo.util.net.Message;

import com.xinguo.util.common.BytesUtil;

public class DeleteBulletinMessage extends BbsMessage {
	public DeleteBulletinMessage(int bulletinId) {
		super(MessageType.DELETE_BULLETIN);

		this.bulletinId = bulletinId;
		byte[] bulletinIdByte = new byte[4];
		BytesUtil.putReverseBytesInt(bulletinIdByte, getBulletinId(), 0);
		msgByte = BytesUtil.addBytes(msgByte, bulletinIdByte);
	}

	public String getName() {
		return "BbsMessage";
	}

	int bulletinId;

	public int getBulletinId() {
		return bulletinId;
	}

	public void setBulletinId(int bulletinId) {
		this.bulletinId = bulletinId;
	}

}
