package com.epic.energyservices.rest.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.epic.energyservices.model.Client;
import com.epic.energyservices.model.Invoice;
import com.epic.energyservices.model.InvoiceStatus;
import com.epic.energyservices.serviceImpl.InvoiceServiceImpl;

@RestController
@RequestMapping("/rest/invoice")
public class InvoiceControllerRest {

	@Autowired
	InvoiceServiceImpl invoiceServiceImpl;
	
	@GetMapping("/save")
	public Invoice save(@RequestBody Invoice invoice, @RequestParam int clientId) {
		return invoiceServiceImpl.save(invoice, clientId);
	}
	
	@GetMapping("/update") 
	public Invoice update(@RequestBody Invoice invoice, @RequestParam int clientId) {
		return invoiceServiceImpl.update(invoice, clientId);
	}
	
	@GetMapping("/delete") 
	public String delete(@RequestParam int numero, int clientId) {
		return invoiceServiceImpl.delete(numero, clientId);
	}
	
	//--	
	
	@GetMapping("/findByClient") 
	public Page<Invoice> findByClient(@RequestParam int clientId, Pageable page) {
		return invoiceServiceImpl.findByClient(clientId, page);
	}
	
	@GetMapping("/findByInvoiceStatus") 
	public Page<Invoice> findByInvoiceStatus(@RequestParam String invoiceStatus, Pageable page) {
		return invoiceServiceImpl.findByInvoiceStatus(invoiceStatus, page);
	}
	
	@GetMapping("/findByYear") 
	public Page<Invoice> findByYear(@RequestParam int year, Pageable page) {
		return invoiceServiceImpl.findByYear(year, page);
	}
	
	@GetMapping("/findByYearAndClientId") 
	public Page<Invoice> findByYearAndClientId(@RequestParam int year, int clientId, Pageable page) {
		return invoiceServiceImpl.findByYearAndClientId(year,clientId, page);
	}
	
	@GetMapping("/findByDate")  //non trova
	public Page<Invoice> findByDate(@RequestParam Date createdAt, Pageable page) {
		return invoiceServiceImpl.findByDate(createdAt, page);
	}
	@GetMapping("/findByDateRange")
	public Page<Invoice> findByDateRange(@RequestParam Date startDate, Date endDate, Pageable page){
		return invoiceServiceImpl.findByDateRange(startDate, endDate, page);
	}
	
	@GetMapping("/findByDateAndClientId")  //non trova
	public Page<Invoice> findByDateAndClientId(@RequestParam Date date, int clientId, Pageable page) {
		return invoiceServiceImpl.findByDateAndClientId(date, clientId, page);
	}
	
	
	@GetMapping("/findByAmountRange")
	public Page<Invoice> findByAmountRange(@RequestParam BigDecimal amountMin, BigDecimal amountMax, Pageable page){
		return invoiceServiceImpl.findByAmountRange(amountMin, amountMax, page);
	}
	
	@GetMapping("/findByInvoiceRangeAndClientId")
	public Page<Invoice> findByInvoiceRangeAndClientId(@RequestParam BigDecimal amountMin, BigDecimal amountMax, int clientId, Pageable page){
		return invoiceServiceImpl.findByInvoiceRangeAndClientId(amountMin, amountMax, clientId, page);
	}
	
	//--
	
	@GetMapping("/getSum") 
	public BigDecimal getSum(@RequestParam int clientId, int year) {
		return invoiceServiceImpl.getSum(clientId, year);
	}
	
	@GetMapping("/getAmountByYearString") 
	public Map<String, BigDecimal> getAmountByYearString(@RequestParam  int year) {
		return invoiceServiceImpl.getAmountByYearString(year);
	}
	
	@GetMapping("/getAmountByYear") 
	public Map<Client, BigDecimal> getAmountByYear(@RequestParam  int year) {
		return invoiceServiceImpl.getAmountByYear(year);
	}
	
}
