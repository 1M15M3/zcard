package com.ftsafe.iccd.huipan.log;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;

public class LoggerUtil {

	
	public Logger createLoggerFor (String loggerName, String file){

		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		PatternLayoutEncoder  encoder = new PatternLayoutEncoder ();
		encoder.setContext(lc);
		encoder.setPattern("%msg%n");
		encoder.start();
		
		RollingFileAppender<ILoggingEvent> rfAppender = new RollingFileAppender<ILoggingEvent>();
		rfAppender.setContext(lc);
		
		TimeBasedRollingPolicy<ILoggingEvent> timePolicy = new TimeBasedRollingPolicy<ILoggingEvent>();
		timePolicy.setFileNamePattern(file);
		timePolicy.setParent(rfAppender);
		timePolicy.setContext(lc);
		timePolicy.start();

		rfAppender.setRollingPolicy(timePolicy);
		rfAppender.setEncoder(encoder);
		rfAppender.start();
		
		Logger logger = (Logger) LoggerFactory.getLogger(loggerName);
		logger.addAppender(rfAppender);
		logger.setLevel(Level.INFO);
		logger.setAdditive(false); /* set to true if root should log too */

		return logger;
	
	}
}
