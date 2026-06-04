package com.DuoOf2.SynapseStack.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


//this entity table will store data and keep track for all the login attempts either made by a user or not

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name="login_attempts")
public class LoginAttempt {

    public LoginAttempt(User user,String email,String ipAddress,boolean success){
        this.user=user;
        this.email=email;
        this.ipAddress=ipAddress;
        this.success=success;
    }
    //should not be modified since the primary key will be created automatically
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "attempt_id",length = 36)
    @Setter(AccessLevel.NONE)
    private String attemptId;

    @Column(nullable = false)
    private String email;
    //relationship to the user table
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Column(nullable = false,name = "ip_address",length = 45)
    private String ipAddress;

    @Column(name="user_agent",columnDefinition = "TEXT")
    private String userAgent;

    //if the login attempt was a success
    @Column(nullable = false)
    private boolean success;
    // reason as to why their login failed example ->WRONG_PASSWORD
    @Column(name = "failure_reason",length=50)
    private String failureReason;

    //should not be modified since CreationTimestamp will record the time of entry automatically
    @Setter(AccessLevel.NONE)
    @CreationTimestamp
    @Column(nullable = false,name="attempt_at")
    private LocalDateTime attemptAt;
}
