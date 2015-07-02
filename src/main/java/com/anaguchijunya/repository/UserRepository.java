package com.anaguchijunya.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anaguchijunya.domain.User;

public interface UserRepository extends JpaRepository<User, String> {

}
