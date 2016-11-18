/*************************************************************************
* Copyright (C) 2016 Angelito (Jojo) O. Sardez, Jr.
* This file is part of Simpol Interpreter.
*
* Simpol Interpreter is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* Simpol Interpreter is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License.
* If not, see <http://www.gnu.org/licenses/>
*/

package com.aosardez.simpol.interpreter.contracts;

public interface IGrammarPattern {
	// <variableScopePattern> ::= "variable"
	boolean variableScopePattern();

	// <codeScopePattern> ::= "code"
	boolean codeScopePattern();

	// <scopeStartPattern> ::= "{"
	boolean scopeStartPattern();

	// <scopeEndPattern> ::= "}"
	boolean scopeEndPattern();

	// <dataTypePatternPattern> ::= "INT"|"BLN"|"STG"
	boolean dataTypePattern();

	// <variableNamePattern> ::= "^[a-zA-Z]+[a-zA-Z0-9]*$"
	boolean variableNamePattern();

	// <inOperatorPattern> ::= "ASK"
	boolean inOperatorPattern();

	// <outOperatorPattern> ::= "PRT"
	boolean outOperatorPattern();

	// <assignOperatorPattern> ::= "PUT"
	boolean assignOperatorPattern();

	// <assignSetOperatorPattern> ::= "IN"
	boolean assignSetOperatorPattern();

	// <operatorPattern> ::=
	// <arithmeticOperatorPattern>|<numericPredicatePattern>|<logicalOperatorPattern>
	boolean operatorPattern();

	// <arithmeticOperatorPattern> ::= "ADD"|"SUB"|"MUL"|"DIV"|"MOD"
	boolean arithmeticOperatorPattern();

	// <numericPredicatePattern> ::= "GRT"|"GRE"|"LET"|"LEE"|"EQL"
	boolean numericPredicatePattern();

	// <logicalOperatorPattern> ::= "AND"|"OHR"
	boolean logicalOperatorPattern();

	// <logicalNotOperatorPattern> ::= "NON"
	boolean logicalNotOperatorPattern();

	// <literalPattern> ::= "^(true|false)$"|"^-?\d*\.?\d*$"|"^\$.*\$$"
	boolean literalPattern();
}
