package com.epic.energyservices.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.spring5.expression.SPELContextMapWrapper;

import com.epic.energyservices.model.BusinessType;
import com.epic.energyservices.model.Client;
import com.epic.energyservices.model.Invoice;
import com.epic.energyservices.model.InvoiceStatus;
import com.epic.energyservices.repository.ClientRepository;
import com.epic.energyservices.repository.InvoiceRepository;
import com.epic.energyservices.repository.InvoiceStatusRepository;
import com.epic.energyservices.serviceImpl.InvoiceServiceImpl;


@Controller
@RequestMapping("/invoice")
public class InvoiceController {

	@Autowired
	InvoiceServiceImpl invoiceServiceImpl;
	
	@Autowired
	InvoiceRepository invoiceRepository;
	
	@Autowired
	ClientRepository clientRepository;
	
	@Autowired
	InvoiceStatusRepository invoiceStatusRepository;
	
	@PostMapping("/save")
	public String save(@ModelAttribute Invoice invoice) {
		int clientId = invoice.getClient().getId(); 
		invoiceServiceImpl.save(invoice, clientId);
		return "redirect:/";
	}
	
	@GetMapping("/newInvoiceForm/{clientId}") 
	public ModelAndView newInvoiceForm(@PathVariable int clientId) {
		ModelAndView mav = new ModelAndView("newInvoice");
		Invoice invoice = new Invoice();
		invoice.setClient(clientRepository.findById(clientId).get());
		mav.addObject("invoice", invoice);
		List<InvoiceStatus> is = invoiceStatusRepository.findAll();
		mav.addObject("invoiceStatus",is);
		return mav;
	}
	
	@PostMapping("/update") 
	public String update(@ModelAttribute Invoice invoice) {
		int clientId = invoice.getClient().getId(); 
		invoiceServiceImpl.update(invoice, clientId);
		return "redirect:/";
	}

	
	@GetMapping("/updateForm/{invoiceId}") 
	public ModelAndView updateForm(@PathVariable int invoiceId) {
		Invoice i = invoiceRepository.findById(invoiceId).get();
		List<InvoiceStatus> is = invoiceStatusRepository.findAll();
		ModelAndView mav = new ModelAndView("updateInvoice");
		mav.addObject("invoice",i);
		mav.addObject("invoiceStatus",is);
		return mav;
	}
	
	@GetMapping("/delete/{number}/{clientId}") 
	public String delete(@PathVariable int number, @PathVariable int clientId) {
		 invoiceServiceImpl.delete(number, clientId);
		 return "redirect:/";
	}
	
	//--	
	
	@GetMapping("/findByClient/{clientId}") 
	public ModelAndView findByClient(@PathVariable int clientId, Pageable page) {
		ModelAndView mav = new ModelAndView("invoiceList");
		mav.addObject("invoiceClient", clientId);
		mav.addObject("invoiceList",invoiceServiceImpl.findByClient(clientId, page));
		return mav;
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
	
//	@GetMapping("/getAmountByYear") 
//	public ModelAndView getAmountByYear(@RequestParam int year) {
//		ModelAndView mav = new ModelAndView("getAmountByYear");
//		mav.addObject("amount",invoiceServiceImpl.getAmountByYear(year).keySet());
//		return mav;
//	}
	
	@GetMapping("/getAmountByYear") 
	public ModelAndView getAmountByYear(@RequestParam int year){
		ModelAndView mav = new ModelAndView("getAmountByYear");
		PropertiesForm form = new PropertiesForm();
		form.setProperties(invoiceRepository.getAmountByYear(year));
		mav.addObject("form", form);
		return mav;
	}
	
}
