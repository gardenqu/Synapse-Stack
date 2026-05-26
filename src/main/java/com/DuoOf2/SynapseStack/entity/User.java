package com.DuoOf2.SynapseStack.entity;
import  jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.*;
import java.time.*;
import java.util.*;
@Entity
@Table(name="users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    public User(String firstName,String lastName,String email,String passwordHash){
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
        this.passwordHash=passwordHash;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length=36,name="user_id")
    private String userId;
    @Column(length=20,nullable = false,name="first_name")
    private String firstName;
    @Column(length=20,nullable = false,name="last_name")
    private String lastName;
    @Column(name="birth_day")
    private LocalDate birthDay;


    @Column(length=50,nullable = false,unique = true)
    private String email;


    @CreationTimestamp
    @Column(nullable = false,updatable = false,name="created_at")
    private LocalDateTime createdAt;

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



}
