package com.ibm.watson.smapi;

import java.util.ArrayList;
import java.util.Iterator;

public class SmapBuilder {
	public static String get(String filename, String pathPrefix, ArrayList /*LineElem*/ elems){
		String name = filename;
		boolean path = false;
		if (filename.indexOf("/") != -1) { //path name included
			int i = filename.lastIndexOf("/");
			name = filename.substring(i+1);
			filename = filename.substring(pathPrefix.length());
			System.out.println("filename = " + filename);
			path = true;
		}
		
		
		String info = "SMAP\n";
	    info += name + ".java\n";
	    info += "X10\n";
	    info += "*S X10\n";
	    info += "*F\n";
	    if (path)
	    	info += "+ ";
	    info += "1 " + name + ".x10\n";
	    if (path)
	    	info += filename + ".x10" + "\n";
	    info += "*L\n";
	    
	    for(Iterator t = elems.iterator(); t.hasNext(); ){
	    	LineElem elem = (LineElem) t.next();
	    	info += elem.getX10() + "#1:" + elem.getStart() +"," + elem.getIncr() +"\n";
	    }
	    
	    info += "*E\n";
	    return info;
	}
}
