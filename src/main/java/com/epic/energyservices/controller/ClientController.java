package com.epic.energyservices.controller;

import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import org.springframework.web.servlet.ModelAndView;

import com.epic.energyservices.model.BusinessType;
import com.epic.energyservices.model.Client;
import com.epic.energyservices.model.OperationalHeadquarters;
import com.epic.energyservices.repository.BusinessTypeRepository;
import com.epic.energyservices.repository.ClientRepository;
import com.epic.energyservices.repository.OperationalHeadquartersRepository;
import com.epic.energyservices.serviceImpl.ClientServiceImpl;




@Controller
@RequestMapping("/client")
public class ClientController {
	
	@Autowired
	ClientServiceImpl clientServiceImpl;
	
	@Autowired
	ClientRepository clientRepository;
	
	@Autowired
	OperationalHeadquartersRepository operationalHeadquartersRepository;
	
	@Autowired
	BusinessTypeRepository businessTypeRepository;
	
	
	@PostMapping("/save")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String save(@ModelAttribute Client client) {
		clientServiceImpl.save(client); //
		return "redirect:/utente/listaNomeCognomeUA"; // non funziona
	}

//-----------------------------------------------------------------------------	
	
	//UPDATE
	
	
	@GetMapping("/updateForm/{clientId}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView updateForm(@PathVariable (value = "clientId") int clientId) {
		ModelAndView mav = new ModelAndView("updateClient");
		Client client = clientRepository.findById(clientId).get();
		OperationalHeadquarters oh = operationalHeadquartersRepository.findByClientId(clientId);
		List<BusinessType> bt = businessTypeRepository.findAll();
		mav.addObject("businessTypeList", bt);
		mav.addObject("operationalHeadquarters", oh);
		mav.addObject("client", client);
		return mav;
	}
	
	@PostMapping("/update")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String update(@ModelAttribute Client client) {
		clientServiceImpl.update(client, client.getId()); //
		return "homePage"; // non funziona
	}
	
//--------------------------------------------------------------------------------
	
	
	
	
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/delete") 
	public String delete(@RequestParam int clientId) {
		return clientServiceImpl.delete(clientId);
	}
	
//----
	
	@GetMapping("/clientDetails/{clientId}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	public ModelAndView clientDetails(@PathVariable (value = "clientId") int clientId) {
			ModelAndView mav = new ModelAndView("clientDetails");
			Client c = clientRepository.findById(clientId).get();
			OperationalHeadquarters oh = operationalHeadquartersRepository.findByClientId(clientId);
			mav.addObject("client", c);
			mav.addObject("operationalHeadquarters", oh);
			return mav;	
	}
	
	
	
	
	
//--	orderBy
	@GetMapping("/orderByLastContact")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	public ModelAndView orderByLastContact() {
			ModelAndView mav = new ModelAndView("orderByLastContact");
			mav.addObject("clientList", clientServiceImpl.orderByLastContact());
			return mav;	
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
	
	//metodo homepage per la paginazione e lista
	@GetMapping("/list")
	public String viewHomePage(Model model) {
	//	model.addAttribute("listClient", clientServiceImpl.getAllClient());
		return findPaginated(1, model, "businessName", "asc");//1 è il default pageNumber
	}	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	@GetMapping("/page/{pageNumber}")
	public String findPaginated(@PathVariable (value = "pageNumber") int pageNumber, Model model, @RequestParam("sortField") String sortField,@RequestParam("sortDir") String sortDir ) {
		//indico numero elementi per pag
		int pageSize = 3;
		//creo la pagina con metodo implementato nel service
		Page<Client> page = clientServiceImpl.findPaginated(pageNumber, pageSize, sortField, sortDir);
		//impagino la mia lista di client con page.getContent();
		List<Client> listClient = page.getContent();
		//model per richiamare il numero della pagina che si sta visitando al momento
		model.addAttribute("currentPage", pageNumber);
		//per indicare il numero totale di pagine (page.getTotalPages() esiste in page)
		model.addAttribute("totalPages", page.getTotalPages());
		//indica numero tot di elementi
		model.addAttribute("totalElements", page.getTotalElements());
		//aggiungo la lista clienti che ho impaginato sopra
		model.addAttribute("listClient", listClient);
		
		//sorting
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		return "clientList";
	}

	
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////
	


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
