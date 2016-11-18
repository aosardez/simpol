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

package com.aosardez.simpol.interpreter.entities;

import com.aosardez.simpol.interpreter.entities.Token;

public class TokenTableItem {
	private Token _token;
	private String _lexeme;
	private int _line;
	private int _column;

	public TokenTableItem(Token token, String lexeme, int line, int column) {
		_token = token;
		_lexeme = lexeme;
		_line = line;
		_column = column;
	}

	public Token getToken() {
		return _token;
	}

	public String GetLexeme() {
		return _lexeme;
	}

	public int GetLine() {
		return _line;
	}

	public int GetColumn() {
		return _column;
	}
}
