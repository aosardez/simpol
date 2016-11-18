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

public class RuntimeError extends Exception {
	String _operation;
	String _errorMessage;
	Throwable _cause;

	public RuntimeError(String operation, String errorMessage, Throwable cause) {
		super("Runtime error encountered while executing " + operation + ": " + errorMessage
				+ (cause != null ? "\nCause: " + cause.getMessage() : ""), cause);

		_operation = operation;
		_errorMessage = errorMessage;
		_cause = cause;
	}

	public String getOperation() {
		return _operation;
	}

	public String getErrorMessage() {
		return _errorMessage;
	}

	@Override
	public Throwable getCause() {
		return _cause;
	}
}
