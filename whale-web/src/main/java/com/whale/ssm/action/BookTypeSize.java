package com.whale.ssm.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.whale.ssm.entities.BookType;
import com.whale.ssm.service.BookTypeService;

@WebServlet("/BookTypeSize.do")
public class BookTypeSize extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	 BookTypeService bookTypeService;
	 
	@Override
	public void init() throws ServletException {
	  //从容器中获得bean
	  bookTypeService=CtxUtil.getBean(BookTypeService.class);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer=response.getWriter();
		List<BookType> queryBookTypesBySql = bookTypeService.queryBookTypesBySql(0, 1);
		
		for (BookType bookType : queryBookTypesBySql) {
			
			System.out.println(bookType.toString());
		}
		writer.print(bookTypeService);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
