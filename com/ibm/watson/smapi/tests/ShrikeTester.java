package com.ibm.watson.smapi.tests;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import junit.framework.TestCase;

import com.ibm.shrikeBT.shrikeCT.ClassInstrumenter;
import com.ibm.shrikeBT.shrikeCT.OfflineInstrumenter;
import com.ibm.shrikeBT.shrikeCT.tools.ClassPrinter;
import com.ibm.shrikeCT.ClassReader;
import com.ibm.shrikeCT.ClassWriter;
import com.ibm.shrikeCT.SourceDebugExtensionWriter;
import com.ibm.shrikeCT.ClassReader.AttrIterator;
import com.ibm.shrikeCT.ClassWriter.Element;

public class ShrikeTester extends TestCase {
	
	
	public void testShrike() {
		
		try {
			OfflineInstrumenter oi = new OfflineInstrumenter();
			File input = new File("C:/eclipse3.1/smapWorkspace/x10.common/examples/Constructs/Async/AsyncFieldAccess.class");
			//File input = new File("C:/eclipse3.1/smapWorkspace/smapi/testClasses/Tiny.class");
			//File input = new File("C:/eclipse3.1/smapWorkspace/x10.common/examples/Constructs/Async/Test.class");
			oi.addInputClass(input);
			oi.setOutputJar(new File("C:/temp/shrike.jar"));
			oi.setPassUnmodifiedClasses(false);
			oi.beginTraversal();
			ClassInstrumenter ci = oi.nextClass();
			modifyClass(oi, ci);
			System.out.println("BEFORE");
			printAttributes(ci);
			printToFile("C:/temp/shrikeIn", ci.getReader());
			oi.close();
		} catch (Exception e) {
			System.err.println(e);
		}
		
		
		
		try {
			OfflineInstrumenter oi = new OfflineInstrumenter();
			oi.addInputJar(new File("C:/temp/shrike.jar"));
			ClassInstrumenter ci = oi.nextClass();
			System.out.println("AFTER");
			printAttributes(ci);
			printToFile("C:/temp/shrikeOut", ci.getReader());
		} catch (Exception e) {
			System.err.println(e);
		}		
	}
	
	
	private void modifyClass(OfflineInstrumenter oi, ClassInstrumenter ci) throws Exception {
		ClassReader cr = ci.getReader();
		ClassWriter w = new ClassWriter();
		w.setRawCP(cr.getCP(), true);
		w.setMajorVersion(cr.getMajorVersion());
	    w.setMinorVersion(cr.getMinorVersion());
	    w.setAccessFlags(cr.getAccessFlags());
	    w.setName(cr.getName());
	    w.setSuperName(cr.getSuperName());
	    w.setInterfaceNames(cr.getInterfaceNames());
	   
	    
	    ClassReader.AttrIterator iter = new ClassReader.AttrIterator();

	    int fieldCount = cr.getFieldCount();
	    for (int i = 0; i < fieldCount; i++) {
	      cr.initFieldAttributeIterator(i, iter);
	      w.addField(cr.getFieldAccessFlags(i), cr.getFieldName(i), cr.getFieldType(i), collectAttributes(cr, iter)); 
	    }

	    
	    
	    int methodCount = cr.getMethodCount();
	    for (int i = 0; i < methodCount; i++) {
	      cr.initMethodAttributeIterator(i, iter);
	      w.addMethod(cr.getMethodAccessFlags(i), cr.getMethodName(i), cr.getMethodType(i), collectAttributes(cr, iter));
	    }

	    cr.initClassAttributeIterator(iter);
	    for (; iter.isValid(); iter.advance()) {
	      w.addClassAttribute(getRawAttribute(cr, iter));
	    }
	    
	    
	    SourceDebugExtensionWriter sw = new SourceDebugExtensionWriter(w);
	    String info = "SMAP\n";
	    info += "AsyncFieldAccess.java\n";
	    info += "X10\n";
	    info += "*S X10\n";
	    info += "*F\n";
	    info += "1 AsyncFieldAccess.x10\n";
	    info += "*L\n";
	    info += "33#1:223\n";
	    info += "*E\n";
	    
	    sw.setDebugInfo(info);
	    w.addClassAttribute(sw);
		
		oi.outputModifiedClass(ci, w);
	}
	
	private Element[] collectAttributes(ClassReader cr, AttrIterator iter) throws Exception {
		Element[] elems = new Element[iter.getRemainingAttributesCount()];
		for (int i = 0; i < elems.length; i++) {			
		    elems[i] = getRawAttribute(cr, iter);
			iter.advance();
		}
		return elems;
	}
	
	private Element getRawAttribute(ClassReader cr, AttrIterator iter) {
		int offset = iter.getRawOffset();
		int end = offset + iter.getRawSize();
		return new ClassWriter.RawElement(cr.getBytes(), offset, end - offset);
	}
	
	
	private void printToFile(String filename, ClassReader cr) throws Exception {
		PrintWriter wr = new PrintWriter(new FileOutputStream(filename));
		ClassPrinter cp = new ClassPrinter(wr);
		cp.doClass(cr);
	}
	
	private void printAttributes(ClassInstrumenter ci) throws Exception {
		ClassReader cr = ci.getReader();
		ClassReader.AttrIterator iter = new ClassReader.AttrIterator();
		int methodCount = cr.getMethodCount();
		for (int i = 0; i < methodCount; i++) {
		      cr.initMethodAttributeIterator(i, iter);
		}
		
		System.out.println("--- Method Attributes");
		while(iter.isValid()){
			System.out.println("att name = " + iter.getName());
			iter.advance();
		}
		
		cr.initClassAttributeIterator(iter);
		System.out.println("--- Class Attributes");
		while(iter.isValid()){
			System.out.println("att name = " + iter.getName());
			iter.advance();
		}
		

	    int fieldCount = cr.getFieldCount();
		
		for (int i = 0; i < fieldCount; i++) {
	      cr.initFieldAttributeIterator(i, iter);
	    }

		System.out.println("--- Field Attributes");
		while(iter.isValid()){
			System.out.println("att name = " + iter.getName());
			iter.advance();
		}
	    
	}
	
}
