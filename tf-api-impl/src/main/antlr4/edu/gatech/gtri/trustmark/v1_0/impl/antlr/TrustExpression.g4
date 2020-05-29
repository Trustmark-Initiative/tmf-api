grammar TrustExpression;
/*@header {
	package edu.gatech.gtri.trustmark.v1_0.impl.antlr;
}*/

trustExpression	: expression EOF;

expression : orExpression;
orExpression	: andExpression (OR andExpression)*;
andExpression	: nestedExpression (AND nestedExpression)*
                | operatorExpression (AND operatorExpression)*;
operatorExpression : nestedExpression WS? OPERATOR WS? nestedExpression;
nestedExpression	: LPAREN orExpression RPAREN
                    | fieldReference
                    | function
                    | constant;
function    :   func=(IDENTIFIER | '!') LPAREN arg (COMMA arg)* RPAREN;
arg :   constant
    |   expression;
fieldReference : identifier=IDENTIFIER INFERENCE_OP field=IDENTIFIER
               | identifier=REF_IDENTIFIER
               | identifier=URL_IDENTIFIER
               | identifier=IDENTIFIER;
constant       : STRING
               | NUMBER
               | BOOLEAN;

//======================================================================================================================
//  CONSTANTS & SIMPLE REGEX EXPRESSIONS  LEXER
//======================================================================================================================
AND		: 'and' | 'AND';
OR		: 'or' | 'OR';
BOOLEAN: 'true' | 'TRUE' | 'false' | 'FALSE';
LPAREN		: '(';
RPAREN		: ')';
UNDERSCORE	: '_';
LCURLY		: '{';
RCURLY		: '}';
DASH		: '-';
LETTER		: [a-zA-Z];
DIGIT		: [0-9];
OPERATOR    : '==' | '!=' | '<' | '>' | '<=' | '>=';
DOUBLE_QUOTE: '"';
SINGLE_QUOTE: '\'';
INFERENCE_OP: '.';
WS		: [ \t\r\n]+ -> skip;
STRING
 : '"' (~[\r\n"] | '\\"')* '"' | '\'' (~[\r\n\'] | '\\\'')* '\''
 ;
COMMA : ',';
COLON : ':';
QUESTION : '?';
SLASH : '/';
NUMBER
 : '-'? [0-9]+ ('.' [0-9]+)?
 ;
PHRASE: (NAMECHAR | WS | DASH);
URL_PATTERN: (NAMECHAR | COLON | SLASH | DASH | INFERENCE_OP | QUESTION | WS);

IDENTIFIER	: (LETTER | UNDERSCORE) (NAMECHAR)*;
REF_IDENTIFIER : ('TD' | 'TIP') LCURLY (PHRASE)* RCURLY;
URL_IDENTIFIER : ('TD' | 'TIP') LCURLY (URL_PATTERN)* RCURLY;
//PARAM_IDENTIFIER : (NAMECHAR)* INFERENCE_OP (NAMECHAR)* WS? OPERATOR WS? (NAMECHAR)*;

fragment NAMECHAR : LETTER
	 	  | DIGIT
		  | UNDERSCORE;
