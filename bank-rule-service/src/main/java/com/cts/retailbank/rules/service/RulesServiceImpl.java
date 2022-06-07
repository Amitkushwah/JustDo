package com.cts.retailbank.rules.service;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.cts.retailbank.rules.model.RulesInput;
import com.cts.retailbank.rules.model.ServiceResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RulesServiceImpl implements RulesService {

	private static final int MINIMUM_BALANCE = 2500;

	@Override
	public HashMap<String, Object> evaluateBalance(RulesInput account) {
		HashMap<String, Object> body=new HashMap<>(); 
		log.info("account {}",account);
		body.put("minimumBalance", MINIMUM_BALANCE);
		log.info("account bal {}",account.getCurrentBalance());
		if (MINIMUM_BALANCE <= account.getCurrentBalance()) {
			log.info("In true}");
			body.put("isNotMinimum", true);
			return body;
		} else {
			log.info("In false");
			body.put("isNotMinimum", false);
			return body;
		}
	}

	@Override
	public ServiceResponse serviceCharges(RulesInput account) {
		ServiceResponse serviceResponse = new ServiceResponse();
		serviceResponse.setAccountId(account.getAccountId());

		// Checking Balance
		if (account.getCurrentBalance() < MINIMUM_BALANCE) {
			double deducted = account.getCurrentBalance() / 10;
			serviceResponse.setMessage("Your Balance is lesser than the minimum required balance. So amount of \\u20B9"
					+ deducted + " is deducted from your account.");
			serviceResponse.setBalance(account.getCurrentBalance() - deducted);
		} else {
			serviceResponse.setMessage("No deduction");
			serviceResponse.setBalance(account.getCurrentBalance());
		}
		return serviceResponse;
	}

}
