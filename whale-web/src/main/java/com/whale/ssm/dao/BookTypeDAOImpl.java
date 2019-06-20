package com.whale.ssm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.whale.ssm.entities.BS_SNO_BOC_20190618;
import com.whale.ssm.entities.BookType;
import com.whale.ssm.mapping.BookTypeDAO;

/**
 * 实现图书类型数据访问
 *
 */
public class BookTypeDAOImpl implements BookTypeDAO {

	@Override
	public List<BookType> getAllBookTypes() {
		//获得会话对象
		SqlSession session=MyBatisUtil.getSession();
		try {
			//通过MyBatis实现接口BookTypeDAO，返回实例
			BookTypeDAO bookTypeDAO=session.getMapper(BookTypeDAO.class);
			return bookTypeDAO.getAllBookTypes();
		} finally {
			session.close();
		}
	}
	@Override
    public List<BookType> queryBookTypesBySql(int currPage, int pageSize) {
		//获得会话对象
	      SqlSession session=MyBatisUtil.getSession();
		   try {
		       BookTypeDAO bookTypeDAO=session.getMapper(BookTypeDAO.class);
               return bookTypeDAO.queryBookTypesBySql(currPage, pageSize);
			} finally {
				session.close();
		 }
    }

	@Override
	public int add(BookType entity) {
		return 0;
	}
	@Override
	public int add100(String sql) {

		//获得会话对象
		SqlSession session=MyBatisUtil.getSession();
		try {
			//通过MyBatis实现接口BookTypeDAO，返回实例
			BookTypeDAO bookTypeDAO=session.getMapper(BookTypeDAO.class);
			return bookTypeDAO.add100(sql);
		} finally {
			session.close();
		}
	}
	@Override
	public int insertList(List<BookType> bookTypeList) {

		//获得会话对象
		SqlSession session=MyBatisUtil.getSession();
		try {
			//通过MyBatis实现接口BookTypeDAO，返回实例
			BookTypeDAO bookTypeDAO=session.getMapper(BookTypeDAO.class);
			return bookTypeDAO.insertList(bookTypeList);
		} finally {
			session.close();
		}
	}
	@Override
	public int insertBsList(List<BS_SNO_BOC_20190618> bss) {
		
		//获得会话对象
		SqlSession session=MyBatisUtil.getSession();
		try {
			//通过MyBatis实现接口BookTypeDAO，返回实例
			BookTypeDAO bookTypeDAO=session.getMapper(BookTypeDAO.class);
			return bookTypeDAO.insertBsList(bss);
		} finally {
			session.close();
		}
	}

}
