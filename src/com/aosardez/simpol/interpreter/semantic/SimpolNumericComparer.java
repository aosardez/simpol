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

import com.aosardez.simpol.interpreter.contracts.semantic.INumericComparison;

public class SimpolNumericComparer implements INumericComparison {

	@Override
	public boolean greaterThan(double input1, double input2) {
		return input1 > input2;
	}

	@Override
	public boolean greaterThanOrEqualTo(double input1, double input2) {
		return input1 >= input2;
	}

	@Override
	public boolean lessThan(double input1, double input2) {
		return input1 < input2;
	}

	@Override
	public boolean lessThanOrEqual(double input1, double input2) {
		return input1 <= input2;
	}

	@Override
	public boolean equal(double input1, double input2) {
		return input1 == input2;
	}

}
