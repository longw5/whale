package org.whale.data;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MemoryData {

	public static final Map<String, Database> MAP = new HashMap<String, Database>();

	public static void main(String[] args) {
		System.out.println(MAP);
	}

	static {

		Database school = new Database();
		
		Table company = new Table();
		company.name = "COMPANY";

		Column companyid = new Column();
		companyid.name = "companyId";
		companyid.type = "varchar";
		companyid.isIndex = true;

		Column companyName = new Column();
		companyName.name = "companyName";
		companyName.type = "varchar";
		companyName.isIndex = true;

		Column sortNo = new Column();
		sortNo.name = "companyNo";
		sortNo.type = "varchar";
		sortNo.isIndex = false;

		company.columns.add(companyid);
		company.columns.add(companyName);
		company.columns.add(sortNo);

		String[] split1 = "123 aaa 1".split(" ");
		String[] split2 = "133 bbb 2".split(" ");
		String[] split3 = "145 ccc 3".split(" ");

		company.data.add(Arrays.asList(split1));
		company.data.add(Arrays.asList(split2));
		company.data.add(Arrays.asList(split3));

		Table dept = new Table();

		dept.name = "DEPT";

		Column deptid = new Column();
		deptid.name = "deptId";
		deptid.type = "varchar";
		deptid.isIndex = true;

		Column deptName = new Column();
		deptName.name = "deptName";
		deptName.type = "varchar";
		deptName.isIndex = true;

		Column deptNo = new Column();
		deptNo.name = "deptNo";
		deptNo.type = "varchar";
		deptNo.isIndex = false;

		dept.columns.add(deptid);
		dept.columns.add(deptName);
		dept.columns.add(deptNo);

		String[] split11 = "x1c xxx 88".split(" ");
		String[] split22 = "x2b aaa 21".split(" ");
		String[] split33 = "x9k ppp 35".split(" ");

		dept.data.add(Arrays.asList(split11));
		dept.data.add(Arrays.asList(split22));
		dept.data.add(Arrays.asList(split33));

		school.tables.add(company);
		school.tables.add(dept);
		MAP.put("school", school);
	}

	public static class Database {
		public LinkedList<Table> tables = new LinkedList<Table>();

		@Override
		public String toString() {
			return "Database [tables=" + tables + "]";
		}
	}

	public static class Table {
		public String name;
		public LinkedList<Column> columns = new LinkedList<Column>();
		public LinkedList<List<String>> data = new LinkedList<List<String>>();

		@Override
		public String toString() {
			return "Table [name=" + name + ", columns=" + columns + ", data=" + data + "]";
		}
	}

	public static class Column {
		public String name;
		public String type;
		public Boolean isIndex;

		@Override
		public String toString() {
			return "Column [name=" + name + ", type=" + type + ", isIndex=" + isIndex + "]";
		}
	}

}
