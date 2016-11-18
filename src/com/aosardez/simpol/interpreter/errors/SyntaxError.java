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

package com.aosardez.simpol.interpreter.errors;

public class SyntaxError extends Exception {
	String _token;
	String _expectedSymbols;
	int _line;
	int _column;

	public SyntaxError(String token, String expectedSymbols, int line, int column) {
		super("Syntax error encountered while processing token " + token + " at line " + line + " column " + column
				+ ": Expecting " + expectedSymbols + ".");

		_token = token;
		_expectedSymbols = expectedSymbols;
		_line = line;
		_column = column;
	}

	public String getToken() {
		return _token;
	}

	public String getExpectedSymbols() {
		return _expectedSymbols;
	}

	public int getLine() {
		return _line;
	}

	public int getColumn() {
		return _column;
	}
}
