package org.uottawa.framework;

import java.util.List;

public interface TestHarness {

    /**
     * Login to issue management sw.
     *
     * @return true if login successful, false otherwise
     */
    boolean login();

    /**
     * Create an issue.
     *
     * @param issue the issue to be created
     * @return created issue
     */
    Issue createIssue(Issue issue);

    /**
     * Delete issue with given id.
     *
     * @param id the id of the issue
     */
    void deleteIssue(String id);

    /**
     * Retrieve all issues.
     *
     * @return list of issues
     */
    List<Issue> loadIssues();
}
