package org.uottawa.impl;

import org.uottawa.framework.Issue;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Test harness implementation for YouTrack
 */
public class YtTestHarness extends StatefulTestHarness {
    private static final String TOKEN = "Bearer perm:YWRtaW4=.NDYtMQ==.3XO1LpTrOi5sALol2UPgHNH7Hj4qWY";
    private static final String YT_URI = "http://localhost:8080/api";

    public YtTestHarness() {
        // TODO: Read config
        super(new HashMap<>());
    }

    @Override
    public boolean login() {
        return false;
    }

    @Override
    public Issue createIssue(Issue issue) {
        var client = HttpClient.newHttpClient();
        String req = """
                {
                    "summary" : "%s",
                    "description" : "%s",
                    "project" : {
                        "id" : "%s"
                    }
                }
                """;
        req = String.format(req, issue.getSummary(), issue.getDescription(), issue.getProject());
        var createReq = getRequestBuilder()
                .uri(URI.create(YT_URI + "/issues?fields=created,summary,description"))
                .POST(HttpRequest.BodyPublishers.ofString(req))
                .build();

        try {
            client.send(createReq, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return issue;
    }

    @Override
    public void deleteIssue(String id) {
    }

    @Override
    public List<Issue> loadIssues() {
        var issues = new ArrayList<Issue>();
        var client = HttpClient.newHttpClient();
        var req = getRequestBuilder()
                .uri(URI.create(YT_URI + "/issues?fields=created,summary,description,project(name,id)"))
                .GET()
                .build();
        try {
            var resp = client.send(req, HttpResponse.BodyHandlers.ofString());
            System.out.println(resp.body());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return issues;
    }

    private static HttpRequest.Builder getRequestBuilder() {
        return HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .header("Authorization", TOKEN);
    }
}
