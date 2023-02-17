package com.epic.energyservices;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.epic.energyservices.model.BusinessType;
import com.epic.energyservices.model.City;
import com.epic.energyservices.model.Client;
import com.epic.energyservices.model.Contact;
import com.epic.energyservices.model.Invoice;
import com.epic.energyservices.model.InvoiceStatus;
import com.epic.energyservices.model.OperationalHeadquarters;
import com.epic.energyservices.model.RegisteredOffice;
import com.epic.energyservices.model.Utente;
import com.epic.energyservices.repository.CityRepository;
import com.epic.energyservices.repository.ClientRepository;
import com.epic.energyservices.repository.InvoiceRepository;
import com.epic.energyservices.repository.InvoiceStatusRepository;
import com.epic.energyservices.repository.OperationalHeadquartersRepository;
import com.epic.energyservices.repository.ProvinceRepository;
import com.epic.energyservices.serviceImpl.BusinessTypeServiceImpl;
import com.epic.energyservices.serviceImpl.CityServiceImpl;
import com.epic.energyservices.serviceImpl.ClientServiceImpl;
import com.epic.energyservices.serviceImpl.InvoiceServiceImpl;
import com.epic.energyservices.serviceImpl.InvoiceStatusServiceImpl;
import com.epic.energyservices.serviceImpl.UtenteServiceImpl;


@Component
public class EpicEnergyServiceRunner implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(EpicEnergyServiceRunner.class);

	@Autowired
	CityServiceImpl cityServiceImpl;

	@Autowired
	CityRepository cityRepository;

	@Autowired
	ProvinceRepository provinceRepository;

	@Autowired
	ClientServiceImpl clientServiceImpl;

	@Autowired
	ClientRepository clientRepository;

	@Autowired
	InvoiceStatusRepository invoiceStatusRepository;
	
	@Autowired
	InvoiceServiceImpl invoiceServiceImpl;
	
	@Autowired
	InvoiceRepository invoiceRepository;
	
	@Autowired
	OperationalHeadquartersRepository operationalHeadquartersRepository;
	
	@Autowired
	InvoiceStatusServiceImpl invoiceStatusServiceImpl;
	
	@Autowired
	BusinessTypeServiceImpl businessTypeServiceImpl;
	
	@Autowired
	UtenteServiceImpl utenteServiceImpl;
	
	 @Override
	public void run(String... args) throws Exception {
    
		Client c = Client.builder()
				.businessName("ccccccccccccccccccccccccccc")
				.email("ccccccccccccccccccccccccccccccccc@azienda.com")
				//.dataInserimento(LocalDate.now())//sostituito da created at
				.pec("cccccccccccccccccccccccc@pec.it")
				.phone(333333333L)
				.contact(Contact.builder()
						.contactName("cccccccccccccc Nome")
						.contactSurname("cccccccccccccccc Cognome")
						.contactEmail("giallo@contatto.com")
						.contactPhone(147258369L)
						.build())
				.registeredOffice(RegisteredOffice.builder()
						.adress("via Giallo")
						.streetNumber("18")
						.locality("località Giallo")
						.postalCode(230) // fare metodo cap
						.city(City.builder()
								.name("Como")
								.build())
						.build())
				.businessType(BusinessType.builder().businessType("SPA").build()) //db prende l'id
				.operationalHeadquarters(OperationalHeadquarters.builder()
						.adress("via Giallo")
						.streetNumber("8")
						.locality("frazione Giallo")
						.postalCode(27326)
						.city(City.builder()
								.name("Imperia").build())
						.build())
				.build();
		
			clientServiceImpl.save(c);
		//	clienteServiceImpl.update(c, 7);
		//	clienteServiceImpl.delete(5);
		
		
		Invoice f = Invoice.builder()//le date sono sotituite da createdAt
				.number(1)
				.amount(BigDecimal.valueOf(16.50))
				.invoiceStatus(InvoiceStatus.builder()
						.invoiceStatus("Sent")
						.build())
				.build();
		//invoiceServiceImpl.save(f, 1);
		
		Utente u = Utente.builder().email("blu@email.com")
				.name("Bluname")
				.surname("Blusurname")
				.username("blublu")
				.password("blu9")
				.build();
		
		
		//log.info("Invoice amount by year: {}", invoiceRepository.getAmountByYear(2022).size());
		
		
		// log.info("Invoice amount: {}", invoiceServiceImpl.getSum(1, 2022));
		
		
		// CARICAMENTO DB
		
		
//		  if (cityServiceImpl.getProvinces(Pageable.unpaged()).isEmpty()) {
//		  //E:\\Codice\\Epicode\\BE06\\CitiesLoader\\comuni.csv 
//		  var fileName = "C:\\Users\\angje\\eclipse-workspace\\eclipse-workspace\\EpicEnergyServices\\comuni.csv";
//		  // apro uno stream sulla url e richiamo il metodo di caricamento 
//		  var cities =  CitiesLoader.load(new FileInputStream(fileName));
//		  
//		  
//		  cities.stream().map(city -> City.builder().capital(city.isCapital())
//				  .name(city.getName())
//		  .province(Province.builder()
//		  .name(city.getProvince()
//				  .getName()) 
//		  .acronym(city.getProvince()
//				  .getAcronym())
//		  .build()) 
//		  .build())
//		  .forEach(city -> cityServiceImpl.add(city));
//		  
//		  log.info("Loaded {} cities", cities.size());
//		  
//		  }
//		  
//		  log.info("Tutte le città  sono state salvate sul db");

	}
}
