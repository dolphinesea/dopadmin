package com.xinguo.util.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UdpUtil {

	private String hostname;
	private int port = 5000;
	private DatagramSocket dataSocket;
	private DatagramPacket dataPacket;

	public UdpUtil(String hostname, int port) throws SocketException {
		super();
		this.hostname = hostname;
		this.port = port;

	}

	public void Send(byte[] data) {
		try {
			dataSocket = new DatagramSocket();
			dataPacket = new DatagramPacket(data, data.length, InetAddress
					.getByName(hostname), port);
			dataSocket.send(dataPacket);

		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {

			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			dataSocket.close();
		}

	}
}
