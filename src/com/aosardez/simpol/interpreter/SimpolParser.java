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

import com.aosardez.simpol.interpreter.contracts.IGrammar;
import com.aosardez.simpol.interpreter.contracts.ILexer;
import com.aosardez.simpol.interpreter.contracts.ISemantic;
import com.aosardez.simpol.interpreter.entities.OperatorType;
import com.aosardez.simpol.interpreter.entities.Token;
import com.aosardez.simpol.interpreter.errors.InvalidDataError;
import com.aosardez.simpol.interpreter.errors.LexicalError;
import com.aosardez.simpol.interpreter.errors.RuntimeError;
import com.aosardez.simpol.interpreter.errors.SyntaxError;

public class SimpolParser implements IGrammar {
	ILexer _lexer;
	ISemantic _semantic;

	// region SimpolParser

	public SimpolParser(ILexer lexer, ISemantic semantic) {
		_lexer = lexer;
		_semantic = semantic;
	}

	public void parse() throws LexicalError, SyntaxError, InvalidDataError, RuntimeError {
		simpol();
	}

	// endregion

	// region IGrammar implementations

	@Override
	public void simpol() throws LexicalError, SyntaxError, InvalidDataError, RuntimeError {
		_lexer.lex();
		variable();
		code();
	}

	@Override
	public void variable() throws LexicalError, SyntaxError {
		if (variableScopePattern()) {
			_lexer.lex();
			if (scopeStartPattern()) {
				_lexer.lex();
				while (!scopeEndPattern() && !_lexer.isEndOfFile()) {
					variableDeclaration();
				}
			}
			if (scopeEndPattern()) {
				_lexer.lex();
			} else {
				raiseSyntaxError("}");
			}
		} else {
			raiseSyntaxError("variable");
		}
	}

	@Override
	public void variableDeclaration() throws LexicalError, SyntaxError {
		if (dataTypePattern()) {
			_lexer.lex();
		} else {
			raiseSyntaxError("INT, BLN, or STG");
		}

		if (variableNamePattern()) {
			_lexer.lex();
		} else {
			raiseSyntaxError("a variable name");
		}
	}

	@Override
	public void code() throws LexicalError, SyntaxError, InvalidDataError, RuntimeError {
		if (codeScopePattern()) {
			_lexer.lex();
			if (scopeStartPattern()) {
				_lexer.lex();
				while (!scopeEndPattern() && !_lexer.isEndOfFile()) {
					statement();
				}
			}
			if (scopeEndPattern()) {
				_lexer.lex();
			} else {
				raiseSyntaxError("}");
			}
		} else {
			raiseSyntaxError("code");
		}
	}

	@Override
	public void statement() throws LexicalError, SyntaxError, InvalidDataError, RuntimeError {
		if (inOperatorPattern()) {
			_lexer.lex();
			if (!variableNamePattern()) {
				raiseSyntaxError("a declared variable");
			}
			_lexer.lex();
			String input = _semantic.statementIn("ASK");
			_semantic.expressionAssign("ASK", _lexer.getLastVariable(), input);
		} else if (outOperatorPattern()) {
			_lexer.lex();
			String input = expression();
			_lexer.lex();
			_semantic.statementOut(input);
		} else if (assignOperatorPattern()) {
			_lexer.lex();
			String input = expression();
			_lexer.lex();
			if (assignSetOperatorPattern()) {
				_lexer.lex();
				if (variableNamePattern()) {
					_semantic.expressionAssign("PUT", _lexer.getLastVariable(), input);
					_lexer.lex();
				} else {
					raiseSyntaxError("a declared variable");
				}
			} else {
				raiseSyntaxError("IN");
			}
		} else {
			raiseSyntaxError("PUT, PRT, or ASK");
		}
	}

