package com.training_system.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.training_system.base.BaseControllerImpl;
import com.training_system.entity.PaymentMethod;

@RestController
@RequestMapping("/api/payment-method")
public class PaymentMethodController extends BaseControllerImpl<PaymentMethod, Long> {

}
