package com.cts.retailbank.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cts.retailbank.auth.model.AppUser;
import com.cts.retailbank.auth.model.AuthenticationResponse;
import com.cts.retailbank.auth.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Validationservice {

	@Autowired
	private JwtUtil jwtutil;
	@Autowired
	private UserRepository userRepo;

	public AuthenticationResponse validate(String token) {
		AuthenticationResponse authenticationResponse = new AuthenticationResponse();
		
		log.info("JWT Token {}",token);
		String jwt = token.substring(7);


		if (jwtutil.validateToken(jwt)) {
			AppUser user=userRepo.findById(jwtutil.extractUsername(jwt)).get();
			authenticationResponse.setUserid(jwtutil.extractUsername(jwt));
			authenticationResponse.setValid(true);
			authenticationResponse.setName(user.getUsername());
			authenticationResponse.setRole(jwtutil.extractRole(jwt));
			authenticationResponse.setCustomerId(user.getCustomerId());
		} else {
			authenticationResponse.setValid(false);
		}
		return authenticationResponse;
	}
}