package com.ibm.watson.smapi.tests;

import java.util.ArrayList;

import com.ibm.watson.smapi.LineElem;
import com.ibm.watson.smapi.SmapBuilder;

import junit.framework.TestCase;

public class SmapBuilderTester extends TestCase {
	public void test1(){
		LineElem l1 = new LineElem(1,2,3);
		LineElem l2 = new LineElem(4,5,6);
		ArrayList s = new ArrayList(); 
		s.add(l1);
		s.add(l2);
		String smap = SmapBuilder.get("foo", null, s);
		String info = "SMAP\n";
	    info += "foo.java\n";
	    info += "X10\n";
	    info += "*S X10\n";
	    info += "*F\n";
	    info += "1 foo.x10\n";
	    info += "*L\n";
	    info += "1#1:2,3\n";
	    info += "4#1:5,6\n";
	    info += "*E\n";
	    if (! info.equals(smap)){
	    	System.err.println("test failed");
	    	System.out.println(smap);
	    }
	}
}
