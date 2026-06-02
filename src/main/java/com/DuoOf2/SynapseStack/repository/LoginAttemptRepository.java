package com.DuoOf2.SynapseStack.repository;

import com.DuoOf2.SynapseStack.entity.LoginAttempt;
import com.DuoOf2.SynapseStack.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface LoginAttemptRepository extends JpaRepository<LoginAttempt,String>{

 List<LoginAttempt> findByEmail(String email);

//for banning abusive ips
 List<LoginAttempt> findByIpAddress(String ipAddress);

 List<LoginAttempt> findBySuccess(boolean sucess);

 //attempts by user
 List<LoginAttempt> findByUser(User user);

 // for recent activity /brute force protection
 List<LoginAttempt> findByAttemptAtAfter(LocalDateTime time);



}
