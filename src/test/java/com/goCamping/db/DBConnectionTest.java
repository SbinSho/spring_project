package com.goCamping.db;

import java.sql.Connection;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class DBConnectionTest {

	
	@Autowired
	private DataSource dataSource;
	
	private static final Logger logger = LogManager.getLogger(DBConnectionTest.class);
	
	@Test
	public void DB_연결테스트() {
		try {
			
			Connection con = dataSource.getConnection();
			logger.info("DB 연결 성공!");
			
		} catch (Exception e) {
			logger.info("DB 연결 실패!");
			e.printStackTrace();
		}
		
	}
	
}
