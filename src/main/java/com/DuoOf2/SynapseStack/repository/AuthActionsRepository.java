package com.DuoOf2.SynapseStack.repository;

import com.DuoOf2.SynapseStack.entity.AuthActions;
import com.DuoOf2.SynapseStack.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthActionsRepository extends JpaRepository<AuthActions,String> {
List<AuthActions> findByUser(User user);

Optional<AuthActions> findByTokenHash(String token);


}
