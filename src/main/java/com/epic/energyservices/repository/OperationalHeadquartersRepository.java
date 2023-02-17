package com.epic.energyservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epic.energyservices.model.Client;
import com.epic.energyservices.model.OperationalHeadquarters;

public interface OperationalHeadquartersRepository extends JpaRepository<OperationalHeadquarters, Integer>{

	public OperationalHeadquarters findByClientId (int id);
}
