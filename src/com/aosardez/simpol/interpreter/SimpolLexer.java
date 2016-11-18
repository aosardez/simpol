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

package com.aosardez.simpol.interpreter;

import com.aosardez.simpol.interpreter.contracts.IGrammarPattern;
import com.aosardez.simpol.interpreter.contracts.ILexer;
import com.aosardez.simpol.interpreter.entities.DataType;
import com.aosardez.simpol.interpreter.entities.InterpreterTables;
import com.aosardez.simpol.interpreter.entities.OperatorType;
import com.aosardez.simpol.interpreter.entities.SymbolTableItem;
import com.aosardez.simpol.interpreter.entities.Token;
import com.aosardez.simpol.interpreter.entities.TokenTableItem;
import com.aosardez.simpol.interpreter.errors.LexicalError;

public class SimpolLexer implements ILexer, IGrammarPattern {
	String _lexeme;
	Token _token;
	Token _lastToken;
	SymbolTableItem _variable;
	OperatorType _operatorType;
	String _text;
	int _line;
	int _nextLine;
	int _column;
	int _nextColumn;
	boolean _endOfFile;
	int _nextIndex;

	// region ILexer and SimpolLexer implementations

	public SimpolLexer(String text) {
		_variable = null;
		_text = text;
		_line = 1;
		_column = 1;
		_nextLine = 1;
		_nextColumn = 1;
		_nextIndex = 0;
	}

	@Override
	public void lex() throws LexicalError {
		parseLexeme();
		setToken();
		addToTokenTable();
	}

	@Override
	public String getLexeme() {
		return _lexeme;
	}

	@Override
	public Token getToken() {
		return _token;
	}

	@Override
	public SymbolTableItem getLastVariable() {
		return _variable;
	}

	@Override
	public OperatorType getLastOperatorType() {
		return _operatorType;
	}

	@Override
	public String getText() {
		return _text;
	}

	@Override
	public boolean isEndOfFile() {
		return _endOfFile;
	}

	@Override
	public int getLine() {
		return _line;
	}

	@Override
	public int getColumn() {
		return _column;
	}

	private void parseLexeme() {
		_line = _nextLine;
		_column = _nextColumn;

		_lexeme = "";
		char previousChar = '\0';
		boolean isString = false;
		boolean isStart = true;
		boolean tokenEnd = false;

		for (int index = _nextIndex; index < _text.length(); index++) {
			char c = _text.charAt(index);

			if ((c == ' ' || c == '\t' || c == '\n' || c == '\r') && !isString) {
				if (c == '\n') {
					_nextLine++;
					_nextColumn = 0;
				}
				tokenEnd = true;
			} else if (c == '$') {
				if (isStart && _lexeme.trim().length() == 0) {
					isString = true;
				}

				if (tokenEnd) {
					break;
				}

				if (!isStart && previousChar != '\\') {
					tokenEnd = true;
					isString = false;
				}
			} else if (tokenEnd) {
				break;
			}

			_lexeme += c;
			previousChar = c;
			_nextIndex++;
			_nextColumn++;
			isStart = false;
		}

		_lexeme = _lexeme.trim();

		if (_lexeme.length() == 0) {
			_endOfFile = true;
		}
	}

	private void setToken() throws LexicalError {
		_token = Token.UNKNOWN;

		if (!scopePatterns() && !dataTypePattern() && !variableNamePattern() && !inOutOperatorPatterns()
				&& !assignmentOperatorPatterns() && !operatorPattern() && !literalPattern()) {
			throw new LexicalError(_lexeme, _line, _column);
		}

		_lastToken = _token;
	}

	private void addToTokenTable() {
		if (_lexeme.trim().length() > 0) {
			InterpreterTables.getInstance().getTokenTable().add(new TokenTableItem(_token, _lexeme, _line, _column));
		}
	}

	private void addToSymbolTable(DataType type, String value) {
		InterpreterTables.getInstance().getSymbolTable().put(_lexeme, new SymbolTableItem(_lexeme, type, value));
	}

	// endregion

	// region IGrammarPattern implementations

	@Override
	public boolean variableScopePattern() {
		if (_lexeme.equals("variable")) {
			_token = Token.SCOPE_VAR;
		}
		return !_token.equals(Token.UNKNOWN);
	}

	@Override
	public boolean codeScopePattern() {
		if (_lexeme.equals("code")) {
			_token = Token.SCOPE_CODE;
		}
		return !_token.equals(Token.UNKNOWN);
	}

	@Override
	public boolean scopeStartPattern() {
		if (_lexeme.equals("{")) {
			_token = Token.SCOPE_START;
		}
		return !_token.equals(Token.UNKNOWN);
	}

	@Override
	public boolean scopeEndPattern() {
		if (_lexeme.equals("}")) {
			_token = Token.SCOPE_END;
		}
		return !_token.equals(Token.UNKNOWN);
	}

	@Override
	public boolean dataTypePattern() {
		switch (_lexeme) {
		case "INT":
			_token = Token.DECLARE_INT;
			break;
		case "BLN":
			_token = Token.DECLARE_BLN;
			break;
		case "STG":
			_token = Token.DECLARE_STG;
			break;
		}

		return !_token.equals(Token.UNKNOWN);
	}

