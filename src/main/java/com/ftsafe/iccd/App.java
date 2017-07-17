package com.ftsafe.iccd;

import com.ftsafe.iccd.personalize.socket.TcpServer;

public class App {

	public static void main(String[] args) {
		try {
			new TcpServer(3030).run();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
