package com.epic.energyservices.security;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.epic.energyservices.model.Utente;
import com.epic.energyservices.repository.ClientRepository;
import com.epic.energyservices.repository.UtenteRepository;



@Controller
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	ClientRepository clientRepository;

	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	UtenteRepository utenteRepository;
	
	
	@GetMapping("/login")
	public ModelAndView authenticateUser(@RequestParam String email, @RequestParam String password,
			HttpServletResponse response) {
		ModelAndView myView = new ModelAndView("homePage");
		List<Utente> utenti = utenteRepository.findAll();
		myView.addObject("utenti", utenti);
		Utente utente = utenteRepository.findByEmail(email).get();
		myView.addObject("utente", utente);
		LoginRequest loginRequest = new LoginRequest(email, password);
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		authentication.getAuthorities();
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		Cookie cookie = new Cookie("Provider", jwt);
		cookie.setMaxAge(86400);
		cookie.setSecure(true);
		cookie.setHttpOnly(true);
		cookie.setPath("/");
		response.addCookie(cookie);
		UtenteDetailsImpl userDetails = (UtenteDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());
		LoginResponse loginResponse = new LoginResponse(jwt, userDetails.getId(), userDetails.getEmail(), roles,
				userDetails.getExpirationTime());
		for (String s : roles) {
			if (s.equals("ROLE_ADMIN")) {
				myView.addObject("admin", 1);
			} else if (s.equals("ROLE_USER")) {
				myView.addObject("user", 2);
			}
		}
		ResponseEntity.ok(loginResponse);
		return myView;
	}
	
	

	@GetMapping("/loginResponseEntity")
	public ModelAndView authenticateUser(@ModelAttribute LoginRequest loginRequest) {
	//public ModelAndView authenticateUser(@RequestBody LoginRequest loginRequest) {
		// Usa l'AuthenticationManager per autenticare i parametri della request
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		// Ottiene i privilegi dell'utente
		authentication.getAuthorities();
		ModelAndView mav = new ModelAndView("homePage");
		// Ottiene il SecurityContext
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Genera il token
		String token = jwtUtils.generateJwtToken(authentication);

		// Ottiene i dati dell'utente
		UtenteDetailsImpl userDetails = (UtenteDetailsImpl) authentication.getPrincipal();

		// Ottiene i ruoli dell'utente
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		// Restituisce la response con status 200, token e dati dell'utente
		var response = new LoginResponse(token, userDetails.getId(), userDetails.getUsername(),
				userDetails.getEmail(), roles, userDetails.getExpirationTime());
		mav.addObject(response);
		ResponseEntity.ok(response);
		return mav;
	}
	

}
