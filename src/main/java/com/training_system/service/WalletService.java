package com.training_system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.training_system.entity.Person;
import com.training_system.entity.User;
import com.training_system.entity.WalletTransaction;
import com.training_system.exceptions.InsufficientBalanceException;
import com.training_system.repo.WalletTransactionRepo;

@Service
public class WalletService {

	@Autowired
	WalletTransactionRepo walletTransactionRepo;
	
	@Autowired
	UserService userService;
	
	  WalletTransaction deposit(Person person,int amount) {
		WalletTransaction transaction = 
				WalletTransaction.builder()
				.person(person)
				.transactionAmount(amount)
				.build();
		
		return walletTransactionRepo.save(transaction);
	}
	
	 WalletTransaction deduct(Person person , int amount) {
		 if(walletTransactionRepo.getBalance(person)<amount) {
			 throw new InsufficientBalanceException("Not Enough Balance to make the deduction");
		 }
		//make transaction negative
		WalletTransaction transaction = 
				WalletTransaction.builder()
				.person(person)
				.transactionAmount(amount*-1)
				.build();
		
		return walletTransactionRepo.save(transaction);
	}
	
	
	public List<WalletTransaction>  findAll(UserDetails userDetails){
		String username = userDetails.getUsername();
		User user = userService.findByUserName(username);
		boolean isAdmin = user.getRoles().stream()
				.filter(role -> role.getName().equalsIgnoreCase("master"))
				.findAny()
				.isPresent();	
		
		if(isAdmin) {
			return findAll();
		}
			return findAllByUser(user);
	}
	
	@PreAuthorize("hasAuthority('master')")
	private List<WalletTransaction> findAll(){
		return walletTransactionRepo.findAll();
	}
	
	public List<WalletTransaction> findAllByUser(User user){
		return walletTransactionRepo.findByPersonUser(user);
	}
	
//	@PreAuthorize()
	public Double getBalance(UserDetails userDetails) {
		Person person =userService.findByUserName(userDetails.getUsername()).getPerson();
		Integer balanceInCents= walletTransactionRepo.getBalance(person);
		if(balanceInCents==null) return 0.0;
		return balanceInCents/100.0;
	}
	
	public boolean isUserOwnerOfWallet(Person person , String userName) {
		return userService.findByUserName(userName).getPerson().getId().equals(person.getId());
	}
	
	
}
