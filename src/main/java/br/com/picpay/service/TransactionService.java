package br.com.picpay.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.picpay.domain.Transaction;
import br.com.picpay.domain.User;
import br.com.picpay.dto.TransactionDto;
import br.com.picpay.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {

	private final TransactionRepository repository;
	
	private final UserService service;
	
	private final RestTemplate template;
	
	public void createTransaction(TransactionDto transaction) throws Exception {
		User sender = this.service.findUserById(transaction.senderId());
		User receiver = this.service.findUserById(transaction.receiverId());
		service.validateTransaction(sender, transaction.value());
		
		boolean isAuthorized  = this.authorizeTransaction(sender, transaction.value());
		if(!isAuthorized) {
			throw new Exception("Transação não autorizada");
		}
	
		Transaction newTransaction = new Transaction();
		newTransaction.setAmount(transaction.value());
		newTransaction.setSender(sender);
		newTransaction.setReceiver(receiver);
		newTransaction.setTimestamp(LocalDateTime.now());
	
	    sender.setBalance(sender.getBalance().subtract(transaction.value()));
	    receiver.setBalance(receiver.getBalance().add(transaction.value()));
	    this.repository.save(newTransaction);
	    this.service.saveUser(sender);
	    this.service.saveUser(receiver);
	}     	
	
	
	
	public boolean authorizeTransaction(User sender,BigDecimal value) {
		ResponseEntity<Map>authotizationResponse = template.getForEntity("https://run.mocky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc",Map.class);
		if(authotizationResponse.getStatusCode() == HttpStatus.OK) {
			String message = (String) authotizationResponse.getBody().get("message");
			return "Autorizado".equalsIgnoreCase(message);
		}else return false;
	}
}
