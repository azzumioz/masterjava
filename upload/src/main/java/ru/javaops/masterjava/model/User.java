package ru.javaops.masterjava.model;

import java.util.Objects;

public class User {
    private String name;
    private String email;
    private UserFlag flag;

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserFlag getFlag() {
        return flag;
    }

    public void setFlag(UserFlag flag) {
        this.flag = flag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) &&
                Objects.equals(email, user.email) &&
                flag == user.flag;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, flag);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", flag=" + flag +
                '}';
    }
}
