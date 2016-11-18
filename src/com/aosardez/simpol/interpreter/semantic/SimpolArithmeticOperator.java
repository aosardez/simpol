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

import com.aosardez.simpol.interpreter.contracts.semantic.IArithmeticOperation;

public class SimpolArithmeticOperator implements IArithmeticOperation {

	@Override
	public double add(double input1, double input2) {
		return input1 + input2;
	}

	@Override
	public double subtract(double input1, double input2) {
		return input1 - input2;
	}

	@Override
	public double multiply(double input1, double input2) {
		return input1 * input2;
	}

	@Override
	public double divide(double input1, double input2) {
		return input1 / input2;
	}

	@Override
	public double mod(double input1, double input2) {
		return input1 % input2;
	}

}
