package com.ibm.watson.smapi;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class LineMapBuilder {

	static String LINE = "//#line "; 
	String filename;

	ArrayList /*LineElem*/ arraylist;
	Map /*integer -> LineElem*/ map;
	
	
	public LineMapBuilder(String filename){
		this.filename = filename;
		build();
	}
	
	public ArrayList get() {
		return arraylist;
	}
	
	public Map getLineMap(){
		return map;
	}
	
	private void /* LineElem */ build() {
		arraylist = new ArrayList();
		map = new HashMap();
		try {
			LineNumberReader ln = new LineNumberReader(new FileReader(filename + ".java"));
			String line = null;
			int origSrcLine = -1;
			int javaStart = -1;
			int javaEnd = -1;
			while((line = ln.readLine()) != null){
				if (line.startsWith(LINE)){
					if (origSrcLine != -1){
						LineElem le = new LineElem(origSrcLine, javaStart, javaEnd - javaStart);
						arraylist.add(le);
						map.put(new Integer(origSrcLine), le);
					}
					origSrcLine = getNumber(line);
					javaStart = ln.getLineNumber() + 1;
					javaEnd = javaStart;
				} else {
					if (origSrcLine != -1)
						javaEnd++;
				}
			}
			if (origSrcLine != -1){
				LineElem le = new LineElem(origSrcLine, javaStart, javaEnd - javaStart);
				arraylist.add(le);
				map.put(new Integer(origSrcLine), le);
			}
			
		} catch (FileNotFoundException e) {
			System.err.println(e);
		} catch (IOException e){
			System.err.println(e);
		}
		
	}
	
	
	
	private static int getNumber(String line){
		String[] lines = line.split(LINE);
		return Integer.parseInt(lines[1]);
	}
	
	
}
