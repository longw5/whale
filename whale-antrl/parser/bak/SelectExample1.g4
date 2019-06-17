grammar SelectExample1;

@header {
package org.whale.antrl.sql;
}

sql : 'select' WHAT 'from' tables ';';
WHAT : [a-z]+ ;
tables : WHAT;
WS : [ \t\r\n]+ -> skip ;