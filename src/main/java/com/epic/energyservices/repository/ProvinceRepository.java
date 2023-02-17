package com.epic.energyservices.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epic.energyservices.model.Province;



public interface ProvinceRepository extends JpaRepository<Province, Integer> {
	// recupera una provincia tramite la sigla
	Optional<Province> findByAcronym(String acronym);
}
