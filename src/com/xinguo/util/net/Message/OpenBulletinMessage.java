package com.xinguo.util.net.Message;

import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;

import com.xinguo.util.common.BytesUtil;
import com.xinguo.util.net.Message.BbsMessage.MessageType;

public class OpenBulletinMessage extends BbsMessage {
	private Logger logger = Logger.getLogger(OpenBulletinMessage.class);
	
	public final static int ADMIN_MESSAGE = 1;

	public final static int NORMAL_MESSAGE = 2;

	public final static int MAX_USERNAME_LENGTH = 20;

	public final static int MAX_BODY_LENGTH = 1001;

	public OpenBulletinMessage() {
		super(MessageType.OPEN_BULLETIN);
	}

	public String getName() {
		return "OpenBulletinMessage";
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

	public OpenBulletinMessage(int bulletinType, String writer, String body) throws UnsupportedEncodingException {
		super(MessageType.OPEN_BULLETIN);

		this.id = 0;
		this.isDeleted = 0;
		this.bulletinType = bulletinType;

		this.writer = writer;
		this.createdDate = 0;
		this.body = body + "\0";
		this.bodySize = body.length();

		byte[] idbyte = new byte[4];
		BytesUtil.putReverseBytesInt(idbyte, id, 0);
		msgByte = BytesUtil.addBytes(msgByte, idbyte);

		byte[] isDeletedbyte = new byte[4];
		BytesUtil.putReverseBytesInt(isDeletedbyte, isDeleted, 0);
		msgByte = BytesUtil.addBytes(msgByte, isDeletedbyte);

		byte[] bulletinTypebyte = new byte[4];
		BytesUtil.putReverseBytesInt(bulletinTypebyte, bulletinType, 0);
		msgByte = BytesUtil.addBytes(msgByte, bulletinTypebyte);

		byte[] writerbyte = new byte[MAX_USERNAME_LENGTH];
		System.arraycopy(writer.getBytes("GB2312"), 0, writerbyte, 0,
				writer.getBytes("GB2312").length);
		msgByte = BytesUtil.addBytes(msgByte, writerbyte);
		
		
		byte[] createdDatebyte = new byte[4];
		BytesUtil.putReverseBytesInt(createdDatebyte, createdDate, 0);
		msgByte = BytesUtil.addBytes(msgByte, createdDatebyte);

		byte[] bodySizebyte = new byte[4];
		BytesUtil.putReverseBytesInt(bodySizebyte, bodySize, 0);
		msgByte = BytesUtil.addBytes(msgByte, bodySizebyte);

		byte[] bodybyte = new byte[MAX_BODY_LENGTH];
		System.arraycopy(body.getBytes("gbk"), 0, bodybyte, 0,
				body.getBytes("gbk").length);
		this.logger.debug("body:" + body);
		System.out.println("bodygbk:" + new String(body.getBytes("gbk"), "gbk"));
		this.logger.debug("bodygbklenght:" + body.getBytes("gbk").length);
		System.out.println("bodybyte:" + new String(bodybyte, "gbk"));
		this.logger.debug("bodybytelenght:" + bodybyte.length);
		msgByte = BytesUtil.addBytes(msgByte, bodybyte);

	}
	
	private byte[] convertCode(String str){
	    char[] c = str.toCharArray();  
	    byte[] fullByte = new byte[3*c.length];  
	    for (int i=0; i<c.length; i++) {  
	        String binary = Integer.toBinaryString(c[i]);  
	        StringBuffer sb = new StringBuffer();  
	        int len = 16 - binary.length();  
	        //前面补零  
	        for(int j=0; j<len; j++){  
	                sb.append("0");  
	            }  
	        sb.append(binary);  
	        //增加位，达到到24位3个字节  
	        sb.insert(0, "1110");  
			sb.insert(8, "10");
			sb.insert(16, "10");
			fullByte[i * 3] = Integer.valueOf(sb.substring(0, 8), 2).byteValue();// 二进制字符串创建整型
			fullByte[i * 3 + 1] = Integer.valueOf(sb.substring(8, 16), 2).byteValue();
			fullByte[i * 3 + 2] = Integer.valueOf(sb.substring(16, 24), 2).byteValue();
	    }
	    
	    return fullByte;
	}

	int id;

	int isDeleted;

	int bulletinType;

	String writer = "";

	int createdDate;

	int bodySize;

	String body = "";
}
