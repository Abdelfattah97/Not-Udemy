package com.training_system.repo;

import org.springframework.stereotype.Repository;

import com.training_system.base.BaseRepository;
import com.training_system.entity.Country;
import com.training_system.entity.Payment;

@Repository
public interface PaymentRepo extends BaseRepository<Payment, Long>{

}
