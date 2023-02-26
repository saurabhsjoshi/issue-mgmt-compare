package org.uottawa.impl;

import org.uottawa.TestHarness;

import java.util.Map;

/**
 * A test harness that is stateful and contains a context.
 */
public abstract class StatefulTestHarness implements TestHarness {
    protected final Map<String, Object> context;

    public StatefulTestHarness(Map<String, Object> context) {
        this.context = context;
    }

    @SuppressWarnings("unchecked")
    protected <T> T getAttr(String name) {
        return (T) context.get(name);
    }
}
