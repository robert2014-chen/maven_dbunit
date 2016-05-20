package com.test;

import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class MainClass {
	// 调用Logger的getLogger方法获得一个Logger的实例，在这个方法中通常传入当前要记录的类
	private static Logger log = Logger.getLogger(MainClass.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		PropertyConfigurator.configure ( "log4j.properties" ) ;
		log.info("程序开始运行" + new Date().toString());
		int result = 0;
		try {
			result = 4 / 0;
		} catch (Exception ex) {
			log.error("在获得两个数相除的结果时产生异常.", ex);
		}
		log.info("程序运行结束" + new Date().toString());
	}
}
