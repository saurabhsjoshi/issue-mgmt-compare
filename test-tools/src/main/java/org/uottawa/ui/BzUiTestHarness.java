package org.uottawa.ui;

import org.uottawa.framework.Issue;
import org.uottawa.impl.StatefulTestHarness;

import java.util.HashMap;
import java.util.List;

public class BzUiTestHarness extends StatefulTestHarness {

    public BzUiTestHarness() {
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
