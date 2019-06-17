parser grammar Tactic;

//Used for when generating:
@header {
    package gen;
}

options { tokenVocab = TacticLexer; }

prog    : (dcl ENDSTMT)* (stmt ENDSTMT)* ENDSTMT;
stmt    : dotAssignment | arrayAssign | procedureCall | condStmt | whileStmt | assignment;
dcl     : intDcl | boolDcl | arrayDcl | stringDcl | gpDcl | floatDcl | vecDcl | procedureDef;

integer         : NUMBER | DIGIT ;
floatVal        : (NUMBER | DIGIT) DOT (NUMBER | DIGIT);
number          : integer | floatVal ;
word            : WORD | LETTER ;
string          : STRINGTEXT ;
identifier      : word (WORD|NUMBER|DIGIT|LETTER)*;
value           : (number | bool | string | vec) | (LPAREN (number | bool | string | vec) RPAREN) | identifier;
vec             : LPAREN vecPara SEPERATOR vecPara (SEPERATOR vecPara)? RPAREN;
type            : INTEGER | FLOAT | VEC | BOOL | STRING | GAMEPIECE ;

procedureCall   : identifier LPAREN arguments? RPAREN;
procedureDef    : identifier LPAREN (type(LBRACKET RBRACKET)* identifier (SEPERATOR type (LBRACKET RBRACKET)* identifier)*)? RPAREN procedureBlock;
procedureBlock  : LCURLY (stmt ENDSTMT)* RCURLY;

dotStmt         : identifier ((DOT identifier(LBRACKET number? RBRACKET)*))+ ;
dotAssignment   : dotStmt ASSIGN value;

//Declaration
intDcl      : INTEGER identifier;
floatDcl    : FLOAT identifier;
vecDcl      : VEC identifier;
boolDcl     : BOOL identifier;
stringDcl   : STRING identifier;
gpDcl       : GAMEPIECE identifier;
arrayDcl    : type (LBRACKET integer RBRACKET)+ identifier;

assignment  : (identifier | dotStmt) (LBRACKET integer RBRACKET)* ASSIGN (value | arithExpr | boolExpr | vecExpr | (identifier (LBRACKET integer RBRACKET)*) | dotStmt);

//Datastructure operations
arrayExpr   : boolExpr | arithExpr | gpDcl | identifier | dotStmt | arrayDcl | value | vec | floatDcl | vecExpr ;
arrayAssign : identifier (((LBRACKET number RBRACKET)+ ASSIGN arrayExpr) | (LBRACKET RBRACKET ASSIGN LCURLY (arrayExpr(SEPERATOR arrayExpr)*) RCURLY));


//Arithmetic operations
arithExpr : arithExprParent | arithExprMiddle ;
arithExprParent : arithExprRight? arithExprParenthMiddle (arithExprBoth arithExprParenthMiddle)* arithExprLeft? ;
arithExprParenthMiddle : LPAREN arithExpr RPAREN ;
arithExprLeft : (arithAction (identifier | number))+ ; //Open left: + 2 + 2 +2
arithExprRight : ((identifier | number) arithAction)+ ; //Open right: 2 + 2 + 2 +
arithExprMiddle : (identifier | number) (arithAction (identifier | number))+; //Not open: 2 + 2 + 2
arithExprBoth : arithAction ((identifier | number) arithAction)* ;
arithAction : ADDITION | SUBTRACTION | DIVISION | MULTIPLY | MODULO ;


vecExpr    : (vecAdd | vecSub) ((ADDITION | SUBTRACTION) (identifier | vec))* ;
vecAdd     : (identifier | vec) ADDITION (identifier | vec) ;
vecSub     : (identifier | vec) SUBTRACTION (identifier | vec) ;
vecPara         : identifier | integer | arithExpr ;

arguments       : value | arguments SEPERATOR arguments ;

//Control structures
condStmt        : ifStmt elseStmt? ;
block           : LCURLY (stmt ENDSTMT)* RCURLY ;
ifStmt          : IF LPAREN (boolExpr) RPAREN  block ;
elseStmt        : ELSE block ;

whileStmt       : WHILE LPAREN boolExpr RPAREN block ;

//Conditional
boolExpr        : (value boolOperaters value | bool | identifier);
bool            : (TRUE | FALSE) ;
boolOperaters   : (BOOL_EQUAL | BOOL_N_EQUAL | BOOL_COND_AND | BOOL_COND_OR
                | BOOL_LESS | BOOL_GREATER | BOOL_LESS_OR_EQUAL | BOOL_GREATER_OR_EQUAL) ;
