package com.Gastos.controll.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Gastos.controll.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
