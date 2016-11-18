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

import com.aosardez.simpol.interpreter.entities.DataType;

public class InvalidDataError extends Exception {
	String _operation;
	String _data;
	DataType _dataType;

	public InvalidDataError(String operation, String data, DataType dataType) {
		super("Invalid data error encountered while executing " + operation + " operation: " + data
				+ " is not a valid value for a(n) " + dataType.toString() + ".");

		_operation = operation;
		_data = data;
		_dataType = dataType;
	}

	public String getOperation() {
		return _operation;
	}

	public String getData() {
		return _data;
	}

	public DataType getDataType() {
		return _dataType;
	}
}
