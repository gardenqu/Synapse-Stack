package com.DuoOf2.SynapseStack.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;


@Entity
@Setter
@Getter
@NoArgsConstructor

public class AuthActions {
    public AuthActions(User user,String actionType,String tokenHash,LocalDateTime expiredAt,LocalDateTime createdAt){
     this.user=user;
     this.actionType=actionType;
     this.tokenHash=tokenHash;
     this.expiredAt=expiredAt;
     this.createdAt=createdAt;

    }

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
    private LocalDateTime expiredAt;

    @Column(name="used_at")
    private LocalDateTime usedAt;

    @CreationTimestamp
    @Column(name="created_at",nullable = false,updatable = false)
    private LocalDateTime createdAt;


}
