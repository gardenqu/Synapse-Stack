package com.DuoOf2.SynapseStack.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.DuoOf2.SynapseStack.entity.User;
import java.util.Optional;
public interface UserRepository extends JpaRepository<User,String>{

Optional<User> findByEmail(String email);

boolean  existsByEmail(String email);


}
