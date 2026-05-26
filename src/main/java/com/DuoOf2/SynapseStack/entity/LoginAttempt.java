package com.DuoOf2.SynapseStack.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name="Login_Attempts")
public class LoginAttempt {
    public LoginAttempt(User user,String email,String ipAddress,boolean success){
        this.user=user;
        this.email=email;
        this.ipAddress=ipAddress;
        this.success=success;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "attempt_id",length = 36)
    private String attemptId;

    @Column(nullable = false)
    private String email;

    @ManyToOne
    //@JoinColumn(name = "user_id");
    @JoinColumn(name="user_id")
    private User user;

    @Column(nullable = false,name = "ip_address",length = 45)
    private String ipAddress;

    @Column(name="user_agent",columnDefinition = "TEXT")
    private String userAgent;

    @Column(nullable = false)
    private boolean success;

    @Column(name = "failure_reason",length=50)
    private String failureReason;

    @CreationTimestamp
    @Column(nullable = false,name="attempt_at")
    private LocalDateTime attemptAt;
}
