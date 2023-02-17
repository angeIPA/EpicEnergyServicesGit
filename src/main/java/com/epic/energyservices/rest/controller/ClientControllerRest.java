package com.epic.energyservices.rest.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.epic.energyservices.model.Client;
import com.epic.energyservices.serviceImpl.ClientServiceImpl;


@RestController
@RequestMapping("/rest/client")
public class ClientControllerRest {
	
	@Autowired
	ClientServiceImpl clientServiceImpl;
	
//	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/save")
	public Client save(@RequestBody Client client) {
		return clientServiceImpl.save(client);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/update") //ripete il risultato
	public Client update(@RequestBody Client client, @RequestParam int clientId) {
		return clientServiceImpl.update(client, clientId);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/delete") 
	public String delete(@RequestParam int clientId) {
		return clientServiceImpl.delete(clientId);
	}
	
//--	orderBy
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	@GetMapping("/orderByLastContact") 
	public List<Entry<String, Date>> orderByLastContact(){
		return clientServiceImpl.orderByLastContact();
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	@GetMapping("/orderByBusinessName") 
	public Page<Client> orderByBusinessName(){
		return clientServiceImpl.orderByBusinessName();
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	@GetMapping("/orderByRegistrationDate") 
	public Page<Client> orderByRegistrationDate(){
		return clientServiceImpl.orderByRegistrationDate();
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	@GetMapping("/orderByRegisteredOfficeProvince") 
	public Page<Client> orderByRegisteredOfficeProvince(){
		return clientServiceImpl.orderByRegisteredOfficeProvince();
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	@GetMapping(value = "/findAllClientsPageSort")
	public Page<Client> findAllClientiPageSort(@RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "3") Integer size, @RequestParam(defaultValue = "asc") String dir,
			@RequestParam(defaultValue = "id") String sort) {
		Page<Client> pag = clientServiceImpl.findAllClientsPageSort(page, size, dir, sort);
		return pag;
	}
	
//--	filterBy
	
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	@GetMapping("/filterByBusinessName") 
	public List<Client> filterByBusinessName(@RequestParam String businessName) {
		return clientServiceImpl.filterByBusinessName(businessName);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	@GetMapping("/filterByRegistrationDate") 
	public List<Client> filterByRegistrationDate(@RequestParam Date startDate, Date endDate){
		return clientServiceImpl.filterByRegistrationDate(startDate, endDate);
	}
	
	
//--	get
	
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	@GetMapping("/getLastContact") 
	public Date getLastContact(@RequestParam int clientId) {
		return clientServiceImpl.getLastContact(clientId);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	@GetMapping("/getRegistrationDate") 
	public Date getRegistrationDate(@RequestParam int clientId) {
		return clientServiceImpl.getRegistrationDate(clientId);
	}
	


	


/*	
	@GetMapping("/findByDataUltimoContattoRange") //come fare su postman?
	public List<Cliente> findByDataUltimoContattoRange(@RequestBody LocalDate dataInizio, LocalDate dataFine){
		return clienteServiceImpl.findByDataUltimoContattoRange(dataInizio, dataFine);
	}
	
	@GetMapping("/findByProvincia")
	public List<Cliente> findByProvincia(@RequestParam String provincia){
		return clienteServiceImpl.findByProvincia(provincia);
	}
	

	
	@GetMapping(value = "/findAllRagioneSocialePageSort")//e per altri metodi
	public Page<Cliente> findAllFatturatoAnnualePageSort(@RequestParam String ragioneSociale, @RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "3") Integer size, @RequestParam(defaultValue = "asc") String dir,
			 @RequestParam(defaultValue = "id") String sort) {
		Pageable paging = PageRequest.of(page, size, Sort.Direction.fromString(dir), sort);
		return  clienteServiceImpl.findByRagioneSociale(ragioneSociale, paging);
		
	}
	
	@GetMapping(value = "/ordinaPerFatturatoAnnuale")//e per altri metodi
	public Page<Cliente> ordinaPerFatturatoAnnuale(@RequestParam int anno, @RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "3") Integer size, @RequestParam(defaultValue = "asc") String dir,
			 @RequestParam(defaultValue = "id") String sort) {
		Pageable paging = PageRequest.of(page, size, Sort.Direction.fromString(dir), sort);
		return  clienteServiceImpl.ordinaPerFatturatoAnnuale(anno, paging);
		}
*/		
	

}
