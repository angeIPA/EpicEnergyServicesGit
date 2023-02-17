package com.epic.energyservices.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epic.energyservices.exception.AppServiceException;
import com.epic.energyservices.model.BusinessType;
import com.epic.energyservices.repository.BusinessTypeRepository;
import com.epic.energyservices.service.BusinessTypeService;

@Service
public class BusinessTypeServiceImpl implements BusinessTypeService {

	@Autowired
	BusinessTypeRepository businessTypeRepository;

	@Override
	public int businessTypeId(String businessType) {
		try {
			return businessTypeRepository.findByBusinessType(businessType).getId();
		} catch (Exception e) {
			throw new AppServiceException(e);
		}
	}

	@Override
	public BusinessType addBusinessType(BusinessType businessType) { // funziona
		try {
			return businessTypeRepository.save(businessType);
		} catch (Exception e) {
			throw new AppServiceException(e);
		}
	}

}
