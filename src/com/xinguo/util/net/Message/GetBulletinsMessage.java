package com.xinguo.util.net.Message;


public class GetBulletinsMessage extends BbsMessage {

	public GetBulletinsMessage() {
		super(MessageType.GET_BULLETINS);
	}

	public final String getName() {
		return "GetBulletinsMessage";
	}
}
