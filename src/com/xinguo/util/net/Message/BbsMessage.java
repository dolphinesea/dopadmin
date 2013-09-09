package com.xinguo.util.net.Message;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import com.xinguo.dop.entity.Bbs;
import com.xinguo.util.common.BytesUtil;

public class BbsMessage implements java.io.Serializable {
	enum MessageType {
		// / return open bulletins.
		GET_BULLETINS,

		// / return closed bulletins.
		GET_CLOSED_BULLETINS,

		// / The bulletin is normal state.
		OPEN_BULLETIN,

		// / operator cann't view the bulletin, but it has been destroyed.
		CLOSE_BULLETIN,

		// / change bulletin from close to open
		REOPEN_BULLETIN,

		// / destroy the bulletin
		DELETE_BULLETIN,
	};

	protected byte[] msgByte;

	public byte[] getMsgByte() {
		return msgByte;
	}

	public BbsMessage(MessageType type) {
		super();
		type_ = type;
		msgByte = new byte[4];
		BytesUtil.putReverseBytesInt(msgByte, getType_().ordinal(), 0);
	}

	public String getName() {
		return "BbsMessage";
	}

	public String getDetailInfo() {
		switch (type_) {
		case GET_BULLETINS:
			return "type = GET_BULLETINS";
		case GET_CLOSED_BULLETINS:
			return "type = GET_CLOSED_BULLETINS";
		case OPEN_BULLETIN:
			return "type = OPEN_BULLETIN";
		case CLOSE_BULLETIN:
			return "type = CLOSE_BULLETIN";
		case REOPEN_BULLETIN:
			return "type = REOPEN_BULLETIN";
		case DELETE_BULLETIN:
			return "type = DELETE_BULLETIN";
		default:
			return "type = unknow";
		}
	}

	public MessageType getType_() {
		return type_;
	}

	public void setType_(MessageType type) {
		type_ = type;
	}

	public byte[] msgBytes() {
		Field[] fields = this.getClass().getFields();
		for (int i = 0; i < fields.length; i++) {
			fields[i].getType();
		}

		return null;
	}

	protected MessageType type_;

	public static void main(String[] args) {

	}
}
