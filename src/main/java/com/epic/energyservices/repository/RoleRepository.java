package com.epic.energyservices.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epic.energyservices.security.Role;
import com.epic.energyservices.security.RoleType;


public interface RoleRepository extends JpaRepository<Role, Integer> {
	/**
	 * Ricerca del ruolo per nome.
	 */
	Optional <Role> findByRoleType(RoleType roleType);
}
