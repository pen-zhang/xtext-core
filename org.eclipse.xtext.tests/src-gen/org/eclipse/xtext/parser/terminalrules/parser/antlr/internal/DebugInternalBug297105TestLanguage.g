/*
 * generated by Xtext
 */
grammar DebugInternalBug297105TestLanguage ;

// Rule Model
ruleModel :
	ruleExpression*
;

// Rule Expression
ruleExpression :
	ruleLiteral '+' ruleLiteral
;

// Rule Literal
ruleLiteral :
	ruleRealLiteral |
	ruleIntLiteral
;

// Rule IntLiteral
ruleIntLiteral :
	ruleIntValue
;

// Rule RealLiteral
ruleRealLiteral :
	ruleRealValue
;

// Rule IntValue
ruleIntValue :
	RULE_INT
;

// Rule RealValue
ruleRealValue :
	ruleReal
;

// Rule Real
ruleReal :
	RULE_INT? '.' (
		RULE_EXT_INT |
		RULE_INT
	)
;

RULE_EXT_INT :
	RULE_INT (
		'e' |
		'E'
	) (
		'-' |
		'+'
	) RULE_INT
;

RULE_ID :
	'^'? (
		'a' .. 'z' |
		'A' .. 'Z' |
		'_'
	) (
		'a' .. 'z' |
		'A' .. 'Z' |
		'_' |
		'0' .. '9'
	)*
;

RULE_INT :
	'0' .. '9'+
;

RULE_STRING :
	'"' (
		'\\' . |
		~ (
			'\\' |
			'"'
		)
	)* '"' |
	'\'' (
		'\\' . |
		~ (
			'\\' |
			'\''
		)
	)* '\''
;

RULE_ML_COMMENT :
	'/*' (
		options { greedy = false ; } : .
	)* '*/' { skip(); }
;

RULE_SL_COMMENT :
	'//' ~ (
		'\n' |
		'\r'
	)* (
		'\r'? '\n'
	)? { skip(); }
;

RULE_WS :
	(
		' ' |
		'\t' |
		'\r' |
		'\n'
	)+ { skip(); }
;

RULE_ANY_OTHER :
	.
;