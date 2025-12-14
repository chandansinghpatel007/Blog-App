package com.csp.blogapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.csp.blogapp.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