	@Override
	public boolean variableNamePattern() {
		if (_lexeme.matches("^[a-zA-Z]+[a-zA-Z0-9]*$")) {
			switch (_lastToken) {
			case DECLARE_INT:
				_token = Token.IDENT_INT;
				addToSymbolTable(DataType.INT, "0");
				break;
			case DECLARE_BLN:
				_token = Token.IDENT_BLN;
				addToSymbolTable(DataType.BLN, "false");
				break;
			case DECLARE_STG:
				_token = Token.IDENT_STG;
				addToSymbolTable(DataType.STG, "");
				break;
			default:
				variableNamePatternInCode();
				break;
			}
		}

		return !_token.equals(Token.UNKNOWN);
	}

	@Override
	public boolean inOperatorPattern() {
		if (_lexeme.equals("ASK")) {
			_token = Token.INOUT_ASK;
		}
		return !_token.equals(Token.UNKNOWN);
	}

	@Override
	public boolean outOperatorPattern() {
		if (_lexeme.equals("PRT")) {
			_token = Token.INOUT_PRT;
		}
		return !_token.equals(Token.UNKNOWN);
	}

	@Override
	public boolean assignOperatorPattern() {
		if (_lexeme.equals("PUT")) {
			_token = Token.ASSIGN_PUT;
		}
		return !_token.equals(Token.UNKNOWN);
	}

	@Override
	public boolean assignSetOperatorPattern() {
		if (_lexeme.equals("IN")) {
			_token = Token.ASSIGN_IN;
		}
		return !_token.equals(Token.UNKNOWN);
	}

	@Override
	public boolean operatorPattern() {
		return arithmeticOperatorPattern() || numericPredicatePattern() || logicalOperatorPattern()
				|| logicalNotOperatorPattern();
	}

	@Override
	public boolean arithmeticOperatorPattern() {
		switch (_lexeme) {
		case "ADD":
			_token = Token.OPER_ADD;
			break;
		case "SUB":
			_token = Token.OPER_SUB;
			break;
		case "MUL":
			_token = Token.OPER_MUL;
			break;
		case "DIV":
			_token = Token.OPER_DIV;
			break;
		case "MOD":
			_token = Token.OPER_MOD;
			break;
		}

		if (!_token.equals(Token.UNKNOWN)) {
			_operatorType = OperatorType.Arithmetic;
		}

		return !_token.equals(Token.UNKNOWN);
	}

	@Override
	public boolean numericPredicatePattern() {
		switch (_lexeme) {
		case "GRT":
			_token = Token.COMPARE_GRT;
			break;
		case "GRE":
			_token = Token.COMPARE_GRE;
			break;
		case "LET":
			_token = Token.COMPARE_LET;
			break;
		case "LEE":
			_token = Token.COMPARE_LEE;
			break;
		case "EQL":
			_token = Token.COMPARE_EQL;
			break;
		}

		if (!_token.equals(Token.UNKNOWN)) {
			_operatorType = OperatorType.Numeric;
		}

		return !_token.equals(Token.UNKNOWN);
	}

	@Override
	public boolean logicalOperatorPattern() {
		switch (_lexeme) {
		case "AND":
			_token = Token.LOGIC_AND;
			break;
		case "OHR":
			_token = Token.LOGIC_OHR;
			break;
		}

		if (!_token.equals(Token.UNKNOWN)) {
			_operatorType = OperatorType.Logical;
		}

		return !_token.equals(Token.UNKNOWN);
	}

	@Override
	public boolean logicalNotOperatorPattern() {

		if (_lexeme.equals("NON")) {
			_token = Token.LOGIC_NON;
		}
		return !_token.equals(Token.UNKNOWN);
	}

	@Override
	public boolean literalPattern() {
		if (_lexeme.matches("^(true|false)$")) {
			_token = Token.LITERAL_BLN;
		} else if (_lexeme.matches("^-?\\d*\\.?\\d*$")) {
			_token = Token.LITERAL_INT;
		} else if (_lexeme.matches("^\\$.*\\$$")) {
			_token = Token.LITERAL_STG;
		}

		return !_token.equals(Token.UNKNOWN);
	}

	// endregion

	// region IGrammarPattern implementation helpers

	private void variableNamePatternInCode() {
		if (InterpreterTables.getInstance().getSymbolTable().containsKey(_lexeme)) {
			SymbolTableItem variable = (SymbolTableItem) InterpreterTables.getInstance().getSymbolTable().get(_lexeme);
			switch (variable.getType()) {
			case INT:
				_token = Token.IDENT_INT;
				break;
			case BLN:
				_token = Token.IDENT_BLN;
				break;
			case STG:
				_token = Token.IDENT_STG;
				break;
			}
			_variable = variable;
		}
	}

	private boolean scopePatterns() {
		return variableScopePattern() || codeScopePattern() || scopeStartPattern() || scopeEndPattern();
	}

	private boolean inOutOperatorPatterns() {
		return inOperatorPattern() || outOperatorPattern();
	}

	private boolean assignmentOperatorPatterns() {
		return assignOperatorPattern() || assignSetOperatorPattern();
	}

	// endregion
}
