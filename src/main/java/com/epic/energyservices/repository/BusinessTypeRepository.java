package com.epic.energyservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epic.energyservices.model.BusinessType;

public interface BusinessTypeRepository extends JpaRepository<BusinessType, Integer> {
	
	public BusinessType findByBusinessType(String businessType);
	
}
