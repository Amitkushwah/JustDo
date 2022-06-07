package com.cts.retailbank.rules.service;

import java.util.HashMap;

import com.cts.retailbank.rules.model.RulesInput;
import com.cts.retailbank.rules.model.ServiceResponse;

public interface RulesService {

	public HashMap<String, Object> evaluateBalance(RulesInput account);

	public ServiceResponse serviceCharges(RulesInput account);

}
