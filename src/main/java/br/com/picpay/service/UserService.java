package br.com.picpay.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import br.com.picpay.domain.User; 
import br.com.picpay.enums.UserType;
import br.com.picpay.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository repository;
	
	public void validateTransaction(User sender,BigDecimal amount) throws Exception {
		if(sender.getUserType() == UserType.MERCHANT) {
			throw new Exception("Usuário do tipo lojista não está autorizado a realizar a transação");
		}
	   if(sender.getBalance().compareTo(amount) < 0 ) {
		   throw new Exception("Saldo insuficiente");
	   }
	  
	}
	
	 public User findUserById(Long id) throws Exception {
		 return this.repository.findUserById(id).orElseThrow(()->new Exception("Usuário não encontrado"));
		   
	   }
     public void sendUser(User user) {
    	 repository.save(user);
     }
}
