package com.whale.ssm.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whale.ssm.entities.BS_SNO_BOC_20190618;
import com.whale.ssm.entities.BookType;
import com.whale.ssm.mapping.BookTypeDAO;

/*
 * 图书类型服务
 */
@Service
public class BookTypeService {

	@Resource
	BookTypeDAO bookTypeDAO;

	public List<BookType> getAllBookTypes() {
		return bookTypeDAO.getAllBookTypes();
	}
	
	public List<BookType> queryBookTypesBySql(int currPage, int pageSize) {
		return bookTypeDAO.queryBookTypesBySql(currPage, pageSize);
	}
	
	@Transactional
	public int addDouble(BookType entity1,BookType entity2){
		int rows=0;
		rows+=bookTypeDAO.add(entity1);
		rows+=bookTypeDAO.add(entity2);
		return rows;
	}
	
	@Transactional
	public int add100(String sql){
		int rows = bookTypeDAO.add100(sql);
		return rows;
	}
	
	@Transactional
	public int insertList(List<BookType> bookTypeList){
		return bookTypeDAO.insertList(bookTypeList);
	}

	public int insertBsList(List<BS_SNO_BOC_20190618> bss) {
		return bookTypeDAO.insertBsList(bss);
	}
	
}
