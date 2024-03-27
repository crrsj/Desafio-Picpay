package br.com.picpay.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.picpay.domain.User;

public interface UserRepository extends JpaRepository<User, Long>{

 Optional<User>findUserByDocument(String document);
 Optional<User>findUserById(Long id);
}
