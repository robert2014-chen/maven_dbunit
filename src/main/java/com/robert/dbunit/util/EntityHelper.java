package com.robert.dbunit.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class EntityHelper<T> {

	/**
	 * 
	 * 断言两个对象是否相等
	 * 
	 * @param expect
	 *            预期对象
	 * @param actual
	 *            实际对象
	 * @param clz
	 *            传入的实体的类型
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public void assertEqualEntity(T expect, T actual, Class clz)
			throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		assertNotNull("获取到的对象为空", actual);
		Field[] fields = clz.getDeclaredFields();
		for (Field field : fields) {
			String methodName = "get"
					+ field.getName().substring(0, 1).toUpperCase()
					+ field.getName().substring(1);
			Method getterMethod = clz.getMethod(methodName);
			assertNotNull(field.getName() + "属性值为空",
					getterMethod.invoke(actual));
			assertEquals(field.getName() + "属性值不相等",
					getterMethod.invoke(expect), getterMethod.invoke(actual));
		}
	}

	/**
	 * 断言两个集合是否相等
	 * 
	 * @param expect
	 *            预期集合
	 * @param actual
	 *            实际集合
	 * @param clz
	 *            集合中元素的类型
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public void assertEqualList(List<T> expect, List<T> actual, Class clz)
			throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		assertNotNull("获取到的集合为空", actual);
		assertEquals("预期集合和获取的集合的长度不相等", expect.size(), actual.size());
		for (int i = 0; i < expect.size(); i++) {
			assertEqualEntity(expect.get(i), actual.get(i), clz);
		}
	}
}
