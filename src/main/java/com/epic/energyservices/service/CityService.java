package com.epic.energyservices.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.epic.energyservices.model.City;
import com.epic.energyservices.model.Province;


public interface CityService {
	// aggiunge una città
	City add(City city);
	// aggiunge un elenco di città
	int saveAll(List<City> cities);
	List<City> getAllCities();
	Optional<City> getById(int id);
	// recupera una città con nome e provincia
	Optional<City> getByName(String name, String acronym);
	// recupera tutte le città di una provincia
	Page<City> getByProvince(String acronym, Pageable pageable);
	// recupera l'elenco delle province
	Page<Province> getProvinces(Pageable pageable);
	
	Province findProvinceByCityName(String name);
	int findProvinceIdByCityName(String name);
	
}
