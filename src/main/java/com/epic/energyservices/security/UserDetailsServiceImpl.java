package com.epic.energyservices.security;

import java.util.Optional; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epic.energyservices.model.Utente;
import com.epic.energyservices.repository.UtenteRepository;


/* UserDetailsServiceImpl 
Implementa l’interfaccia UserDetailsService fornita dal modulo Spring Security.
L'interfaccia UserDetailsService viene utilizzata per recuperare i dati relativi all'utente. 
Ha un metodo chiamato loadUserByUsername() che può essere sovrascritto per personalizzare il processo di ricerca dell'utente
UserDetailsService restituisce un'implementazione UserDetails che contiene le GrantedAuthorities
*/
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UtenteRepository utenteRepository;

	@Override
	// Il metodo viene eseguito in una transazione DB
	@Transactional
	// Cerca l'utente nel DB e ritorna l'utente tramite l'implementazione di UserDetailsImpl o un'eccezione
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Utente> utente = utenteRepository.findByEmail(email);
		if (utente.isPresent()) {
			return UtenteDetailsImpl.build(utente.get());
		} else {
			throw new UsernameNotFoundException("Utente " + email + "non trovato!");
		}
	}

}
