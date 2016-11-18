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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InterpreterTables {
	private static InterpreterTables _instance;
	private List<TokenTableItem> _tokenTable;
	private Map _symbolTable;

	private InterpreterTables() {
		_tokenTable = new ArrayList<TokenTableItem>();
		_symbolTable = new HashMap<String, SymbolTableItem>();
	}

	public static InterpreterTables getInstance() {
		if (_instance == null) {
			_instance = new InterpreterTables();
		}
		return _instance;
	}

	public void clear() {
		_tokenTable.clear();
		_symbolTable.clear();
	}

	public List<TokenTableItem> getTokenTable() {
		return _tokenTable;
	}

	public Map getSymbolTable() {
		return _symbolTable;
	}
}
