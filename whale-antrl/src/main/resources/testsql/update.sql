--UPDATE tablename SET column = value [, column = value ...] [WHERE expression]




UPDATE TESTTABLENEW SET NAME=222 WHERE ID=1;






--建表时需要指定TBLPROPERTIES(transactional)

set hive.auto.convert.join.noconditionaltask.size = 10000000;
set hive.support.concurrency = true;
set hive.enforce.bucketing = true;  ---215目前不支持
set hive.exec.dynamic.partition.mode = nonstrict;
set hive.txn.manager = org.apache.hadoop.hive.ql.lockmgr.DbTxnManager;
set hive.compactor.initiator.on = true;

--不能updtae bucket的列值
create table testTableNew(id int ,name string ) clustered by (id) into 2 buckets stored as orc TBLPROPERTIES('transactional'='true');