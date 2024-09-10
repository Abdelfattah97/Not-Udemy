package com.training_system.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.training_system.base.BaseRepository;
import com.training_system.entity.Person;
import com.training_system.entity.User;
import com.training_system.entity.WalletTransaction;



public interface WalletTransactionRepo extends BaseRepository<WalletTransaction, Long>{

	List<WalletTransaction> findByPerson(Person person);
	
	List<WalletTransaction> findByCreatedDateBetween(LocalDateTime from , LocalDateTime to);
	
	@Query(value = "select sum(w.transactionAmount) from WalletTransaction w where w.person = :person")
	Integer getBalance(Person person);

	@Query(value = "select sum(w.transactionAmount) from WalletTransaction w where w.person.id = :person_id")
	Integer getBalance(Long person_id);
	
	List<WalletTransaction> findByPersonUser(User user);
	
}
