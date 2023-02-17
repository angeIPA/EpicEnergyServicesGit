package com.epic.energyservices.serviceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.epic.energyservices.exception.AppServiceException;
import com.epic.energyservices.model.Client;
import com.epic.energyservices.model.Invoice;
import com.epic.energyservices.model.BusinessType;
import com.epic.energyservices.repository.CityRepository;
import com.epic.energyservices.repository.ClientRepository;
import com.epic.energyservices.repository.InvoiceRepository;
import com.epic.energyservices.repository.ProvinceRepository;
import com.epic.energyservices.repository.OperationalHeadquartersRepository;
import com.epic.energyservices.repository.BusinessTypeRepository;
import com.epic.energyservices.service.ClientService;


@Transactional
@Service
public class ClientServiceImpl implements ClientService {

	@Autowired
	ClientRepository clientRepository;

	@Autowired
	ProvinceRepository provinceRepository;

	@Autowired
	InvoiceRepository invoiceRepository;

	@Autowired
	OperationalHeadquartersRepository operationalHeadquartersRepository;

	@Autowired
	CityServiceImpl cityServiceImpl;

	@Autowired
	CityRepository cityRepository;

	@Autowired
	BusinessTypeRepository businessTypeRepository;

	
	@Transactional
	@Override
	public Client save(Client client) { // funziona 
		try {
			// controllo comune Sede Legale
			String registeredOfficeCityName = client.getRegisteredOffice().getCity().getName();
			if (cityRepository.findByName(registeredOfficeCityName).isPresent()) {
				client.getRegisteredOffice().setCity(cityRepository.findByName(registeredOfficeCityName).get());
			} else {
				throw new AppServiceException();
			}
			
			// controllo comune Sede Operativa
			String operationalHeadquartesCityName = client.getOperationalHeadquarters().getCity().getName();
			if (cityRepository.findByName(operationalHeadquartesCityName).isPresent()) {
				client.getOperationalHeadquarters().setCity(cityRepository.findByName(operationalHeadquartesCityName).get());
				client.getOperationalHeadquarters().setClient(client);
			} else {
				throw new AppServiceException();
			}
			/**calcolo PartitaIva**/
			// cerco id della provincia collegata al comune
			int provinceId = cityServiceImpl.findProvinceIdByCityName(registeredOfficeCityName);
			// creo il codice per provincia
			String provinceCode = provinceCode(provinceId);
			// calcolo la partita iva
			String partitaIva = partitaIvaCalc(randomNumber(), provinceCode);
			// controllo se p iva generata è già presente nel db NON FUNZIONA
			if (clientRepository.findByPartitaIva(partitaIva).isPresent()) {
				throw new AppServiceException();
			}
			client.setPartitaIva(partitaIva);
			
			// set del tipo
			client.setBusinessType(businessTypeRepository.findByBusinessType(client.getBusinessType().getBusinessType()));
			  
			 
			return clientRepository.save(client);
		} catch (Exception e) {
			throw new AppServiceException(e);
		}
	}

	@Override
	public Client update(Client client, int id) { //funziona
		try {
			Client c = clientRepository.findById(id).get();
			if (client.getBusinessName() != null)
				c.setBusinessName(client.getBusinessName());
			if (client.getEmail() != null)
				c.setEmail(client.getEmail());
			if (client.getPec() != null)
				c.setPec(client.getPec());
			if (!Objects.isNull(client.getPhone()))
				c.setPhone(client.getPhone());

		// sede legale embedded
			if (client.getRegisteredOffice().getAdress() != null)
				c.getRegisteredOffice().setAdress((client.getRegisteredOffice().getAdress()));
			if (client.getRegisteredOffice().getStreetNumber() != null)
				c.getRegisteredOffice().setStreetNumber((client.getRegisteredOffice().getStreetNumber()));
			if (client.getRegisteredOffice().getLocality() != null)
				c.getRegisteredOffice().setLocality((client.getRegisteredOffice().getLocality()));
			if (!Objects.isNull(client.getRegisteredOffice().getPostalCode()))
				c.getRegisteredOffice().setPostalCode((client.getRegisteredOffice().getPostalCode()));
		
			
			// comune non cambia. comune è ManyToOne //non posso cambiare il comune?
			if (client.getRegisteredOffice().getCity().getName() != null) {
				c.getRegisteredOffice().setCity( cityRepository.findByName
				 (client.getRegisteredOffice().getCity().getName()).get());
			}
			  
			  // sede operativa OneToOne
		  if (client.getOperationalHeadquarters().getAdress() != null)
				c.getOperationalHeadquarters().setAdress((client.getOperationalHeadquarters().getAdress()));
			if (client.getOperationalHeadquarters().getStreetNumber() != null)
				c.getOperationalHeadquarters().setStreetNumber((client.getOperationalHeadquarters().getStreetNumber()));
			if (client.getOperationalHeadquarters().getLocality() != null)
				c.getOperationalHeadquarters().setLocality((client.getOperationalHeadquarters().getLocality()));
			if (!Objects.isNull(client.getOperationalHeadquarters().getPostalCode()))
				c.getOperationalHeadquarters().setPostalCode((client.getOperationalHeadquarters().getPostalCode()));
			// comune non cambia ManyToOne
			
		  if (client.getOperationalHeadquarters().getCity().getName() != null) {
			  c.getOperationalHeadquarters().setCity(cityRepository.findByName
				(client.getOperationalHeadquarters().getCity().getName()).get());
			  }
			// contatto
			if (client.getContact().getContactEmail() != null)
				c.getContact().setContactEmail((client.getContact().getContactEmail()));
			if (client.getContact().getContactName() != null)
				c.getContact().setContactName((client.getContact().getContactName()));
			if (client.getContact().getContactSurname() != null)
				c.getContact().setContactSurname((client.getContact().getContactSurname()));
			if (!Objects.isNull(client.getContact().getContactPhone()))
				c.getContact().setContactPhone((client.getContact().getContactPhone()));
			
			c.setBusinessType(businessTypeRepository.findByBusinessType(client.getBusinessType().getBusinessType()));
	
			return clientRepository.save(c);
		} catch (Exception e) {
			throw new AppServiceException();
		}

	}

