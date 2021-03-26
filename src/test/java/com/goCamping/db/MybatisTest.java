package com.goCamping.db;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"file:src/main/webapp/WEB-INF/sprig/root-context.xml"} )
public class MybatisTest {

	@Autowired
	private SqlSessionFactory sqlFactory;
	
	private static final Logger logger = LogManager.getLogger(MybatisTest.class);
	
	@Test
	public void mybaits_작동테스트() {
		
		try {
			
			SqlSession session = sqlFactory.openSession();
			logger.info("mybatis 작동 테스트 성공!");
			
		} catch (Exception e) {
			
			logger.info("mybatis 작동 실패!");
			e.printStackTrace();
			
		}
		
	}
	
	
}
