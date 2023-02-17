package com.epic.energyservices.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.epic.energyservices.model.City;

public interface CityRepository extends JpaRepository<City, Integer> {
	
	Optional<City> findByNameAndProvinceAcronym(String name, String acronym);

	Page<City> findAllByProvinceAcronym(String acronym, Pageable pageable);
	
	Optional<City> findByName(String name);
}
