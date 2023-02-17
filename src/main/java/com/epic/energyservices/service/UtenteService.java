package com.epic.energyservices.service;

import com.epic.energyservices.model.Utente;

public interface UtenteService {

	public Utente save(Utente utente);
	public Utente update(Utente utente, int utenteId);
	public String delete(int utenteId);
	
}
