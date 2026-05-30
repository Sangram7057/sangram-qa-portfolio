package com.sangram.qa.data;

import com.sangram.qa.config.EnvConfig;

public final class TestUsers {
    private static final TestUser VALID_USER =
        new TestUser(EnvConfig.username(), EnvConfig.password());

    private TestUsers() {
    }

    public static TestUser validUser() {
        return VALID_USER;
    }

    public record TestUser(String username, String password) {
    }
}
