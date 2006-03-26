package com.ibm.watson.smapi;

import java.util.ArrayList;
import java.util.Iterator;

public class SmapBuilder {
    /**
     * @param filename
     * @param pathPrefix
     * @param elems
     * @param fileExten file name extension of the original source file; should not have a leading "."
     * @return the SMAP attribute contents, as specified by JSR 045
     */
    public static String get(String filename, String pathPrefix, ArrayList/*<LineElem>*/ elems, String fileExten) {
        String name= filename;
        boolean path= false;
        if (filename.indexOf("/") != -1) { // path name included
            int i= filename.lastIndexOf("/");
            name= filename.substring(i + 1);
            filename= filename.substring(pathPrefix.length());
            System.out.println("filename = " + filename);
            path= true;
        }

        String info= "SMAP\n";
        info+= name + ".java\n";
        info+= fileExten + "\n";
        info+= "*S " + fileExten + "\n";
        info+= "*F\n";
        if (path)
            info+= "+ ";
        info+= "1 " + name + "." + fileExten + "\n";
        if (path)
            info+= filename + "." + fileExten + "\n";
        info+= "*L\n";

        for(Iterator t= elems.iterator(); t.hasNext();) {
            LineElem elem= (LineElem) t.next();
            info+= elem.getOrigSrcStart() + "#1:" + elem.getJavaStart() + "," + elem.getIncr() + "\n";
        }

        info+= "*E\n";
        return info;
    }
}
