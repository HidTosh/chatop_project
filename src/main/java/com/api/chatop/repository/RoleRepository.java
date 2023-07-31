package com.api.chatop.repository;

import com.api.chatop.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
