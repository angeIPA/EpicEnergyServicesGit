package com.epic.energyservices.serviceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.epic.energyservices.exception.AppServiceException;
import com.epic.energyservices.model.Utente;
import com.epic.energyservices.repository.RoleRepository;
import com.epic.energyservices.repository.UtenteRepository;
import com.epic.energyservices.security.RoleType;
import com.epic.energyservices.service.UtenteService;


@Service
public class UtenteServiceImpl implements UtenteService{
	
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	UtenteRepository utenteRepository;
	
	@Autowired
	RoleRepository roleRepository;

	@Override
	public Utente save(Utente utente) {
		try {
			if(utenteRepository.findByEmail(utente.getEmail()).isPresent()) {
				throw new AppServiceException();
			}
			utente.setPassword(encoder.encode(utente.getPassword()));
			return utenteRepository.save(utente);
		} catch (Exception e) {
			throw new AppServiceException();
		}
	}

	@Override
	public Utente update(Utente utente, int utenteId) {
		try {
			Utente u = utenteRepository.findById(utenteId).get();
			if(u != null) {
				if(utente.getName() != null)
					u.setName(utente.getName());
				if(utente.getSurname()!= null)
					u.setSurname(utente.getSurname());
				if(utente.getEmail() != null && !utenteRepository.findByEmail(utente.getEmail()).isPresent()){
					u.setEmail(utente.getEmail());
				}
				return utenteRepository.save(utente);
			}
			 log.error("Utente {} non trovato", utenteId);
			} catch (Exception e) {
			throw new AppServiceException(e);
		}
		return null;
	}

	@Override
	public String delete(int utenteId) {
		try {
			utenteRepository.deleteById(utenteId);
		} catch (Exception e) {
			log.error("Utente {} non trovato", utenteId);
			throw new AppServiceException(e);
			}
		return null;
	}
/*	
	public RoleType stringToEnumRole(String roleType) {
		try {
			if(RoleType.valueOf(roleType) != null);
			return RoleType.valueOf(roleType);
		} catch (Exception e) {
			throw new AppServiceException(e);
		}
	}	
*/
}
