package com.epic.energyservices.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.epic.energyservices.model.Client;
import com.epic.energyservices.model.Invoice;
import com.epic.energyservices.model.InvoiceStatus;

public interface InvoiceService {
	
	public Invoice save (Invoice invoice, int clientId);
	public Invoice update (Invoice invoice, int clientId);
	public String delete (int number, int clientId);
	
	public Page<Invoice> findByClient (int clientId, Pageable page);
	
	//Data
	public Page<Invoice> findByDate (Date date, Pageable page);
	
	//Anno
	public Page<Invoice> findByYear (int year, Pageable page);
	
	public Page<Invoice> findByYearAndClientId (int year, int clientId, Pageable page);
	public Page<Invoice> findByDateAndClientId (Date date, int clientId, Pageable page);
	public Page<Invoice> findByDateRange(Date startDate, Date endDate, Pageable page);
	//Importo
	public Page<Invoice> findByAmountRange (BigDecimal amountMin, BigDecimal amountMax, Pageable page);
	public Page<Invoice> findByInvoiceRangeAndClientId (BigDecimal amountMin, BigDecimal amountMax, int clientId, Pageable page);
	
	//Stato
	public Page<Invoice> findByInvoiceStatus (String invoiceStatus, Pageable page);
	
	//Fatturato
	public BigDecimal getSum(int clientId, int year);
	public Map<String, BigDecimal> getAmountByYearString(int year);//solo per stampa
	public Map<Client, BigDecimal> getAmountByYear(int year);
	
	
}
