/**
 * 
 */
package com.ibm.watson.smapi;

public class LineElem {
	int x10;
	int javaStart;
	int incr;
	
	public LineElem(int x10, int javaStart, int incr){
		this.x10 = x10;
		this.javaStart = javaStart;
		this.incr = incr;
	}
	
	public int getX10(){
		return x10;
	}
	
	public int getStart(){
		return javaStart;
	}
	
	public int getIncr(){
		return incr;
	}
}