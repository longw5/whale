package com.whale.ssm.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
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

import com.whale.ssm.entities.BS_SNO_BOC_20190618;
import com.whale.ssm.entities.BookType;
import com.whale.ssm.service.BookTypeService;

@WebServlet("/BookTypeAddJuLong.do")
public class BookTypeAddJuLong extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	 BookTypeService bookTypeService;
	 
	@Override
	public void init() throws ServletException {
	  //从容器中获得bean
	  bookTypeService=CtxUtil.getBean(BookTypeService.class);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		List<BS_SNO_BOC_20190618> bss = new ArrayList<BS_SNO_BOC_20190618>();

		Timestamp ts = new Timestamp(System.currentTimeMillis());  
		
		for (int i = 0; i < 40; i++) {
			
			BS_SNO_BOC_20190618 bs = new BS_SNO_BOC_20190618();
			
			bs.setBusi_guid("3c026e7cacd544d8b30087e1d2eb9f7b");
			bs.setCreate_date(Timestamp.valueOf("2019-06-17 11:09:23"));
			bs.setSeri_no("123");
			bs.setReve_seri_no("321");
			bs.setDt_busi(Timestamp.valueOf("2019-06-17 11:09:23"));
			bs.setTf_flag(1);
			bs.setError_code("000000101010101000000000000010000000000000000001");
			bs.setNote_vers(100);;
			bs.setFace_value(100);
			bs.setPosi_offs(100);
			bs.setSequ_no(1);
			bs.setFsn_path("BackupFiles\\FSN10_CNY_BOC78329832214_BOC23485922341_BOC_18_10000000000000000003_20190617105010.fsn");
			bs.setFsn_no("FSN10_CNY_BOC78329832214_BOC23485922341_BOC_18_10000000000000000003_20190617105010");
			bs.setFile_type("fsn");
			bs.setNote_curr("CNY");
			bs.setBusi_type("HM");
			bs.setFinan_ins("boc");
			bs.setMach_no("2233232");
			bs.setManu_code("JL");
			bs.setMach_type("ZB_DZ_JBY");
			bs.setMachine_model("GA-QFJ10800A3");
			bs.setHard_verno("HV03");
			bs.setAuthsoft_verno("SV03");
			bs.setCommercial_code("11111111111111");
			bs.setBank_no("22222222222222");
			bs.setMach_oper_no("oper");
			
			bss.add(bs);
		}
		bookTypeService.insertBsList(bss);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
