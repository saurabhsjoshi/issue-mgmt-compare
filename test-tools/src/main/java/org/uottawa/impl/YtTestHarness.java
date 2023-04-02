package org.uottawa.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.uottawa.framework.Issue;
import org.uottawa.framework.Project;

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
    private final static ObjectMapper objectMapper = new ObjectMapper();
    private static final String TOKEN = "Bearer perm:YWRtaW4=.NDYtMA==.1VHUmwkW2ONqKAgiYVunJdTjCJT1pP";

    private static final String ADMIN_URI = "http://localhost:8080/api/admin";
    private static final String YT_URI = "http://localhost:8080/api";

    private static final HttpClient client = HttpClient.newHttpClient();

    public YtTestHarness() {
        // TODO: Read config
        super(new HashMap<>());
    }

    @Override
    public boolean login() {
        return false;
    }

    @Override
    public Project createProject(Project project) {
        String req = """
                {
                    "name" : "%s",
                    "description" : "This is a test project",
                    "shortName" : "TP",
                    "template" : "default",
                    "leader": {
                            "id": "1-1"
                    }
                }
                """;
        req = String.format(req, project.getName());
        var createReq = getRequestBuilder()
                .uri(URI.create(ADMIN_URI + "/projects?fields=id,key,name&template=default"))
                .POST(HttpRequest.BodyPublishers.ofString(req))
                .build();
        try {
            var resp = client.send(createReq, HttpResponse.BodyHandlers.ofString());
            JsonNode node = objectMapper.readTree(resp.body());
            project.setId(node.get("id").asText());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return project;
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
        req = String.format(req, issue.getSummary(), issue.getDescription(), issue.getProject().getId());
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

    @Override
    public void deleteProject(Project project) {
        var delete = getRequestBuilder()
                .uri(URI.create(ADMIN_URI + "/projects/" + project.getId()))
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
                .header("Content-Type", "application/json")
                .header("Authorization", TOKEN);
    }
}
