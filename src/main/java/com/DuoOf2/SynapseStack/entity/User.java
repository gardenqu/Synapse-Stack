package com.DuoOf2.SynapseStack.entity;
import  jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.*;
import java.util.*;
@Entity
@Table(name="users")
@Getter
@Setter
@NoArgsConstructor
public class User implements UserDetails {
    public User(String firstName,String lastName,String email,String passwordHash){
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
        this.passwordHash=passwordHash;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length=36,name="user_id")
    @Setter(AccessLevel.NONE)
    private String userId;
    @Column(length=20,nullable = false,name="first_name")
    private String firstName;
    @Column(length=20,nullable = false,name="last_name")
    private String lastName;
    @Column(name="birth_day")
    private LocalDate birthDay;


    @Column(length=50,nullable = false,unique = true)
    private String email;

    @Setter(AccessLevel.NONE)
    @CreationTimestamp
    @Column(nullable = false,updatable = false,name="created_at")
    private LocalDateTime createdAt;
    @Setter(AccessLevel.NONE)
    @UpdateTimestamp
    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @Column(nullable = false,columnDefinition = "text",name = "password_hash")
    private String passwordHash;

    @OneToMany(mappedBy = "user")
    List<SecurityLog> logs = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    List<AuthActions> authActions= new ArrayList<>();
    @OneToMany(mappedBy = "user")
    List<RefreshToken> refreshTokens= new ArrayList<>();
    @OneToMany(mappedBy = "user")
    List<UserActivity> userActivities= new ArrayList<>();
    @OneToMany(mappedBy = "user")
    List<LoginAttempt> loginAttempts= new ArrayList<>();


    // ── UserDetails implementation ──────────────────────────
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // No roles entity yet — defaulting to USER role
        // TODO: replace with real roles once Role entity is ready
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return email; // Spring Security uses this to identify the user
    }
}
