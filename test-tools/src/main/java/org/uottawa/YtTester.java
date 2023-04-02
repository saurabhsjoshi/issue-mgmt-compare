package org.uottawa;

import org.uottawa.impl.YtTestHarness;

public class YtTester {

    public static void main(String[] args) {
        TestSuite testSuite = new TestSuite(new YtTestHarness());
        testSuite.start();
    }
}
