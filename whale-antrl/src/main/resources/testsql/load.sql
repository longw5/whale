--加载表
LOAD DATA INPATH '/DATA' INTO TABLE TEST.TEST;

LOAD DATA LOCAL INPATH '/DATA' INTO TABLE TEST.TEST;


--加载表某分区
LOAD DATA INPATH '/TEST' INTO TABLE TEST PARTITION(DT='2018-02');

LOAD DATA LOCAL INPATH '/TEST' INTO TABLE TEST PARTITION(DT='2018-02');