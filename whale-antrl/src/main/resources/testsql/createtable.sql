--HIVE创建表

--创建临时表
CREATE TEMPORARY TABLE TEST12 AS SELECT * FROM TEST.T_TDR;

--创建各种格式的表
CREATE TABLE HIVE.TEST
(
   ROWID INTEGER,
   NAME VARCHAR,
   AGE INTEGER,
   GENDER VARCHAR
)
ROW FORMAT DELIMITED FIELDS TERMINATED BY ','
STORED AS TEXTFILE/ORC/PARQUET/AVRO/RCFILE;

--映射其他数据源表
--STORED BY语法



--HUBBLE创建各种格式的表
CREATE TABLE HIVE.TEST.TEST_ORC (
   ROWID INTEGER,
   NAME VARCHAR,
   AGE INTEGER,
   GENDER VARCHAR
)
WITH (
   FORMAT = 'TEXTFILE/ORC/PARQUET/AVRO'
);