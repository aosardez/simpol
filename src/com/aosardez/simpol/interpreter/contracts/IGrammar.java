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

import com.aosardez.simpol.interpreter.errors.InvalidDataError;
import com.aosardez.simpol.interpreter.errors.LexicalError;
import com.aosardez.simpol.interpreter.errors.RuntimeError;
import com.aosardez.simpol.interpreter.errors.SyntaxError;

public interface IGrammar extends IGrammarPattern {
	// <simpol> ::= <variable><code>
	void simpol() throws LexicalError, SyntaxError, InvalidDataError, RuntimeError;

	// <variable> ::=
	// <variableScopePattern><scopeStartPattern>{<variableDeclaration>}<scopeEndPattern>
	void variable() throws LexicalError, SyntaxError;

	// <variableDeclaration> ::= <dataTypePattern><variableNamePattern>
	void variableDeclaration() throws LexicalError, SyntaxError;

	// <code> ::=
	// <codeScopePattern><scopeStartPattern>{<statement>}<scopeEndPattern>
	void code() throws LexicalError, SyntaxError, InvalidDataError, RuntimeError;

	// <statement> ::= <inOperatorPattern><variableNamePattern>|
	// <outOperatorPattern><expression>|
	// <assignOperatorPattern><expression><assignSetOperatorPattern><variableNamePattern>
	void statement() throws LexicalError, SyntaxError, InvalidDataError, RuntimeError;

	// <expression> ::= <operatorPattern><expression><expression>|
	// <logicalNotOperatorPattern><expression>|
	// <variableNamePattern>|
	// <literalPattern>
	String expression() throws LexicalError, SyntaxError, InvalidDataError;
}
