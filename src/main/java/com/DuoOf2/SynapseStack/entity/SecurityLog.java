package com.DuoOf2.SynapseStack.entity;
import jakarta.persistence.*;
import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name="security_logs")
public class SecurityLog {
    public SecurityLog(String eventType,User userId){
        this.eventType=eventType;
        this.userId=userId;
    }
    @Id
    @Column(length=36)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String logId;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User userId;

    @Column(name="event_type",length=50,nullable = false)
    private String eventType;

    @Column(length = 45,name="ip_address")
    private String ipAddress;

    @Column(name="user_agent",columnDefinition = "text")
    private String userAgent;

    @Column(columnDefinition = "text")
    private String details;

    @CreationTimestamp
    @Column(nullable = false,name="created_at")
    private LocalDateTime createdAt;




}
