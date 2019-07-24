备注：
Hubble数据库支持多源融合，分3层结构：catalog/database/table
Hive只支持2层：database/table
所以使用Hubble库 Hubble语法：use hive.database;  Hive语法：user database;
如果不指定库名，直接到表明，执行Sql的语法：
	Hubble ：select * from hive.database.table;
	Hive：select * from database.table;
关于此类差异，下面不在进行另行对比。

下面表操作：
	Hubble默使用认 : use hive.database;只操作表
	Hive默认使用 : user database；只操作表
