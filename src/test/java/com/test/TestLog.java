package com.test;

import org.junit.Test;

import com.robert.dbunit.util.DBUtil;

public class TestLog {
	
	@Test
	public void test01(){
		DBUtil.getInstance().getConnection();
	}
}
