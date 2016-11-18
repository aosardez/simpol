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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SimpolConsoleIn {
	String _fileName;
	String _fileContent;

	public void getAndValidateFileName(String[] args) throws Exception {
		String fileName = (args != null && args.length > 0) ? args[0] : "";

		if (fileName == "") {
			System.out.println("Please provide Simpol source code file name and press [ENTER]:");
			System.out.print("sim>");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			fileName = reader.readLine().trim();
		}
		fileName = fileName.replace("\"", "");

		if (!fileName.matches("^\"?(?i).*\\.sim\"?$")) {
			throw new IllegalArgumentException("The file name provided is not valid for a Simpol source code file.");
		}

		_fileName = fileName;
	}

	public String getFileName() {
		return _fileName;
	}

	public String getFileContent() throws IOException {
		String codeText = new String(Files.readAllBytes(Paths.get(_fileName)));
		return codeText;
	}
}
