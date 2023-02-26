package org.uottawa.impl;


import org.uottawa.Issue;

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
        return false;
    }

    @Override
    public Issue createIssue(Issue issue) {
        return null;
    }

    @Override
    public void deleteIssue(String id) {

    }

    @Override
    public List<Issue> loadIssues() {
        return null;
    }
}
