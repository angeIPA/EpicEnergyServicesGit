package com.epic.energyservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epic.energyservices.model.InvoiceStatus;

public interface InvoiceStatusRepository extends JpaRepository<InvoiceStatus, Integer> {
	
	public InvoiceStatus findByInvoiceStatus(String stato);
	
}
