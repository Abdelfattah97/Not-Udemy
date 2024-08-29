package com.training_system.repo;

import org.springframework.stereotype.Repository;

import com.training_system.base.BaseRepository;
import com.training_system.entity.Country;
import com.training_system.entity.PaymentMethod;

@Repository
public interface PaymentMethodRepo extends BaseRepository<PaymentMethod, Long>{

}
