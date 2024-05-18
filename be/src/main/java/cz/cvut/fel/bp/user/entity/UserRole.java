package cz.cvut.fel.bp.user.entity;

public enum UserRole {

    ROLE_USER("USER"),
    ROLE_ADMIN("ADMIN");

    private final String name;

    UserRole(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
