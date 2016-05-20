package com.robert.dbunit.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlProducer;
import org.dbunit.operation.DatabaseOperation;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.xml.sax.InputSource;

public abstract class AbstractDbUnitTestCase {

	public static IDatabaseConnection dbuintCon;
	private File tempFile;

	/**
	 * 在执行构造函数之前会执行这个方法，但是仅仅只执行一次 用于获取DbUnit的Connection
	 */
	@BeforeClass
	public static void init() throws DatabaseUnitException, SQLException {

	}

	/**
	 * 根据文件名来获取DataSet
	 * 
	 * @param name
	 *            文件名
	 * @return
	 * @throws DataSetException
	 */
	protected IDataSet createDataSet(String name) throws DataSetException {
		// 测试数据文件获取流
		InputStream is = AbstractDbUnitTestCase.class.getClassLoader()
				.getResourceAsStream(name + ".xml");
		Assert.assertNotNull("dbunit基本数据文件不存在", is);
		return new FlatXmlDataSet(new FlatXmlProducer(new InputSource(is)));

	}

	/**
	 * 备份数据库中所有表
	 * 
	 * @throws SQLException
	 * @throws IOException
	 * @throws DataSetException
	 */
	protected void backupAllTable() throws SQLException, IOException,
			DataSetException {
		IDataSet ds = dbuintCon.createDataSet();
		writeBackupFile(ds);
	}

	private void writeBackupFile(IDataSet ds) throws IOException,
			DataSetException {
		// 创建临时文件
		tempFile = File.createTempFile("back", "xml");
		FlatXmlDataSet.write(ds, new FileWriter(tempFile));

	}

	/**
	 * 根据数据库中表名进行数据备份
	 * 
	 * @param names
	 * @throws DataSetException
	 * @throws IOException
	 */
	protected void backupTableNames(String... names) throws DataSetException,
			IOException {
		QueryDataSet qds = new QueryDataSet(dbuintCon);
		for (String name : names) {
			qds.addTable(name);
		}
		writeBackupFile(qds);
	}

	/**
	 * 还原数据库
	 * 
	 * @throws FileNotFoundException
	 * @throws DatabaseUnitException
	 * @throws SQLException
	 */
	protected void resumeTalbe() throws FileNotFoundException,
			DatabaseUnitException, SQLException {
		IDataSet ds = new FlatXmlDataSet(new FlatXmlProducer(new InputSource(
				new FileInputStream(tempFile))));
		DatabaseOperation.CLEAN_INSERT.execute(dbuintCon, ds);
	}

	/**
	 * 关闭DbUnit的Connection
	 */
	@AfterClass
	public static void destory() {
		try {
			if (null != dbuintCon)
				dbuintCon.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.CloseConection();
		}
	}
}
