package com.cts.retailbank.rules.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cts.retailbank.rules.exception.MinimumBalanceException;
import com.cts.retailbank.rules.model.RulesInput;
import com.cts.retailbank.rules.service.RulesServiceImpl;

@Controller
public class RulesController {

	@Autowired
	public RulesServiceImpl rulesServiceImpl;

	@PostMapping("/evaluateMinBal")
	public ResponseEntity<?> evaluate(@RequestBody RulesInput account) throws MinimumBalanceException {
		// Jwt token is checked
		// check the accountId is Not null
		if (account.getCurrentBalance() == 0) {
			throw new MinimumBalanceException("INVALED");
		} else {
			HashMap<String, Object> body=rulesServiceImpl.evaluateBalance(account);
			return new ResponseEntity<Object>(body, HttpStatus.OK);
		}
	}

}
