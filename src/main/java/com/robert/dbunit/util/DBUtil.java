package com.robert.dbunit.util;

import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class DBUtil {

	private static Logger log = Logger.getLogger(DBUtil.class.getName());

	public static String url = "jdbc:mysql:///test";

	public static String driverName = "com.mysql.jdbc.Driver";

	public static String username = "root";

	public static String password = "root";

	private static java.sql.Connection conn;

	private static DBUtil dbUtil;

	private DBUtil() {
	}

	public static DBUtil getInstance() {
		if (null == dbUtil) {
			dbUtil = new DBUtil();
		}
		return dbUtil;
	}

	public java.sql.Connection getConnection() {
		if (conn == null)
			try {
				Class.forName(driverName);

				log.info("获取当前连接的URL--->" + url);

				conn = DriverManager.getConnection(url, username, password);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return conn;
	}

	public static void CloseConection() {
		if (null != conn) {
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
