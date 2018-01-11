package com.osg.framework.test;

import org.junit.After;
import org.junit.Before;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.osg.entity.ReturnStatus;

public abstract class TestCaseBase {

	private ClassPathXmlApplicationContext ctx;

	public <T> T getBean(Class<T> cls) {
		return ctx.getBean(cls);
	}

	@Before
	public void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("classpath*:/spring/spring-*.xml");
		ctx.start();
	}

	@After
	public void shutdown() throws Exception {
	}

	protected void printStatus(ReturnStatus status) {
		System.out.println(status.toString());
	}

}
