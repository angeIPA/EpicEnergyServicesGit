package com.epic.energyservices.rest.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.epic.energyservices.model.Utente;
import com.epic.energyservices.service.UtenteService;
import com.epic.energyservices.serviceImpl.UtenteServiceImpl;


@CrossOrigin(origins = "http://domain2.com", maxAge=3600)
@RestController
@RequestMapping("/rest/utente")
public class UtenteControllerRest {
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	UtenteServiceImpl utenteServiceImpl;
	
	@PostMapping("/save")
	public Utente save(@RequestBody Utente utente) {
		return utenteServiceImpl.save(utente);
	}

}
