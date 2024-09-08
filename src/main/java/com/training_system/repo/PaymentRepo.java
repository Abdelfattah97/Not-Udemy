package com.training_system.repo;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.training_system.base.BaseRepository;
import com.training_system.entity.Country;
import com.training_system.entity.Payment;
import com.training_system.entity.Person;

@Repository
public interface PaymentRepo extends BaseRepository<Payment, Long>{
	
	List<Payment> findByBuyer(Person buyer);

}
