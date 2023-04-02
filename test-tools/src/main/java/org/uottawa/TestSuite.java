package org.uottawa;

import org.uottawa.framework.Issue;
import org.uottawa.framework.TestHarness;

import java.util.stream.IntStream;

public class TestSuite {
    private final TestHarness testHarness;

    public TestSuite(TestHarness testHarness) {
        this.testHarness = testHarness;
    }

    public void start() {
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "8");
        testHarness.login();
        Issue issue = new Issue();
        issue.setId("hello");
        issue.setSummary("This is from test harness");
        issue.setDescription("This is from description");


        for (int j = 0; j < 100; j++) {
            long startTime = System.nanoTime();
            var createdIssues = IntStream.range(0, 10)
                    .parallel()
                    .mapToObj(i -> testHarness.createIssue(issue))
                    .toList();
            long endTime = System.nanoTime();
            long duration = (endTime - startTime) / 1000000;
            System.out.println("Took " + duration + " to create first " + ((j + 1) * 10) + " issues.");
        }
    }
}
