package org.uottawa.impl;


import org.uottawa.framework.Issue;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;

/**
 * Test harness implementation for bugzilla.
 */
public class BzTestHarness extends StatefulTestHarness {

    public BzTestHarness() {
        // TODO: Read config
        super(new HashMap<>());
    }

    @Override
    public boolean login() {
        // No login required for bugzilla
        return true;
    }

    @Override
    public Issue createIssue(Issue issue) {
        var client = HttpClient.newHttpClient();

        String request = """
                {
                            "product" : "TestProduct",
                            "component" : "TestComponent",
                            "version" : "unspecified",
                            "summary" : "%s",
                            "op_sys" : "All",
                            "rep_platform" : "All",
                            "description" : "%s"
                }
                """;

        request = String.format(request, issue.getSummary(), issue.getDescription());
        var create = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost/bugzilla/rest/bug?Bugzilla_login=admin@test.com&Bugzilla_password=password"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(request))
                .build();

        try {
            client.send(create, HttpResponse.BodyHandlers.ofString());
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
        return null;
    }
}
