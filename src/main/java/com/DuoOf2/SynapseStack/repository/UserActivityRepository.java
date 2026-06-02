package com.DuoOf2.SynapseStack.repository;

import com.DuoOf2.SynapseStack.entity.SecurityLog;
import com.DuoOf2.SynapseStack.entity.User;
import com.DuoOf2.SynapseStack.entity.UserActivity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface UserActivityRepository extends JpaRepository<UserActivity,String> {

    List<UserActivity> findByUser(User user);

    List<UserActivity> findByPerformedAtAfter(LocalDateTime time);

    List<UserActivity> findByUserAndActivityType(User user,String activity);


}
