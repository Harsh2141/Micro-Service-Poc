package com.tender247.poc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tender247.poc.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

	@Query(value = "select * from get_user_by_user_id(:userName)", nativeQuery = true)
	Users findByUserName(String userName);
	
	List<Users> findByUserNameIn(List<String> usernames);
    Boolean existsByUserName(String username);
    Boolean existsByEmail(String email);

}
