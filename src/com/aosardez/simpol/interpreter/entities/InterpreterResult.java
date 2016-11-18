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
import java.util.List;

public class InterpreterResult {
	private boolean _successful;
	private Throwable _error;
	private List<TokenTableItem> _tokenTable;
	private List<SymbolTableItem> _symbolTable;

	public InterpreterResult(boolean successful, Throwable error, InterpreterTables tables) {
		_successful = successful;
		_error = error;
		_tokenTable = tables.getTokenTable();
		_symbolTable = new ArrayList(tables.getSymbolTable().values());
	}

	public boolean getSuccessful() {
		return _successful;
	}

	public Throwable getError() {
		return _error;
	}

	public List<TokenTableItem> getTokenTable() {
		return _tokenTable;
	}

	public List<SymbolTableItem> getSymbolTable() {
		return _symbolTable;
	}
}
