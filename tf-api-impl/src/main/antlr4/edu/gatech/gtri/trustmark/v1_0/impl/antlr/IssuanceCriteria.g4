grammar IssuanceCriteria;
/*@header {
	package edu.gatech.gtri.trustmark.v1_0.impl.antlr;
}*/

//======================================================================================================================
//  PARSER RULES
//======================================================================================================================
issuanceCriteriaExpression : expression EOF;

expression
 : expression AND_OR expression
 | LPAREN expression RPAREN
 | NOT expression
 | predicate;


predicate
 : LOGIC_OP LPAREN STEP_ID RPAREN
 | LOGIC_OP LPAREN STEP_ID_SEQUENCE RPAREN
 | LOGIC_OP LPAREN STEP_ID_SET RPAREN;

//======================================================================================================================
//  LEXER RULES
//======================================================================================================================
NOT
 : LOWER_NOT
 | UPPER_NOT
 | BANG;

AND_OR
 : AND | OR;

LOGIC_OP
 : YES | NO | NA;

STEP_ID_SEQUENCE : STEP_ID '...' STEP_ID;
STEP_ID_SET : STEP_ID (WS* ',' WS* STEP_ID)+;
STEP_ID     : NAMECHAR+;
YES         : 'yes'|'YES';
NO          : 'no'|'NO';
NA          : 'na'|'NA';
AND         : 'and'|'AND';
OR          : 'or'|'OR';
LOWER_NOT   : 'not';
UPPER_NOT   : 'NOT';
BANG        : '!';
LPAREN      : '(';
RPAREN      : ')';
NAMECHAR    : '_' | '-' | [a-zA-Z] | [0-9];
WS	        : [ \t\r\n]+ -> skip;
