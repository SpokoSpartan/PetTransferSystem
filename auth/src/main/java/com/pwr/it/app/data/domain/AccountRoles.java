package com.pwr.it.app.data.domain;

public enum AccountRoles {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    String name;

    AccountRoles(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
