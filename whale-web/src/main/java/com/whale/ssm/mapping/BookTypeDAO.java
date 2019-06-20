package com.whale.ssm.mapping;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.whale.ssm.entities.BS_SNO_BOC_20190618;
import com.whale.ssm.entities.BookType;

/**
 * 图书类型数据访问接口
 *
 */
public interface BookTypeDAO {

	public List<BookType> getAllBookTypes();
	
	List<BookType> queryBookTypesBySql(int currPage, int pageSize);
	
	public int add(BookType entity);
	
	public int add100(String sql);
	
	public int insertList(@Param("bookTypeList") List<BookType> bookTypeList);

	public int insertBsList(@Param("bssList") List<BS_SNO_BOC_20190618> bss);
}
