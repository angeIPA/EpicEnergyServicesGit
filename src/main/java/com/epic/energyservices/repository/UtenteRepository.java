package com.epic.energyservices.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epic.energyservices.model.Utente;


public interface UtenteRepository extends JpaRepository<Utente, Integer>{

	public Optional<Utente> findById(int userId);
	public List<Utente> findByName(String name);
	public List<Utente> findBySurname(String surname);
	public Optional<Utente> findByEmail(String email);
	public List<Utente> findAll();
}
