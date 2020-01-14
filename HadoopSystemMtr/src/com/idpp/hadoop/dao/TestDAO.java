package com.idpp.hadoop.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import com.idpp.hadoop.dto.ResponseModel;

public class TestDAO {

	
	//ȣ��Ʈ���� ���
	public static void insertAmbariHostInfo(ResponseModel model) {
		SqlSession sqlSession = SqlMapSessionFactory.getSqlSession();
		sqlSession.insert("testSQL.insertAmbariHostInfo", model);
		sqlSession.commit();
		sqlSession.close();
	}
	
	//HDFS ���� ���
	public static void insertHdfsOverall (ResponseModel model) {
		SqlSession sqlSession = SqlMapSessionFactory.getSqlSession();
		sqlSession.insert("testSQL.insertHdfsOverall", model);
		sqlSession.commit();
		sqlSession.close();
	}
	
	//HDFS node���� ���
	public static void insertNodeState (ResponseModel model) {
		SqlSession sqlSession = SqlMapSessionFactory.getSqlSession();
		sqlSession.insert("testSQL.insertNodeState", model);
		sqlSession.commit();
		sqlSession.close();
	}
	
	//HDFS ȣ��Ʈ ������Ʈ ���
	public static void insertHostRole (List<ResponseModel> list) {
		SqlSession sqlSession = SqlMapSessionFactory.getSqlSession();
		for(ResponseModel rModel : list) {
			sqlSession.insert("testSQL.insertHostRole", rModel);
		}
		
		sqlSession.commit();
		sqlSession.close();
	}
	
}
