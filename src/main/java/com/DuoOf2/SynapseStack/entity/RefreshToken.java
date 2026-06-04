package com.DuoOf2.SynapseStack.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.*;

//This Entity stores/holds data about tokens used to keep user logged in
@Entity
@NoArgsConstructor
@Setter
@Getter
@Table(name="refresh_tokens")
public class RefreshToken {
    public RefreshToken(User user,String tokenHash,boolean revoked,LocalDateTime expiredAt){
        this.user=user;
        this.tokenHash=tokenHash;
        this.revoked=revoked;
        this.expiredAt=expiredAt;
    }
    @Setter(AccessLevel.NONE)
    @Id
    @Column(columnDefinition = "char(36)",name="token_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String tokenId;

    @ManyToOne
    @JoinColumn(name="user_id",nullable = false)
    private User user;
    @Column(name="token_hash",columnDefinition="TEXT",nullable=false)
    private String tokenHash;
    @Column(nullable = false)
    private boolean revoked=false;

    //token expired date
    @Column(name="expired_at",nullable = false)
    private LocalDateTime expiredAt;

    @Column(name="replaced_by",columnDefinition = "CHAR(36)")
    private String replacedBy;
    //keeps track of where token was created
    @Column(name="ip_address",length=45)
    private String ipAddress;

    @Column(name="user_agent",columnDefinition = "TEXT")
    private String userAgent;

    @Setter(AccessLevel.NONE)
    @Column(name="created_at",nullable = false,updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;




}
