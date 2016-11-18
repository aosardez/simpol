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

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.aosardez.simpol.interpreter.contracts.ISemantic;
import com.aosardez.simpol.interpreter.contracts.semantic.IArithmeticOperation;
import com.aosardez.simpol.interpreter.contracts.semantic.IInOutOperation;
import com.aosardez.simpol.interpreter.contracts.semantic.ILiteralConversion;
import com.aosardez.simpol.interpreter.contracts.semantic.ILiteralValidation;
import com.aosardez.simpol.interpreter.contracts.semantic.ILogicalOperation;
import com.aosardez.simpol.interpreter.contracts.semantic.INumericComparison;
import com.aosardez.simpol.interpreter.entities.DataType;
import com.aosardez.simpol.interpreter.entities.OperatorType;
import com.aosardez.simpol.interpreter.entities.SymbolTableItem;
import com.aosardez.simpol.interpreter.entities.Token;
import com.aosardez.simpol.interpreter.errors.InvalidDataError;
import com.aosardez.simpol.interpreter.errors.RuntimeError;

public class SimpolSemantic implements ISemantic {
	IInOutOperation _inOutOperator;
	IArithmeticOperation _arithmeticOperator;
	ILogicalOperation _logicalOperator;
	INumericComparison _numericComparer;
	ILiteralValidation _literalValidator;
	ILiteralConversion _literalConverter;

	// region SimpolSemantic

	public SimpolSemantic(IInOutOperation inOutOperator, IArithmeticOperation arithmeticOperator,
			ILogicalOperation logicalOperator, INumericComparison numericComparer, ILiteralValidation literalValidator,
			ILiteralConversion literalConverter) {
		_inOutOperator = inOutOperator;
		_arithmeticOperator = arithmeticOperator;
		_logicalOperator = logicalOperator;
		_numericComparer = numericComparer;
		_literalValidator = literalValidator;
		_literalConverter = literalConverter;
	}

	// endregion

	// region ISemantic implementations

	@Override
	public String statementIn(String operation) throws RuntimeError {
		String result = "";

		try {
			result = _inOutOperator.ask();
		} catch (IOException e) {
			raiseRuntimeError(operation, e.getMessage(), e.getCause());
		}

		return result;
	}

	@Override
	public void statementOut(String text) {
		text = _literalConverter.toString(text);
		_inOutOperator.print(text);
	}

	@Override
	public String expression(OperatorType operatorType, Token tokenType, String input1, String input2)
			throws InvalidDataError {
		String result = "";

		switch (operatorType) {
		case Arithmetic:
			result = expressionArithmeticOperatorProcess(tokenType, input1, input2);
			break;
		case Logical:
			result = expressionLogicalOperatorProcess(tokenType, input1, input2);
			break;
		case Numeric:
			result = expressionNumericOperatorProcess(tokenType, input1, input2);
			break;
		}

		return result;
	}

	@Override
	public String expressionLogicalNot(String operation, String input) throws InvalidDataError {
		boolean convertedInput = validateAndGetTranslatedBoolean(operation, input);
		boolean operationResult = _logicalOperator.not(convertedInput);
		return Boolean.toString(operationResult);
	}

	@Override
	public void expressionAssign(String operation, SymbolTableItem variable, String value) throws InvalidDataError {
		if ((variable.getType().equals(DataType.BLN) && !_literalValidator.isBoolean(value))
				|| (variable.getType().equals(DataType.INT) && !_literalValidator.isInteger(value))) {
			raiseInvalidDataError(operation, value, variable.getType());
		}

		if (variable.getType().equals(DataType.STG) && _literalValidator.isString(value)) {
			value = _literalConverter.toString(value);
		}

		variable.setValue(value);
	}

	// endregion

	// region Local and helper operations

	private String expressionArithmeticOperatorProcess(Token tokenType, String input1, String input2)
			throws InvalidDataError {
		Double result = 0.0;
		String operation = getOperatorOperation(tokenType);
		Double convertedInput1 = validateAndGetTranslatedInteger(operation, input1);
		Double convertedInput2 = validateAndGetTranslatedInteger(operation, input2);

		switch (tokenType) {
		case OPER_ADD:
			result = _arithmeticOperator.add(convertedInput1, convertedInput2);
			break;
		case OPER_SUB:
			result = _arithmeticOperator.subtract(convertedInput1, convertedInput2);
			break;
		case OPER_MUL:
			result = _arithmeticOperator.multiply(convertedInput1, convertedInput2);
			break;
		case OPER_DIV:
			result = _arithmeticOperator.divide(convertedInput1, convertedInput2);
			break;
		case OPER_MOD:
			result = _arithmeticOperator.mod(convertedInput1, convertedInput2);
			break;
		}

		return formatNumber(result);
	}

	private String expressionLogicalOperatorProcess(Token tokenType, String input1, String input2)
			throws InvalidDataError {
		boolean result = false;
		String operation = getOperatorOperation(tokenType);
		boolean convertedInput1 = validateAndGetTranslatedBoolean(operation, input1);
		boolean convertedInput2 = validateAndGetTranslatedBoolean(operation, input2);

		switch (tokenType) {
		case LOGIC_AND:
			result = _logicalOperator.and(convertedInput1, convertedInput2);
			break;
		case LOGIC_OHR:
			result = _logicalOperator.or(convertedInput1, convertedInput2);
			break;
		}

		return Boolean.toString(result);
	}

	private String expressionNumericOperatorProcess(Token tokenType, String input1, String input2)
			throws InvalidDataError {
		boolean result = false;
		String operation = getOperatorOperation(tokenType);
		Double convertedInput1 = validateAndGetTranslatedInteger(operation, input1);
		Double convertedInput2 = validateAndGetTranslatedInteger(operation, input2);

		switch (tokenType) {
		case COMPARE_GRT:
			result = _numericComparer.greaterThan(convertedInput1, convertedInput2);
			break;
		case COMPARE_GRE:
			result = _numericComparer.greaterThanOrEqualTo(convertedInput1, convertedInput2);
			break;
		case COMPARE_LET:
			result = _numericComparer.lessThan(convertedInput1, convertedInput2);
			break;
		case COMPARE_LEE:
			result = _numericComparer.lessThanOrEqual(convertedInput1, convertedInput2);
			break;
		case COMPARE_EQL:
			result = _numericComparer.equal(convertedInput1, convertedInput2);
			break;
		}

		return Boolean.toString(result);
	}

	private boolean validateAndGetTranslatedBoolean(String operation, String input) throws InvalidDataError {
		if (!_literalValidator.isBoolean(input)) {
			raiseInvalidDataError(operation, input, DataType.BLN);
		}

		return _literalConverter.toBoolean(input);
	}

	private double validateAndGetTranslatedInteger(String operation, String input) throws InvalidDataError {
		if (!_literalValidator.isInteger(input)) {
			raiseInvalidDataError(operation, input, DataType.INT);
		}

		return _literalConverter.toInteger(input);
	}

	private String formatNumber(double input) {
		NumberFormat formatter = new DecimalFormat("#.##########");
		return formatter.format(input);
	}

	private String getOperatorOperation(Token tokenType) {
		return tokenType.toString().split("_")[1];
	}

	private void raiseInvalidDataError(String operation, String value, DataType dataType) throws InvalidDataError {
		throw new InvalidDataError(operation, value, dataType);
	}

	private void raiseRuntimeError(String operation, String errorMessage, Throwable cause) throws RuntimeError {
		throw new RuntimeError(operation, errorMessage, cause);
	}

	// endregion
}
