package com.fintech.auth.repository;

import com.fintech.auth.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<Auth, Long> {
  @Query("select a from Auth a where a.email = :email")
  Optional<Auth> findByEmail(String email);

}
