package com.idpp.hadoop.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class SqlMapSessionFactory {

	public static SqlSessionFactory ssf;
	public static SqlSession session = null;
	
	public void demo() {
		
		
		String resource = "mybatis-config.xml";
		
		Properties prop = new Properties();
		prop.put("driver", "org.mariadb.jdbc.Driver");
		prop.put("url", "jdbc:mariadb://50.100.100.17:3306/IDPP");
		prop.put("username", "dev");
		prop.put("password", "qwer1234!@");
		
		
		try {
			InputStream inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream, prop);
			
			session = sqlSessionFactory.openSession(false);
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static SqlSessionFactory getSqlSessionFactory() {
		return ssf;
	}
}


