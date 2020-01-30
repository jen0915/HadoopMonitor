package com.idpp.hadoop.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.ibatis.session.SqlSession;

import com.idpp.hadoop.dto.DiskInfoVO;
import com.idpp.hadoop.dto.ResponseModel;

public class TestDAO {

	static SqlSession sess = SqlMapSessionFactory.getSqlSessionFactory().openSession(true);
	
	
	
	//호스트정보 등록
	public static void insertAmbariHostInfo(List<ResponseModel> statelist) throws Exception{
//		SqlSession sqlSession = SqlMapSessionFactory.getSqlSession();
//		for(ResponseModel rModel : statelist) {
//			sqlSession.insert("testSQL.insertAmbariHostInfo", rModel);
//		}
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("statelist", statelist);
			sess.insert("testSQL.insertAmbariHostInfo", map);
			//sqlSession.commit();

		}catch(Exception e){
			e.printStackTrace();
		}finally {
			sess.close();
		}
	}
	
	//HDFS 성능 등록
	public static void insertHdfsOverall (ResponseModel model)  throws Exception{
//		SqlSession sqlSession = SqlMapSessionFactory.getSqlSession();
		try {

			sess.insert("testSQL.insertHdfsOverall", model);
			//sqlSession.commit();

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			sess.close();
		}
	}
	
	//HDFS node상태 등록
	public static void insertNodeState (ResponseModel model)  throws Exception{
//		SqlSession sqlSession = SqlMapSessionFactory.getSqlSession();
		try {
			sess.insert("testSQL.insertNodeState", model);
			//sqlSession.commit();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			sess.close();
		}
	}
	
	//HDFS 호스트 컴포넌트 등록
	public static void insertHostRole (List<ResponseModel> list)  throws Exception{
//		SqlSession sqlSession = SqlMapSessionFactory.getSqlSession();
//		for(ResponseModel rModel : list) {
//			sqlSession.insert("testSQL.insertHostRole", rModel);
//		}
		try {
			Map<String, Object> rmap = new HashMap<String, Object>();
			rmap.put("list", list);
			sess.insert("testSQL.insertHostRole", rmap);
			//sqlSession.commit();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			sess.close();
		}
	}

	//암바리 호스트 리스트 상태정보 등록
	public static void InsertHostStateInfo(List<ResponseModel> statelist) {
		SqlSession sqlSession = SqlMapSessionFactory.getSqlSessionFactory().openSession(true);
		try {
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("list", statelist);
			for(ResponseModel model : statelist) {
				sqlSession.insert("testSQL.insertHostState", model);
			}
//			sqlSession.insert("testSQL.insertHostState", map);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			sqlSession.close();
		}
	}

	//암바리 호스트 리스트 CPU,MEM,DISK정보 등록
	public static void insertHostPerformInfo(List<ResponseModel> performlist) {
		SqlSession session = SqlMapSessionFactory.getSqlSessionFactory().openSession(true);
		try {
//			Map<String, Object> pmap = new HashMap<String, Object>();
//			pmap.put("performlist", performlist);
			for(ResponseModel model : performlist) {
				session.insert("testSQL.insertHostPerform", model);
			}
//			sqlSession.insert("testSQL.insertHostPerform", pmap);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
	}

	public static void insertLakeDeviceList(List<Map<String, Object>> perDisk) {

		SqlSession sqlSession = SqlMapSessionFactory.getSqlSessionFactory().openSession(true);
		try {
			for(int i=0; i<perDisk.size(); i++) {
				List<DiskInfoVO> str = new ArrayList<DiskInfoVO>();
				
				for(Entry<String, Object> elem : perDisk.get(i).entrySet()) {
					System.out.println("키 : " + elem.getKey());
					
					List list = new ArrayList();
					list.add(elem.getValue());
					Iterator<List<DiskInfoVO>> itr = list.iterator();
					
						str = itr.next();
						System.out.println(str.size());
				}
				
				for(DiskInfoVO inputDisk : str) {
					sqlSession.insert("testSQL.insertLakeDiskInfo", inputDisk);
//					System.out.println("==============================");
//					  System.out.println(inputDisk.getSERVER_NM());
//					  System.out.println(inputDisk.getDISK_NM());
//					  System.out.println(inputDisk.getDISK_AVAILABLE());
//					  System.out.println(inputDisk.getDISK_USED());
//					  System.out.println(inputDisk.getDISK_PERCENT());
//					  System.out.println(inputDisk.getDISK_TOTAL());
//					  System.out.println("==============================");
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			sqlSession.close();
		}
	}
	
}
