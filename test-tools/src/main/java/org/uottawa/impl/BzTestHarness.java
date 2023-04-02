package org.uottawa.impl;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.uottawa.framework.Issue;
import org.uottawa.framework.Project;

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
    private final HttpClient client;

    private final static ObjectMapper objectMapper = new ObjectMapper();

    private static final String BZ_URI = "http://localhost/bugzilla/rest";

    private static final String CRED = "?Bugzilla_login=admin@test.com&Bugzilla_password=password";

    public BzTestHarness() {
        // TODO: Read config
        super(new HashMap<>());
        client = HttpClient.newHttpClient();
    }

    @Override
    public boolean login() {
        // No login required for bugzilla
        return true;
    }

    @Override
    public Project createProject(Project project) {
        String request = """
                {
                  "name" : "%s",
                  "description" : "Test Project",
                  "classification" : "Unclassified",
                  "is_open" : true,
                  "has_unconfirmed" : true,
                  "version" : "unspecified"
                }
                """;

        request = String.format(request, project.getName());
        var create = getRequestBuilder()
                .uri(URI.create(BZ_URI + "/product" + CRED))
                .POST(HttpRequest.BodyPublishers.ofString(request))
                .build();

        request = """
                {
                  "product" : "%s",
                  "name" : "TestComponent",
                  "description" : "This is a test component",
                  "default_assignee" : "admin@test.com"
                }
                """;
        request = String.format(request, project.getName());
        var createComponent = getRequestBuilder()
                .uri(URI.create(BZ_URI + "/component" + CRED))
                .POST(HttpRequest.BodyPublishers.ofString(request))
                .build();
        try {
            var resp = client.send(create, HttpResponse.BodyHandlers.ofString());
            JsonNode node = objectMapper.readTree(resp.body());
            project.setId(node.get("id").asText());
            client.send(createComponent, HttpResponse.BodyHandlers.ofString());
            project.setComponent("TestComponent");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return project;
    }

    @Override
    public Issue createIssue(Issue issue) {
        String request = """
                {
                            "product" : "%s",
                            "component" : "%s",
                            "version" : "unspecified",
                            "summary" : "%s",
                            "op_sys" : "All",
                            "rep_platform" : "All",
                            "description" : "%s"
                }
                """;

        request = String.format(request, issue.getProject().getName(), issue.getProject().getComponent(), issue.getSummary(), issue.getDescription());
        var create = getRequestBuilder()
                .uri(URI.create(BZ_URI + "/bug?Bugzilla_login=admin@test.com&Bugzilla_password=password"))
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

    @Override
    public void deleteProject(Project project) {
        var delete = getRequestBuilder()
                .uri(URI.create(BZ_URI + "/product/" + project.getName() + CRED))
                .DELETE()
                .build();
        try {
            var resp = client.send(delete, HttpResponse.BodyHandlers.ofString());
            System.out.println(resp.body());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static HttpRequest.Builder getRequestBuilder() {
        return HttpRequest.newBuilder()
                .header("Content-Type", "application/json");
    }
}
