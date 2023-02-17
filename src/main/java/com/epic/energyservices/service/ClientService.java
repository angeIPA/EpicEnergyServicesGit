package com.epic.energyservices.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.epic.energyservices.model.Client;
import com.epic.energyservices.model.Invoice;
import com.epic.energyservices.model.BusinessType;

public interface ClientService {
	
	public Client save (Client client);
	public Client update (Client client, int clientId);
	public String delete (int clientId);
	
	/***FILTRA***/
	//cerca per parte del nome
	public List<Client> filterByBusinessName(String businessName);
	
	//restituisce ultimo contatto  del cliente selezionato in base a createdAt
	public Date getLastContact(int clientId);	

	//restituisce data inserimento del cliente selezionato in base a createdAt
	public Date getRegistrationDate(int clientId);
	
	//filtra per fattura annuale(vedi fattura repository query getSum)
	
	
	/***ORDINA***/
	
	//ordina per nome
	public Page<Client> orderByBusinessName ();
		
		
	//ordina per ultimo contatto
	public List<Entry<String, Date>> orderByLastContact(); //mappa ordinata per valori
	
	//ordina per data di inserimento
	public Page<Client> orderByRegistrationDate();
	
	//ordina per provincia sede legale
	public Page<Client> orderByRegisteredOfficeProvince();
	
	//ordina per fatturato annuale query su repository di fattura
	
	
	
	//---------------------------------------------------------------------------------------------------------------
	
	
		

	//PartitaIva
	public int randomNumber();
	public String provinceCode(int provinceId);//da acronimo a codice
	public String partitaIvaCalc(int randomNumber, String provinceCode);//in save, crea la stringa di pIva
	
	//pageable
	
	public Page<Client> findAllClientsPageSort(Integer page, Integer size, String dir, String sort);
	
	public List<Client> filterByRegistrationDate(Date startDate, Date endDate);
	//Tipo

//	public List<Cliente> findByTipo(Tipo tipo);
	
	//page
	
	Page<Client> findPaginated(int pageNumber, int pageSize, String sortField, String sortDirection);

	List<Client> getAllClient();
	
	
	//---------------------------------------------------------------------------------------------------------------

	
//	public Page<Cliente> findByRagioneSociale (String ragioneSociale, Pageable paging);
//	public List<Cliente> findByProvincia (String provincia);
	

/*	//FatturatoAnnuale REPOSITORY DI FATTURA
	public BigDecimal calcoloFatturatoAnnuale(List<Fattura>fatture, int anno);
	public List<Cliente> findByFatturatoAnnualeRange (BigDecimal fatturatoAnnualeMin, BigDecimal fatturatoAnnualeMax, int anno);
	public List<Cliente> findByFatturatoAnnuale (BigDecimal fatturatoAnnuale, int anno);
	public Page<Cliente> ordinaPerFatturatoAnnuale (int anno, Pageable paging);
*/	
	//UltimoContatto
//	public List<Cliente> findByDataUltimoContattoRange (LocalDate startDate, LocalDate endDate);
//	public List<Cliente> findByDataUltimoContattoBetween (LocalDate startDate, LocalDate endDate);

	
	//DataInserimento
	
	
	

//	public List<Cliente> findByDataInserimento (LocalDate dataInserimento);
	
	
	
	
	
	
	
	/*
	 * 
	 * 
	 * 
	 * 
	Il back-end deve fornire al front-end tutte le funzioni necessarie a gestire in modo completo (Aggiungere, modificare ed eleiminare)i suddetti elementi.
	Deve essere possibile ordinare i clienti per:


	// PAGEABLE
	 
	Nome 
	Fatturato annuale
	Data di inserimento
	Data di ultimo contatto
	Provincia della sede legale.

	Deve essere possibile filtrare la lista clienti per:

	Fatturato annuale
	Data di inserimento
	Data di ultimo contatto
	Parte del nome
*/
}
