package com.base;

/**
 * Represents the supported deployment environments.
 * The active environment is resolved from the JVM system property {@code -Denv=<value>}.
 * Defaults to {@link #QA} when the property is absent or blank.
 *
 * <pre>
 * Usage examples:
 *   mvn clean test -Pdev
 *   mvn clean test -Pqa
 *   mvn clean test -Pstage
 *   mvn clean test -Pprod
 *   mvn clean test -Denv=dev
 * </pre>
 */
public enum Environment {

    DEV("dev"),
    QA("qa"),
    STAGE("stage"),
    PROD("prod");

    private final String key;

    Environment(String key) {
        this.key = key;
    }

    /**
     * Returns the lowercase environment key used to resolve the config file name,
     * e.g. {@code "qa"} → {@code config.qa.properties}.
     */
    public String getKey() {
        return key;
    }

    /**
     * Resolves an {@link Environment} from a string value (case-insensitive).
     * Returns {@link #QA} when the value is {@code null} or empty.
     *
     * @param value the environment name (e.g. "dev", "QA", "Stage")
     * @return the matching {@link Environment} constant
     * @throws IllegalArgumentException if the value does not match any environment
     */
    public static Environment fromString(String value) {
        if (value == null || value.trim().isEmpty()) {
            return QA;
        }
        for (Environment env : values()) {
            if (env.key.equalsIgnoreCase(value.trim())) {
                return env;
            }
        }
        throw new IllegalArgumentException(
            "Unknown environment: '" + value + "'. Valid values are: dev, qa, stage, prod."
        );
    }
}
