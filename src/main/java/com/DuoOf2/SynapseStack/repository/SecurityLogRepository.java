package com.DuoOf2.SynapseStack.repository;

import com.DuoOf2.SynapseStack.entity.AuthActions;
import com.DuoOf2.SynapseStack.entity.SecurityLog;
import com.DuoOf2.SynapseStack.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SecurityLogRepository extends JpaRepository<SecurityLog,String> {

List<SecurityLog> findByUser(User user);

List<SecurityLog> findByCreatedAtAfter(LocalDateTime time);

}