	@Override
	public String delete(int id) { // funziona
		try {
			Client c = clientRepository.findById(id).get();
			if (!invoiceRepository.findByClient(c).isEmpty()) {
				invoiceRepository.deleteAll(invoiceRepository.findByClient(c));
			}
			c.setBusinessType(null);
				c.getOperationalHeadquarters().setCity(null);
				c.getRegisteredOffice().setCity(null);
			clientRepository.delete(c);
			return "Deleted";
		} catch (Exception e) {
			throw new AppServiceException(e);
		}

	}

//----------------------------------------------------------------------------------------------------------------	
	//-- 	orderBy
	
	@Override
	public List<Entry<String, Date>> orderByLastContact() {
		try {
			Map<String, Date> lastContact = new TreeMap<>();
			for(Client c : clientRepository.findAll()) {
				lastContact.put(c.getBusinessName(), getLastContact(c.getId()));
			}
			List<Entry<String, Date>> list = new ArrayList<>(lastContact.entrySet());
			list.sort(Entry.comparingByValue());
			//list.forEach(System.out::println);
			return list;
		} catch (Exception e) {
			throw new AppServiceException(e);
		}
	}
	
	@Override
	public Page<Client> orderByBusinessName() {
		try {
			List <Client> clients = clientRepository.findAll().stream()
					.sorted(Comparator.comparing(c -> c.getBusinessName()))
					.collect(Collectors.toList());
					 return new PageImpl<>(clients);
		} catch (Exception e) {
			throw new AppServiceException(e);
		}
	}
	
	@Override
	public Page<Client> orderByRegistrationDate() {
		try {
			return new PageImpl<>(clientRepository.findAll().stream().sorted(Comparator.comparing(Client::getCreatedAt))
					.collect(Collectors.toList()));
		} catch (Exception e) {
			throw new AppServiceException();
		}
	}
	
	@Override
	public Page<Client> orderByRegisteredOfficeProvince() { 
		try {
			return new PageImpl<>(clientRepository.findAll().stream()
					.sorted(Comparator.comparing(c -> c.getRegisteredOffice().getCity().getProvince().getName()))
					.collect(Collectors.toList()));
		} catch (Exception e) {
			throw new AppServiceException();
		}
	
	}
	
	@Override
	public Page<Client> findAllClientsPageSort(Integer page, Integer size, String dir, String sort) {
		try {
			Pageable paging = PageRequest.of(page, size, Sort.Direction.fromString(dir), sort);
			Page<Client> pagedResult = clientRepository.findAll(paging);
			if(pagedResult.hasContent()) {
				return pagedResult;
			}
		} catch (Exception e) {
			throw new AppServiceException();
		}
		return null;
	}
	
	
	//--	filterBy
	
	/*
	@Override
	public List<Cliente> filtraPerRagioneSociale(String ragioneSociale, Pageable paging) {
		return clienteRepository.findAll().stream()
				.filter(c -> ragioneSociale.contains(c.getRagioneSociale()))
				.sorted(Comparator.comparing(Cliente::getRagioneSociale))
				.collect(Collectors.toList());
	}
	*/

	@Override
	public List<Client> filterByBusinessName(String businessName) {
		return clientRepository.findAll().stream()
				.filter(c -> c.getBusinessName().contains(businessName))
				.sorted(Comparator.comparing(Client::getBusinessName))
				.collect(Collectors.toList());
	}
	
	@Override
	public List<Client> filterByRegistrationDate(Date startDate, Date endDate) { 
		try {
			return clientRepository.findAll().stream().filter(
					c -> !c.getCreatedAt().before(startDate) && !c.getCreatedAt().after(endDate))
					.collect(Collectors.toList());
		} catch (Exception e) {
			throw new AppServiceException(e);
		}
	}

	
	
