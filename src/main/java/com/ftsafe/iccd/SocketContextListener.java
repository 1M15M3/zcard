package com.ftsafe.iccd;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.axis.common.Log;
import com.axis.common.log.LogWrapper;
import com.ftsafe.iccd.personalize.socket.TcpServer;

public class SocketContextListener implements ServletContextListener {

	private static final LogWrapper logger = Log.get();
	private Thread myThread;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		if (myThread == null) {
			myThread = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						int port = ZcardConfig.TCP_PORT;
						logger.info("TCP 服务端口 {}", port);
						new TcpServer(port).run();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			myThread.start();
		}
	}

}
