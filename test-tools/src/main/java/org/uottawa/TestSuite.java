package org.uottawa;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.uottawa.framework.Issue;
import org.uottawa.framework.Project;
import org.uottawa.framework.TestHarness;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

public class TestSuite {
    private final TestHarness testHarness;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public TestSuite(TestHarness testHarness) {
        this.testHarness = testHarness;
    }

    public void start() {
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "8");
        testHarness.login();

        // Create a project
        Project project = new Project();
        project.setName("TestProject");
        project = testHarness.createProject(project);

        Issue issue = new Issue();
        issue.setId("hello");
        issue.setSummary("This is from test harness");
        issue.setDescription("This is from description");
        issue.setProject(project);

        List<Measurement> measurements = new ArrayList<>(100);
        List<Issue> issues = new ArrayList<>();

        System.out.println("Test start time: " + new Date());

        for (int j = 0; j < 50; j++) {
            long startTime = System.nanoTime();
            var createdIssues = IntStream.range(0, 10)
                    .parallel()
                    .mapToObj(i -> testHarness.createIssue(issue))
                    .toList();
            issues.addAll(createdIssues);

            long endTime = System.nanoTime();
            long duration = (endTime - startTime) / 1000000;
            measurements.add(new Measurement((j + 1) * 10, duration));

            System.out.println("Took " + duration + " to create first " + ((j + 1) * 10) + " issues.");
        }

        System.out.println("Test end time: " + new Date());

        try {
            var measureJson = objectMapper.writeValueAsString(measurements);
            System.out.println(measureJson);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        testHarness.deleteProject(project);
    }
}
