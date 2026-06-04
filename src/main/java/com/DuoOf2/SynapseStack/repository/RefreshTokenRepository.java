package com.DuoOf2.SynapseStack.repository;

import com.DuoOf2.SynapseStack.entity.RefreshToken;
import com.DuoOf2.SynapseStack.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,String> {

//retries  all tokens that belong to a user
List<RefreshToken> findByUser(User user);

Optional<RefreshToken> findByTokenHash(String tokenHash);

//retrieves active tokens for a user
List<RefreshToken> findByUserAndRevokedFalse(User user);




}
