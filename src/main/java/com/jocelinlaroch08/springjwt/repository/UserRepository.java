package com.jocelinlaroch08.springjwt.repository;

import com.jocelinlaroch08.springjwt.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByEmail(String email);
}