package com.xinguo.util.net.Message;

import com.xinguo.util.common.BytesUtil;

public class ReopenBulletinMessage extends BbsMessage {
	public ReopenBulletinMessage(int bulletinId) {
		super(MessageType.REOPEN_BULLETIN);

		this.bulletinId = bulletinId;
		byte[] bulletinIdByte = new byte[8];
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