	@Override
	public String expression() throws LexicalError, SyntaxError, InvalidDataError {
		String result = "";

		if (operatorPattern()) {
			OperatorType operatorType = _lexer.getLastOperatorType();
			Token tokenType = _lexer.getToken();
			_lexer.lex();
			String input1 = expression();
			_lexer.lex();
			String input2 = expression();
			result = _semantic.expression(operatorType, tokenType, input1, input2);
		} else if (logicalNotOperatorPattern()) {
			Token tokenType = _lexer.getToken();
			_lexer.lex();
			String operation = getOperatorOperation(tokenType);
			String input = expression();
			return _semantic.expressionLogicalNot(operation, input);
		} else if (variableNamePattern()) {
			result = _lexer.getLastVariable().getValue();
		} else if (literalPattern()) {
			String value = _lexer.getLexeme();
			result = value;
		} else {
			raiseSyntaxError("a valid operator, variable, or literal");
		}

		return result;
	}

	// endregion

	// region IGrammarPattern implementations

	@Override
	public boolean variableScopePattern() {
		return _lexer.getToken().equals(Token.SCOPE_VAR);
	}

	@Override
	public boolean codeScopePattern() {
		return _lexer.getToken().equals(Token.SCOPE_CODE);
	}

	@Override
	public boolean scopeStartPattern() {
		return _lexer.getToken().equals(Token.SCOPE_START);
	}

	@Override
	public boolean scopeEndPattern() {
		return _lexer.getToken().equals(Token.SCOPE_END);
	}

	@Override
	public boolean dataTypePattern() {
		return _lexer.getToken().equals(Token.DECLARE_INT) || _lexer.getToken().equals(Token.DECLARE_BLN)
				|| _lexer.getToken().equals(Token.DECLARE_STG);
	}

	@Override
	public boolean variableNamePattern() {
		return _lexer.getToken().equals(Token.IDENT_INT) || _lexer.getToken().equals(Token.IDENT_BLN)
				|| _lexer.getToken().equals(Token.IDENT_STG);
	}

	@Override
	public boolean inOperatorPattern() {
		return _lexer.getToken().equals(Token.INOUT_ASK);
	}

	@Override
	public boolean outOperatorPattern() {
		return _lexer.getToken().equals(Token.INOUT_PRT);
	}

	@Override
	public boolean assignOperatorPattern() {
		return _lexer.getToken().equals(Token.ASSIGN_PUT);
	}

	@Override
	public boolean assignSetOperatorPattern() {
		return _lexer.getToken().equals(Token.ASSIGN_IN);
	}

	@Override
	public boolean operatorPattern() {
		return arithmeticOperatorPattern() || numericPredicatePattern() || logicalOperatorPattern();
	}

	@Override
	public boolean arithmeticOperatorPattern() {
		return _lexer.getToken().equals(Token.OPER_ADD) || _lexer.getToken().equals(Token.OPER_SUB)
				|| _lexer.getToken().equals(Token.OPER_MUL) || _lexer.getToken().equals(Token.OPER_DIV)
				|| _lexer.getToken().equals(Token.OPER_MOD);
	}

	@Override
	public boolean numericPredicatePattern() {
		return _lexer.getToken().equals(Token.COMPARE_GRT) || _lexer.getToken().equals(Token.COMPARE_GRE)
				|| _lexer.getToken().equals(Token.COMPARE_LET) || _lexer.getToken().equals(Token.COMPARE_LEE)
				|| _lexer.getToken().equals(Token.COMPARE_EQL);
	}

	@Override
	public boolean logicalOperatorPattern() {
		return _lexer.getToken().equals(Token.LOGIC_AND) || _lexer.getToken().equals(Token.LOGIC_OHR);
	}

	@Override
	public boolean logicalNotOperatorPattern() {
		return _lexer.getToken().equals(Token.LOGIC_NON);
	}

	@Override
	public boolean literalPattern() {
		return _lexer.getToken().equals(Token.LITERAL_INT) || _lexer.getToken().equals(Token.LITERAL_BLN)
				|| _lexer.getToken().equals(Token.LITERAL_STG);
	}

	// endregion

	// region Local and helper operations

	private void raiseSyntaxError(String expectedSymbols) throws SyntaxError {
		throw new SyntaxError(_lexer.getLexeme(), expectedSymbols, _lexer.getLine(), _lexer.getColumn());
	}

	private String getOperatorOperation(Token tokenType) {
		return tokenType.toString().split("_")[1];
	}

	// endregion
}
