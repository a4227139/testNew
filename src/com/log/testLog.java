package com.log;

import java.util.Properties;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class testLog {

	protected final static Logger logger = Logger.getLogger(testLog.class.getName());
	public static void main(String[] args) throws InterruptedException {
		Properties prop = new Properties();
		prop.setProperty("log4j.logger.com.log", "DEBUG, ServerDailyRollingFile");
		prop.setProperty("log4j.appender.ServerDailyRollingFile", "org.apache.log4j.DailyRollingFileAppender");
		prop.setProperty("log4j.appender.ServerDailyRollingFile.DatePattern", "'.'yyyy-MM-dd");
		prop.setProperty("log4j.appender.ServerDailyRollingFile.File", "E://logs/wmscsg.log");
		prop.setProperty("log4j.appender.ServerDailyRollingFile.layout", "org.apache.log4j.PatternLayout");
		prop.setProperty("log4j.appender.ServerDailyRollingFile.layout.ConversionPattern", "%d - %m%n");
		prop.setProperty("log4j.appender.ServerDailyRollingFile.Append", "true");
		PropertyConfigurator.configure(prop);
		System.out.println(Thread.currentThread().getContextClassLoader().getResource("/").getPath());
		logger.debug("hhh3");
		logger.debug("hhh4");
	}
}