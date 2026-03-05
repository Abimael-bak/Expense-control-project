package com.Gastos.controll.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Gastos.controll.entities.Role;


public interface RoleRepository extends JpaRepository<Role, Long>{

	Role findByName(String name);
}
