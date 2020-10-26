package ru.javaops.masterjava.model;

import javax.xml.bind.annotation.XmlEnumValue;

public enum UserFlag {

    @XmlEnumValue("active")
    ACTIVE("active"),
    @XmlEnumValue("deleted")
    DELETED("deleted"),
    @XmlEnumValue("superuser")
    SUPERUSER("superuser");

    private final String value;

    UserFlag(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static UserFlag fromValue(String v) {
        for (UserFlag c: UserFlag.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }


}
