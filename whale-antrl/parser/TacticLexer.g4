lexer grammar TacticLexer;

//Used for when generating:
@lexer::header {
    package gen;
}

//Keywords
LPAREN      :       '(';
RPAREN      :       ')';
LCURLY      :       '{';
RCURLY      :       '}';
LBRACKET    :       '[';
RBRACKET    :       ']';
GAMEPIECE   :       'GamePiece';
INTEGER     :       'int';
FLOAT       :       'float';
VEC         :       'vector';
BOOL        :       'bool';
SEPERATOR   :       ',';
IF          :       'if';
ELSEIF      :       'elseif';
ELSE        :       'else';
TRUE        :       'true';
FALSE       :       'false';
WHILE       :       'while';
DOT         :       '.';
STRING_MARK :       '"';
STRING      :       'string';
VOID        :       'void';


// Literals
DIGIT           : ('0'..'9') ;
LETTER          : [a-z] | [A-Z] | [_] ;
WORD            : LETTER LETTER* ;
STRINGTEXT      : '"' ~('\r' | '\n' | '"')* '"' ;
NUMBER          : DIGIT+;
ASSIGN          : '='  ;
ADDITION        : '+'  ;
SUBTRACTION     : '-'  ;
DIVISION        : '/'  ;
MULTIPLY        : '*' ;
MODULO          : '%' ;
ENDSTMT         : ';'  ;
BOOL_EQUAL      : '==' ;
BOOL_N_EQUAL    : '!=' ;
BOOL_COND_AND   : '&&' ;
BOOL_COND_OR    : '||' ;
BOOL_LESS       : '<' ;
BOOL_GREATER            : '>' ;
BOOL_LESS_OR_EQUAL      : '<=' ;
BOOL_GREATER_OR_EQUAL   : '>=' ;
BOOL_NEGATION   : '!';

// Whitespace and comments
WS              : [ \t\r\n]+    -> channel(HIDDEN);
COMMENT         : '/*' .*? '*/' -> skip;
LINE_COMMENT    : DIVISION'/' ~[\r\n]* -> skip;

