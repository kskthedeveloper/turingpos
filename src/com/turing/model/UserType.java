package com.turing.model;

public enum UserType {
    ADMIN("admin"), CASHIER("cashier");

    String type;

    UserType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static UserType fromString(String text) {
        for (UserType userType : UserType.values()) {
            if (userType.type.equalsIgnoreCase(text)) {
                return userType;
            }
        }
        return null;
    }
}