	//--	get
	
	@Override
	public Date getLastContact(int clientId) { 
		try {
			Client c = clientRepository.findById(clientId).get();
			List<Invoice> invoices = invoiceRepository.findByClient(clientRepository.findById(clientId).get());
			// se ci sono fatture
			if (!invoices.isEmpty()) {
				return invoices.stream().map(Invoice::getCreatedAt).max(Date::compareTo).get();
			}
			// se lista vuota, metti data inserimento
			return c.getCreatedAt();
		} catch (Exception e) {
			throw new AppServiceException(e);
		}
	}
	
	@Override
	public Date getRegistrationDate(int clientId) { 
		try {
			return clientRepository.findById(clientId).get().getCreatedAt();
		} catch (Exception e) {
			throw new AppServiceException(e);
		}
	}

	
	//----------------------------------------------------------------------------------------------------------------	
			
	
	//PARTITA IVA

		@Override
		public int randomNumber() { // genera 7 numeri random
			try {
				return (int) (Math.random() * 9999999) + 100000;
			} catch (Exception e) {
				throw new AppServiceException(e);
			}

		}

		@Override
		public String provinceCode(int provinceId) { // metodo che associa acronimo a codice id(non so come fare con
															// //FUNZIONA // codici veri)
			try {
				String provinceCode = "";
				String id = provinceId + "";
				if (id.length() == 1) {
					provinceCode = "00" + id;
				} else if (id.length() == 2) {
					provinceCode = "0" + id;
				} else if (id.length() == 3) {
					provinceCode = id;

				}
				return provinceCode;
			} catch (Exception e) {
				throw new AppServiceException(e);
			}
		}

		@Override
		public String partitaIvaCalc(int randomNumber, String provinceCode) { // algoritmo di Luhn
			try {
				int even = 0;
				int odd = 0;
				int controlCode = 0;
				// unisco i valori in una stringa
				String firstPartString = randomNumber + provinceCode;
				// divido in array di caratteri
				char[] array = firstPartString.toCharArray();
				// somma dei numeri in posizione pari
				for (int i = 0; i < array.length; i++) {
					if (i % 2 == 0) {
						int n = Character.getNumericValue(array[i]);
						even += n;
					}
				}
				// somma dei numeri in posizione dispari
				for (int i = 0; i < array.length; i++) {
					if ((i % 2) != 0) {
						int n = Character.getNumericValue(array[i]);
						int oddMultiplication = n * 2;
						if (oddMultiplication > 9) {
							oddMultiplication -= 9;
						}
						odd += oddMultiplication;
					}
				}
				// somma generale
				int totalEvenOdd = even + odd;

				// codice di controllo
				// se somma minore di 10 trovo la differenza
				if (totalEvenOdd < 10) {
					controlCode = 10 - totalEvenOdd;
				} else {
					// se somma maggiore di 10 prendo solo ultima cifra
					String totalEvenOddString = totalEvenOdd + "";
					char controlCodeChar = totalEvenOddString.charAt(totalEvenOddString.length() - 1);
					int controlCodeC = Character.getNumericValue(controlCodeChar);
					controlCode = 10 - controlCodeC;

				}
				String partitaIvaString = "IT" + randomNumber + provinceCode + controlCode;
				return partitaIvaString;
			} catch (Exception e) {
				throw new AppServiceException(e);
			}

		}

		
		
		@Override
		public Page<Client> findPaginated(int pageNumber, int pageSize,  String sortField, String sortDirection) {
			Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :  Sort.by(sortField).descending();
			Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);//per iniziare da pag 0
			return this.clientRepository.findAll(pageable);
		}
		

	
		
		@Override
		public List<Client> getAllClient() {
			// TODO Auto-generated method stub
			return clientRepository.findAll();
		}


		
		
		
		
		
		

	//----------------------------------------------------------------------------------------------------------------		
	

	/*	@Override
		public List<Cliente> findByTipo(Tipo tipo) { //embedded?
			try {
				return clienteRepository.findByTipo(tipo);
			} catch (Exception e) {
				throw new AppServiceException(e);
			}
		}*/
//---------------------------------------------------------------------------------------------------------------------	

		
		
		/*	
	@Override
	public Page<Cliente> findByRagioneSociale(String ragioneSociale, Pageable paging) { // funziona
		try {
			return clienteRepository.findByRagioneSociale(ragioneSociale, paging);
		} catch (Exception e) {
			throw new AppServiceException();
		}
	}
*/	
/*	
	@Override
	public List<Cliente> findByProvincia(String provincia) { // funziona
		try {
			return clienteRepository.findAll().stream()
					.filter(c -> c.getSedeLegale().getComune().getProvince().getName().equals(provincia))
					.collect(Collectors.toList());
		} catch (Exception e) {
			throw new AppServiceException(e);
		}
	}

	
*/
	

	

//----------------------------------------------------------------------------------------------------------------		








}
