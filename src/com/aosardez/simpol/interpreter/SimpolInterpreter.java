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

import com.aosardez.simpol.interpreter.contracts.ILexer;
import com.aosardez.simpol.interpreter.contracts.ISemantic;
import com.aosardez.simpol.interpreter.entities.InterpreterResult;
import com.aosardez.simpol.interpreter.entities.InterpreterTables;
import com.aosardez.simpol.interpreter.semantic.SimpolArithmeticOperator;
import com.aosardez.simpol.interpreter.semantic.SimpolInOutOperator;
import com.aosardez.simpol.interpreter.semantic.SimpolLiteralConverter;
import com.aosardez.simpol.interpreter.semantic.SimpolLiteralValidator;
import com.aosardez.simpol.interpreter.semantic.SimpolLogicalOperator;
import com.aosardez.simpol.interpreter.semantic.SimpolNumericComparer;

public class SimpolInterpreter {
	public InterpreterResult Interpret(String text) {
		boolean successful = false;
		Throwable error = null;

		ILexer lexer = new SimpolLexer(text);
		ISemantic semantic = new SimpolSemantic(new SimpolInOutOperator(), new SimpolArithmeticOperator(),
				new SimpolLogicalOperator(), new SimpolNumericComparer(), new SimpolLiteralValidator(),
				new SimpolLiteralConverter());
		SimpolParser parser = new SimpolParser(lexer, semantic);

		try {
			parser.parse();
			successful = true;
		} catch (Throwable e) {
			error = e;
		}

		InterpreterResult result = new InterpreterResult(successful, error, InterpreterTables.getInstance());
		return result;
	}
}
