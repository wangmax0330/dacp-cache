package com.pikai.cache.convention;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AccountServiceTest {

	private AccountService accountService;

	private final Logger logger = LoggerFactory.getLogger(AccountServiceTest.class);

	@Before
	public void setUp() throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext1.xml");
		accountService = context.getBean("accountService", AccountService.class);
		// Resource resource = new ClassPathResource("appcontext.xml");
		// BeanFactory factory = new XmlBeanFactory(resource);
		// 用classpath路径
		// ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:appcontext.xml");
		// ApplicationContext factory = new ClassPathXmlApplicationContext("appcontext.xml");
		// ClassPathXmlApplicationContext使用了file前缀是可以使用绝对路径的
		// ApplicationContext factory = new ClassPathXmlApplicationContext("file:F:/workspace/example/src/appcontext.xml");
		// 用文件系统的路径,默认指项目的根路径
		// ApplicationContext factory = new FileSystemXmlApplicationContext("src/appcontext.xml");
		// ApplicationContext factory = new FileSystemXmlApplicationContext("webRoot/WEB-INF/appcontext.xml");
		// 使用了classpath:前缀,这样,FileSystemXmlApplicationContext也能够读取classpath下的相对路径
		// ApplicationContext factory = new FileSystemXmlApplicationContext("classpath:appcontext.xml");
		// ApplicationContext factory = new FileSystemXmlApplicationContext("file:F:/workspace/example/src/appcontext.xml");
		// 不加file前缀
		// ApplicationContext factory = new FileSystemXmlApplicationContext("F:/workspace/example/src/appcontext.xml");
		// IHelloWorld hw = (IHelloWorld)factory.getBean("helloworldbean");
		// log.info(hw.getContent("luoshifei"));

	}

	@Test
	public void testInject() {
		assertNotNull(accountService);
	}

	@Test
	public void testGetAccountByName() throws Exception {
		accountService.getAccountByName("accountName");
		accountService.getAccountByName("accountName");

		accountService.reload();
		logger.info("after reload ....");

		accountService.getAccountByName("accountName");
		accountService.getAccountByName("accountName");
	}
}
