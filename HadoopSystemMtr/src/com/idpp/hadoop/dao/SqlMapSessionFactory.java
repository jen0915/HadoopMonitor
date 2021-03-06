package com.idpp.hadoop.dao;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class SqlMapSessionFactory {

	private static SqlSessionFactory sessionFactory;
	
	static {
		try {
			String resource = "mybatis-config.xml";
			Reader reader = Resources.getResourceAsReader(resource);
			
			if(sessionFactory == null) {
				sessionFactory = new SqlSessionFactoryBuilder().build(reader);
			}
//			SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader);
			
//			session = sqlMapper.openSession();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public static SqlSessionFactory getSqlSessionFactory() {
		return sessionFactory;
	}
}


