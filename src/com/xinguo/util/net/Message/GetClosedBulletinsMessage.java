package com.xinguo.util.net.Message;

public class GetClosedBulletinsMessage extends BbsMessage {
	public GetClosedBulletinsMessage() {
		super(MessageType.GET_CLOSED_BULLETINS);
	}

	public String getName() {
		return "GetClosedBulletinsMessage";
	}
}
