package org.uottawa.framework;

import lombok.Data;

/**
 * Class that represents an issue in the issue management system.
 */
@Data
public class Issue {
    private String id;
    private String project;
    private String component;
    private String summary;
    private String description;
}
