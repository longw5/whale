grammar SelectSQL;

/*
 * Parser Rules
 */
@header {
package org.whale.antrl.sql;
}

compileUnit
    :    start
    ;

/*
 * Lexer Rules
 */
WS
    :    [ \t\n\r]+ -> skip
    ;

COMMA:',';
SELECT: 'SELECT';
STAR:'*';
FROM:'FROM';
WHERE:'WHERE';
ORDERBY:'ORDER BY';
DIRECTION:'ASC'|'DESC';
CHAR: 'a'..'z'|'A'..'Z';
NUM: '0'..'9';
STRING:'\'' .*? '\'';
LB:'(';
RB:')';
LBRACE:'[';
RBRACE:']';
CONDITIONS_OPERATOR    
    :'AND'
    |'OR'
    ;
CONDITION_OPERATOR    
    :'='
    |'>'
    |'<'
    |'<>'
    |'!='
    |'>='
    |'<='
    ;
FCOUNT:'COUNT';


start
    :statement_list
    ;

statement_list
    :statement statement*
    ;

statement
    :selectStatement
    ;


selectStatement
    :selectStmt fromStmt whereStmt? orderbyStmt?
    ;

selectStmt
    :SELECT columns
    ;

columns
    :column (COMMA column)*
    ;

column
    : identifier
    | LBRACE identifier RBRACE
    | functionStmt
    | STAR
    ;

functionStmt
    :function LB (parameters) RB
    ;
    
function
    :FCOUNT
    ;

parameters
    : parameter (COMMA parameter)*
    ;

parameter
    : identifier
    | integer
    | string
    | STAR
    ;

fromStmt
    :FROM table
    ;

table
    : identifier
    | LBRACE identifier RBRACE
    ;


whereStmt
    : WHERE conditions
    ;
    
conditions
    : condition (CONDITIONS_OPERATOR condition)* 
    ;

condition
    :left CONDITION_OPERATOR right
    ;
    
left
    : parameter
    ;
    
right
    : parameter
    ;
    
orderbyStmt
    :ORDERBY sortStmt
    ;

sortStmt
    : sortCondition (COMMA sortCondition)*
    ;
    
sortCondition
    :sortColumn DIRECTION
    ;

sortColumn
    : identifier
    | LBRACE identifier RBRACE
    ;

identifier
    :CHAR (CHAR|NUM)*
    ;
integer
    :NUM+
    ;
string
    : STRING
    ;