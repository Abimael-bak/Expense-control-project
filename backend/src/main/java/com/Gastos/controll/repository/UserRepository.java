package com.Gastos.controll.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Gastos.controll.entities.Category;
import com.Gastos.controll.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByEmail(String email);
		
	Optional<User> findByName(String name);
	
}
