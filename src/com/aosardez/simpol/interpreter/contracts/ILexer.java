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

import com.aosardez.simpol.interpreter.entities.OperatorType;
import com.aosardez.simpol.interpreter.entities.SymbolTableItem;
import com.aosardez.simpol.interpreter.entities.Token;
import com.aosardez.simpol.interpreter.errors.LexicalError;

public interface ILexer {
	void lex() throws LexicalError;

	String getLexeme();

	Token getToken();

	SymbolTableItem getLastVariable();

	OperatorType getLastOperatorType();

	String getText();

	int getLine();

	int getColumn();

	boolean isEndOfFile();
}
