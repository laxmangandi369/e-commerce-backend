package com.mindbowser.springsecurityassignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mindbowser.springsecurityassignment.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
	Role findByName(String role);
}
