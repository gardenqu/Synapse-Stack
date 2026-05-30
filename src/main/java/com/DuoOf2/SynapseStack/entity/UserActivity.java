package com.DuoOf2.SynapseStack.entity;
import lombok.*;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.*;

//This entity will stores/holds data about user activity in the app such as creating a deck, flashcard, changed bio,username
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="user_activities")
public class UserActivity {
    public UserActivity(User user,String activityType,String resource){
        this.user=user;
        this.activityType=activityType;
        this.resource=resource;
    }
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="activity_id",length=36)
    private String activityId;

    @ManyToOne
    @JoinColumn(name="user_id",nullable = false)
    private User user;
    @Column(length = 50,nullable = false,name="activity_type")
    private String activityType;
    // Hibernate by default sets the size of varchar to 255 if you do not set it explicitly
    @Column(nullable = false)
    private String resource;

    @Column(columnDefinition="TEXT")
    private String metadata;

    @Setter(AccessLevel.NONE)
    @Column(name="performed_at",nullable = false)
    @CreationTimestamp
    private LocalDateTime performedAt;




}
