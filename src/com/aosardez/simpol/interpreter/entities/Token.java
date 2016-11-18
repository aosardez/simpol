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

package com.aosardez.simpol.interpreter.entities;

public enum Token {
	SCOPE_VAR, SCOPE_CODE, SCOPE_START, SCOPE_END, DECLARE_INT, DECLARE_BLN, DECLARE_STG, IDENT_INT, IDENT_BLN, IDENT_STG, INOUT_ASK, INOUT_PRT, ASSIGN_PUT, ASSIGN_IN, OPER_ADD, OPER_SUB, OPER_MUL, OPER_DIV, OPER_MOD, COMPARE_GRT, COMPARE_GRE, COMPARE_LET, COMPARE_LEE, COMPARE_EQL, LOGIC_AND, LOGIC_OHR, LOGIC_NON, LITERAL_INT, LITERAL_BLN, LITERAL_STG, UNKNOWN
}
