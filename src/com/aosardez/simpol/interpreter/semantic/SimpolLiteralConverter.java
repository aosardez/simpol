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

package com.aosardez.simpol.interpreter.semantic;

import com.aosardez.simpol.interpreter.contracts.semantic.ILiteralConversion;

public class SimpolLiteralConverter implements ILiteralConversion {

	@Override
	public boolean toBoolean(String input) {
		return Boolean.parseBoolean(input);
	}

	@Override
	public double toInteger(String input) {
		return Double.parseDouble(input);
	}

	@Override
	public String toString(String input) {
		String text = "";
		if (input == null) {
			input = "";
		}

		if (input.charAt(0) == '$' && input.charAt(input.length() - 1) == '$') {
			text = input.substring(1, input.length() - 1);
		} else {
			text = input;
		}

		text = text.replace("\\$", "$");
		return text;
	}
}
