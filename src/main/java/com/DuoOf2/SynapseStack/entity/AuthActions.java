package com.DuoOf2.SynapseStack.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;


@Entity
@Setter
@Getter
@NoArgsConstructor
//This entity holds/stores one timed temporary tokens used to reset password and account recovery
public class AuthActions {
    public AuthActions(User user,String actionType,String tokenHash,LocalDateTime expiresAt){
     this.user=user;
     this.actionType=actionType;
     this.tokenHash=tokenHash;
     this.expiresAt=expiresAt;

    }
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36,name="action_id")
    private String actionId;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @Column(name="action_type",length = 30,nullable = false)
    private String actionType;

    @Column(name="token_hash",columnDefinition = "text",nullable=false)
    private String tokenHash;

    @Column(name="expires_at",nullable = false)
    private LocalDateTime expiresAt;

    @Column(name="used_at")
    private LocalDateTime usedAt;

    @Setter(AccessLevel.NONE)
    @CreationTimestamp
    @Column(name="created_at",nullable = false,updatable = false)
    private LocalDateTime createdAt;


}
