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

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.aosardez.simpol.interpreter.entities.InterpreterResult;
import com.aosardez.simpol.interpreter.entities.SymbolTableItem;
import com.aosardez.simpol.interpreter.entities.TokenTableItem;

public class SimpolConsoleOut {
	public void clearScreen() {
		try {
			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		} catch (InterruptedException | IOException e) {
		}
	}

	public void displayProcessStart() {
		System.out.println("Simpol Interpreter by Angelito O. Sardez, Jr.");
		System.out.println("Copyright (c) 2016\n");
	}

	public void displayExecutionStart(String fileName) {
		System.out.println("Simpol code interpretation and execution for the file " + fileName + " started at "
				+ formatDate(new Date()) + ".");
		System.out.println();
		System.out.println(padText("", 17, "*") + " EXECUTION OUTPUT START " + padText("", 17, "*"));
		System.out.println();
	}

	public void displayExecutionEnd(InterpreterResult result) {
		displayExecutionResult(result.getSuccessful(), result.getError());
		displaySymbolTable(result.getSymbolTable());
		displayTokenTable(result.getTokenTable());
	}

	public void displayThrowable(Throwable error) {
		System.out.println("Error encountered: " + error.toString());
		System.out.println("Please run the interpreter again to retry.");
	}

	private void displayExecutionResult(boolean successful, Throwable throwable) {
		System.out.println();
		System.out.println(padText("", 17, "*") + " EXECUTION OUTPUT END " + padText("", 19, "*"));
		System.out.println();
		if (successful) {
			System.out.println("Simpol code interpretation and execution successfully completed at "
					+ formatDate(new Date()) + ".");
		} else {
			System.out.println("Simpol code interpretation or execution completed at " + formatDate(new Date())
					+ " with the following error:");
			System.out.println(throwable.toString());
		}
		System.out.println("\n");
	}

	private void displaySymbolTable(List<SymbolTableItem> symbolTable) {
		System.out.println(padText("", 50, "-"));
		System.out.println("|" + padText("", 18, " ") + "SYMBOL TABLE" + padText("", 18, " ") + "|");
		System.out.println(padText("", 50, "-"));
		System.out.println("|NO.|TYPE|" + padText("NAME", 8, " ") + "|" + padText("VALUE", 30, " ") + "|");
		System.out.println(padText("", 50, "-"));

		if (symbolTable != null && symbolTable.size() > 0) {
			int counter = 1;
			for (SymbolTableItem item : symbolTable) {
				System.out.println("|" + padText(Integer.toString(counter), 3, " ") + "|"
						+ padText(item.getType().toString(), 4, " ") + "|" + padText(item.getName(), 8, " ") + "|"
						+ padText(item.getValue(), 30, " ") + "|");
				counter++;
			}
		}
		System.out.println(padText("", 50, "-"));
		System.out.println();
	}

	private void displayTokenTable(List<TokenTableItem> tokenTable) {
		System.out.println(padText("", 60, "-"));
		System.out.println("|" + padText("", 23, " ") + "TOKEN TABLE" + padText("", 24, " ") + "|");
		System.out.println(padText("", 60, "-"));
		System.out.println("|NO.|" + padText("TOKEN", 12, " ") + "|" + padText("LEXEME", 33, " ") + "|LN.|COL|");
		System.out.println(padText("", 60, "-"));

		if (tokenTable != null && tokenTable.size() > 0) {
			int counter = 1;
			for (TokenTableItem item : tokenTable) {
				System.out.println("|" + padText(Integer.toString(counter), 3, " ") + "|"
						+ padText(item.getToken().toString(), 12, " ") + "|" + padText(item.GetLexeme(), 33, " ") + "|"
						+ padText(Integer.toString(item.GetLine()), 3, " ") + "|"
						+ padText(Integer.toString(item.GetColumn()), 3, " ") + "|");
				counter++;
			}
		}
		System.out.println(padText("", 60, "-"));
		System.out.println();
	}

	private String formatDate(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return dateFormat.format(date);
	}

	private String padText(String text, int maxLength, String padString) {
		if (text == null) {
			text = "";
		}

		if (text.length() < maxLength) {
			text += new String(new char[maxLength - text.length()]).replace("\0", padString);
		}

		return text;
	}
}
