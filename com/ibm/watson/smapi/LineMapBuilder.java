package com.ibm.watson.smapi;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;

public class LineMapBuilder {

	static String LINE = "//#line "; 
	
	public static ArrayList /* LineElem */ get(String filename) {
		ArrayList result = new ArrayList();
		try {
			LineNumberReader ln = new LineNumberReader(new FileReader(filename + ".java"));
			String line = null;
			int x10 = -1;
			int javaStart = -1;
			int javaEnd = -1;
			while((line = ln.readLine()) != null){
				if (line.startsWith(LINE)){
					if (x10 != -1){
						result.add(new LineElem(x10, javaStart, javaEnd - javaStart));
					}
					x10 = getNumber(line);
					javaStart = ln.getLineNumber() + 1;
					javaEnd = javaStart;
				} else {
					if (x10 != -1)
						javaEnd++;
				}
			}
			if (x10 != -1){
				result.add(new LineElem(x10, javaStart, javaEnd - javaStart));
			}
			
		} catch (FileNotFoundException e) {
			System.err.println(e);
		} catch (IOException e){
			System.err.println(e);
		}
		
		return result;
		
	}
	
	private static int getNumber(String line){
		String[] lines = line.split(LINE);
		return Integer.parseInt(lines[1]);
	}
	
	
}
