package org.uottawa.impl;

import org.uottawa.Issue;

import java.util.HashMap;
import java.util.List;

/**
 * Test harness implementation for YouTrack
 */
public class YtTestHarness extends StatefulTestHarness {
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
