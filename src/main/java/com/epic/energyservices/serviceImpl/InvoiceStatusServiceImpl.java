package com.epic.energyservices.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epic.energyservices.exception.AppServiceException;
import com.epic.energyservices.model.InvoiceStatus;
import com.epic.energyservices.repository.InvoiceStatusRepository;
import com.epic.energyservices.service.InvoiceStatusService;

@Service
public class InvoiceStatusServiceImpl implements InvoiceStatusService {

	@Autowired
	InvoiceStatusRepository invoiceStatusRepository;

	@Override
	public int invoiceStatusId(String invoiceStatus) {
		try {
			return invoiceStatusRepository.findByInvoiceStatus(invoiceStatus).getId();
		} catch (Exception e) {
			throw new AppServiceException(e);
		}

	}

	@Override
	public InvoiceStatus addInvoiceStatus(InvoiceStatus invoiceStatus) { // funziona
		try {
			return invoiceStatusRepository.save(invoiceStatus);
		} catch (Exception e) {
			throw new AppServiceException(e);
		}

	}
}
