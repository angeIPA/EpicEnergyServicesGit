package com.epic.energyservices.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.epic.energyservices.model.Client;
import com.epic.energyservices.model.RegisteredOffice;
import com.epic.energyservices.model.BusinessType;


public interface ClientRepository extends JpaRepository<Client, Integer>{

	
	public Page<Client> findByRegisteredOffice(RegisteredOffice registeredOffice, Pageable paging);

	public Page<Client> findByBusinessName(String businessName, Pageable paging);
	
	public Page<Client> findByCreatedAt(Date createdAT, Pageable paging);
	
	//richiamare createdAt o fattura inserita. fare in fatturaRepository
//	public List<Cliente> findByDataUltimoContatto(LocalDate dataInserimento); 
	
	public List<Client> findByBusinessType(BusinessType businessType);
	
	public Optional<Client> findByPartitaIva(String partitaIva);
	
//	public String deleteBySedeLegale(SedeLegale sedeLegale);
	
//	public List<Cliente> findByDataUltimoContattoBetween(LocalDate startDate, LocalDate endDate);
	
	
}
