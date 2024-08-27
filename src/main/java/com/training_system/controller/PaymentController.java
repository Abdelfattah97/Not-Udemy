package com.training_system.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.training_system.base.BaseControllerImpl;
import com.training_system.entity.Payment;

@RestController
@RequestMapping("/api/payment")
public class PaymentController extends BaseControllerImpl<Payment, Long> {

}
