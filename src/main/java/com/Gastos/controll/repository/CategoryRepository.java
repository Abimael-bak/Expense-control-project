package com.Gastos.controll.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Gastos.controll.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}
