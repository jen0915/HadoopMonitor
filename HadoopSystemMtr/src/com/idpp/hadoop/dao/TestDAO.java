package com.idpp.hadoop.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import com.idpp.hadoop.dto.ResponseModel;

public class TestDAO {

	
	//호스트정보 등록
	public static void insertAmbariHostInfo(List<ResponseModel> statelist) throws Exception{
		SqlSession sqlSession = SqlMapSessionFactory.getSqlSession();
		System.out.println("statelist size : " + statelist.size());
		for(ResponseModel rModel : statelist) {
			sqlSession.insert("testSQL.insertAmbariHostInfo", rModel);
		}
		sqlSession.commit();
		sqlSession.close();
	}
	
	//HDFS 성능 등록
	public static void insertHdfsOverall (ResponseModel model)  throws Exception{
		SqlSession sqlSession = SqlMapSessionFactory.getSqlSession();
		sqlSession.insert("testSQL.insertHdfsOverall", model);
		sqlSession.commit();
		sqlSession.close();
	}
	
	//HDFS node상태 등록
	public static void insertNodeState (ResponseModel model)  throws Exception{
		SqlSession sqlSession = SqlMapSessionFactory.getSqlSession();
		sqlSession.insert("testSQL.insertNodeState", model);
		sqlSession.commit();
		sqlSession.close();
	}
	
	//HDFS 호스트 컴포넌트 등록
	public static void insertHostRole (List<ResponseModel> list)  throws Exception{
		SqlSession sqlSession = SqlMapSessionFactory.getSqlSession();
		for(ResponseModel rModel : list) {
			sqlSession.insert("testSQL.insertHostRole", rModel);
		}
		
		sqlSession.commit();
		sqlSession.close();
	}
	
}
