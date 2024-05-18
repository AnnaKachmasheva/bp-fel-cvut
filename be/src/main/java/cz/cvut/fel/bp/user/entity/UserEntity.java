package cz.cvut.fel.bp.user.entity;

import cz.cvut.fel.bp.common.entity.BaseEntity;
import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "APP_USER")
public class UserEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "is_deleted",
            nullable = false,
            columnDefinition = "boolean default false")
    private boolean isDeleted;

    @Timestamp
    @Column(updatable = false, name = "created_at")
    private LocalDateTime createdAt;

    @Timestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public boolean isAdmin() {
        return role == UserRole.ROLE_ADMIN;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + this.getId() +
                ", role=" + role +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", isDeleted=" + isDeleted +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
