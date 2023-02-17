package com.epic.energyservices.serviceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.epic.energyservices.exception.AppServiceException;
import com.epic.energyservices.model.Client;
import com.epic.energyservices.model.Invoice;
import com.epic.energyservices.model.InvoiceStatus;
import com.epic.energyservices.repository.ClientRepository;
import com.epic.energyservices.repository.InvoiceRepository;
import com.epic.energyservices.repository.InvoiceStatusRepository;
import com.epic.energyservices.service.InvoiceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@Transactional
@Service
public class InvoiceServiceImpl implements InvoiceService {

	@Autowired
	InvoiceRepository invoiceRepository;

	@Autowired
	ClientServiceImpl clientServiceImpl;

	@Autowired
	InvoiceStatusServiceImpl invoiceStatusServiceImpl;

	@Autowired
	InvoiceStatusRepository invoiceStatusRepository;

	@Autowired
	ClientRepository clientRepository;
	
	@PersistenceContext
	public EntityManager em;

	// non funziona findByCliente

	@Override
	public Invoice save(Invoice invoice, int clientId) { // funziona
		try {
			Client c = clientRepository.findById(clientId).get();
			invoice.setClient(c);

			// setto il valore della fattura ///
			int lastInvoiceNumber = invoiceRepository.findByClient(clientRepository.findById(clientId).get())
					.size();
			invoice.setNumber(lastInvoiceNumber + 1);

			// stato 
//			fattura.setStato(statoRepository.findById(statoServiceImpl.idStato(fattura.getStato().getStato())).get());
			invoice.setInvoiceStatus(invoiceStatusRepository.findByInvoiceStatus(invoice.getInvoiceStatus().getInvoiceStatus()));
			// aggiungo alla lista fatture del cliente //ha senso?
			invoiceRepository.findByClient(clientRepository.findById(clientId).get()).add(invoice);
			//rimane uguale mentre il cratedAt si modfica con le modifiche
			invoice.setYear(LocalDate.now().getYear());


			// salvo fattura nel db
			return invoiceRepository.save(invoice);
		} catch (Exception e) {
			throw new AppServiceException(e);
		}
	}
	
	@Override
	public Invoice update(Invoice invoice, int clientId) { // funziona
		try {
			Invoice f = invoiceRepository.findByNumberAndClientId(invoice.getNumber(), clientId);
			if (f != null) {
				if (!Objects.isNull(invoice.getAmount())) {
					f.setAmount(invoice.getAmount());
				}
				if (!Objects.isNull(invoice.getNumber()))
					f.setNumber(invoice.getNumber());
				f.setYear(LocalDate.now().getYear());
				f.setInvoiceStatus(invoiceStatusRepository.findByInvoiceStatus(invoice.getInvoiceStatus().getInvoiceStatus()));
				//dataCreazione non cambia
			}
			return invoiceRepository.save(f);
		} catch (Exception e) {
			throw new AppServiceException(e);
		}
	}

	@Override
	public String delete(int number, int clientId) { // funziona
		try {
			Invoice invoice = invoiceRepository.findByNumberAndClientId(number, clientId);
			invoice.setInvoiceStatus(null);
			invoiceRepository.delete(invoice);
			return "Deleted";
		} catch (Exception e) {
			throw new AppServiceException(e);
		}

	}

//---------------------------------------------------------------------------

	@Override
	public Page<Invoice> findByClient(int clientId, Pageable page) { // funziona
		try {
			return invoiceRepository.findByClient(clientRepository.findById(clientId).get(), page);
		} catch (Exception e) {
			throw new AppServiceException(e);
		}
	}
	
	@Override
	public Page<Invoice> findByInvoiceStatus(String invoiceStatus, Pageable page) { // embedded
		try {
			
		return invoiceRepository.findByInvoiceStatus(invoiceStatusRepository.findByInvoiceStatus(invoiceStatus), page);
		} catch (Exception e) {
			throw new AppServiceException(e);
		}
	}
	
