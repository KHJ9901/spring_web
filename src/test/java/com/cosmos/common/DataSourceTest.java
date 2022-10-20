package com.cosmos.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class DataSourceTest {

	private static final Logger log = LoggerFactory.getLogger("DataSourceTest.class");
	
	@Autowired
	private DataSource ds;
	
	@Test
	public void test() { //메소드 이름으로 객체가 만들어짐
		
		try {
			Connection conn = ds.getConnection();
			log.info("데이터-소스" + conn.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
