--DELETE FROM TABLENAME [WHERE EXPRESSION]

DELETE FROM TESTTABLENEW WHERE ID=1;






--建表时需要指定TBLPROPERTIES(TRANSACTIONAL)

set hive.auto.convert.join.noconditionaltask.size = 10000000;
set hive.support.concurrency = true;
set hive.enforce.bucketing = true;  ---215目前不支持
set hive.exec.dynamic.partition.mode = nonstrict;
set hive.txn.manager = org.apache.hadoop.hive.ql.lockmgr.DbTxnManager;
set hive.compactor.initiator.on = true;

--不能UPDTAE BUCKET的列值
CREATE TABLE TESTTABLENEW(ID INT ,NAME STRING ) CLUSTERED BY (ID) INTO 2 BUCKETS STORED AS ORC TBLPROPERTIES('TRANSACTIONAL'='TRUE');