	@Override  //tutte dell'anno di tutti i clienti
	public Page<Invoice> findByYear(int year, Pageable page) { // funziona
		try {
			return invoiceRepository.findByYear(year, page);
		} catch (Exception e) {
			throw new AppServiceException();
		}
	}
	
	@Override
	public Page<Invoice> findByYearAndClientId(int year, int clientId, Pageable page) {
		try {
			return new PageImpl<>(invoiceRepository.findByClient(clientRepository.findById(clientId).get()).stream().filter(f -> f.getYear()==year).collect(Collectors.toList()));
		} catch (Exception e) {
			throw new AppServiceException(e);
		}
		
	}

	@Override //non trova
	public Page<Invoice> findByDate(Date createdAt, Pageable page) {
		try {
		return invoiceRepository.findByCreatedAt(createdAt, page);
		} catch (Exception e) {
			throw new AppServiceException(e);
		}
	}
	
	@Override //non trova
	public Page<Invoice> findByDateAndClientId(Date date, int clientId, Pageable page) {
		
	return new PageImpl<>(invoiceRepository.findByClient(clientRepository.findById(clientId).get())
			.stream()
			.filter(f -> f.getCreatedAt().equals(date))
			.collect(Collectors.toList()));
	}
	
	@Override
	public Page<Invoice> findByDateRange(Date startDate, Date endDate, Pageable page) { 
		try {
			return new PageImpl<>(invoiceRepository.findAll().stream()
					.filter(f -> !f.getCreatedAt().before(startDate) && !f.getCreatedAt().after(endDate))
					.collect(Collectors.toList()));
		} catch (Exception e) {
			throw new AppServiceException(e);
		}
	}
	
	
	@Override //fare page
	public Page<Invoice> findByAmountRange(BigDecimal amountMin, BigDecimal amountMax, Pageable page) { 
		try {
			return new PageImpl<>(invoiceRepository.findAll().stream().filter(
					f -> f.getAmount().compareTo(amountMin) == 1 && f.getAmount().compareTo(amountMax) == -1)
					.collect(Collectors.toList()));
		} catch (Exception e) {
			throw new AppServiceException();
		}
	}
	
	@Override
	public Page<Invoice> findByInvoiceRangeAndClientId(BigDecimal importoMin, BigDecimal importoMax, int clientId, Pageable page) { // funziona
		try {
			return new PageImpl<>(invoiceRepository.findByClient(clientRepository.findById(clientId).get()).stream().filter(
					f -> f.getAmount().compareTo(importoMin) == 1 && f.getAmount().compareTo(importoMax) == -1)
					.collect(Collectors.toList()));
		} catch (Exception e) {
			throw new AppServiceException();
		}
	}

//---------------------------------------------------------------------------
	@Override
	public BigDecimal getSum(int clientId, int year) {
		return invoiceRepository.getSum(clientId, year);
	};
	
	@Override
	public Map<String,BigDecimal> getAmountByYearString(int year){
		Map<String,BigDecimal> map = new TreeMap<>();
		for (Invoice f : invoiceRepository.findAll()){
			map.put(f.getClient().getBusinessName(), getSum(f.getClient().getId(),year));
		}
		return  map;
	}
/*	
	@Override
	public Map<Cliente,BigDecimal> getAmountByYear(int anno){
		return fatturaRepository.getAmountByYear(anno);
	}
*/	
	@Override
	public Map<Client,BigDecimal> getAmountByYear(int year){
		Map<Client,BigDecimal> map = new TreeMap<>();
		for (Invoice f : invoiceRepository.findAll()){
			map.put(f.getClient(), getSum(f.getClient().getId(),year));
		}
		return  map;
	}
	
	


/*	

	@Override
	public List<Fattura> findByDataRange(LocalDate startDate, LocalDate endDate) { // funziona
		try {
			return fatturaRepository.findAll().stream()
					.filter(f -> !f.getData().isBefore(startDate) && !f.getData().isAfter(endDate))
					.collect(Collectors.toList());
		} catch (Exception e) {
			throw new AppServiceException(e);
		}
	}


*/

}
