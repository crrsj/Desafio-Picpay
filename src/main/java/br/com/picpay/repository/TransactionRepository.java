package br.com.picpay.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.picpay.domain.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
