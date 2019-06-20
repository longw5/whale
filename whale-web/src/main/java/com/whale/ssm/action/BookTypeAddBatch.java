package com.whale.ssm.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

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

@WebServlet("/BookTypeAddBatch.do")
public class BookTypeAddBatch extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	 BookTypeService bookTypeService;
	 
	@Override
	public void init() throws ServletException {
	  //从容器中获得bean
	  bookTypeService=CtxUtil.getBean(BookTypeService.class);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		List<BookType> bookTypes = new ArrayList<BookType>();
		
		for (int i = 0; i < 200; i++) {
			
			BookType bookType = new BookType(new Random().nextInt(10000),UUID.randomUUID().toString());
			bookTypes.add(bookType);
		}
		bookTypeService.insertList(bookTypes);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
