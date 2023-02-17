package com.epic.energyservices.serviceImpl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.epic.energyservices.exception.AppServiceException;
import com.epic.energyservices.model.City;
import com.epic.energyservices.model.Province;
import com.epic.energyservices.repository.CityRepository;
import com.epic.energyservices.repository.ProvinceRepository;
import com.epic.energyservices.service.CityService;

@Transactional
@Service
public class CityServiceImpl implements CityService {

	@Autowired
	CityRepository cityRepository;

	@Autowired
	ProvinceRepository provinceRepository;

	@Transactional
	@Override
	public City add(City city) {
		try {
			// recupero la provincia dal database
			Province province;
			var p = provinceRepository.findByAcronym(city.getProvince().getAcronym()); // cerco la provincia
			if (p.isEmpty())
				// se non c'è la inserisco sul database
				province = provinceRepository.save(city.getProvince());
			else
				// altrimenti prendo quella recuperata
				province = p.get();
			// alla fine metto il risultato della precedente istruzione nella provincia
			// della città 
			city.setProvince(province);
			return cityRepository.save(city);
		} catch (Exception e) {
			throw new AppServiceException(e);
		}
	}

	@Override
	public int saveAll(List<City> cities) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<City> getAllCities() {
		try {
			return cityRepository.findAll();
		} catch (Exception e) {
			throw new AppServiceException(e);
		}
	}

	@Override
	public Optional<City> getById(int id) {
		try {
			return cityRepository.findById(id);
		} catch (Exception e) {
			throw new AppServiceException(e);
		}
	}

	@Override
	public Optional<City> getByName(String name, String acronym) {
		try {
			return cityRepository.findByNameAndProvinceAcronym(name, acronym);
		} catch (Exception e) {
			throw new AppServiceException(e);
		}
	}

	@Override
	public Page<City> getByProvince(String acronym, Pageable pageable) {
		try {
			return cityRepository.findAllByProvinceAcronym(acronym, pageable);
		} catch (Exception e) {
			throw new AppServiceException(e);
		}
	}

	@Override
	public Page<Province> getProvinces(Pageable pageable) {
		try {
			return provinceRepository.findAll(pageable);
		} catch (Exception e) {
			throw new AppServiceException(e);
		}
	}

	@Override
	public Province findProvinceByCityName(String nome) {
		try {
			String acronym = "";
			Province province = new Province();
			if (cityRepository.findByName(nome).isPresent()) {
				acronym = cityRepository.findByName(nome).get()
						.getProvince()
						.getAcronym();
			
			}
			if (provinceRepository.findByAcronym(acronym).isPresent()) {
				province = provinceRepository.findByAcronym(acronym).get();
			}
			return province;
		} catch (Exception e) {
			throw new AppServiceException(e);
		}

	}

	@Override
	public int findProvinceIdByCityName(String name) {
		try {
			String acronym = "";
			Province province = new Province();
			if (cityRepository.findByName(name).isPresent()) {
				acronym = cityRepository.findByName(name).get()
						.getProvince()
						.getAcronym();
			}
			if (provinceRepository.findByAcronym(acronym).isPresent()) {
				province = provinceRepository.findByAcronym(acronym).get();
			}
			return province.getId();
		} catch (Exception e) {
			throw new AppServiceException(e);
		}
	}

}
