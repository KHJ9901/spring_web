package com.cosmos.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class TimeMapperTest {

	private static final Logger log = LoggerFactory.getLogger("TimeMapperTest.class");
	
	@Autowired
	private TimeMapper tm;
	
	@Test
	public void test() { //메소드 이름으로 객체가 만들어짐
		
		tm.getTime();
		
		log.info("시간 처찍어: " + tm.getTime());
		
	}

}
