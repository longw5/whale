package com.whale.ssm.dao;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.whale.ssm.entities.BS_SNO_BOC_20190618;
import com.whale.ssm.entities.BookType;
import com.whale.ssm.mapping.BookTypeDAO;

@Repository
public class BTDImpl implements BookTypeDAO {
	@Resource
	private SqlSession sqlSession;

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public List<BookType> queryBookTypesBySql(int currPage, int pageSize) {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
//        data.put("currIndex", (currPage-1)*pageSize);
//        data.put("pageSize", pageSize);
		  data.put("currIndex", currPage);
	      data.put("pageSize", pageSize);
		//return sqlSession.selectList(BookTypes.class.getName()+".queryBookTypesBySql", data);
		return sqlSession.selectList("com.zhangguo.Spring61.mapping.BookTypeDAO.queryBookTypesBySql",data);
	}
	@Override
	public List<BookType> getAllBookTypes() {
		return sqlSession.selectList("com.zhangguo.Spring61.mapping.BookTypeDAO.getAllBookTypes");
	}

	@Override
	public int add(BookType entity) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int add100(String sql) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertList(List<BookType> bookTypeList) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertBsList(List<BS_SNO_BOC_20190618> bss) {
		// TODO Auto-generated method stub
		return 0;
	}

}
