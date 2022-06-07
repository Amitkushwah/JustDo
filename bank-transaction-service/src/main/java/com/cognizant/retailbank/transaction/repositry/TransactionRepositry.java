package com.cognizant.retailbank.transaction.repositry;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cognizant.retailbank.transaction.model.FinancialTransactions;

/*
 * Database Repository class of Transaction MicroService
 */

@Repository
public interface TransactionRepositry extends JpaRepository<FinancialTransactions, Long> {
	
	public List<FinancialTransactions> findAllByAccountIdIn(List<String> accounts,Sort sort);
	
	@Query(value = "select * from financial_transactions where account_id=:accountId AND date_of_transaction BETWEEN :startDate AND :endDate",nativeQuery = true)
	public List<FinancialTransactions> findAllByDateOfTransactionBetween(@Param("startDate") String startDate,@Param("endDate") String endDate,@Param("accountId") String accountId);
	
	@Query(value = "select * from financial_transactions where MONTH(date_of_transaction) = MONTH(CURRENT_DATE()) AND account_id=:accountId",nativeQuery = true)
	public List<FinancialTransactions> findAllByMonth(@Param("accountId") String accountId);
	
}
