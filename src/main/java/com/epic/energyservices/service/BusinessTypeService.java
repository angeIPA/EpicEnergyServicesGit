package com.epic.energyservices.service;

import com.epic.energyservices.model.BusinessType;

public interface BusinessTypeService {
	
	public int businessTypeId (String businessType);
	
	public BusinessType addBusinessType (BusinessType businessType);
}
