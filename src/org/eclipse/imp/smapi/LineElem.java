/**
 * 
 */
package org.eclipse.imp.smapi;

public class LineElem {
    int origSrcStart;

    int javaStart;

    int incr;

    public LineElem(int origSrcStart, int javaStart, int incr) {
        this.origSrcStart= origSrcStart;
        this.javaStart= javaStart;
        this.incr= incr;
    }

    public int getOrigSrcStart() {
        return origSrcStart;
    }

    public int getJavaStart() {
        return javaStart;
    }

    public int getIncr() {
        return incr;
    }
}
