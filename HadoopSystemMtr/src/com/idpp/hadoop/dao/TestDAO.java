package com.idpp.hadoop.dao;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.idpp.hadoop.dto.ResponseModel;

public class TestDAO {

	private static TestDAO instance;
	private static SqlSessionFactory factory;

	public static TestDAO getInstance() {
		if(instance == null) {
			synchronized (TestDAO.class) {
				instance = new TestDAO();
			}
		}
		return instance;
	}

	//constructor
	public TestDAO() {

		try {
			Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
			factory = new SqlSessionFactoryBuilder().build(reader);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	//호스트정보 등록
	public static void insertAmbariHostInfo(ResponseModel model) {
		SqlSession sqlSession = factory.openSession();
		sqlSession.insert("testSQL.insertAmbariHostInfo", model);
		sqlSession.commit();
		sqlSession.close();
	}
	
	//HDFS 성능 등록
	public static void insertHdfsOverall (ResponseModel model) {
		SqlSession sqlSession = factory.openSession();
		sqlSession.insert("testSQL.insertHdfsOverall", model);
		sqlSession.commit();
		sqlSession.close();
	}
	
	//HDFS node상태 등록
	public static void insertNodeState (ResponseModel model) {
		SqlSession sqlSession = factory.openSession();
		sqlSession.insert("testSQL.insertNodeState", model);
		sqlSession.commit();
		sqlSession.close();
	}
	
	//HDFS 호스트 컴포넌트 등록
	public static void insertHostRole (ResponseModel model) {
		SqlSession sqlSession = factory.openSession();
		sqlSession.insert("testSQL.insertHostRole", model);
		sqlSession.commit();
		sqlSession.close();
	}
	
}
