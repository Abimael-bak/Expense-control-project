package com.Gastos.controll.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Gastos.controll.entities.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long>{

}
