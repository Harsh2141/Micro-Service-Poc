package com.tender247.poc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tender247.poc.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

}
