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

package com.aosardez.simpol;

import com.aosardez.simpol.interpreter.SimpolInterpreter;
import com.aosardez.simpol.interpreter.entities.InterpreterResult;

public class Run {
	private static SimpolConsoleIn _consoleIn;
	private static SimpolConsoleOut _consoleOut;
	private static SimpolInterpreter _interpreter;

	public static void main(String[] args) {
		try {
			_consoleIn = new SimpolConsoleIn();
			_consoleOut = new SimpolConsoleOut();
			_interpreter = new SimpolInterpreter();

			_consoleOut.clearScreen();
			_consoleOut.displayProcessStart();
			_consoleIn.getAndValidateFileName(args);
			_consoleOut.displayExecutionStart(_consoleIn.getFileName());
			InterpreterResult result = _interpreter.Interpret(_consoleIn.getFileContent());
			_consoleOut.displayExecutionEnd(result);
		} catch (Throwable e) {
			_consoleOut.displayThrowable(e);
		}
	}
}
