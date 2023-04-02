package org.uottawa;

import org.uottawa.impl.BzTestHarness;

public class BzTester {
    public static void main(String[] args) {
        TestSuite testSuite = new TestSuite(new BzTestHarness());
        testSuite.start();
    }
}
